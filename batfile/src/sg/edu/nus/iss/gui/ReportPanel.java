package sg.edu.nus.iss.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import sg.edu.nus.iss.store.Transaction;

public class ReportPanel extends JPanel {
	
	private static final long serialVersionUID = -5148543969874913835L;
	private StoreApplication manager;
	private AbstractTableModel categoryTableModel;
	private AbstractTableModel productTableModel;
	private AbstractTableModel memberTableModel;
	private Border raisedetched;
	private Border loweredetched;
	private JPanel cards;
	private static final String Category_ ="Category";
	private static final String Member_ ="Member";
	private static final String Product_ ="Product";
	private static final String Transaction_ ="Transaction";
	private ArrayList<Transaction> productList;
	private AbstractTableModel transactionTableModel;
	private SimpleDateFormat ft;
	private static final String[] COLUMN_NAMES = {"Trans. ID", "Prod. ID", "Member Id", "Quant purchased", "Date", "Prod Name", "Prod Desc"};


	/**
	 * Create the panel.
	 */
	public ReportPanel(StoreApplication manager) {
		this.manager = manager;
		ft = new SimpleDateFormat("yyyy-MM-dd");
		raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED); 
		categoryTableModel = manager.getCategoryTableModel(); 
		productTableModel = manager.getProductTableModel();
		memberTableModel = manager.getMemberTableModel();
		setLayout (new BorderLayout());		
		cards = new JPanel(new CardLayout());
		
		cards.add(createReportViewPanel(categoryTableModel, "List of All Categories"), Category_);
		cards.add(createReportViewPanel(memberTableModel, "List of All Members"), Member_);
		cards.add(createReportViewPanel(productTableModel, "List of All Products"),Product_);
		cards.add(showTransactionTable(), Transaction_);

		add(cards,BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.EAST);
		
	}
	
	public JPanel createButtonPanel () {
		JPanel p = new JPanel(new GridLayout(0,1,0,10));
		JPanel panel = new JPanel(new BorderLayout());
		CardLayout cl = (CardLayout)(cards.getLayout());

		JButton catBtn = new JButton("Category");
		catBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 cl.show(cards, Category_);
			}
		});
		
		p.add(catBtn);

		JButton memBtn = new JButton("Member");
		memBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 cl.show(cards, Member_);
			}
		});
		
		p.add(memBtn);
		JButton prodBtn = new JButton("Product");
		prodBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(cards, Product_);
			}
		});
		
		p.add(prodBtn);
		
		JButton transBtn = new JButton("Transaction");
		transBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(cards, Transaction_);
			}
		});
		
		p.add(transBtn);
		
		panel.add(p, "North");
		panel.setBorder(BorderFactory.createCompoundBorder(
				raisedetched, loweredetched)); 

		return panel;
	}
	
	public JPanel createReportViewPanel (AbstractTableModel tableModel, String title) {
		JPanel panel = new JPanel();
		JScrollPane scroller;
		JTable table;
		panel.setLayout(new BorderLayout());
		JLabel label = new JLabel(title);
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		table = new JTable();	
		table.setModel(tableModel);
		tableModel.fireTableDataChanged();
		scroller = new JScrollPane(table);
		table.setFillsViewportHeight(true);  

		panel.add(label, "North");
		panel.add(scroller, "Center");

		panel.setBorder(BorderFactory.createCompoundBorder(
				raisedetched, loweredetched)); 

		return panel;
	}
	
	
	private JPanel showTransactionTable() {  
		JScrollPane scroller;
		JTable table = new JTable();
		scroller = new JScrollPane(table);
		String startDate = "2000-01-01";
		String endDate = "2000-01-01";

		try {
			productList = manager.getTransactions(startDate, endDate);
			transactionTableModel = getTransactionTableModel();
			table.setModel(transactionTableModel);
			table.setFillsViewportHeight(true); 
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		JPanel panel = new JPanel();
		JTextField startDateField = new JTextField();
		JTextField endDateField = new JTextField();
		panel.setLayout(new BorderLayout());
		JPanel p = new JPanel(new BorderLayout());
		JPanel p1 = new JPanel();
		JLabel label = new JLabel("Enter range of date to get report : (yyyy-MM-dd)");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		p1.add(label);
		JPanel p2 = new JPanel(new GridLayout(1, 0, 10, 0));
		p2.add(new JLabel("Start Date"));
		p2.add(startDateField);
		p2.add(new JLabel("End Date"));
		p2.add(endDateField);
		
		JButton okBtn = new JButton("Ok");
		
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ft.parse(startDateField.getText());
					ft.parse(endDateField.getText());
					productList = manager.getTransactions(startDateField.getText(), endDateField.getText());	
					transactionTableModel.fireTableDataChanged();

				}catch (ParseException ex) {
					JOptionPane.showMessageDialog(null,
		                    "Please enter valid date in given date format!",
		                    "Date Format Exception",
		                    JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	
		p2.add(okBtn);
		
		p.add(p1, "North");
		p.add(p2, "Center");
		
		panel.add(p, "North");
		panel.add(scroller, "Center");

		panel.setBorder(BorderFactory.createCompoundBorder(
				raisedetched, loweredetched)); 

		return panel;

	}
	
	public AbstractTableModel getTransactionTableModel() {
		if (transactionTableModel != null) 
			return transactionTableModel;
		else {
			transactionTableModel = new AbstractTableModel() {

				private static final long serialVersionUID = 1L;

				@Override
				public String getColumnName(int column) {
					return COLUMN_NAMES[column];
				}

				@Override
				public int getRowCount() {
					return productList.size();
				}

				@Override
				public int getColumnCount() {
					return COLUMN_NAMES.length;
				}

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					Transaction transaction = productList.get(rowIndex);
					switch (columnIndex) {
					case 0: return transaction.getTranasctionId();
					case 1: return transaction.getProductId();
					case 2: return transaction.getMemberId();
					case 3: return transaction.getqtyPurchased();
					case 4: return transaction.getDateOfPurchase();
					case 5: return (manager.getProductByID(transaction.getProductId())).getProductId();
					case 6: return (manager.getProductByID(transaction.getProductId())).getName();
					default: return null;
					}
				}
			};

			return transactionTableModel;
		}
	}

}
	