package sg.edu.nus.iss.store;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Koushik Radhakrishnan - Transaction - Handles Transaction and saves each
 *         Transaction in file
 *
 */
public class Transaction implements Comparable {
	int tranasctionId = 1;
	String productId;
	String memberId;
	int qtyPurchased;
	String dateOfPurchase;
	double transActionTotal;
	double discountPercentage;
	int loyaltyPoints;
	double bonusPoints;
	ArrayList<Transaction> transAction;
	Member currentMember;
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	public Transaction() {
		transAction = new ArrayList<Transaction>();
		try {
			readFromFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Transaction(int tranasctionId, String productId, String memberId, int qtypurchased, String dateOfPurchase) {
		super();
		this.tranasctionId = tranasctionId;
		this.productId = productId;
		this.memberId = memberId;
		qtyPurchased = qtypurchased;
		this.dateOfPurchase = dateOfPurchase;
	}

	public int getTranasctionId() {
		return tranasctionId;
	}

	public void setTranasctionId(int tranasctionId) {
		this.tranasctionId = tranasctionId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getqtyPurchased() {
		return qtyPurchased;
	}

	public void setQtypurchased(int qtyPurchased) {
		this.qtyPurchased = qtyPurchased;
	}

	public String getDateOfPurchase() {
		return dateOfPurchase;
	}

	public void setDateOfPurchase(String dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}

	public double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public double calculateTransactionTotal() {
		return transActionTotal;
	}

	public void setTransActionTotal(double transActionTotal) {
		this.transActionTotal = transActionTotal;
	}

	public double getBalance(double transActionTotal, double amountReceived) {
		return amountReceived - transActionTotal;
	}
	public double getBonusPoints() {
		return bonusPoints;
	}

	public void setBonusPoints(double bonusPoints) {
		this.bonusPoints = bonusPoints;
	}

	public ArrayList<Transaction> getAllTransactions() {
		/* connect to text file */
		// convert each row to transaction and add it to list
		System.out.println("TransactionArrayList size is" + transAction.size());
		return transAction;
	}
	public int getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(int loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	/**
	 * @param List of cart objects
	 * @param discountPerc
	 * @return ProductAddition status
	 */
	public String addProductsToCart(List<Cart> c1, double discountPerc) {
		String addStatus = "failed";
		transActionTotal = 0;
		ArrayList<Cart> cart = new ArrayList<Cart>();
		cart.addAll(c1);
		Iterator productIterator = cart.iterator();
		while (productIterator.hasNext()) {
			Cart lineItem = (Cart) productIterator.next();
			 currentMember = lineItem.getMember();
			loyaltyPoints = (currentMember==null)?   0 : currentMember.getLoyaltyPoints();
			int productQuantityOrdered = lineItem.getQuantity();
			Product product = lineItem.getProduct();
			System.out.println("Product available is" + product.getQuantityAvailable());
			System.out.println("Product threshold is " + product.getThreshold());
			transActionTotal += product.getPrice() * productQuantityOrdered;
			System.out.println("Transaction total inside addProductsToCart method is" + transActionTotal);
			addStatus = "sucess";
		}
		discountPercentage = discountPerc;
		return addStatus;
	}

	/**
	 * @param amountReceived
	 * @param transActionTotal
	 * @param discount
	 * @param redeemPoints
	 * @param c1
	 * @param members
	 * @param products
	 * @return paymentStatus
	 */
	public String makePayment(double amountReceived, double transActionTotal, double discount, double redeemPoints,
			ArrayList<Cart> c1, MemberRegister members, ProductRegister products) {
		String paymentStatus = "success";
		double temptransActionTotal = transActionTotal - (transActionTotal * discount / 100) + redeemPoints / 1000;
		System.out.println(" Temporary Transaction total is" + temptransActionTotal);
		if (amountReceived > temptransActionTotal) {
			bonusPoints  = (currentMember==null)? 0 : transActionTotal / 10;
			transActionTotal = temptransActionTotal;
			System.out.println("Transaction id is" + tranasctionId);
			System.out.println("Cartobject is" + c1.toString());
			System.out.println("Redeem points is" + redeemPoints);
			System.out.println("Discount is " + discount);
			saveTransaction(tranasctionId, transActionTotal, c1, redeemPoints, bonusPoints, members, products);
		} else {
			paymentStatus = "failed";
			System.out.println("Payment status is " + paymentStatus);
			return paymentStatus;
		}
		return paymentStatus;
	}

	

	/**
	 * @param tranasctionId
	 * @param transActionTotal
	 * @param c1
	 * @param redeemPoints
	 * @param bonusPoints
	 * @param members
	 * @param products
	 * @return Transaction save status
	 */
	public String saveTransaction(int tranasctionId, double transActionTotal, ArrayList<Cart> c1, double redeemPoints,
			double bonusPoints, MemberRegister members, ProductRegister products) {
		String saveTransactionStatus = "";
		ArrayList<Cart> cart = new ArrayList<Cart>();
		// DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String currentDateString = formatter.format(date);
		System.out.println("Current date string is" + currentDateString);
		cart.addAll(c1);
		Iterator<Cart> productIterator = cart.iterator();
		while (productIterator.hasNext()) {
			Cart c2 = (Cart) productIterator.next();
			System.out.println("Cart object inside iterator is" + c2.toString());
			if (c2.getMember() == null)
				memberId = "PUB";
			else {
				memberId = c2.getMember().getMemberID();
			}
			qtyPurchased = c2.getQuantity();
			Product p = c2.getProduct();
			productId = p.getProductId();
			transAction.add(new Transaction(tranasctionId, productId, memberId, qtyPurchased, currentDateString));
			System.out.println("Transaction arraylist is" + transAction.toString());
			try {
				products.updateQuantity(productId, qtyPurchased);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!memberId.equals("PUB"))
		members.updateRedeemPoints(memberId, redeemPoints, bonusPoints);
		// writeToFile();
		System.out.println("Transaction initialisations");
		setTransActionTotal(0);
		setTranasctionId(this.tranasctionId + 1);
		System.out.println("Transaction total is" + transActionTotal);

		return "";
	}

	/**
	 * @param fromDate
	 * @param toDate
	 * @return List of Transactions within the date limit specified
	 * @throws ParseException
	 */
	public ArrayList<Transaction> getTransactions(String fromDate, String toDate) throws ParseException {
		ArrayList<Transaction> transactionPeriod = new ArrayList<Transaction>();
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDateTransaction = formatter.parse(fromDate);
		Date toDateTransaction = formatter.parse(toDate);
		if(!toDateTransaction.before(fromDateTransaction)){
		for (Transaction transaction : transAction) {
			Date dateofPurchase = formatter.parse(transaction.getDateOfPurchase());
				if ((dateofPurchase.after(fromDateTransaction)&&dateofPurchase.before(toDateTransaction))||dateofPurchase.equals(toDateTransaction)||dateofPurchase.equals(fromDateTransaction))
				transactionPeriod.add(transaction);
		}
		}
		Collections.sort(transactionPeriod, Transaction.transProductId);
		return transactionPeriod;
	}

	public static Comparator<Transaction> transProductId = new Comparator<Transaction>() {

		@Override
		public int compare(Transaction transaction1, Transaction transaction2) {
			String productId1 = transaction1.getProductId();
			String productId2 = transaction2.getProductId();
			return productId1.compareTo(productId2);
		}

	};

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	/**
	 * Write to File (ArrayList of contents to file)
	 */
	public void writeToFile() {
		try {
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter("StoreAppData/Transaction.dat"));
			for (Transaction t : transAction) {
				fileWriter.write(t.getTranasctionId() + ",");
				fileWriter.write(t.getProductId() + ",");
				fileWriter.write(t.getMemberId() + ",");
				fileWriter.write(t.getqtyPurchased() + ",");
				fileWriter.write(t.getDateOfPurchase() + "\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read List of Transaction from file and Save it in ArrayList
	 * 
	 * @throws IOException
	 */
	public void readFromFile() throws IOException {
		ArrayList<String> contentsFromFile = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader("StoreAppData/Transaction.dat"));
		String individualLine;
		while ((individualLine = br.readLine()) != null) {
			contentsFromFile.add(individualLine);
		}
		String line[] = contentsFromFile.toArray(new String[contentsFromFile.size()]);
		int idChecker = line.length - 1;
		for (int i = 0; i < line.length; i++) {
			String singleTransactionLine = contentsFromFile.get(i);
			String[] transactionFields = singleTransactionLine.split(",");
			int transactionIde = Integer.parseInt(transactionFields[0]);
			String productId = transactionFields[1];
			String memberId = transactionFields[2];
			int qty = Integer.parseInt(transactionFields[3]);
			String date = transactionFields[4];
			transAction.add(new Transaction(transactionIde, productId, memberId, qty, date));
			if (i == idChecker) {
				this.setTranasctionId(transactionIde + 1);
			}
		}
		br.close();
	}
}
