package tictactoe;

public class Board {
    final Cell[][] boardArray;


    public Board() {
        this.boardArray = new Cell[3][3];
        for (int j = 0; j < boardArray.length; j++) {
            for (int i = 0; i < boardArray[j].length; i++) {
                boardArray[j][i] = new Cell(' ', new int[]{j, i});
            }


        }
//        for (Cell[] cells : boardArray) { //initial fill of the board array with spaces
//            Arrays.fill(cells, ' ');//board array is just the slots for gameplay
//        }
    }

    public static String toAlgebraic(int y, int x) {
        return "" + ((char) (x + 'A')) + (y + 1);
    }

    public static int[] fromAlgebraic(String coordString) {
        int[] coords = new int[2];
        coords[1] = coordString.charAt(0) - 'A'; // be careful, all your old code is [y,x]
        coords[0] = coordString.charAt(1) - '0' - 1;
        return coords;
    }

    void print() {
        System.out.println("---------");
        for (Cell[] ints : boardArray) {// for each line
            System.out.print("| ");
            for (Cell cell : ints) {
                System.out.print(cell.symbol + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    Cell[][] getLineArray() {
        Cell[][] lineArray = new Cell[8][3];
        System.arraycopy(boardArray, 0, lineArray, 0, 3); //horizontal lines
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                lineArray[j + 3][i] = boardArray[i][j]; //vertical lines
            }
        }
        for (int i = 0; i < 3; i++) {
            lineArray[6][i] = boardArray[i][i]; //diagonal top left to bottom right
        }
        for (int i = 0; i < 3; i++) {
            lineArray[7][i] = boardArray[i][2 - i];//diagonal top right to bottom left
        }
        return lineArray;
    }

    void play(int[] coords, char playerToken) {
        this.boardArray[coords[0]][coords[1]].symbol = playerToken;
    }

    public String getCell(String coords) {
        int y = coords.charAt(0) - 'A'; // be careful, all your old code is [y,x]
        int x = coords.charAt(1) - '0' - 1;

        return this.boardArray[y][x].toString();
    }

    class Cell {
        char symbol;
        int[] coords = new int[2];
        public Cell(char symbol, int[] coords) {
            this.symbol = symbol;
            this.coords = coords;
        }

        @Override
        public String toString() {
            return "" + symbol;
        }

        public int[] getCoords() {
            return coords;
        }
    }
}