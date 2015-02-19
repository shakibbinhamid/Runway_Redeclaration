package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ChooseRunwayPanel extends JPanel{
		
		private Tab tab;
		private JRadioButton smallAngled;
		private JRadioButton bigAngled;
		
		public ChooseRunwayPanel(Tab tab){
			this.tab = tab;
			init();
		}
		
		private void init(){
			smallAngled = new JRadioButton(tab.getField().getSmallAngledRunway().getIdentifier());
			bigAngled = new JRadioButton(tab.getField().getLargeAngledRunway().getIdentifier());
			
			ButtonGroup runwaySelection = new ButtonGroup();
			runwaySelection.add(smallAngled);
			runwaySelection.add(bigAngled);
			
			this.setLayout(new GridLayout(1,0));
			this.add(smallAngled);
			this.add(bigAngled);
		}
		
		class RunwayChangeListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		}
	}