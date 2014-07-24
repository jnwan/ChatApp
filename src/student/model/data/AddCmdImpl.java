package student.model.data;

import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import comp310f13.rmiChat.IAddCmd;
/**
 * class to define the action about how to add cmd!
 * @author Junnan
 *
 */
public class AddCmdImpl implements IAddCmd{

	private static final long serialVersionUID = -1146037519385866110L;

	private Class<?> classtype;
	private ADataPacketAlgoCmd<ADataPacket, ?, ?> cmd;
	
	@Override
	public Class<?> getID() {
		return classtype;
	}

	@Override
	public ADataPacketAlgoCmd<ADataPacket, ?, ?> getNewCmd() {
		return cmd;
	}

	public AddCmdImpl(Class<?> classtype, ADataPacketAlgoCmd<ADataPacket, ?, ?> cmd){
		this.classtype = classtype;
		this.cmd = cmd;
	}
}
