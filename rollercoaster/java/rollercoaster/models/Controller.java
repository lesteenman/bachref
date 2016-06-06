package rollercoaster.models ;
import rollercoaster.models.* ;
import rollercoaster.models.Sorts.* ;
import java.io.* ;
import java.util.HashMap ;


public class Controller {
	/**
	Types:
		SwitchPos
			up
			down
		Segment
			station
			lift
			main
			braking
			repair
		Cart_Id
			cart1
			cart2
	
	Available functions:
		button_push_button
		gate_open
		sw_up
		sw_down
		cart2_backward
			Segment
			Segment
		cart1_backward
			Segment
			Segment
		cart2_forward
			Segment
			Segment
		cart1_forward
			Segment
			Segment
		cart2_close_braces
		cart1_close_braces
		cart2_open_braces
			Segment
		cart1_open_braces
			Segment
		gate_close
		**/
		
		TrackModel track ;
		GateModel gate ;
		ButtonModel button ;
		SwModel sw ;
		CartModel cart2 ;
		CartModel cart1 ;
		
		public Controller ( ) {
			this.track = new TrackModel ( ) ;
			this.gate = new GateModel ( false ) ;
			this.button = new ButtonModel ( false ) ;
			this.sw = new SwModel ( SwitchPos.down ) ;
			this.cart2 = new CartModel ( Segment.repair , false , false ) ;
			this.cart1 = new CartModel ( Segment.braking , false , false ) ;
		}
		
		public boolean performButtonPushButton ( ) {
			return this.button.push_button ( ) ;
		}
		
