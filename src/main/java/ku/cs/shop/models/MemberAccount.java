package ku.cs.shop.models;

import java.time.LocalDateTime;
import java.util.List;

public class MemberAccount {
    private String type;
    private String name;
    private String username;
    private String password;
    private String imagePath;
    private String storeName;
    private String timeLogin;
    private String access;
    private int banAccessCounting;

    public MemberAccount(String type, String name, String username, String password, String imagePath, String storeName, String timeLogin, String access, int banAccessCounting) {
        this.type = type;
        this.name = name;
        this.username = username;
        this.password = password;
        this.imagePath = imagePath;
        this.storeName = storeName;
        this.timeLogin = timeLogin;
        this.access = access;
        this.banAccessCounting = banAccessCounting;
    }

    public MemberAccount(String type, String name, String username, String password, String imagePath) {
        this.type = type;
        this.name = name;
        this.username = username;
        this.password = password;
        this.imagePath = imagePath;
        this.storeName = "none";
        this.timeLogin = "This user hasn't logged in yet.";
        this.access = "allow";
        this.banAccessCounting = 0;
    }

    public MemberAccount(String type, String name, String username, String password) {
        this(name, username, password);
        this.type = type;
    }

    public MemberAccount(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.type = "member";
        this.imagePath = getClass().getResource("images/profileDefault.png").toExternalForm();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setStoreName(String storeName) { this.storeName = storeName; }

    public void setTimeLogin(String timeLogin) {
        this.timeLogin = timeLogin;
    }

    public void setAccess(String access) { this.access = access; }

    public void setBanAccessCounting(int banAccessCounting) { this.banAccessCounting = banAccessCounting; }

    public String getTimeLogin() {
        return timeLogin;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getStoreName(){return storeName;}

    public String getAccess() { return access; }

    public int getBanAccessCounting() { return banAccessCounting; }

    public boolean checkUsername(String username) {
        if (this.username.equals(username.trim()))
            return true;
        return false;
    }

    public boolean checkPassword(String password) {
        if (this.password.equals(password))
            return true;
        return false;
    }

    public String toCsv() {
        return type + "," + name + "," + username + "," + password + "," + imagePath + "," + storeName + "," + timeLogin + "," + access + "," + banAccessCounting;
    }

    public String toRecord() {
        return type + "," + name + "," + username + "," + LocalDateTime.now().withNano(0);
    }

    @Override
    public String toString() {
        return  username;
    }
}
