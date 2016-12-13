package sg.edu.nus.iss.store;

import sg.edu.nus.iss.exceptions.BadValueException;



public class Product {
	private String productId;
	private Category category;
	private String productName;
	private String description;
	private int quantity;
	private double price;
	private String barcodeNumber;
	private int threshold;
	private int orderQuantity;
	
	public Product(String id,Category category,String name,String description,int quantity,
			double price,String barcodeNumber,int threshold,int orderQuantity) throws BadValueException{
		String error = null;
		if(id == null) 
			error = "Product ID is null";
		else if (category == null) 
			error = "Product Category is null";
		else if (name == null)
			error = "Product name is null";
		else if (description == null) 
			error = "Description is null";
		else if (barcodeNumber == null )
			error = "Barcode is null";
		if (error != null)
			throw new BadValueException(error);
		this.productId=id;
		this.category=category;
		this.productName=name;
		this.description=description;
		this.quantity=quantity;
		this.price=price;
		this.barcodeNumber=barcodeNumber;
		this.threshold=threshold;
		this.orderQuantity=orderQuantity;
	}
	
	public Product(Category category,String name,String description,int quantity,
			double price,String barcodeNumber,int threshold,int orderQuantity) throws BadValueException{
		String error = null;
		if (category == null) 
			error = "Product Category is null";
		else if (name == null)
			error = "Product name is null";
		else if (description == null) 
			error = "Description is null";
		else if (barcodeNumber == null )
			error = "Barcode is null";
		if (error != null)
			throw new BadValueException(error);
		this.category=category;
		this.productName=name;
		this.description=description;
		this.quantity=quantity;
		this.price=price;
		this.barcodeNumber=barcodeNumber;
		this.threshold=threshold;
		this.orderQuantity=orderQuantity;
	}
	
	public String getProductId(){
		return productId;
	}
	
	public Category getCategory(){
		return category;
	}
	
	public String getName(){
		return productName;
	}
	
	public String getDescription(){
		return description;
	}
	public int getQuantityAvailable(){
		return quantity;
	}
	
	public double getPrice(){
		return price;
	}
	
	public String getBarcodeNumber(){
		return barcodeNumber;
	}
	
	public int getThreshold(){
		return threshold;
	}
	
	public int getOrderQuantity(){
		return orderQuantity;
	}
	
	public void setProductId(String id){
		this.productId=id;
	}
	
	public void setCategory(Category category){
		this.category=category;
	}
	
	public void setName(String name){
		this.productName=name;
	}
	
	public void setDescription(String description){
		this.description=description;
	}
	
	public void setQuantityAvailable(int quantity){
		this.quantity=quantity;
	}
	
	public void setPrice(double price){
		this.price=price;
	}
	
	public void setBarcodeNumber(String barcodeNumber){
		this.barcodeNumber=barcodeNumber;
	}
	
	public void setThrethold(int threthold){
		this.threshold=threthold;
	}
	
	public void setOrderQuantity(int orderQuantity){
		this.orderQuantity=orderQuantity;
	}
	
	public boolean checkBelowThrethold(){
		if(quantity<threshold){
			return true;
		}else{
			return false;
		}	
	}
	
	public void addQuantity(int addValue){
		quantity+=addValue;
	}
	
	public void minusQuantity(int purchasedQuantity){
		quantity-=purchasedQuantity;
	}
	
	/*
	 * check if two product is same . Be used in ProductReg.
	 */	
	public boolean equalOfProduct(Product another){
		//if everything is same,then it is same
		if(productName.equals(another.getName())&&category.equals(another.getCategory())&&
				description.equals(another.getDescription())&&(price==another.getPrice())&&
				barcodeNumber.equals(another.getBarcodeNumber())&&(threshold==another.getThreshold())&&
				(orderQuantity==another.getOrderQuantity())){
			return true;
		}else{
			return false;
		}
	}
	
	public String toString(){
		
		StringBuffer line=new StringBuffer();
		line.append(productId+",");
		line.append(productName+",");
		line.append(description+",");
		line.append(quantity+",");
		line.append(price+",");
		line.append(barcodeNumber+",");
		line.append(threshold+",");
		line.append(orderQuantity+",");
		return line.toString();
	}
}
