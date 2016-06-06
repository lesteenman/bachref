package rollercoaster.models ;
import rollercoaster.models.Sorts.* ;


public class SwModel {
	public SwitchPos pos ;
	
	public SwModel ( SwitchPos pos ) {
		this.pos = pos ;
		
	}
	
	public boolean up ( ) {
		if ( pos == SwitchPos.down ) {
			this.pos = SwitchPos.up ;
			return true ;
			
		}
		return false;
	}
	
	
	public boolean down ( ) {
		if ( pos == SwitchPos.up ) {
			this.pos = SwitchPos.down ;
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
		if ( pos == SwitchPos.up ) {
			if ( segment1 == Segment.repair && segment2 == Segment.station ) {
				return true ;
			}
			
		}
		if ( pos == SwitchPos.down ) {
			if ( segment1 == Segment.braking && segment2 == Segment.station ) {
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
		if ( pos == SwitchPos.up ) {
			if ( segment1 == Segment.station && segment2 == Segment.braking ) {
				return true ;
			}
			if ( segment1 == Segment.station && segment2 == Segment.repair ) {
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
		if ( pos == SwitchPos.up ) {
			if ( segment1 == Segment.repair && segment2 == Segment.station ) {
				return true ;
			}
			
		}
		if ( pos == SwitchPos.down ) {
			if ( segment1 == Segment.braking && segment2 == Segment.station ) {
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
		if ( pos == SwitchPos.up ) {
			if ( segment1 == Segment.station && segment2 == Segment.braking ) {
				return true ;
			}
			if ( segment1 == Segment.station && segment2 == Segment.repair ) {
				return true ;
			}
			
		}
		return false ;
	}
	
}

