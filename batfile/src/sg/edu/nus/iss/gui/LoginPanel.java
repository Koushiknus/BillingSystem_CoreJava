package sg.edu.nus.iss.gui;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import sg.edu.nus.iss.store.Cart;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;



public class LoginPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6316032716933825384L;
	private JPasswordField password;
	private MainPanel mainPanel;
	private Border raisedetched;
	private Border loweredetched; 
	/**
	 * Create the panel.
	 */
	public LoginPanel(StoreApplication manager) {
		setBackground(SystemColor.inactiveCaption);
		ISSLogoPanel logo = new ISSLogoPanel();

		raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		setBorder(BorderFactory.createCompoundBorder(
				raisedetched, loweredetched)); 

		setLayout(new BorderLayout());

		// Add Welcome Message
		JLabel lblWelcomeToThe = new JLabel("Welcome To the Souvenir Store");
		
		lblWelcomeToThe.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblWelcomeToThe.setHorizontalAlignment(SwingConstants.CENTER);
		//lblWelcomeToThe.setHorizontalAlignment(SwingConstants.BOTTOM);
		add(lblWelcomeToThe, BorderLayout.NORTH);

		//Add Login Panel
		JPanel login = new JPanel();
		JLabel lblUsername = new JLabel("Username");
		JLabel lblPassword = new JLabel("Password");
		JTextField username = new JTextField();
		password = new JPasswordField();

		JPanel usernamePanel = new JPanel();
		JPanel passwordPanel = new JPanel();
		usernamePanel.setLayout(new GridLayout(1,0));
		passwordPanel.setLayout(new GridLayout(1,0));

		usernamePanel.add(lblUsername);
		usernamePanel.add(username);
		passwordPanel.add(lblPassword);
		passwordPanel.add(password);

		login.setLayout(new GridLayout(0,1));

		login.add(usernamePanel);
		login.add(passwordPanel);



		JPanel centerPanel = new JPanel(new GridLayout(3,3));

		JPanel panelNW = new JPanel();
		JPanel panelNE = new JPanel();
		JPanel panelE = new JPanel();
		JPanel panelW = new JPanel();
		JPanel panelSE = new JPanel();
		JPanel panelS = new JPanel();
		JPanel panelSW = new JPanel();
		JPanel panelN = new JPanel();
		JPanel panelC = new JPanel(new BorderLayout());

		panelC.add(login, "North");

		JLabel lblLogin = new JLabel("Please login to continue: ");	
		panelN.setLayout(new BorderLayout());
		panelN.add(lblLogin, "Center");

		JButton loginBtn = new JButton("Login");
		JButton resetBtn = new JButton("Reset");

		panelS.add(loginBtn);
		panelS.add(Box.createRigidArea(new Dimension(10,10)));
		panelS.add(resetBtn);

		panelNE.add(logo);
		
		centerPanel.add(panelNW);
		centerPanel.add(panelN);
		centerPanel.add(panelNE);
		centerPanel.add(panelW);
		centerPanel.add(panelC);
		centerPanel.add(panelE);
		centerPanel.add(panelSW);
		centerPanel.add(panelS);
		centerPanel.add(panelSE);


		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(manager.validate(username.getText(), String.valueOf(password.getPassword()))){
					mainPanel = new MainPanel(manager);
					removeAll();
					setLayout(new BorderLayout());
					add("Center", mainPanel);
					revalidate();
					repaint();		
				} else {
					JOptionPane.showMessageDialog(manager.getMainWindow(),
							"Username/Password Incorrect !",
							"Login Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username.setText(null);
				password.setText(null);
			}
		});

		add(centerPanel, "Center");

	}

	public void refreshCart() {
		mainPanel.refreshCart();
		
	}
	
	public Cart getSelectedCartItem() {
		return mainPanel.getSelectedCartItem();
	}

}
