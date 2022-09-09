package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.function.Function;

/**
 * Calculator layout that implements LayoutManager2
 * 
 * @author Daniel_Ranogajec
 *
 */
public class CalcLayout implements LayoutManager2 {
	
	private final int rows = 5;
	private final int columns = 7;
	private Component[][] components = new Component[rows][columns];
	private int space;
	
	/**
	 * Constructor method that receives space between RCPositions in argument
	 * @param space
	 */
	public CalcLayout(int space) {
		if (space < 0) 
			throw new CalcLayoutException();
		
		this.space = space;
	}
	
	/**
	 * Constructor method that sets space between RCPoisions to 0
	 */
	public CalcLayout() {
		this(0);
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (comp == null || constraints == null)
			throw new NullPointerException();
			
		RCPosition pos;
		if (constraints instanceof String) 
			pos = RCPosition.parse((String)constraints);
		else if (constraints instanceof RCPosition)
			pos = (RCPosition) constraints;
		else
			throw new IllegalArgumentException("Constraints must be instanceof RCPosition or String.");
		
		chechConstraint(pos);
		
		this.components[pos.getRow()-1][pos.getColumn()-1] = comp;
	}

	/**
	 * Private method used for checking constraints of given position
	 * @param pos RCPosition
	 * @throws CalcLayoutException if given position does not fit
	 */
	private void chechConstraint(RCPosition pos) {
		int row = pos.getRow();
		int col = pos.getColumn();
		if (row < 1 || row > this.rows || col < 1 || col > this.columns)
			throw new CalcLayoutException();
		if (row == 1 && (col > 1 && col < 6)) 
			throw new CalcLayoutException();
		if (this.components[row-1][col-1] != null)
			throw new CalcLayoutException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				if (components[i][j].equals(comp)) {
					components[i][j] = null;
				}
			}
		}
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int x = insets.left;
		int y = insets.top;
		double heightDouble = (double)(parent.getHeight() - insets.top - insets.bottom - (rows-1)*space) / rows;
		double widthDouble = (double)(parent.getWidth() - insets.left - insets.right - (columns-1)*space) / columns;
		int[] h = uniformlyDistributeHeigth(heightDouble);
		int[] w = uniformlyDistributeWidth(widthDouble);
		int width = (int) widthDouble;
		int height = (int) heightDouble;
		
		int totalH = 0;
		for (int i = 0; i < rows; i++) {
			int totalW = 0;
			for (int j = 0; j < columns; j++) {
				if (components[i][j] != null)
				if (i == 0 && j == 0 && components[i][j] != null) 
					components[i][j].setBounds(y, x, width*5 + space*4 + w[0] + w[1] + w[2] + w[3] + w[4], height + h[0]);			
				else if (components[i][j] != null) 
					components[i][j].setBounds(y + j*width + j*space + totalW, x + i*height + i*space + totalH, width + w[j], height + h[i]);
				
				totalW += w[j];
			}
			totalH += h[i];	
		}
	}

	/**
	 * Private method used for uniform distribution of width
	 * @param width
	 * @return array of 0s and 1s
	 */
	private int[] uniformlyDistributeWidth(double width) {
		int result[];
		switch((int)(width * rows - (int)width * rows)) {
		case 6 -> result = new int[] {1,1,1,0,1,1,1};
		case 5 -> result = new int[] {1,1,0,1,0,1,1};
		case 4 -> result = new int[] {1,0,1,0,1,0,1};
		case 3 -> result = new int[] {0,1,0,1,0,1,0};
		case 2 -> result = new int[] {0,0,1,0,1,0,0};
		case 1 -> result = new int[] {0,0,0,1,0,0,0};
		default -> result = new int[] {0,0,0,0,0,0,0};
		};
		return result;
	}

	/**
	 * Private method used for uniform distribution of height
	 * @param width
	 * @return array of 0s and 1s
	 */
	private int[] uniformlyDistributeHeigth(double height) {
		int result[];
		switch((int)(height * columns - (int)height * columns)) {
		case 4 -> result = new int[] {1,1,0,1,1};
		case 3 -> result = new int[] {1,0,1,0,1};
		case 2 -> result = new int[] {0,1,0,1,0};
		case 1 -> result = new int[] {0,0,1,0,0};
		default -> result = new int[] {0,0,0,0,0};
		};
		return result;
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getLayoutSize(parent, Component::getPreferredSize);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getLayoutSize(parent, Component::getMinimumSize);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getLayoutSize(target, Component::getMaximumSize);
	}
	
	/**
	 * PRivate method used for getting Dimensions for given Container and Function
	 * @param parent
	 * @param func
	 * @return
	 */
	private Dimension getLayoutSize(Container parent, Function<Component, Dimension> func) {
		int maxWidth = 0;
		int maxHeight = 0;
		Insets insets = parent.getInsets();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (components[i][j] != null) {
					Dimension d = func.apply(components[i][j]);
					if (d.height > maxHeight)
						maxHeight = d.height;
					if (d.width > maxWidth)
						maxWidth = d.width;
				}
			}
		}
		return new Dimension(maxWidth * columns + space * (columns-1) + insets.left + insets.right
				, maxHeight * rows + space * (rows-1) + insets.top + insets.bottom);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		return;
	}

}
