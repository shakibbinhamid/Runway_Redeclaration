package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Core.Airport;
import Core.ParrallelRunwayException;
import Exceptions.CannotMakeRunwayException;
import Exceptions.VariableDeclarationException;

public class ObstacleListener implements ActionListener{

	TopFrame topFrame;
	//test
	public static void main(String[] args) throws ParrallelRunwayException, CannotMakeRunwayException, VariableDeclarationException {
		TopFrame tf = new TopFrame();
		tf.loadOrCreateAirport(new Airport("dan"));
		double[] dim = {1,1,1,1,1,1,1,1};
		double[] small = {1,1,1,1};
		double[] big = {1,1,1,1};
		tf.getAirport().addNewAirfield(9, dim, small, big);
		FormObstacle fo = new FormObstacle(tf);
		fo.init();
	}
	
	public ObstacleListener(TopFrame topFrame){
		this.topFrame = topFrame;
	}
	
	public void actionPerformed(ActionEvent e) {		
		FormObstacle fo = new FormObstacle(topFrame);
		fo.init();
	}

}
