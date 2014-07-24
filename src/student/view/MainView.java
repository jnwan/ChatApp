package student.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import comp310f13.rmiChat.IChatRoom;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;
/**
 * Make MainView as a main GUI of Chat App.
 * @author Junnan
 *
 */
public class MainView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7036068353709935077L;
	private JPanel contentPane;
	private final JPanel panel = new JPanel();
	private final JButton btnCreatChatRoom = new JButton("CreatChatRoom");
	private IMainModelAdapter mainModelAdapter;
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTextArea textArea = new JTextArea();
	private final DefaultListModel<String> listModel = new DefaultListModel<String>();
	private final JList <String> roomlist = new JList<String>(listModel);
	private final JPanel panel_1 = new JPanel();

	private final JLabel lblNewLabel = new JLabel("ChatRoomList");
	private final JLabel lblChatroomname = new JLabel("ChatRoomName:");
	private final JTextField txtAmazzzzingChatRoom = new JTextField();

	/**
	 * Create the frame.
	 */
	public MainView(IMainModelAdapter mainModelAdapter) {
		super("MainGUI");
		txtAmazzzzingChatRoom.setToolTipText("Input the ChatRoom Name you want to create.");
		txtAmazzzzingChatRoom.setText("Amazzzzing Chat Room");
		txtAmazzzzingChatRoom.setColumns(15);
		this.mainModelAdapter = mainModelAdapter;
		initGUI();
	}
	public void initGUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 575, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		contentPane.add(panel, BorderLayout.NORTH);
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		/**
		 * define the action of create chat room.
		 */
		btnCreatChatRoom.setToolTipText("Creat a Chat Room.");
		btnCreatChatRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainModelAdapter.creatChatRoom(txtAmazzzzingChatRoom.getText());
			}
		}); 
		lblChatroomname.setToolTipText("ChatRoomName.");

		panel.add(lblChatroomname);
		
		panel.add(txtAmazzzzingChatRoom);
		
		panel.add(btnCreatChatRoom);
		lblNewLabel.setToolTipText("ChatRoomList");
		
		panel_1.add(lblNewLabel);
		
		panel_1.add(roomlist);
		
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		scrollPane.setViewportView(textArea);
		
		contentPane.add(panel_1, BorderLayout.EAST);
		
		/**
		 * add the WindowListener to define the action of closing window.
		 */
		this.addWindowListener(new WindowAdapter()
		{
		   public void windowClosing(WindowEvent e)
		   {
			   setVisible(false);
			   mainModelAdapter.leaveAllChatRooms();
		    }
		});
	}
	/**
	 * Append the given string(s) to the view's output text adapter.  
	 * @param s the string to display.
	 */
	public void append(String s) {
		textArea.append(s);
		//Force the JScrollPane to go to scroll down to the new text
		textArea.setCaretPosition(textArea.getText().length());
	}
	/**
	 * Starts the view by making it visible.
	 */
	public void start() {

		setVisible(true);
	}
	/**
	 * define the action about how to updateRoomList.
	 */
    public void updateRoomList(){
    	listModel.clear();
    	for(IChatRoom room : mainModelAdapter.getChatRoom()){
			listModel.addElement(room.getName());
		   }
    }
}
