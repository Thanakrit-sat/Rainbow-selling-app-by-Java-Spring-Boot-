package ku.cs.shop.services.datasource;

import ku.cs.shop.models.MemberAccount;
import ku.cs.shop.models.MemberAccountList;

import java.io.*;

public class UserAccountSource implements DataSource<MemberAccountList>{
    private String directory;
    private String filename;
    private MemberAccountList userList;

    public UserAccountSource(){
        this("csv-database", "useraccount.csv");
    }

    public UserAccountSource(String directory, String filename) {
        this.directory = directory;
        this.filename = filename;
        initialFileIfNotExist();
        userList = new MemberAccountList();
    }

    private void initialFileIfNotExist() {
        File file = new File(directory);
        if (!file.exists()) {
            file.mkdir();
        }

        String path = directory + File.separator + filename;
        file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MemberAccountList readData(){
        String path = directory + File.separator + filename;
        File file = new File(path);

        FileReader fileReader = null;
        BufferedReader buffer = null;
        try {
            fileReader = new FileReader(file);
            buffer = new BufferedReader(fileReader);

            String line = null;

            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(",");
                String type = data[0].trim();
                String name = data[1].trim();
                String username = data[2].trim();
                String password = data[3].trim();
                String imagePath = data[4].trim();
                String storeName = data[5].trim();
                String timeLogin = data[6].trim();
                String access = data[7].trim();
                int banAccessCounting = Integer.parseInt(data[8].trim());

                userList.addUser(new MemberAccount(type, name, username, password, imagePath,storeName, timeLogin, access, banAccessCounting));

            }
        } catch (FileNotFoundException e) {
            System.out.println("ไม่พบไฟล์ " + filename);
        } catch (IOException e) {
            System.out.println("Error reading " + filename);
        }
        return userList;
    }


    public void addData(MemberAccount member){
        userList.addUser(member);
        writeData(this.userList);
    }
    public void writeData(MemberAccountList users) {

        String path = directory + File.separator + filename;
        File file = new File(path);

        FileWriter fileWriter = null;
        BufferedWriter buffer = null;
        try {
            fileWriter = new FileWriter(file);
            buffer = new BufferedWriter(fileWriter);
            buffer.write(users.toCsv());
            buffer.flush();

        } catch (FileNotFoundException e) {
            System.out.println("ไม่พบไฟล์ " + filename);
        } catch (IOException e) {
            System.out.println("ไม่สามารถเขียนลงไฟล์ " + filename);
        }
        finally {
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
