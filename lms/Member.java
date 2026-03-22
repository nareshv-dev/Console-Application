import java.util.ArrayList;
import java.util.List;

public class Member {
    String id, name, email, password, role;
    boolean active;
    double deposit;
    List<String> borrowedIsbns = new ArrayList<>();

    public Member(String id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.active = true;
        this.deposit = role.equals("BORROWER") ? 1500.0 : 0.0;
    }

    public void display() {
        System.out.printf("  %-8s %-18s %-26s %-10s  Rs %.0f%n",
                id, name, email, role, deposit);
    }
}
