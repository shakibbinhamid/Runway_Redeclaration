package view;

import interfaces.DeclaredRunwayInterface;
import interfaces.ObstacleInterface;
import interfaces.PositionedObstacleInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import exceptions.UnusableRunwayException;

public class InfoPanel extends JPanel{
	
	private static final String[] runwayColumnNames 	= 	{"Parameter","Default", "Redeclared"};
	private static final String[] obstacleColumnNames 	= 	{"Parameter", "Value"};
	
	private DeclaredRunwayInterface defaultRunway;
	private DeclaredRunwayInterface runway;
	private PositionedObstacleInterface obs;
	
	private TablePanel runwayDataTable;
	private TablePanel obstacleDataTable;
	private TablePanel advancedDataTable;
	
	public InfoPanel(DeclaredRunwayInterface[] runways, PositionedObstacleInterface obs){
		
		this.setRunway(runways[0]);
		this.setRunway(runways[1]);
		this.setObs(obs);
		
		this.setPreferredSize(new Dimension(300,800));
		init();
		
		updateAllTables(runways, obs);
	}
	
	public void init(){
		
		runwayDataTable = new TablePanel("Runway Parameters", runwayColumnNames);
		obstacleDataTable = new TablePanel("Obstacle Data", obstacleColumnNames);
		advancedDataTable = new TablePanel("Advanced Parameters", runwayColumnNames);
			
		this.setLayout(new GridLayout(3,1));
		
		this.add(runwayDataTable);
		this.add(obstacleDataTable);
		this.add(advancedDataTable);
		
	}
	
	//=============================== GETTERS AND SETTERS =============================//
	public DeclaredRunwayInterface getDefaultRunway() {
		return defaultRunway;
	}

	public void setDefaultRunway(DeclaredRunwayInterface defaultRunway) {
		this.defaultRunway = defaultRunway;
	}
	
	public DeclaredRunwayInterface getRunway() {
		return runway;
	}

	public void setRunway(DeclaredRunwayInterface runway) {
		this.runway = runway;
	}

	public ObstacleInterface getObs() {
		return obs;
	}

	public void setObs(PositionedObstacleInterface obs) {
		this.obs = obs;
	}
	
	/**
	 * Update all tables with a single command.
	 * If any of the runways is null, no runway table is updated.
	 * If the obstacle table is null, that table isn't updated.
	 * @param runways default,current
	 * @param obs the obstacle
	 */
	public void updateAllTables(DeclaredRunwayInterface[] runways, PositionedObstacleInterface obs){
		updateRunwayTables(runways);
		updateObstacleTable(obs);
	}
	