		public boolean performGateOpen ( ) {
			if ( this.isGateOpenAllowed ( ) ) {
				return this.gate.open ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performSwUp ( ) {
			return this.sw.up ( ) ;
		}
		
		public boolean performSwDown ( ) {
			return this.sw.down ( ) ;
		}
		
		public boolean performCart2Backward ( Segment segment1 , Segment segment2 ) {
			if ( this.isCart2BackwardAllowed ( segment1 , segment2 ) ) {
				return this.cart2.backward ( segment1 , segment2 ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performCart1Backward ( Segment segment1 , Segment segment2 ) {
			if ( this.isCart1BackwardAllowed ( segment1 , segment2 ) ) {
				return this.cart1.backward ( segment1 , segment2 ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performCart2Forward ( Segment segment1 , Segment segment2 ) {
			if ( this.isCart2ForwardAllowed ( segment1 , segment2 ) ) {
				return this.cart2.forward ( segment1 , segment2 ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performCart1Forward ( Segment segment1 , Segment segment2 ) {
			if ( this.isCart1ForwardAllowed ( segment1 , segment2 ) ) {
				return this.cart1.forward ( segment1 , segment2 ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performCart2CloseBraces ( ) {
			if ( this.isCart2CloseBracesAllowed ( ) ) {
				return this.cart2.close_braces ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performCart1CloseBraces ( ) {
			if ( this.isCart1CloseBracesAllowed ( ) ) {
				return this.cart1.close_braces ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performCart2OpenBraces ( Segment segment1 ) {
			if ( this.isCart2OpenBracesAllowed ( segment1 ) ) {
				return this.cart2.open_braces ( segment1 ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performCart1OpenBraces ( Segment segment1 ) {
			if ( this.isCart1OpenBracesAllowed ( segment1 ) ) {
				return this.cart1.open_braces ( segment1 ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performGateClose ( ) {
			return this.gate.close ( ) ;
		}
		
		public boolean isGateOpenAllowed ( ) {
			boolean allow = true ;
			allow = allow && button.allowsGateOpen ( ) ;
			return allow ;
		}
		
		public boolean isCart2BackwardAllowed ( Segment segment1 , Segment segment2 ) {
			boolean allow = true ;
			allow = allow && cart1.allowsCart2Backward ( segment1 , segment2 ) ;
			allow = allow && track.allowsCart2Backward ( segment1 , segment2 ) ;
			allow = allow && sw.allowsCart2Backward ( segment1 , segment2 ) ;
			allow = allow && gate.allowsCart2Backward ( segment1 , segment2 ) ;
			return allow ;
		}
		
		public boolean isCart1BackwardAllowed ( Segment segment1 , Segment segment2 ) {
			boolean allow = true ;
			allow = allow && cart2.allowsCart1Backward ( segment1 , segment2 ) ;
			allow = allow && track.allowsCart1Backward ( segment1 , segment2 ) ;
			allow = allow && sw.allowsCart1Backward ( segment1 , segment2 ) ;
			allow = allow && gate.allowsCart1Backward ( segment1 , segment2 ) ;
			return allow ;
		}
		
		public boolean isCart2ForwardAllowed ( Segment segment1 , Segment segment2 ) {
			boolean allow = true ;
			allow = allow && cart1.allowsCart2Forward ( segment1 , segment2 ) ;
			allow = allow && track.allowsCart2Forward ( segment1 , segment2 ) ;
			allow = allow && sw.allowsCart2Forward ( segment1 , segment2 ) ;
			allow = allow && button.allowsCart2Forward ( segment1 , segment2 ) ;
			allow = allow && gate.allowsCart2Forward ( segment1 , segment2 ) ;
			return allow ;
		}
		
		public boolean isCart1ForwardAllowed ( Segment segment1 , Segment segment2 ) {
			boolean allow = true ;
			allow = allow && cart2.allowsCart1Forward ( segment1 , segment2 ) ;
			allow = allow && track.allowsCart1Forward ( segment1 , segment2 ) ;
			allow = allow && sw.allowsCart1Forward ( segment1 , segment2 ) ;
			allow = allow && button.allowsCart1Forward ( segment1 , segment2 ) ;
			allow = allow && gate.allowsCart1Forward ( segment1 , segment2 ) ;
			return allow ;
		}
		
		public boolean isCart2CloseBracesAllowed ( ) {
			boolean allow = true ;
			allow = allow && button.allowsCart2CloseBraces ( ) ;
			return allow ;
		}
		
		public boolean isCart1CloseBracesAllowed ( ) {
			boolean allow = true ;
			allow = allow && button.allowsCart1CloseBraces ( ) ;
			return allow ;
		}
		
		public boolean isCart2OpenBracesAllowed ( Segment segment1 ) {
			boolean allow = true ;
			allow = allow && track.allowsCart2OpenBraces ( segment1 ) ;
			allow = allow && button.allowsCart2OpenBraces ( segment1 ) ;
			return allow ;
		}
		
		public boolean isCart1OpenBracesAllowed ( Segment segment1 ) {
			boolean allow = true ;
			allow = allow && track.allowsCart1OpenBraces ( segment1 ) ;
			allow = allow && button.allowsCart1OpenBraces ( segment1 ) ;
			return allow ;
		}
		
		public HashMap<String,HashMap<String,String>> getStates ( ) {
			HashMap<String, HashMap<String, String>> states = new HashMap<String, HashMap<String, String>>();
			HashMap<String, String> instance_states = null;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "open" , String.valueOf ( this.gate.open ) ) ;
			states.put ( "gate" , instance_states ) ;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "open" , String.valueOf ( this.button.open ) ) ;
			states.put ( "button" , instance_states ) ;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "pos" , String.valueOf ( this.sw.pos ) ) ;
			states.put ( "sw" , instance_states ) ;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "pos" , String.valueOf ( this.cart2.pos ) ) ;
			instance_states.put ( "open" , String.valueOf ( this.cart2.open ) ) ;
			instance_states.put ( "locked" , String.valueOf ( this.cart2.locked ) ) ;
			states.put ( "cart2" , instance_states ) ;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "pos" , String.valueOf ( this.cart1.pos ) ) ;
			instance_states.put ( "open" , String.valueOf ( this.cart1.open ) ) ;
			instance_states.put ( "locked" , String.valueOf ( this.cart1.locked ) ) ;
			states.put ( "cart1" , instance_states ) ;
			return states ;
		}
		
	}
	
