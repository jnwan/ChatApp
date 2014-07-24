package student.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.ComponentOrientation;
import java.rmi.RemoteException;

import javax.swing.JScrollPane;
import javax.swing.JList;

import comp310f13.rmiChat.IUser;
/*
 * Make MiniView as a GUI of a ChatRoom.  
 */
public class MiniView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3723842241357312021L;
	private JPanel contentPane;
	private final JPanel panel = new JPanel();
	private final JSplitPane splitPane = new JSplitPane();
	private IMiniModelAdapter miniModelAdapter;
	private IMinitoMainAdapter minitoMainAdapter;
	private final JLabel lblRemotehost = new JLabel("            RemoteHost:");
	/*
	 * Input the IP Address.
	 */
	private final JTextField IP_textField = new JTextField();
	private final JButton btnInvitetochatroom = new JButton("InviteToChatRoom");
	private final JButton btnSendmessage = new JButton("SendMessage");
	private final JPanel panel_1 = new JPanel();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JScrollPane scrollPane_1 = new JScrollPane();
	private final JTextArea textArea = new JTextArea();
	private final JTextArea textArea_1 = new JTextArea();
	/**
	 * make a JList to display users in current ChatRoom.
	 */
	private final DefaultListModel<String> listModel = new DefaultListModel<String>();
	private final JList<String> userlist = new JList<String>(listModel);
	private final JLabel lblUserList = new JLabel("User List");
	public MiniView(IMinitoMainAdapter minitoMainAdapter,IMiniModelAdapter miniModelAdapter){
    	super("Chatroom");
    	IP_textField.setToolTipText("Input the RemoteHost.");
    	IP_textField.setColumns(10);
    	initGUI();
    	this.miniModelAdapter = miniModelAdapter;
    	this.minitoMainAdapter = minitoMainAdapter;
    }
	/**
	 * Create the frame.
	 * @return 
	 */
	public void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 525, 349);
		textArea.setLineWrap(true);
		textArea_1.setLineWrap(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		splitPane.setDividerLocation(180);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 4, 0, 0));
		lblRemotehost.setToolTipText("RemoteHost");
		lblRemotehost.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		panel.add(lblRemotehost);
		
		panel.add(IP_textField);
		IP_textField.setText("10.101.194.252");
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea_1.setWrapStyleWord(true);
		textArea_1.setLineWrap(true);
		/**
		 * Infine the action of InvitetoChatroom.
		 */
		btnInvitetochatroom.setToolTipText("Invite the IP left to Chat Room.");
		btnInvitetochatroom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				minitoMainAdapter.invitetoChatRoom(IP_textField.getText());
			}
		});

		panel.add(btnInvitetochatroom);
		/**
		 * define the action of sendmessage.
		 */
		btnSendmessage.setToolTipText("Send message to the users in the chat room.");
		btnSendmessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				miniModelAdapter.sendmessage(textArea_1.getText());
				textArea_1.setText(null);
			}
		});
		
		panel.add(btnSendmessage);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		splitPane.setLeftComponent(scrollPane);
		
		scrollPane.setViewportView(textArea);
		
		splitPane.setRightComponent(scrollPane_1);
		
		scrollPane_1.setViewportView(textArea_1);
		
		contentPane.add(panel_1, BorderLayout.EAST);
		lblUserList.setToolTipText("User List");
		
		panel_1.add(lblUserList);
		
		panel_1.add(userlist);
		/**
		 * add the WindowListener to define the action of closing window.
		 */
		this.addWindowListener(new WindowAdapter()
		{
		   public void windowClosing(WindowEvent e)
		   {
			   setVisible(false);
			   miniModelAdapter.removeUser(); //remove local user.
			   minitoMainAdapter.deleteChatRoom(); //delete chat room.
		    }
		});
	}
	public void start() {
		setVisible(true);
	}
	/**
	 * define the action about how to update user list.
	 */
	public void updateUserList(){
		listModel.clear();
		for(IUser user: minitoMainAdapter.getUsers())
			try {
				listModel.addElement(user.getName());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	}
    /**
     * display the string in textArea.
     * @param s
     */
	public void append(String s){
		textArea.append(s+"\n");
	}
}
