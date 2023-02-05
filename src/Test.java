import com.orsoncharts.plot.PiePlot3D;
import org.jfree.chart.*;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.Rotation;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Test extends JFrame {
    public Test() throws IOException {
        DefaultPieDataset defaultPieDataset = new DefaultPieDataset();
        defaultPieDataset.setValue("Cat A", 30);
        defaultPieDataset.setValue("Cat B", 10);
        defaultPieDataset.setValue("Cat C", 20);
        defaultPieDataset.setValue("Cat D", 5);
        JFreeChart chart = ChartFactory.createPieChart3D("Cat Summary", defaultPieDataset, true, true, false);
//        ChartUtilities.saveChartAsJPEG(new File("./heello.jpeg"), chart, 500, 300);
//        JFreeChart chart1 = ChartFactory.createPieChart("Cat Summary", defaultPieDataset, true, true, false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);

        ChartPanel chartPanel = new ChartPanel(chart);
//        ChartPanel chartPanel1 = new ChartPanel(chart1);
        setContentPane(chartPanel);
//        setContentPane(chartPanel1);
        pack();
        setVisible(true);
    }
    public Test(int x){
        setTitle("Frame title");
        XYSeries cat1 = new XYSeries("Cat1");
        cat1.add(1, 2);
        cat1.add(2.5,3);
        cat1.add(3.5, 1);

        XYSeries cat2 = new XYSeries("Cat2");
        cat2.add(1.2, 3);
        cat2.add(1.7, 4);
        cat2.add(3, 4);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(cat1);
        dataset.addSeries(cat2);

        JFreeChart chart = ChartFactory.createXYLineChart("Cat summary", "x line title", "y line title", dataset, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
        setVisible(true);
        pack();
    }
    enum Day{
        Mon(1, "Monday"), Tue(2, "Tuesday"), Wed(3, "Wednesday"), Thu(4, "Thursday"), Fri(5, "Friday"), Sat(6, "Saturday"), Sun(7, "Sunday");
        private int value;
        private String nameOfDay;
        private Day(int value, String nameOfDay) {
            this.value = value;
            this.nameOfDay = nameOfDay;
        }
    }
    public static void main(String[] args) throws IOException {

    }
}
