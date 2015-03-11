package view;

import java.awt.GridLayout;

import javax.swing.JPanel;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.DeclaredRunwayInterface;

public class ViewPanel extends JPanel{
	
	private AirfieldInterface field;
	private DeclaredRunwayInterface runway;
	private View view1, view2;

	public ViewPanel(AirfieldInterface field, DeclaredRunwayInterface runway){
		this.field = field;
		this.runway = runway;
		view1 = new View(field, runway);
	//	view2 = new View(null, null);
		init();
	}
	
	private void init(){
		this.setLayout(new GridLayout(0,1));
		this.add(view1);
		this.add(new JPanel());
		
	}
	
	public void updateView(DeclaredRunwayInterface run){
		this.runway = run;
		view1.setRunway(run);
		view1.repaint();
	}
	
	public View getTopView(){
		return view1;
	}
}
