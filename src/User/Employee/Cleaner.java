package User.Employee;

public class Cleaner extends Employee {
    public Cleaner(String Name){
        super(Name);
    }
    public static int Search_View_Visitor_Pass(){
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
