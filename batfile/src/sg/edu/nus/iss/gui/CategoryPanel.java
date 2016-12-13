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



public class CategoryPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private StoreApplication manager;
	private JScrollPane scroller;
	private JTable table;
	private AbstractTableModel categoryTableModel;
	private ArrayList<Category> categories ;
	private String action_source;
	private Border raisedetched;
	private Border loweredetched; 

	public CategoryPanel(StoreApplication manager) {
		this.manager = manager;
		raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED); 
		setLayout (new BorderLayout());
		categories = manager.getCategories();
		categoryTableModel = manager.getCategoryTableModel();
		add(createButtonPanel(), BorderLayout.EAST);
		add(showCategoryListPanel(), BorderLayout.CENTER);
		add(createAddCategoryPanel(), BorderLayout.SOUTH);

	}

	//*****************To create add category Panel**********************

	private JPanel createAddCategoryPanel () { 

		JPanel panel = new JPanel(new GridLayout(1, 0, 10, 0));

		JLabel label = new JLabel("Add Category : ");
		label.setFont(new Font("Tahoma", Font.BOLD, 12 ));
		JLabel cName = new JLabel("Category Name: ");
		JTextField cNameT = new JTextField();
		JLabel cCode = new JLabel("Category Code: ");
		JTextField cCodeT = new JTextField();

		panel.add(label);
		panel.add(cName);
		panel.add(cNameT);
		panel.add(cCode);
		panel.add(cCodeT);

		JButton addBtn = new JButton ("Add");
		addBtn.addActionListener (new ActionListener () { 
			public void actionPerformed (ActionEvent e) {
				action_source  =((JButton)e.getSource()).getText();
				if (cCodeT.getText().length()!=0 && cNameT.getText().length()!=0){
					if(cCodeT.getText().length()==3) {
						try {
							if (!(manager.addCategory(cCodeT.getText(), cNameT.getText()))){
								JOptionPane.showMessageDialog(manager.getMainWindow(),
										"Category Code Already Exists !",
										"Duplicate Category Code",
										JOptionPane.ERROR_MESSAGE); 
							} else {
								refresh();
							}
						} catch (HeadlessException e1) {
							e1.printStackTrace();
						} catch (BadValueException e1) {
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(manager.getMainWindow(),
								"Category code should have 3 characters.",
								"Invalid Category Code",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(manager.getMainWindow(),
							"Fields cannot be empty !",
							"Empty Fields",
							JOptionPane.INFORMATION_MESSAGE); 
				}
			}
		});

		JButton resetBtn = new JButton("Reset");

		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				action_source  =((JButton)e.getSource()).getText();
				cCodeT.setText(null);
				cNameT.setText(null);
			}
		});

		panel.add(addBtn);
		panel.add(resetBtn);

		panel.setBorder(BorderFactory.createTitledBorder(
				loweredetched, "New Category Pane")); 

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
						if(table.getColumnName(i).equalsIgnoreCase("Code")){
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

	//*****************To Show category List Panel**********************	

	private JPanel showCategoryListPanel() {  
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JLabel label = new JLabel("Categories : ");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		table = new JTable();	
		table.setModel(categoryTableModel);
		categoryTableModel.fireTableDataChanged();
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
			int rowIndex = categories.size()-1;
			categoryTableModel.fireTableRowsInserted(rowIndex, rowIndex);
		}
		else if(action_source.equalsIgnoreCase("Remove")){
			categoryTableModel.fireTableRowsDeleted(table.getSelectedRow(), table.getSelectedRow());
		}


	}


	// ************Show the confirm dialog on removing and performs the remove functionality*********

	public void showConfirmDialog(String s) { 
		String title = "Remove Member";
		String msg = "Do you really want to remove member " + s + " ?";
		ConfirmDialog d = new ConfirmDialog (manager.getMainWindow(), title, msg) {

			private static final long serialVersionUID = 1L;

			protected boolean performOkAction () {
				manager.removeCategory(s);
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
