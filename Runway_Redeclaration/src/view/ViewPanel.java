package view;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.DeclaredRunwayInterface;

public class ViewPanel extends JPanel{
	
	private AirfieldInterface field;
	private DeclaredRunwayInterface runway;
	private ViewTop view1, view2;

	public ViewPanel(AirfieldInterface field, DeclaredRunwayInterface runway){
		this.field = field;
		this.runway = runway;
		view1 = new ViewTop(field, runway);
	//	view2 = new View(null, null);
		init();
	}
	
	private void init(){
		this.setLayout(new GridLayout(0,1));
		this.add(view1);
		this.add(new JPanel());

		view1.setBorder(BorderFactory.createTitledBorder("Top View"));
	}
	
	public void updateView(DeclaredRunwayInterface run){
		this.runway = run;
		view1.setRunway(run);
		view1.repaint();
	}
	
	public ViewTop getTopView(){
		return view1;
	}
}
