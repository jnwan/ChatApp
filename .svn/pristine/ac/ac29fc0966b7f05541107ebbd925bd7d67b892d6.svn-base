package student.controller;

import java.util.Set;

import comp310f13.rmiChat.IChatRoom;
import student.model.IMainViewAdapter;
import student.model.MainModel;
import student.view.IMainModelAdapter;
import student.view.MainView;
/**
 * MainController to communicate among MiniController, MainView and MainModel.
 * @author Junnan
 *
 */
public class MainController {
	private MainView view;
	private MainModel model;
	//Constructor of MainController.
	public MainController(){
		//concrete model and view to concrete the methods.
		 model = new MainModel(new IMainViewAdapter(){
				public void append(String s) {
					view.append(s);
				}

				@Override
				public void updateRoomList() {
					view.updateRoomList();
					
				}
		    	 
		     });
	     view = new MainView(new IMainModelAdapter(){

			@Override
			public Set<IChatRoom> getChatRoom() {
				return model.getChatRoom();
			}

			@Override
			public MiniController makeMiniController() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void leaveAllChatRooms() {
				model.leaveAllChatRooms();
				
			}

			@Override
			public void creatChatRoom(String s) {
				model.creatChatRoom(s);
				
			}
	    	 
	     });
	    
	}
	//Start view and model to start the project.
	public void start(){
		view.start();
		model.start();
	}
	//start MainController.
	public static void main(String[] args) {
		(new MainController()).start();
	}

}
