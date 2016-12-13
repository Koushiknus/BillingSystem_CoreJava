package sg.edu.nus.iss.store;

import sg.edu.nus.iss.exceptions.BadValueException;



public class Customer {
	private String customerName;
	
	public Customer(String customerName) throws BadValueException {
		String error = null;
		if (customerName== null)
			error = "Customer name is null";
		if(error !=null)
			throw new BadValueException(error);
		this.customerName = customerName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	

}
