package trafficlights.models ;
import trafficlights.models.* ;
import trafficlights.models.Sorts.* ;
import java.io.* ;
import java.util.HashMap ;


public class Controller {
	/**
	Types:
		WindDir
			north
			east
			south
			west
		Color
			red
			yellow
			green
	
	Available functions:
		left_east_red
		left_west_red
		left_north_red
		left_south_red
		right_north_yellow
		right_west_yellow
		right_south_yellow
		right_east_yellow
		straight_north_green
		straight_west_green
		straight_south_green
		straight_east_green
		left_east_yellow
		left_west_yellow
		left_north_yellow
		left_south_yellow
		right_north_green
		right_west_green
		right_south_green
		right_east_green
		bicycle_south_red
		bicycle_north_red
		bicycle_east_red
		bicycle_west_red
		bicycle_south_yellow
		bicycle_north_yellow
		bicycle_east_yellow
		bicycle_west_yellow
		straight_north_red
		straight_west_red
		straight_south_red
		straight_east_red
		left_east_green
		left_west_green
		left_north_green
		left_south_green
		straight_north_yellow
		straight_west_yellow
		straight_south_yellow
		straight_east_yellow
		right_north_red
		right_west_red
		right_south_red
		right_east_red
		bicycle_south_green
		bicycle_north_green
		bicycle_east_green
		bicycle_west_green
		**/
		
		RightLightModel right_north ;
		RightLightModel right_west ;
		RightLightModel right_south ;
		RightLightModel right_east ;
		StraightLightModel straight_north ;
		StraightLightModel straight_west ;
		StraightLightModel straight_south ;
		StraightLightModel straight_east ;
		LeftLightModel left_east ;
		LeftLightModel left_west ;
		LeftLightModel left_north ;
		LeftLightModel left_south ;
		BicycleLightModel bicycle_south ;
		BicycleLightModel bicycle_north ;
		BicycleLightModel bicycle_east ;
		BicycleLightModel bicycle_west ;
		
		public Controller ( ) {
			this.right_north = new RightLightModel ( WindDir.north , Color.red ) ;
			this.right_west = new RightLightModel ( WindDir.west , Color.red ) ;
			this.right_south = new RightLightModel ( WindDir.south , Color.red ) ;
			this.right_east = new RightLightModel ( WindDir.east , Color.red ) ;
			this.straight_north = new StraightLightModel ( WindDir.north , Color.red ) ;
			this.straight_west = new StraightLightModel ( WindDir.west , Color.red ) ;
			this.straight_south = new StraightLightModel ( WindDir.south , Color.red ) ;
			this.straight_east = new StraightLightModel ( WindDir.east , Color.red ) ;
			this.left_east = new LeftLightModel ( WindDir.east , Color.red ) ;
			this.left_west = new LeftLightModel ( WindDir.west , Color.red ) ;
			this.left_north = new LeftLightModel ( WindDir.north , Color.red ) ;
			this.left_south = new LeftLightModel ( WindDir.south , Color.red ) ;
			this.bicycle_south = new BicycleLightModel ( WindDir.south , Color.red ) ;
			this.bicycle_north = new BicycleLightModel ( WindDir.north , Color.red ) ;
			this.bicycle_east = new BicycleLightModel ( WindDir.east , Color.red ) ;
			this.bicycle_west = new BicycleLightModel ( WindDir.west , Color.red ) ;
		}
		
		public boolean performLeftEastRed ( ) {
			return this.left_east.red ( ) ;
		}
		
		public boolean performLeftWestRed ( ) {
			return this.left_west.red ( ) ;
		}
		
		public boolean performLeftNorthRed ( ) {
			return this.left_north.red ( ) ;
		}
		
		public boolean performLeftSouthRed ( ) {
			return this.left_south.red ( ) ;
		}
		
		public boolean performRightNorthYellow ( ) {
			return this.right_north.yellow ( ) ;
		}
		
