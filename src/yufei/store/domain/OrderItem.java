package yufei.store.domain;

public class OrderItem {
	private String itemid;
	private int quantity;
	private double total;
	private Product p;
	private Order o;
	public OrderItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderItem(String itemid, int quantity, double total, Product p, Order o) {
		super();
		this.itemid = itemid;
		this.quantity = quantity;
		this.total = total;
		this.p = p;
		this.o = o;
	}
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Product getP() {
		return p;
	}
	public void setP(Product p) {
		this.p = p;
	}
	public Order getO() {
		return o;
	}
	public void setO(Order o) {
		this.o = o;
	}
	@Override
	public String toString() {
		return "OrderItem [itemid=" + itemid + ", quantity=" + quantity + ", total=" + total + ", p=" + p + ", o=" + o
				+ "]";
	}
	
}
