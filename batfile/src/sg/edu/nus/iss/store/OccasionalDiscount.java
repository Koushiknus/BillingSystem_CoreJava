package sg.edu.nus.iss.store;

import sg.edu.nus.iss.exceptions.BadValueException;


public class OccasionalDiscount extends Discount {
		private String startDate;
		private String discountPeriod;
		private String applicableToMember;
		

	public OccasionalDiscount(String discountCode, String description, String startDate, String discountPeriod,
			float percentage) throws BadValueException {
		super(discountCode, description, percentage);
		String error = null;
		if (startDate == null) 
			error = "start Date is null";
		else if (discountPeriod == null)
			error = "Discount period is null";
		if(error != null) 
			throw new BadValueException(error);
		this.startDate = startDate;
		this.discountPeriod = discountPeriod;
		applicableToMember = "A";
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getDiscountPeriod() {
		return discountPeriod;
	}

	public void setDiscountPeriod(String discountPeriod) {
		this.discountPeriod = discountPeriod;
	}

	public String getApplicableToMember() {
		return applicableToMember;
	}

	public void setApplicableToMember(String applicableToMember) {
		this.applicableToMember = applicableToMember;
	}


}
