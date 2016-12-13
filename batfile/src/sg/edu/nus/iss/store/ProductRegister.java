package sg.edu.nus.iss.store;

import java.io.IOException;
import sg.edu.nus.iss.dao.ProductDao;
import sg.edu.nus.iss.exceptions.BadValueException;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.table.AbstractTableModel;



public class ProductRegister {
	//list of product
	private ArrayList<Product> products;
	//file operation class,used to read productList from file and write productList to file
	private ProductDao pDao;
	private AbstractTableModel productTableModel;
	private static final String[] COLUMN_NAMES = {"Prod. ID", "Prod. Name", "Description", "Quantity Avail.", "Price", "Bar Code", "Reorder Quant.", "Order Quant."};

	//constructor
	public ProductRegister(Store store){
		pDao=new ProductDao(store);

	}

	public ProductRegister() {
		// TODO Auto-generated constructor stub
	}

	//read product list from file
	public void createListFromFile() throws IOException{
		products=pDao.readProductsFromFile();
	}

	//write product list to file
	public void writeListToFile() throws IOException{
		pDao.writeProductsToFile(products);
	}
	//add a line to file
	public void appendToFile(Product p) throws IOException{
		pDao.appendProductToFile(p);
	}

	//add a new product,in this method,first step is check if this product already exists,if it exists,just add quantity.
	//if it doesn't exist,generate a new product id,then add this product to products(product list)
	public void addProduct(Category category,String name,String description,int quantity,
			double price,String barcodeNumber,int threshold,int orderQuantity) throws IOException, BadValueException{

		//go through the list of product , see if this kind of product has already exist.
		//if the product exists,we just add the quantity to the product

		Product temp = new Product("temp", category, name, description, quantity, price, barcodeNumber, threshold, orderQuantity);
		int i=0;
		for(Product p:products){			
			if(p.equalOfProduct(temp)){
				p.addQuantity(quantity);
				//add
				writeListToFile();
				break;
			}
			i++;
		}
		if(i>=products.size()){
			String id=generateProductId(category);
			Product newProduct=new Product(id, category, name, description, quantity, price, barcodeNumber, threshold, orderQuantity);
			products.add(newProduct);
			//add
			appendToFile(newProduct);
		}

	}

	//get product list
	public ArrayList<Product> getProducts(){
		return products;
	}

	//get a product by product number
	public Product getProductsByBarcodenumber(String barcodenumber){

		for(Product p:products){
			if(p.getBarcodeNumber().equals(barcodenumber)){
				return p;
			}
		}
		return null;
	}

	//get a product by product id
	public Product getProductById(String pId){
		for(Product p:products){
			if(p.getProductId().equals(pId)){
				return p;
			}
		}
		return null;
	}

	//remove one product
	public void removeProduct(Product product) throws IOException{
		products.remove(product);
		//add
		writeListToFile();
	}

	//remove a product by id
	public void removeProduct(String pId) throws IOException{
		removeProduct(getProductById(pId));
	}

	//generate a new product id automatically
	public String generateProductId(Category category){
		/*
		 * Usually,the id is formed by the first three characters of category and the number of product under this categoty plus 1
		 * But if you remove one product,it deletes one productId,for example,there are "CLO/1" "CLO/2" "CLO/3" "CLO/4"
		 * after you remove "CLO/3",there are "CLO/1" "CLO/2" "CLO/4",and the next productId should be "CLO/5"
		 */	

		String catcode=category.getCategoryCode();
		int id = 0;
		String pId= null;
		for(Product p:products){
			if(p.getCategory().getCategoryCode().equals(category.getCategoryCode())){
				id = Integer.parseInt(p.getProductId().split("/")[1]);	
				pId=catcode+'/'+(id+1);
			}
		}
		if(pId == null){
			pId=catcode+'/'+(1);
		}
		return pId;
	}

	//updateQuantity(id,qtypurchased)
	public void updateQuantity(String productId,int qutPurchased) throws IOException{
		getProductById(productId).minusQuantity(qutPurchased);
		//add
		writeListToFile();
	}

	//checkProductsBelowThreshold
	public ArrayList<Product> checkProductsBelowThreshold(){
		ArrayList<Product> productsBelow=new ArrayList<>();
		for(Product p:products){
			if(p.checkBelowThrethold()){
				productsBelow.add(p);
			}
		}
		return productsBelow;
	}
	//delete products of one category
	public void deleteProductsOfCategory(Category category) throws IOException{
		Iterator<Product> i=products.iterator();
		while (i.hasNext()) {
			Product product = (Product) i.next();
			if((product.getCategory().getCategoryCode()==category.getCategoryCode())&&(product.getCategory().getCategoryName()==category.getCategoryName())){
				products.remove(product);
			}
		}
		//add
		writeListToFile();

	}
	
	//add quantity for products below threshold after clicking reorder button
	public void reFreshInventoryForThreshold() throws IOException{
		for(Product p:products){
			if(p.checkBelowThrethold()){
				p.addQuantity(p.getOrderQuantity());
			}
		}
		writeListToFile();
	}
	
	// returns table model for the list of products
	public AbstractTableModel getProductTableModel() {
		if (productTableModel != null) 
			return productTableModel;
		else {
			productTableModel = new AbstractTableModel() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public String getColumnName(int column) {
					return COLUMN_NAMES[column];
				}

				@Override
				public int getRowCount() {
					return products.size();
				}

				@Override
				public int getColumnCount() {
					return COLUMN_NAMES.length;
				}

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					Product product = products.get(rowIndex);
					switch (columnIndex) {
					case 0: return product.getProductId();
					case 1: return product.getName();
					case 2: return product.getDescription();
					case 3: return product.getQuantityAvailable();
					case 4: return product.getPrice();
					case 5: return product.getBarcodeNumber();
					case 6: return product.getThreshold();
					case 7: return product.getOrderQuantity();
					default: return null;
					}
				}
			};

			return productTableModel;
		}
	}

}
