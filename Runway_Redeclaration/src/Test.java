import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.GridLayout;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.Font;


public class Test {

	private JFrame frmRunwayRedeclarationTool;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test window = new Test();
					window.frmRunwayRedeclarationTool.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRunwayRedeclarationTool = new JFrame();
		frmRunwayRedeclarationTool.setTitle("Runway Redeclaration Tool");
		BorderLayout borderLayout = (BorderLayout) frmRunwayRedeclarationTool.getContentPane().getLayout();
		frmRunwayRedeclarationTool.setBounds(100, 100, 549, 442);
		frmRunwayRedeclarationTool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmRunwayRedeclarationTool.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_6 = new JPanel();
		panel_4.add(panel_6, BorderLayout.NORTH);
		
		JLabel lblRunway = new JLabel("Runway");
		panel_6.add(lblRunway);
		
		JLabel lblX = new JLabel("X");
		panel_6.add(lblX);
		
		JPanel panel_7 = new JPanel();
		panel_4.add(panel_7, BorderLayout.CENTER);
		panel_7.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_8 = new JPanel();
		panel_5.add(panel_8, BorderLayout.NORTH);
		
		JLabel lblRunway_1 = new JLabel("Runway");
		panel_8.add(lblRunway_1);
		
		JLabel lblY = new JLabel("Y");
		panel_8.add(lblY);
		
		JPanel panel_9 = new JPanel();
		panel_5.add(panel_9, BorderLayout.CENTER);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_12 = new JPanel();
		panel_2.add(panel_12);
		panel_12.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel_12.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel_14 = new JPanel();
		tabbedPane.addTab("Top Down", null, panel_14, null);
		tabbedPane.setBackgroundAt(0, Color.WHITE);
		panel_14.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_15 = new JPanel();
		tabbedPane.addTab("Side On", null, panel_15, null);
		panel_15.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_13 = new JPanel();
		panel_2.add(panel_13);
		panel_13.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		panel_13.add(tabbedPane_1, BorderLayout.CENTER);
		
		JPanel panel_16 = new JPanel();
		tabbedPane_1.addTab("Top Down", null, panel_16, null);
		panel_16.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_17 = new JPanel();
		tabbedPane_1.addTab("Side On", null, panel_17, null);
		panel_17.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_10 = new JPanel();
		panel_3.add(panel_10, BorderLayout.NORTH);
		
		JLabel lblLog = new JLabel("Log");
		panel_10.add(lblLog);
		
		JPanel panel_11 = new JPanel();
		panel_3.add(panel_11, BorderLayout.CENTER);
		panel_11.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_11.add(scrollPane, BorderLayout.CENTER);
		
		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("Verdana", Font.PLAIN, 14));
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		JMenuBar menuBar = new JMenuBar();
		frmRunwayRedeclarationTool.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.setIcon(new ImageIcon(Test.class.getResource("/com/sun/java/swing/plaf/windows/icons/Directory.gif")));
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.setIcon(new ImageIcon(Test.class.getResource("/com/sun/java/swing/plaf/windows/icons/FloppyDrive.gif")));
		mnFile.add(mntmSave);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnAddComponent = new JMenu("Add Component");
		menuBar.add(mnAddComponent);
		
		JMenuItem mntmAddAirport = new JMenuItem("Add Airport");
		mnAddComponent.add(mntmAddAirport);
		
		JMenuItem mntmAddRunway = new JMenuItem("Add Runway");
		mnAddComponent.add(mntmAddRunway);
		
		JMenuItem mntmAddObstacle = new JMenuItem("Add Obstacle");
		mnAddComponent.add(mntmAddObstacle);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem menuItem_1 = new JMenuItem("Contact");
		mnHelp.add(menuItem_1);
		
		JMenuItem menuItem = new JMenuItem("About");
		mnHelp.add(menuItem);
	}

}
