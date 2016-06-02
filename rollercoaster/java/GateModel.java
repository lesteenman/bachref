package rollercoaster.models
import models.Sorts.* ;


public class GateModel {
	boolean open ;
	
	public  ( boolean open ) {
		self.open = open ;
		
	}
	
	public boolean open ( ) {
		if ( open == false ) {
			self.open = true ;
			
		}
	}
	
	
	public boolean close ( ) {
		if ( open == true ) {
			self.open = false ;
			
		}
	}
	
	
}

