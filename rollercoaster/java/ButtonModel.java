package rollercoaster.models
import models.Sorts.* ;


public class ButtonModel {
	boolean open ;
	
	public  ( boolean open ) {
		self.open = open ;
		
	}
	
	public boolean push_button ( ) {
		if ( open == true ) {
			self.open = false ;
			
		}
	}
	
	
}

