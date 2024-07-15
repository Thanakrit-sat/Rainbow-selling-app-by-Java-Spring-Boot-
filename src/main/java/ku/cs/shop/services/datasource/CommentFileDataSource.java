package ku.cs.shop.services.datasource;

import ku.cs.shop.models.CommentList;
import ku.cs.shop.models.Commentator;

import java.io.*;

public class CommentFileDataSource implements DataSource<CommentList> {
    private String directoryName;
    private String filename;
    private CommentList commentList;

    public CommentFileDataSource() {
        this("csv-database", "comment-list.csv");
    }

    public CommentFileDataSource(String directoryName, String filename) {
        this.directoryName = directoryName;
        this.filename = filename;
        initialFileIfNotExist();
    }

    private void initialFileIfNotExist() {
        File file = new File(directoryName);
        if (!file.exists()) {
            file.mkdir();
        }

        String path = directoryName + File.separator + filename;
        file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public CommentList readData() {
        commentList = new CommentList();
        String path = directoryName + File.separator + filename;
        File file = new File(path);

        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);

            String line = "";
            while( (line = buffer.readLine()) != null ){
                String[] data = line.split(",");
                String name = data[0].trim();
                String text = data[1].trim();
                double score = Double.parseDouble(data[2].trim());
                String toProductName = data[3].trim();

                commentList.addComment(new Commentator(name, text, score, toProductName));

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return commentList;
    }

    @Override
    public void writeData(CommentList commentList) {
        String path = directoryName + File.separator + filename;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file);
            buffer = new BufferedWriter(writer);

            buffer.write(commentList.toCSV());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addData(Commentator commentator) {
        this.commentList.addComment(commentator);
        writeData(this.commentList);
    }
}
