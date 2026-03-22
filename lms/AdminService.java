import java.time.LocalDate;
import java.util.*;

public class AdminService {

    private List<Member>       members;
    private List<Book>         books;
    private List<BorrowRecord> records;
    private int mSeq;

    public AdminService(List<Member> members, List<Book> books, List<BorrowRecord> records, int mSeq) {
        this.members = members;
        this.books   = books;
        this.records = records;
        this.mSeq    = mSeq;
    }

    // ── Book Management ────────────────────────────────────────────

    void addBook() {
        Helper.header("ADD BOOK");
        String isbn = Helper.input("ISBN");
        if (findBook(isbn) != null) { System.out.println("  ISBN already exists."); return; }
        String title  = Helper.input("Title");
        String author = Helper.input("Author");
        int    qty    = Helper.inputInt("Quantity");
        double price  = Double.parseDouble(Helper.input("Price (Rs)"));
        books.add(new Book(isbn, title, author, qty, price));
        System.out.println("  [OK] Book added.");
    }

    void listBooks() {
        Helper.header("ALL BOOKS");
        System.out.printf("  %-12s %-26s %-18s  Avail  Price%n", "ISBN", "Title", "Author");
        Helper.line();
        for (Book b : books) b.display();
    }

    void searchBook() {
        Helper.header("SEARCH BOOK");
        String key = Helper.input("ISBN or title keyword").toLowerCase();
        boolean found = false;
        for (Book b : books)
            if (b.isbn.equalsIgnoreCase(key) || b.title.toLowerCase().contains(key)) {
                b.display(); found = true;
            }
        if (!found) System.out.println("  No books found.");
    }

    // ── Member Management ──────────────────────────────────────────

    void addBorrower() {
        Helper.header("ADD BORROWER");
        String name  = Helper.input("Name");
        String email = Helper.input("Email");
        if (findMember(email) != null) { System.out.println("  Email already registered."); return; }
        String pass = Helper.input("Password");
        String id   = "M" + String.format("%03d", mSeq++);
        members.add(new Member(id, name, email, pass, "BORROWER"));
        System.out.println("  [OK] Added. ID: " + id + "  Deposit: Rs 1500");
    }

    void listBorrowers() {
        Helper.header("ALL BORROWERS");
        System.out.printf("  %-8s %-18s %-24s  Deposit%n", "ID", "Name", "Email");
        Helper.line();
        for (Member m : members)
            if (m.role.equals("BORROWER")) m.display();
    }

    // ── Reports ────────────────────────────────────────────────────

    void reports() {
        Helper.header("REPORTS");
        System.out.println("  1) Low Stock   2) Not Returned   3) Never Borrowed");
        int ch = Helper.inputInt("Choice");
        Helper.line();

        if (ch == 1) {
            int t = Helper.inputInt("Available qty <=");
            for (Book b : books) if (b.availableQty <= t) b.display();

        } else if (ch == 2) {
            System.out.printf("  %-8s %-18s %-14s %-12s%n", "RecID", "Member", "ISBN", "Due");
            Helper.line();
            for (BorrowRecord r : records) {
                if (!r.isActive()) continue;
                Member m = findMemberById(r.memberId);
                String overdue = LocalDate.now().isAfter(r.dueDate) ? " [OVERDUE]" : "";
                System.out.printf("  %-8s %-18s %-14s %-12s%s%n",
                        r.recordId, m != null ? m.name : r.memberId, r.isbn, r.dueDate, overdue);
            }

        } else if (ch == 3) {
            Set<String> borrowed = new HashSet<>();
            for (BorrowRecord r : records) borrowed.add(r.isbn);
            for (Book b : books) if (!borrowed.contains(b.isbn)) b.display();
        }
    }

    // ── Lookup Helpers ─────────────────────────────────────────────

    private Book   findBook(String isbn)     { for (Book b : books)     if (b.isbn.equalsIgnoreCase(isbn)) return b; return null; }
    private Member findMember(String email)  { for (Member m : members) if (m.email.equalsIgnoreCase(email)) return m; return null; }
    private Member findMemberById(String id) { for (Member m : members) if (m.id.equals(id)) return m; return null; }
}
