package sg.edu.nus.iss.gui;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import sg.edu.nus.iss.utils.OkCancelDialog;

/**
 * @author koushik - Displays Payment Summary Details
 *
 */
public class PaymentSummaryDialog extends OkCancelDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField transactionTotalAmount;
	private JTextField discountedAmount;
	private JTextField receivedAmount;
	private JTextField balance;
	private JTextField pointsEarned;

	public PaymentSummaryDialog(StoreApplication manager, double transactionTotal, double amountAfterDiscount,
			double amountReceived, double balanceAmount, double earnedPoints) {
		super(manager.getMainWindow(), "Summary Details");
		transactionTotalAmount.setText(Double.toString(transactionTotal));
		transactionTotalAmount.setEditable(false);
		discountedAmount.setText(Double.toString(amountAfterDiscount));
		discountedAmount.setEditable(false);
		receivedAmount.setText(Double.toString(amountReceived));
		receivedAmount.setEditable(false);
		balance.setText(Double.toString(balanceAmount));
		balance.setEditable(false);
		pointsEarned.setText(Double.toString(earnedPoints));
		pointsEarned.setEditable(false);
	}

	@Override
	protected JPanel createFormPanel() {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(0, 2));
		p.add(new JLabel("Total"));
		transactionTotalAmount = new JTextField(20);
		p.add(transactionTotalAmount);
		p.add(new JLabel("Total After Discount"));
		discountedAmount = new JTextField(20);
		p.add(discountedAmount);
		p.add(new JLabel("Received Amount"));
		receivedAmount = new JTextField(20);
		p.add(receivedAmount);
		p.add(new JLabel("balance"));
		balance = new JTextField(20);
		p.add(balance);
		p.add(new JLabel("Earned Points"));
		pointsEarned = new JTextField(20);
		p.add(pointsEarned);
		return p;
	}

	@Override
	protected boolean performOkAction() {
		JOptionPane.showMessageDialog(null, "Payment is successfull", "Confirmation",
				JOptionPane.INFORMATION_MESSAGE);
		return true;
	}

}
