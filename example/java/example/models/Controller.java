package example.models ;
import example.models.* ;
import example.models.Sorts.* ;
import java.io.* ;
import java.util.HashMap ;


public class Controller {
	/**
	Types:
	
	Available functions:
		traffic_light_2_yellow
		light2_green
		light1_green
		light2_red
		light1_red
		traffic_light_3_green
		traffic_light_3_yellow
		traffic_light_3_red
		traffic_light_2_green
		traffic_light_2_red
		light2_yellow
		light1_yellow
		**/
		
		TrafficLight1Model light2 ;
		TrafficLight1Model light1 ;
		TrafficLight2Model traffic_light_2 ;
		TrafficLight3Model traffic_light_3 ;
		
		public Controller ( ) {
			this.light2 = new TrafficLight1Model ( true , true , true ) ;
			this.light1 = new TrafficLight1Model ( true , true , true ) ;
			this.traffic_light_2 = new TrafficLight2Model ( true , false , false ) ;
			this.traffic_light_3 = new TrafficLight3Model ( true , false , false ) ;
		}
		
		public boolean performTrafficLight2Yellow ( ) {
			if ( this.isTrafficLight2YellowAllowed ( ) ) {
				return this.traffic_light_2.yellow ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performLight2Green ( ) {
			if ( this.isLight2GreenAllowed ( ) ) {
				return this.light2.green ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performLight1Green ( ) {
			if ( this.isLight1GreenAllowed ( ) ) {
				return this.light1.green ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performLight2Red ( ) {
			return this.light2.red ( ) ;
		}
		
		public boolean performLight1Red ( ) {
			return this.light1.red ( ) ;
		}
		
		public boolean performTrafficLight3Green ( ) {
			if ( this.isTrafficLight3GreenAllowed ( ) ) {
				return this.traffic_light_3.green ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performTrafficLight3Yellow ( ) {
			return this.traffic_light_3.yellow ( ) ;
		}
		
		public boolean performTrafficLight3Red ( ) {
			return this.traffic_light_3.red ( ) ;
		}
		
		public boolean performTrafficLight2Green ( ) {
			if ( this.isTrafficLight2GreenAllowed ( ) ) {
				return this.traffic_light_2.green ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performTrafficLight2Red ( ) {
			return this.traffic_light_2.red ( ) ;
		}
		
		public boolean performLight2Yellow ( ) {
			return this.light2.yellow ( ) ;
		}
		
		public boolean performLight1Yellow ( ) {
			return this.light1.yellow ( ) ;
		}
		
		public boolean isTrafficLight2YellowAllowed ( ) {
			boolean allow = true ;
			allow = allow && light2.allowsTrafficLight2Yellow ( ) ;
			allow = allow && light1.allowsTrafficLight2Yellow ( ) ;
			return allow ;
		}
		
		public boolean isLight2GreenAllowed ( ) {
			boolean allow = true ;
			allow = allow && traffic_light_2.allowsLight2Green ( ) ;
			return allow ;
		}
		
		public boolean isLight1GreenAllowed ( ) {
			boolean allow = true ;
			allow = allow && traffic_light_2.allowsLight1Green ( ) ;
			return allow ;
		}
		
		public boolean isTrafficLight3GreenAllowed ( ) {
			boolean allow = true ;
			allow = allow && light2.allowsTrafficLight3Green ( ) ;
			allow = allow && light1.allowsTrafficLight3Green ( ) ;
			return allow ;
		}
		
		public boolean isTrafficLight2GreenAllowed ( ) {
			boolean allow = true ;
			allow = allow && light2.allowsTrafficLight2Green ( ) ;
			allow = allow && light1.allowsTrafficLight2Green ( ) ;
			return allow ;
		}
		
		public HashMap<String,HashMap<String,String>> getStates ( ) {
			HashMap<String, HashMap<String, String>> states = new HashMap<String, HashMap<String, String>>();
			HashMap<String, String> instance_states = null;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "is_red" , String.valueOf ( this.light2.is_red ) ) ;
			instance_states.put ( "is_yellow" , String.valueOf ( this.light2.is_yellow ) ) ;
			instance_states.put ( "is_green" , String.valueOf ( this.light2.is_green ) ) ;
			states.put ( "light2" , instance_states ) ;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "is_red" , String.valueOf ( this.light1.is_red ) ) ;
			instance_states.put ( "is_yellow" , String.valueOf ( this.light1.is_yellow ) ) ;
			instance_states.put ( "is_green" , String.valueOf ( this.light1.is_green ) ) ;
			states.put ( "light1" , instance_states ) ;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "is_red" , String.valueOf ( this.traffic_light_2.is_red ) ) ;
			instance_states.put ( "is_yellow" , String.valueOf ( this.traffic_light_2.is_yellow ) ) ;
			instance_states.put ( "is_green" , String.valueOf ( this.traffic_light_2.is_green ) ) ;
			states.put ( "traffic_light_2" , instance_states ) ;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "is_red" , String.valueOf ( this.traffic_light_3.is_red ) ) ;
			instance_states.put ( "is_yellow" , String.valueOf ( this.traffic_light_3.is_yellow ) ) ;
			instance_states.put ( "is_green" , String.valueOf ( this.traffic_light_3.is_green ) ) ;
			states.put ( "traffic_light_3" , instance_states ) ;
			return states ;
		}
		
	}
	
