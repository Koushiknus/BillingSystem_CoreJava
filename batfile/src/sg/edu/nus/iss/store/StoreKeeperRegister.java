package sg.edu.nus.iss.store;

import java.io.IOException;
import java.util.ArrayList;

import sg.edu.nus.iss.dao.StoreKeeperDao;
import sg.edu.nus.iss.exceptions.BadValueException;


public class StoreKeeperRegister
{
	private ArrayList<StoreKeeper> storeKeepers;
	private StoreKeeperDao storeDao; 
	
	public StoreKeeperRegister()
	{
		 storeKeepers =new ArrayList<StoreKeeper>();
		storeDao = new StoreKeeperDao();
	}
	
	public void addStoreKeeper(String storeKeeperName, String password) throws BadValueException
	{
		StoreKeeper storeKeeper= new StoreKeeper(storeKeeperName, password);
		storeKeepers.add(storeKeeper);
	}
	
	public StoreKeeper getStoreKeeper(String storeKeeperName)
	{
		for(StoreKeeper sk: storeKeepers)
		{
			if(storeKeeperName.equals(sk.getStoreKeeperName()))
				return sk; 
		}
	
		return null;
	}
	
	public boolean validate(String storeKeeperName,String password)
	{
		for (StoreKeeper sk: storeKeepers){
				if((storeKeeperName.compareTo(sk.getStoreKeeperName())==0)&&(password.compareTo(sk.getPassword())==0)) {
				return true;
				}
		}
		return false;
	}
	
	public void createListFromFile() throws IOException{
		storeKeepers = storeDao.createListFromFile();
	}
	
	public void writeToFile() {
		try {
			storeDao.writeToFile(storeKeepers);
		}catch (IOException ex) {
			System.out.println("Cannot Write to file !");
			ex.printStackTrace();
		}
	}
	
}