package sg.edu.nus.iss.dao;

import sg.edu.nus.iss.exceptions.BadValueException;
import sg.edu.nus.iss.store.*;
import java.io.IOException;
import java.util.ArrayList;


public class ProductDao extends BaseDao{
	private static final String Product_File="StoreAppData/Products.dat";
	private Store store;
	
	public ProductDao(Store store){
		super();
		this.store=store;
	}
	
	public ArrayList<Product> readProductsFromFile() throws IOException{
		ArrayList<String> readString=super.readFromFile(Product_File);
		
		ArrayList<Product> productList=new ArrayList<>();
		
		for(int i=0;i<readString.size();i++){
			String line=readString.get(i);
			String[] filed = line.split(",");
			
			String productId=filed[0];
			
			String[] categoryFiled=filed[0].split("/");
			String categoryCode=categoryFiled[0];		
			//will be modified future
			Category category=store.getCategoryReg().getCategory(categoryCode); //modified sanskar
			String productName=filed[1];
			String description=filed[2];
			int quantity=Integer.parseInt(filed[3]);
			double price=Double.parseDouble(filed[4]);
			String barcodeNumber=filed[5];
			int threshold=Integer.parseInt(filed[6]);
			int orderQuantity=Integer.parseInt(filed[7]);
			
			Product productNew;
			try {
				productNew = new Product(productId, category, productName, description, quantity, price, barcodeNumber, threshold, orderQuantity);
				productList.add(productNew);
			} catch (BadValueException e) {
				e.printStackTrace();
			}
									
			
		}
		return productList;
	}
	
	public void writeProductsToFile(ArrayList<Product> products) throws IOException{
		ArrayList<StringBuffer> writeList=new ArrayList<>();
		for(Product p:products){

			String productId=p.getProductId();
			String productName=p.getName();
			String description=p.getDescription();
			String quantity=""+p.getQuantityAvailable();
			String price=""+p.getPrice();
			String barcodeNumber=p.getBarcodeNumber();
			String threshold=""+p.getThreshold();
			String orderQuantity=""+p.getOrderQuantity();
			
			StringBuffer line=new StringBuffer();
			line.append(productId+",");
			line.append(productName+",");
			line.append(description+",");
			line.append(quantity+",");
			line.append(price+",");
			line.append(barcodeNumber+",");
			line.append(threshold+",");
			line.append(orderQuantity);
			
			writeList.add(line);
		}
		super.writeToFile(writeList, Product_File);
	}
	
	//add a line to file
		public void appendProductToFile(Product p) throws IOException{
			String productId=p.getProductId();
			String productName=p.getName();
			String description=p.getDescription();
			String quantity=""+p.getQuantityAvailable();
			String price=""+p.getPrice();
			String barcodeNumber=p.getBarcodeNumber();
			String threshold=""+p.getThreshold();
			String orderQuantity=""+p.getOrderQuantity();
			
			StringBuffer line=new StringBuffer();
			line.append(productId+",");
			line.append(productName+",");
			line.append(description+",");
			line.append(quantity+",");
			line.append(price+",");
			line.append(barcodeNumber+",");
			line.append(threshold+",");
			line.append(orderQuantity);
			
			super.appendToFile(line.toString(), Product_File);
		}
}
