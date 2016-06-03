package rollercoaster.models
import models.Sorts.* ;


public class GateModel {
	boolean open ;
	
	public GateModel ( boolean open ) {
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
	
	
	public allowsCart2Forward ( Segment segment1 , Segment segment2 ) {
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
		return false ;
	}
	
	public allowsCart2Backward ( Segment segment1 , Segment segment2 ) {
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
		return false ;
	}
	
	public allowsCart1Forward ( Segment segment1 , Segment segment2 ) {
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
		return false ;
	}
	
	public allowsCart1Backward ( Segment segment1 , Segment segment2 ) {
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
		return false ;
	}
	
}

