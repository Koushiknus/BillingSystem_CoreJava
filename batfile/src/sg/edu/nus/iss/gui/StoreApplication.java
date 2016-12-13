package sg.edu.nus.iss.gui;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import sg.edu.nus.iss.exceptions.BadValueException;
import sg.edu.nus.iss.store.*;
import sg.edu.nus.iss.utils.ConfirmDialog;



public class StoreApplication {
	private StoreWindow storeWindow;
	private Store store;
	public StoreApplication() {
		store = new Store();
	}

	public static void main(String[] args) {
		StoreApplication storeApplication = new StoreApplication();
		storeApplication.start();

	}
	public Store getStore(){ 
		return this.store;
	}

	private void start() {
		storeWindow = new StoreWindow("Store Application", this);
		storeWindow.pack ();
		store.initializeData();
	}

	public void shutdown () {
		System.exit(0);
	}

	public boolean validate(String storeKeeperName, String password){
		return store.validate(storeKeeperName, password);
	}

	public ArrayList<Member> getMembers() {
		return store.getMembers();
	}
	public StoreWindow getMainWindow() {
		return storeWindow;
	}

	public boolean addMember(String memberName, String memberID) throws BadValueException {
		return store.addMember(memberName, memberID);
	}

	public boolean addCategory(String categoryCode, String categoryName) throws BadValueException {
		return store.addCategory(categoryCode, categoryName);
	}

	public ArrayList<Category> getCategories() {
		return store.getCategories();
	}

	public void removeMember(String memberID) {
		store.removeMember(memberID);
	}

	public void removeCategory(String categoryCode) {
		store.removeCategory(categoryCode);
	}

	public ArrayList<Discount> getDiscounts(){
		return store.getDiscounts();
	}

	public boolean addDiscount(String discountCode, String description, float percentage, String startDate, String discountPeriod) throws BadValueException {
		return store.addDiscount(discountCode, description, percentage, startDate, discountPeriod);
	}

	public void removeDiscount(String discountCode) {
		store.removeDiscount(discountCode);		
	}

	public void modifyDiscount(String discountCode, float percentage) {
		store.modifyDiscount(discountCode, percentage);
	}

	public boolean  addProductsToCart(Product product,int quantity,Member member) throws BadValueException{
		boolean addProductStatus =store.addProductsToCart( product, quantity, member);
		storeWindow.refreshCart();
		return addProductStatus;
	}

	public ArrayList<Cart> getProductsAddedInCart() {
		return store.getProductsAddedInCart();
	}

	public ArrayList<Product> getProducts(){
		return store.getProducts();
	}

	public ArrayList<Product> getProductsBelowThreshold(){
		return store.getProductsBelowThreshold();
	}

	public void removeProduct(String id){
		try {
			store.removeProduct(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getTransactionTotal() {
		return store.getTransactionTotal();
	}
	public String beginCheckout(List<Cart> cart){
		String cartStatus = store.beginCheckout(cart);
		return cartStatus;
	}
	public String getLoyaltyPoints() {
		return store.getLoyaltyPoints();
	}




	public void removeSelectedItem() {
		final Cart lineItem = storeWindow.getSelectedCartItem();
		if(lineItem==null){
			return;
		}
		String title = "Remove Product";
		String msg = "Do you really want to remove Product from Cart " + lineItem.toString() + " ?";
		ConfirmDialog confirm = new ConfirmDialog(storeWindow,title,msg){
			private static final long serialVersionUID = 1L;
			@Override
			protected boolean performOkAction() {
				store.removeCartItem(lineItem);
				storeWindow.refreshCart();
				return true;
			}

		};
		confirm.pack();
		confirm.setVisible(true);	
	}

	public void makePayment(double amountreceived, double transactiontotal,
			double discountValue, double redeemPointsValue, ArrayList<Cart> cart) {
		store.makePayment(amountreceived,transactiontotal,discountValue,redeemPointsValue,cart);

	}
	public String getDiscount() {
		return store.getDiscount();
	}

	public ArrayList<Vendor> getVendors() {
		return store.getVendors();
	}


	public boolean addVendor(String vendorName, String vendorDescription, Category category) throws BadValueException {
		return store.addVendor(vendorName, vendorDescription, category);
	}

	public ArrayList<Vendor> getVendorsPerCategory(Category category) {
		return store.getVendorsPerCategory(category);
	}

	public void removeVendor(String vendorName) {
		store.removeVendor(vendorName);
	}

	public Member getMember(String memberIdentity) {
		return store.getMember(memberIdentity);
	}

	public Product getProductByID(String productIdentity) {
		return store.getProductByID(productIdentity);
	}
	public Category getCategory(String categoryCode) {
		return store.getCategory(categoryCode);
	}
	public Vendor getVendor(String vendorName) {
		return store.getVendor(vendorName);
	}

	public Category getCategoryByName(String categoryName) {
		return store.getCategoryByName(categoryName);
	}

	public AbstractTableModel getCategoryTableModel() {
		return store.getCategoryTableModel();
	}

	public AbstractTableModel getMemberTableModel() {
		return store.getMemberTableModel();
	}

	public AbstractTableModel getProductTableModel() {
		return store.getProductTableModel();
	}

	public double getBonusPoints() {
		return store.getBonusPoints();
	}
	
	public ArrayList<Transaction> getTransactions(String fromDate, String toDate) throws ParseException {
		return store.getTransactions(fromDate, toDate);
	}
	
	public void AddQuantityForThretholdProducts(){
		try {
			store.getProductReg().reFreshInventoryForThreshold();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
