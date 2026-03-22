import java.util.*;

public class LibraryMain {

    static List<Member>       members = new ArrayList<>();
    static List<Book>         books   = new ArrayList<>();
    static List<BorrowRecord> records = new ArrayList<>();
    static int mSeq = 4, rSeq = 1;

    public static void main(String[] args) {
        seedData();
        System.out.println("\n  === LIBRARY MANAGEMENT SYSTEM ===");
        System.out.println("  admin@lib.com  / admin123");
        System.out.println("  arjun@mail.com / arjun123");
        Helper.line();

        while (true) {
            Member user = login();
            if (user != null) {
                if (user.role.equals("ADMIN")) adminMenu(user);
                else                           borrowerMenu(user);
            }
            System.out.print("\n  Login again? (y/n): ");
            if (!Helper.sc.nextLine().trim().equalsIgnoreCase("y")) break;
        }
        System.out.println("  Goodbye!");
    }

    // ── Login ──────────────────────────────────────────────────────

    static Member login() {
        Helper.header("LOGIN");
        String email = Helper.input("Email");
        String pass  = Helper.input("Password");
        for (Member m : members)
            if (m.email.equalsIgnoreCase(email) && m.password.equals(pass) && m.active) {
                System.out.println("  Welcome, " + m.name + " [" + m.role + "]");
                return m;
            }
        System.out.println("  Invalid credentials.");
        return null;
    }

    // ── Admin Menu ─────────────────────────────────────────────────

    static void adminMenu(Member user) {
        AdminService admin = new AdminService(members, books, records, mSeq);
        while (true) {
            Helper.header("ADMIN MENU — " + user.name);
            System.out.println("  1) Add Book       2) List Books");
            System.out.println("  3) Search Book    4) Add Borrower");
            System.out.println("  5) List Borrowers 6) Reports");
            System.out.println("  0) Logout");
            Helper.line();
            int ch = Helper.inputInt("Choice");
            if (ch == 0) { System.out.println("  Logged out."); break; }
            switch (ch) {
                case 1 -> admin.addBook();
                case 2 -> admin.listBooks();
                case 3 -> admin.searchBook();
                case 4 -> admin.addBorrower();
                case 5 -> admin.listBorrowers();
                case 6 -> admin.reports();
                default -> System.out.println("  Invalid choice.");
            }
        }
    }

    // ── Borrower Menu ──────────────────────────────────────────────

    static void borrowerMenu(Member user) {
        BorrowerService borrower = new BorrowerService(books, records, user, rSeq);
        while (true) {
            Helper.header("BORROWER MENU — " + user.name + "  [Rs " + (int) user.deposit + "]");
            System.out.println("  1) View Books     2) Borrow Book");
            System.out.println("  3) Return Book    4) Extend Tenure");
            System.out.println("  5) My History     0) Logout");
            Helper.line();
            int ch = Helper.inputInt("Choice");
            if (ch == 0) { System.out.println("  Logged out."); break; }
            switch (ch) {
                case 1 -> admin_listBooks();
                case 2 -> borrower.borrowBook();
                case 3 -> borrower.returnBook();
                case 4 -> borrower.extendTenure();
                case 5 -> borrower.myHistory();
                default -> System.out.println("  Invalid choice.");
            }
        }
    }

    static void admin_listBooks() {
        Helper.header("AVAILABLE BOOKS");
        System.out.printf("  %-12s %-26s %-18s  Avail  Price%n", "ISBN", "Title", "Author");
        Helper.line();
        for (Book b : books) b.display();
    }

    // ── Seed Data ──────────────────────────────────────────────────

    static void seedData() {
        members.add(new Member("M001", "Admin",       "admin@lib.com",  "admin123", "ADMIN"));
        members.add(new Member("M002", "Arjun Kumar", "arjun@mail.com", "arjun123", "BORROWER"));
        members.add(new Member("M003", "Meena Devi",  "meena@mail.com", "meena123", "BORROWER"));

        books.add(new Book("978-0001", "Effective Java",   "Joshua Bloch",     5, 850));
        books.add(new Book("978-0002", "Clean Code",       "Robert C. Martin", 4, 720));
        books.add(new Book("978-0003", "Atomic Habits",    "James Clear",      6, 490));
        books.add(new Book("978-0004", "Sapiens",          "Yuval Harari",     4, 550));
        books.add(new Book("978-0005", "The Great Gatsby", "F.S. Fitzgerald",  3, 350));
    }
}
