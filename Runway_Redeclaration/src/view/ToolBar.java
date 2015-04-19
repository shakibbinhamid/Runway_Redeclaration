package view;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JToolBar;

/**
 * This is a ToolBar (extends JToolBar) that can Handle ButtonGroups.
 * @see {@link JToolBar}
 * @author Shakib-Bin Hamid
 *
 */
public class ToolBar extends JToolBar {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, ButtonGroup> buttonGroups;

	public ToolBar() {
		init();
	}

	public ToolBar(int orientation) {
		super(orientation);
		init();
	}
	
	private void init(){
		buttonGroups = new HashMap<>();
	}
	
	/**
	 * Add all the buttons in this group to the ToolBar.
	 * If a group already exists with such a name, it will be overrwritten.
	 * 
	 * @param groupName Name of the buttongroup
	 * @param group group of buttons
	 * @return true if successfully added, false otherwise
	 */
	public boolean addButtonGroup(String groupName, ButtonGroup group){
		for(AbstractButton b: Collections.list(group.getElements())){
			add(b);
		}
		return buttonGroups.put(groupName, group) != null;
	}
	
	/**
	 * Add all the buttons in the collection of tools to the group and then
	 * add all the buttons in the group to the ToolBar.
	 * If a group already exists with such a name, it will be overrwritten.
	 * 
	 * @param groupName Name of the buttongroup
	 * @param group group of buttons
	 * @param tools the collection of tools to be added to the group and toolbar
	 * @return true if successfully added, false otherwise
	 */
	public boolean addButtonGroup(String groupName, ButtonGroup group, Collection<AbstractButton> tools){
		for(AbstractButton tool: tools)
			group.add(tool);
		return addButtonGroup(groupName, group);
	}
	
	/**
	 * The button group of given name will be removed from toolbar
	 * @param groupName name of the group being removed
	 * @return true if the toolbar contains the buttongroup, false otherwise
	 */
	public boolean removeButtonGroup(String groupName){
		return removeButtonGroup(buttonGroups.get(groupName));
	}
	
	/**
	 * IF the group with given name is contained in this toolbar -
	 * 1. All the tools will be removed from the group
	 * 2. All the tools will be removed from the toolbar
	 * 
	 * @param groupName name of the group to remove buttons from
	 * @param tools the buttons to remove from toolbar group
	 * @return true if the group is contained in the toolbar, false otherwise.
	 */
	public boolean removeButtonGroup(String groupName, Collection<AbstractButton> tools){
		return removeButtonGroup(buttonGroups.get(groupName), tools);
	}
	
	/**
	 * All the buttons in this group will be removed from toolbar IF the group exists in toolbar
	 * @param group the group being removed from toolbar
	 * @return true if the toolbar contains the buttongroup, false otherwise
	 */
	public boolean removeButtonGroup(ButtonGroup group){
		if (buttonGroups.containsValue(group)){
			for(AbstractButton b: Collections.list(group.getElements())){
				remove(b);
			}
		}
		return buttonGroups.remove(group) != null;
	}
	
	/**
	 * IF the group is contained in this toolbar -
	 * 1. All the tools will be removed from the group
	 * 2. All the tools will be removed from the toolbar
	 * 
	 * @param group the group to remove buttons from
	 * @param tools the buttons to remove from toolbar group
	 * @return true if the group is contained in the toolbar, false otherwise.
	 */
	public boolean removeButtonGroup(ButtonGroup group, Collection<AbstractButton> tools){
		if (buttonGroups.containsValue(group)){
			for(AbstractButton b: Collections.list(group.getElements())){
				group.remove(b);
				remove(b);
			}
			return true;
		}
		return buttonGroups.containsValue(group);
	}

}
