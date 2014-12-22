package run;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

import setting.MainSetting;
import base.DownloadAbstract;
import base.DownloadFactory;
import common.Message;

import java.awt.Font;

import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import javax.swing.border.LineBorder;
import java.awt.Color;



public class Main {

	private JFrame frmDownload;
	private JTextField txtPath;
	private JTextField txtPage;
	private JList<String> typeList;
	private static final String[] DOWNLOAD_MANAGER_TYPE=new String[]{
		"Earth"
	};
	private JTextField txtFolder;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			Main window=null;
			public void run() {
				try {
					window= new Main();
					window.frmDownload.setVisible(true);
				} catch (Exception e) {
					if(window !=null)
					Message.showMessage(window.frmDownload, "Đã có lỗi xảy ra: "+e.getMessage());
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
		init();
	}
	private void init(){
		txtPage.setText("0");
		typeList.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = DOWNLOAD_MANAGER_TYPE;
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDownload = new JFrame();
		frmDownload.setTitle("Download");
		frmDownload.setBounds(100, 100, 591, 407);
		frmDownload.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDownload.getContentPane().setLayout(null);
		
		JLabel lblPath = new JLabel("Path");
		lblPath.setBounds(20, 37, 46, 14);
		frmDownload.getContentPane().add(lblPath);
		
		txtPath = new JTextField();
		txtPath.setBounds(86, 34, 454, 20);
		frmDownload.getContentPane().add(txtPath);
		txtPath.setColumns(10);
		
		JButton btnDownload = new JButton("Download");
		btnDownload.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				run();
			}
		});
		btnDownload.setBounds(228, 301, 145, 29);
		frmDownload.getContentPane().add(btnDownload);
		
		JLabel lblPage = new JLabel("Page");
		lblPage.setBounds(20, 62, 46, 14);
		frmDownload.getContentPane().add(lblPage);
		
		txtPage = new JTextField();
		
		txtPage.setBounds(86, 59, 55, 20);
		frmDownload.getContentPane().add(txtPage);
		txtPage.setColumns(10);
		
		 typeList = new JList<String>();
		 typeList.setBackground(Color.BLACK);
		 typeList.setBorder(new LineBorder(new Color(0, 0, 0)));
		 typeList.setVisibleRowCount(10);
		 typeList.setToolTipText("Select Type");
		 typeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		typeList.setBounds(86, 148, 376, -40);
		frmDownload.getContentPane().add(typeList);
		
		JLabel lblType = new JLabel("Type");
		lblType.setBounds(20, 94, 46, 14);
		frmDownload.getContentPane().add(lblType);
		
		JLabel lblFolder = new JLabel("Folder");
		lblFolder.setBounds(20, 180, 46, 14);
		frmDownload.getContentPane().add(lblFolder);
		
		txtFolder = new JTextField();
		txtFolder.setBounds(90, 177, 450, 20);
		frmDownload.getContentPane().add(txtFolder);
		txtFolder.setColumns(10);
	}

	private void run() {
		try{
//			MainSetting setting= getSetting();
//			if(setting==null){
//				return;
//			}
			MainSetting setting=new MainSetting();
			setting.setDirect(false);
			setting.setPath(txtPath.getText().trim());
			setting.setFolder(txtFolder.getText().trim());
			
			DownloadAbstract download =DownloadFactory.getDownloadManager("Earth");
			download.setSetting(setting);
			download.run();
			
		}catch(Exception e){
			e.printStackTrace();
			Message.showMessage(frmDownload, "Đã có lỗi xảy ra: "+e.getMessage());
		}
		
	}
	private MainSetting getSetting(){
		String path=txtPath.getText().trim();
		if("".equals(path)){
			Message.showMessage(frmDownload, "Path rỗng");
			return null;
		}
		String pageStr=txtPage.getText().trim();
		if("".equals(pageStr)){
			Message.showMessage(frmDownload, "Page rỗng");
			return null;
		}
		if(typeList.getSelectedIndex() < 0){
			Message.showMessage(frmDownload, "Phải chọn loại download");
			return null; 
		}
		String type=typeList.getSelectedValue();
		return new MainSetting(path, pageStr, type, true);
	}
}
