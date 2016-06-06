package rollercoaster.models ;
import rollercoaster.models.Sorts.* ;


public class TrackModel {
	
	public TrackModel ( ) {
		
	}
	
	public boolean allowsCart2Forward ( Segment segment1 , Segment segment2 ) {
		if ( segment1 == Segment.station && segment2 == Segment.lift ) {
			return true ;
		}
		if ( segment1 == Segment.lift && segment2 == Segment.main ) {
			return true ;
		}
		if ( segment1 == Segment.main && segment2 == Segment.braking ) {
			return true ;
		}
		if ( segment1 == Segment.braking && segment2 == Segment.station ) {
			return true ;
		}
		if ( segment1 == Segment.repair && segment2 == Segment.station ) {
			return true ;
		}
		return false ;
	}
	
	public boolean allowsCart2Backward ( Segment segment1 , Segment segment2 ) {
		if ( segment1 == Segment.station && segment2 == Segment.repair ) {
			return true ;
		}
		return false ;
	}
	
	public boolean allowsCart2OpenBraces ( Segment segment1 ) {
		if ( segment1 == Segment.station ) {
			return true ;
		}
		return false ;
	}
	
	public boolean allowsCart1Forward ( Segment segment1 , Segment segment2 ) {
		if ( segment1 == Segment.station && segment2 == Segment.lift ) {
			return true ;
		}
		if ( segment1 == Segment.lift && segment2 == Segment.main ) {
			return true ;
		}
		if ( segment1 == Segment.main && segment2 == Segment.braking ) {
			return true ;
		}
		if ( segment1 == Segment.braking && segment2 == Segment.station ) {
			return true ;
		}
		if ( segment1 == Segment.repair && segment2 == Segment.station ) {
			return true ;
		}
		return false ;
	}
	
	public boolean allowsCart1Backward ( Segment segment1 , Segment segment2 ) {
		if ( segment1 == Segment.station && segment2 == Segment.repair ) {
			return true ;
		}
		return false ;
	}
	
	public boolean allowsCart1OpenBraces ( Segment segment1 ) {
		if ( segment1 == Segment.station ) {
			return true ;
		}
		return false ;
	}
	
}

