package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class TopMenu extends JMenuBar{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JMenu file, edit, print, help;
	private JMenu create, load, save;
	
	private JMenuItem createAirport, createRunway, createObstacle;
	private JMenuItem loadAirport, loadObstacle;
	private JMenuItem saveAirport, saveObstacle;
	
	private JMenuItem exit;
	private JMenuItem editRunway, editObstacle;
	private JMenuItem printCalculation;
	private JMenuItem about, contact;
	
	private Icon icreate, iload, isave, iedit, iprint, iairport, iairfield, iobstacle, iabout, icontact, iexit;
	
	private TopFrame frame; 
	
	public TopMenu(TopFrame top){
		
		this.frame = top;
		loadIcons();
		createFileMenu();
		createEditMenu();
		createPrintMenu();
		createHelpMenu();
		
		//================================Adding menus========================================//
		addMenus(new JMenu[] {file, edit, print, help});
		
	}
	
	private void loadIcons(){		
		//================================ICONS==============================================//
		
		icreate = getIcon("./icon/NewIcon.png");
		iload = getIcon("./icon/open.png");
		isave = getIcon("./icon/save.png");
		iprint = getIcon("./icon/print.jpg");
		iairport = getIcon("./icon/airport.png");
		iairfield = getIcon("./icon/airfield.png");
		iobstacle = getIcon("./icon/obstacle.jpg");
		iabout = getIcon("./icon/about.png");
		icontact = getIcon("./icon/contact.png");
		iexit = getIcon("./icon/exit.png");
		iedit = getIcon("./icon/edit.png");
		
	}
	
	private void createFileMenu(){
		//================================CREATE MENU==============================================//
		
		createAirport = getItem("Create Airport", iairport, SwingConstants.CENTER);
		createRunway = getItem("Create Airfield", iairfield, SwingConstants.CENTER);//TODO: need to get an icon for runway
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
		
		save = getMenu("Save", isave, new JMenuItem[]{saveAirport, saveObstacle});
		saveAirport.addActionListener(new SaveAirportListener(frame));
		saveObstacle.addActionListener(new SaveObjectListener(frame));

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
		
		edit = getMenu("Edit", null, new JMenuItem[]{editRunway, editObstacle});
	}
	
	private void createPrintMenu(){
		//================================PRINT MENU==============================================//
		printCalculation = getItem("Print Calculation", iprint, SwingConstants.CENTER);
		
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
	private Icon getIcon(String location){
		
		return new ImageIcon(location);
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
