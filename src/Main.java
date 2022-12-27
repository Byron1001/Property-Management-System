
import javax.swing.text.View;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
public class Main {
    public static void main(String[] args) {
        SecurityGuard a = new SecurityGuard("VI00001");
        a.Search_View_Visitor_Pass("VI00001");
    }
}

