package view;

import io.CustomFilter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import view.createEditForms.FormAirport;
import view.createEditForms.FormCreateAirfield;
import view.createEditForms.FormEditAirfield;
import view.createEditForms.FormEditObstacle;
import view.createEditForms.FormObstacle;
import view.createLoadSaveListeners.LoadAirportListener;
import view.createLoadSaveListeners.LoadObstacleListener;
import view.createLoadSaveListeners.SaveAirportAsListener;
import view.createLoadSaveListeners.SaveAirportListener;
import view.createLoadSaveListeners.SaveObjectListener;
import view.createLoadSaveListeners.SaveObstacleAsListener;
import view.customComponents.SelfCheckingField;
import view.helpPanel.AboutPanel;
import view.helpPanel.ContactPanel;
import view.helpPanel.FAQPanel;
import view.helpPanel.GettingStartedPanel;
import view.panels.TabbedPanel;
import core.interfaces.AirfieldInterface;

/**
 * Creates a TopMenu.
 * 
 * @author Shakib-Bin Hamid
 *
 */
public class TopMenu extends JMenuBar{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Font MENU_FONT = new Font("verdana", Font.PLAIN, 12);
	private static final Font ITEM_FONT = new Font("verdana", Font.PLAIN, 12);

	private JMenu create, load, save, edit, remove, help, email;
	
	private JMenuItem createAirport, createRunway, createObstacle;
	private JMenuItem loadAirport, loadObstacle;
	private JMenuItem saveAirport, saveObstacle, saveTopView, saveSideView, saveAirportAs, saveObstacleAs;
	
	private JMenuItem exit;
	private JMenuItem editRunway, editObstacle, removeObs, removeField;
	private JMenuItem about, contact, gettingStarted, faq;
	private JMenuItem enableEmail, setUpEmail;
	
	static ImageIcon icreate, iload, isave, iedit, iview, icalc, iairport, iairfield, iobstacle, iabout, icontact, iexit, iremove, igetStar, ifaq, iemail, itick;
	
	private TopFrame frame; 
	
	public TopMenu(TopFrame top){
		
		this.frame = top;
		loadIcons();
		createFileMenu();
		createEditMenu();
		createRemoveMenu();
		createHelpMenu();
		createEmailMenu();
		
		//================================Adding menus========================================//
		addMenus(new JMenu[] {create, load, save, edit, remove, email, help });
		
	}
	
	public void paint(Graphics g){
		
		if(TopFrame.isEmailEnabled())
			enableEmail.setIcon(itick);
		else
			enableEmail.setIcon(null);
		
		if (frame.getAirport() == null){
			save.setEnabled(false);
			edit.setEnabled(false);
			remove.setEnabled(false);
			
			loadObstacle.setEnabled(false);
			createRunway.setEnabled(false);
			createObstacle.setEnabled(false);
		}else{
			save.setEnabled(true);
			edit.setEnabled(true);
			remove.setEnabled(true);
			
			if(frame.getTabbePanel().getActiveField() != null){
			
				loadObstacle.setEnabled(true);
				createRunway.setEnabled(true);
				createObstacle.setEnabled(true);

				if(frame.getTabbePanel().getActiveField().getPositionedObstacle() == null){
					editObstacle.setEnabled(false);

					saveObstacle.setEnabled(false);
					saveObstacleAs.setEnabled(false);
					removeObs.setEnabled(false);
					
					saveTopView.setEnabled(true);
					saveSideView.setEnabled(true);
				}else{
					editObstacle.setEnabled(true);

					saveObstacle.setEnabled(true);
					saveObstacleAs.setEnabled(true);
					removeObs.setEnabled(true);
				}
			}else{
				edit.setEnabled(false);
				remove.setEnabled(false);
				
				createObstacle.setEnabled(false);
				loadObstacle.setEnabled(false);
				saveObstacle.setEnabled(false);
				saveObstacleAs.setEnabled(false);
				saveTopView.setEnabled(false);
				saveSideView.setEnabled(false);
				
				createRunway.setEnabled(true);
			}
		}
		super.paint(g);
	}
	
	
	private void loadIcons(){	
		icreate = getIcon("/NewIcon.png");
		iload = getIcon("/open.png");
		isave = getIcon("/save.png");
		iairport = getIcon("/airport.png");
		iairfield = getIcon("/airfield.png");
		iobstacle = getIcon("/obstacle.jpg");
		iabout = getIcon("/about.png");
		icontact = getIcon("/contact.png");
		iexit = getIcon("/exit.png");
		iedit = getIcon("/edit.png");
		icalc = getIcon("/icalc.png");
		iremove = getIcon("/bin.png");
		iview = getIcon("/view.png");
		igetStar = getIcon("/gettingstarted.png");
		ifaq = getIcon("/faq.png");
		itick = getIcon("/tick.png");
		iemail = getIcon("/email.png");
	}
	
