package trafficlights.models ;
import trafficlights.models.Sorts.* ;


public class StraightLightModel {
	public WindDir winddir ;
	public Color color ;
	
	public StraightLightModel ( WindDir winddir , Color color ) {
		this.winddir = winddir ;
		this.color = color ;
		
	}
	
	public boolean green ( ) {
		if ( color == Color.red ) {
			this.color = Color.green ;
			return true ;
			
		}
		return false;
	}
	
	
	public boolean yellow ( ) {
		if ( color == Color.green ) {
			this.color = Color.yellow ;
			return true ;
			
		}
		return false;
	}
	
	
	public boolean red ( ) {
		if ( color == Color.yellow ) {
			this.color = Color.red ;
			return true ;
			
		}
		return false;
	}
	
	
	public boolean allowsStraightNorthGreen ( ) {
		if ( winddir == WindDir.east || winddir == WindDir.west ) {
			if ( color == Color.red ) {
				return true ;
				
			}
			
		}
		else {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsStraightSouthGreen ( ) {
		if ( winddir == WindDir.east || winddir == WindDir.west ) {
			if ( color == Color.red ) {
				return true ;
				
			}
			
		}
		else {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsStraightEastGreen ( ) {
		if ( winddir == WindDir.north || winddir == WindDir.south ) {
			if ( color == Color.red ) {
				return true ;
				
			}
			
		}
		else {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsStraightWestGreen ( ) {
		if ( winddir == WindDir.north || winddir == WindDir.south ) {
			if ( color == Color.red ) {
				return true ;
				
			}
			
		}
		else {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsRightNorthGreen ( ) {
		if ( winddir == WindDir.east ) {
			if ( color == Color.red ) {
				return true ;
				
			}
			
		}
		else {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsRightEastGreen ( ) {
		if ( winddir == WindDir.south ) {
			if ( color == Color.red ) {
				return true ;
				
			}
			
		}
		else {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsRightSouthGreen ( ) {
		if ( winddir == WindDir.west ) {
			if ( color == Color.red ) {
				return true ;
				
			}
			
		}
		else {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsRightWestGreen ( ) {
		if ( winddir == WindDir.north ) {
			if ( color == Color.red ) {
				return true ;
				
			}
			
		}
		else {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsLeftEastGreen ( ) {
		if ( color == Color.red ) {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsLeftWestGreen ( ) {
		if ( color == Color.red ) {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsLeftNorthGreen ( ) {
		if ( color == Color.red ) {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsLeftSouthGreen ( ) {
		if ( color == Color.red ) {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsBicycleSouthGreen ( ) {
		if ( color == Color.red ) {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsBicycleNorthGreen ( ) {
		if ( color == Color.red ) {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsBicycleEastGreen ( ) {
		if ( color == Color.red ) {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsBicycleWestGreen ( ) {
		if ( color == Color.red ) {
			return true ;
			
		}
		return false ;
	}
	
}

