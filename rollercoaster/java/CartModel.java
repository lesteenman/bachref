package rollercoaster.models
import models.Sorts.* ;


public class CartModel {
	Segment pos ;
	boolean open ;
	boolean locked ;
	
	public CartModel ( Segment pos , boolean open , boolean locked ) {
		self.pos = pos ;
		self.open = open ;
		self.locked = locked ;
		
	}
	
	public boolean forward ( segment_1 , segment_2 ) {
		if ( open == false ) {
			if ( segment_1 == pos && segment_2 == lift ) {
				self.pos = lift ;
			}
			if ( segment_1 == pos && segment_2 == main ) {
				self.pos = main ;
			}
			if ( segment_1 == pos && segment_2 == braking ) {
				self.pos = braking ;
			}
			if ( segment_1 == pos && segment_2 == station ) {
				self.pos = station ;
			}
			if ( segment_1 == pos && segment_2 == repair ) {
				self.pos = repair ;
			}
		}
	}
	
	
	public boolean backward ( segment_1 , segment_2 ) {
		if ( open == false && pos == station ) {
			if ( segment_1 == station && segment_2 == repair ) {
				self.pos = repair ;
			}
		}
	}
	
	
	public boolean open_braces ( segment_1 ) {
		if ( open == false && locked == false ) {
			if ( segment_1 == pos ) {
				self.open = true ;
			}
		}
	}
	
	
	public boolean close_braces ( ) {
		if ( open == true ) {
			self.open = false ;
			self.locked = true ;
			
		}
	}
	
	
	public allowsCart2Forward ( Segment segment1 , Segment segment2 ) {
		if ( pos == station ) {
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
		}
		if ( pos == lift ) {
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
		}
		if ( pos == main ) {
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
		}
		if ( pos == braking ) {
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
		}
		if ( pos == repair ) {
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
		return false ;
	}
	
	public allowsCart1Forward ( Segment segment1 , Segment segment2 ) {
		if ( pos == station ) {
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
		}
		if ( pos == lift ) {
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
		}
		if ( pos == main ) {
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
		}
		if ( pos == braking ) {
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
		}
		if ( pos == repair ) {
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
		return false ;
	}
	
}

