package sg.edu.nus.iss.store;

import sg.edu.nus.iss.exceptions.BadValueException;




public abstract class Discount {
	public static final String DATE_FORMAT = "d-MMM-yyyy";
	private String discountCode;
	private String description;
	private float percentage;

	
	public Discount (String discountCode, String description, 
					 float percentage) throws BadValueException{
		String error = null;
		if (discountCode == null)
			error = "discount code is null";
		else if (description == null)
			error = "discount description is null";
		if (error != null)
			throw new BadValueException(error);
		this.discountCode=discountCode;
		this.description=description;
		this.percentage=percentage;

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public String getDiscountCode() {
		return discountCode;
	}
	public abstract String getStartDate();
	
	public abstract String getDiscountPeriod();
	
	public abstract String getApplicableToMember();

	
}
