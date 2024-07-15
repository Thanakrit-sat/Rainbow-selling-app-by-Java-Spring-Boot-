package ku.cs.shop.services.datasource;

import ku.cs.shop.models.Product;
import ku.cs.shop.models.ProductList;

import java.io.*;

public class ProductFileDataSource implements DataSource<ProductList>{
    private String directoryName;
    private String filename;

    public ProductFileDataSource(){ this("csv-database", "products.csv");}

    public ProductFileDataSource(String directoryName, String filename){
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
    public ProductList readData() {
        ProductList list = new ProductList();
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
                list.addProduct(
                        new Product(
                                data[0].trim(),
                                Double.parseDouble(data[1].trim()),
                                Integer.parseInt(data[2].trim()),
                                data[3].trim(),
                                data[4].trim(),
                                data[5].trim(),
                                Integer.parseInt(data[6].trim()),
                                data[7].trim()
                        )
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
        return list;
    }

    @Override
    public void writeData(ProductList productList) {
        String path = directoryName + File.separator + filename;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file);
            buffer = new BufferedWriter(writer);

            buffer.write(productList.toCSV());

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
}
