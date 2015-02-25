package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import CoreInterfaces.DeclaredRunwayInterface;

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
			
			smallAngled.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					tab.changeCurrentRunway(new DeclaredRunwayInterface[]{ tab.getField().getDefaultSmallAngledRunway()
																		, tab.getField().getSmallAngledRunway()});
				}});
			bigAngled.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					tab.changeCurrentRunway(new DeclaredRunwayInterface[]{ tab.getField().getDefaultLargeAngledRunway()
																		, tab.getField().getLargeAngledRunway()});
				}});
			this.setLayout(new GridLayout(1,0));
			this.add(smallAngled);
			this.add(bigAngled);
		}
	}