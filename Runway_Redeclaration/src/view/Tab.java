package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.DeclaredRunwayInterface;
import Exceptions.UnusableRunwayException;

public class Tab extends JPanel{
	
	private AirfieldInterface field;
	private DeclaredRunwayInterface runway;
	
	private JPanel dataPanel;
		private ChooseRunwayPanel selectDirectionPanel;
		private InfoPanel info;
	private ViewPanel views;
	
	public Tab(AirfieldInterface field){
		this.field = field;
		this.runway = field.getSmallAngledRunway();
		init();
		try {
			if(field != null)
				populateRunwayTable(field.getLargeAngledRunway(),field.getLargeAngledRunway());
		} catch (UnusableRunwayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void init(){
		this.setLayout(new BorderLayout());
		
		dataPanel = new JPanel();
		dataPanel.setLayout(new BorderLayout());
		this.add(dataPanel, BorderLayout.WEST);
		
		selectDirectionPanel = new ChooseRunwayPanel(this);
		dataPanel.add(selectDirectionPanel, BorderLayout.NORTH);
		
		info = new InfoPanel(runway);
		dataPanel.add(info, BorderLayout.CENTER);
		
		views = new ViewPanel(runway);
		this.add(views, BorderLayout.CENTER);
		
	}
	
	protected InfoPanel getInfoPanel(){
		return info;
	}

	protected ViewPanel getViewPanel(){
		return views;
	}
	
	protected DeclaredRunwayInterface getRunway(){
		return runway;
	}
	
	protected AirfieldInterface getField(){
		return field;
	}
	
	protected void populateRunwayTable(DeclaredRunwayInterface def, DeclaredRunwayInterface run) throws UnusableRunwayException{
		String[] tora, toda, asda, lda, dt;
		String[] resa, blast, clear, stop, angleA, angleD;
		
		tora = new String[]{"TORA", getPara(def, "tora"), getPara(run, "tora")};
		toda = new String[]{"TODA", getPara(def, "toda"), getPara(run, "toda")};
		asda = new String[]{"ASDA", getPara(def, "asda"), getPara(run, "asda")};
		lda = new String[]{"LDA", getPara(def, "lda"), getPara(run, "lda")};
		dt = new String[]{"Displaced Threshold", getPara(def, "dt"), getPara(run, "dt")};
		
		stop = new String[]{"Stopway",getPara(def, "stop"), getPara(run, "stop")};
		clear = new String[]{"Clearway",getPara(def, "clear"), getPara(run, "clear")};
		//blast= new String[]{"Blast Protection", getPara(def, "blast"), getPara(run, "blast")};
		//resa = new String[]{"RESA", getPara(def, "resa"), getPara(run, "resa")};
		angleA = new String[]{"Angle of Ascent", getPara(def, "ascent"), getPara(run, "ascent")};
		angleD = new String[]{"Angle of Descent", getPara(def, "descent"), getPara(run, "descent")};
		
		info.updateRunwayTable(new String[][]{tora, toda, asda, lda, dt});
	}
	
	String getPara(DeclaredRunwayInterface runway, String para) throws UnusableRunwayException{
		switch(para){
		case "tora": return String.valueOf(runway.getTORA());
		case "asda": return String.valueOf(runway.getASDA());
		case "toda": return String.valueOf(runway.getTODA());
		case "lda": return String.valueOf(runway.getLDA());
		case "dt": return String.valueOf(runway.getDisplacedThreshold());
		
		case "stop": return String.valueOf(runway.getStopway());
		//case "resa": return String.valueOf(runway.ge);
		case "clear": return String.valueOf(runway.getClearway());
		//case "blast": return String.valueOf(runway.());
		case "ascent": return String.valueOf(runway.getAngleOfAscent());
		case "descent": return String.valueOf(runway.getAngleOfDescent());
		}
		return null;
	}
}
