package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Core.Airport;

public class FormAirfield extends FormGeneral{
	
	public static void main(String[] args) {
		TopFrame tf = new TopFrame();
		Airport airp = new Airport("Gatwicked");
		tf.loadOrCreateAirport(airp);
		FormAirfield fa = new FormAirfield(tf, "Test");
		//FormObstacle fo = new FormObstacle(tf);
	}
	
	TopFrame topFrame;
	ArrayList<JTextField> smallValueTextFields;
	ArrayList<JTextField> bigValueTextFields;
	
	JPanel upperPanel;
		JPanel textFieldsPanel;
	
	JLabel smallValuesLabel;
	JLabel largeValuesLabel;
	
	JTextField smallTORATextBox;
	JTextField smallTODATextBox;
	JTextField smallASDATextBox;
	JTextField smallLDATextBox;
	
	JLabel toraLabel;
	JLabel todaLabel;
	JLabel asdaLabel;
	JLabel ldaLabel;
	
	JTextField bigTORATextBox;
	JTextField bigTODATextBox;
	JTextField bigASDATextBox;
	JTextField bigLDATextBox;
	
	public FormAirfield(TopFrame topFrame, String title) {
		super(topFrame, title);
		this.topFrame = topFrame;
		
		initialiseStuff();
		setToolTips();
		setLayout();
		storeUserInput();
		//init();
	}
	
	public void init(){
		setPreferredSize(new Dimension(400,250));
		pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void initialiseStuff(){
		smallValueTextFields = new ArrayList<JTextField>(10);
		bigValueTextFields = new ArrayList<JTextField>(10);
		
		upperPanel = new JPanel();
		textFieldsPanel = new JPanel();
		
		smallValuesLabel = new JLabel("<html>"+"<i>"+" Left Starting Runway"+"</i>" +" values"+"</html>");
		largeValuesLabel = new JLabel("<html>"+"<i>"+" Right Starting Runway"+"</i>" +" values"+"</html>");
		
		smallValuesLabel.setHorizontalAlignment(SwingConstants.LEFT);
		largeValuesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		smallValuesLabel.setFont(FormAirfield.VER_PL);
		largeValuesLabel.setFont(FormAirfield.VER_PL);
		
		toraLabel = new JLabel("------- TORA -------");
		todaLabel = new JLabel("------- TODA -------");
		asdaLabel = new JLabel("------- ASDA -------");
		ldaLabel = new JLabel("------- LDA -------");
		
		toraLabel.setFont(FormAirfield.VER_IT);
		todaLabel.setFont(FormAirfield.VER_IT);
		asdaLabel.setFont(FormAirfield.VER_IT);
		ldaLabel.setFont(FormAirfield.VER_IT);
		
		bigTORATextBox = new JTextField();
		bigTODATextBox = new JTextField();
		bigASDATextBox = new JTextField();
		bigLDATextBox = new JTextField();
		
		smallTORATextBox = new JTextField();
		smallTODATextBox = new JTextField();
		smallASDATextBox = new JTextField();
		smallLDATextBox = new JTextField();
		
	}
	
	private void setHeaderLabels(String s1, String s2){
		smallValuesLabel.setText(s1);
		largeValuesLabel.setText(s2);
	}
	
	private void storeUserInput(){
		smallValueTextFields.add(smallTORATextBox);
		bigValueTextFields.add(bigTORATextBox);
		smallValueTextFields.add(smallASDATextBox);
		bigValueTextFields.add(bigASDATextBox);
		smallValueTextFields.add(smallTODATextBox);
		bigValueTextFields.add(bigTODATextBox);
		smallValueTextFields.add(smallLDATextBox);
		bigValueTextFields.add(bigLDATextBox);
	}
	
	private void setToolTips(){
		toraLabel.setToolTipText("<html>" + "Take-Off Run Available" + "<br>" + "The length of the runway available for take-off."+ "</html>");
		todaLabel.setToolTipText("<html>" + "Take-Off Distance Available" + "<br>" + "The total distance an aircraft can safely utilise for its take-off and initial ascent." + "<br>" + "TORA + any Clearway."+ "</html>");
		asdaLabel.setToolTipText("<html>" + "Accelerate-Stop Distance Available" + "<br>" + "The total distance available to the aircraft in case of an aborted take-off." + "<br>" + "TORA + any Stopway"+ "</html>");
		ldaLabel.setToolTipText("<html>" + "Landing Distance Available" + "<br>" + "The length of the runway available for landing."+ "</html>");
	}
	
	private void setLayout(){
		centerPanel.setLayout(new BorderLayout());
			centerPanel.add(upperPanel, BorderLayout.NORTH);
			centerPanel.add(textFieldsPanel, BorderLayout.CENTER);
			
		textFieldsPanel.setLayout(new GridLayout(4, 3, 5 ,5));
		
		textFieldsPanel.add(smallTORATextBox);
		toraLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldsPanel.add(toraLabel);
		textFieldsPanel.add(bigTORATextBox);
		
		textFieldsPanel.add(smallTODATextBox);
		todaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldsPanel.add(todaLabel);
		textFieldsPanel.add(bigTODATextBox);
		
		textFieldsPanel.add(smallASDATextBox);
		asdaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldsPanel.add(asdaLabel);
		textFieldsPanel.add(bigASDATextBox);
		
		textFieldsPanel.add(smallLDATextBox);
		ldaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldsPanel.add(ldaLabel);
		textFieldsPanel.add(bigLDATextBox);
	}

}
