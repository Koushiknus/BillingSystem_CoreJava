package sg.edu.nus.iss.store;
import java.io.*;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import sg.edu.nus.iss.dao.CustomerDao;
import sg.edu.nus.iss.exceptions.BadValueException;



public class MemberRegister {
	private ArrayList<Member> members;
	private Member member;
	private CustomerDao custDao;
	private AbstractTableModel memberTableModel;
	private static final String[] COLUMN_NAMES = {"Name", "ID", "Loyalty Points"};
	
	public MemberRegister() {
		members = new ArrayList<Member> ();
		custDao = new CustomerDao();
		//members.add(new Member("", "PUBLIC"));
	}
	public boolean addMember(String customerName, String memberID) throws BadValueException{
		for(Member m: members){
			if(m.getMemberID().equals(memberID)){
				return false;
			}
		}
		member = new Member(customerName, memberID);
		members.add(member);
		return true;
	}
	
	public void addMember(String customerName, String memberID, int loyaltyPoints) throws BadValueException{
		members.add(new Member(customerName, memberID, loyaltyPoints));
	}
	
	public void removeMember(String memberID) {    //Removing by selecting memberID
		for(Member m : members){
			if(memberID.compareTo(m.getMemberID())==0){
				members.remove(m);
				break;
			}
		}
	}
	
	public ArrayList<Member> getMembers() {
		return members;
	}
	
	public Member getMember(String memberID) {  
		for(Member m : members){
			if (memberID.equalsIgnoreCase(m.getMemberID())){
				return m;
			}
		}
		return null;
	}
	
	public boolean isMemberPresent(String idx) {
			for(Member m : members){
				if (idx.compareTo(m.getMemberID())==0){
					return true;
				}
			}
		return false;
	}
	
	
	public void createListFromFile() throws IOException
	{
		members = custDao.readFromFile();
	}
	
	public void writeToFile() {
		try {
			 custDao.writeToFile(members);
		}catch(IOException exception)
		{
			System.out.println("Cannot write to file");
		}
			
	}
	public void updateRedeemPoints(String memberId, double redeemPoints, double bonusPoints) {
		Member m = getMember(memberId);
		Double loyaltyPoints;
		if(m.isFirstTime()){
			m.setLoyaltyPoints(0);
			redeemPoints = 0.0;
		} 
		loyaltyPoints = m.getLoyaltyPoints()-(redeemPoints) + (bonusPoints);

		m.setLoyaltyPoints(loyaltyPoints.intValue());
		writeToFile();
	}
	
	public AbstractTableModel getMemberTableModel() {
		if (memberTableModel != null) 
			return memberTableModel;
		else {
			memberTableModel = new AbstractTableModel() {

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
					return members.size();
				}

				@Override
				public int getColumnCount() {
					return COLUMN_NAMES.length;
				}

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					Member member = members.get(rowIndex);
					switch (columnIndex) {
					case 0: return member.getCustomerName();
					case 1: return member.getMemberID();
					case 2: return member.getLoyaltyPoints();
					default: return null;
					}
				}
			};

			return memberTableModel;
		}
	}


}
