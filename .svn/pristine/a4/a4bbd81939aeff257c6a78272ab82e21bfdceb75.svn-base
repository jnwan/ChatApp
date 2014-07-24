package student.model.cmd;

import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import provided.datapacket.DataPacket;
import provided.datapacket.ICmd2ModelAdapter;
import student.model.data.AddCmdImpl;

import comp310f13.rmiChat.IAddCmd;
import comp310f13.rmiChat.IRequestCmd;
/**
 * class to define action about how to request cmd.
 * @author Junnan
 *
 */
public class RequestCmdCmd extends ADataPacketAlgoCmd<ADataPacket, IRequestCmd, Void>{

	private static final long serialVersionUID = 870950209469845339L;
	private transient ICmd2ModelAdapter _cmd2ModelAdpt;
	private transient ICmd2MainAdapter _cmd2MainAdpt;
	
	@SuppressWarnings("unchecked")
	@Override
	public ADataPacket apply(Class<?> index, DataPacket<IRequestCmd> host, Void... params) {
		
		Class<?> idx = host.getData().getID();

		return new DataPacket<IAddCmd>(IAddCmd.class, _cmd2ModelAdpt.getLocalUserStub(), new AddCmdImpl(idx, (ADataPacketAlgoCmd<ADataPacket, ?, ?>) _cmd2MainAdpt.getCmd(idx)));

			
	}
    //set cmd to model adapter.
	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		_cmd2ModelAdpt = cmd2ModelAdpt;
		
	}
	//set cmd to main adapter.
	public void setCmd2MainAdpt(ICmd2MainAdapter _cmd2MainAdpt){
		this._cmd2MainAdpt = _cmd2MainAdpt;
	}
	
	public RequestCmdCmd(){
		
	}
}
