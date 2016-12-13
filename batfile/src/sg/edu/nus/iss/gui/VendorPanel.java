package sg.edu.nus.iss.gui;

import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import sg.edu.nus.iss.utils.ConfirmDialog;
import sg.edu.nus.iss.exceptions.BadValueException;
import sg.edu.nus.iss.store.Category;
import sg.edu.nus.iss.store.Vendor;



public class VendorPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private StoreApplication manager;
	private JScrollPane scroller;
	private JTable table;
	private static final String[] COLUMN_NAMES = {"Vendor Name", "Description"};
	private AbstractTableModel vendorTableModel;
	private ArrayList<Vendor> vendors ;
	private String action_source;
	private JComboBox<String> categoryBox;
	private JComboBox<String> vendorBox;
	private Border raisedetched;
	private Border loweredetched; 
	private JTextField vNameT;
	private JTextField vDescT ;
	
	public VendorPanel(StoreApplication manager) {
		this.manager = manager;
		vendors = manager.getVendors();
		vendorTableModel = getVendorTableModel();
		raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED); 
		setLayout (new BorderLayout());
		
		add(createButtonPanel(), BorderLayout.EAST);
		add(showVendorListPanel(), BorderLayout.CENTER);
		add(createAddVendorPanel(), BorderLayout.SOUTH);

	}

	//*****************To create add Vendor Panel**********************

	private JPanel createAddVendorPanel () { 

		JPanel panel = new JPanel(new GridLayout(3, 4, 10, 0));
		JLabel label = new JLabel("Add Vendor : ");
		label.setFont(new Font("Tahoma", Font.BOLD, 12 ));
		JLabel categoryLabel = new JLabel("Select Category:");
		categoryBox = new JComboBox<String>();
		for(Category c:manager.getCategories()){
			categoryBox.addItem(c.getCategoryName());
		}
		
		JLabel vendorLabel = new JLabel("Select Vendor:");
		
		vendorBox = new JComboBox<String>();
		for(Vendor v:manager.getVendors()){
			vendorBox.addItem(v.getVendorName());
		}
		
		JLabel vName = new JLabel("Vendor Name: ");
		vNameT = new JTextField();
		JLabel vDesc = new JLabel("Description: ");
		vDescT = new JTextField();
		
		//panel.add(label);
		panel.add(vendorLabel);
		panel.add(vendorBox);
		panel.add(vName);
		panel.add(vNameT);
		panel.add(vDesc);
		panel.add(vDescT);
		panel.add(categoryLabel);
		panel.add(categoryBox);

		JButton addBtn = new JButton ("Add");
		addBtn.addActionListener (new ActionListener () { 
			public void actionPerformed (ActionEvent e) {
				action_source  =((JButton)e.getSource()).getText();
				if (vNameT.getText().length()!=0 && vDescT.getText().length()!=0){ //if vendor name and description not empty (means a new vendor)
							try {
								if (!(manager.addVendor(vNameT.getText(), vDescT.getText(), manager.getCategoryByName((String) categoryBox.getSelectedItem())))){
								JOptionPane.showMessageDialog(manager.getMainWindow(),
										"Vendor Already Exists in the Category !",
										"Duplicate Vendor Code",
										JOptionPane.ERROR_MESSAGE); 
} else {
								vendors = manager.getVendorsPerCategory(manager.getCategoryByName((String)categoryBox.getSelectedItem()));
								vendorTableModel.fireTableDataChanged();
								refresh();
}
							} catch (HeadlessException e1) {
								e1.printStackTrace();
							} catch (BadValueException e1) {
								e1.printStackTrace();
							}
				} else { String vendorName = (String) vendorBox.getSelectedItem();
					try {
						if (!(manager.addVendor(vendorName, (manager.getVendor(vendorName)).getDescription(), manager.getCategoryByName((String) categoryBox.getSelectedItem())))){
							JOptionPane.showMessageDialog(manager.getMainWindow(),
									"Vendor Already Exists in the Category !",
									"Duplicate Vendor Code",
									JOptionPane.ERROR_MESSAGE); 
						}
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					} catch (BadValueException e1) {
						JOptionPane.showMessageDialog(manager.getMainWindow(),
								"Bad Value entered !",
								"Bad Value",
								JOptionPane.ERROR_MESSAGE); 
					}
				}
			}
			});
		panel.add(new JPanel());
		panel.add(new JPanel());
		panel.add(addBtn);
		
		JButton viewBtn = new JButton("View");
		viewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ArrayList<Vendor> temp = manager.getVendorsPerCategory(manager.getCategoryByName((String)categoryBox.getSelectedItem()));
				if (temp != null){
					vendors = temp;
				vendorTableModel.fireTableDataChanged();
				}
				else {
					JOptionPane.showMessageDialog(manager.getMainWindow(),
							"No Vendor Exists in the Selected Category!",
							"Empty Vendor Category",
							JOptionPane.ERROR_MESSAGE); 
				}
			}
		});
		
		panel.add(viewBtn);		
		panel.setBorder(BorderFactory.createTitledBorder(
				loweredetched, "New Vendor Pane")); 

		return panel;
	}

	//*****************To Show all Buttons **********************	

	private JPanel createButtonPanel(){ 
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
						if(table.getColumnName(i).equalsIgnoreCase("Vendor Name")){
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

		p.add(removeBtn);

		panel.add(p, "North");
		panel.setBorder(BorderFactory.createCompoundBorder(
				raisedetched, loweredetched)); 

		return panel;

	}

	//*****************To Show Vendor List Panel**********************	

	private JPanel showVendorListPanel() {  
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JLabel label = new JLabel("Vendors : ");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		table = new JTable();	
		table.setModel(vendorTableModel);
		vendorTableModel.fireTableDataChanged();
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
			int rowIndex = vendors.size()-1;
			vendorTableModel.fireTableRowsInserted(rowIndex, rowIndex);
			vendorBox.removeAllItems();
			for(Vendor v:manager.getVendors()){
				vendorBox.addItem(v.getVendorName());
			}
			vNameT.setText(null);
			vDescT.setText(null);
		}
		else if(action_source.equalsIgnoreCase("Remove")){
			vendorTableModel.fireTableRowsDeleted(table.getSelectedRow(), table.getSelectedRow());
			vendorBox.removeAllItems();
			for(Vendor v:manager.getVendors()){
				vendorBox.addItem(v.getVendorName());
			}
		}

	}


	// ************Show the confirm dialog on removing and performs the remove functionality*********

	public void showConfirmDialog(String s) { 
		String title = "Remove Vendor";
		String msg = "Do you really want to remove Vendor " + s + " ?";
		ConfirmDialog d = new ConfirmDialog (manager.getMainWindow(), title, msg) {

			private static final long serialVersionUID = 1L;

			protected boolean performOkAction () {
				manager.removeVendor(s);
				refresh();
				return true;
			}
		};
		d.setModal(true);
		d.setLocationRelativeTo(this);
		d.pack();
		d.setVisible (true);
	}
	
	public String getSelectedCategory() {
		return (String)categoryBox.getSelectedItem();
	}
	

	public AbstractTableModel getVendorTableModel() {
		if (vendorTableModel != null) 
			return vendorTableModel;
		else {
			vendorTableModel = new AbstractTableModel() {

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
					return vendors.size();
				}

				@Override
				public int getColumnCount() {
					return COLUMN_NAMES.length;
				}

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					Vendor vendor = vendors.get(rowIndex);
					switch (columnIndex) {
					case 0: return vendor.getVendorName();
					case 1: return vendor.getDescription();
					default: return null;
					}
				}
			};

			return vendorTableModel;
		}
	}

	
}
