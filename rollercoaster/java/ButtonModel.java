package rollercoaster.models
import models.Sorts.* ;


public class ButtonModel {
	boolean open ;
	
	public ButtonModel ( boolean open ) {
		self.open = open ;
		
	}
	
	public boolean push_button ( ) {
		if ( open == true ) {
			self.open = false ;
			
		}
	}
	
	
	public allowsCart2OpenBraces ( Segment segment1 ) {
		if ( open == false ) {
			if ( (
				segment1 == station ||
				segment1 == lift ||
				segment1 == main ||
				segment1 == braking ||
				segment1 == repair
			) ) {
				return true ;
			}
		}
		return false ;
	}
	
	public allowsCart2CloseBraces ( ) {
		if ( open == false ) {
			return true ;
			
		}
		return false ;
	}
	
	public allowsCart2Forward ( Segment segment1 , Segment segment2 ) {
		if ( open == false ) {
			if ( segment1 == station && (
				segment2 == station ||
				segment2 == lift ||
				segment2 == main ||
				segment2 == braking ||
				segment2 == repair
			) ) {
				return true ;
			}
		}
		if ( (
			segment1 == station ||
			segment1 == lift ||
			segment1 == main ||
			segment1 == braking ||
			segment1 == repair
		) && (
			segment2 == station ||
			segment2 == lift ||
			segment2 == main ||
			segment2 == braking ||
			segment2 == repair
		) ) {
			return true ;
		}
		return false ;
	}
	
	public allowsCart1OpenBraces ( Segment segment1 ) {
		if ( open == false ) {
			if ( (
				segment1 == station ||
				segment1 == lift ||
				segment1 == main ||
				segment1 == braking ||
				segment1 == repair
			) ) {
				return true ;
			}
		}
		return false ;
	}
	
	public allowsCart1CloseBraces ( ) {
		if ( open == false ) {
			return true ;
			
		}
		return false ;
	}
	
	public allowsCart1Forward ( Segment segment1 , Segment segment2 ) {
		if ( open == false ) {
			if ( segment1 == station && (
				segment2 == station ||
				segment2 == lift ||
				segment2 == main ||
				segment2 == braking ||
				segment2 == repair
			) ) {
				return true ;
			}
		}
		if ( (
			segment1 == station ||
			segment1 == lift ||
			segment1 == main ||
			segment1 == braking ||
			segment1 == repair
		) && (
			segment2 == station ||
			segment2 == lift ||
			segment2 == main ||
			segment2 == braking ||
			segment2 == repair
		) ) {
			return true ;
		}
		return false ;
	}
	
	public allowsGateOpen ( ) {
		if ( open == true ) {
			return true ;
			
		}
		return false ;
	}
	
}

