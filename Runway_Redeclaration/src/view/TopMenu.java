package view;

import io.ImageFilter;
import io.JPGFilter;
import io.PNGFilter;
import io.Print;

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

import listeners.LoadAirportListener;
import listeners.LoadObstacleListener;
import listeners.SaveAirportAsListener;
import listeners.SaveAirportListener;
import listeners.SaveObjectListener;
import listeners.SaveObstacleAsListener;
import CoreInterfaces.AirfieldInterface;

public class TopMenu extends JMenuBar{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JMenu edit, print, help;
	private JMenu create, load, save, remove;
	
	private JMenuItem createAirport, createRunway, createObstacle;
	private JMenuItem loadAirport, loadObstacle;
	private JMenuItem saveAirport, saveObstacle, saveTopView, saveAirportAs, saveObstacleAs;
	
	private JMenuItem exit;
	private JMenuItem editRunway, editObstacle, removeObs;
	private JMenuItem printCalculation;
	private JMenuItem about, contact;
	
	private ImageIcon icreate, iload, isave, iedit, iview, iprint, icalc, iairport, iairfield, iobstacle, iabout, icontact, iexit, iremove;
	
	private TopFrame frame; 
	
	public TopMenu(TopFrame top){
		
		this.frame = top;
		loadIcons();
		createFileMenu();
		createEditMenu();
		createRemoveMenu();
		createPrintMenu();
		createHelpMenu();
		
		//================================Adding menus========================================//
		addMenus(new JMenu[] {create, load, save, edit, remove ,print });//, help});
		
	}
	
	private void loadIcons(){	
		//================================ICONS==============================================//
		
		icreate = getIcon("/NewIcon.png");
		iload = getIcon("/open.png");
		isave = getIcon("/save.png");
		iprint = getIcon("/print.jpg");
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
		
		createAirport = getItem("Create Airport", iairport, SwingConstants.LEFT);
		createRunway = getItem("Create Airfield", iairfield, SwingConstants.LEFT);
		createObstacle = getItem("Create Obstacle", iobstacle, SwingConstants.LEFT);
		
		createAirport.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new FormAirport(frame);
			}});
		createRunway.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (frame.getAirport() != null)
					new FormAirfield(frame);
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
		loadAirport = getItem("Load Airport", iairport, SwingConstants.LEFT);
		loadObstacle = getItem("Load Obstacle", iobstacle, SwingConstants.LEFT);
		
		load = getMenu("Load", iload, new JMenuItem[]{loadAirport, loadObstacle});
		loadAirport.addActionListener(new LoadAirportListener(frame));
		loadObstacle.addActionListener(new LoadObstacleListener(frame));
		
		//================================SAVE MENU==============================================//
		saveAirport = getItem("Save Airport", iairport, SwingConstants.LEFT);
		saveObstacle = getItem("Save Obstacle", iobstacle, SwingConstants.LEFT);
		saveTopView = getItem("Save Topview", iview, SwingConstants.LEFT);
		
		saveAirportAs = getItem("Save Airport As", iairport, SwingConstants.LEFT);
		saveObstacleAs = getItem("Save Obstacle As", iobstacle, SwingConstants.LEFT);
		
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
					fc.addChoosableFileFilter(new JPGFilter());
					fc.addChoosableFileFilter(new PNGFilter());
					int select = fc.showSaveDialog(frame);
					if(select == JFileChooser.APPROVE_OPTION){
						File fileToSave = fc.getSelectedFile();
						try {
							ImageFilter filter = (ImageFilter) fc.getFileFilter();
							//Fix for Mac
							if(filter == null) filter = new JPGFilter();
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
		
		exit = getItem("Exit", iexit, SwingConstants.LEFT);
		exit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}});
	}
	
	private void createEditMenu(){
		//================================EDIT MENU==============================================//
		editRunway = getItem("Edit Runway", iairfield, SwingConstants.LEFT);
		editObstacle = getItem("Edit Obstacles", iobstacle, SwingConstants.LEFT);
		
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
		removeObs = getItem("Remove Obstacle", iobstacle, SwingConstants.LEFT);
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
		
		remove = getMenu("Remove", iremove, new JMenuItem[]{removeObs});
	}
	
	private void createPrintMenu(){
		//================================PRINT MENU==============================================//
		printCalculation = getItem("Print Calculation", icalc, SwingConstants.LEFT);
		printCalculation.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Print.print(frame.getLogPanel().getCalcTextPane().getText());
			}
			
		});
		
		print = getMenu("Print", iprint, new JMenuItem[]{printCalculation});
	}
	
	private void createHelpMenu(){
		//================================HELP MENU==============================================//
		about = getItem("About", iabout, SwingConstants.LEFT);
		contact = getItem("Contact", icontact, SwingConstants.LEFT);
		
		help = getMenu("Help", null, new JMenuItem[]{about, contact});
	}
	
	/**
	 * 
	 * @param text Name of the menu
	 * @param icon Icon to be displayed
	 * @param textAllignment text allignment of the menuitem
	 * @return a jmenuitem with the settings
	 */
	private JMenuItem getItem(String text, Icon icon, int textAllignment){
		
		JMenuItem item = new JMenuItem(text);
		item.setIcon(icon);
		item.setHorizontalAlignment(textAllignment);
		
		return item;
	}
	
	/**
	 * 
	 * @param location file location of the icon
	 * @return an icon object based on the file
	 */
	private ImageIcon getIcon(String location){
		
		return new ImageIcon(TopFrame.class.getResource(location));
	}
	
	/**
	 * 
	 * @param text the name of the Menu
	 * @param items the jmenuitems to be added to it
	 * @return a jmenu with the jmenuitems
	 */
	private JMenu getMenu(String text, Icon icon, JMenuItem[] items){
		
		JMenu menu = new JMenu(text);
		menu.setIcon(icon);
		menu.setHorizontalAlignment(SwingConstants.LEFT);
		for(int i=0; i<items.length; i++)
			menu.add(items[i]);
		
		return menu;
	}
	
	/**
	 * 
	 * @param menus the menus to be added
	 */
	private void addMenus(JMenu[] menus){
		
		for(int i=0; i<menus.length; i++)
			this.add(menus[i]);
		
	}
	
}
