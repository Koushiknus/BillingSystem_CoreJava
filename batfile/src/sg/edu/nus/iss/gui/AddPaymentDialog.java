package sg.edu.nus.iss.gui;

import java.awt.GridLayout;
import java.util.Iterator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import sg.edu.nus.iss.store.Cart;
import sg.edu.nus.iss.utils.OkCancelDialog;

/**
 * @author Koushik Radhakrishnan - AddPaymentDialog - Validates Payment -
 *
 */
public class AddPaymentDialog extends OkCancelDialog  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StoreApplication manager ;
	private java.util.ArrayList<Cart> cart;
	private   JTextArea  productDetails;
	private JTextField total;
	private JTextField discount;
	private JTextField redeemPoints;
	private JTextField amountReceived;
	
	public AddPaymentDialog(StoreApplication manager, TransactionProductPanel transactionProductPanel){
		super(manager.getMainWindow(),"Payment Details");
		this.manager = manager;
		cart = transactionProductPanel.getSelectedProducts();
		System.out.println("Add items to cart dialog array list size is"+cart.size());
		Iterator<Cart> i = cart.iterator();
		while(i.hasNext()){
			Cart c = (Cart)i.next();
			productDetails.append(c.toString() +System.getProperty("line.separator") );
		}
		productDetails.setEditable(false);

		total.setText(manager.getTransactionTotal());
		total.setEditable(false);
		discount.setText(manager.getDiscount());
		redeemPoints.setText(manager.getLoyaltyPoints());
		
	}


	@Override
	protected JPanel createFormPanel() {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(0,2));
		p.add(new JLabel("Product Details"));
		productDetails = new JTextArea ();
		p.add(productDetails);
		p.add(new JLabel("Total"));
		total = new JTextField(20);
		p.add(total);
		p.add(new JLabel("Discount"));
		discount = new JTextField(20);
		p.add(discount);
		p.add(new JLabel("Redeem points"));
		redeemPoints = new JTextField(20);
		p.add(redeemPoints);
		p.add(new JLabel("Amount Received "));
		amountReceived = new JTextField(20);
		p.add(amountReceived);
		return p;
	}


	
	@Override
	protected boolean performOkAction() {
			boolean paymentStatus = true;
		//String makePayment(double amountReceived,double transActionTotal,double discount,double redeemPoints,ArrayList<Cart> c1 )
		if(total.getText().equals("")||amountReceived.getText().equals("")){
    		JOptionPane.showMessageDialog(null, "InvalidDetails", "Enter All details", JOptionPane.ERROR_MESSAGE);
    		paymentStatus = false;
    		return paymentStatus;
    		}
		try{
			double amountreceived = Double.parseDouble(amountReceived.getText());
			double transactiontotal = Double.parseDouble(total.getText());
			double discountValue  = Double.parseDouble(discount.getText());
			double redeemPointsValue = Double.parseDouble(redeemPoints.getText());
			if (redeemPointsValue>Double.parseDouble(manager.getLoyaltyPoints())) {
				JOptionPane.showMessageDialog(null, "Points redeemed cannot be greater than Points available", "Redeem Points Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			double tempAmountcheck = transactiontotal - (transactiontotal*(discountValue/100)) + redeemPointsValue/1000;
			double balanceAmount = amountreceived-tempAmountcheck;
			String balance = "Balance to be tendered  is"+" "+Double.toString(balanceAmount);
			System.out.println("Tempamount check is"+tempAmountcheck);
			if(tempAmountcheck<=amountreceived){
				manager.makePayment(amountreceived,transactiontotal,discountValue,redeemPointsValue,cart);
				summaryDescription(transactiontotal,tempAmountcheck,amountreceived,balanceAmount,manager.getBonusPoints());
				JOptionPane.showMessageDialog(this,balance ,"Payment is Successful", 0);

				
			}
			else{
				JOptionPane.showMessageDialog(null, "Payment is not sufficient", "Payment error", JOptionPane.ERROR_MESSAGE);
	    		paymentStatus = false;
	    		return   paymentStatus;
			}
    	
		}catch(Exception n){
    		JOptionPane.showMessageDialog(null, "Invalid Inputs entered", "Invalid Inputs", JOptionPane.ERROR_MESSAGE);
    		n.printStackTrace();
    		paymentStatus = false;
    		return paymentStatus;

		}
		
		paymentStatus = true;
		return paymentStatus;
	}


	public  void summaryDescription(double transactionTotal,double amountAfterDiscount,double amountReceived,double balance,double earnedPoints) {
			PaymentSummaryDialog paymentSummary = new PaymentSummaryDialog(manager,transactionTotal,amountAfterDiscount,amountReceived,balance,earnedPoints);
			paymentSummary.pack();
			paymentSummary.setVisible(true);
	}
}
