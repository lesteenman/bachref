package rollercoaster.models
import models.Sorts.* ;


public class SwitchModel {
	SwitchPos pos ;
	
	public  ( SwitchPos pos ) {
		self.pos = pos ;
		
	}
	
	public boolean up ( ) {
		if ( pos == down ) {
			self.pos = up ;
			
		}
	}
	
	
	public boolean down ( ) {
		if ( pos == up ) {
			self.pos = down ;
			
		}
	}
	
	
}

