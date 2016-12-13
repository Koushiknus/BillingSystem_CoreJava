package sg.edu.nus.iss.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.exceptions.BadValueException;
import sg.edu.nus.iss.store.Member;
import sg.edu.nus.iss.store.MemberRegister;

/**
 * 
 * @author Sanskar Deepak
 *
 */
public class MemberRegisterTest {
	
	private MemberRegister member;
	private Member m1,m3;
	
	@Before
	public void setUp() throws BadValueException{
		member = new MemberRegister();
		m1 = new Member("Srishti", "E0013502");
		m3 = new Member("Srishti", null);
	}
	
	
	@After
	public void TearDown(){
		member = null;
		m1 =null; 
		m3= null;
	}

	@Test
	public void testMemberRegister() {
		assertTrue(member!=null);
	}
	
	@Test
	public void testAddMember() throws BadValueException{
		assertTrue(member.addMember(m1.getCustomerName(),m1.getMemberID()));
		assertFalse(member.addMember("Sachin", "E0013502"));
		assertFalse(member.addMember(m3.getCustomerName(),m3.getMemberID()));	// Description should not be null in member	
	}
	
	@Test
	public void testGetMembers() throws BadValueException{
		assertNotNull(member);
		ArrayList<Member> membersList = new ArrayList<>();
		member.addMember(m1.getCustomerName(),m1.getMemberID());
		member.addMember(m3.getCustomerName(),m3.getMemberID());
		membersList = member.getMembers();
		assertTrue(membersList.size()>0);
		assertTrue(checkIfEqual(membersList.get(0),m1));
		
	}
	
	@Test
	public void testRemoveMember() throws BadValueException{
		member.addMember(m1.getCustomerName(),m1.getMemberID());
		member.addMember(m3.getCustomerName(),m3.getMemberID());
		member.removeMember(m1.getMemberID());
		
		assertNull(member.getMember(m1.getMemberID()));
	}
	
	@Test
	public void testEquality() throws BadValueException{
		Member m2 = new Member("Sanskar", "E0013502");
		assertFalse(checkIfEqual(m1, m3));
		assertTrue(checkIfEqual(m1,m2));		
	}
	
	public boolean checkIfEqual(Member o1, Member o2){
		if((o1.getMemberID()).equals(o2.getMemberID()))
			return true;
		else  return false;		
	}

}
