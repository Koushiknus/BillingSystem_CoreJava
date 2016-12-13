package sg.edu.nus.iss.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sg.edu.nus.iss.exceptions.BadValueException;
import sg.edu.nus.iss.utils.OkCancelDialog;


public class AddDiscountDialog extends OkCancelDialog{
	private static final long serialVersionUID = 1L;
	private JTextField codeText;
	private JTextField descText;
	private JTextField startDateText;
	private JTextField periodText;
	private JTextField percText;
	JComboBox<String> discountCategory;
	private static final String[] options = {"Member Discount", "Occasional Discount"};
	private StoreApplication manager;
	private DiscountPanel dp;
	private SimpleDateFormat ft;

	public AddDiscountDialog(StoreApplication manager, DiscountPanel dp) {
		super(manager.getMainWindow(), "Add Discount");
		this.manager = manager;
		this.dp = dp;
		ft = new SimpleDateFormat("yyyy-MM-dd");
		ft.setLenient(false);
	}
	protected JPanel createFormPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		
		JLabel selectDiscount = new JLabel("Select type of discount");
		discountCategory = new JComboBox<String>(options);
		JLabel dCode = new JLabel("Discount Code");
		codeText = new JTextField();
		JLabel dDescription = new JLabel("Description");
		descText = new JTextField();
		JLabel dStartDate = new JLabel("Start Date : (yyyy-mm-dd)");
		startDateText = new JTextField();
		JLabel dPeriod = new JLabel("Period");
		periodText = new JTextField();
		JLabel dPercent = new JLabel("Percentage");
		percText = new JTextField();
		
		discountCategory.setSelectedIndex(0);
		panel.add(selectDiscount);
		panel.add(discountCategory);
		panel.add(dCode);
		panel.add(codeText);
		panel.add(dDescription);
		panel.add(descText);
		panel.add(dStartDate);
		panel.add(startDateText);
		panel.add(dPeriod);
		panel.add(periodText);
		panel.add(dPercent);
		panel.add(percText);
		
		codeText.setEnabled(false);
		descText.setEnabled(false);
		startDateText.setEnabled(false);
		periodText.setEnabled(false);
		percText.setEnabled(false);		
		
		discountCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dCategory = (String)discountCategory.getSelectedItem();
				if(dCategory.equals("Member Discount")){
					startDateText.setText("ALWAYS");
					periodText.setText("ALWAYS");
					startDateText.setEnabled(false);
					periodText.setEnabled(false);
					codeText.setEnabled(true);
					descText.setEnabled(true);
					percText.setEnabled(true);
					}
				if(dCategory.equals("Occasional Discount")){
					startDateText.setText(null);
					periodText.setText(null);
					codeText.setEnabled(true);
					descText.setEnabled(true);
					startDateText.setEnabled(true);
					periodText.setEnabled(true);
					percText.setEnabled(true);
				}
			}
		});
		
		return panel;

	}	
	
	protected boolean performOkAction() { 
		boolean b = false;
		if(startDateText.getText().length()==0||periodText.getText().length()==0 || codeText.getText().length()==0 ||
				descText.getText().length()==0||percText.getText().length()==0){
			return false;
		}
		
		try {
			String dCategory = (String)discountCategory.getSelectedItem();
			if(dCategory.equals("Occasional Discount")){
			ft.parse(startDateText.getText()); //to check for parsing error
			Integer.parseInt(periodText.getText());
			}
			Float.parseFloat(percText.getText());
		
		if (((String)discountCategory.getSelectedItem()).equals("Member Discount")) {
			b = manager.addDiscount(codeText.getText(), descText.getText(), Float.parseFloat(percText.getText()), null, null);
		} else if ((((String)discountCategory.getSelectedItem()).equals("Occasional Discount"))) {
			b = manager.addDiscount(codeText.getText(), descText.getText(), Float.parseFloat(percText.getText()), startDateText.getText(), periodText.getText());
		}
		dp.refresh();
		if(b==false){
			JOptionPane.showMessageDialog(this,
                    "Discount Code already exists !",
                    "Duplicate Discount",
                    JOptionPane.ERROR_MESSAGE);
		}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,
                    "Please enter valid value!",
                    "Number Format Exception",
                    JOptionPane.ERROR_MESSAGE);
		} catch (ParseException e){
			JOptionPane.showMessageDialog(this,
                    "Please enter valid date in given date format!",
                    "Date Format Exception",
                    JOptionPane.ERROR_MESSAGE);
		} catch (BadValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return b;
	}

}