	/**
	 * Update the runway tables only with the default runway and current runway.
	 * 
	 * @param runways an array where {airfield.default, airfield.dec}
	 */
	public void updateRunwayTables(DeclaredRunwayInterface[] runways){
		if(runways == null)
			return;
		defaultRunway = runways[0];
		runway = runways[1];
		
		if(defaultRunway != null && runway != null){
			try {
				populateRunwayTable(defaultRunway, runway);
				populateAdvancedTable(defaultRunway, runway);
			} catch (UnusableRunwayException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Update the obstacle table with an obstacle object data
	 * @param obs the obstacle whose data we will update on the table
	 */
	public void updateObstacleTable(PositionedObstacleInterface obs){
		if(obs != null){
			this.obs = obs;
			String[] name, height, radius, distance;
		
			name = new String[]{"Name", getObstaclePara(obs, "name")};
			height = new String[]{"Height", getObstaclePara(obs, "height")};
			radius = new String[]{"Radius", getObstaclePara(obs, "radius")};
			distance = new String[]{"Distance from left side", getObstaclePara(obs, "distance")};
		
			updateObstacleTable(new String[][]{name, height, radius, distance});
		}
	}
	
	private void populateRunwayTable(DeclaredRunwayInterface def, DeclaredRunwayInterface run) throws UnusableRunwayException{
		String[] tora, toda, asda, lda, dt;
		
		tora = new String[]{"TORA", getRunwayPara(def, "tora"), getRunwayPara(run, "tora")};
		toda = new String[]{"TODA", getRunwayPara(def, "toda"), getRunwayPara(run, "toda")};
		asda = new String[]{"ASDA", getRunwayPara(def, "asda"), getRunwayPara(run, "asda")};
		lda = new String[]{"LDA", getRunwayPara(def, "lda"), getRunwayPara(run, "lda")};
		dt = new String[]{"DT", getRunwayPara(def, "dt"), getRunwayPara(run, "dt")};
		
		updateRunwayTable(new String[][]{tora, toda, asda, lda, dt});
	}
	
	private void populateAdvancedTable(DeclaredRunwayInterface def, DeclaredRunwayInterface run) throws UnusableRunwayException{
		String[] resa, blast, clear, stop, angleA, angleD;
		
		stop = new String[]{"Stopway",getRunwayPara(def, "stop"), getRunwayPara(run, "stop")};
		clear = new String[]{"Clearway",getRunwayPara(def, "clear"), getRunwayPara(run, "clear")};
		blast= new String[]{"Blast Protection", getRunwayPara(def, "blast"), getRunwayPara(run, "blast")};
		resa = new String[]{"RESA", getRunwayPara(def, "resa"), getRunwayPara(run, "resa")};
		angleA = new String[]{"Ascent Angle", getRunwayPara(def, "ascent"), getRunwayPara(run, "ascent")};
		angleD = new String[]{"Descent Angle", getRunwayPara(def, "descent"), getRunwayPara(run, "descent")};
		
		updateAdvancedTable(new String[][]{stop, clear, blast, resa, angleA, angleD});
	}
	
	private String getRunwayPara(DeclaredRunwayInterface runway, String para) throws UnusableRunwayException{
		switch(para){
		case "tora": return String.valueOf(runway.getTORA());
		case "asda": return String.valueOf(runway.getASDA());
		case "toda": return String.valueOf(runway.getTODA());
		case "lda": return String.valueOf(runway.getLDA());
		case "dt": return String.valueOf(runway.getDisplacedThreshold());
		
		case "stop": return String.valueOf(runway.getStopway());
		case "resa": return String.valueOf(runway.getRESA());
		case "clear": return String.valueOf(runway.getClearway());
		//case "blast": return String.valueOf();
		case "ascent": return String.valueOf(runway.getAscentAngle());
		case "descent": return String.valueOf(runway.getDescentAngle());
		}
		return null;
	}
	
	private String getObstaclePara(PositionedObstacleInterface obs, String para){
		switch(para){
		case "name" : return obs.getName();
		case "radius" : return String.valueOf(obs.getRadius());
		case "height" : return String.valueOf(obs.getHeight());
		case "distance": return String.valueOf(obs.distanceFromSmallEnd());
		}
		return null;
	}
	
	public void updateRunwayTable(String[][] data){
		updateTable(runwayDataTable, data);
	}
	
	public void updateObstacleTable(String[][] data){
		updateTable(obstacleDataTable, data);
	}
	
	public void updateAdvancedTable(String[][] data){
		updateTable(advancedDataTable, data);
	}	
	
	private void updateTable(TablePanel table, String[][] data){
		table.redrawTable(data);
	}

	/**
	 * This is a titled panel with a table on it
	 * We must be able to update the tables on it.
	 * This class knows nothing about the runway or anything.
	 * It only works as a data holder for any sort of titled table.
	 * We use it for both obstacle and runway data.
	 * 
	 * Primary method is redraw table with an array of rows of data (string)
	 * @author shakib-binhamid
	 *
	 */
	private class TablePanel extends JPanel{
		
		private JTable table;
		private String title;
		
		private DefaultTableModel tableModel = new DefaultTableModel(){
			@Override
			public boolean isCellEditable(int row, int column) {
			       return false;
			}
		};
		
		/**
		 * Cannot change table name and column headers once constructed.
		 */
		TablePanel (String panelName, String[] columnNames){
			
			title = panelName;	
			table = new JTable(tableModel);
			
			for(int i=0; i<columnNames.length; i++){
				tableModel.addColumn(columnNames[i]);
			}
			
			table.getTableHeader().setReorderingAllowed(false);
			
			table.setFocusable(false);
			table.setRowSelectionAllowed(false);
			
			this.setBorder(BorderFactory.createTitledBorder(title));
			this.setLayout(new BorderLayout());
			this.add(new JScrollPane(table), BorderLayout.CENTER);
		}
		
		/*
		 * Every string[] is a row of data of the table.
		 */
		private void redrawTable(String[][] data){;
			int rowCount=tableModel.getRowCount();
			for(int i = rowCount - 1; i >=0; i--){
				tableModel.removeRow(i); 
			}
			for(int i=0; i<data.length; i++){
				tableModel.addRow(data[i]);
			}
			table.revalidate();
		}
	
	}
}