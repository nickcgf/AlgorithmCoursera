
import edu.princeton.cs.algs4.MinPQ;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Solver {


    private final MinPQ<Tuple> minPQ;
    private int move = -1;
    private Tuple lastMove = null;
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("Initial board cannot be null.");
        minPQ = new MinPQ<Tuple>(new Comparator<Tuple>() {
            public int compare(Tuple o1, Tuple o2) {
                if ( o1.board.hamming() != o2.board.hamming()) return o1.board.hamming() - o2.board.hamming();
                if ( o1.board.manhattan() != o2.board.manhattan()) return o1.board.manhattan() - o2.board.manhattan();
                return o1.moves - o2.moves;
            }
        });
        Tuple t = new Tuple();
        t.board = initial;
        t.moves = 0;
        minPQ.insert(t);
        startSearch();
    }

    private class Tuple {
        Board board;
        int moves;
        ArrayList<Board> boardArray;
        Tuple() {
            boardArray = new ArrayList<Board>();
        }
    }

    private void startSearch() {
        while (!minPQ.isEmpty()) {
            Tuple tuple = minPQ.delMin();
            Board current = tuple.board;
            if (current.isGoal()) {
                lastMove = tuple;
                tuple.boardArray.addLast(current);
                move = tuple.moves;
                return;
            }
            Iterator<Board> iterator = current.neighbors().iterator();
            while (iterator.hasNext()) {
                Board next = iterator.next();
                if (next.hamming() < current.hamming() || next.manhattan() < current.manhattan()) {
                    Tuple nextTuple = new Tuple();
                    nextTuple.board = next;
                    nextTuple.moves = tuple.moves + 1;
                    if (tuple.boardArray != null) {
                        nextTuple.boardArray = new ArrayList<Board>(tuple.boardArray);
                    }else {
                        nextTuple.boardArray = new ArrayList<Board>();
                    }

                    nextTuple.boardArray.addLast(current);
                    minPQ.insert(nextTuple);
                }
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

        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                if (lastMove == null) {
                    return null;
                }
                return new SolverInterator();
            }
        };
    }


    private class SolverInterator implements Iterator<Board> {
        int index;

        @Override
        public boolean hasNext() {
            return index < lastMove.boardArray.size();
        }

        @Override
        public Board next() {
            if (hasNext()) {
                return lastMove.boardArray.get(index++);
            }else {
                throw new NoSuchElementException();
            }
        }

    }

    // test client (see below)
    public static void main(String[] args) {
        Board initial = new Board(new int[][] {{0,1,3},{4,2,5},{7,8,6}});
        Solver solver = new Solver(initial);
        System.out.println(solver.isSolvable());
        if (solver.isSolvable()) {
            Iterator<Board> it = solver.solution().iterator();
            while (it.hasNext()) {
                Board board = it.next();
                System.out.println(board);
            }
        }
    }

}