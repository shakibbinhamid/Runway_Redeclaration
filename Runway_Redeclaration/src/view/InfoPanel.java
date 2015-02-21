package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import CoreInterfaces.DeclaredRunwayInterface;

public class InfoPanel extends JPanel{
	
	private static final String[] runwayColumnNames = {"Parameter","Default", "Redeclared"};
	private static final String[] obstacleColumnNames = {"Parameter", "Value"};
	
	DeclaredRunwayInterface runway;
	
	TablePanel runwayDataTable;
	TablePanel obstacleDataTable;
	TablePanel advancedDataTable;
	
	public InfoPanel(DeclaredRunwayInterface runway){
		
		this.runway = runway;
		init();
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
		
	class TablePanel extends JPanel{
		
		JTable table;
		String title;
		
		DefaultTableModel tableModel = new DefaultTableModel(){
			@Override
			public boolean isCellEditable(int row, int column) {
			       return false;
			}
		};
			
		TablePanel (String panelName, String[] columnNames){
			
			title = panelName;	
			table = new JTable(tableModel);
			
			for(int i=0; i<columnNames.length; i++){
				tableModel.addColumn(columnNames[i]);
			}
			
			table.setFocusable(false);
			table.setRowSelectionAllowed(false);
			
			init();
		}
		
		void init(){
			this.setBorder(BorderFactory.createTitledBorder(title));
			this.setLayout(new BorderLayout());
			this.add(new JScrollPane(table), BorderLayout.CENTER);
		}
		
		void redrawTable(String[][] data){;
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