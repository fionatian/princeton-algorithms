/* *****************************************************************************
 *  Name:Fiona Tian
 *  Date:2019.7.29
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class Solver {

    private static final int TIMEOUT_TRIALS = 9999999;
    private int numMoves;
    private SearchNode last;
    private boolean solved;


    // SearchNode is implements Comparable Interface. PQ will use its compreTo() to decide position
    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final SearchNode previous;
        private final int priority;


        public SearchNode(Board current, int moves, SearchNode previous) {
            this.board = current;
            this.moves = moves;
            this.previous = previous;
            this.priority = moves + current.manhattan();
        }

        public Board getBoard() {
            return board;
        }

        public SearchNode getPrevious() {
            return previous;
        }

        public int getMoves() {
            return moves;
        }

        public int compareTo(SearchNode that) {
            // if (this.priority > that.priority) return 1;
            // else if (this.priority < that.priority) return -1;
            // else if (board.hamming() + moves < that.getMoves() + that.getBoard().hamming())
            //     return -1;
            // else if (board.hamming() + moves > that.getMoves() + that.getBoard().hamming())
            //     return 1;
            // else return 0;
            //return Integer.compare(moves + board.manhattan(), that.moves + that.getBoard().manhattan());
            return Integer.compare(this.priority, that.priority);
        }

    }

    public Solver(Board initial) {
        if (initial == null) throw new java.lang.IllegalArgumentException("null given to Solver()");

        solved = false;
        MinPQ<SearchNode> pqOrg = new MinPQ<SearchNode>();
        MinPQ<SearchNode> pqTwin = new MinPQ<SearchNode>();
        numMoves = -1;
        last = null;
        int count = 0;

        pqOrg.insert(new SearchNode(initial, 0, null));
        pqTwin.insert(new SearchNode(initial.twin(), 0, null));
        while (count++ < TIMEOUT_TRIALS) {
/*            if (count % 10000 == 0) {
                System.out
                        .println("count:" + count + " size: " + pqOrg.size() + " " + pqTwin.size());
            }*/
            // run the A* algorithm on two puzzle instances—one with the initial board and one with the initial board modified by swapping a pair of tiles—in lockstep
            if (!pqOrg.isEmpty()) {
                MinPQ<SearchNode> pq = pqOrg;
                SearchNode current = pq.delMin();
                int priority = current.getBoard().manhattan() + current.getMoves();
     /*           System.out.println("###########\n" +
                                           "count: " + count + " delMin: " + current.getBoard()
                                           + "its moves is "
                                           + current.getMoves()
                                           + "\nits manhattan is " + current.getBoard().manhattan()
                                           + "\nsum is "
                                           + priority);*/

                if (current.getBoard().isGoal()) {
                    numMoves = current.getMoves();
                    last = current;
                    solved = true;
                    break;
                }

                else {
                    int neiMoves = current.getMoves() + 1;
                    for (Board nei : current.getBoard().neighbors()) {
                        if ((neiMoves > 1) && (nei.equals(current.getPrevious().getBoard()))) {
                            continue;
                        }
                        pq.insert(new SearchNode(nei, neiMoves, current));
                        // System.out.println("adding neighbor: " + nei + "its moves is " + neiMoves);

                    }
                }
            }

            if (!pqTwin.isEmpty()) {
                MinPQ<SearchNode> pq = pqTwin;
                SearchNode current = pq.delMin();
                // System.out.println("Twin delMin: " + current.getBoard() + "its moves is " + current.getMoves());
                if (current.getBoard().isGoal()) {
  /*                  System.out
                            .println("Twin solved!" + current.getBoard() + "its moves is " + current
                                    .getMoves());*/
                    break;
                }

                else {
                    int neiMoves = current.getMoves() + 1;
                    for (Board nei : current.getBoard().neighbors()) {
                        if ((neiMoves > 1) && (nei.equals(current.getPrevious().getBoard()))) {
                            continue;
                        }
                        pq.insert(new SearchNode(nei, neiMoves, current));

                    }
                }
            }


        }
        //        System.out.println("count:" + count);

    }


    // min number of moves to solve initial board
    public int moves() {
        return numMoves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (!solved) return null;
        LinkedList<Board> solution = new LinkedList<Board>();
        SearchNode cur = last;
        while (cur != null) {
            solution.addFirst(cur.getBoard());
            cur = cur.getPrevious();
        }

        return solution;

    }

    public boolean isSolvable() {
        return solved;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }
}
