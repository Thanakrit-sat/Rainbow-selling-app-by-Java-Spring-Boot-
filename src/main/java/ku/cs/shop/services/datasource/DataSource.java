package ku.cs.shop.services.datasource;

public interface DataSource<T> {
    T readData();
    void writeData(T t);
}
