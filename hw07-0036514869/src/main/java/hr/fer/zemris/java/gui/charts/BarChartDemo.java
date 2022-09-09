package hr.fer.zemris.java.gui.charts;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class BarChartDemo extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BarChart model;
		
		if (args.length != 1) {
			model = new BarChart(
					Arrays.asList(
							new XYValue(1,8), new XYValue(2,20), new XYValue(3,22),
							new XYValue(4,10), new XYValue(5,4)
							),
					"Number of people in the car",
					"Frequency",
					0,22,2);
		} else {
			if (args[0] == null) {
				System.out.println("Can't recieve null as an argument.");
				return;
			}
			File file = new File(args[0]);
			if (!file.isFile()) {
				System.out.println("Not a text file.");
				return;
			}
			List<String> lines;
			try {
				lines = Files.readAllLines(file.toPath());
			} catch (IOException e) {
				System.out.println("Couldn't read the file.");
				return;
			}
			try {
				List<XYValue> xyList = new ArrayList<>();
				String[] xy = lines.get(2).split(" ");
				for (String string : xy) {
					String[] value = string.split(",");
					xyList.add(new XYValue(Integer.parseInt(value[0]), Integer.parseInt(value[1])));
				}
				model = new BarChart(xyList, lines.get(0), lines.get(1), 
						Integer.parseInt(lines.get(3)), Integer.parseInt(lines.get(4)), Integer.parseInt(lines.get(5)));
			} catch (NumberFormatException | IndexOutOfBoundsException  ex) {
				System.out.println("Content of file is not parsable.");
				return;
			}
		}
		
		JComponent component = new BarChartComponent(model);
		f.add(component);
		f.setSize(800,800);
		f.setVisible(true);
	}

}
