package sg.edu.nus.iss.store;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import sg.edu.nus.iss.dao.CategoryDao;
import sg.edu.nus.iss.exceptions.BadValueException;



public class CategoryRegister {
	private ArrayList<Category> categories;
	private Category category;
	private CategoryDao catDao;
	private static final String[] COLUMN_NAMES = {"Code", "Category Name"};
	private AbstractTableModel categoryTableModel;
	
	public CategoryRegister() {
		categories = new ArrayList<Category> ();
		catDao = new CategoryDao();
	}
	
	public boolean addCategory(String categoryCode, String categoryName) throws BadValueException {
		for(Category c : categories){
			if(c.getCategoryCode().equalsIgnoreCase(categoryCode)){
				return false;
			}
		}
		category = new Category(categoryCode, categoryName);
		categories.add(category);
		return true;
	}
	
	public void removeCategory(String categoryCode) {   // Removing by selecting category code
		if(categories!=null){
			for(Category c : categories){
				if(categoryCode.compareTo(c.getCategoryCode())==0){
					categories.remove(c);
					break;
				}
			}
		writeToFile();
		}
	}
	
	public Category getCategory(String categoryCode) {
		if(categories!=null){
			for(Category c : categories){
				if (categoryCode.equalsIgnoreCase(c.getCategoryCode())){
					return c;
				}
			}
		}
		return null;
	}
	
	public Category getCategorybyName(String categoryName) {
		for(Category c :categories) {
			if (c.getCategoryName().equals(categoryName)) {
				return c;
			}
		}
		return null;
	}
	
	public ArrayList<Category> getCategories() {
		return categories;
	}
	
	
	public void createListFromFile() throws IOException
	{
		categories = catDao.createListFromFile();
	}
	
	public void writeToFile() {
		try {
			catDao.writeToFile(categories);
		}catch (IOException ex) {
			System.out.println("Cannot Write to file !");
			ex.printStackTrace();
		}
	}
	
	public AbstractTableModel getCategoryTableModel() {
		if (categoryTableModel != null) 
			return categoryTableModel;
		else {
			categoryTableModel = new AbstractTableModel() {

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
					return categories.size();
				}

				@Override
				public int getColumnCount() {
					return COLUMN_NAMES.length;
				}

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					Category category = categories.get(rowIndex);
					switch (columnIndex) {
					case 0: return category.getCategoryCode();
					case 1: return category.getCategoryName();
					default: return null;
					}
				}
			};

			return categoryTableModel;
		}
	}
}
