package sg.edu.nus.iss.exceptions;

public class BadValueException  extends Exception{

	private static final long serialVersionUID = 1L;

	public BadValueException () {
    }

    public BadValueException (String msg) {
        super (msg);
    }
}
