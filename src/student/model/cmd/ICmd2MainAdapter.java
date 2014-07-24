package student.model.cmd;

import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import provided.extvisitor.IExtVisitorCmd;
import comp310f13.rmiChat.IChatRoom;
/**
 * the adapter for communicating between Cmd and MainAdapter.
 * @author Junnan
 *
 */
public interface ICmd2MainAdapter {
	public IChatRoom getRoom();
	
	public void updateView();
	
	public void addCmd(Class<?> idx, ADataPacketAlgoCmd<ADataPacket, ?, ?> cmd);
	
	public IExtVisitorCmd<ADataPacket, Class<?>, Void, ADataPacket> getCmd(Class<?> idx);
}
