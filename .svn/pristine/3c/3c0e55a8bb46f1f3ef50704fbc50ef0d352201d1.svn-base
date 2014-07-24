package student.model.cmd;

import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import provided.datapacket.DataPacket;
import provided.datapacket.ICmd2ModelAdapter;

import comp310f13.rmiChat.IRemoveUser;
import comp310f13.rmiChat.IStatusOk;
/**
 * cmd about how to remove user.
 * @author Junnan
 *
 */
public class RemoveUserCmd extends ADataPacketAlgoCmd<ADataPacket, IRemoveUser, Void>{

	private static final long serialVersionUID = 9085561789505622950L;
	private transient ICmd2ModelAdapter _cmd2ModelAdpt;
	private transient ICmd2MainAdapter _cmd2MainAdpt;
	/*
	 * method about how to remove user.(non-Javadoc)
	 * @see provided.datapacket.ADataPacketAlgoCmd#apply(java.lang.Class, provided.datapacket.DataPacket, java.lang.Object[])
	 */
	@Override
	public ADataPacket apply(Class<?> index, DataPacket<IRemoveUser> host, Void... params) {
		
		_cmd2MainAdpt.getRoom().removeLocalUser(host.getData().getUser());
		_cmd2MainAdpt.updateView();
		return new DataPacket<IStatusOk>(IStatusOk.class, _cmd2ModelAdpt.getLocalUserStub(), new IStatusOk(){

			private static final long serialVersionUID = 7575385553613351672L;});
	}
    // set cmd to model adapter.
	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		_cmd2ModelAdpt = cmd2ModelAdpt;
		
	}
	// set cmd to mainAdapter.
	public void setCmd2MainAdpt(ICmd2MainAdapter _cmd2MainAdpt){
		this._cmd2MainAdpt = _cmd2MainAdpt;
	}
	
	public RemoveUserCmd(){
		
	}

}