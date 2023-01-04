package User.Employee;

public class Employee {
    int employeeID = 0;
    static int employeeSalary = 0;
    String employeeName;
    public Employee(String _Name){
        System.out.println("This is Constructor from User.Employee.User.Employee");
        employeeName = _Name;
    }
    public Employee(){
        System.out.println("This is Constructor from User.Employee.User.Employee");
    }
    public static int getEmployeeID(){
        System.out.println("This is GetID");
        return 1;
    }
    public static int getEmployeeName(){
        System.out.println("This is GetName");
        return 1;
    }
    public static int getEmployeePosition(){
        System.out.println("This is GetPosition");
        return 1;
    }

}
