package sg.edu.nus.iss.gui;

import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import sg.edu.nus.iss.utils.ConfirmDialog;
import sg.edu.nus.iss.store.Product;
import sg.edu.nus.iss.exceptions.BadValueException;
import sg.edu.nus.iss.store.Category;


public class ProductPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private StoreApplication manager;
	private JScrollPane scroller;
	private JTable table;
	private AbstractTableModel productTableModel;
	private ArrayList<Product> product_list ;
	private String action_source;
	private Border raisedetched;
	private Border loweredetched; 
	private JComboBox<String> categoryField;
	private JTextField productNameField;
	private JTextField productDescriptionField;
	private JTextField productQuantityfield;
	private JTextField productPricefield;
	private JTextField productBarcodeNumberfield;
	private JTextField productThresholdfield;
	private JTextField productOrderQuantityfield;
	private static final String Check_Below_Threshold = "Below Threshold";
	private static final String Product_List = "Product_List";
	private JPanel cards;


	public ProductPanel(StoreApplication manager) {
		this.manager = manager;
		productTableModel = manager.getProductTableModel();		
		raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED); 
		
		cards = new JPanel(new CardLayout());
		cards.add(showProductListPanel(), Product_List);
		cards.add(new CheckProductsBelowThrethold(manager),Check_Below_Threshold );

		setLayout (new BorderLayout());
		product_list = manager.getProducts();
		add(createButtonPanel(), BorderLayout.EAST);
		add(cards, BorderLayout.CENTER);
		add(createAddProductPanel(), BorderLayout.SOUTH);

	}

	//*****************To create add product Panel**********************

	private JPanel createAddProductPanel () { 

		JPanel panel = new JPanel(new BorderLayout());

		JPanel p = new JPanel(new GridLayout(0,4,10,5));

		JLabel label = new JLabel("Add Product : ");
		label.setFont(new Font("Tahoma", Font.BOLD, 12 ));
		panel.add(label, BorderLayout.WEST);

		p.add(new JLabel("Category"));
		categoryField=new JComboBox<String>();
		for(Category c:manager.getCategories()){
			categoryField.addItem(c.getCategoryName());
		}
		p.add(categoryField);

		p.add(new JLabel("Product Name"));
		productNameField=new JTextField(20);
		p.add(productNameField);

		p.add(new JLabel("Description"));
		productDescriptionField=new JTextField(20);
		p.add(productDescriptionField);

		p.add(new JLabel("Quantity"));
		productQuantityfield=new JTextField(20);
		p.add(productQuantityfield);

		p.add(new JLabel("Price"));
		productPricefield=new JTextField(20);
		p.add(productPricefield);

		p.add(new JLabel("Barcode number"));
		productBarcodeNumberfield=new JTextField(20);
		p.add(productBarcodeNumberfield);

		p.add(new JLabel("Threshold"));
		productThresholdfield=new JTextField(20);
		p.add(productThresholdfield);

		p.add(new JLabel("Order quantity"));
		productOrderQuantityfield=new JTextField(20);
		p.add(productOrderQuantityfield);

		JButton addBtn = new JButton ("Add");
		addBtn.addActionListener (new ActionListener () { 
			public void actionPerformed (ActionEvent e) {
				action_source  =((JButton)e.getSource()).getText();
				if (productNameField.getText().length()!=0 && productNameField.getText().length()!=0 &&
					productDescriptionField.getText().length()!=0 && productQuantityfield.getText().length()!=0 &&
					productPricefield.getText().length()!=0 && productBarcodeNumberfield.getText().length()!=0 &&
					productThresholdfield.getText().length()!=0 && productOrderQuantityfield.getText().length()!=0) {
				boolean value;
				try {
					value = performAddAction();
					if(value == true)
					{
						refresh();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

			}else {
				JOptionPane.showMessageDialog(manager.getMainWindow(),
						"Fields cannot be empty !",
						"Empty Fields",
						JOptionPane.INFORMATION_MESSAGE); 
			}
			} 
		}
				);

		JButton resetBtn = new JButton("Reset");

		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				action_source  =((JButton)e.getSource()).getText();
				productNameField.setText(null);
				productDescriptionField.setText(null);
				productQuantityfield.setText(null);
				productPricefield.setText(null);
				productBarcodeNumberfield.setText(null);
				productThresholdfield.setText(null);
				productOrderQuantityfield.setText(null);

			}
		});
		panel.add(p, BorderLayout.CENTER);
		
		JPanel bPanel = new JPanel();
		bPanel.setLayout(new BoxLayout(bPanel, BoxLayout.Y_AXIS));
		bPanel.add(addBtn);
		bPanel.add(resetBtn);
		panel.add(bPanel, BorderLayout.EAST);

		panel.setBorder(BorderFactory.createTitledBorder(
				loweredetched, "New Product Pane")); 

		return panel;
	}

	//*****************To Show all Buttons **********************	

	private JPanel createButtonPanel(){ 
		CardLayout layout = (CardLayout)(cards.getLayout());
		JPanel p = new JPanel(new GridLayout(0,1,0,10));
		JPanel panel = new JPanel(new BorderLayout());

		JButton removeBtn = new JButton ("Remove");
		removeBtn.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				String s =null;
				action_source  =(((JButton)e.getSource()).getText());
				if(table.getSelectedRow()!=-1){ //If at least one row is selected

					for(int i = 0 ; i<table.getColumnCount() ; i++)
					{
						if(table.getColumnName(i).equalsIgnoreCase("Prod. ID")){
							s = (String)table.getValueAt(table.getSelectedRow(), i);
							showConfirmDialog(s);
						}
					}
				}  else {
					JOptionPane.showMessageDialog( manager.getMainWindow(),
							"Please select a row to remove!",
							"Select a Row",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		JButton showProdBtn = new JButton ("Show Products");
		showProdBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(cards, Product_List);
				removeBtn.setEnabled(true);
			}
		});
		
		p.add(showProdBtn);
		
		//add a button for show the product below threthold to storekeeper
		JButton checkProductsBelowThresholdBtn = new JButton("Products below Threshold");

		checkProductsBelowThresholdBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action_source = (((JButton)e.getSource()).getText());
				//create a new panel for display,includes a return button.
				layout.show(cards, Check_Below_Threshold);
				removeBtn.setEnabled(false);			}
		});
		p.add(checkProductsBelowThresholdBtn);

		p.add(removeBtn);

		panel.add(p, "North");
		panel.setBorder(BorderFactory.createCompoundBorder(
				raisedetched, loweredetched)); 

		return panel;

	}

	//*****************To Show Product List Panel**********************	

	private JPanel showProductListPanel() {  
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JLabel label = new JLabel("Products : ");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		table = new JTable();	
		table.setModel(productTableModel);
		productTableModel.fireTableDataChanged();
		scroller = new JScrollPane(table); //scroller automatically puts the table header at the top
		table.setFillsViewportHeight(true); // true : table uses the entire height of the container, even if the table doesn't have enough rows to use the whole vertical space. 

		panel.add(label, "North");
		panel.add(scroller, "Center");

		panel.setBorder(BorderFactory.createCompoundBorder(
				raisedetched, loweredetched)); 

		return panel;

	}


	//******************Reflect the changes done on the screen by the buttons******************

	public void refresh(){   
		if(action_source.equalsIgnoreCase("Add")){
			int rowIndex = product_list.size()-1;
			productTableModel.fireTableRowsInserted(rowIndex, rowIndex);
		}
		else if(action_source.equalsIgnoreCase("Remove")){
			productTableModel.fireTableRowsDeleted(table.getSelectedRow(), table.getSelectedRow());
		}
	}
	
	protected boolean performAddAction() throws IOException{
	try {
		int categoryIndex = categoryField.getSelectedIndex();
	Category category=manager.getCategories().get(categoryIndex);
	String productName=productNameField.getText();
	String productDescription=productDescriptionField.getText();
	int productQuantity=Integer.parseInt(productQuantityfield.getText());
	Double productPrice=Double.parseDouble(productPricefield.getText());
	String productBarcodeNumber=productBarcodeNumberfield.getText();
	int productthreshold=Integer.parseInt(productThresholdfield.getText());
	int productOrderNumber=Integer.parseInt(productOrderQuantityfield.getText());
	
	manager.getStore().getProductReg().addProduct(category, productName, productDescription, productQuantity,
			productPrice, productBarcodeNumber, productthreshold, productOrderNumber);
	
	productNameField.setText("");
	productDescriptionField.setText("");
	productQuantityfield.setText("");
	productPricefield.setText("");
	productBarcodeNumberfield.setText("");
	productThresholdfield.setText("");
	productOrderQuantityfield.setText("");
	} catch (NumberFormatException e) {
		JOptionPane.showMessageDialog(this,
                "Please enter valid value!",
                "Number Format Exception",
                JOptionPane.ERROR_MESSAGE);
	} catch (BadValueException e) {
		e.printStackTrace();
	}
	return true;
	}
	// ************Show the confirm dialog on removing and performs the remove functionality*********

	public void showConfirmDialog(String s) { 
		String title = "Remove Product";
		String msg = "Do you really want to remove product " + s + " ?";
		ConfirmDialog d = new ConfirmDialog (manager.getMainWindow(), title, msg) {

			private static final long serialVersionUID = 1L;

			protected boolean performOkAction () {
				manager.removeProduct(s);
				refresh();
				return true;
			}
		};
		d.setModal(true);
		d.setLocationRelativeTo(this);
		d.pack();
		d.setVisible (true);
	}

}
