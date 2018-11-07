package yufei.store.domain;

public class CartItem {
		private Product p;
		private int num;
		private double sum;
		public Product getP() {
			return p;
		}
		public void setP(Product p) {
			this.p = p;
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		public double getSum() {
			return p.getShop_price()*num;
		}
		@Override
		public String toString() {
			return "CartItem [p=" + p + ", num=" + num + ", sum=" + sum + "]";
		}
		
}
