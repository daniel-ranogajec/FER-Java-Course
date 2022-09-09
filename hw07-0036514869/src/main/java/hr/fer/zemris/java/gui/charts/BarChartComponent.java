package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * Class that is extended from JComponent
 * 
 * @author Daniel_Ranogajec
 *
 */
public class BarChartComponent extends JComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final BarChart chart;
	private final int space = 100;
	
	/**
	 * Constructor method that recieves a reference to a BarChart model
	 * @param chart BarChart model
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    
	    double xScale = (double)(this.getWidth() - 2 * space) / (chart.getList().size());
	    double yScale = (double)(this.getHeight() - 2 * space) / (chart.getMaxY()-chart.getMinY());

	    FontMetrics metrics = g2d.getFontMetrics();
	    
	    g2d.drawString(chart.getxDesc(), (getWidth() - metrics.stringWidth(chart.getxDesc()))/2, getHeight() - space/2);
	    
	    AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);
	    g2d.drawString(chart.getyDesc(), -(getHeight() + metrics.stringWidth(chart.getyDesc()))/2, space/2);
	    at.rotate(Math.PI / 2);
		g2d.setTransform(at);
	    
		g2d.setColor(Color.orange);
	    for (int i = chart.getMinY(); i <= chart.getMaxY(); i = i + chart.getSpaceY()) {				//horizontal lines
	    	g2d.drawLine(space - 5 + metrics.stringWidth(Integer.toString(chart.getMaxY())),(int)(getHeight() - space - i*yScale), getWidth()-space + 5 + metrics.stringWidth(Integer.toString(chart.getMaxY())), (int)(getHeight() - space - i*yScale));
	    }
	    for (int i = 0; i <= chart.getList().size(); i++) {												//vertical lines
		    g2d.drawLine((int)(space + i*xScale + metrics.stringWidth(Integer.toString(chart.getMaxY()))), getHeight() - space + 5, (int)(space + i*xScale + metrics.stringWidth(Integer.toString(chart.getMaxY()))), space - 5);
	    }
	    
		g2d.setColor(Color.BLACK);
	    for (int i = chart.getMinY(); i <= chart.getMaxY(); i = i + chart.getSpaceY()) {				//y numbers
	    	g2d.drawString(Integer.toString(i), space + metrics.stringWidth(Integer.toString(chart.getMaxY()))
	    	- metrics.stringWidth(Integer.toString(i)) - 10, (int)(getHeight() - space - i*yScale) + 5);
	    }
	    for (int i = 0; i < chart.getList().size(); i++) {												//x numbers
		    g2d.drawString(Integer.toString(i+1), (int)((2*space + (2*i+1)*xScale)/2), getHeight() - space + 25);
	    }
	    
	    g2d.setColor(Color.RED);
	    for (XYValue value : this.chart.getList() ) 
			g2d.fillRect((int)(space + (value.getX()-1)*xScale + metrics.stringWidth(Integer.toString(chart.getMaxY()))),
					(int)(getHeight() - space - value.getY()*yScale), (int)xScale, (int)(yScale * value.getY()));
		
	    
	    g2d.setStroke(new BasicStroke(2));
	    g2d.setColor(Color.LIGHT_GRAY);
	    for (XYValue value : this.chart.getList() ) 
			g2d.drawRect((int)(space + (value.getX()-1)*xScale + metrics.stringWidth(Integer.toString(chart.getMaxY()))),
					(int)(getHeight() - space - value.getY()*yScale), (int)xScale, (int)(yScale * value.getY()));
	    
	    g2d.setColor(Color.GRAY);
	    g2d.drawLine(space - 5 + metrics.stringWidth(Integer.toString(chart.getMaxY())), getHeight() - space, getWidth() - space + 5 + metrics.stringWidth(Integer.toString(chart.getMaxY())), getHeight() - space);	//x-axis
	    g2d.drawLine(space + metrics.stringWidth(Integer.toString(chart.getMaxY())), getHeight() - space + 5, space + metrics.stringWidth(Integer.toString(chart.getMaxY())), space - 5);								//y-axis
	    
	    g2d.setStroke(new BasicStroke(5));
	    g2d.drawPolygon(new int[]{getWidth()-space + 20 + metrics.stringWidth(Integer.toString(chart.getMaxY())), 
	    		getWidth() - space + 5 + metrics.stringWidth(Integer.toString(chart.getMaxY())), 
	    		getWidth() - space + 5 + metrics.stringWidth(Integer.toString(chart.getMaxY()))}, //x-axis arrow
	    		new int[]{getHeight() - space, getHeight() - space - 3, getHeight() - space + 3}, 3);
	    g2d.drawPolygon(new int[]{space + metrics.stringWidth(Integer.toString(chart.getMaxY())), 
	    		space - 3 + metrics.stringWidth(Integer.toString(chart.getMaxY())), space + 3 + metrics.stringWidth(Integer.toString(chart.getMaxY()))}, 									
	    		new int[]{space - 20, space - 5, space - 5}, 3);								//y-axis arrow
	    
	    
	}
}
