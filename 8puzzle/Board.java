/* *****************************************************************************
 *  Name: Fiona Tian
 *  Date: 2019/7/23
 *  Description: Board.java
 **************************************************************************** */

import java.util.LinkedList;
import java.util.List;

public class Board {


    private final int n;
    private final int[][] tiles;
    private boolean hammingDisCalculated;
    private int hammingDis;
    private boolean manhattanDisCalculated;
    private int manhattanDis;
    private int openX;
    private int openY;


    public Board(int[][] tiles) {

        if (tiles == null) throw new java.lang.IllegalArgumentException("null given to Board()");
        n = tiles.length;
        this.tiles = new int[n][];
        hammingDisCalculated = false;
        manhattanDisCalculated = false;

        for (int i = 0; i < n; i++) {
            this.tiles[i] = new int[n];
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    openX = i;
                    openY = j;
                }
            }
        }
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(tiles[i][j] + " ");
            }
            s.append("\n");
        }
        //s.append("\n");
        return s.toString();
    }

    public int dimension() {
        return n;
    }


    // number of tiles out of place
    public int hamming() {
        if (!hammingDisCalculated) {
            hammingDis = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if ((tiles[i][j] != 0) && (expected(i, j) != tiles[i][j])) {
                        hammingDis++;
                    }
                }
            }
            hammingDisCalculated = true;
        }
        return hammingDis;
    }

    private int manhattan(int i, int j, int val) {

        int row;
        int col;

        // expected row and col in goal
        if (val == 0) {
            return 0;
        }
        else {
            row = (val - 1) / n;
            col = (val - 1) % n;
        }
        int distance = Math.abs(i - row) + Math.abs(j - col);
        return distance;
    }

    public int manhattan() {
        if (!manhattanDisCalculated) {
            manhattanDis = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    manhattanDis = manhattanDis + manhattan(i, j, tiles[i][j]);
                }
            }
            manhattanDisCalculated = true;
        }
        return manhattanDis;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (!(y.getClass() == this.getClass())) {
            return false;
        }
        Board yy = (Board) y;
        if (this.n != yy.n) return false;

        if (this.openX != yy.openX || this.openY != yy.openY) return false;

        // Arrays.deepEquals(a,b)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != yy.tiles[i][j]) return false;
            }
        }
        return true;

    }


    public Iterable<Board> neighbors() {

        List<Board> listNei = new LinkedList<Board>();

        if (openX > 0) {
            int openXLeft = this.openX - 1;
            int[][] neiTiles = copyTiles(tiles);
            swap(neiTiles, openX, openY, openXLeft, openY);
            listNei.add(new Board(neiTiles));
        }
        if (openX < n - 1) {
            int openXRight = this.openX + 1;
            int[][] neiTiles = copyTiles(tiles);
            swap(neiTiles, openX, openY, openXRight, openY);
            listNei.add(new Board(neiTiles));
        }
        if (openY > 0) {
            int openYUp = this.openY - 1;
            int[][] neiTiles = copyTiles(tiles);
            swap(neiTiles, openX, openY, openX, openYUp);
            listNei.add(new Board(neiTiles));
        }
        if (openY < n - 1) {
            int openYDown = this.openY + 1;
            int[][] neiTiles = copyTiles(tiles);
            swap(neiTiles, openX, openY, openX, openYDown);
            listNei.add(new Board(neiTiles));
        }

        return listNei;

    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinTiles = copyTiles(tiles);
        if (openY == 0) {
            swap(twinTiles, 0, 1, 1, 1);
        }
        else {
            swap(twinTiles, 0, 0, 1, 0);
        }
        return new Board(twinTiles);
    }

    // helper functions for manipulating tiles array

    private int[][] copyTiles(int[][] tiles) {
        int n = tiles.length;
        int[][] newTiles = new int[n][];
        for (int i = 0; i < n; i++) {
            newTiles[i] = tiles[i].clone();
        }
        return newTiles;
    }

    private int expected(int i, int j) {
        if ((i == n - 1) && (j == n - 1)) return 0;
        return i * n + j + 1;
    }

    private void swap(int[][] tiles, int xA, int yA, int xB, int yB) {
        int temp = tiles[xA][yA];
        tiles[xA][yA] = tiles[xB][yB];
        tiles[xB][yB] = temp;
    }


    public static void main(String[] args) {

    }
}
