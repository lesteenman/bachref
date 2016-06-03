package rollercoaster.models
import models.Sorts.* ;


public class SwitchModel {
	SwitchPos pos ;
	
	public SwitchModel ( SwitchPos pos ) {
		self.pos = pos ;
		
	}
	
	public boolean up ( ) {
		if ( pos == down ) {
			self.pos = up ;
			
		}
	}
	
	
	public boolean down ( ) {
		if ( pos == up ) {
			self.pos = down ;
			
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
		if ( pos == up ) {
			if ( segment1 == repair && segment2 == station ) {
				return true ;
			}
		}
		if ( pos == down ) {
			if ( segment1 == braking && segment2 == station ) {
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
		if ( pos == up ) {
			if ( segment1 == station && segment2 == braking ) {
				return true ;
			}
			if ( segment1 == station && segment2 == repair ) {
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
		if ( pos == up ) {
			if ( segment1 == repair && segment2 == station ) {
				return true ;
			}
		}
		if ( pos == down ) {
			if ( segment1 == braking && segment2 == station ) {
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
		if ( pos == up ) {
			if ( segment1 == station && segment2 == braking ) {
				return true ;
			}
			if ( segment1 == station && segment2 == repair ) {
				return true ;
			}
		}
		return false ;
	}
	
}

