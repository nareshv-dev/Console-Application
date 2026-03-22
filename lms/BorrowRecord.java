import java.time.LocalDate;

public class BorrowRecord {
    String recordId, memberId, isbn;
    LocalDate borrowDate, dueDate, returnDate;
    String status;   // BORROWED, RETURNED, LOST
    int extensions;
    double fine;

    public BorrowRecord(String recordId, String memberId, String isbn) {
        this.recordId = recordId;
        this.memberId = memberId;
        this.isbn = isbn;
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusDays(15);
        this.status = "BORROWED";
        this.extensions = 0;
        this.fine = 0;
    }

    public boolean isActive() {
        return status.equals("BORROWED");
    }

    public void display() {
        System.out.printf("  %-10s %-12s %-12s %-12s %-12s %-10s  Ext:%d%n",
                recordId, isbn, borrowDate, dueDate,
                returnDate != null ? returnDate.toString() : "-",
                status, extensions);
    }
}
