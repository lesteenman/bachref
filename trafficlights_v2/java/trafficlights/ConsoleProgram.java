package trafficlights ;
import trafficlights.models.* ;
import trafficlights.models.Sorts.* ;
import java.io.* ;
import java.util.HashMap ;
import java.util.Map ;
import java.util.Iterator ;


public class ConsoleProgram {
	Controller controller ;
	
	public static void main ( String[] args ) {
		new ConsoleProgram();
	}
	
	public ConsoleProgram ( ) {
		this.controller = new Controller() ;
		InputStreamReader inputStream = new InputStreamReader(System.in) ;
		BufferedReader inputReader = new BufferedReader(inputStream);
		this.printStates();
		try {
			while (true) {
				if (inputReader.ready()) {
					String input = inputReader.readLine() ;
					this.handleInput(input);
					this.printStates();
				}
			}
		} catch (IOException e) {
			System.out.println("Error while reading from console: " + e.getMessage());
		}
	}
	
	public void handleInput ( String input ) {
		String[] split = input.split(" ");
		String method = split[0];
		boolean result = true;
		String[] args = new String[ split.length - 1 ];
		System.arraycopy ( split, 1, args, 0, args.length);
		
		switch ( method ) {
			case "left_east_red" :
				result = controller.performLeftEastRed ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "left_west_red" :
				result = controller.performLeftWestRed ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "left_north_red" :
				result = controller.performLeftNorthRed ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "left_south_red" :
				result = controller.performLeftSouthRed ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "right_north_yellow" :
				result = controller.performRightNorthYellow ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "right_west_yellow" :
				result = controller.performRightWestYellow ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "right_south_yellow" :
				result = controller.performRightSouthYellow ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "right_east_yellow" :
				result = controller.performRightEastYellow ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "straight_north_green" :
				result = controller.performStraightNorthGreen ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "straight_west_green" :
				result = controller.performStraightWestGreen ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "straight_south_green" :
				result = controller.performStraightSouthGreen ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "straight_east_green" :
				result = controller.performStraightEastGreen ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "left_east_yellow" :
				result = controller.performLeftEastYellow ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "left_west_yellow" :
				result = controller.performLeftWestYellow ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "left_north_yellow" :
				result = controller.performLeftNorthYellow ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "left_south_yellow" :
				result = controller.performLeftSouthYellow ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "right_north_green" :
				result = controller.performRightNorthGreen ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "right_west_green" :
				result = controller.performRightWestGreen ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "right_south_green" :
				result = controller.performRightSouthGreen ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "right_east_green" :
				result = controller.performRightEastGreen ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "bicycle_south_red" :
				result = controller.performBicycleSouthRed ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "bicycle_north_red" :
				result = controller.performBicycleNorthRed ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "bicycle_east_red" :
				result = controller.performBicycleEastRed ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "bicycle_west_red" :
				result = controller.performBicycleWestRed ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "bicycle_south_yellow" :
				result = controller.performBicycleSouthYellow ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "bicycle_north_yellow" :
				result = controller.performBicycleNorthYellow ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "bicycle_east_yellow" :
				result = controller.performBicycleEastYellow ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "bicycle_west_yellow" :
				result = controller.performBicycleWestYellow ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "straight_north_red" :
				result = controller.performStraightNorthRed ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "straight_west_red" :
				result = controller.performStraightWestRed ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "straight_south_red" :
				result = controller.performStraightSouthRed ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "straight_east_red" :
				result = controller.performStraightEastRed ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "left_east_green" :
				result = controller.performLeftEastGreen ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "left_west_green" :
				result = controller.performLeftWestGreen ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "left_north_green" :
				result = controller.performLeftNorthGreen ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "left_south_green" :
				result = controller.performLeftSouthGreen ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "straight_north_yellow" :
				result = controller.performStraightNorthYellow ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "straight_west_yellow" :
				result = controller.performStraightWestYellow ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "straight_south_yellow" :
				result = controller.performStraightSouthYellow ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "straight_east_yellow" :
				result = controller.performStraightEastYellow ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "right_north_red" :
				result = controller.performRightNorthRed ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "right_west_red" :
				result = controller.performRightWestRed ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "right_south_red" :
				result = controller.performRightSouthRed ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "right_east_red" :
				result = controller.performRightEastRed ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "bicycle_south_green" :
				result = controller.performBicycleSouthGreen ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "bicycle_north_green" :
				result = controller.performBicycleNorthGreen ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "bicycle_east_green" :
				result = controller.performBicycleEastGreen ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "bicycle_west_green" :
				result = controller.performBicycleWestGreen ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			default:
				System.out.println("Unrecognized input: " + method);
				break;
		}
	}
	
	public void printStates ( ) {
		System.out.println("Current states:");
		HashMap<String, HashMap<String, String>> states = this.controller.getStates();
		Iterator actor_it = states.entrySet().iterator();
		while (actor_it.hasNext()) {
			Map.Entry actor_pair = (Map.Entry)actor_it.next();
			String actor = (String)actor_pair.getKey();
			System.out.println("Actor: " + actor);
			Iterator state_it = ((HashMap)actor_pair.getValue()).entrySet().iterator();
			while (state_it.hasNext()) {
				Map.Entry state_pair = (Map.Entry)state_it.next();
				System.out.println("	" + (String)state_pair.getKey() + ":	" + (String)state_pair.getValue());
			}
		}
		System.out.println("\r\n\r\n");
	}
	
}

