import java.util.*;
import java.time.*;
import java.time.format.*;

public class Helper {

    static Scanner sc = new Scanner(System.in);

    static String input(String prompt) {
        System.out.print("  " + prompt + ": ");
        return sc.nextLine().trim();
    }

    static int inputInt(String prompt) {
        while (true) {
            try { return Integer.parseInt(input(prompt)); }
            catch (NumberFormatException e) { System.out.println("  Enter a valid number."); }
        }
    }

    static LocalDate inputDate(String prompt) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            try { return LocalDate.parse(input(prompt + " (DD/MM/YYYY)"), f); }
            catch (DateTimeParseException e) { System.out.println("  Invalid date. Use DD/MM/YYYY."); }
        }
    }

    static void line()           { System.out.println("  " + "-".repeat(55)); }
    static void header(String t) { System.out.println("\n  === " + t + " ==="); line(); }
}
