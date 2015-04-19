package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import core.Airport;
import core.DeclaredRunway;

public class FormAirfield extends FormGeneral{
	
	protected TopFrame topFrame;
	protected ArrayList<SelfCheckingField> smallValueTextFields;
	protected ArrayList<SelfCheckingField> bigValueTextFields;
	
	protected JPanel upperPanel;
		protected JPanel textFieldsPanel;
	
	protected JLabel smallValuesLabel, largeValuesLabel;
	protected SelfCheckingField smallTORATextBox, smallTODATextBox, smallASDATextBox, smallLDATextBox;
	protected JLabel toraLabel,todaLabel,asdaLabel,ldaLabel;
	protected SelfCheckingField bigTORATextBox,  bigTODATextBox, bigASDATextBox, bigLDATextBox;
	
	public FormAirfield(TopFrame topFrame, String title) {
		super(topFrame, title);
		this.topFrame = topFrame;
		
		initialiseStuff();
		setToolTips();
		storeUserInput();
		setLayout();
	}
	
	public void init(){
		setPreferredSize(new Dimension(400,250));
		pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	protected void setButtonDeactivated(){
		button.setEnabled(false);
		JButtonStateController jbsc = new JButtonStateController(button);
		setTextFieldListeners(smallValueTextFields, jbsc);
		setTextFieldListeners(bigValueTextFields, jbsc);
	}
	
	public void setTextFieldListeners(ArrayList<SelfCheckingField> smallValueTextFields2, DocumentListener docListener){
		for(JTextField jtf : smallValueTextFields2){
			Document doc = jtf.getDocument();
			doc.addDocumentListener(docListener);
		}
	}
	
	private void initialiseStuff(){
		smallValueTextFields = new ArrayList<>(10);
		bigValueTextFields = new ArrayList<>(10);
		
		upperPanel = new JPanel();
		textFieldsPanel = new JPanel();
		
		smallValuesLabel = new JLabel("<html>"+"<i>"+" Left Starting Runway"+"</i>" +" (meters)"+"</html>");
		largeValuesLabel = new JLabel("<html>"+"<i>"+" Right Starting Runway"+"</i>" +" (meters)"+"</html>");
		
		smallValuesLabel.setHorizontalAlignment(SwingConstants.LEFT);
		largeValuesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		smallValuesLabel.setFont(FormAirfield.VER_PL);
		largeValuesLabel.setFont(FormAirfield.VER_PL);
		
		toraLabel = new JLabel("------- TORA -------");
		todaLabel = new JLabel("------- TODA -------");
		asdaLabel = new JLabel("------- ASDA -------");
		ldaLabel = new JLabel("------- LDA -------");
		
		toraLabel.setFont(FormAirfield.VER_IT);
		todaLabel.setFont(FormAirfield.VER_IT);
		asdaLabel.setFont(FormAirfield.VER_IT);
		ldaLabel.setFont(FormAirfield.VER_IT);
		
		bigTORATextBox = new SelfCheckingField(DeclaredRunway.DIST_REGEX);
		bigTODATextBox = new SelfCheckingField(DeclaredRunway.DIST_REGEX);
		bigASDATextBox = new SelfCheckingField(DeclaredRunway.DIST_REGEX);
		bigLDATextBox = new SelfCheckingField(DeclaredRunway.DIST_REGEX);
		
		smallTORATextBox = new SelfCheckingField(DeclaredRunway.DIST_REGEX);
		smallTODATextBox = new SelfCheckingField(DeclaredRunway.DIST_REGEX);
		smallASDATextBox = new SelfCheckingField(DeclaredRunway.DIST_REGEX);
		smallLDATextBox = new SelfCheckingField(DeclaredRunway.DIST_REGEX);
		
	}
	
	private void storeUserInput(){
		smallValueTextFields.add(smallTORATextBox);
		bigValueTextFields.add(bigTORATextBox);
		smallValueTextFields.add(smallASDATextBox);
		bigValueTextFields.add(bigASDATextBox);
		smallValueTextFields.add(smallTODATextBox);
		bigValueTextFields.add(bigTODATextBox);
		smallValueTextFields.add(smallLDATextBox);
		bigValueTextFields.add(bigLDATextBox);
	}
	
	private void setToolTips(){
		toraLabel.setToolTipText("<html>" + "Take-Off Run Available" + "<br>" + "The length of the runway available for take-off."+ "</html>");
		todaLabel.setToolTipText("<html>" + "Take-Off Distance Available" + "<br>" + "The total distance an aircraft can safely utilise for its take-off and initial ascent." + "<br>" + "TORA + any Clearway."+ "</html>");
		asdaLabel.setToolTipText("<html>" + "Accelerate-Stop Distance Available" + "<br>" + "The total distance available to the aircraft in case of an aborted take-off." + "<br>" + "TORA + any Stopway"+ "</html>");
		ldaLabel.setToolTipText("<html>" + "Landing Distance Available" + "<br>" + "The length of the runway available for landing."+ "</html>");
	}
	
	private void setLayout(){
		centerPanel.setLayout(new BorderLayout());
			centerPanel.add(upperPanel, BorderLayout.NORTH);
			centerPanel.add(textFieldsPanel, BorderLayout.CENTER);
			
		textFieldsPanel.setLayout(new GridLayout(4, 3, 5 ,5));
		
		textFieldsPanel.add(smallTORATextBox);
		toraLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldsPanel.add(toraLabel);
		textFieldsPanel.add(bigTORATextBox);
		
		textFieldsPanel.add(smallTODATextBox);
		todaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldsPanel.add(todaLabel);
		textFieldsPanel.add(bigTODATextBox);
		
		textFieldsPanel.add(smallASDATextBox);
		asdaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldsPanel.add(asdaLabel);
		textFieldsPanel.add(bigASDATextBox);
		
		textFieldsPanel.add(smallLDATextBox);
		ldaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldsPanel.add(ldaLabel);
		textFieldsPanel.add(bigLDATextBox);
	}

	// Listener used to active/deactivate main button if the text fields are empty
	class JButtonStateController implements DocumentListener {
		JButton button; 

		JButtonStateController(JButton button) {
			this.button = button;
		}

		public void changedUpdate(DocumentEvent e) {
			change(e);
		}

		public void insertUpdate(DocumentEvent e) {
			change(e);
		}

		public void removeUpdate(DocumentEvent e) {
			change(e);
		}

		public void change(DocumentEvent e) {

			boolean enabled = true;
			for (int i = 0; i < smallValueTextFields.size(); i++) {
				SelfCheckingField f1 = smallValueTextFields.get(i);
				SelfCheckingField f2 = bigValueTextFields.get(i);
				if (!f1.getText().trim().matches(f1.getRegex()) || !f2.getText().trim().matches(f2.getRegex())){
					enabled = false;
					break;
				}
			}
			button.setEnabled(enabled);
		}
	}
}
