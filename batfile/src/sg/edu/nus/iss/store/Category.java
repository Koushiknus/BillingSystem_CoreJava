package sg.edu.nus.iss.store;

import sg.edu.nus.iss.exceptions.BadValueException;


public class Category {
	private String categoryCode;
	private String categoryName;
	
	public Category(String categoryName) throws BadValueException {
		String error = null;
		if (categoryName==null)
			error = "Category Name is null";
		if (error != null)
			throw new BadValueException(error);
		this.categoryName = categoryName;
		categoryCode = (categoryName.substring(0, 2)).toUpperCase();
	}
	
	public Category(String categoryCode, String categoryName) throws BadValueException{
		String error = null;
		if (categoryName==null)
			error = "Category Name is null";
		else if (categoryCode == null)
			error = "Category code cannot be null";
		if (error != null)
			throw new BadValueException(error);
		this.categoryName = categoryName;
		this.categoryCode = categoryCode.toUpperCase();
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

}
