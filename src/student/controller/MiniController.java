package student.controller;

import javax.swing.JFrame;

import comp310f13.rmiChat.IChatRoom;
import student.model.IMiniViewAdapter;
import student.model.MiniModel;
import student.view.IMiniModelAdapter;
import student.view.IMinitoMainAdapter;
import student.view.MiniView;
/**
 * MiniController to communicate with MainController, MiniView and MiniModel. 
 * @author Junnan
 *
 */
public class MiniController{
	
	public MiniView miniView;
	public MiniModel miniModel;
	public IMinitoMainAdapter minitomainAdapter;
	public MiniController(IChatRoom chatroom, IMinitoMainAdapter minitomainAdapter){
		this.minitomainAdapter = minitomainAdapter;
		this.miniView = new MiniView(minitomainAdapter,new IMiniModelAdapter(){
			@Override
			public void sendmessage(String text) {
				miniModel.sendmessage(text);
				
			}

			@Override
			public void removeUser() {
				miniModel.removeUser();
				
			}
			
		});
		this.miniModel = new MiniModel(chatroom, minitomainAdapter, new IMiniViewAdapter(){

			@Override
			public void append(String s) {
				miniView.append(s);
				
			}

			@Override
			public void updateUserList() {
				miniView.updateUserList();
				
			}
			
		});
		this.miniView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	/**
	 * start the m
	 */
	public void start() {
       miniView.start();
	}

}
