package example.models
import models.Sorts.* ;


public class TrafficLight1Model {
	boolean is_red ;
	boolean is_yellow ;
	boolean is_green ;
	
	public TrafficLight1Model ( boolean is_red , boolean is_yellow , boolean is_green ) {
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
	
	
	public allowsTrafficLight2Green ( ) {
		if ( is_red == true ) {
			return true ;
			
		}
		return false ;
	}
	
	public allowsTrafficLight2Yellow ( ) {
		if ( is_green == true ) {
			return true ;
			
		}
		return false ;
	}
	
	public allowsTrafficLight3Green ( ) {
		if ( is_red == true ) {
			return true ;
			
		}
		return false ;
	}
	
}

