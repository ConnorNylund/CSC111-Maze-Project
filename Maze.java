// Maze tracking 
//
// Connor Nylund
// 3/7/24

import java.util.Scanner;
import java.io.*;

public class Maze {

    public static int width; // Width of the maze
    public static int height; // Height of the maze
    public static int startCol; // Column index of the start point
    public static int endCol; // Column index of the finish point

    public static final char WALL = 'x'; // Wall character
    public static final char CLEAR = ' '; // Clear space character
    public static final char START = 'S'; // Start point character
    public static final char FINISH = 'F'; // Finish point character
    public static final char PATH = 'P'; // Path character
    public static final char VISITED = 'V'; // Visited character

    public static void main(String[] args) {
        String inputLine; 
        char[][] maze; 

        Scanner fileInput; // Scanner to read from file
        File inFile = new File("Maze.txt"); 

        // Print message the program is reading the file
        System.out.println("Opening and reading file");

        try {
            fileInput = new Scanner(inFile);

            if (fileInput.hasNextInt()) {
                width = fileInput.nextInt();
                height = fileInput.nextInt();

                fileInput.nextLine(); 
                endCol = fileInput.nextInt();
                fileInput.nextLine(); 
                startCol = fileInput.nextInt();
                fileInput.nextLine(); 
            }

            // Initialize the maze array
            maze = new char[height][width];

            int row = 0; 
            while (fileInput.hasNextLine()) {
                inputLine = fileInput.nextLine(); // Read the next line from the file
                for (int col = 0; col < inputLine.length(); col++) {
                    maze[row][col] = inputLine.charAt(col); // Store in maze array
                    if (row == 0 && inputLine.charAt(col) == ' ') {
                        maze[row][col] = 'F';
                    }
                    if (row == height - 1 && inputLine.charAt(col) == ' ') {
                        maze[row][col] = 'S';
                    }
                }
                row++; 
            }

            solveMaze(maze, height - 1, startCol);

            fileInput.close(); 
        } catch (FileNotFoundException e) { // Catch block 
            System.out.println(e); 
            System.exit(1); 
        }

    }

    public static boolean solveMaze(char[][] maze, int row, int col) {
        // Base case
        if (row < 0 || col < 0 || row >= height || col >= width || maze[row][col] == WALL || maze[row][col] == VISITED) {
            return false;
        }

        if (maze[row][col] == FINISH) {
            maze[row][col] = PATH;
            display(maze); // Display the maze with the solution path
            System.out.println("\nSolution found!"); 
            return true; 
        }

        // Mark the current position as visited
        maze[row][col] = VISITED;

        if (solveMaze(maze, row - 1, col) || solveMaze(maze, row + 1, col) || solveMaze(maze, row, col - 1)
                || solveMaze(maze, row, col + 1)) {
            maze[row][col] = PATH;
            return true; // Return true solution found
        }

        return false; // Return false no solution found
    }

    // Method to display the maze
    public static void display(char[][] maze) {
        for (int i = 0; i < height; i++) {
            System.out.println(); 
            for (int j = 0; j < width; j++) {
                System.out.print(maze[i][j]); 
            }
        }

        System.out.println("\nEnd of program"); // Print message end of program
    }

}