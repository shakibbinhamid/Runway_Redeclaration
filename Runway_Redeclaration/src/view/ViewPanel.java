package view;

import java.awt.GridLayout;

import javax.swing.JPanel;

import CoreInterfaces.DeclaredRunwayInterface;

public class ViewPanel extends JPanel{
	
	private DeclaredRunwayInterface runway;
	private View view1, view2;

	public ViewPanel(DeclaredRunwayInterface runway){
		this.runway = runway;
		view1 = new View();
		view2 = new View();
		init();
	}
	
	private void init(){
		this.setLayout(new GridLayout(0,1));
		
		this.add(view1);
		this.add(view2);
		
	}
}
