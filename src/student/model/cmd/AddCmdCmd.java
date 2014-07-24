package student.model.cmd;

import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import provided.datapacket.DataPacket;
import provided.datapacket.ICmd2ModelAdapter;
import comp310f13.rmiChat.IAddCmd;
import comp310f13.rmiChat.IStatusOk;
/**
 * cmd about how to add cmd.
 * @author Junnan
 *
 */
public class AddCmdCmd extends ADataPacketAlgoCmd<ADataPacket, IAddCmd, Void>{

	private static final long serialVersionUID = 1220893841646566245L;
	private transient ICmd2ModelAdapter _cmd2ModelAdpt;
	private transient ICmd2MainAdapter _cmd2MainAdpt;
	//method to add cmd.
	@Override
	public ADataPacket apply(Class<?> index, DataPacket<IAddCmd> host, Void... params) {
		
		_cmd2MainAdpt.addCmd(host.getData().getID(), host.getData().getNewCmd());
		return new DataPacket<IStatusOk>(IStatusOk.class, _cmd2ModelAdpt.getLocalUserStub(), new IStatusOk(){

			private static final long serialVersionUID = 7575385553613351672L;});
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
	
	public AddCmdCmd(){
		
	}
}