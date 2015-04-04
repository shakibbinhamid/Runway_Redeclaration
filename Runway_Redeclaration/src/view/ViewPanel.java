package view;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.DeclaredRunwayInterface;

public class ViewPanel extends JPanel{
	
	private AirfieldInterface field;
	private DeclaredRunwayInterface runway;
	private ViewTop view1;
	private ViewSide view2;

	public ViewPanel(AirfieldInterface field, DeclaredRunwayInterface runway){
		this.field = field;
		this.runway = runway;
		view1 = new ViewTop(field, runway);
		view2 = new ViewSide(field, runway);
		init();
	}
	
	private void init(){
		this.setLayout(new GridLayout(2,1));
		this.add(view1);
		this.add(view2);

		view1.setBorder(BorderFactory.createTitledBorder("Top View"));
		view2.setBorder(BorderFactory.createTitledBorder("Side View"));
		//TODO gimme a border Shakib!
	}
	
	public void updateView(DeclaredRunwayInterface run){
		this.runway = run;
		view1.setRunway(run);
		view1.repaint();
		view2.setRunway(run);
		view2.repaint();

	}
	
	public ViewTop getTopView(){
		return view1;
	}
}
