package student.view;

import comp310f13.rmiChat.IChatRoom;
import comp310f13.rmiChat.IUser;
/**
 * The adapter used to communicate between MiniView and MainModel. 
 * @author Junnan
 *
 */
public interface IMinitoMainAdapter {
    //the method to excute invitetoChatRoom.
	public String invitetoChatRoom(String remoteHost);
	//the method to get user list from MainModel.
	public Iterable<IUser> getUsers();
	//the method to user that make this chatroom.
	public IUser getUser(IChatRoom room);
    //the method to delete chatroom when closing window. 
	public void deleteChatRoom();

}
