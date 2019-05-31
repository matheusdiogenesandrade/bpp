package problems.bpp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Bin extends ArrayList<Integer>{
	
	private BPP bpp;
	private Double weight;	
	
	public Bin (BPP bpp) {
		this.bpp = bpp;
		weight = 0d;
	}
	
	public Bin (BPP bpp, Integer... itens) {
		this(bpp);	
		Collections.addAll(this, itens);
	}
	
	@Override
	public boolean add (Integer item) {
		if (this.weight + bpp.itensWeight[item] <= this.bpp.capacity) {
			super.add(item);
			this.weight += bpp.itensWeight[item];
			return true;
		} 	
		return false;
	}
	
	@Override
	public boolean addAll(Collection<? extends Integer> items) {
		double itemsWeight = bpp.getWeightItems(items);
		if (this.weight + itemsWeight <= this.bpp.capacity) {
			super.addAll(items);
			this.weight += itemsWeight;
			return true;
		} 	
		return false;
	}
		
	@Override
	public boolean remove (Object item) {
		Integer _item = (Integer) item;
		if (super.remove(_item)) {		
			this.weight -= bpp.itensWeight[_item];
			return true;
		}
		return false;
	}
	
}