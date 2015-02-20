package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import CoreInterfaces.AirfieldInterface;
import CoreInterfaces.DeclaredRunwayInterface;
import CoreInterfaces.ObstacleInterface;
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
			if(field != null){
				populateRunwayTable(field.getLargeAngledRunway(),field.getLargeAngledRunway());
				populateAdvancedTable(field.getLargeAngledRunway(), field.getSmallAngledRunway());
			}
		} catch (UnusableRunwayException e) {
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
		
		tora = new String[]{"TORA", getRunwayPara(def, "tora"), getRunwayPara(run, "tora")};
		toda = new String[]{"TODA", getRunwayPara(def, "toda"), getRunwayPara(run, "toda")};
		asda = new String[]{"ASDA", getRunwayPara(def, "asda"), getRunwayPara(run, "asda")};
		lda = new String[]{"LDA", getRunwayPara(def, "lda"), getRunwayPara(run, "lda")};
		dt = new String[]{"Displaced Threshold", getRunwayPara(def, "dt"), getRunwayPara(run, "dt")};
		
		info.updateRunwayTable(new String[][]{tora, toda, asda, lda, dt});
	}
	
	protected void populateAdvancedTable(DeclaredRunwayInterface def, DeclaredRunwayInterface run) throws UnusableRunwayException{
		String[] resa, blast, clear, stop, angleA, angleD;
		
		stop = new String[]{"Stopway",getRunwayPara(def, "stop"), getRunwayPara(run, "stop")};
		clear = new String[]{"Clearway",getRunwayPara(def, "clear"), getRunwayPara(run, "clear")};
		//blast= new String[]{"Blast Protection", getRunwayPara(def, "blast"), getRunwayPara(run, "blast")};
		//resa = new String[]{"RESA", getRunwayPara(def, "resa"), getRunwayPara(run, "resa")};
		angleA = new String[]{"Angle of Ascent", getRunwayPara(def, "ascent"), getRunwayPara(run, "ascent")};
		angleD = new String[]{"Angle of Descent", getRunwayPara(def, "descent"), getRunwayPara(run, "descent")};
		
		info.updateAdvancedTable(new String[][]{stop, clear, angleA, angleD});
	}
	
	protected void populateObstacleTable(ObstacleInterface obs){
		String[] name, height, radius;
		
		name = new String[]{"Name", getObstaclePara(obs, "name")};
		height = new String[]{"Height", getObstaclePara(obs, "height")};
		radius = new String[]{"Radius", getObstaclePara(obs, "radius")};
		
		info.updateObstacleTable(new String[][]{name, height, radius});
	}
	
	private String getRunwayPara(DeclaredRunwayInterface runway, String para) throws UnusableRunwayException{
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
	
	private String getObstaclePara(ObstacleInterface obs, String para){
		switch(para){
		case "name" : return obs.getName();
		case "radius" : return String.valueOf(obs.getRadius());
		case "height" : return String.valueOf(obs.getHeight());
		}
		return null;
	}
}
