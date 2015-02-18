package view;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

public class TopMenu extends JMenuBar{

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
		
		this.add(file);
		this.add(edit);
		this.add(print);
		this.add(help);
		
	}
	
	private JMenuItem getItem(String text, Icon icon, int textAllignment){
		
		JMenuItem item = new JMenuItem(text);
		item.setIcon(icon);
		item.setHorizontalAlignment(textAllignment);
		
		return item;
	}
	
	private ImageIcon getIcon(String location){
		
		return new ImageIcon(location);
	}
	
	private JMenu getMenu(String text, JMenuItem[] items){
		
		JMenu menu = new JMenu(text);
		for(int i=0; i<items.length; i++)
			menu.add(items[i]);
		
		return menu;
	}
	
}
