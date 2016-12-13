package sg.edu.nus.iss.utils;

import javax.swing.*;

public abstract class ConfirmDialog extends OkCancelDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6202153659235580926L;
	private JLabel       messageLabel;

    public ConfirmDialog (JFrame parent, String title, String message) {
        super (parent, title);
        messageLabel.setText (message);
    }

    protected JPanel createFormPanel () {
        JPanel p = new JPanel ();
        messageLabel = new JLabel ();
        p.add (messageLabel);
        return p;
    }

}
