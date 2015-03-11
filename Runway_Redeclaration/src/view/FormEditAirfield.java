package view;

import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import Core.Airfield;
import Core.Airport;
import Exceptions.VariableDeclarationException;

public class FormEditAirfield extends FormAirfield{
	Airfield currentAirfield;
	
//	// TESTING 
//	public static void main(String[] args) {
//		test();
//	}
//	
//	public static void test(){
//		double[] testSmallValues = {3500,4000,4500,2500};
//		double[] testBigValues = {4000,4500,5000,3000};
//		TopFrame tf = new TopFrame();
//		Airport airp = new Airport("Gatwicked");
//		tf.loadOrCreateAirport(airp);
//		try {
//			tf.loadOrCreateField(90, ' ', testSmallValues, testBigValues);
//			tf.loadOrCreateAirport(airp);
//		} catch (VariableDeclarationException e) {
//			e.printStackTrace();
//		}
//		
//		FormEditAirfield fa = new FormEditAirfield(tf);
//	}
	
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
							jtf.setText(String.valueOf(currentAirfield.getSmallAngledRunway().getTORA()));
						}
						if(smallValueTextFields.indexOf(jtf) == 1){
							jtf.setText(String.valueOf(currentAirfield.getSmallAngledRunway().getTODA()));
						}
						if(smallValueTextFields.indexOf(jtf) == 2){
							jtf.setText(String.valueOf(currentAirfield.getSmallAngledRunway().getASDA()));
						}
						if(smallValueTextFields.indexOf(jtf) == 3){
							jtf.setText(String.valueOf(currentAirfield.getSmallAngledRunway().getLDA()));
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
}
