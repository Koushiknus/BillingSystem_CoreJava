package sg.edu.nus.iss.test;

import static org.junit.Assert.*;
import java.text.ParseException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.store.*;

/**
 * @author koushik - Transaction JUnit Test Class
 *
 */
public class TransactionTest {
	private Transaction transaction;
	private ArrayList<Cart> cartList,cartList2;
	private MemberRegister members;
	private ProductRegister products;
	private Category c1,c3;
	private Product p1,p3;
	private Member m1,m3;
	private Cart cart1,cart2,cart3,cart4,cart5 ; 

	@Before
	public void setUp() throws Exception {
		transaction = new Transaction();
		members = new MemberRegister();
		products = new ProductRegister();
		cartList = new ArrayList<Cart>();
		
		c1 = new Category("CLO", "CLOTHING");
		c3=new Category("STA","station");
		
		p1 = new Product("CLO/1", c1, "Centenary Jumper", "A really nice momento", 235, 21.45, "wqd", 4, 5);
		p3=new Product("ELE/1",c3, "product2", "description of product2", 10, 99.0, "98989", 3, 10);
		
		m1 = new Member("sanskar", "e0013519", -1);
		m3 = new Member("Srishti", "E0013502", 12);
		
		cart1 = new Cart(p1, m1, 4);
		cart2= 	new Cart(p3,m1,12);
		cart3 = new Cart(p3,m3,12);
		cart4 = new Cart(p1,m3,4);
		cart5 = new Cart(p1,m1,10);

		cartList.add(cart1);
		cartList.add(cart4);
		cartList.add(cart5);
		
		cartList2 = new ArrayList<Cart>();
		cartList2.add(cart2);
		cartList2.add(cart3);
		
	}
	
	@After
	public void tearDown(){
		transaction = null;
		members = null;
		products = null;
		p1 = p3= null;
		c1=c3=null;
		m1=m3=null;
		cart1=cart2=cart3=cart4=cart5 =null;
		cartList = cartList2 = null;
		
	}
	
	@Test
	public void testAddProductsToCart() {
		assertEquals(transaction.addProductsToCart(cartList, 0.0),"sucess");
		assertEquals(transaction.addProductsToCart(cartList2, 10),"sucess");
	}

	@Test
	public void testMakePayment() {
		double amountReceived = -1;
		double transactionTotal = 15;
		double discount = 10;
		double redeemPoints = 100;
		String paymentStatus = transaction.makePayment(amountReceived, transactionTotal, discount, redeemPoints,
				cartList, members, products);
		assertEquals("Payment is successful when amount received greater or equal to Transaction total ", "failed",
				paymentStatus);
		amountReceived = 12;
		paymentStatus = transaction.makePayment(amountReceived, transactionTotal, discount, redeemPoints, cartList,
				members, products);
		assertEquals("Payment is failed when amount received less than Transaction total", "failed", paymentStatus);

	}

	/*
	 * All the transactions are saved in the Transaction list when the Payment
	 * is done
	 */
	@Test
	public void testSaveTransaction() {
		ArrayList<Transaction> transactionlist = transaction.getAllTransactions();
		Transaction t1 = new Transaction(12, cart1.getProduct().getProductId(),cart1.getMember().getMemberID(),
				cart1.getQuantity(),"04-02-2016");
		Transaction t2 = new Transaction(12, cart2.getProduct().getProductId(),cart2.getMember().getMemberID(),
				cart2.getQuantity(),"04-02-2016");
		Transaction t3 = new Transaction(12, cart3.getProduct().getProductId(),cart3.getMember().getMemberID(),
				cart3.getQuantity(),"04-02-2016");
		
		transactionlist.add(t1);
		transactionlist.add(t2);
		transactionlist.add(t3);
		
		assertTrue(transactionlist.contains(t1));
		assertTrue(transactionlist.contains(t2));
		assertTrue(transactionlist.contains(t3));		
	}

	@Test
	public void testGetTransactionsWithinPeriod() throws ParseException{
		ArrayList<Transaction> transactionlist = transaction.getAllTransactions();
		Transaction t1 = new Transaction(12, cart1.getProduct().getProductId(),cart1.getMember().getMemberID(),
				cart1.getQuantity(),"04-02-2016");
		Transaction t2 = new Transaction(12, cart2.getProduct().getProductId(),cart2.getMember().getMemberID(),
				cart2.getQuantity(),"04-02-2016");
		
		transactionlist.add(t1);
		transactionlist.add(t2);
		
		assertTrue(transactionlist.contains(t1));
		assertTrue(transactionlist.contains(t2));
		 
		assertTrue(transaction.getTransactions("2016-03-30", "2016-02-20").isEmpty());
		assertNotNull(transaction.getTransactions("2016-03-30", "2016-03-31"));
	}

}
