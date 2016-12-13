package sg.edu.nus.iss.store;

import sg.edu.nus.iss.exceptions.BadValueException;



public class Member extends Customer{
	private String memberID;
	private int loyaltyPoints;
	
	public Member(String customerName, String memberID) throws BadValueException {
		super(customerName);
		String error = null;
        if (memberID == null)
            error = "No memberID specified";
        if (error != null)
            throw new BadValueException (error);
		this.memberID = memberID;
		loyaltyPoints = -1;
		}
	public Member(String customerName, String memberID, int loyaltyPoints) throws BadValueException {
		super(customerName);
		String error = null;
        if (memberID == null)
            error = "No memberID specified";
        if (error != null)
            throw new BadValueException (error);
		this.memberID = memberID;
		this.loyaltyPoints = loyaltyPoints;
	}
	
	
	public String getMemberID() {
		return memberID;
	}
	public int getLoyaltyPoints() {
		return loyaltyPoints;
	}


	public void setLoyaltyPoints(int loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}


	public boolean isFirstTime(){
		if(loyaltyPoints < 0)
		return true;
		return false;
	}
	

}
