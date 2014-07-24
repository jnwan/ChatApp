package student.model.data;

import comp310f13.rmiChat.IRemoveUser;
import comp310f13.rmiChat.IUser;
/**
 * class to remove user.
 * @author Junnan
 *
 */
public class RemoveUserImpl implements IRemoveUser{

	private static final long serialVersionUID = 8521611340198105494L;

	private IUser user;
	
	@Override
	public IUser getUser() {
		
		return user;
	}
	
	public RemoveUserImpl(IUser user){
		this.user = user;
	}

}
