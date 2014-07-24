package student.model.cmd;

import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import provided.datapacket.DataPacket;
import provided.datapacket.ICmd2ModelAdapter;

import comp310f13.rmiChat.IStatusOk;
import comp310f13.rmiChat.ITextMessage;
/**
 * Class TextMessageCmd to propose sending and receiving text.
 * @author Junnan
 *
 */
public class TextMessageCmd  extends ADataPacketAlgoCmd<ADataPacket, ITextMessage, Void>{

	private static final long serialVersionUID = -1070264239819098216L;
	private transient ICmd2ModelAdapter _cmd2ModelAdpt;
	/**
	 * method about how to propose textmessage.
	 */
	@Override
	public ADataPacket apply(Class<?> index, DataPacket<ITextMessage> host, Void... params) {
		
		String s = host.getData().getName() + ": " + host.getData().getTime().toString() + "\n" + host.getData().getMsg();
		_cmd2ModelAdpt.append(s);
		
		return new DataPacket<IStatusOk>(IStatusOk.class, _cmd2ModelAdpt.getLocalUserStub(), new IStatusOk(){

			private static final long serialVersionUID = 7575385553613351672L;});
	}
    //set cmd to model adapter.
	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		_cmd2ModelAdpt = cmd2ModelAdpt;
		
	}
	
	public TextMessageCmd (){
		
	}
}
