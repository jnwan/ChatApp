package student.model.data;

import java.util.Date;

import comp310f13.rmiChat.ITextMessage;
/**
 * class TextMessageImpl to make construction of text message.
 * @author Junnan
 *
 */
public class TextMessageImpl  implements ITextMessage{
	
	private static final long serialVersionUID = -1247201491183814400L;
	private String name;
	private Date time;
	private String msg;

	@Override
	public String getName() {
		
		return name;
	}

	@Override
	public Date getTime() {
		return time;
	}

	@Override
	public String getMsg(){
		return msg;
	}
    //constructor of the class.
	public TextMessageImpl(String name, Date time, String msg){
		this.name = name;
		this.time = time;
		this.msg = msg;
	}
}
