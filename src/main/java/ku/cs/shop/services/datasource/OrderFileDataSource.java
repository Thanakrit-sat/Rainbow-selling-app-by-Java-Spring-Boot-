package ku.cs.shop.services.datasource;

import ku.cs.shop.models.Order;
import ku.cs.shop.models.OrderList;

import java.io.*;

public class OrderFileDataSource implements DataSource<OrderList> {
    private String directoryName;
    private String filename;
    private OrderList orders;


    public OrderFileDataSource() {this("csv-database", "order-list.csv");}

    public OrderFileDataSource(String directoryName, String filename){
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
    public OrderList readData() {
        orders = new OrderList();
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
                String buyer = data[0].trim();
                String orderProductName = data[1].trim();
                String orderProductImage =  data[2].trim();
                String orderTimeSell =  data[3].trim();
                int orderAmount = Integer.parseInt(data[4].trim());
                double orderTotalPrice = Double.parseDouble(data[5].trim());
                String statusOrder = data[6].trim();
                String trackNum = data[7].trim();
                String storeName = data[8].trim();

                orders.addOrder(new Order(
                        buyer, orderProductName,
                        orderProductImage, orderTimeSell,
                        orderAmount, orderTotalPrice,
                        statusOrder, trackNum,
                        storeName)
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

        return orders;
    }

    @Override
    public void writeData(OrderList orders) {
        String path = directoryName + File.separator + filename;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file);
            buffer = new BufferedWriter(writer);

            buffer.write(orders.toCSV());

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

    public void addData(Order order) {
        this.orders.addOrder(order);
        writeData(this.orders);
    }

}
