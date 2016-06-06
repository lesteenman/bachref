package rollercoaster.models ;
import rollercoaster.models.Sorts.* ;


public class CartModel {
	public Segment pos ;
	public boolean open ;
	public boolean locked ;
	
	public CartModel ( Segment pos , boolean open , boolean locked ) {
		this.pos = pos ;
		this.open = open ;
		this.locked = locked ;
		
	}
	
	public boolean forward ( Segment segment1 , Segment segment2 ) {
		if ( open == false ) {
			if ( segment1 == pos && segment2 == Segment.lift ) {
				this.pos = Segment.lift ;
				return true ;
			}
			if ( segment1 == pos && segment2 == Segment.main ) {
				this.pos = Segment.main ;
				return true ;
			}
			if ( segment1 == pos && segment2 == Segment.braking ) {
				this.pos = Segment.braking ;
				return true ;
			}
			if ( segment1 == pos && segment2 == Segment.station ) {
				this.pos = Segment.station ;
				return true ;
			}
			if ( segment1 == pos && segment2 == Segment.repair ) {
				this.pos = Segment.repair ;
				return true ;
			}
			
		}
		return false;
	}
	
	
	public boolean backward ( Segment segment1 , Segment segment2 ) {
		if ( open == false && pos == Segment.station ) {
			if ( segment1 == Segment.station && segment2 == Segment.repair ) {
				this.pos = Segment.repair ;
				return true ;
			}
			
		}
		return false;
	}
	
	
	public boolean open_braces ( Segment segment1 ) {
		if ( open == false && locked == false ) {
			if ( segment1 == pos ) {
				this.open = true ;
				return true ;
			}
			
		}
		return false;
	}
	
	
	public boolean close_braces ( ) {
		if ( open == true ) {
			this.open = false ;
			this.locked = true ;
			return true ;
			
		}
		return false;
	}
	
	
	public boolean allowsCart2Forward ( Segment segment1 , Segment segment2 ) {
		if ( pos == Segment.station ) {
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
			
		}
		if ( pos == Segment.lift ) {
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
			
		}
		if ( pos == Segment.main ) {
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
			
		}
		if ( pos == Segment.braking ) {
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
			
		}
		if ( pos == Segment.repair ) {
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
		return false ;
	}
	
	public boolean allowsCart1Forward ( Segment segment1 , Segment segment2 ) {
		if ( pos == Segment.station ) {
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
			
		}
		if ( pos == Segment.lift ) {
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
			
		}
		if ( pos == Segment.main ) {
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
			
		}
		if ( pos == Segment.braking ) {
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
			
		}
		if ( pos == Segment.repair ) {
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
		return false ;
	}
	
}

