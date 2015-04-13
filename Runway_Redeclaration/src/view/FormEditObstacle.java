package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import core.Obstacle;
import core.PositionedObstacle;

public class FormEditObstacle extends FormObstacle{
	PositionedObstacle currentObstacle;
	
//	//TESTING
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
//			Obstacle obj = new Obstacle("Dick",2,2);
//			tf.getTabbePanel().getActiveField().addObstacle(obj, 50, 3000); // this method throws a weird Exception
//			tf.loadOrCreateAirport(airp);
//		} catch (VariableDeclarationException e) {
//			e.printStackTrace();
//		}
//		FormEditObstacle feo = new FormEditObstacle(tf);
//	}
	
	public FormEditObstacle(TopFrame tf){
		super(tf, "Edit obstacle");
		currentObstacle = (PositionedObstacle) topFrame.getTabbePanel().getActiveTab().getObs();
		setInitialTextfields();
		setTextfieldListener();
		setPreferredSize(new Dimension(300,350));
		button.setText("Apply");
		init();
	}
	
	public void setInitialTextfields(){
		
		for(JTextField jtf : textFields){
			
			if(textFields.indexOf(jtf) == 0){
				jtf.setText(currentObstacle.getName());
			}
			if(textFields.indexOf(jtf) == 1){
				jtf.setText(String.valueOf(currentObstacle.getRadius()));
			}
			if(textFields.indexOf(jtf) == 2){
				jtf.setText(String.valueOf(currentObstacle.getHeight()));
			}
			if(textFields.indexOf(jtf) == 3){
				jtf.setText(String.valueOf(currentObstacle.distanceFromSmallEnd()));
			}
			if(textFields.indexOf(jtf) == 4){
				jtf.setText(String.valueOf(currentObstacle.distanceFromLargeEnd()));
			}
		}
	}
	
	public void setTextfieldListener(){
		
		for(final JTextField jtf : textFields){
			jtf.addFocusListener(new FocusListener() {
				public void focusLost(FocusEvent e) {
					if (jtf.getText().equals("")){
						if(textFields.indexOf(jtf) == 0){
							jtf.setText(currentObstacle.getName());
						}
						if(textFields.indexOf(jtf) == 1){
							jtf.setText(String.valueOf(currentObstacle.getRadius()));
						}
						if(textFields.indexOf(jtf) == 2){
							jtf.setText(String.valueOf(currentObstacle.getHeight()));
						}
						if(textFields.indexOf(jtf) == 3){
							jtf.setText(String.valueOf(currentObstacle.distanceFromSmallEnd()));
						}
						if(textFields.indexOf(jtf) == 4){
							jtf.setText(String.valueOf(currentObstacle.distanceFromLargeEnd()));
						}
					}
				}
				
				public void focusGained(FocusEvent e) {
				}
			});
		}
	}
}
