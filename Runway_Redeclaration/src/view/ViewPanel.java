package view;

import javax.swing.JSplitPane;

import coreInterfaces.AirfieldInterface;
import coreInterfaces.DeclaredRunwayInterface;

public class ViewPanel extends JSplitPane{
	
	private AirfieldInterface field;
	private DeclaredRunwayInterface runway;
	
	private ViewTop view1;
	private ViewSide view2;

	public ViewPanel(AirfieldInterface field, DeclaredRunwayInterface runway){
		super(JSplitPane.VERTICAL_SPLIT);
		
		this.field = field;
		this.runway = runway;
		
		view1 = new ViewTop(field, runway, "Top");
		view2 = new ViewSide(field, runway, "Bottom");
		
		init();
	}
	
	private void init(){
		setLeftComponent(view1);
		setRightComponent(view2);
		
		setResizeWeight(0.5);
		setOneTouchExpandable(true);
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
