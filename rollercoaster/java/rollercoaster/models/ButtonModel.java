package rollercoaster.models ;
import rollercoaster.models.Sorts.* ;


public class ButtonModel {
	public boolean open ;
	
	public ButtonModel ( boolean open ) {
		this.open = open ;
		
	}
	
	public boolean push_button ( ) {
		if ( open == true ) {
			this.open = false ;
			return true ;
			
		}
		return false;
	}
	
	
	public boolean allowsCart2OpenBraces ( Segment segment1 ) {
		if ( open == false ) {
			if ( (
				segment1 == Segment.station ||
				segment1 == Segment.lift ||
				segment1 == Segment.main ||
				segment1 == Segment.braking ||
				segment1 == Segment.repair
			) ) {
				return true ;
			}
			
		}
		return false ;
	}
	
	public boolean allowsCart2CloseBraces ( ) {
		if ( open == false ) {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsCart2Forward ( Segment segment1 , Segment segment2 ) {
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
		return false ;
	}
	
	public boolean allowsCart1OpenBraces ( Segment segment1 ) {
		if ( open == false ) {
			if ( (
				segment1 == Segment.station ||
				segment1 == Segment.lift ||
				segment1 == Segment.main ||
				segment1 == Segment.braking ||
				segment1 == Segment.repair
			) ) {
				return true ;
			}
			
		}
		return false ;
	}
	
	public boolean allowsCart1CloseBraces ( ) {
		if ( open == false ) {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsCart1Forward ( Segment segment1 , Segment segment2 ) {
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
		return false ;
	}
	
	public boolean allowsGateOpen ( ) {
		if ( open == true ) {
			return true ;
			
		}
		return false ;
	}
	
}

