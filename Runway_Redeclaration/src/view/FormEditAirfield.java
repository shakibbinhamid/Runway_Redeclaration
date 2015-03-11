package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Core.Airfield;

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
			tf.loadOrCreateField(90, ' ', testSmallValues, testBigValues);
			tf.loadOrCreateAirport(airp);
		} catch (VariableDeclarationException e) {
			e.printStackTrace();
		}
		
		FormEditAirfield fa = new FormEditAirfield(tf);
	}
	
	public FormEditAirfield(TopFrame topFrame){
		super(topFrame, "Edit airfield");
		currentAirfield = (Airfield) topFrame.getTabbePanel().getActiveField();
		init();
	}
	
	public void setTextfieldsListener(){
		
		for(final JTextField jtf : smallValueTextFields){
			jtf.addFocusListener(new FocusListener() {
				Font newTextFieldFont=new Font(jtf.getFont().getName(),Font.ITALIC,jtf.getFont().getSize());
				public void focusLost(FocusEvent e) {
					if (jtf.getText().equals("")){
						jtf.setFont(newTextFieldFont);
						if(smallValueTextFields.indexOf(jtf) == 0){
							jtf.setText("TORA: "+ String.valueOf(currentAirfield.getSmallAngledRunway().getTORA()));
						}
						if(smallValueTextFields.indexOf(jtf) == 1){
							jtf.setText("TODA: "+String.valueOf(currentAirfield.getSmallAngledRunway().getTODA()));
						}
						if(smallValueTextFields.indexOf(jtf) == 2){
							jtf.setText("ASDA: "+ String.valueOf(currentAirfield.getSmallAngledRunway().getASDA()));
						}
						if(smallValueTextFields.indexOf(jtf) == 3){
							jtf.setText("LDA: "+ String.valueOf(currentAirfield.getSmallAngledRunway().getLDA()));
						}
					}
				}
				public void focusGained(FocusEvent e) {
					jtf.setFont(null);
					jtf.setText("");
				}
			});
		}
		
		for(final JTextField jtf : bigValueTextFields){
			jtf.addFocusListener(new FocusListener() {
				Font newTextFieldFont=new Font(jtf.getFont().getName(),Font.ITALIC,jtf.getFont().getSize());
				public void focusLost(FocusEvent e) {
					if (jtf.getText().equals("")){
						jtf.setFont(newTextFieldFont);
						if(bigValueTextFields.indexOf(jtf) == 0){
							jtf.setText(String.valueOf(currentAirfield.getLargeAngledRunway().getTORA()));
						}
						if(bigValueTextFields.indexOf(jtf) == 1){
							jtf.setText(String.valueOf(currentAirfield.getLargeAngledRunway().getTODA()));
						}
						if(bigValueTextFields.indexOf(jtf) == 2){
							jtf.setText(String.valueOf(currentAirfield.getLargeAngledRunway().getASDA()));
						}
						if(bigValueTextFields.indexOf(jtf) == 3){
							jtf.setText(String.valueOf(currentAirfield.getLargeAngledRunway().getLDA()));
						}
					}
				}
				public void focusGained(FocusEvent e) {
					jtf.setFont(null);
					jtf.setText("");
				}
			});
		}
	}
	

	public void setListener(){
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean okToAdd = false;
				try{
					if(angleTextBox.getText().equals("") || Integer.parseInt(angleTextBox.getText()) < 0){
						JOptionPane.showMessageDialog(null, "Insert a valid angle value!", "Error!", JOptionPane.ERROR_MESSAGE);
					}
				} catch (NumberFormatException n) {
					JOptionPane.showMessageDialog(null, "Insert a valid angle value!", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				
				double[] smallInputs = new double[4];
				double[] bigInputs = new double[4];
				int angle = Integer.parseInt(angleTextBox.getText());
				String sideStrig= (String) sideComboBox.getSelectedItem();
				char sideChar = sideStrig.charAt(0);
				
				if(Integer.parseInt(angleTextBox.getText()) > 0){
					for (int i = 0; i < 4; i++) {
						try {
							if (Integer.parseInt(smallValueTextFields.get(i).getText()) < 0
									|| Integer.parseInt(bigValueTextFields.get(i).getText()) < 0
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
								smallInputs[i] = Integer
										.parseInt(smallValueTextFields.get(i)
												.getText());
								bigInputs[i] = Integer.parseInt(bigValueTextFields
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
							angle = Integer.parseInt(angleTextBox.getText());
							sideStrig = (String) sideComboBox.getSelectedItem();
							sideChar = sideStrig.charAt(0);
							topFrame.getTabbePanel().updateTab(new Airfield(angle, sideChar,
									smallInputs, bigInputs));
							dispose();
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(null, e.getMessage(),
									"Invalid Input!", JOptionPane.ERROR_MESSAGE);
						} catch (VariableDeclarationException e) {
							JOptionPane.showMessageDialog(null, e.getMessage(),
									"Invalid Input!", JOptionPane.ERROR_MESSAGE);
						}
				}
			}
		});
	}
}
