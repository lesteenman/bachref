package example.models ;
import example.models.Sorts.* ;


public class TrafficLight3Model {
	public boolean is_red ;
	public boolean is_yellow ;
	public boolean is_green ;
	
	public TrafficLight3Model ( boolean is_red , boolean is_yellow , boolean is_green ) {
		this.is_red = is_red ;
		this.is_yellow = is_yellow ;
		this.is_green = is_green ;
		
	}
	
	public boolean green ( ) {
		if ( is_red == true ) {
			this.is_red = false ;
			this.is_yellow = false ;
			this.is_green = true ;
			return true ;
			
		}
		return false;
	}
	
	
	public boolean yellow ( ) {
		if ( is_green == true ) {
			this.is_red = false ;
			this.is_yellow = true ;
			this.is_green = false ;
			return true ;
			
		}
		return false;
	}
	
	
	public boolean red ( ) {
		if ( is_yellow == true ) {
			this.is_red = true ;
			this.is_yellow = false ;
			this.is_green = false ;
			return true ;
			
		}
		return false;
	}
	
	
}

