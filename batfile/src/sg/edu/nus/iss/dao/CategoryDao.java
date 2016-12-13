

package sg.edu.nus.iss.dao;

import java.io.IOException;
import java.util.ArrayList;

import sg.edu.nus.iss.exceptions.BadValueException;
import sg.edu.nus.iss.store.Category;

public class CategoryDao extends BaseDao {

	private static final String FILE_NAME = "StoreAppData/Categories.dat";
	private ArrayList<Category> categoryList;
	
	public CategoryDao()
	{
		categoryList = new ArrayList<Category>();
	}
	
	public ArrayList<Category> createListFromFile() throws IOException{
		ArrayList<String> fileList = new ArrayList<String>();
		fileList = super.readFromFile(FILE_NAME);
		
		if(fileList!=null)
		{
			for(String line: fileList)
			{
				String categoryData[] = line.split(",");
				try {
					categoryList.add(new Category(categoryData[0],categoryData[1]));
				} catch (BadValueException e) {
					e.printStackTrace();
				}
			}
		}
		return categoryList;
	
	}
	
	public void writeToFile(ArrayList<Category> categoryList) throws IOException
	{
		ArrayList<StringBuffer> writeCategory = new ArrayList<>();
		for(Category cat : categoryList)
		{
			StringBuffer catLine = new StringBuffer();
			catLine.append(cat.getCategoryCode());
			catLine.append(",");
			catLine.append(cat.getCategoryName());
			writeCategory.add(catLine);
		}
		super.writeToFile(writeCategory, FILE_NAME);
	}
	
}
