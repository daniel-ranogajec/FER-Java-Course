package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Class used for defining bar chart model
 * 
 * @author Daniel_Ranogajec
 *
 */
public class BarChart {

	private final List<XYValue> list;
	private final String xDesc;
	private final String yDesc;
	private final int minY;
	private final int maxY;
	private final int spaceY;
	
	/**
	 * Constructor method that recieves a list of XY objects, description for x and y axis, minimal Y value,
	 * maximal Y value and space inbetween
	 * @param list
	 * @param xDesc
	 * @param yDesc
	 * @param minY
	 * @param maxY
	 * @param spaceY
	 */
	public BarChart(List<XYValue> list, String xDesc, String yDesc, int minY, int maxY, int spaceY) {
		if (minY < 0)
			throw new IllegalArgumentException("Minimal Y must be greater than 0.");
		if (maxY <= minY)
			throw new IllegalArgumentException("Maximal Y must be greater than minimal Y.");
		if (maxY - minY < spaceY) 
			throw new IllegalArgumentException("Space must be smaller than difference between maximal Y and minimal Y.");
		for (XYValue xyValue : list) {
			if (xyValue.getY() < minY)
				throw new IllegalArgumentException("All Y values must be greater or equal than minimal Y.");
		}

		this.list = list;
		this.xDesc = xDesc;
		this.yDesc = yDesc;
		
		while((maxY - minY) % spaceY != 0)
			maxY++;
		
		this.minY = minY;
		this.maxY = maxY;
		this.spaceY = spaceY;
		
	}

	
	public List<XYValue> getList() {
		return list;
	}

	public String getxDesc() {
		return xDesc;
	}

	public String getyDesc() {
		return yDesc;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getSpaceY() {
		return spaceY;
	}
	
}
