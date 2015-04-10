package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import exceptions.VariableDeclarationException;

public class FormCreateAirfield extends FormAirfield {
	
	JLabel angleLabel;
	JTextField angleTextBox;
	JLabel sideLabel;
	JComboBox<String> sideComboBox;	

	public FormCreateAirfield(TopFrame topFrame) {
		super(topFrame, "Create Airfield");
		//setHeaderLabels("Insert Left Starting Runway values", "Insert Right Starting Runway values");
		String[] sides = {" ","L","R","C"};
		angleLabel = new JLabel("Insert Airfield Angle to the North:");
		angleTextBox = new JTextField();
		sideLabel = new JLabel("Choose the Side of the Runway:");
		sideComboBox = new JComboBox<String>(sides);
		
		angleLabel.setFont(FormAirfield.VER_PL);
		sideLabel.setFont(FormAirfield.VER_PL);
		
		setPreferredSize(new Dimension(500,300));
		init();
	}
	
	public void init(){
		upperPanel.setLayout(new GridLayout(3,2));
		upperPanel.add(angleLabel);
		upperPanel.add(sideLabel);
		upperPanel.add(angleTextBox);
		upperPanel.add(sideComboBox);
		upperPanel.add(smallValuesLabel);
		largeValuesLabel.setAlignmentX(RIGHT_ALIGNMENT);
		upperPanel.add(largeValuesLabel);
		
		setButtonListener();
		pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	
//	public void setTextfieldsListener(final ArrayList<JTextField> textFields){
//		for(final JTextField jtf : textFields){
//			jtf.addFocusListener(new FocusListener() {
//				Font newTextFieldFont=new Font(jtf.getFont().getName(),Font.ITALIC,jtf.getFont().getSize());
//				public void focusLost(FocusEvent e) {
//					if (jtf.getText().equals("")){
//						//jtf.setFont(newTextFieldFont);
//						if(textFields.indexOf(jtf) == 0){
//							jtf.setText("TORA");
//						}
//						if(textFields.indexOf(jtf) == 2){
//							jtf.setText("TODA");
//						}
//						if(textFields.indexOf(jtf) == 1){
//							jtf.setText("ASDA");
//						}
//						if(textFields.indexOf(jtf) == 3){
//							jtf.setText("LDA");
//						}
//					}
//				}
//				public void focusGained(FocusEvent e) {
//					if (jtf.getText().equals("TORA")
//							|| jtf.getText().equals("TODA")
//							|| jtf.getText().equals("ASDA")
//							|| jtf.getText().equals("LDA")) {
//						jtf.setText("");
//					}
//				}
//			});
//		}
//	}
	
	public void setButtonListener(){
		
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
					
					okToAdd = true;
				
					if (okToAdd){
						try {
							angle = Integer.parseInt(angleTextBox.getText());
							sideStrig = (String) sideComboBox.getSelectedItem();
							sideChar = sideStrig.charAt(0);
							topFrame.loadOrCreateField(angle, sideChar,
									smallInputs, bigInputs);
							dispose();
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(null, e.getMessage(),
									"Invalid Input!", JOptionPane.ERROR_MESSAGE);
						} catch (VariableDeclarationException e) {
							JOptionPane.showMessageDialog(null, e.getMessage(),
									"Invalid Input!", JOptionPane.ERROR_MESSAGE);
						}
					}else
						JOptionPane.showMessageDialog(topFrame, "Some inputs were not entered properly", "FAILED TO CREATE AIRFIELD", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
