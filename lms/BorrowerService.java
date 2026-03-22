import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class BorrowerService {

    private List<Book>         books;
    private List<BorrowRecord> records;
    private Member             user;
    private int                rSeq;

    public BorrowerService(List<Book> books, List<BorrowRecord> records, Member user, int rSeq) {
        this.books   = books;
        this.records = records;
        this.user    = user;
        this.rSeq    = rSeq;
    }

    void borrowBook() {
        Helper.header("BORROW BOOK");
        if (user.deposit < 500)          { System.out.println("  Need min Rs 500 deposit."); return; }
        if (activeRecords().size() >= 3)  { System.out.println("  Max 3 books at a time."); return; }

        String isbn = Helper.input("ISBN");
        Book b = findBook(isbn);
        if (b == null)                       { System.out.println("  Book not found."); return; }
        if (b.availableQty == 0)             { System.out.println("  No copies available."); return; }
        if (user.borrowedIsbns.contains(isbn)) { System.out.println("  Already borrowed."); return; }

        String rid = "R" + String.format("%03d", rSeq++);
        records.add(new BorrowRecord(rid, user.id, isbn));
        b.availableQty--;
        user.borrowedIsbns.add(isbn);
        System.out.println("  [OK] Borrowed: " + b.title + "  Due: " + LocalDate.now().plusDays(15));
    }

    void returnBook() {
        Helper.header("RETURN BOOK");
        List<BorrowRecord> active = activeRecords();
        if (active.isEmpty()) { System.out.println("  No active borrowings."); return; }
        for (BorrowRecord r : active) r.display();

        BorrowRecord rec = getRecord();
        if (rec == null) return;

        LocalDate returnDate = Helper.inputDate("Return Date");
        Book b = findBook(rec.isbn);

        if (returnDate.isAfter(rec.dueDate)) {
            long days = ChronoUnit.DAYS.between(rec.dueDate, returnDate);
            double fine = Math.min(days * 2.0, b != null ? 0.8 * b.price : 999);
            System.out.printf("  Overdue %d day(s) — Fine: Rs %.2f%n", days, fine);
            System.out.println("  1) Pay Cash   2) Deduct from Deposit");
            if (Helper.inputInt("Choice") == 2) {
                user.deposit = Math.max(0, user.deposit - fine);
                System.out.printf("  Deducted. Balance: Rs %.2f%n", user.deposit);
            } else {
                System.out.printf("  Pay Rs %.2f at counter.%n", fine);
            }
            rec.fine = fine;
        }

        rec.returnDate = returnDate;
        rec.status     = "RETURNED";
        if (b != null) b.availableQty++;
        user.borrowedIsbns.remove(rec.isbn);
        System.out.println("  [OK] Returned: " + (b != null ? b.title : rec.isbn));
    }

    void extendTenure() {
        Helper.header("EXTEND TENURE");
        List<BorrowRecord> active = activeRecords();
        if (active.isEmpty()) { System.out.println("  No active borrowings."); return; }
        for (BorrowRecord r : active) r.display();

        BorrowRecord rec = getRecord();
        if (rec == null) return;
        if (rec.extensions >= 2) { System.out.println("  Max 2 extensions reached."); return; }

        rec.dueDate = rec.dueDate.plusDays(15);
        rec.extensions++;
        System.out.println("  [OK] New due date: " + rec.dueDate + "  (" + rec.extensions + "/2)");
    }

    void myHistory() {
        Helper.header("MY BORROW HISTORY");
        System.out.printf("  %-8s %-14s %-12s %-12s %-10s%n", "RecID", "ISBN", "Due", "Returned", "Status");
        Helper.line();
        boolean any = false;
        for (BorrowRecord r : records) {
            if (!r.memberId.equals(user.id)) continue;
            r.display(); any = true;
        }
        if (!any) System.out.println("  No history yet.");
        System.out.printf("%n  Deposit Balance: Rs %.2f%n", user.deposit);
    }

    // ── Helpers ────────────────────────────────────────────────────

    private List<BorrowRecord> activeRecords() {
        List<BorrowRecord> res = new ArrayList<>();
        for (BorrowRecord r : records)
            if (r.memberId.equals(user.id) && r.isActive()) res.add(r);
        return res;
    }

    private BorrowRecord getRecord() {
        String rid = Helper.input("Record ID");
        for (BorrowRecord r : records)
            if (r.recordId.equals(rid) && r.memberId.equals(user.id) && r.isActive()) return r;
        System.out.println("  Invalid record ID.");
        return null;
    }

    private Book findBook(String isbn) {
        for (Book b : books) if (b.isbn.equalsIgnoreCase(isbn)) return b; return null;
    }
}
