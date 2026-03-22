public class Book {
    String isbn, title, author;
    int totalQty, availableQty;
    double price;

    public Book(String isbn, String title, String author, int qty, double price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.totalQty = qty;
        this.availableQty = qty;
        this.price = price;
    }

    public void display() {
        System.out.printf("  %-12s %-28s %-18s  Avail: %d/%d  Rs %.0f%n",
                isbn, title, author, availableQty, totalQty, price);
    }
}
