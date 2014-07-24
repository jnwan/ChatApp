package student.model.cmd;

import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import provided.datapacket.DataPacket;
import provided.datapacket.ICmd2ModelAdapter;
import comp310f13.rmiChat.IAddUser;
import comp310f13.rmiChat.IStatusOk;
/**
 * cmd about how to add user.
 */
public class AddUserCmd extends ADataPacketAlgoCmd<ADataPacket, IAddUser, Void>{

	private static final long serialVersionUID = 7116031602767120615L;
	private transient ICmd2ModelAdapter _cmd2ModelAdpt;
	private transient ICmd2MainAdapter _cmd2MainAdpt;
	//method to add user.
	@Override
	public ADataPacket apply(Class<?> index, DataPacket<IAddUser> host, Void... params) {
		
		_cmd2MainAdpt.getRoom().addLocalUser(host.getData().getUser());
		_cmd2MainAdpt.updateView();
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
	
	public AddUserCmd(){
		
	}

}
