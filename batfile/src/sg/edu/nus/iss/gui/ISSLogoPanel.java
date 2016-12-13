package sg.edu.nus.iss.gui;

import java.awt.*;
import javax.swing.*;




public class ISSLogoPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	@Override
	protected void paintComponent(Graphics g) {
		//super.paintComponent(g);
		Image image = new ImageIcon("StoreAppData/NUS_ISS-logo.jpg").getImage();
		g.drawImage(image, 0, 0, this);            
	}

}

