package student.model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.UUID;

import provided.datapacket.ADataPacket;
import comp310f13.rmiChat.IUser;

/**
 * Private class to decorate an IUser to override the equals() and hashCode() 
 * methods so that a dictionary, e.g. Hashtable, can properly compare IUsers.
 * @author swong
 *
 */
public class ProxyUser implements IUser, Serializable {
	
	/**
	 * Required for proper serialization
	 */
	private static final long serialVersionUID = 5682755540794448769L; // regenerate this!
	
	/**
	 * The decoree
	 */
	private IUser stub;

	/**
	 * Constructor for the class
	 * @param stub The decoree
	 */
	public ProxyUser(IUser stub){
		this.stub = stub;
	}
	
	/**
	 * No-op decoration of the getName method.   Just pass the request to the decoree.
	 * @return The name of the user.
	 * @throws RemoteException
	 */
	@Override
	public String getName() throws RemoteException {
		return stub.getName();
	}

	/**
	 * Get the decoree
	 * @return the decoree
	 */
	public IUser getStub() {
		return stub;
	}


	/**
	 * Overriden equals() method to simply compare hashCodes.
	 * @return  Equal if the hashCodes are the same.  False otherwise.
	 */
	@Override
	public boolean equals(Object other){
		try {
			return getUUID() == ((IUser)other).getUUID();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	

	/**
	 * Overriden hashCode() method to create a hashcode from all the accessible values in IUser.
	 * @return a hashCode tied to the values of this IUser.	
	 */
	@Override
	public int hashCode(){
		try {
			// Only name is available for now.
			return stub.getUUID().hashCode();
		} catch (RemoteException e) {
			// Deal with the exception without throwing a RemoteException.
			System.err.println("ProxyStub.hashCode(): Error calling remote method on IUser stub: "+e);
			e.printStackTrace();
			return super.hashCode();
		}
	}

	@Override
	public UUID getUUID() throws RemoteException {
		return stub.getUUID();
	}

	@Override
	public ADataPacket receiveData(ADataPacket dp) throws RemoteException {
		return stub.receiveData(dp);
	}
	
}