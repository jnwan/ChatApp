package student.model.data;

import comp310f13.rmiChat.IRequestCmd;
/**
 * define the action when receiving unkown package.
 * @author Junnan
 *
 */
public class RequestCmdImpl implements IRequestCmd{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2451507253985734800L;

	private Class<?> classtype;
	
	public RequestCmdImpl(Class<?> classtype){
		this.classtype = classtype;
	}
	
	@Override
	public Class<?> getID() {
		return classtype;
	}

}
