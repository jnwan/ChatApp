package student.model;

import java.rmi.RemoteException;
import java.util.UUID;

import javax.swing.JOptionPane;

import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import provided.datapacket.DataPacket;
import provided.datapacket.DataPacketAlgo;
import provided.datapacket.ICmd2ModelAdapter;
import comp310f13.rmiChat.IChatRoom;
import comp310f13.rmiChat.IUser;
/**
 * concrete class to implements IUser.
 * @author Junnan
 *
 */
public class ChatUser implements IUser{

	private transient UUID userID;
	private transient DataPacketAlgo<ADataPacket,Void> algo;
	private transient IChatRoom room;
	private transient IUser stub;
    private String name;
	/**
	 * @return the algo
	 */
	public DataPacketAlgo<ADataPacket, Void> getAlgo() {
		return algo;
	}

	/**
	 * @param algo the algo to set
	 */
	public void setAlgo(DataPacketAlgo<ADataPacket, Void> algo) {
		this.algo = algo;
	}

	@Override
	public String getName() throws RemoteException {
		return name;
	}

	@Override
	public UUID getUUID() throws RemoteException {
		return userID;
	}

	@Override
	public ADataPacket receiveData(ADataPacket dp)
			throws RemoteException {
		return dp.execute(algo, new Void[0]);
	}
    //Constructor of ChatUser.
	public ChatUser(UUID ID, IChatRoom room){
		name = JOptionPane.showInputDialog(null,"Input your user name!");
		if(name == null) name = "Default Name";
		userID = ID;
		this.setRoom(room);
		algo = new DataPacketAlgo<ADataPacket,Void>(new ADataPacketAlgoCmd<ADataPacket, Object, Void>(){


			/**
			 * 
			 */
			private static final long serialVersionUID = 9074804678813177904L;

			@Override
			public ADataPacket apply(Class<?> index, DataPacket<Object> host,
					Void... params) {
				return null;
			}

			@Override
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {

				
			}
			
		});
	}

	public IChatRoom getRoom() {
		return room;
	}

	public void setRoom(IChatRoom room) {
		this.room = room;
	}

	public IUser getStub() {
		return stub;
	}

	public void setStub(IUser stub) {
		this.stub = stub;
	}
}
