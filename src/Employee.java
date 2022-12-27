import java.nio.file.SimpleFileVisitor;

public class Employee {
    int ID = 0;
    static int Salary = 0;
    String Name = "Steve";
    public Employee(String _Name){
        System.out.println("This is Constructor from Employee");
        Name = _Name;
    }
    public static int GetID(){
        System.out.println("This is GetID");
        return 1;
    }
    public static int GetName(){
        System.out.println("This is GetName");
        return 1;
    }
    public static int GetPosition(){
        System.out.println("This is GetPosition");
        return 1;
    }

}
