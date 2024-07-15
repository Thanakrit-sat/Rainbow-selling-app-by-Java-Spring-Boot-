package ku.cs.shop.services.datasource;

import ku.cs.shop.models.Order;
import ku.cs.shop.models.OrderList;
import ku.cs.shop.models.ReportList;
import ku.cs.shop.models.Reporter;

import java.io.*;

public class ReportFileDataSource implements DataSource<ReportList>{
    private String directoryName;
    private String filename;
    private ReportList reportList;

    public ReportFileDataSource() {
        this("csv-database", "report-list.csv");
    }

    public ReportFileDataSource(String directoryName, String filename) {
        this.directoryName = directoryName;
        this.filename = filename;
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
    public ReportList readData() {
        reportList = new ReportList();
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
                String reporter = data[0].trim();
                String title = data[1].trim();
                String text = data[2].trim();
                String storeName = data[3].trim();
                String productName = data[4].trim();

                reportList.addReport(new Reporter(
                        reporter,
                        title,
                        text,
                        storeName,
                        productName)
                );
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

        return reportList;
    }

    @Override
    public void writeData(ReportList reportList) {
        String path = directoryName + File.separator + filename;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file);
            buffer = new BufferedWriter(writer);

            buffer.write(reportList.toCSV());

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

    public void addData(Reporter info) {
        this.reportList.addReport(info);
        writeData(this.reportList);
    }
}
