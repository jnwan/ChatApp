package student.model;

import java.awt.Component;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.swing.JOptionPane;

import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import provided.datapacket.DataPacket;
import provided.datapacket.ICmd2ModelAdapter;
import provided.extvisitor.IExtVisitorCmd;
import provided.rmiUtils.IRMIUtils;
import provided.rmiUtils.IRMI_Defs;
import provided.rmiUtils.RMIUtils;
import provided.util.IVoidLambda;
import student.controller.MiniController;
import student.model.cmd.AddCmdCmd;
import student.model.cmd.AddUserCmd;
import student.model.cmd.ICmd2MainAdapter;
import student.model.cmd.RemoveUserCmd;
import student.model.cmd.RequestCmdCmd;
import student.model.cmd.TextMessageCmd;
import student.model.data.AddUserImpl;
import student.model.data.RemoveUserImpl;
import student.model.data.RequestCmdImpl;
import student.view.IMinitoMainAdapter;
import comp310f13.rmiChat.IAddCmd;
import comp310f13.rmiChat.IAddUser;
import comp310f13.rmiChat.IChatRoom;
import comp310f13.rmiChat.IHost;
import comp310f13.rmiChat.IRemoveUser;
import comp310f13.rmiChat.IRequestCmd;
import comp310f13.rmiChat.IStatusOk;
import comp310f13.rmiChat.ITextMessage;
import comp310f13.rmiChat.IUser;
/**
 * MainModel of the project, many concrete methods defined were here.
 * @author Junnan
 *
 */
public class MainModel {
	private IHost localHostStub = null;

	/**
	 * output command used to put multiple strings up onto the view.
	 */
	private IVoidLambda<String> outputCmd = new IVoidLambda<String>() {

		public void apply(String... params) {
			for (String s : params) {
				_mainViewAdpt.append(s + "\n");
			}
		}
	};


