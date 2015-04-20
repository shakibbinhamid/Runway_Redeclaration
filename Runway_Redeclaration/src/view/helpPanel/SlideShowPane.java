package view.helpPanel;

import io.CustomFilter;
import io.FileSystem;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SlideShowPane extends JPanel{


	private final String ext = ".png";
	private final String dir = "./help/gettingstarted/";
	
	private int scaler;
	
	
	private String description;
	private boolean isSlideshow;
	private ArrayList<File> slides;
	private JLabel slide;
	private int currentSlide;

	public SlideShowPane (File dir, int scaler){
		this.scaler = scaler;
		currentSlide = 0;
		slide = new JLabel();

		if(dir.isDirectory()){
			isSlideshow = true;
			FileSystem fs = new FileSystem();
			slides = fs.getAllFilteredFiles(dir, new CustomFilter(ext));
			setSlide();
		}
		else
		{
			isSlideshow = false;
			slide.setIcon(new ImageIcon (dir.getAbsolutePath()));
		}
		
		init();
	}
	
	private void init(){
		this.add(slide);
		this.setVisible(true);
	}
	
	public boolean hasNext(){
		if(isSlideshow){
			return currentSlide < (slides.size() - 1);
		}
		else
		{
			return false;
		}
	}
	
	public boolean hasPrev(){
		if(isSlideshow){
			return currentSlide > 0;
		}
		else
		{
			return false;
		}
		
	}

	private void setSlide() {
		File currentS = slides.get(currentSlide);
		
		updateDescription(currentS);
	    slide.setIcon(new ImageIcon(ImageTools.getScaledImage((new ImageIcon(currentS.getAbsolutePath(), description)).getImage(), scaler)));
	}

	private void updateDescription(File currentS) {
		int index = currentS.getName().indexOf(".");
	    String name = currentS.getName().substring(0, index);
	    File des = new File(dir + name + ".txt");
	    FileSystem fs = new FileSystem();
	    if(des.exists()){
	    	description = fs.getTextFromFile(des);
	    }
	    else{
	    	description = "";
	    }
	    
	}
	
	public String getDescription(){
		return description;
	}

	public void nextSlide(){
		addToCounter();
		setSlide();
	}

	public void prevSlide(){
		reduceCounter();
		setSlide();
	}
	
	private void addToCounter(){
		if(currentSlide < slides.size() - 1){
			currentSlide += 1;
		}
	}
	private void reduceCounter(){
		if(currentSlide > 0){
			currentSlide -= 1;
		}
	}
	
}
