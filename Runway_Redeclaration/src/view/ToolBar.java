package view;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JToolBar;


public class ToolBar extends JToolBar {
	
	Map<String, ButtonGroup> buttonGroups;

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
	
	public boolean addButtonGroup(String groupName, ButtonGroup group){
		for(AbstractButton b: Collections.list(group.getElements())){
			add(b);
		}
		return buttonGroups.put(groupName, group) != null;
	}
	
	public boolean addButtonGroup(String groupName, ButtonGroup group, Collection<AbstractButton> tools){
		for(AbstractButton tool: tools)
			group.add(tool);
		return addButtonGroup(groupName, group);
	}
	
	public boolean removeButtonGroup(String groupName){
		return removeButtonGroup(buttonGroups.get(groupName));
	}
	
	public boolean removeButtonGroup(String groupName, Collection<AbstractButton> tools){
		return removeButtonGroup(buttonGroups.get(groupName), tools);
	}
	
	public boolean removeButtonGroup(ButtonGroup group){
		if (buttonGroups.containsValue(group)){
			for(AbstractButton b: Collections.list(group.getElements())){
				remove(b);
			}
		}
		return buttonGroups.remove(group) != null;
	}
	
	public boolean removeButtonGroup(ButtonGroup group, Collection<AbstractButton> tools){
		if (buttonGroups.containsValue(group)){
			for(AbstractButton b: Collections.list(group.getElements())){
				group.remove(b);
			}
			return true;
		}
		return buttonGroups.containsValue(group);
	}

}