	/**
	 * Factory for the Registry and other uses.
	 */
	IRMIUtils rmiUtils = new RMIUtils(outputCmd);
  /**
   * Make minicontroller to create chatroom.
   * @param chatroom
   * @return
   */
	public MiniController makeMiniController(final IChatRoom chatroom) {
		return new MiniController(chatroom, new IMinitoMainAdapter(){
			public String invitetoChatRoom(String remoteHost) {
				return inviteToChatRoom(remoteHost,chatroom);
			}

			@Override
			public Iterable<IUser> getUsers() {
				
				return chatroom.getUsers();
			}

			@Override
			public IUser getUser(IChatRoom room) {
				return roomList.get(room);
			}

			@Override
			public void deleteChatRoom() {
				RemoveFromChatRoom(chatroom);
				
			}
		});

	}
	/**
	 * remove user from chatroom when leaving.
	 * @param chatroom
	 */
	public void RemoveFromChatRoom(IChatRoom chatroom){
		for(IUser user : chatroom.getUsers()){
			try {
				if(!user.getUUID().equals(machineID))
				user.receiveData(new DataPacket<IRemoveUser>(IRemoveUser.class, ((ChatUser)(roomList.get(chatroom))).getStub(), (IRemoveUser)(new RemoveUserImpl(((ChatUser)(roomList.get(chatroom))).getStub()))));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
       
	   roomList.remove(chatroom);
	   _mainViewAdpt.updateRoomList();
	}
	
	public void leaveAllChatRooms(){
		Object[] rooms = roomList.keySet().toArray();
		for(Object room : rooms){
			RemoveFromChatRoom((IChatRoom)room);
		}
	}
	/**
	 * create chatroom.
	 * @param s
	 */
	public void creatChatRoom(String s){
		
		try {
			localHost.addToChatRoom(makeChatRoom(s));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	public IChatRoom makeChatRoom(String s) throws RemoteException{
		ChatRoom room = new ChatRoom(s);
		
		return room;
		
	}
	private ArrayList<IHost> hostList;

	private IHost localHost;

	private Map<IChatRoom, IUser> roomList;

	private IMainViewAdapter _mainViewAdpt;

	private UUID machineID;
	
	/**
	 * The RMI Registry
	 */
	private Registry registry;
	/**
	 * constructor of MainModel. 
	 * @param _viewadpt
	 */
	public MainModel(IMainViewAdapter _viewadpt){
		this.hostList = new ArrayList<IHost>();
		this._mainViewAdpt = _viewadpt;
		this.roomList = new HashMap<IChatRoom, IUser>();
		this.machineID = UUID.randomUUID();
		localHost = new IHost(){
            //sendLocalHostStub.
			public void sendLocalHostStub(IHost localHostStub)
					throws RemoteException {
				hostList.add(localHostStub);

			}
           //sendInvite.
			public boolean sendInvite(String chatroomInfo)
					throws RemoteException {
				String information = "Do you want to accept the invitation from "+ chatroomInfo + "?";
				 int n = JOptionPane.showConfirmDialog(null, information,  
                        information, JOptionPane.YES_NO_OPTION);
                   if(n == JOptionPane.YES_OPTION) return true;
				   return false;
			}
            //addToChatRoom.
			public boolean addToChatRoom(final IChatRoom localChatRoom) throws RemoteException {
				boolean sign = true;
				for(IUser user : localChatRoom.getUsers()){
					if(user.getUUID().equals(machineID) ){
						sign = false;
						break;
					}
				}
				if(sign == false){
					return false;
				}
				final MiniController miniController = makeMiniController(localChatRoom);
				final ChatUser newUser = new ChatUser(machineID, localChatRoom);
				final IUser newUserStub = (IUser) UnicastRemoteObject.exportObject(newUser, IUser.CONNECTION_PORT);
				newUser.setStub(newUserStub);
				final ICmd2ModelAdapter cmd2ModelAdpt = new ICmd2ModelAdapter(){
					@Override
					public IUser getLocalUserStub() {
						return newUserStub;
					}
					@Override
					public void append(String s) {
						miniController.miniModel.append(s);
						
					}
					@Override
					public void addComponent(String name, Component newComp) {
						
					}
					
				};
				
				ICmd2MainAdapter cmd2MainAdpt = new ICmd2MainAdapter(){

					@Override
					public IChatRoom getRoom() {
						return localChatRoom;
					}

					@Override
					public void updateView() {
						miniController.miniModel.updateUserList();
						
					}
                    //method to addcmd.
					@SuppressWarnings("unchecked")
					@Override
					public void addCmd(Class<?> idx,
							ADataPacketAlgoCmd<ADataPacket, ?, ?> cmd) {
						cmd.setCmd2ModelAdpt(cmd2ModelAdpt);
						newUser.getAlgo().setCmd(idx, (IExtVisitorCmd<ADataPacket, Class<?>, Void, ADataPacket>) cmd);
					}
                    //method to getcmd.
					@Override
					public IExtVisitorCmd<ADataPacket, Class<?>, Void, ADataPacket> getCmd(
							Class<?> idx) {
						return newUser.getAlgo().getCmd(idx);
					}

					
				};
				
				//define defaultcmd.
				ADataPacketAlgoCmd<ADataPacket, Object, Void> defaultCmd = new ADataPacketAlgoCmd<ADataPacket, Object, Void>(){

					private static final long serialVersionUID = -3191258040357387770L;

					private transient ICmd2ModelAdapter _cmd2ModelAdpt;
					
					@Override
					public ADataPacket apply(Class<?> index, DataPacket<Object> host, Void... params) {
						
						try {
							ADataPacket returnPacket = host.getSender().receiveData(new DataPacket<IRequestCmd>(IRequestCmd.class, _cmd2ModelAdpt.getLocalUserStub(), (IRequestCmd)new RequestCmdImpl(index)));
							returnPacket.execute(newUser.getAlgo(), new Void[0]);
							host.execute(newUser.getAlgo(), new Void[0]);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return new DataPacket<IStatusOk>(IStatusOk.class, _cmd2ModelAdpt.getLocalUserStub(), new IStatusOk(){

							private static final long serialVersionUID = 7575385553613351672L;});
					
						//return new DataPacket<IStatusFail>(IStatusFail.class, _cmd2ModelAdpt.getLocalUserStub(), new StatusFailImpl("Cannot find command for " + index.toString(), host));
					}

					@Override
					public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
						_cmd2ModelAdpt = cmd2ModelAdpt;
						
					}
					
				};
				//excute kinds of cmd.
				defaultCmd.setCmd2ModelAdpt(cmd2ModelAdpt);
				newUser.getAlgo().setDefaultCmd(defaultCmd);
				
				AddUserCmd addUserCmd = new AddUserCmd();
				addUserCmd.setCmd2ModelAdpt(cmd2ModelAdpt);
				addUserCmd.setCmd2MainAdpt(cmd2MainAdpt);
				newUser.getAlgo().setCmd(IAddUser.class, addUserCmd);
				
				RemoveUserCmd removeUserCmd = new RemoveUserCmd();
				removeUserCmd.setCmd2MainAdpt(cmd2MainAdpt);
				removeUserCmd.setCmd2ModelAdpt(cmd2ModelAdpt);
				newUser.getAlgo().setCmd(IRemoveUser.class, removeUserCmd);

				TextMessageCmd textMessageCmd = new TextMessageCmd();
				textMessageCmd.setCmd2ModelAdpt(cmd2ModelAdpt);
				newUser.getAlgo().setCmd(ITextMessage.class, textMessageCmd);
			
				AddCmdCmd addCmdCmd = new AddCmdCmd();
				addCmdCmd.setCmd2MainAdpt(cmd2MainAdpt);
				addCmdCmd.setCmd2ModelAdpt(cmd2ModelAdpt);
				newUser.getAlgo().setCmd(IAddCmd.class, addCmdCmd);
				
				RequestCmdCmd requestCmdCmd = new RequestCmdCmd();
				requestCmdCmd.setCmd2MainAdpt(cmd2MainAdpt);
				requestCmdCmd.setCmd2ModelAdpt(cmd2ModelAdpt);
				newUser.getAlgo().setCmd(IRequestCmd.class, requestCmdCmd);
				
				for(IUser user : localChatRoom.getUsers()){
					user.receiveData(new DataPacket<IAddUser>(IAddUser.class, newUserStub, (IAddUser)(new AddUserImpl(newUserStub))));
				}
				localChatRoom.addLocalUser(newUserStub);
				cmd2MainAdpt.updateView();
				roomList.put(localChatRoom, newUser);
				_mainViewAdpt.updateRoomList();
				miniController.start();
				
				return true;
			}

			public String getName() throws RemoteException {
				return "Host: jw50";
			}

			public UUID getUUID() throws RemoteException {
				return machineID;
			}

		};
	}
	// method to getChatRoom.
	public Set<IChatRoom> getChatRoom(){
		return roomList.keySet();
	}
	//method to inviteToChatRoom.
	public String inviteToChatRoom(String remoteHost, IChatRoom room) {
		try {
			_mainViewAdpt.append("Locating registry at " + remoteHost + "...\n");
			//Registry registry = registryFac.getRemoteRegistry(remoteHost);
			Registry registry = rmiUtils.getRemoteRegistry(remoteHost);
			_mainViewAdpt.append("Found registry: " + registry + "\n");
			IHost remoteHostStub = (IHost) registry.lookup(IHost.BOUND_NAME);
			_mainViewAdpt.append("Found remote Compute object \n");
			hostList.add(remoteHostStub);
			if(null==localHostStub){  // Don't re-export clientTA if already done.
				localHostStub = (IHost) UnicastRemoteObject
					.exportObject(localHost, IHost.CONNECTION_PORT);
			}
			remoteHostStub.sendLocalHostStub(localHostStub);
			_mainViewAdpt.append("Sent local host stub\n");
			boolean signal = remoteHostStub.sendInvite("Invitation from room " + room.getName());
			//System.out.println(signal);
			
			if(signal){
				
				remoteHostStub.addToChatRoom(room);
				_mainViewAdpt.append("Chat Room is Sent to remote host\n");
				
			}


		} catch (Exception e) {
			_mainViewAdpt.append("Exception connecting to " + remoteHost + ": " + e
					+ "\n");
			e.printStackTrace();
			return "No connection established!";
		}
		return "Connection to " + remoteHost + " established!";
	}

	/**
	 * Start the server by setting the necessary RMI system parameters, starting the security manager, 
	 * locating the local Registry and binding an instance of the ComputeEngine to it.  
	 * Also starts the class file server to enable remote dynamic class loading.
	 */
	public void start() {
		rmiUtils.startRMI(IRMI_Defs.CLASS_SERVER_PORT_SERVER);

		try {

			// Use this technique rather than the simpler "registry.rebind(name, engine);"
			// because it enables us to specify a port number so we can open that port on the firewall
			localHostStub =(IHost) UnicastRemoteObject.exportObject(localHost, IHost.CONNECTION_PORT);
			_mainViewAdpt.append("Looking for registry..."+"\n");

			registry = rmiUtils.getLocalRegistry();

			_mainViewAdpt.append("Found registry: "+ registry+ "\n");
			registry.rebind(IHost.BOUND_NAME, localHostStub);
			_mainViewAdpt.append("ComputeEngine bound to "+IHost.BOUND_NAME+"\n");
		} 
		catch (Exception e) {
			System.err.println("ComputeEngine exception:"+"\n");
			e.printStackTrace();
			System.exit(-1);
		}

		_mainViewAdpt.append("Waiting..."+"\n");

	}

	/**
	 * Stops the EngineModel by unbinding the ComputeEngine from the Registry 
	 * and stopping class file server. 
	 */
	public void stop() {
		try {
			registry.unbind(IHost.BOUND_NAME);
			System.out.println("EngineController: " + IHost.BOUND_NAME
					+ " has been unbound.");

			rmiUtils.stopRMI();

			System.exit(0);
		} catch (Exception e) {
			System.err.println("EngineController: Error unbinding "
					+ IHost.BOUND_NAME + ":\n" + e);
			System.exit(-1);
		}
	}

}
