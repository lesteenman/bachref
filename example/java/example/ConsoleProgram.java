package example ;
import example.models.* ;
import example.models.Sorts.* ;
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
			case "traffic_light_2_yellow" :
				result = controller.performTrafficLight2Yellow ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "light2_green" :
				result = controller.performLight2Green ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "light1_green" :
				result = controller.performLight1Green ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "light2_red" :
				result = controller.performLight2Red ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "light1_red" :
				result = controller.performLight1Red ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "traffic_light_3_green" :
				result = controller.performTrafficLight3Green ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "traffic_light_3_yellow" :
				result = controller.performTrafficLight3Yellow ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "traffic_light_3_red" :
				result = controller.performTrafficLight3Red ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "traffic_light_2_green" :
				result = controller.performTrafficLight2Green ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "traffic_light_2_red" :
				result = controller.performTrafficLight2Red ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "light2_yellow" :
				result = controller.performLight2Yellow ( ) ;
				if (result)
					System.out.println("executed");
				else
					System.out.println("could not execute");
				break ;
			
			case "light1_yellow" :
				result = controller.performLight1Yellow ( ) ;
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

