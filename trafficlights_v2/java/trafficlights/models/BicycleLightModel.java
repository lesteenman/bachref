package trafficlights.models ;
import trafficlights.models.Sorts.* ;


public class BicycleLightModel {
	public WindDir winddir ;
	public Color color ;
	
	public BicycleLightModel ( WindDir winddir , Color color ) {
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
		if ( color == Color.red ) {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsStraightWestGreen ( ) {
		if ( color == Color.red ) {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsStraightSouthGreen ( ) {
		if ( color == Color.red ) {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsStraightEastGreen ( ) {
		if ( color == Color.red ) {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsRightNorthGreen ( ) {
		if ( color == Color.red ) {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsRightWestGreen ( ) {
		if ( color == Color.red ) {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsRightSouthGreen ( ) {
		if ( color == Color.red ) {
			return true ;
			
		}
		return false ;
	}
	
	public boolean allowsRightEastGreen ( ) {
		if ( color == Color.red ) {
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
	
}

