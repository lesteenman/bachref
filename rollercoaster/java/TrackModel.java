package rollercoaster.models
import models.Sorts.* ;


public class TrackModel {
	
	public TrackModel ( ) {
		
	}
	
	public allowsCart2Forward ( Segment segment1 , Segment segment2 ) {
		if ( segment1 == station && segment2 == lift ) {
			return true ;
		}
		if ( segment1 == lift && segment2 == main ) {
			return true ;
		}
		if ( segment1 == main && segment2 == braking ) {
			return true ;
		}
		if ( segment1 == braking && segment2 == station ) {
			return true ;
		}
		if ( segment1 == repair && segment2 == station ) {
			return true ;
		}
		return false ;
	}
	
	public allowsCart2Backward ( Segment segment1 , Segment segment2 ) {
		if ( segment1 == station && segment2 == repair ) {
			return true ;
		}
		return false ;
	}
	
	public allowsCart2OpenBraces ( Segment segment1 ) {
		if ( segment1 == station ) {
			return true ;
		}
		return false ;
	}
	
	public allowsCart1Forward ( Segment segment1 , Segment segment2 ) {
		if ( segment1 == station && segment2 == lift ) {
			return true ;
		}
		if ( segment1 == lift && segment2 == main ) {
			return true ;
		}
		if ( segment1 == main && segment2 == braking ) {
			return true ;
		}
		if ( segment1 == braking && segment2 == station ) {
			return true ;
		}
		if ( segment1 == repair && segment2 == station ) {
			return true ;
		}
		return false ;
	}
	
	public allowsCart1Backward ( Segment segment1 , Segment segment2 ) {
		if ( segment1 == station && segment2 == repair ) {
			return true ;
		}
		return false ;
	}
	
	public allowsCart1OpenBraces ( Segment segment1 ) {
		if ( segment1 == station ) {
			return true ;
		}
		return false ;
	}
	
}

