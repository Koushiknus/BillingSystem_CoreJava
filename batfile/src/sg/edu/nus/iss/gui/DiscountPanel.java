package sg.edu.nus.iss.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import sg.edu.nus.iss.store.Discount;
import sg.edu.nus.iss.utils.ConfirmDialog;




public class DiscountPanel extends JPanel {

	private static final long serialVersionUID = 1722399005697599362L;

	private JScrollPane scroller;
	private JTable table;
	private static final String[] COLUMN_NAMES = {"Disc. Code", "Description", "Start Date", "Period", "Disc. %age","Member(M)/All(A)" };
	private AbstractTableModel discountTableModel;
	private ArrayList<Discount> discounts ;
	private String action_source;
	private Border raisedetched;
	private Border loweredetched;
	private StoreApplication manager;
	private DiscountPanel dp;


	public DiscountPanel(StoreApplication manager) {
		this.manager = manager;
		dp = this;
		discounts = manager.getDiscounts();
		setLayout(new BorderLayout());
		raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED); 

		add(createButtonPanel(), BorderLayout.EAST);
		add(showDiscountListPanel(), BorderLayout.CENTER);
		add(createAddDiscountPanel(), BorderLayout.SOUTH);

	}

	//**********************Display Discount List Panel********l**********************	

	private JPanel showDiscountListPanel() {  
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JLabel label = new JLabel("Discounts Available : ");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		table = new JTable();	
		table.setModel(getTableModel());
		discountTableModel.fireTableDataChanged();
		scroller = new JScrollPane(table); //scroller automatically puts the table header at the top
		table.setFillsViewportHeight(true); // true : table uses the entire height of the container, even if the table doesn't have enough rows to use the whole vertical space. 

		panel.add(label, "North");
		panel.add(scroller, "Center");

		panel.setBorder(BorderFactory.createCompoundBorder(
				raisedetched, loweredetched)); 

		return panel;

	}

	//**********************Add/Modify Discount Panel *************************************	
	private JPanel createAddDiscountPanel () { 

		JPanel panel = new JPanel(new GridLayout(1, 0, 20, 0));

		JLabel label = new JLabel("Add/Modify Discount:");
		label.setFont(new Font("Tahoma", Font.BOLD, 12 ));

		JButton addDiscBtn = new JButton("Add Discount");
		JButton modifyDiscBtn = new JButton("Modify Discount Percentage");

		panel.add(label);
		panel.add(addDiscBtn);
		panel.add(modifyDiscBtn);

		//Add Discount
		addDiscBtn.addActionListener (new ActionListener () { 
			public void actionPerformed (ActionEvent e) {
				action_source  =((JButton)e.getSource()).getText();
				AddDiscountDialog d = new AddDiscountDialog (manager, dp);
				d.setModal(true);
				d.setLocationRelativeTo(manager.getMainWindow());
				d.pack();
				d.setVisible (true);
			}
		});

		// Modify Percentage 
		modifyDiscBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				String s =null;
				action_source  =((JButton)e.getSource()).getText();
				if(table.getSelectedRow()!=-1) {
					for (int i=0; i<table.getColumnCount();i++) {
						if(table.getColumnName(i).equalsIgnoreCase("Disc. Code")){
							s= (String)table.getValueAt(table.getSelectedRow(), i);
						}
					}
					String input = JOptionPane.showInputDialog("Input the new Percentage :");
					if(input!=null){
						if(!isFloat(input)) {
							JOptionPane.showMessageDialog(manager.getMainWindow(),
									"Percentage should be a number not a string!",
									"Invalid percentage",
									JOptionPane.ERROR_MESSAGE);
						} else {
							manager.modifyDiscount(s, Float.parseFloat(input));
							refresh();
						}
					}

				} else {
					JOptionPane.showMessageDialog(manager.getMainWindow(),
							"Please select a row to modify!",
							"Select a Row",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		panel.setBorder(BorderFactory.createTitledBorder(
				loweredetched, "ADD/Modify Discount Pane")); 

		return panel;
	}

	//*****************To create Button Panel**********************	

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
						if(table.getColumnName(i).equalsIgnoreCase("Disc. Code")){
							s = (String)table.getValueAt(table.getSelectedRow(), i);
							showConfirmDialog(s);
						}
					}

				}  else {
					JOptionPane.showMessageDialog(manager.getMainWindow(),
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

	//**********************Set Table Model********l********************************	

	public TableModel getTableModel() {
		if (discountTableModel != null) 
			return discountTableModel;
		else {
			discountTableModel = new AbstractTableModel() {

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
					return discounts.size();
				}

				@Override
				public int getColumnCount() {
					return COLUMN_NAMES.length;
				}

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					Discount discount = discounts.get(rowIndex);
					switch (columnIndex) {
					case 0: return discount.getDiscountCode();
					case 1: return discount.getDescription();
					case 2: return discount.getStartDate();
					case 3: return discount.getDiscountPeriod();
					case 4: return discount.getPercentage();
					case 5: return discount.getApplicableToMember();
					default: return null;
					}
				}
			};

			return discountTableModel;
		}
	}

	//******************Reflect the changes done on the screen using buttons******************

	public void refresh(){   
		if(action_source.equalsIgnoreCase("Add Discount")){
			int rowIndex = discounts.size()-1;
			discountTableModel.fireTableRowsInserted(rowIndex, rowIndex);
		}
		else if(action_source.equalsIgnoreCase("Remove")){
			discountTableModel.fireTableRowsDeleted(table.getSelectedRow(), table.getSelectedRow());
		}
		else if (action_source.equalsIgnoreCase("Modify Discount Percentage")) {
			discountTableModel.fireTableRowsUpdated(table.getSelectedRow(), table.getSelectedRow());
		}

	}

//******************Show confirmation dialog on removing******************

	public void showConfirmDialog(String s) { 
		String title = "Remove Discount";
		String msg = "Do you really want to remove discount " + s + " ?";
		ConfirmDialog d = new ConfirmDialog (manager.getMainWindow(), title, msg) {

			private static final long serialVersionUID = 1L;

			protected boolean performOkAction () {
				manager.removeDiscount(s);
				refresh();
				return true;
			}
		};
		d.setModal(true);
		d.setLocationRelativeTo(this);
		d.pack();
		d.setVisible (true);
	}

// To check if the number input is a float or not
	public boolean isFloat( String input ) {
		try {
			Float.parseFloat( input );
			return true;
		}
		catch( Exception e ) {
			return false;
		}
	}

}
