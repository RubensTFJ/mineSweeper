import java.util.Scanner;

public class InputClass {

	private Scanner scanner = new Scanner(System.in);
	private String	input[] = new String[2];
	private int	x = 0;
	private int	y = 0;

	public void scanInput() {
		input[0] = scanner.nextLine();
	}

	public String getInput() {
		return (input[0]);
	}

	public void	closeScanner() {
		scanner.close();
	}
}
