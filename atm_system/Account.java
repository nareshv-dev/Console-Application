public class Account {
    int accountNumber;
    String name;
    int pin;
    double balance;
    String transactions = "";

    Account(int accountNumber,String name,int pin,double balance){
        this.accountNumber = accountNumber;
        this.name = name;
        this.pin = pin;
        this.balance = balance;
    }
}