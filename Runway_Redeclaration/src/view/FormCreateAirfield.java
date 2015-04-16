package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import view.FormAirfield.JButtonStateController;
import core.Airfield;
import core.DeclaredRunway;
import exceptions.VariableDeclarationException;

public class FormCreateAirfield extends FormAirfield {
	
	JLabel angleLabel;
	SelfCheckingField angleTextBox;
	JLabel sideLabel;
	JComboBox<String> sideComboBox;	
	
	JPanel mainPanel;
	JPanel helpImgPanel;

	public FormCreateAirfield(TopFrame topFrame) {
		super(topFrame, "Create Airfield");
		//setHeaderLabels("Insert Left Starting Runway values", "Insert Right Starting Runway values");
		String[] sides = {" ","L","R","C"};
		angleLabel = new JLabel("Insert Airfield Angle to the North:");
		angleTextBox = new SelfCheckingField(DeclaredRunway.ANGLE_REGEX);
		sideLabel = new JLabel("Choose the Side of the Runway:");
		sideComboBox = new JComboBox<String>(sides);
		
		try {
			addRightImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		angleLabel.setFont(FormAirfield.VER_PL);
		sideLabel.setFont(FormAirfield.VER_PL);
		
		setPreferredSize(new Dimension(1150,300));
		init();
	}
	
	protected void addRightImage() throws IOException{
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		helpImgPanel = new JPanel();
		
		getContentPane().setLayout(new GridLayout(1,1));
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		buttonPanel.add(button);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		
		JLabel picLabel = new JLabel(new ImageIcon(TopFrame.class.getResource("/Help.jpg")), JLabel.CENTER);
		helpImgPanel.add(picLabel);
		helpImgPanel.setBorder(BorderFactory.createTitledBorder("Example Airfield"));
		getContentPane().add(helpImgPanel);
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
		setButtonDeactivated();
		
		pack();
		this.setLocationRelativeTo(topFrame);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	protected void setButtonDeactivated(){
		button.setEnabled(false);
		JButtonStateController jbsc = new JButtonStateControllerExtra(button);
		angleTextBox.getDocument().addDocumentListener(jbsc);
		setTextFieldListeners(smallValueTextFields, jbsc);
		setTextFieldListeners(bigValueTextFields, jbsc);
	}
	
	class JButtonStateControllerExtra extends JButtonStateController {
		JButtonStateControllerExtra(JButton button) {
			super(button);
		}

		public void change(DocumentEvent e) {
			super.change(e);
			
			button.setEnabled(false);

			if (angleTextBox.getText().trim().matches(angleTextBox.getRegex()) && !sideComboBox.getSelectedItem().equals(null) ){
				System.out.println(sideComboBox.getSelectedItem());
				button.setEnabled(true);
			}
		}
	}
	
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
