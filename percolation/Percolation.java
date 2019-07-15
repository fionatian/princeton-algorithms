/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF sys;
    private final WeightedQuickUnionUF sysNoBot;
    private final int top; // psuedo top node
    private final int bot; // psuedo bottom node
    private boolean[] sysOpen;
    private int numOpenSites;
    private final int gridWidth;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("grid size " + n + " is not greater than 0");
        }


        sys = new WeightedQuickUnionUF(n * n + 2);
        sysNoBot = new WeightedQuickUnionUF(n * n + 1);

        sysOpen = new boolean[n * n + 2];

        for (int i = 1; i < sysOpen.length; i++) {
            sysOpen[i] = false;
        }
        top = 0;
        bot = n * n + 1;

        sysOpen[top] = true;
        sysOpen[bot] = true;

        numOpenSites = 0;
        gridWidth = n;  //this.n=n;


    }

    public void open(int row, int col) {
        int p = (row - 1) * gridWidth + col;
        int pL = p - 1;
        int pR = p + 1;
        int pA = p - gridWidth;
        int pB = p + gridWidth;
        int skipL = 0;
        int skipR = 0;
        int skipBNoBot = 0;

        validate(row, col);

        if (!sysOpen[p]) {
            numOpenSites++;
            sysOpen[p] = true;

            // first row
            if (row == 1) {
                pA = top;
            }
            // last row
            if (row == gridWidth) {
                pB = bot;
                skipBNoBot = 1;
            }

            // left col
            if (col == 1) {
                skipL = 1;
            }

            // right col
            if (col == gridWidth) {
                skipR = 1;
            }

            if (skipL == 0 && (sysOpen[pL])) {
                sys.union(pL, p);
                sysNoBot.union(pL, p);
            }
            if (skipR == 0 && (sysOpen[pR])) {
                sys.union(p, pR);
                sysNoBot.union(p, pR);
            }
            if (sysOpen[pA]) {
                sys.union(p, pA);
                sysNoBot.union(p, pA);

            }
            if (sysOpen[pB]) {
                sys.union(p, pB);
                if (skipBNoBot == 0) {
                    sysNoBot.union(p, pB);
                }
            }
        }

    }

    private void validate(int row, int col) {

        if (row < 1 || row > gridWidth) {
            throw new IllegalArgumentException("row " + row + " is not between 1 and " + gridWidth);
        }
        if (col < 1 || col > gridWidth) {
            throw new IllegalArgumentException("col " + col + " is not between 1 and " + gridWidth);
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);

        int p = (row - 1) * gridWidth + col;
        return sysOpen[p];

    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        int p = (row - 1) * gridWidth + col;
        return sysNoBot.connected(top, p);
    }

    public int numberOfOpenSites() {
        return numOpenSites;

    }

    public boolean percolates() {
        return sys.connected(top, bot);
    }

    // public static void main(String[] args) {
    //
    // }
}
