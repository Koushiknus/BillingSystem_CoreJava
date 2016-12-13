package sg.edu.nus.iss.gui;

import javax.swing.JPanel;
import sg.edu.nus.iss.store.Cart;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = -8820162333877980019L;
	private MemberPanel memberPanel;
	private ProductPanel productPanel;
	private CategoryPanel categoryPanel;
	private DiscountPanel discountPanel;
	private TransactionProductPanel transactionProductPanel;
	private VendorPanel vendorPanel;
	private ReportPanel reportPanel;
	private static final String Category_ ="Category";
	private static final String Member_ ="Member";
	private static final String Product_ ="Product";
	private static final String Transaction_ ="Transaction";
	private static final String Vendor_ = "Vendor";
	private static final String Report_ = "Report";
	private static final String Discount_ = "Discount";
	private JPanel cards;

	/**
	 * Create the panel.
	 */
	public MainPanel(StoreApplication manager) {
		cards = new JPanel(new CardLayout());

		memberPanel =new  MemberPanel(manager);
		productPanel = new ProductPanel(manager);
		categoryPanel = new CategoryPanel(manager);
		discountPanel = new DiscountPanel(manager);
		transactionProductPanel = new TransactionProductPanel(manager);
		vendorPanel = new VendorPanel(manager);
		reportPanel = new ReportPanel(manager);

		cards.add(transactionProductPanel, Transaction_);
		cards.add(memberPanel, Member_);
		cards.add(categoryPanel, Category_);
		cards.add(productPanel, Product_);
		cards.add(vendorPanel, Vendor_);
		cards.add(reportPanel, Report_);
		cards.add(discountPanel, Discount_);
		setLayout(new BorderLayout());
		add(cards, "Center");
		add(createMenuPanel(), "West");


	}

	public JPanel createMenuPanel() {
		CardLayout layout = (CardLayout)(cards.getLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JPanel b = new JPanel(new GridLayout(0,1,0,30));

		JButton btnMakeATransaction = new JButton("Make a Transaction");
		btnMakeATransaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(cards, Transaction_);
			}
		});
		b.add(btnMakeATransaction);

		JButton btnMemberRegistration = new JButton("Member Registration");
		btnMemberRegistration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(cards, Member_);
			}
		});
		b.add(btnMemberRegistration);

		JButton btnCategory = new JButton("Category");
		btnCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layout.show(cards, Category_);
			}
		});
		b.add(btnCategory);

		JButton productBtn = new JButton("Product");
		productBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(cards, Product_);
			}
		});
		b.add(productBtn);

		JButton vendorBtn = new JButton("Vendor");
		vendorBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(cards, Vendor_);
			}
		});
		b.add(vendorBtn);

		JButton reportBtn = new JButton("Report");
		reportBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(cards, Report_);
			}
		});
		b.add(reportBtn);

		JButton btnDiscount = new JButton("Discount");
		btnDiscount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(cards, Discount_);
			}
		});
		b.add(btnDiscount);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});

		panel.add(b, BorderLayout.NORTH);
		panel.add(btnExit, BorderLayout.SOUTH);
		return panel;

	}

	public void refreshCart() {
		// TODO Auto-generated method stub
		transactionProductPanel.refresh();

	}

	public Cart getSelectedCartItem() {
		Cart selectedLineItem = transactionProductPanel.getRemoveSelectedCartItem();
		return selectedLineItem;
	}

}
