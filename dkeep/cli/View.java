package dkeep.cli;

public class View {

	public static void displayBoard(char matrix[][]) {
		System.out.println("\n\n");
		String currentLine;

		for (int i = 0; i < matrix.length; i++) {
			currentLine = "";
			for (int j = 0; j < matrix[i].length; j++) {
				currentLine += " " + matrix[i][j];
			}
			System.out.println(currentLine);
		}
		
	}

}
