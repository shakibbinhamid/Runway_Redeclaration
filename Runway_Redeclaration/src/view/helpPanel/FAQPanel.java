package view.helpPanel;

import io.FileSystem;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FAQPanel extends JFrame{
	
	private final String faqText = "./help/faq.txt";
	private final String helpDir = "./help/";
	private final String title = "Frequently Asked Questions";
	private final int scaler = 400;
	
	private ArrayList<QA> questions;
	private JList<QA> questionsList;
	private JTextArea answerBox;
	private JLabel image;
	

	public static void main(String[] args) {
		FAQPanel p = new FAQPanel();

	}
	
	public FAQPanel(){
		questions = new ArrayList<QA>();
		loadText();
		init();
	}

	private void init() {
		
		questions.forEach(x -> System.out.println(x.hasImage()));
		System.out.println();
		
		this.setTitle(title);
		this.setLayout(new BorderLayout());
		
		questionsList = new JList<>(questions.stream().toArray(QA[]::new));
		
		answerBox = new JTextArea();
		answerBox.setLineWrap(true);
		answerBox.setEditable(false);
		answerBox.setFont(new Font(answerBox.getFont().getFontName(), answerBox.getFont().getStyle(), 18));
		answerBox.setBackground(this.getBackground());
		
		questionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		image =  new JLabel();
		
		questionsList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				QA qa = (QA) questionsList.getSelectedValue();
				
				answerBox.setText(qa.getAnswer());
				
				if(qa.hasImage()){
					System.out.println("yh");
					System.out.println("setting image...");
					image.setIcon(qa.getImage());
				}
				else
				{ System.out.println("No");
					image.setIcon(null);
				}
			}
			
		});
		
		this.add(questionsList, BorderLayout.LINE_START);
		
		JPanel answerPanel = new JPanel(new GridLayout());
		answerPanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
		answerPanel.add(answerBox);
		this.add(answerPanel, BorderLayout.CENTER);
		
		this.add(image, BorderLayout.PAGE_END);
		
		this.setSize(800, 600);
		this.setVisible(true);
	}

	private void loadText() {
		
		FileSystem fs = new FileSystem();
		String[] lines = fs.getTextFromFile(new File(faqText)).split("#\r\n");
		
		
		for(int i = 0; i < lines.length; i++){
			String[] qa  = lines[i].split("/");
			if(qa.length == 2){
				questions.add(new QA(qa[0],qa[1]));
			}
			else {
				if (qa.length == 3){
					File imgDir = new File(helpDir + "img0.png");
					if (imgDir.exists()){
						System.out.println("image");
						questions.add(new QA(qa[0], qa[1], new ImageIcon(ImageTools.getScaledImage(new ImageIcon(imgDir.getAbsolutePath()).getImage(), scaler))));
					}
					else{
						questions.add(new QA(qa[0],qa[1]));
					}
				}
			}
			
		}
		
	}

	private void printQA() {
		for(QA qa : questions){
			System.out.println("Question: " + qa.getQuestion());
			System.out.println("Answer: " + qa.getAnswer());
		}
		
	}

}
