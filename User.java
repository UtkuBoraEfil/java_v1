
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;

public class User {
    String name;
    String password;
    float balance;

    public User(String a, String b, float c){
        name = a;
        password = b;
        balance = c;
    }
    public User(String a, String b){
        name = a;
        password = b;
    }
    public void NewUser(){
         try {
            FileWriter myWriter = new FileWriter("users.txt", true);
            myWriter.write(name+","+password+","+balance+ "\n");
            myWriter.close();
         }
         catch(IOException e){
            System.out.println("An error occurred while writing to users.txt");
            e.printStackTrace();
         }
    }
     @Override
    public String toString() {
        return "Username: " + name + ", Password: " + password + ", Balance: $" + balance;
    }
    
}
