/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Solver {
    private final MinPQ<SearchNode> minPQ;
    private final MinPQ<SearchNode> twinMinPQ;
    private int move = -1;
    private SearchNode lastMove = null;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("Initial board cannot be null.");
        minPQ = new MinPQ<SearchNode>();
        twinMinPQ = new MinPQ<SearchNode>();
        SearchNode t = new SearchNode(null, initial, 0);
        SearchNode p = new SearchNode(null, initial.twin(), 0);

        minPQ.insert(t);
        twinMinPQ.insert(p);
        startSearch();
    }


    static private class SearchNode implements Comparable<SearchNode> {
        Board board;
        int moves;
        SearchNode previous;
        int priority;


        SearchNode(SearchNode previous, Board board, int moves) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;

            priority = moves + board.manhattan();
        }

        public int compareTo(SearchNode that) {
            return priority - that.priority;
        }
    }

    private void startSearch() {
        boolean solved = false;
        boolean twinSolved = false;
        while (!solved && !twinSolved) {

            SearchNode current = minPQ.delMin();
            Board board = current.board;
            if (board.isGoal()) {
                solved = true;
                lastMove = current;
                move = current.moves;
                break;
            }

            for (Board neighbor : board.neighbors()) {
                if (current.previous != null && neighbor.equals(current.previous.board)) {
                    continue;
                }

                minPQ.insert(new SearchNode(current, neighbor, current.moves + 1));

            }

            SearchNode currentTwin = twinMinPQ.delMin();
            Board twin = currentTwin.board;
            if (twin.isGoal()) {
                twinSolved = true;
                break;
            }

            for (Board neighbor : twin.neighbors()) {
                if (currentTwin.previous != null && neighbor.equals(currentTwin.previous.board)) {
                    continue;
                }

                twinMinPQ.insert(new SearchNode(currentTwin, neighbor, currentTwin.moves + 1));
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return moves() > -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return move;
    }

    // sequence of boards in a shortest solution; null if unsolvable

    public Iterable<Board> solution() {
        if (lastMove == null) return null;
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return new SolverInterator(lastMove);
            }
        };
    }


    static private class SolverInterator implements Iterator<Board> {
        SearchNode node;
        Stack<Board> borads;

        SolverInterator(SearchNode node) {
            this.node = node;
            borads = new Stack<Board>();
            while (node != null) {
                borads.push(node.board);
                node = node.previous;
            }
        }

        @Override
        public boolean hasNext() {
            return !borads.isEmpty();
        }

        @Override
        public Board next() {
            if (hasNext()) {
                return borads.pop();
            }
            else {
                throw new NoSuchElementException();
            }
        }

    }

    // test client (see below)
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
