package student.model;

import java.rmi.RemoteException;
import java.util.Date;

import provided.datapacket.DataPacket;
import student.model.data.TextMessageImpl;
import student.view.IMinitoMainAdapter;
import comp310f13.rmiChat.IChatRoom;
import comp310f13.rmiChat.ITextMessage;
/**
 * Class MiniModel to concrete the method.
 * @author Junnan
 *
 */
public class MiniModel {
	private IMiniViewAdapter miniViewAdapter;
	private IMinitoMainAdapter minitoMainAdapter;
    private IChatRoom chatroom;
    //constructor of MiniModel.
	public MiniModel(IChatRoom chatroom, IMinitoMainAdapter minitoMainAdapter, IMiniViewAdapter iMiniViewAdapter) {
		this.miniViewAdapter = iMiniViewAdapter;
		this.minitoMainAdapter = minitoMainAdapter;
		this.chatroom = chatroom;
	}
    //concret sendmessage method.
	public void sendmessage(String text) {
		System.out.println(text);
		try {
			chatroom.sendMessage(new DataPacket<ITextMessage>(ITextMessage.class, ((ChatUser)minitoMainAdapter.getUser(chatroom)).getStub(), new TextMessageImpl(minitoMainAdapter.getUser(chatroom).getName(), new Date(), text)));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public void removeUser() {
		
	}

	public void append(String s) {
		miniViewAdapter.append(s);
		
	}
// method to updateuserList.
	public void updateUserList() {
	
		miniViewAdapter.updateUserList();
	}

}
