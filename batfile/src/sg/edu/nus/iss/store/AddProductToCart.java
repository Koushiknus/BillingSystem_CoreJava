package sg.edu.nus.iss.store;
import javax.swing.JOptionPane;

import sg.edu.nus.iss.exceptions.BadValueException;
import sg.edu.nus.iss.gui.*;


public class AddProductToCart {
	
	StoreApplication manager;
	Store store;
	Cart cart = new Cart();
	ProductRegister productregister;
			
	public AddProductToCart() {
	}
	public AddProductToCart(Store store) {
		super();
		this.store = store;
		productregister = new ProductRegister();
	}
	
	public Cart addProductsToCart(Product product,int quantity,Member member) throws BadValueException{
		//boolean addProductsStatus = false;
		/*try{
			product = productregister.getProductById(productId);
			if(product==null){
				addProductsStatus = false;
				return null;
		}}catch(NullPointerException n){
			//n.printStackTrace();
			return null;
		}
		*/
		if(product==null){
			return null;
		}
		else if(product.getQuantityAvailable()<quantity){
			JOptionPane.showMessageDialog(null, "Insuffiecient products", "Insuffiecient products", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		else if(product.getQuantityAvailable()<product.getThreshold()){
			JOptionPane.showMessageDialog(null, "Product is Running out of stock", "Product shortage ", JOptionPane.ERROR_MESSAGE);
			Cart c1 =cart.addCart(product, quantity, member);
//			if(c1!=null)
//				addProductsStatus = true;
			return c1;
		}
		else{
			Cart c1 =cart.addCart(product, quantity, member);
//			if(c1!=null)
//				addProductsStatus = true
			return c1;
		}
		
	}

}
