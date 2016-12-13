package sg.edu.nus.iss.gui;

import java.util.ArrayList;

/**
 * @author Koushik Radhakrishnan - CartDisplay - Displays Cart items 
 *
 */
public class CartDisplay {
			private String productName ;
			private double quantity;
			private double priceName;
			private ArrayList<CartDisplay> cartDisplayList ;
	
		public CartDisplay(){
			
		}
		public CartDisplay(String productName , int quantity , double priceName){
				this.productName = productName;
				this.quantity = quantity;
				this.priceName = priceName;
		}
		public CartDisplay addCartDisplayItems(String productName , int quantity , double priceName){
			CartDisplay cartdisplay = new CartDisplay( productName ,  quantity ,  priceName);
			cartDisplayList.add(cartdisplay);
			return cartdisplay;
		}
		@Override
		public String toString() {
			return " [productName=" + productName + ", quantity="
					+ quantity + ", price=" + priceName + "]";
		}
		
}
