package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Model that implements ListModel with one additional method: next().
 * 
 * @author Daniel_Ranogajec
 *
 */
public class PrimListModel implements ListModel<Integer>{

	private List<Integer> elements;
	private List<ListDataListener> listeners;
	
	/**
	 * Constructor class which initializes list of elements and ElementListeners
	 */
	public PrimListModel() {
		elements = new ArrayList<>();
		elements.add(1);
		listeners = new ArrayList<>();
	}
	
	/**
	 * Method that generates next primary number
	 */
	public void next() {
		int k = elements.get(elements.size()-1);
		int m;
		while(true) {
			k++;
			for (m = k-1; m > 1; m--) {
				if (k % m == 0)
					break;
			}
			if (m==1) {
				elements.add(k);
				break;
			}
		}		
		
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, elements.size(), elements.size());		
		for(ListDataListener l : listeners) 
			l.intervalAdded(event);
	}
	
	@Override
	public int getSize() {
		return elements.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return elements.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

}
