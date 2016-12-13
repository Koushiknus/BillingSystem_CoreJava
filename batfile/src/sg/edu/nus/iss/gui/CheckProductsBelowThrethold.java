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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import sg.edu.nus.iss.store.Product;

public class CheckProductsBelowThrethold extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JScrollPane scroller;
	private JTable table;
	private static final String[] COLUMN_NAMES = {"Prod. ID", "Prod. Name", "Description", "Quantity Avail.", "Price", "Bar Code", "Reorder Quant.", "Order Quant."};
	private AbstractTableModel cTableModel;
	private ArrayList<Product> product_Below_list ;
	private Border raisedetched;
	private Border loweredetched;
	private StoreApplication manager;
	
	public CheckProductsBelowThrethold(StoreApplication manager){
		this.manager = manager;
		raisedetched=BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		loweredetched=BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		
		setLayout(new BorderLayout());
		product_Below_list=manager.getProductsBelowThreshold();
		add(showProductsBelowThreshold(),BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.EAST);
		
	}
	

	private JPanel createButtonPanel(){
		JPanel panel = new JPanel(new BorderLayout());
		JPanel p=new JPanel(new GridLayout(0, 1,0,10));
		
		
		JButton reOrderBtn=new JButton("Reorder Products");
		reOrderBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				manager.AddQuantityForThretholdProducts();
				product_Below_list=manager.getProductsBelowThreshold();
				cTableModel.fireTableDataChanged();
			}
		});

		p.add(reOrderBtn);
		panel.add(p,"North");
		panel.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredetched));
		
		return panel;
	}
	

	private JPanel showProductsBelowThreshold(){
		JPanel panel=new JPanel();
		panel.setLayout(new BorderLayout());
		
		JLabel label=new JLabel("Products Below Threthold:");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		table=new JTable();
		table.setModel(getTableModel());
		cTableModel.fireTableDataChanged();
		scroller=new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		panel.add(label,"North");
		panel.add(scroller, "Center");
		
		panel.setBorder(BorderFactory.createCompoundBorder(raisedetched, loweredetched));
		return panel;
	}
	
	//**********************Set Table Mode******************************	

		public TableModel getTableModel() {
			if (cTableModel != null) 
				return cTableModel;
			else {
				cTableModel = new AbstractTableModel() {

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
						return product_Below_list.size();
					}

					@Override
					public int getColumnCount() {
						return COLUMN_NAMES.length;
					}

					@Override
					public Object getValueAt(int rowIndex, int columnIndex) {
						Product product = product_Below_list.get(rowIndex);
						switch (columnIndex) {
						case 0: return product.getProductId();
						case 1: return product.getName();
						case 2: return product.getDescription();
						case 3: return product.getQuantityAvailable();
						case 4: return product.getPrice();
						case 5: return product.getBarcodeNumber();
						case 6: return product.getThreshold();
						case 7: return product.getOrderQuantity();
						default: return null;
						}
					}
				};

				return cTableModel;
			}
		}
	
	
	
}





