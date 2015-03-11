package view;

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

import CoreInterfaces.AirfieldInterface;

public class TopMenu extends JMenuBar{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JMenu file, edit, print, help;
	private JMenu create, load, save;
	
	private JMenuItem createAirport, createRunway, createObstacle;
	private JMenuItem loadAirport, loadObstacle;
	private JMenuItem saveAirport, saveObstacle, saveTopView;
	
	private JMenuItem exit;
	private JMenuItem editRunway, editObstacle, removeObs;
	private JMenuItem printCalculation;
	private JMenuItem about, contact;
	
	private ImageIcon icreate, iload, isave, iedit, iprint, iairport, iairfield, iobstacle, iabout, icontact, iexit;
	
	private TopFrame frame; 
	
	public TopMenu(TopFrame top){
		
		this.frame = top;
		loadIcons();
		createFileMenu();
		createEditMenu();
		createPrintMenu();
		createHelpMenu();
		
		//================================Adding menus========================================//
		addMenus(new JMenu[] {file, edit ,print });//, help});
		
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
		
	}
	
	private void createFileMenu(){
		//================================CREATE MENU==============================================//
		
		createAirport = getItem("Create Airport", iairport, SwingConstants.CENTER);
		createRunway = getItem("Create Airfield", iairfield, SwingConstants.CENTER);
		createObstacle = getItem("Create Obstacle", iobstacle, SwingConstants.CENTER);
		
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
		loadAirport = getItem("Load Airport", iairport, SwingConstants.CENTER);
		loadObstacle = getItem("Load Obstacle", iobstacle, SwingConstants.CENTER);
		
		load = getMenu("Load", iload, new JMenuItem[]{loadAirport, loadObstacle});
		loadAirport.addActionListener(new LoadAirportListener(frame));
		loadObstacle.addActionListener(new LoadObstacleListener(frame));
		
		//================================SAVE MENU==============================================//
		saveAirport = getItem("Save Airport", iairport, SwingConstants.CENTER);
		saveObstacle = getItem("Save Obstacle", iobstacle, SwingConstants.CENTER);
		saveTopView = getItem("Save Topview", null, SwingConstants.CENTER);
		
		save = getMenu("Save", isave, new JMenuItem[]{saveAirport, saveObstacle, saveTopView});
		saveAirport.addActionListener(new SaveAirportListener(frame));
		saveObstacle.addActionListener(new SaveObjectListener(frame));
		saveTopView.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(frame.getAirport() != null){

					JFileChooser fc = new JFileChooser();
					fc.setDialogTitle("Save TopDown View");

					int select = fc.showSaveDialog(frame);

					if(select == JFileChooser.APPROVE_OPTION){
						File fileToSave = fc.getSelectedFile();
						try {
							frame.getTabbePanel().getActiveTab().saveTopView(fileToSave.getAbsolutePath() + ".png");
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
		
		exit = getItem("Exit", iexit, SwingConstants.CENTER);
		exit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}});
		
		file = getMenu("File", null, new JMenuItem[]{create, load, save, exit});
	}
	
	private void createEditMenu(){
		//================================EDIT MENU==============================================//
		editRunway = getItem("Edit Runway", iedit, SwingConstants.CENTER);
		editObstacle = getItem("Edit Obstacles", iedit, SwingConstants.CENTER);
		
		removeObs = getItem("Remove Obstacle", null, SwingConstants.CENTER);
		removeObs.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				TabbedPanel panel = frame.getTabbePanel();
				AirfieldInterface field = panel.getActiveField();
				field.removeObstacle();
				panel.updateTab(field);
			}
			
		});
		
		edit = getMenu("Edit", null, new JMenuItem[]{ removeObs});
	}
	
	private void createPrintMenu(){
		//================================PRINT MENU==============================================//
		printCalculation = getItem("Print Calculation", iprint, SwingConstants.CENTER);
		printCalculation.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Print.print(frame.getLogPanel().getCalcTextPane().getText());
			}
			
		});
		
		print = getMenu("Print", null, new JMenuItem[]{printCalculation});
	}
	
	private void createHelpMenu(){
		//================================HELP MENU==============================================//
		about = getItem("About", iabout, SwingConstants.CENTER);
		contact = getItem("Contact", icontact, SwingConstants.CENTER);
		
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
		menu.setHorizontalAlignment(SwingConstants.CENTER);
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
