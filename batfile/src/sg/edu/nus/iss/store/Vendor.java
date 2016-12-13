package sg.edu.nus.iss.store;

import sg.edu.nus.iss.exceptions.BadValueException;

public class Vendor {
	private String vendorName;
	private String description;
	
	public Vendor(String vendorName,String description) throws BadValueException{
		String error = null;
		if(description == null) 
			error = "Description is null";
		else if (vendorName == null) {
			error = "Vendor Name is null";
		}
		if (error != null) 
			throw new BadValueException(error);
		this.description=description;
		this.vendorName=vendorName;
	}
	
	public void setVendorName(String vendorName) throws BadValueException{
		String error = null;
		if(vendorName == null)
			error = "Vendor Name is null";
		if (error != null)
			throw new BadValueException(error);
		this.vendorName=vendorName;
	}
	
	public String getVendorName(){
		return vendorName;
	}
	
	public void setDescription(String description){
		this.description=description;
	}
	
	public String getDescription(){
		return description;
	}
}
