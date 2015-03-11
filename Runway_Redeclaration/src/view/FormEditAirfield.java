package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Core.Airfield;
import Core.Airport;
import Exceptions.VariableDeclarationException;

public class FormEditAirfield extends FormAirfield{
	Airfield currentAirfield;
	
	// TESTING 
	public static void main(String[] args) {
		test();
	}
	
	public static void test(){
		double[] testSmallValues = {3500,4000,4500,2500};
		double[] testBigValues = {4000,4500,5000,3000};
		TopFrame tf = new TopFrame();
		Airport airp = new Airport("Gatwicked");
		tf.loadOrCreateAirport(airp);
		try {
			tf.loadOrCreateField(90, 'L', testSmallValues, testBigValues);
			tf.loadOrCreateAirport(airp);
		} catch (VariableDeclarationException e) {
			e.printStackTrace();
		}
		
		FormEditAirfield fa = new FormEditAirfield(tf);
	}
	
	public FormEditAirfield(TopFrame topFrame){
		super(topFrame, "Edit airfield");
		currentAirfield = (Airfield) topFrame.getTabbePanel().getActiveField();
		setUpEditingForm();
		init();
	}
	
	public void setUpEditingForm(){
		button.setText("Apply");
		angleLabel = new JLabel("Airfield ");
		angleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		angleTextBox.setText(currentAirfield.getName());
		angleTextBox.setEditable(false);
		sideLabel.setText(currentAirfield.getName());
		sideComboBox.setSelectedItem(currentAirfield.getSmallAngledRunway().getSideLetter());
		sideComboBox.setEditable(false);
		
	}
public void init(){
		
		
		upperPanel.setLayout(new GridLayout(3,2));
		upperPanel.add(angleLabel);
		upperPanel.add(sideLabel);
		//upperPanel.add(angleTextBox);
		upperPanel.add(new JLabel());
//		upperPanel.add(sideComboBox);
		upperPanel.add(new JLabel());
		
		String dan = topFrame.getTabbePanel().getActiveField().getDefaultSmallAngledRunway().getIdentifier();
		smallValuesLabel.setText(dan + " Runway Parameters");
		smallValuesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		upperPanel.add(smallValuesLabel);
		String charlie = topFrame.getTabbePanel().getActiveField().getDefaultLargeAngledRunway().getIdentifier();
		largeValuesLabel.setText(charlie + " Runway Parameters");
		largeValuesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		upperPanel.add(largeValuesLabel);
		
		bottomPanel.setLayout(new GridLayout(5, 3));
		
		
//		bottomPanel.add(smallTORALabel);
//		bottomPanel.add(bigTORALabel);
		bottomPanel.add(smallTORATextBox);
		JLabel toraLabel = new JLabel("TORA");
		toraLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bottomPanel.add(toraLabel);
		bottomPanel.add(bigTORATextBox);
		
//		bottomPanel.add(smallTODALabel);
//		bottomPanel.add(bigTODALabel);
		bottomPanel.add(smallTODATextBox);
		JLabel todaLabel = new JLabel("TODA");
		todaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bottomPanel.add(todaLabel);;
		bottomPanel.add(bigTODATextBox);
		
//		bottomPanel.add(smallASDALabel);
//		bottomPanel.add(bigASDALabel);
		bottomPanel.add(smallASDATextBox);
		JLabel asdaLabel = new JLabel("ASDA");
		asdaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bottomPanel.add(asdaLabel);		
		bottomPanel.add(bigASDATextBox);
		
//		bottomPanel.add(smallLDALabel);
//		bottomPanel.add(bigLDALabel);
		bottomPanel.add(smallLDATextBox);
		JLabel ldaLabel = new JLabel("LDA");
		ldaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bottomPanel.add(ldaLabel);
		bottomPanel.add(bigLDATextBox);
		
		textFieldsPanel.setLayout(new BorderLayout());
		textFieldsPanel.add(upperPanel, BorderLayout.NORTH);
		textFieldsPanel.add(bottomPanel, BorderLayout.CENTER);
		
		smallValueTextFields.add(smallTORATextBox);
		bigValueTextFields.add(bigTORATextBox);
		smallValueTextFields.add(smallASDATextBox);
		bigValueTextFields.add(bigASDATextBox);
		smallValueTextFields.add(smallTODATextBox);
		bigValueTextFields.add(bigTODATextBox);
		smallValueTextFields.add(smallLDATextBox);
		bigValueTextFields.add(bigLDATextBox);
		
		
//		for(int i=0;i<4;i++){
//			Font newTextFieldFont; 
//			JTextField currentSmall =  smallValueTextFields.get(i);
//			JTextField currentBig =  bigValueTextFields.get(i);
//			newTextFieldFont = new Font(currentSmall.getFont().getName(),Font.ITALIC,currentSmall.getFont().getSize());
//			currentSmall.setFont(newTextFieldFont);
//			currentBig.setFont(newTextFieldFont);
//		}
		
		
		setTextfieldsListener();
		setListener();
		pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void setTextfieldsListener(){
		
		for(final JTextField jtf : smallValueTextFields){
			jtf.addFocusListener(new FocusListener() {
				Font newTextFieldFont=new Font(jtf.getFont().getName(),Font.ITALIC,jtf.getFont().getSize());
				public void focusLost(FocusEvent e) {
					if (jtf.getText().equals("")){
						//jtf.setFont(newTextFieldFont);
						if(smallValueTextFields.indexOf(jtf) == 0){
							jtf.setText(String.valueOf(currentAirfield.getSmallAngledRunway().getTORA()));
						}
						if(smallValueTextFields.indexOf(jtf) == 2){
							jtf.setText(String.valueOf(currentAirfield.getSmallAngledRunway().getTODA()));
						}
						if(smallValueTextFields.indexOf(jtf) == 1){
							jtf.setText(String.valueOf(currentAirfield.getSmallAngledRunway().getASDA()));
						}
						if(smallValueTextFields.indexOf(jtf) == 3){
							jtf.setText(String.valueOf(currentAirfield.getSmallAngledRunway().getLDA()));
						}
					}
				}
				public void focusGained(FocusEvent e) {
					jtf.setFont(null);
					//jtf.setText("");
				}
			});
		}
		
		for(final JTextField jtf : bigValueTextFields){
			jtf.addFocusListener(new FocusListener() {
				Font newTextFieldFont=new Font(jtf.getFont().getName(),Font.ITALIC,jtf.getFont().getSize());
				public void focusLost(FocusEvent e) {
					if (jtf.getText().equals("")){
						//jtf.setFont(newTextFieldFont);
						if(bigValueTextFields.indexOf(jtf) == 0){
							jtf.setText(String.valueOf(currentAirfield.getLargeAngledRunway().getTORA()));
						}
						if(bigValueTextFields.indexOf(jtf) == 2){
							jtf.setText(String.valueOf(currentAirfield.getLargeAngledRunway().getTODA()));
						}
						if(bigValueTextFields.indexOf(jtf) == 1){
							jtf.setText(String.valueOf(currentAirfield.getLargeAngledRunway().getASDA()));
						}
						if(bigValueTextFields.indexOf(jtf) == 3){
							jtf.setText(String.valueOf(currentAirfield.getLargeAngledRunway().getLDA()));
						}
					}
				}
				public void focusGained(FocusEvent e) {
					jtf.setFont(null);
					//jtf.setText("");
				}
			});
		}
	}
	

	public void setListener(){
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean okToAdd = false;
				
				System.out.println(smallValueTextFields.get(0).getText());
				//System.out.println(currentAirfield.);
				
				double[] smallInputs = new double[4];
				double[] bigInputs = new double[4];
				int angle ;
				//String sideStrig= (String) sideComboBox.getSelectedItem();
	;
				
					for (int i = 0; i < 4; i++) {
						try {
							if (Double.parseDouble(smallValueTextFields.get(i).getText()) < 0
									|| Double.parseDouble(bigValueTextFields.get(i).getText()) < 0
									|| smallValueTextFields.get(i).getText().equals("")
									|| bigValueTextFields.get(i).getText().equals("")) {
								System.err.println("Invalid inputs!");
								JOptionPane.showMessageDialog(null,
										"Please input only integers!",
										"Invalid Input!",
										JOptionPane.ERROR_MESSAGE);
								break;
							} else {
								okToAdd = true;
								smallInputs[i] = Double.parseDouble(smallValueTextFields.get(i)
												.getText());
								bigInputs[i] = Double.parseDouble(bigValueTextFields
										.get(i).getText());
							}
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(null,
									"Please input only integers!",
									"Invalid Input!", JOptionPane.ERROR_MESSAGE);
							break;
						}
					}
				
					if (okToAdd)
						try {
							angle = currentAirfield.getDefaultSmallAngledRunway().getAngle();
							char sideChar = currentAirfield.getSmallAngledRunway().getSideLetter();
							Airfield f = new Airfield(angle, sideChar,
									smallInputs, bigInputs);
							System.out.println(f.getName());
							topFrame.getTabbePanel().updateTab(new Airfield(angle, sideChar,
									smallInputs, bigInputs));
							System.out.println(angle);
							System.out.println(sideChar);

							dispose();
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(null, e.getMessage(),
									"Invalid Input!", JOptionPane.ERROR_MESSAGE);
						} catch (VariableDeclarationException e) {
							JOptionPane.showMessageDialog(null, e.getMessage(),
									"Invalid Input!", JOptionPane.ERROR_MESSAGE);
						}
			}
		});
	}
}
