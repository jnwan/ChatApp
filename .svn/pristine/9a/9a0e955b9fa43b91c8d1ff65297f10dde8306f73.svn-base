package student.model.data;

import provided.datapacket.ADataPacket;
import comp310f13.rmiChat.IStatusFail;
/**
 * return value when fail.
 * @author Junnan
 *
 */
public class StatusFailImpl implements IStatusFail{

	private static final long serialVersionUID = 677356416112882420L;
	private String msg;
	private ADataPacket pkt;
	@Override
	public String getMsg() {	
		return msg;
	}

	@Override
	public ADataPacket getDataPacket() {
		return pkt;
	}

	public StatusFailImpl(String msg, ADataPacket pkt){
		this.msg = msg;
		this.pkt = pkt;
	}
}
