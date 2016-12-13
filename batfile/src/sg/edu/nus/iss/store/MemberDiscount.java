package sg.edu.nus.iss.store;

import sg.edu.nus.iss.exceptions.BadValueException;



public class MemberDiscount extends Discount {
	private String startDate;
	private String discountPeriod;
	String applicableToMember;

	public MemberDiscount(String discountCode, String description,float percentage) throws BadValueException {
		super(discountCode, description, percentage);
		startDate = "ALWAYS";
		discountPeriod = "ALWAYS";
		applicableToMember = "M";
		}

	public String getStartDate() {
		return startDate;
	}

	public String getDiscountPeriod() {
		return discountPeriod;
	}

	@Override
	public String getApplicableToMember() {
		return applicableToMember;
	}

}
