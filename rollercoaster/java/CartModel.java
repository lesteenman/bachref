package rollercoaster.models
import models.Sorts.* ;


public class CartModel {
	Segment pos ;
	boolean open ;
	boolean locked ;
	
	public  ( Segment pos , boolean open , boolean locked ) {
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
	
	
}

