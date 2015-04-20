package view.helpPanel;

import javax.swing.ImageIcon;

public class QA {

	private String question, answer;
	private ImageIcon image;
	
	public QA(String question, String answer, ImageIcon image){
		this.question = question;
		this.answer = answer;
		this.image = image;
	}
	
	public QA(String question, String answer){
		this.question = question;
		this.answer = answer;
		this.image = null;
	}
	
	public ImageIcon getImage(){
		return image;
	}
	
	public boolean hasImage(){
		return (image == null);
	}
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String toString(){
		return question;
	}
}
