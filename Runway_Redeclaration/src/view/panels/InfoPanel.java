package view.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import core.interfaces.AirfieldInterface;
import core.interfaces.DeclaredRunwayInterface;
import core.interfaces.ObstacleInterface;
import core.interfaces.PositionedObstacleInterface;

/**
 * This Panel (extends JPanel) and lets you update values for the Airfield and Obstacle values
 * @author Shakib-Bin Hamid
 * 
 * @see {@link TablePanel}
 * @see {@link AirfieldInterface} 
 * @see {@link DeclaredRunwayInterface} 
 * @see {@link ObstacleInterface}   
 * @see {@link JTable} 
 */
public class InfoPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String[] runwayColumnNames 	= 	{"Parameter","Default (m)", "Redeclared (m)"};
	private static final String[] obstacleColumnNames 	= 	{"Parameter", "Value (m)"};

	private AirfieldInterface field;
	private DeclaredRunwayInterface defaultRunway;
	private DeclaredRunwayInterface runway;
	private PositionedObstacleInterface obs;

	private TablePanel runwayDataTable;
	private TablePanel obstacleDataTable;
	private TablePanel advancedDataTable;

	public InfoPanel( AirfieldInterface field){
		init();
		
		DeclaredRunwayInterface[] runways = new DeclaredRunwayInterface[]{field.getDefaultRunways()[0],field.getRunways()[0]};

		this.field = field;
		this.setRunway(runways);
		this.setObs(field.getPositionedObstacle());

		this.setPreferredSize(new Dimension(300,800));
		
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
	
	/**
	 * Returns the current default runway (without any redeclaration)
	 * @return current default runway
	 */
	public DeclaredRunwayInterface getDefaultRunway() {
		return defaultRunway;
	}

	/**
	 * Returns the current runway
	 * @return current runway
	 */
	public DeclaredRunwayInterface getRunway() {
		return runway;
	}
	
	/**
	 * Returns the current Obstacle on the current Airfield
	 * @return the current airfield
	 */
	public ObstacleInterface getObs() {
		return obs;
	}

	/**
	 * Setting a Runway will require the Default and the Current Runway both.
	 * It will update the Runway Data Tables.
	 * @param runways = {Default, Current}
	 */
	public void setRunway(DeclaredRunwayInterface[] runways) {
		this.defaultRunway = runways[0];
		this.runway = runways[1];
		updateRunwayTables(runways);
	}

	/**
	 * Setting a Obstacle will also update the Obstacle table.
	 * @param obs
	 */
	public void setObs(PositionedObstacleInterface obs) {
		this.obs = obs;
		updateObstacleTable(obs);
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
			populateRunwayTable(defaultRunway, runway);
			populateAdvancedTable(defaultRunway, runway);
		}
	}

	/**
	 * Update the obstacle table with an obstacle object data
	 * @param obs the obstacle whose data we will update on the table
	 */
	public void updateObstacleTable(PositionedObstacleInterface obs){
		if(obs != null){
			this.obs = obs;
			String[] name, height, radius, distanceLeft, distanceRight;

			name = new String[]{"Name", getObstaclePara(obs, "name")};
			height = new String[]{"Height", getObstaclePara(obs, "height")};
			radius = new String[]{"Radius", getObstaclePara(obs, "radius")};
			distanceLeft = new String[]{"Distance from "+ field.getSmallAngledRunway().getIdentifier(), getObstaclePara(obs, "distance from left")};
			distanceRight = new String[]{"Distance from "+ field.getLargeAngledRunway().getIdentifier(), getObstaclePara(obs, "distance from right")};

			updateObstacleTable(new String[][]{name, height, radius, distanceLeft, distanceRight});
		}
	}

	private void populateRunwayTable(DeclaredRunwayInterface def, DeclaredRunwayInterface run){
		String[] tora, toda, asda, lda, dt;

		tora = new String[]{"TORA", getRunwayPara(def, "tora"), getRunwayPara(run, "tora")};
		toda = new String[]{"TODA", getRunwayPara(def, "toda"), getRunwayPara(run, "toda")};
		asda = new String[]{"ASDA", getRunwayPara(def, "asda"), getRunwayPara(run, "asda")};
		lda = new String[]{"LDA", getRunwayPara(def, "lda"), getRunwayPara(run, "lda")};
		dt = new String[]{"DT", getRunwayPara(def, "dt"), getRunwayPara(run, "dt")};

		updateRunwayTable(new String[][]{tora, toda, asda, lda, dt});
	}

	private void populateAdvancedTable(DeclaredRunwayInterface def, DeclaredRunwayInterface run) {
		
		String[] resa, blast, clear, stop, angleA, angleD;

		stop = new String[]{"Stopway",getRunwayPara(def, "stop"), getRunwayPara(run, "stop")};
		clear = new String[]{"Clearway",getRunwayPara(def, "clear"), getRunwayPara(run, "clear")};
		blast= new String[]{"Blast Allowance", getRunwayPara(def, "blast"), getRunwayPara(run, "blast")};
		resa = new String[]{"RESA", getRunwayPara(def, "resa"), getRunwayPara(run, "resa")};
		angleA = new String[]{"Ascent Angle", "1:"+getRunwayPara(def, "ascent"), "1:"+getRunwayPara(run, "ascent")};
		angleD = new String[]{"Descent Angle", "1:"+getRunwayPara(def, "descent"), "1:"+getRunwayPara(run, "descent")};

		updateAdvancedTable(new String[][]{stop, clear, blast, resa, angleA, angleD});
	}

	private String getRunwayPara(DeclaredRunwayInterface runway, String para){
		switch(para){
		case "tora": return String.valueOf(runway.getTORA());
		case "asda": return String.valueOf(runway.getASDA());
		case "toda": return String.valueOf(runway.getTODA());
		case "lda": return String.valueOf(runway.getLDA());
		case "dt": return String.valueOf(runway.getDisplacedThreshold());

		case "stop": return String.valueOf(runway.getStopway());
		case "resa": return String.valueOf(runway.getRESA());
		case "clear": return String.valueOf(runway.getClearway());
		case "blast": return String.valueOf(field.getBlastAllowance());
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
		case "distance from left": return String.valueOf(obs.distanceFromSmallEnd());
		case "distance from right": return String.valueOf(obs.distanceFromLargeEnd());
		}
		return null;
	}

	private void updateRunwayTable(String[][] data){
		updateTable(runwayDataTable, data);
	}

	private void updateObstacleTable(String[][] data){
		updateTable(obstacleDataTable, data);
	}

	private void updateAdvancedTable(String[][] data){
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
	 * @author Shakib-Bin Hamid
	 *
	 */
	private class TablePanel extends JPanel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private JTable table;
		private String title;

		private DefaultTableModel tableModel = new DefaultTableModel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		/**
		 * Cannot change table name and column headers once constructed.
		 */
		private TablePanel (String panelName, String[] columnNames){

			title = panelName;	

			for(int i=0; i<columnNames.length; i++){
				tableModel.addColumn(columnNames[i]);
			}
			table = new JTable(tableModel);

			table.getTableHeader().setReorderingAllowed(false);

			table.setFocusable(false);
			table.setRowSelectionAllowed(false);
			table.setFillsViewportHeight( true );
			
			this.setBorder(BorderFactory.createTitledBorder(title));
			this.setLayout(new BorderLayout());
			this.add(new JScrollPane(table), BorderLayout.CENTER);
		}

		/*
		 * Every string[] is a row of data of the table.
		 */
		private void redrawTable(String[][] data){
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