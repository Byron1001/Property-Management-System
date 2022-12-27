public class Technician extends Employee{
    public Technician(String Name){
        super(Name);
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
