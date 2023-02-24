package UIPackage.Event;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface EventMenuSelected {
    public void selected(int index) throws IOException, ClassNotFoundException;
}
