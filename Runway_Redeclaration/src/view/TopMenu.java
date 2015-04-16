package view;

import io.CustomFilter;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import coreInterfaces.AirfieldInterface;
import listeners.LoadAirportListener;
import listeners.LoadObstacleListener;
import listeners.SaveAirportAsListener;
import listeners.SaveAirportListener;
import listeners.SaveObjectListener;
import listeners.SaveObstacleAsListener;

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

	private JMenu help;
	private JMenu create, load, save, edit, remove;
	
	private JMenuItem createAirport, createRunway, createObstacle;
	private JMenuItem loadAirport, loadObstacle;
	private JMenuItem saveAirport, saveObstacle, saveTopView, saveAirportAs, saveObstacleAs;
	
	private JMenuItem exit;
	private JMenuItem editRunway, editObstacle, removeObs, removeField;
	private JMenuItem about, contact;
	
	static ImageIcon icreate, iload, isave, iedit, iview, icalc, iairport, iairfield, iobstacle, iabout, icontact, iexit, iremove;
	
	private TopFrame frame; 
	
	public TopMenu(TopFrame top){
		
		this.frame = top;
		loadIcons();
		createFileMenu();
		createEditMenu();
		createRemoveMenu();
		createHelpMenu();
		
		//================================Adding menus========================================//
		addMenus(new JMenu[] {create, load, save, edit, remove });//, help});
		
	}
	
	public void paint(Graphics g){
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
			
			loadObstacle.setEnabled(true);
			createRunway.setEnabled(true);
			createObstacle.setEnabled(true);
			
			if(frame.getTabbePanel().getActiveField().getPositionedObstacle() == null){
				editObstacle.setEnabled(false);
				
				saveObstacle.setEnabled(false);
				saveObstacleAs.setEnabled(false);
				removeObs.setEnabled(false);
			}else{
				editObstacle.setEnabled(true);
				
				saveObstacle.setEnabled(true);
				saveObstacleAs.setEnabled(true);
				removeObs.setEnabled(true);
			}
		}
		super.paint(g);
	}
	
	
	private void loadIcons(){	
		//================================ICONS==============================================//
		
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
		
		saveAirportAs = getMenuItem("Save Airport As", iairport, SwingConstants.LEFT);
		saveObstacleAs = getMenuItem("Save Obstacle As", iobstacle, SwingConstants.LEFT);
		
		save = getMenu("Save", isave, new JMenuItem[]{saveAirport, saveObstacle, saveAirportAs, saveObstacleAs, saveTopView});
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
		contact = getMenuItem("Contact", icontact, SwingConstants.LEFT);
		
		help = getMenu("Help", null, new JMenuItem[]{about, contact});
	}
	
	private void addMenus(JMenu[] menus){
		for(int i=0; i<menus.length; i++)
			this.add(menus[i]);
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
}