		public boolean performRightWestYellow ( ) {
			return this.right_west.yellow ( ) ;
		}
		
		public boolean performRightSouthYellow ( ) {
			return this.right_south.yellow ( ) ;
		}
		
		public boolean performRightEastYellow ( ) {
			return this.right_east.yellow ( ) ;
		}
		
		public boolean performStraightNorthGreen ( ) {
			if ( this.isStraightNorthGreenAllowed ( ) ) {
				return this.straight_north.green ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performStraightWestGreen ( ) {
			if ( this.isStraightWestGreenAllowed ( ) ) {
				return this.straight_west.green ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performStraightSouthGreen ( ) {
			if ( this.isStraightSouthGreenAllowed ( ) ) {
				return this.straight_south.green ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performStraightEastGreen ( ) {
			if ( this.isStraightEastGreenAllowed ( ) ) {
				return this.straight_east.green ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performLeftEastYellow ( ) {
			return this.left_east.yellow ( ) ;
		}
		
		public boolean performLeftWestYellow ( ) {
			return this.left_west.yellow ( ) ;
		}
		
		public boolean performLeftNorthYellow ( ) {
			return this.left_north.yellow ( ) ;
		}
		
		public boolean performLeftSouthYellow ( ) {
			return this.left_south.yellow ( ) ;
		}
		
		public boolean performRightNorthGreen ( ) {
			if ( this.isRightNorthGreenAllowed ( ) ) {
				return this.right_north.green ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performRightWestGreen ( ) {
			if ( this.isRightWestGreenAllowed ( ) ) {
				return this.right_west.green ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performRightSouthGreen ( ) {
			if ( this.isRightSouthGreenAllowed ( ) ) {
				return this.right_south.green ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performRightEastGreen ( ) {
			if ( this.isRightEastGreenAllowed ( ) ) {
				return this.right_east.green ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performBicycleSouthRed ( ) {
			return this.bicycle_south.red ( ) ;
		}
		
		public boolean performBicycleNorthRed ( ) {
			return this.bicycle_north.red ( ) ;
		}
		
		public boolean performBicycleEastRed ( ) {
			return this.bicycle_east.red ( ) ;
		}
		
		public boolean performBicycleWestRed ( ) {
			return this.bicycle_west.red ( ) ;
		}
		
		public boolean performBicycleSouthYellow ( ) {
			return this.bicycle_south.yellow ( ) ;
		}
		
		public boolean performBicycleNorthYellow ( ) {
			return this.bicycle_north.yellow ( ) ;
		}
		
		public boolean performBicycleEastYellow ( ) {
			return this.bicycle_east.yellow ( ) ;
		}
		
		public boolean performBicycleWestYellow ( ) {
			return this.bicycle_west.yellow ( ) ;
		}
		
		public boolean performStraightNorthRed ( ) {
			return this.straight_north.red ( ) ;
		}
		
		public boolean performStraightWestRed ( ) {
			return this.straight_west.red ( ) ;
		}
		
		public boolean performStraightSouthRed ( ) {
			return this.straight_south.red ( ) ;
		}
		
		public boolean performStraightEastRed ( ) {
			return this.straight_east.red ( ) ;
		}
		
		public boolean performLeftEastGreen ( ) {
			if ( this.isLeftEastGreenAllowed ( ) ) {
				return this.left_east.green ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performLeftWestGreen ( ) {
			if ( this.isLeftWestGreenAllowed ( ) ) {
				return this.left_west.green ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performLeftNorthGreen ( ) {
			if ( this.isLeftNorthGreenAllowed ( ) ) {
				return this.left_north.green ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performLeftSouthGreen ( ) {
			if ( this.isLeftSouthGreenAllowed ( ) ) {
				return this.left_south.green ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performStraightNorthYellow ( ) {
			return this.straight_north.yellow ( ) ;
		}
		
		public boolean performStraightWestYellow ( ) {
			return this.straight_west.yellow ( ) ;
		}
		
		public boolean performStraightSouthYellow ( ) {
			return this.straight_south.yellow ( ) ;
		}
		
		public boolean performStraightEastYellow ( ) {
			return this.straight_east.yellow ( ) ;
		}
		
		public boolean performRightNorthRed ( ) {
			return this.right_north.red ( ) ;
		}
		
		public boolean performRightWestRed ( ) {
			return this.right_west.red ( ) ;
		}
		
		public boolean performRightSouthRed ( ) {
			return this.right_south.red ( ) ;
		}
		
		public boolean performRightEastRed ( ) {
			return this.right_east.red ( ) ;
		}
		
		public boolean performBicycleSouthGreen ( ) {
			if ( this.isBicycleSouthGreenAllowed ( ) ) {
				return this.bicycle_south.green ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performBicycleNorthGreen ( ) {
			if ( this.isBicycleNorthGreenAllowed ( ) ) {
				return this.bicycle_north.green ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performBicycleEastGreen ( ) {
			if ( this.isBicycleEastGreenAllowed ( ) ) {
				return this.bicycle_east.green ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean performBicycleWestGreen ( ) {
			if ( this.isBicycleWestGreenAllowed ( ) ) {
				return this.bicycle_west.green ( ) ;
			} else {
				return false ;
			}
		}
		
		public boolean isRightNorthGreenAllowed ( ) {
			boolean allow = true ;
			allow = allow && left_east.allowsRightNorthGreen ( ) ;
			allow = allow && left_west.allowsRightNorthGreen ( ) ;
			allow = allow && left_north.allowsRightNorthGreen ( ) ;
			allow = allow && left_south.allowsRightNorthGreen ( ) ;
			allow = allow && straight_north.allowsRightNorthGreen ( ) ;
			allow = allow && straight_west.allowsRightNorthGreen ( ) ;
			allow = allow && straight_south.allowsRightNorthGreen ( ) ;
			allow = allow && straight_east.allowsRightNorthGreen ( ) ;
			allow = allow && bicycle_south.allowsRightNorthGreen ( ) ;
			allow = allow && bicycle_north.allowsRightNorthGreen ( ) ;
			allow = allow && bicycle_east.allowsRightNorthGreen ( ) ;
			allow = allow && bicycle_west.allowsRightNorthGreen ( ) ;
			return allow ;
		}
		
		public boolean isRightWestGreenAllowed ( ) {
			boolean allow = true ;
			allow = allow && left_east.allowsRightWestGreen ( ) ;
			allow = allow && left_west.allowsRightWestGreen ( ) ;
			allow = allow && left_north.allowsRightWestGreen ( ) ;
			allow = allow && left_south.allowsRightWestGreen ( ) ;
			allow = allow && straight_north.allowsRightWestGreen ( ) ;
			allow = allow && straight_west.allowsRightWestGreen ( ) ;
			allow = allow && straight_south.allowsRightWestGreen ( ) ;
			allow = allow && straight_east.allowsRightWestGreen ( ) ;
			allow = allow && bicycle_south.allowsRightWestGreen ( ) ;
			allow = allow && bicycle_north.allowsRightWestGreen ( ) ;
			allow = allow && bicycle_east.allowsRightWestGreen ( ) ;
			allow = allow && bicycle_west.allowsRightWestGreen ( ) ;
			return allow ;
		}
		
		public boolean isRightSouthGreenAllowed ( ) {
			boolean allow = true ;
			allow = allow && left_east.allowsRightSouthGreen ( ) ;
			allow = allow && left_west.allowsRightSouthGreen ( ) ;
			allow = allow && left_north.allowsRightSouthGreen ( ) ;
			allow = allow && left_south.allowsRightSouthGreen ( ) ;
			allow = allow && straight_north.allowsRightSouthGreen ( ) ;
			allow = allow && straight_west.allowsRightSouthGreen ( ) ;
			allow = allow && straight_south.allowsRightSouthGreen ( ) ;
			allow = allow && straight_east.allowsRightSouthGreen ( ) ;
			allow = allow && bicycle_south.allowsRightSouthGreen ( ) ;
			allow = allow && bicycle_north.allowsRightSouthGreen ( ) ;
			allow = allow && bicycle_east.allowsRightSouthGreen ( ) ;
			allow = allow && bicycle_west.allowsRightSouthGreen ( ) ;
			return allow ;
		}
		
		public boolean isRightEastGreenAllowed ( ) {
			boolean allow = true ;
			allow = allow && left_east.allowsRightEastGreen ( ) ;
			allow = allow && left_west.allowsRightEastGreen ( ) ;
			allow = allow && left_north.allowsRightEastGreen ( ) ;
			allow = allow && left_south.allowsRightEastGreen ( ) ;
			allow = allow && straight_north.allowsRightEastGreen ( ) ;
			allow = allow && straight_west.allowsRightEastGreen ( ) ;
			allow = allow && straight_south.allowsRightEastGreen ( ) ;
			allow = allow && straight_east.allowsRightEastGreen ( ) ;
			allow = allow && bicycle_south.allowsRightEastGreen ( ) ;
			allow = allow && bicycle_north.allowsRightEastGreen ( ) ;
			allow = allow && bicycle_east.allowsRightEastGreen ( ) ;
			allow = allow && bicycle_west.allowsRightEastGreen ( ) ;
			return allow ;
		}
		
		public boolean isStraightNorthGreenAllowed ( ) {
			boolean allow = true ;
			allow = allow && right_north.allowsStraightNorthGreen ( ) ;
			allow = allow && right_west.allowsStraightNorthGreen ( ) ;
			allow = allow && right_south.allowsStraightNorthGreen ( ) ;
			allow = allow && right_east.allowsStraightNorthGreen ( ) ;
			allow = allow && straight_west.allowsStraightNorthGreen ( ) ;
			allow = allow && straight_south.allowsStraightNorthGreen ( ) ;
			allow = allow && straight_east.allowsStraightNorthGreen ( ) ;
			allow = allow && left_east.allowsStraightNorthGreen ( ) ;
			allow = allow && left_west.allowsStraightNorthGreen ( ) ;
			allow = allow && left_north.allowsStraightNorthGreen ( ) ;
			allow = allow && left_south.allowsStraightNorthGreen ( ) ;
			allow = allow && bicycle_south.allowsStraightNorthGreen ( ) ;
			allow = allow && bicycle_north.allowsStraightNorthGreen ( ) ;
			allow = allow && bicycle_east.allowsStraightNorthGreen ( ) ;
			allow = allow && bicycle_west.allowsStraightNorthGreen ( ) ;
			return allow ;
		}
		
		public boolean isStraightWestGreenAllowed ( ) {
			boolean allow = true ;
			allow = allow && right_north.allowsStraightWestGreen ( ) ;
			allow = allow && right_west.allowsStraightWestGreen ( ) ;
			allow = allow && right_south.allowsStraightWestGreen ( ) ;
			allow = allow && right_east.allowsStraightWestGreen ( ) ;
			allow = allow && straight_north.allowsStraightWestGreen ( ) ;
			allow = allow && straight_south.allowsStraightWestGreen ( ) ;
			allow = allow && straight_east.allowsStraightWestGreen ( ) ;
			allow = allow && left_east.allowsStraightWestGreen ( ) ;
			allow = allow && left_west.allowsStraightWestGreen ( ) ;
			allow = allow && left_north.allowsStraightWestGreen ( ) ;
			allow = allow && left_south.allowsStraightWestGreen ( ) ;
			allow = allow && bicycle_south.allowsStraightWestGreen ( ) ;
			allow = allow && bicycle_north.allowsStraightWestGreen ( ) ;
			allow = allow && bicycle_east.allowsStraightWestGreen ( ) ;
			allow = allow && bicycle_west.allowsStraightWestGreen ( ) ;
			return allow ;
		}
		
		public boolean isStraightSouthGreenAllowed ( ) {
			boolean allow = true ;
			allow = allow && right_north.allowsStraightSouthGreen ( ) ;
			allow = allow && right_west.allowsStraightSouthGreen ( ) ;
			allow = allow && right_south.allowsStraightSouthGreen ( ) ;
			allow = allow && right_east.allowsStraightSouthGreen ( ) ;
			allow = allow && straight_north.allowsStraightSouthGreen ( ) ;
			allow = allow && straight_west.allowsStraightSouthGreen ( ) ;
			allow = allow && straight_east.allowsStraightSouthGreen ( ) ;
			allow = allow && left_east.allowsStraightSouthGreen ( ) ;
			allow = allow && left_west.allowsStraightSouthGreen ( ) ;
			allow = allow && left_north.allowsStraightSouthGreen ( ) ;
			allow = allow && left_south.allowsStraightSouthGreen ( ) ;
			allow = allow && bicycle_south.allowsStraightSouthGreen ( ) ;
			allow = allow && bicycle_north.allowsStraightSouthGreen ( ) ;
			allow = allow && bicycle_east.allowsStraightSouthGreen ( ) ;
			allow = allow && bicycle_west.allowsStraightSouthGreen ( ) ;
			return allow ;
		}
		
		public boolean isStraightEastGreenAllowed ( ) {
			boolean allow = true ;
			allow = allow && right_north.allowsStraightEastGreen ( ) ;
			allow = allow && right_west.allowsStraightEastGreen ( ) ;
			allow = allow && right_south.allowsStraightEastGreen ( ) ;
			allow = allow && right_east.allowsStraightEastGreen ( ) ;
			allow = allow && straight_north.allowsStraightEastGreen ( ) ;
			allow = allow && straight_west.allowsStraightEastGreen ( ) ;
			allow = allow && straight_south.allowsStraightEastGreen ( ) ;
			allow = allow && left_east.allowsStraightEastGreen ( ) ;
			allow = allow && left_west.allowsStraightEastGreen ( ) ;
			allow = allow && left_north.allowsStraightEastGreen ( ) ;
			allow = allow && left_south.allowsStraightEastGreen ( ) ;
			allow = allow && bicycle_south.allowsStraightEastGreen ( ) ;
			allow = allow && bicycle_north.allowsStraightEastGreen ( ) ;
			allow = allow && bicycle_east.allowsStraightEastGreen ( ) ;
			allow = allow && bicycle_west.allowsStraightEastGreen ( ) ;
			return allow ;
		}
		
		public boolean isLeftEastGreenAllowed ( ) {
			boolean allow = true ;
			allow = allow && right_north.allowsLeftEastGreen ( ) ;
			allow = allow && right_west.allowsLeftEastGreen ( ) ;
			allow = allow && right_south.allowsLeftEastGreen ( ) ;
			allow = allow && right_east.allowsLeftEastGreen ( ) ;
			allow = allow && straight_north.allowsLeftEastGreen ( ) ;
			allow = allow && straight_west.allowsLeftEastGreen ( ) ;
			allow = allow && straight_south.allowsLeftEastGreen ( ) ;
			allow = allow && straight_east.allowsLeftEastGreen ( ) ;
			allow = allow && left_west.allowsLeftEastGreen ( ) ;
			allow = allow && left_north.allowsLeftEastGreen ( ) ;
			allow = allow && left_south.allowsLeftEastGreen ( ) ;
			allow = allow && bicycle_south.allowsLeftEastGreen ( ) ;
			allow = allow && bicycle_north.allowsLeftEastGreen ( ) ;
			allow = allow && bicycle_east.allowsLeftEastGreen ( ) ;
			allow = allow && bicycle_west.allowsLeftEastGreen ( ) ;
			return allow ;
		}
		
		public boolean isLeftWestGreenAllowed ( ) {
			boolean allow = true ;
			allow = allow && right_north.allowsLeftWestGreen ( ) ;
			allow = allow && right_west.allowsLeftWestGreen ( ) ;
			allow = allow && right_south.allowsLeftWestGreen ( ) ;
			allow = allow && right_east.allowsLeftWestGreen ( ) ;
			allow = allow && straight_north.allowsLeftWestGreen ( ) ;
			allow = allow && straight_west.allowsLeftWestGreen ( ) ;
			allow = allow && straight_south.allowsLeftWestGreen ( ) ;
			allow = allow && straight_east.allowsLeftWestGreen ( ) ;
			allow = allow && left_east.allowsLeftWestGreen ( ) ;
			allow = allow && left_north.allowsLeftWestGreen ( ) ;
			allow = allow && left_south.allowsLeftWestGreen ( ) ;
			allow = allow && bicycle_south.allowsLeftWestGreen ( ) ;
			allow = allow && bicycle_north.allowsLeftWestGreen ( ) ;
			allow = allow && bicycle_east.allowsLeftWestGreen ( ) ;
			allow = allow && bicycle_west.allowsLeftWestGreen ( ) ;
			return allow ;
		}
		
		public boolean isLeftNorthGreenAllowed ( ) {
			boolean allow = true ;
			allow = allow && right_north.allowsLeftNorthGreen ( ) ;
			allow = allow && right_west.allowsLeftNorthGreen ( ) ;
			allow = allow && right_south.allowsLeftNorthGreen ( ) ;
			allow = allow && right_east.allowsLeftNorthGreen ( ) ;
			allow = allow && straight_north.allowsLeftNorthGreen ( ) ;
			allow = allow && straight_west.allowsLeftNorthGreen ( ) ;
			allow = allow && straight_south.allowsLeftNorthGreen ( ) ;
			allow = allow && straight_east.allowsLeftNorthGreen ( ) ;
			allow = allow && left_east.allowsLeftNorthGreen ( ) ;
			allow = allow && left_west.allowsLeftNorthGreen ( ) ;
			allow = allow && left_south.allowsLeftNorthGreen ( ) ;
			allow = allow && bicycle_south.allowsLeftNorthGreen ( ) ;
			allow = allow && bicycle_north.allowsLeftNorthGreen ( ) ;
			allow = allow && bicycle_east.allowsLeftNorthGreen ( ) ;
			allow = allow && bicycle_west.allowsLeftNorthGreen ( ) ;
			return allow ;
		}
		
		public boolean isLeftSouthGreenAllowed ( ) {
			boolean allow = true ;
			allow = allow && right_north.allowsLeftSouthGreen ( ) ;
			allow = allow && right_west.allowsLeftSouthGreen ( ) ;
			allow = allow && right_south.allowsLeftSouthGreen ( ) ;
			allow = allow && right_east.allowsLeftSouthGreen ( ) ;
			allow = allow && straight_north.allowsLeftSouthGreen ( ) ;
			allow = allow && straight_west.allowsLeftSouthGreen ( ) ;
			allow = allow && straight_south.allowsLeftSouthGreen ( ) ;
			allow = allow && straight_east.allowsLeftSouthGreen ( ) ;
			allow = allow && left_east.allowsLeftSouthGreen ( ) ;
			allow = allow && left_west.allowsLeftSouthGreen ( ) ;
			allow = allow && left_north.allowsLeftSouthGreen ( ) ;
			allow = allow && bicycle_south.allowsLeftSouthGreen ( ) ;
			allow = allow && bicycle_north.allowsLeftSouthGreen ( ) ;
			allow = allow && bicycle_east.allowsLeftSouthGreen ( ) ;
			allow = allow && bicycle_west.allowsLeftSouthGreen ( ) ;
			return allow ;
		}
		
		public boolean isBicycleSouthGreenAllowed ( ) {
			boolean allow = true ;
			allow = allow && right_north.allowsBicycleSouthGreen ( ) ;
			allow = allow && right_west.allowsBicycleSouthGreen ( ) ;
			allow = allow && right_south.allowsBicycleSouthGreen ( ) ;
			allow = allow && right_east.allowsBicycleSouthGreen ( ) ;
			allow = allow && straight_north.allowsBicycleSouthGreen ( ) ;
			allow = allow && straight_west.allowsBicycleSouthGreen ( ) ;
			allow = allow && straight_south.allowsBicycleSouthGreen ( ) ;
			allow = allow && straight_east.allowsBicycleSouthGreen ( ) ;
			allow = allow && left_east.allowsBicycleSouthGreen ( ) ;
			allow = allow && left_west.allowsBicycleSouthGreen ( ) ;
			allow = allow && left_north.allowsBicycleSouthGreen ( ) ;
			allow = allow && left_south.allowsBicycleSouthGreen ( ) ;
			return allow ;
		}
		
		public boolean isBicycleNorthGreenAllowed ( ) {
			boolean allow = true ;
			allow = allow && right_north.allowsBicycleNorthGreen ( ) ;
			allow = allow && right_west.allowsBicycleNorthGreen ( ) ;
			allow = allow && right_south.allowsBicycleNorthGreen ( ) ;
			allow = allow && right_east.allowsBicycleNorthGreen ( ) ;
			allow = allow && straight_north.allowsBicycleNorthGreen ( ) ;
			allow = allow && straight_west.allowsBicycleNorthGreen ( ) ;
			allow = allow && straight_south.allowsBicycleNorthGreen ( ) ;
			allow = allow && straight_east.allowsBicycleNorthGreen ( ) ;
			allow = allow && left_east.allowsBicycleNorthGreen ( ) ;
			allow = allow && left_west.allowsBicycleNorthGreen ( ) ;
			allow = allow && left_north.allowsBicycleNorthGreen ( ) ;
			allow = allow && left_south.allowsBicycleNorthGreen ( ) ;
			return allow ;
		}
		
		public boolean isBicycleEastGreenAllowed ( ) {
			boolean allow = true ;
			allow = allow && right_north.allowsBicycleEastGreen ( ) ;
			allow = allow && right_west.allowsBicycleEastGreen ( ) ;
			allow = allow && right_south.allowsBicycleEastGreen ( ) ;
			allow = allow && right_east.allowsBicycleEastGreen ( ) ;
			allow = allow && straight_north.allowsBicycleEastGreen ( ) ;
			allow = allow && straight_west.allowsBicycleEastGreen ( ) ;
			allow = allow && straight_south.allowsBicycleEastGreen ( ) ;
			allow = allow && straight_east.allowsBicycleEastGreen ( ) ;
			allow = allow && left_east.allowsBicycleEastGreen ( ) ;
			allow = allow && left_west.allowsBicycleEastGreen ( ) ;
			allow = allow && left_north.allowsBicycleEastGreen ( ) ;
			allow = allow && left_south.allowsBicycleEastGreen ( ) ;
			return allow ;
		}
		
		public boolean isBicycleWestGreenAllowed ( ) {
			boolean allow = true ;
			allow = allow && right_north.allowsBicycleWestGreen ( ) ;
			allow = allow && right_west.allowsBicycleWestGreen ( ) ;
			allow = allow && right_south.allowsBicycleWestGreen ( ) ;
			allow = allow && right_east.allowsBicycleWestGreen ( ) ;
			allow = allow && straight_north.allowsBicycleWestGreen ( ) ;
			allow = allow && straight_west.allowsBicycleWestGreen ( ) ;
			allow = allow && straight_south.allowsBicycleWestGreen ( ) ;
			allow = allow && straight_east.allowsBicycleWestGreen ( ) ;
			allow = allow && left_east.allowsBicycleWestGreen ( ) ;
			allow = allow && left_west.allowsBicycleWestGreen ( ) ;
			allow = allow && left_north.allowsBicycleWestGreen ( ) ;
			allow = allow && left_south.allowsBicycleWestGreen ( ) ;
			return allow ;
		}
		
		public HashMap<String,HashMap<String,String>> getStates ( ) {
			HashMap<String, HashMap<String, String>> states = new HashMap<String, HashMap<String, String>>();
			HashMap<String, String> instance_states = null;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "winddir" , String.valueOf ( this.right_north.winddir ) ) ;
			instance_states.put ( "color" , String.valueOf ( this.right_north.color ) ) ;
			states.put ( "right_north" , instance_states ) ;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "winddir" , String.valueOf ( this.right_west.winddir ) ) ;
			instance_states.put ( "color" , String.valueOf ( this.right_west.color ) ) ;
			states.put ( "right_west" , instance_states ) ;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "winddir" , String.valueOf ( this.right_south.winddir ) ) ;
			instance_states.put ( "color" , String.valueOf ( this.right_south.color ) ) ;
			states.put ( "right_south" , instance_states ) ;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "winddir" , String.valueOf ( this.right_east.winddir ) ) ;
			instance_states.put ( "color" , String.valueOf ( this.right_east.color ) ) ;
			states.put ( "right_east" , instance_states ) ;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "winddir" , String.valueOf ( this.straight_north.winddir ) ) ;
			instance_states.put ( "color" , String.valueOf ( this.straight_north.color ) ) ;
			states.put ( "straight_north" , instance_states ) ;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "winddir" , String.valueOf ( this.straight_west.winddir ) ) ;
			instance_states.put ( "color" , String.valueOf ( this.straight_west.color ) ) ;
			states.put ( "straight_west" , instance_states ) ;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "winddir" , String.valueOf ( this.straight_south.winddir ) ) ;
			instance_states.put ( "color" , String.valueOf ( this.straight_south.color ) ) ;
			states.put ( "straight_south" , instance_states ) ;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "winddir" , String.valueOf ( this.straight_east.winddir ) ) ;
			instance_states.put ( "color" , String.valueOf ( this.straight_east.color ) ) ;
			states.put ( "straight_east" , instance_states ) ;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "winddir" , String.valueOf ( this.left_east.winddir ) ) ;
			instance_states.put ( "color" , String.valueOf ( this.left_east.color ) ) ;
			states.put ( "left_east" , instance_states ) ;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "winddir" , String.valueOf ( this.left_west.winddir ) ) ;
			instance_states.put ( "color" , String.valueOf ( this.left_west.color ) ) ;
			states.put ( "left_west" , instance_states ) ;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "winddir" , String.valueOf ( this.left_north.winddir ) ) ;
			instance_states.put ( "color" , String.valueOf ( this.left_north.color ) ) ;
			states.put ( "left_north" , instance_states ) ;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "winddir" , String.valueOf ( this.left_south.winddir ) ) ;
			instance_states.put ( "color" , String.valueOf ( this.left_south.color ) ) ;
			states.put ( "left_south" , instance_states ) ;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "winddir" , String.valueOf ( this.bicycle_south.winddir ) ) ;
			instance_states.put ( "color" , String.valueOf ( this.bicycle_south.color ) ) ;
			states.put ( "bicycle_south" , instance_states ) ;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "winddir" , String.valueOf ( this.bicycle_north.winddir ) ) ;
			instance_states.put ( "color" , String.valueOf ( this.bicycle_north.color ) ) ;
			states.put ( "bicycle_north" , instance_states ) ;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "winddir" , String.valueOf ( this.bicycle_east.winddir ) ) ;
			instance_states.put ( "color" , String.valueOf ( this.bicycle_east.color ) ) ;
			states.put ( "bicycle_east" , instance_states ) ;
			instance_states = new HashMap<String, String>();
			instance_states.put ( "winddir" , String.valueOf ( this.bicycle_west.winddir ) ) ;
			instance_states.put ( "color" , String.valueOf ( this.bicycle_west.color ) ) ;
			states.put ( "bicycle_west" , instance_states ) ;
			return states ;
		}
		
	}
	
