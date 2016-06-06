package rollercoaster.models ;
import rollercoaster.models.Sorts.* ;


public class GateModel {
	public boolean open ;
	
	public GateModel ( boolean open ) {
		this.open = open ;
		
	}
	
	public boolean open ( ) {
		if ( open == false ) {
			this.open = true ;
			return true ;
			
		}
		return false;
	}
	
	
	public boolean close ( ) {
		if ( open == true ) {
			this.open = false ;
			return true ;
			
		}
		return false;
	}
	
	
	public boolean allowsCart2Forward ( Segment segment1 , Segment segment2 ) {
		if ( (
			segment1 == Segment.station ||
			segment1 == Segment.lift ||
			segment1 == Segment.main ||
			segment1 == Segment.braking ||
			segment1 == Segment.repair
		) && (
			segment2 == Segment.station ||
			segment2 == Segment.lift ||
			segment2 == Segment.main ||
			segment2 == Segment.braking ||
			segment2 == Segment.repair
		) ) {
			return true ;
		}
		if ( open == false ) {
			if ( segment1 == Segment.station && (
				segment2 == Segment.station ||
				segment2 == Segment.lift ||
				segment2 == Segment.main ||
				segment2 == Segment.braking ||
				segment2 == Segment.repair
			) ) {
				return true ;
			}
			
		}
		return false ;
	}
	
	public boolean allowsCart2Backward ( Segment segment1 , Segment segment2 ) {
		if ( (
			segment1 == Segment.station ||
			segment1 == Segment.lift ||
			segment1 == Segment.main ||
			segment1 == Segment.braking ||
			segment1 == Segment.repair
		) && (
			segment2 == Segment.station ||
			segment2 == Segment.lift ||
			segment2 == Segment.main ||
			segment2 == Segment.braking ||
			segment2 == Segment.repair
		) ) {
			return true ;
		}
		if ( open == false ) {
			if ( segment1 == Segment.station && (
				segment2 == Segment.station ||
				segment2 == Segment.lift ||
				segment2 == Segment.main ||
				segment2 == Segment.braking ||
				segment2 == Segment.repair
			) ) {
				return true ;
			}
			
		}
		return false ;
	}
	
	public boolean allowsCart1Forward ( Segment segment1 , Segment segment2 ) {
		if ( (
			segment1 == Segment.station ||
			segment1 == Segment.lift ||
			segment1 == Segment.main ||
			segment1 == Segment.braking ||
			segment1 == Segment.repair
		) && (
			segment2 == Segment.station ||
			segment2 == Segment.lift ||
			segment2 == Segment.main ||
			segment2 == Segment.braking ||
			segment2 == Segment.repair
		) ) {
			return true ;
		}
		if ( open == false ) {
			if ( segment1 == Segment.station && (
				segment2 == Segment.station ||
				segment2 == Segment.lift ||
				segment2 == Segment.main ||
				segment2 == Segment.braking ||
				segment2 == Segment.repair
			) ) {
				return true ;
			}
			
		}
		return false ;
	}
	
	public boolean allowsCart1Backward ( Segment segment1 , Segment segment2 ) {
		if ( (
			segment1 == Segment.station ||
			segment1 == Segment.lift ||
			segment1 == Segment.main ||
			segment1 == Segment.braking ||
			segment1 == Segment.repair
		) && (
			segment2 == Segment.station ||
			segment2 == Segment.lift ||
			segment2 == Segment.main ||
			segment2 == Segment.braking ||
			segment2 == Segment.repair
		) ) {
			return true ;
		}
		if ( open == false ) {
			if ( segment1 == Segment.station && (
				segment2 == Segment.station ||
				segment2 == Segment.lift ||
				segment2 == Segment.main ||
				segment2 == Segment.braking ||
				segment2 == Segment.repair
			) ) {
				return true ;
			}
			
		}
		return false ;
	}
	
}

