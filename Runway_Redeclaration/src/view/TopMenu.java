package view;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

public class TopMenu extends JMenuBar{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JMenu file, edit, print, help;
	
	JMenuItem open, openRecent, save, exit;
	JMenuItem editRunway, editObstacle;
	JMenuItem printCalculation;
	JMenuItem about, contact;
	
	public TopMenu(){
		
		//================================FILE MENU==============================================//
		open = getItem("Open", getIcon("./icon/OpenIcon.png"), SwingConstants.CENTER);
		openRecent = getItem("Open Recent", getIcon("./icon/OpenIcon.png"), SwingConstants.CENTER);
		save = getItem("Save", getIcon("./icon/HelpIcon.png"), SwingConstants.CENTER);
		exit = getItem("Exit", getIcon("./icon/ExitIcon.png"), SwingConstants.CENTER);
		
		file = getMenu("File", new JMenuItem[]{open, openRecent, save, exit});
		
		//================================EDIT MENU==============================================//
		editRunway = getItem("Edit Runway", getIcon("./icon/OpenIcon.png"), SwingConstants.CENTER);
		editObstacle = getItem("Edit Obstacles", getIcon("./icon/OpenIcon.png"), SwingConstants.CENTER);
		
		edit = getMenu("Edit", new JMenuItem[]{editRunway, editObstacle});
		
		//================================PRINT MENU==============================================//
		printCalculation = getItem("Print Calculation", getIcon("./icon/PrintIcon.png"), SwingConstants.CENTER);
		
		print = getMenu("Print", new JMenuItem[]{printCalculation});
		
		//================================HELP MENU==============================================//
		about = getItem("About", getIcon("./icon/PrintIcon.png"), SwingConstants.CENTER);
		contact = getItem("Contact", getIcon("./icon/AddressBookIcon.png"), SwingConstants.CENTER);
		
		help = getMenu("Help", new JMenuItem[]{about, contact});
		
		//================================Adding menus==============================================//
		addMenus(new JMenu[] {file, edit, print, help});
		
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
	private JMenu getMenu(String text, JMenuItem[] items){
		
		JMenu menu = new JMenu(text);
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
