package sg.edu.nus.iss.gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;

import sg.edu.nus.iss.store.Cart;

public class StoreWindow extends JFrame {

	
	private static final long serialVersionUID = 7460404160082932891L;
	private LoginPanel loginPanel;
	//	/private StoreApplication manager;


	/**
	 * Create the frame.
	 */
	public StoreWindow(String s, StoreApplication manager) {
		super(s);
		//this.manager = manager;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		try {
			// Check if Nimbus is supported and get its classname
			for (UIManager.LookAndFeelInfo lafInfo : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(lafInfo.getName())) {
					UIManager.setLookAndFeel(lafInfo.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			try {
				// If Nimbus is not available, set to the default Java (metal) look and feel
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		setVisible(true);
		this.setMinimumSize(new Dimension(600, 500));
		loginPanel = new LoginPanel(manager);
		add ("Center", loginPanel);
	}


	public void refreshCart() {
		// TODO Auto-generated method stub
		loginPanel.refreshCart();
	}
	
	public Cart getSelectedCartItem() {
		return loginPanel.getSelectedCartItem();
	}

	


}
