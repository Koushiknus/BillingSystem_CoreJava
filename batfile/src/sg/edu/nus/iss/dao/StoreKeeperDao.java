package sg.edu.nus.iss.dao;

import java.io.IOException;
import java.util.ArrayList;

import sg.edu.nus.iss.exceptions.BadValueException;
import sg.edu.nus.iss.store.StoreKeeper;


public class StoreKeeperDao extends BaseDao{

	private static final String FILE_NAME = "StoreAppData/StoreKeepers.dat";
	private ArrayList<StoreKeeper> storeKeeperList;
	
	public  StoreKeeperDao() {

		storeKeeperList = new ArrayList<StoreKeeper>();
	}
	
	public ArrayList<StoreKeeper> createListFromFile() throws IOException{
		ArrayList<String> fileList = new ArrayList<String>();
		fileList = super.readFromFile(FILE_NAME);
		
		if(fileList!=null)
		{
			for(String line: fileList)
			{
				String storeData[] = line.split(",");
				try {
					storeKeeperList.add(new StoreKeeper(storeData[0],storeData[1]));
				} catch (BadValueException e) {
					e.printStackTrace();
				}
			}
		}
		return storeKeeperList;
	
	}
	
	public void writeToFile(ArrayList<StoreKeeper> storeList) throws IOException
	{
		ArrayList<StringBuffer> writeCategory = new ArrayList<>();
		for(StoreKeeper store : storeList)
		{
			StringBuffer catLine = new StringBuffer();
			catLine.append(store.getStoreKeeperName());
			catLine.append(",");
			catLine.append(store.getPassword());
			writeCategory.add(catLine);
		}
		super.writeToFile(writeCategory, FILE_NAME);
	}
	
}

