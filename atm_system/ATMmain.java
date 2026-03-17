import java.util.Scanner;
public class ATMmain {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);

        int atmBalance = 20000;
        int totalTransactions = 0;

        Account a1 = new Account(1001,"Ram",1509,5000);
        Account a2 = new Account(1002,"Babu",4323,3000);
        
        System.out.println("1 Admin");
        System.out.println("2 Account User");

        int role = sc.nextInt();
        if(role == 1){
            System.out.println("Enter Admin Password:");
            int password = sc.nextInt();

            if(password != 9999)
            {
                System.out.println("Invalid Admin Password");
                return;
            }

            System.out.println("1 Add Money to ATM");
            System.out.println("2 View ATM Balance");
            System.out.println("3 View Total Transactions");

            int adminChoice = sc.nextInt();

            if(adminChoice == 1)
            {
                System.out.println("Enter amount to add:");
                int amount = sc.nextInt();

                atmBalance += amount;

                System.out.println("Money added to ATM");
                System.out.println("ATM Balance: " + atmBalance);
            }

            else if(adminChoice == 2)
            {
                System.out.println("ATM Balance: " + atmBalance);
            }

            else if(adminChoice == 3)
            {
                System.out.println("Total Transactions: " + totalTransactions);
            }

            return;
}
        else if(role == 2 ){
        System.out.println("Enter Your Account Number");
        int acc = sc.nextInt();

        System.out.println("Enter the PIN number");
        int pin = sc.nextInt();

        Account currentuser = null;

        if(acc == a1.accountNumber && pin == a1.pin){
            currentuser = a1;
        }

        else if(acc == a2.accountNumber && pin == a2.pin){
            currentuser = a2;
        }

        else{
            System.out.println("Invalid Account or PIN Number");
            return;
        }


        System.out.println("Login Successful");
        System.out.println("Welcome " + currentuser.name);
        while(true){
        System.out.println("1 Deposit");
        System.out.println("2 Withdraw");
        System.out.println("3 Balance Check");
        System.out.println("4 Change PIN");
        System.out.println("5 Mini Statement");
        System.out.println("6 Money Transfer");
        System.out.println("7 Exit");

        int choice = sc.nextInt();

        if(choice == 1){
            System.out.println("Enter the Amount to be deposited");
            int amount = sc.nextInt();

            if(amount % 100 != 0 )
            {
                System.out.println("Only 100, 200, 500 notes allowed");
            }

            else{
                totalTransactions++;
                currentuser.balance = currentuser.balance + amount;
                currentuser.transactions += "Deposited: " + amount + "\n";
                System.out.println("Deposited Succesfully");
                System.out.println("New Balance: " + currentuser.balance);
            }
        }
        
        else if(choice == 2){
            System.out.println("Enter the Amount to be Withdraw");
            int amount = sc.nextInt();

            if(amount % 100 != 0){
                System.out.println("Enter the amount in multiples of 100");
            }

            else if(amount > currentuser.balance){
                System.out.println("Insufficient Balance");
            }

            else{
                totalTransactions++;
                currentuser.balance = currentuser.balance - amount;
                currentuser.transactions += "Withdrawn: " + amount + "\n";
                int note500 = amount / 500;
                amount = amount % 500;

                int note200 = amount/200;
                amount = amount % 200;

                int note100 = amount / 100;

                System.out.println("Dispensing Cash:");
                System.out.println("500 x " + note500);
                System.out.println("200 x " + note200);
                System.out.println("100 x " + note100);

                System.out.println("Available balance: "+ currentuser.balance);
            }
        }

        else if(choice == 3)
        {
            System.out.println("Your Balance: " + currentuser.balance);
        }

        else if(choice == 4){
            System.out.println("Enter Your Current PIN: ");
            int currentpin = sc.nextInt();

            if(currentpin == currentuser.pin){
                System.out.println("Enter the New PIN: ");
                int newpin = sc.nextInt();
                currentuser.pin = newpin;
                System.out.println("PIN Updated Successfully");
                currentuser.transactions += "PIN Changed\n";
            }
            else{
                System.out.println("Invalid PIN");
            }
        }

        else if(choice == 5){
            System.out.println("Mini Statement");
            System.out.println(currentuser.transactions);
        }

        else if (choice == 6){
            System.out.println("Enter the Receiver Account Number: ");
            int receiveracc = sc.nextInt();

            System.out.println("Enter the amount to be Transferred: ");
            int amount = sc.nextInt();

            if(amount > currentuser.balance){
                System.out.println("Insufficient Balance");
            }

            else{

                Account receiver = null;

                if(receiveracc == a1.accountNumber){
                    receiver = a1;
                }

                else if(receiveracc == a2.accountNumber){
                    receiver = a2;
                }

                if(receiver == null){
                    System.out.println("Receiver Account Not Found");
                }

                else{
                    totalTransactions++;
                    currentuser.balance = currentuser.balance - amount;
                    receiver.balance = receiver.balance - amount;

                    currentuser.transactions += "Transferred: " + amount + " to " + receiver.name + "\n";
                    receiver.transactions += "Received: " + amount + " from " + currentuser.name + "\n";

                    System.out.println("Transfer successful");
                    System.out.println("Your new balance: " + currentuser.balance);
                }

                }
            }

        else if(choice == 7)
        {
            System.out.println("Thank you for using ATM");
            return;
        }
    }
    }
    }
}