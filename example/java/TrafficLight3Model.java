package example.models
import models.Sorts.* ;


public class TrafficLight3Model {
	boolean is_red ;
	boolean is_yellow ;
	boolean is_green ;
	
	public TrafficLight3Model ( boolean is_red , boolean is_yellow , boolean is_green ) {
		self.is_red = is_red ;
		self.is_yellow = is_yellow ;
		self.is_green = is_green ;
		
	}
	
	public boolean green ( ) {
		if ( is_red == true ) {
			self.is_red = false ;
			self.is_yellow = false ;
			self.is_green = true ;
			
		}
	}
	
	
	public boolean yellow ( ) {
		if ( is_green == true ) {
			self.is_red = false ;
			self.is_yellow = true ;
			self.is_green = false ;
			
		}
	}
	
	
	public boolean red ( ) {
		if ( is_yellow == true ) {
			self.is_red = true ;
			self.is_yellow = false ;
			self.is_green = false ;
			
		}
	}
	
	
}

