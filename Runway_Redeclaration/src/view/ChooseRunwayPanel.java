package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * This class will be responsible to change between the runways and 
 * @author shakib-binhamid
 *
 */
public class ChooseRunwayPanel extends JPanel{
		
		private Tab tab;
		private JRadioButton smallAngled;
		private JRadioButton bigAngled;
		
		public ChooseRunwayPanel(Tab tab){
			this.tab = tab;
			init();
		}
		
		private void init(){
			
			String small = tab.getField().getSmallAngledRunway().getIdentifier();
			String big = tab.getField().getLargeAngledRunway().getIdentifier();
			
			smallAngled = new JRadioButton(small);
			bigAngled = new JRadioButton(big);
			
			ButtonGroup runwaySelection = new ButtonGroup();
			runwaySelection.add(smallAngled);
			runwaySelection.add(bigAngled);
			
			smallAngled.setSelected(true);
			
			smallAngled.addActionListener(new RunwayChangeListener(smallAngled.getText()));
			bigAngled.addActionListener(new RunwayChangeListener(bigAngled.getText()));
			
			this.setLayout(new GridLayout(1,0));
			this.add(smallAngled);
			this.add(bigAngled);
		}
		
		private class RunwayChangeListener implements ActionListener{
			
			private String runwayId;
			
			private RunwayChangeListener(String runwayId){
				this.runwayId = runwayId;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				tab.changeCurrentRunway(null);//TODO get a tab.getField().getDefaultRunway(String name) tab.getField().getRunway(String name);
			}
			
		}
	}