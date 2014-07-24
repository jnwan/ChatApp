package student.view;

import java.util.Set;

import comp310f13.rmiChat.IChatRoom;
import student.controller.MiniController;
/**
 * the adapter for communicating between MainView and MainModel.
 * @author Junnan
 *
 */
public interface IMainModelAdapter {
    /**
     * method to make MiniController(Chatroom)
     * @return
     */
	public MiniController makeMiniController();
	/**
	 * method to transport roomlist to MainView.
	 * @return
	 */
	public Set<IChatRoom> getChatRoom();
    /**
     * method to create chat room.
     * @param s 
     */
	public void creatChatRoom(String s);
	/**
	 * method to leave all chatrooms.
	 */
	public void leaveAllChatRooms();

}
