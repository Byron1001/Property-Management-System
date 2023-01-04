package User.Employee;

public class Technician extends Employee {

    String name;
    String technicianID;
    String position = "User.Employee.Technician";

    public Technician (String technicianID){
        super();
        this.name = super.name;
        technicianID = "1423";
    }
    public static int Search_View_Visitor_Pass(){
        System.out.println("Hello");
        return 1;
    }
    public static int Record_Update_Visitor_Entry(){
        return 1;
    }
    public static int CheckPoint_Check_In(){
        return 1;
    }
    public static int Record_Update_Incident(){
        return 1;
    }
}
