import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;

public class Test extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 539, 350);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.setIcon(new ImageIcon(Test.class.getResource("/com/sun/java/swing/plaf/windows/icons/TreeOpen.gif")));
		mntmOpen.setHorizontalAlignment(SwingConstants.CENTER);
		mnFile.add(mntmOpen);
		
		JMenuItem mntmOpenRecent = new JMenuItem("Open Recent");
		mntmOpenRecent.setIcon(new ImageIcon(Test.class.getResource("/com/sun/java/swing/plaf/windows/icons/UpFolder.gif")));
		mntmOpenRecent.setHorizontalAlignment(SwingConstants.CENTER);
		mnFile.add(mntmOpenRecent);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.setIcon(new ImageIcon(Test.class.getResource("/com/sun/java/swing/plaf/windows/icons/FloppyDrive.gif")));
		mntmSave.setHorizontalAlignment(SwingConstants.CENTER);
		mnFile.add(mntmSave);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setIcon(new ImageIcon(Test.class.getResource("/javax/swing/plaf/metal/icons/ocean/paletteClose.gif")));
		mntmExit.setHorizontalAlignment(SwingConstants.CENTER);
		mnFile.add(mntmExit);
		
		JMenu mnOpen = new JMenu("Edit");
		mnOpen.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(mnOpen);
		
		JMenuItem mntmEditRunway = new JMenuItem("Edit Runway");
		mntmEditRunway.setHorizontalAlignment(SwingConstants.CENTER);
		mnOpen.add(mntmEditRunway);
		
		JMenuItem mntmEditObstacle = new JMenuItem("Edit Obstacle");
		mntmEditObstacle.setHorizontalAlignment(SwingConstants.CENTER);
		mnOpen.add(mntmEditObstacle);
		
		JMenu mnPrint = new JMenu("Print");
		mnPrint.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(mnPrint);
		
		JMenuItem mntmPrintCalculation = new JMenuItem("Print Calculation");
		mntmPrintCalculation.setHorizontalAlignment(SwingConstants.CENTER);
		mnPrint.add(mntmPrintCalculation);
		
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.setHorizontalAlignment(SwingConstants.CENTER);
		mnHelp.add(mntmAbout);
		
		JMenuItem mntmContact = new JMenuItem("Contact");
		mntmContact.setHorizontalAlignment(SwingConstants.CENTER);
		mnHelp.add(mntmContact);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Airport");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_3.add(scrollPane);
		
		JTextPane txtpnDataWillBe = new JTextPane();
		txtpnDataWillBe.setBorder(new EmptyBorder(6, 6, 6, 6));
		txtpnDataWillBe.setEditable(false);
		scrollPane.setViewportView(txtpnDataWillBe);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel_1.add(tabbedPane);
		
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("Runway 2", null, panel_5, null);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_5.add(panel_4, BorderLayout.WEST);
		panel_4.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel panel_8 = new JPanel();
		panel_4.add(panel_8);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		JLabel lbll = new JLabel("09L");
		lbll.setHorizontalAlignment(SwingConstants.CENTER);
		panel_8.add(lbll, BorderLayout.NORTH);
		
		JPanel panel_10 = new JPanel();
		panel_8.add(panel_10, BorderLayout.CENTER);
		panel_10.setLayout(new BorderLayout(0, 0));
		
		table = new JTable();
		panel_10.add(table, BorderLayout.CENTER);
		
		JPanel panel_9 = new JPanel();
		panel_4.add(panel_9);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		JLabel lblr = new JLabel("27R");
		lblr.setHorizontalAlignment(SwingConstants.CENTER);
		panel_9.add(lblr, BorderLayout.NORTH);
		
		JPanel panel_12 = new JPanel();
		panel_9.add(panel_12, BorderLayout.CENTER);
		panel_12.setLayout(new BorderLayout(0, 0));
		
		table_1 = new JTable();
		panel_12.add(table_1, BorderLayout.CENTER);
		
		JPanel panel_7 = new JPanel();
		panel_5.add(panel_7, BorderLayout.CENTER);
		panel_7.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel panel_16 = new JPanel();
		panel_7.add(panel_16);
		
		JPanel panel_15 = new JPanel();
		panel_7.add(panel_15);
		
		JPanel panel_11 = new JPanel();
		panel_5.add(panel_11, BorderLayout.EAST);
		panel_11.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel panel_13 = new JPanel();
		panel_11.add(panel_13);
		
		JButton btnNewButton = new JButton("Top Down");
		
		JButton btnNewButton_2 = new JButton("Side On");
		
		JButton btnNewButton_1 = new JButton("3D");
		panel_13.setLayout(new GridLayout(3, 0, 0, 0));
		
		panel_13.add(btnNewButton);
		panel_13.add(btnNewButton_2);
		panel_13.add(btnNewButton_1);
		
		JPanel panel_14 = new JPanel();
		panel_11.add(panel_14);
		panel_14.setLayout(new GridLayout(3, 0, 0, 0));
		
		JButton btnTopDown = new JButton("Top Down");
		panel_14.add(btnTopDown);
		
		JButton btnNewButton_3 = new JButton("Side On");
		panel_14.add(btnNewButton_3);
		
		JButton btnd = new JButton("3D");
		panel_14.add(btnd);
		
		JPanel panel_6 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_6, null);
	}

}
