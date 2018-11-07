package yufei.store.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cart {
	private Map<String,CartItem> map = new HashMap<>();
	
	private double sum ;

	public Map<String, CartItem> getMap() {
		return map;
	}

	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}

	public double getSum() {
		sum = 0;
		Collection<CartItem> values = map.values();
		for (CartItem cartItem : values) {
			sum +=cartItem.getSum();
		}
		return sum;
	}
	
	public void removeAll() {
		map.clear();
	}
	
	public void removeItem(String pid) {
		map.remove(pid);
	}
	
	public Collection<CartItem> getItems() {
		return map.values();
	}
	
	
	public void addItem(CartItem c) {
		String pid = c.getP().getPid();
		if(map.containsKey(pid)) {
			CartItem old = map.get(pid);
			old.setNum(old.getNum()+c.getNum());
			map.put(pid, old);
		}else {
			map.put(pid, c);
		}
		
	}

	@Override
	public String toString() {
		return "Cart [map=" + map + ", sum=" + sum + "]";
	}

	
	
}
