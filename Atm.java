
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Atm {
    private List<User> users = new ArrayList<>();
    private final String FILE_NAME = "users.txt";

    public Atm() {
        loadUsersFromFile();
    }
     private void loadUsersFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    float balance = Float.parseFloat(parts[2]);
                    users.add(new User(username, password, balance));
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading user file: " + e.getMessage());
        }
    }
    public User login(String inputUser, String inputPass) {
        for (User user : users) {
            if (user.name.equals(inputUser) && user.password.equals(inputPass)) {
                return user;
            }
        }
        return null;
    }
    public void saveAll() {
         try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
        for (User user : users) {
            bw.write(user.name + "," + user.password + "," + user.balance); // ✅ write as CSV
            bw.newLine();
        }
        } catch (IOException e) {
            System.out.println("❌ Error saving users to file: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Atm atm = new Atm();

        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User currentUser = atm.login(username, password);
        if (currentUser == null) {
            User newUser = new User(username, password, 0.0f);
            atm.users.add(newUser);
            newUser.NewUser(); // Save new user to file
            System.out.println("New user created");
            currentUser = newUser;
            
        }

        System.out.println("✅ Welcome, " + currentUser.name + "!");

        int choice;
        do {
            System.out.println("1. Check balance\n2. Deposit\n3. Withdraw\n4. Exit");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Balance: $" + currentUser.balance);
                    break;
                case 2:
                    System.out.print("Amount to deposit: ");
                    float d = scanner.nextFloat();
                    currentUser.balance += d;
                    break;
                case 3:
                    System.out.print("Amount to withdraw: ");
                    float w = scanner.nextFloat();
                    if (w <= currentUser.balance) currentUser.balance -= w;
                    else System.out.println("❌ Not enough balance!");
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
            }
        } while (choice != 4);

        atm.saveAll(); 
        scanner.close();
}

}
