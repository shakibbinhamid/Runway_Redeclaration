package view.panels;

import javax.swing.JSplitPane;

import view.views.ViewSide;
import view.views.ViewTop;
import core.interfaces.AirfieldInterface;
import core.interfaces.DeclaredRunwayInterface;

/**
 * This class (extends JSplitPane) holdes the two views.
 * The views are updated from this class.
 * 
 * @author Shakib-Bin Hamid
 * @see {@link JSplitPane}
 * @see {@link ViewTop}
 * @see {@link ViewSide}
 */
public class ViewPanel extends JSplitPane{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ViewTop view1;
	private ViewSide view2;

	public ViewPanel(AirfieldInterface field, DeclaredRunwayInterface runway){
		super(JSplitPane.VERTICAL_SPLIT);
		
		view1 = new ViewTop(field, runway);
		view2 = new ViewSide(field, runway);
		
		init();
	}
	
	private void init(){
		setLeftComponent(view1);
		setRightComponent(view2);
		
		setResizeWeight(0.5);
		setOneTouchExpandable(true);
	}
	
	/**
	 * Both of the views are updated with this runway values.
	 * The views are reset to initial looks.
	 * @param run
	 */
	public void updateView(DeclaredRunwayInterface run){
		view1.setRunway(run);
		view1.repaint();
		view2.setRunway(run);
		view2.repaint();
	}
	
	/**
	 * Returns the top view
	 * @return top view
	 */
	public ViewTop getTopView(){
		return view1;
	}
	
	/**
	 * Returns the side on view
	 * @return side view
	 */
	public ViewSide getSideView(){
		return view2;
	}
}
