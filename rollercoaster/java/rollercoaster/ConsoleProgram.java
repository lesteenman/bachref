package rollercoaster ;
import rollercoaster.models.* ;
import rollercoaster.models.Sorts.* ;
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
			case "button_push_button" :
				result = controller.performButtonPushButton ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "gate_open" :
				result = controller.performGateOpen ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "sw_up" :
				result = controller.performSwUp ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "sw_down" :
				result = controller.performSwDown ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "cart2_backward" :
				if ( args.length != 2 ) {
					System.out.println("Invalid number of parameters, expected: 2 ");
					return;
				}
				result = controller.performCart2Backward ( Segment.valueOf ( args [ 0 ] ) , Segment.valueOf ( args [ 1 ] ) ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "cart1_backward" :
				if ( args.length != 2 ) {
					System.out.println("Invalid number of parameters, expected: 2 ");
					return;
				}
				result = controller.performCart1Backward ( Segment.valueOf ( args [ 0 ] ) , Segment.valueOf ( args [ 1 ] ) ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "cart2_forward" :
				if ( args.length != 2 ) {
					System.out.println("Invalid number of parameters, expected: 2 ");
					return;
				}
				result = controller.performCart2Forward ( Segment.valueOf ( args [ 0 ] ) , Segment.valueOf ( args [ 1 ] ) ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "cart1_forward" :
				if ( args.length != 2 ) {
					System.out.println("Invalid number of parameters, expected: 2 ");
					return;
				}
				result = controller.performCart1Forward ( Segment.valueOf ( args [ 0 ] ) , Segment.valueOf ( args [ 1 ] ) ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "cart2_close_braces" :
				result = controller.performCart2CloseBraces ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "cart1_close_braces" :
				result = controller.performCart1CloseBraces ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "cart2_open_braces" :
				if ( args.length != 1 ) {
					System.out.println("Invalid number of parameters, expected: 1 ");
					return;
				}
				result = controller.performCart2OpenBraces ( Segment.valueOf ( args [ 0 ] ) ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "cart1_open_braces" :
				if ( args.length != 1 ) {
					System.out.println("Invalid number of parameters, expected: 1 ");
					return;
				}
				result = controller.performCart1OpenBraces ( Segment.valueOf ( args [ 0 ] ) ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "gate_close" :
				result = controller.performGateClose ( ) ;
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

