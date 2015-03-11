package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;


public class FormGeneral extends JDialog {
	protected JPanel textFieldsPanel;		
	protected JPanel buttonPanel;	
	protected JButton button;
	
	public FormGeneral(TopFrame topFrame, String title, boolean modality){
		super(topFrame, title, modality);
		textFieldsPanel = new JPanel();
		buttonPanel = new JPanel();
		button = new JButton("Create");
		
		getContentPane().add(textFieldsPanel, BorderLayout.CENTER);
		buttonPanel.add(button);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.getRootPane().setDefaultButton(button);
				
		
		if (topFrame != null) {
		      Dimension parentSize = topFrame.getSize(); 
		      Point p = topFrame.getLocation(); 
		      setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		      setPreferredSize(new Dimension(300,450));
		    }
	}
	
	public void init(){}
	
}
