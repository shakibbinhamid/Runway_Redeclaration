package view;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import core.PositionedObstacle;

public class FormEditObstacle extends FormObstacle{
	PositionedObstacle currentObstacle;
	
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
		
		for(JTextField jtf : getTextFields()){
			
			if(getTextFields().indexOf(jtf) == 0){
				jtf.setText(currentObstacle.getName());
			}
			if(getTextFields().indexOf(jtf) == 1){
				jtf.setText(String.valueOf(currentObstacle.getRadius()));
			}
			if(getTextFields().indexOf(jtf) == 2){
				jtf.setText(String.valueOf(currentObstacle.getHeight()));
			}
			if(getTextFields().indexOf(jtf) == 3){
				jtf.setText(String.valueOf(currentObstacle.distanceFromSmallEnd()));
			}
			if(getTextFields().indexOf(jtf) == 4){
				jtf.setText(String.valueOf(currentObstacle.distanceFromLargeEnd()));
			}
		}
	}
	
	public void setTextfieldListener(){
		
		for(final JTextField jtf : getTextFields()){
			jtf.addFocusListener(new FocusListener() {
				public void focusLost(FocusEvent e) {
					if (jtf.getText().equals("")){
						if(getTextFields().indexOf(jtf) == 0){
							jtf.setText(currentObstacle.getName());
						}
						if(getTextFields().indexOf(jtf) == 1){
							jtf.setText(String.valueOf(currentObstacle.getRadius()));
						}
						if(getTextFields().indexOf(jtf) == 2){
							jtf.setText(String.valueOf(currentObstacle.getHeight()));
						}
						if(getTextFields().indexOf(jtf) == 3){
							jtf.setText(String.valueOf(currentObstacle.distanceFromSmallEnd()));
						}
						if(getTextFields().indexOf(jtf) == 4){
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
