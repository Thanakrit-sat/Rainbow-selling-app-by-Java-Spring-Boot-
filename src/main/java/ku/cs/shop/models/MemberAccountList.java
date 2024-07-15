package ku.cs.shop.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MemberAccountList {
    private ArrayList<MemberAccount> users;

    public MemberAccountList(){
        users = new ArrayList<>();
    }

    public void addUser(MemberAccount user){
        users.add(user);
    }

    public ArrayList<MemberAccount> getAllUsers(){
        return users;
    }

    public int countUser() { return users.size();}

    public MemberAccount getUserAccountByUsername(String username) {
        for (MemberAccount user : users) {
            if (user.checkUsername(username)) {
                return user;
            }
        }
        return null;
    }

    public void sort(Comparator<MemberAccount> memberAccountComparator) {
        Collections.sort(this.users, memberAccountComparator);
    }

    // ----------------------- method ที่เช็คว่า ไม่มี username ซ้ำ (Register) -------------------
    public boolean checkUsername(MemberAccount user) {
        if (countUser() == 0) {
            return true;
        }else if (getUserAccountByUsername(user.getUsername()) == null) {
            return true;
        }else
            return false;
    }

    public boolean checkStoreNameIsEmpty(String storeName) {
        for (MemberAccount member : this.users) {
            if (member.getStoreName().equals(storeName)) {
                return false;
            }
        }
        return true;
    }

    public String getUsernameByStoreName(String storeName) {
        for (MemberAccount member : users) {
            if (member.getStoreName().equals(storeName)){
                return member.getUsername();
            }
        }
        return "";
    }

    public String toCsv() {
        String result = "";
        for (MemberAccount member : this.users) {
            result += member.toCsv() + "\n";
        }
        return result;
    }

}
