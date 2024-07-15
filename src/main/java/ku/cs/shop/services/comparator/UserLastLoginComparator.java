package ku.cs.shop.services.comparator;

import ku.cs.shop.models.MemberAccount;

import java.util.Comparator;

public class UserLastLoginComparator implements Comparator<MemberAccount> {
    @Override
    public int compare(MemberAccount o1, MemberAccount o2) {
        if (o2.getTimeLogin().equals("This user hasn't logged in yet.") || o1.getTimeLogin().equals("This user hasn't logged in yet.")) {
            return (o1.getTimeLogin().compareTo(o2.getTimeLogin()));
        }
        return (o1.getTimeLogin().compareTo(o2.getTimeLogin())) * -1;
    }
}
