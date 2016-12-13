package sg.edu.nus.iss.store;

import sg.edu.nus.iss.exceptions.BadValueException;

public class StoreKeeper {
	
	private String storeKeeperName;
	private String password;
	
		public StoreKeeper(String storeKeeperName, String password) throws BadValueException
		{
			String error = null;
			if(storeKeeperName == null) 
				error = "Store keeper name is null";
			else if (password == null)
				error = "Password is null";
			if (error != null)
				throw new BadValueException(error);
			this.storeKeeperName = storeKeeperName;
			this.password = password;
		}

		public String getStoreKeeperName() {
			return storeKeeperName;
		}

		public String getPassword() {
			return password;
		}

		public String toString()
		{
			return this.storeKeeperName + " " + this.password;
		}

}
