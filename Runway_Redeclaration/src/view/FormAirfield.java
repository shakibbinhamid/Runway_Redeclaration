package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FormAirfield extends FormGeneral {
	TopFrame topFrame;
	ArrayList<JTextField> smallValueTextFields;
	ArrayList<JTextField> bigValueTextFields;
	
	JPanel upperPanel;
	JPanel bottomPanel;
	JPanel smallAndLargeInputsPanel;
	
	JLabel smallValuesLabel;
	JLabel largeValuesLabel;
	
	JLabel smallTORALabel;
	JTextField smallTORATextBox;
	JLabel smallStopwayLabel;
	JTextField smallStopwayTextBox;
	JLabel smallClearwayLabel;
	JTextField smallClearwayTextBox;
	JLabel smallDisplacedLabel;
	JTextField smallDisplacedTextBox;
	
	JLabel bigTORALabel;
	JTextField bigTORATextBox;
	JLabel bigStopwayLabel;
	JTextField bigStopwayTextBox;
	JLabel bigClearwayLabel;
	JTextField bigClearwayTextBox;
	JLabel bigDisplacedLabel;
	JTextField bigDisplacedTextBox;
	
	JLabel angleLabel;
	JTextField angleTextBox;

//	JLabel runwayWidthLabel;
//	JTextField runwayWidthTextBox;
//	
//	JLabel runwayLengthLabel;
//	JTextField runwayLengthTextBox;
//	
//	JLabel initialStopwayLabel;
//	JTextField initialStopwayTextBox;
//	
//	JLabel stripSideLengthLabel;
//	JTextField stripSideLengthTextBox;
//	
//	JLabel distanceToLongSpaceLabel;
//	JTextField distanceToLongSpaceTextBox;
//	
//	JLabel shortClearedWidthLabel;
//	JTextField shortClearedWidthTextBox;
//	
//	JLabel longClearedWidthSpaceLabel;
//	JTextField longClearedWidthSpaceTextBox;
//	
//	JLabel fullWidthSpaceLabel;
//	JTextField fullWidthSpaceTextBox;
	
	

	public FormAirfield(TopFrame topFrame) {
		super(topFrame, "Create Airfield",true);
		this.topFrame = topFrame;
		smallValueTextFields = new ArrayList<JTextField>(10);
		bigValueTextFields = new ArrayList<JTextField>(10);
		
		upperPanel = new JPanel();
		bottomPanel = new JPanel();
		smallAndLargeInputsPanel = new JPanel();
		
		smallValuesLabel = new JLabel("Small values:");
		largeValuesLabel = new JLabel("Large values:");
		
		bigTORALabel = new JLabel("Insert big TORA:");
		bigTORATextBox = new JTextField();
		bigStopwayLabel = new JLabel("Insert big Stopway");
		bigStopwayTextBox = new JTextField();
		bigClearwayLabel = new JLabel("Insert big Clearway");
		bigClearwayTextBox = new JTextField();
		bigDisplacedLabel = new JLabel("Insert big Displaced Threshold:");
		bigDisplacedTextBox = new JTextField();
		
		smallTORALabel = new JLabel("Insert small TORA:");
		smallTORATextBox = new JTextField();
		smallStopwayLabel = new JLabel("Insert small Stopway:");
		smallStopwayTextBox = new JTextField();
		smallClearwayLabel = new JLabel("Insert small Clearway:");
		smallClearwayTextBox = new JTextField();
		smallDisplacedLabel = new JLabel("Insert small Displaced Threshold:");
		smallDisplacedTextBox = new JTextField();
		
		angleLabel = new JLabel("Insert airfield angle to the north :");
		angleTextBox = new JTextField();
		
//		runwayWidthLabel = new JLabel("Insert airfield width:");
//		runwayWidthTextBox = new JTextField();
//		
//		runwayLengthLabel = new JLabel("Insert airfield length:");
//		runwayLengthTextBox = new JTextField();
//		
//		initialStopwayLabel  = new JLabel("Insert initial Stopway:");
//		initialStopwayTextBox = new JTextField();
//		
//		stripSideLengthLabel  = new JLabel("Insert strip side length:");
//		stripSideLengthTextBox = new JTextField();
//		
//		distanceToLongSpaceLabel = new JLabel("Insert distance to long:");
//		distanceToLongSpaceTextBox = new JTextField();
//		
//		shortClearedWidthLabel = new JLabel("Insert short cleared width:");
//		shortClearedWidthTextBox = new JTextField();
//		
//		longClearedWidthSpaceLabel = new JLabel("Insert long cleared width:");
//		longClearedWidthSpaceTextBox = new JTextField();
//		
//		fullWidthSpaceLabel = new JLabel("Insert full width:");
//		fullWidthSpaceTextBox = new JTextField();
		setPreferredSize(new Dimension(500,350));
		
		init();
	}
	
	public void init(){
		upperPanel.setLayout(new GridLayout(2,1));
		upperPanel.add(angleLabel);
		upperPanel.add(angleTextBox);
		
		bottomPanel.setLayout(new GridLayout(8, 2));
		
		bottomPanel.add(smallTORALabel);
		bottomPanel.add(bigTORALabel);
		bottomPanel.add(smallTORATextBox);
		bottomPanel.add(bigTORATextBox);
		
		bottomPanel.add(smallStopwayLabel);
		bottomPanel.add(bigStopwayLabel);
		bottomPanel.add(smallStopwayTextBox);
		bottomPanel.add(bigStopwayTextBox);
		
		bottomPanel.add(smallClearwayLabel);
		bottomPanel.add(bigClearwayLabel);
		bottomPanel.add(smallClearwayTextBox);
		bottomPanel.add(bigClearwayTextBox);
		
		bottomPanel.add(smallDisplacedLabel);
		bottomPanel.add(bigDisplacedLabel);
		bottomPanel.add(smallDisplacedTextBox);
		bottomPanel.add(bigDisplacedTextBox);
		
		textFieldsPanel.setLayout(new BorderLayout());
		textFieldsPanel.add(upperPanel, BorderLayout.NORTH);
		textFieldsPanel.add(bottomPanel, BorderLayout.CENTER);
		
		
//		textFieldsPanel.add(runwayWidthLabel);
//		textFieldsPanel.add(runwayWidthTextBox);
//		textFieldsPanel.add(runwayLengthLabel);
//		textFieldsPanel.add(runwayLengthTextBox);
//		textFieldsPanel.add(initialStopwayLabel);
//		textFieldsPanel.add(initialStopwayTextBox);
//		textFieldsPanel.add(stripSideLengthLabel);
//		textFieldsPanel.add(stripSideLengthTextBox);
//		textFieldsPanel.add(distanceToLongSpaceLabel);
//		textFieldsPanel.add(distanceToLongSpaceTextBox);
//		textFieldsPanel.add(shortClearedWidthLabel);
//		textFieldsPanel.add(shortClearedWidthTextBox);
//		textFieldsPanel.add(longClearedWidthSpaceLabel);
//		textFieldsPanel.add(longClearedWidthSpaceTextBox);
//		textFieldsPanel.add(fullWidthSpaceLabel);
//		textFieldsPanel.add(fullWidthSpaceTextBox);
		
		smallValueTextFields.add(smallTORATextBox);
		bigValueTextFields.add(bigTORATextBox);
		smallValueTextFields.add(smallStopwayTextBox);
		bigValueTextFields.add(bigStopwayTextBox);
		smallValueTextFields.add(smallClearwayTextBox);
		bigValueTextFields.add(bigClearwayTextBox);
		smallValueTextFields.add(smallDisplacedTextBox);
		bigValueTextFields.add(bigDisplacedTextBox);
		
		setListener();
		pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	//TODO Make a sanitizing class to make this listener shorter
	public void setListener(){
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(angleTextBox.getText().equals("") || Integer.parseInt(angleTextBox.getText()) < 0){
					JOptionPane.showMessageDialog(null, "Insert a valid angle value!", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				else{
					double[] physicalInputs = {80, 1500, 150, 60, 300, 75, 105, 150};
					double[] smallInputs = new double[4];
					double[] bigInputs = new double[4];
					
					for(int i = 0; i < 4; i++){
							smallInputs[i] = Integer.parseInt( smallValueTextFields.get(i).getText());
							bigInputs[i] = Integer.parseInt( bigValueTextFields.get(i).getText());
					}
						try {
							topFrame.loadOrCreateField(Integer.parseInt(angleTextBox.getText()), physicalInputs, smallInputs, bigInputs);
							dispose();
						} catch (NumberFormatException e) {
							e.printStackTrace();
						}											
				}}});}}
