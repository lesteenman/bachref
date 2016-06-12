package trafficlights ;
import trafficlights.models.* ;
import trafficlights.models.Sorts.* ;
import java.io.* ;
import java.util.HashMap ;
import java.util.Map ;
import java.util.Iterator ;


public class ConsoleProgram {
	Controller controller ; boolean jtorx ;
	
	public static void main ( String[] args ) {
		boolean jtorx = args.length > 0 && args[0].equals("jtorx");
		new ConsoleProgram(jtorx);
	}
	
	public ConsoleProgram ( boolean jtorx ) {
		this.jtorx = jtorx;
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
			this.outputError("Error while reading from console: " + e.getMessage());
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
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "left_west_red" :
				result = controller.performLeftWestRed ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "left_north_red" :
				result = controller.performLeftNorthRed ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "left_south_red" :
				result = controller.performLeftSouthRed ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "right_north_yellow" :
				result = controller.performRightNorthYellow ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "right_west_yellow" :
				result = controller.performRightWestYellow ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "right_south_yellow" :
				result = controller.performRightSouthYellow ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "right_east_yellow" :
				result = controller.performRightEastYellow ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "straight_north_green" :
			case "perform_straight_north_green" :
				result = controller.performStraightNorthGreen ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "straight_west_green" :
			case "perform_straight_west_green" :
				result = controller.performStraightWestGreen ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "straight_south_green" :
			case "perform_straight_south_green" :
				result = controller.performStraightSouthGreen ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "straight_east_green" :
			case "perform_straight_east_green" :
				result = controller.performStraightEastGreen ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "left_east_yellow" :
				result = controller.performLeftEastYellow ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "left_west_yellow" :
				result = controller.performLeftWestYellow ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "left_north_yellow" :
				result = controller.performLeftNorthYellow ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "left_south_yellow" :
				result = controller.performLeftSouthYellow ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "right_north_green" :
			case "perform_right_north_green" :
				result = controller.performRightNorthGreen ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "right_west_green" :
			case "perform_right_west_green" :
				result = controller.performRightWestGreen ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "right_south_green" :
			case "perform_right_south_green" :
				result = controller.performRightSouthGreen ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "right_east_green" :
			case "perform_right_east_green" :
				result = controller.performRightEastGreen ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "bicycle_south_red" :
				result = controller.performBicycleSouthRed ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "bicycle_north_red" :
				result = controller.performBicycleNorthRed ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "bicycle_east_red" :
				result = controller.performBicycleEastRed ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "bicycle_west_red" :
				result = controller.performBicycleWestRed ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "bicycle_south_yellow" :
				result = controller.performBicycleSouthYellow ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "bicycle_north_yellow" :
				result = controller.performBicycleNorthYellow ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "bicycle_east_yellow" :
				result = controller.performBicycleEastYellow ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "bicycle_west_yellow" :
				result = controller.performBicycleWestYellow ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "straight_north_red" :
				result = controller.performStraightNorthRed ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "straight_west_red" :
				result = controller.performStraightWestRed ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "straight_south_red" :
				result = controller.performStraightSouthRed ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "straight_east_red" :
				result = controller.performStraightEastRed ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "left_east_green" :
			case "perform_left_east_green" :
				result = controller.performLeftEastGreen ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "left_west_green" :
			case "perform_left_west_green" :
				result = controller.performLeftWestGreen ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "left_north_green" :
			case "perform_left_north_green" :
				result = controller.performLeftNorthGreen ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "left_south_green" :
			case "perform_left_south_green" :
				result = controller.performLeftSouthGreen ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "straight_north_yellow" :
				result = controller.performStraightNorthYellow ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "straight_west_yellow" :
				result = controller.performStraightWestYellow ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "straight_south_yellow" :
				result = controller.performStraightSouthYellow ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "straight_east_yellow" :
				result = controller.performStraightEastYellow ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "right_north_red" :
				result = controller.performRightNorthRed ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "right_west_red" :
				result = controller.performRightWestRed ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "right_south_red" :
				result = controller.performRightSouthRed ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "right_east_red" :
				result = controller.performRightEastRed ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "bicycle_south_green" :
			case "perform_bicycle_south_green" :
				result = controller.performBicycleSouthGreen ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "bicycle_north_green" :
			case "perform_bicycle_north_green" :
				result = controller.performBicycleNorthGreen ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "bicycle_east_green" :
			case "perform_bicycle_east_green" :
				result = controller.performBicycleEastGreen ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			case "bicycle_west_green" :
			case "perform_bicycle_west_green" :
				result = controller.performBicycleWestGreen ( ) ;
				if (result)
					this.output("executed");
				else
					this.outputError("could not execute");
				break ;
			
			default:
				this.outputError("Unrecognized input: " + method);
				break;
		}
	}
	
	public void printStates ( ) {
		this.output("Current states:");
		HashMap<String, HashMap<String, String>> states = this.controller.getStates();
		Iterator actor_it = states.entrySet().iterator();
		while (actor_it.hasNext()) {
			Map.Entry actor_pair = (Map.Entry)actor_it.next();
			String actor = (String)actor_pair.getKey();
			this.output("Actor: " + actor);
			Iterator state_it = ((HashMap)actor_pair.getValue()).entrySet().iterator();
			while (state_it.hasNext()) {
				Map.Entry state_pair = (Map.Entry)state_it.next();
				this.output("	" + (String)state_pair.getKey() + ":	" + (String)state_pair.getValue());
			}
		}
		this.output("\r\n\r\n");
	}
	
	public void output ( String output ) {
		if (!this.jtorx) {
			System.out.println(output);
		}
	}
	
	public void outputError ( String output ) {
		System.out.println(output);
	}
	
}

