package student.model;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;

import provided.datapacket.ADataPacket;
import comp310f13.rmiChat.IChatRoom;
import comp310f13.rmiChat.IUser;
/**
 * concrete class to implements IChatRoom.
 * @author Junnan
 *
 */
public class ChatRoom implements IChatRoom{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8889340129957225338L;
	private Set<ProxyUser> userList;
	private String name;
	public ChatRoom(String s){
		this.userList = new HashSet<ProxyUser>();
		name = s;
	}
	public String getName() {
		return name;
	}

	public void addLocalUser(IUser newUserStub) {
		userList.add(new ProxyUser(newUserStub));
	}

	public void removeLocalUser(IUser userStub) {
		ProxyUser puser = null;
		for(ProxyUser user : userList){
			try {
				if(user.getUUID().equals(userStub.getUUID())){
					puser = user;
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(puser != null)
			userList.remove(puser);
	}

	public Iterable<IUser> getUsers() {
		Set<IUser> userList1 = new HashSet<IUser>();
		for(IUser user : userList)
			userList1.add(user);
		return userList1;
	}

	public Iterable<ADataPacket> sendMessage(ADataPacket dp) {
		Set<ADataPacket> dataPacketList = new HashSet<ADataPacket>();
		for(IUser user : userList ){
			try {
				dataPacketList.add(user.receiveData(dp));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return dataPacketList;
	}
	

}
