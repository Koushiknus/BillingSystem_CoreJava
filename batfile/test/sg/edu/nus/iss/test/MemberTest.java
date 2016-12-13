package sg.edu.nus.iss.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.exceptions.BadValueException;
import sg.edu.nus.iss.store.Member;

/**
 * 
 * @author Sanskar Deepak
 *
 */
public class MemberTest {
	
	private Member m1,m4;
	
	@Before
	public void setUp() throws BadValueException{
		m1 = new Member("Srishti", "E0013502");
		m4 = new Member(null, "E0013516");
	}
	
	
	@After
	public void TearDown(){
		m1 =null; 
		m4= null;
	}
	
	@Test 
	public void testMember(){
	assertNull(m4.getCustomerName());
	assertEquals(m4.getMemberID(), "E0013516");
	assertEquals(m1.getCustomerName(), "Srishti");
		
	}
	
	@Test
	public void testGetMemberId() throws BadValueException{
		Member m2 = new Member("Sanskar", "E0013519");
		assertEquals(m2.getMemberID(), "E0013519");
		assertEquals(m1.getMemberID(), "E0013502");
	}
	
	@Test
	public void testGetLoyaltyPoints() throws BadValueException{
		Member m2 = new Member("Sanskar", "E0013519",12); 
		assertEquals(12, m2.getLoyaltyPoints());
		assertEquals(-1, m1.getLoyaltyPoints());		
	}
	
	@Test
	public void testSetLoyaltyPoints(){
		int point = m1.getLoyaltyPoints() + 22 ; 
		m1.setLoyaltyPoints(point);
		assertEquals(m1.getLoyaltyPoints(), point);
	}

}
