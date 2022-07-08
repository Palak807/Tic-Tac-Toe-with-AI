package tictactoe;

import java.util.Scanner;

public class InitialSetup {

    static State state = State.GAME_NOT_FINISHED;
    static boolean xsTurn = true;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (true) {
            System.out.print("Enter the cells: ");
            input = scanner.nextLine().toUpperCase().replace("_", " ");
            if (input.matches("[XO ]{9}")) {
                break;
            }
        }

        char[][] cellsNormal = new char[3][3];
        char[][] cellsRotated = new char[3][3];

        int k = 0;
        int xCount = 0;
        int oCount = 0;
        for (short i = 0; i < 3; i++) {
            for (short j = 0; j < 3; j++) {
                char val = input.charAt(k);
                cellsNormal[i][j] = val;
                cellsRotated[j][i] = val;
                if (val == 'X') {
                    xCount++;
                } else if (val == 'O') {
                    oCount++;
                }
                k++;
            }
        }

        if (xCount > oCount) {
            xsTurn = false;
        }

        printCells(cellsNormal);

        while (true) {
            System.out.print("Enter the coordinates: ");
            String indexString = scanner.nextLine().replace(" ", "");
            boolean isInt = true;

            try {
                Integer.parseInt(indexString);
            } catch (NumberFormatException e) {
                isInt = false;
            }

            if (isInt && indexString.length() > 1) {
                int indexX = Character.getNumericValue(indexString.charAt(0)) - 1;
                int indexY = Character.getNumericValue(indexString.charAt(1)) - 1;

                if (indexX > 2 || indexX < 0 || indexY > 2 || indexY < 0) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } else if (cellsNormal[indexX][indexY] == ' ') {

                    if (!xsTurn) {
                        cellsNormal[indexX][indexY] = 'O';
                        cellsRotated[indexY][indexX] = 'O';
                        oCount++;
                    } else {
                        cellsNormal[indexX][indexY] = 'X';
                        cellsRotated[indexY][indexX] = 'X';
                        xCount++;
                    }
                    printCells(cellsNormal);
                    checkAndSetState(cellsNormal, cellsRotated);
                    break;

                } else {
                    System.out.println("This cell is occupied! Choose another one!");
                }
            } else {
                System.out.println("You should enter numbers!");
            }
        }

        if (xCount + oCount == 9 && state != State.O_WINS && state != State.X_WINS) {
            state = State.DRAW;
        }
        System.out.println(state.getExpression());
    }

    private static void printCells(char[][] cellsNormal) {

        System.out.println("---------");
        for (char[] cells : cellsNormal) {
            System.out.print("| ");
            for (char cell : cells) {
                System.out.print(cell + " ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("---------");
    }

    private static void checkAndSetState(char[][] cellsNormal, char[][] cellsRotated) {

        // check X axis
        for (char[] cells : cellsNormal) {
            compareCells(cells);
        }
        // check Y axis
        for (char[] cells : cellsRotated) {
            compareCells(cells);
        }
        // check diagonal
        compareCells(new char[]{cellsNormal[0][0], cellsNormal[1][1], cellsNormal[2][2]});
        compareCells(new char[]{cellsNormal[0][2], cellsNormal[1][1], cellsNormal[2][0]});
    }

    private static void compareCells(char[] row) {

        if (row[0] == row[1] && row[1] == row[2]) {
            if (row[0] == 'X') {
                state = State.X_WINS;
            } else if (row[0] == 'O') {
                state = State.O_WINS;
            }
        }
    }
}

enum State {
    GAME_NOT_FINISHED("Game not finished"),
    DRAW("Draw"),
    X_WINS("X wins"),
    O_WINS("O wins");

    private final String expression;

    State(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return this.expression;
    }
}