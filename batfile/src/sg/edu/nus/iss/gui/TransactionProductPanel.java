package sg.edu.nus.iss.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import sg.edu.nus.iss.exceptions.BadValueException;
import sg.edu.nus.iss.store.Cart;
import sg.edu.nus.iss.store.Product;

/**
 * @author Koushik Radhakrishnan - TransactionProduct panel - Processes Product details
 *
 */
public class TransactionProductPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	StoreApplication manager;
	private JTextField memberId;
	private JTextField quantity;
	private JTextField productId;
	private java.util.ArrayList<Cart> cart;
	private java.util.List<Cart> cartSelectedItems;
	private java.awt.List cartList;
	private CartDisplay c; 
	private Border raisedetched;
	private Border loweredetched; 

	public TransactionProductPanel(StoreApplication manager) {
		this.manager = manager;
		c = new CartDisplay();
		raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED); 

		setLayout(new BorderLayout());
		add("North", createAddProductToTransactionPanel());
		add("Center", createCartPanel());
		add("East", createButtonPanel());

	}

	private JPanel createAddProductToTransactionPanel() {
		productId = new JTextField(10);
		quantity = new JTextField(10);
		memberId = new JTextField(10);
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1,0,10,0));
		p.add(new JLabel("ProductId"));
		p.add(productId);
		p.add(new JLabel("QTY"));
		p.add(quantity);
		p.add(new JLabel("Member id "));
		p.add(memberId);

		JButton b;
		ActionListener l;

		b = new JButton("Add Products");
		l = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String productIdentity = "";
				int Quantity = 0;
				String memberIdentity = "";
				try {
					productIdentity = productId.getText();
					Quantity = Integer.parseInt(quantity.getText());
					memberIdentity = memberId.getText();
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(null, "Invalid Details", "Enter valid format",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (productIdentity.length() == 0 || Quantity <= 0) {
					JOptionPane.showMessageDialog(null, "Invalid Details", "Invalid Details", JOptionPane.ERROR_MESSAGE);
					return;
				}
				System.out.println("Entering before addition"+manager.getMember(memberIdentity));
				boolean addProductStatus;
				try {
					addProductStatus = manager.addProductsToCart(manager.getProductByID(productIdentity), Quantity,
							manager.getMember(memberIdentity));

				if (!addProductStatus) {
					JOptionPane.showMessageDialog(null, "Invalid Product detail", "No Product found",
							JOptionPane.ERROR_MESSAGE);
				}
				productId.setText("");
				quantity.setText("");
				refresh();		
				} catch (BadValueException e1) {
					e1.printStackTrace();
				}
			}
		};
		b.addActionListener(l);
		p.add(b);
		p.setBorder(BorderFactory.createTitledBorder(
				loweredetched, "Add Products to Cart :")); 
		return p;
	}

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		
		
		JButton cartButton;
		cartButton = new JButton("Add CheckedItems");
		ActionListener actionListener;
		actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionevent) {
				cartSelectedItems = getSelectedProducts();
				if (cartSelectedItems.size() == 0) {
					JOptionPane.showMessageDialog(null, "Select atleast one item to Payment", "Alert",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				String addProductStatus = manager.beginCheckout(cartSelectedItems);
				makeTransaction();
				cart.removeAll(cart);
				refresh();
			}
		};
		cartButton.addActionListener(actionListener);
		panel.add(cartButton);

		cartButton = new JButton("Remove Items ");
		actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionevent) {
				manager.removeSelectedItem();

			}
		};
		cartButton.addActionListener(actionListener);
		panel.add(cartButton);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.add(panel, BorderLayout.SOUTH);
		buttonPanel.setBorder(BorderFactory.createCompoundBorder(
				raisedetched, loweredetched));
		return buttonPanel;

	}


	public JPanel createCartPanel() {	
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		cartList = new java.awt.List(6);
		cartList.setMultipleMode(true);
		panel.add("North",new JLabel("Cart Items"));
		panel.add("Center", cartList);
		panel.setBorder(BorderFactory.createCompoundBorder(
				raisedetched, loweredetched));
		return panel;
	}

	public void refresh() {
		System.out.println("Entering inside refresh method");
		cartList.removeAll();
		cart = manager.getProductsAddedInCart();
		System.out.println("Cart list size is"+cart.size());
		Iterator<Cart> i = cart.iterator();
		while (i.hasNext()) {
	Cart c = (Cart) i.next();
	int quantity = c.getQuantity();
	Product product = c.getProduct();
	String productName = product.getName();
	double price = product.getPrice();
	CartDisplay cartDisplay = new CartDisplay(productName, quantity, price);
	cartList.add(cartDisplay.toString());
		}
	}

	/**
	 * @return List of Products selected added in Cart Panel is returned
	 */
	public ArrayList<Cart> getSelectedProducts() {
		ArrayList<Cart> productList = new ArrayList<Cart>();
		int[] cartProducts = cartList.getSelectedIndexes();
		for (int i = 0; i < cartProducts.length; i++) {
			Cart c1 = cart.get(cartProducts[i]);
			productList.add(c1);
		}
		return productList;
	}


	/**
	 * @return Particular Cart item is removed and returned
	 */
	public Cart getRemoveSelectedCartItem() {
		int idx = cartList.getSelectedIndex();
		return (idx == -1) ? null : cart.get(idx);

	}

	/**
	 * Selected Products in Cart Panel is Proceeded to Payment
	 */
	public void makeTransaction() {
		if (getSelectedProducts() == null) {
			return;
		}
		AddPaymentDialog addPaymentDialog = new AddPaymentDialog(manager, this);
		addPaymentDialog.pack();
		addPaymentDialog.setVisible(true);

	}
}