	private void createFileMenu(){
		//================================CREATE MENU==============================================//
		
		createAirport = getMenuItem("Create Airport", iairport, SwingConstants.LEFT);
		createRunway = getMenuItem("Create Airfield", iairfield, SwingConstants.LEFT);
		createObstacle = getMenuItem("Create Obstacle", iobstacle, SwingConstants.LEFT);
		
		createAirport.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new FormAirport(frame);
			}});
		createRunway.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (frame.getAirport() != null)
					new FormCreateAirfield(frame);
				else 
					JOptionPane.showMessageDialog(frame, "Please load or create an airport first!", "ERROR: Cannot make an airfield", JOptionPane.ERROR_MESSAGE);
			}});
		createObstacle.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (frame.getAirport() != null)
					new FormObstacle(frame);
				else 
					JOptionPane.showMessageDialog(frame, "Please load or create an airport first!", "ERROR: Cannot place an object", JOptionPane.ERROR_MESSAGE);
			}});
		
		create = getMenu("Create", icreate, new JMenuItem[]{createAirport, createRunway, createObstacle});
		
		//================================LOAD MENU==============================================//
		loadAirport = getMenuItem("Load Airport", iairport, SwingConstants.LEFT);
		loadObstacle = getMenuItem("Load Obstacle", iobstacle, SwingConstants.LEFT);
		
		load = getMenu("Load", iload, new JMenuItem[]{loadAirport, loadObstacle});
		loadAirport.addActionListener(new LoadAirportListener(frame));
		loadObstacle.addActionListener(new LoadObstacleListener(frame));
		
		//================================SAVE MENU==============================================//
		saveAirport = getMenuItem("Save Airport", iairport, SwingConstants.LEFT);
		saveObstacle = getMenuItem("Save Obstacle", iobstacle, SwingConstants.LEFT);
		saveTopView = getMenuItem("Save Topview", iview, SwingConstants.LEFT);
		saveSideView = getMenuItem("Save Sideview", iview, SwingConstants.LEFT);
		
		saveAirportAs = getMenuItem("Save Airport As", iairport, SwingConstants.LEFT);
		saveObstacleAs = getMenuItem("Save Obstacle As", iobstacle, SwingConstants.LEFT);
		
		save = getMenu("Save", isave, new JMenuItem[]{saveAirport, saveAirportAs});
		save.addSeparator();
		save.add(saveObstacle);
		save.add(saveObstacleAs);
		save.addSeparator();
		save.add(saveTopView);
		save.add(saveSideView);
		saveAirport.addActionListener(new SaveAirportListener(frame));
		saveObstacle.addActionListener(new SaveObjectListener(frame));
		saveAirportAs.addActionListener(new SaveAirportAsListener(frame));
		saveObstacleAs.addActionListener(new SaveObstacleAsListener(frame));
		saveTopView.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(frame.getAirport() != null){

					JFileChooser fc = new JFileChooser();
					fc.setDialogTitle("Save TopDown View");
					
					fc.setAcceptAllFileFilterUsed(false);
					fc.setCurrentDirectory(new File("./"));
					fc.addChoosableFileFilter(new CustomFilter("JPG", ".jpg", "JPEG Files"));
					fc.addChoosableFileFilter(new CustomFilter("PNG", ".png", "PNG Files"));
					int select = fc.showSaveDialog(frame);
					if(select == JFileChooser.APPROVE_OPTION){
						File fileToSave = fc.getSelectedFile();
						try {
							CustomFilter filter = (CustomFilter) fc.getFileFilter();
							//Fix for Mac
							if(filter == null) filter = new CustomFilter("JPG", ".jpg", "JPEG Files");
							frame.getTabbePanel().getActiveTab().saveTopView(fileToSave.getAbsolutePath() + filter.getExt(), filter.getName());
							JOptionPane.showMessageDialog(frame, "Saved Successfully at "+ fileToSave.getAbsolutePath(), "SAVING DONE", JOptionPane.INFORMATION_MESSAGE);
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(frame, "Could not save!", "SAVING FAILED", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
				}else
					JOptionPane.showMessageDialog(frame, "Nothing to save!", "SAVING FAILED", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		saveSideView.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(frame.getAirport() != null){

					JFileChooser fc = new JFileChooser();
					fc.setDialogTitle("Save TopDown View");
					
					fc.setAcceptAllFileFilterUsed(false);
					fc.setCurrentDirectory(new File("./"));
					fc.addChoosableFileFilter(new CustomFilter("JPG", ".jpg", "JPEG Files"));
					fc.addChoosableFileFilter(new CustomFilter("PNG", ".png", "PNG Files"));
					int select = fc.showSaveDialog(frame);
					if(select == JFileChooser.APPROVE_OPTION){
						File fileToSave = fc.getSelectedFile();
						try {
							CustomFilter filter = (CustomFilter) fc.getFileFilter();
							//Fix for Mac
							if(filter == null) filter = new CustomFilter("JPG", ".jpg", "JPEG Files");
							frame.getTabbePanel().getActiveTab().saveSideView(fileToSave.getAbsolutePath() + filter.getExt(), filter.getName());
							JOptionPane.showMessageDialog(frame, "Saved Successfully at "+ fileToSave.getAbsolutePath(), "SAVING DONE", JOptionPane.INFORMATION_MESSAGE);
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(frame, "Could not save!", "SAVING FAILED", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
				}else
					JOptionPane.showMessageDialog(frame, "Nothing to save!", "SAVING FAILED", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		exit = getMenuItem("Exit", iexit, SwingConstants.LEFT);
		exit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}});
	}
	
	private void createEditMenu(){
		//================================EDIT MENU==============================================//
		editRunway = getMenuItem("Edit Runway", iairfield, SwingConstants.LEFT);
		editObstacle = getMenuItem("Edit Obstacles", iobstacle, SwingConstants.LEFT);
		
		editRunway.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (frame.getTabbePanel().getActiveField() != null)
					new FormEditAirfield(frame);
				else
					JOptionPane.showMessageDialog(frame, "There is no Airfield to edit", "ERROR: Edit Airfield Failure", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		editObstacle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TabbedPanel panel = frame.getTabbePanel();
				AirfieldInterface field = panel.getActiveField();
				if(field != null){
					if(field.getPositionedObstacle() != null){
						new FormEditObstacle(frame);
					}else{
						JOptionPane.showMessageDialog(frame, "There is no obstacle to edit!", "ERROR: Edit Obstacle Failure", JOptionPane.ERROR_MESSAGE);
					}
				}else
					JOptionPane.showMessageDialog(frame, "There is not even an airfield!", "ERROR: Edit Obstacle Failure", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		edit = getMenu("Edit", iedit, new JMenuItem[]{editRunway, editObstacle});
	}
	
	private void createRemoveMenu(){
		removeObs = getMenuItem("Remove Obstacle", iobstacle, SwingConstants.LEFT);
		removeObs.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				TabbedPanel panel = frame.getTabbePanel();
				AirfieldInterface field = panel.getActiveField();
				if(field != null){
					if(field.getPositionedObstacle() != null){
						field.removeObstacle();
						panel.updateTab(field);
					}else{
						JOptionPane.showMessageDialog(frame, "There is no obstacle to remove!", "ERROR: Remove Obstacle Failure", JOptionPane.ERROR_MESSAGE);
					}
				}else
					JOptionPane.showMessageDialog(frame, "There is no Airfield to remove the obstacle from!", "ERROR: Remove Obstacle Failure", JOptionPane.ERROR_MESSAGE);
			}
			
		});
		
		removeField = getMenuItem("Remove Airfield", iairfield, SwingConstants.LEFT);
		removeField.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(frame.getAirport().getAirfields().size() == 1)
					JOptionPane.showMessageDialog(frame, "Must Have At Least One Airfield", "Cannot Delete Airfield", JOptionPane.ERROR_MESSAGE);
				else{
					AirfieldInterface field = frame.getTabbePanel().getActiveField();
					String ObjButtons[] = {"Yes","No"};
					int promptResult = JOptionPane.showOptionDialog(null, 
							"Are you sure you want to remove "+field.getName()+" ?", "Remove Airfield", 
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, 
							ObjButtons,ObjButtons[1]);
					if(promptResult==JOptionPane.YES_OPTION)
						frame.removeField(field);
				}
			}
			
		});
		
		remove = getMenu("Remove", iremove, new JMenuItem[]{removeObs, removeField});
	}
	
	private void createHelpMenu(){
		//================================HELP MENU==============================================//
		about = getMenuItem("About", iabout, SwingConstants.LEFT);
		about.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new AboutPanel();
				
			}
			
		});
		contact = getMenuItem("Contact", icontact, SwingConstants.LEFT);
		contact.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new ContactPanel();
				
			}
			
		});
		gettingStarted = getMenuItem("Getting Started", igetStar, SwingConstants.LEFT);
		gettingStarted.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new GettingStartedPanel();
				
			}
			
		});
		faq = getMenuItem("FAQ", ifaq, SwingConstants.LEFT);
		faq.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new FAQPanel();
				
			}
			
		});
		help = getMenu("Help", null, new JMenuItem[]{gettingStarted, faq, about, contact});
	}
	
	private void addMenus(JMenu[] menus){
		for(int i=0; i<menus.length; i++)
			this.add(menus[i]);
	}
	
	private void createEmailMenu(){
		setUpEmail = getMenuItem("Set up Email", null, SwingConstants.LEFT);
		enableEmail = getMenuItem("Enable Email", null, SwingConstants.LEFT);
		
		setUpEmail.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EnterEmail();
			}
		});
		
		enableEmail.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!TopFrame.hasEmail()){
					new EnterEmail();
				}else{
					if(TopFrame.isEmailEnabled()){
						TopFrame.setEnableEmail(false);
						System.out.println("DISABLED");
						enableEmail.setIcon(null);
					}else{
						TopFrame.setEnableEmail(true);
						System.out.println("ENABLED");
						enableEmail.setIcon(itick);
					}
				}
			}
		});
		
		email = getMenu("Email", iemail, new JMenuItem[]{setUpEmail, enableEmail});
	}
	
	/**
	 * 
	 * @param text Name of the menu
	 * @param icon Icon to be displayed
	 * @param textAllignment text allignment of the menuitem
	 * @return a jmenuitem with the settings
	 */
	public static JMenuItem getMenuItem(String text, Icon icon, int textAllignment){
		JMenuItem item = new JMenuItem(text);
		item.setIcon(icon);
		item.setHorizontalAlignment(textAllignment);
		
		item.setFont(ITEM_FONT);
		
		return item;
	}
	
	/**
	 * 
	 * @param location file location of the icon
	 * @return an icon object based on the file
	 */
	public static ImageIcon getIcon(String location){
		return new ImageIcon(TopFrame.class.getResource(location));
	}
	
	/**
	 * 
	 * @param text the name of the Menu
	 * @param items the jmenuitems to be added to it
	 * @return a jmenu with the jmenuitems
	 */
	public static JMenu getMenu(String text, Icon icon, JMenuItem[] items){
		JMenu menu = new JMenu(text);
		menu.setIcon(icon);
		menu.setHorizontalAlignment(SwingConstants.LEFT);
		for(int i=0; i<items.length; i++)
			menu.add(items[i]);

		menu.setFont(MENU_FONT);
		
		return menu;
	}
	
	private class EnterEmail extends JFrame{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		JPanel toppanel, insidepanel;
		JButton ok;
		SelfCheckingField tf;
		
		EnterEmail(){
			init();
		}
		
		private void enableOrDisable(){
			if(!tf.getText().matches(TopFrame.EMAIL_REGEX))
				ok.setEnabled(false);
			else
				ok.setEnabled(true);
		}
		
		private void init(){
			toppanel = new JPanel(new BorderLayout());
			insidepanel = new JPanel(new BorderLayout());
			
			tf = new SelfCheckingField(TopFrame.EMAIL_REGEX);
			ok = new JButton("OK");
			ok.setEnabled(false);
			
			tf.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void removeUpdate(DocumentEvent e) {enableOrDisable();}
				@Override
				public void insertUpdate(DocumentEvent e) {enableOrDisable();}
				@Override
				public void changedUpdate(DocumentEvent e) {enableOrDisable();}
			});
			
			ok.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					TopFrame.setEmail(tf.getText());
					TopFrame.setEnableEmail(true);
					dispose();
				}
			});
			
			insidepanel.add(new JLabel("Enter Email"), BorderLayout.WEST);
			insidepanel.add(tf, BorderLayout.CENTER);
			
			toppanel.add(insidepanel, BorderLayout.CENTER);
			toppanel.add(ok, BorderLayout.SOUTH);
			
			this.setContentPane(toppanel);
			this.setLocationRelativeTo(frame);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setMinimumSize(new Dimension(300, 50));
			this.getRootPane().setDefaultButton(ok);
			this.pack();
			this.setVisible(true);
		}
	}
}
