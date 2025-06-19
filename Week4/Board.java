import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private int N;
    private int[][] tiles;
    private Board[] neighbours;
    public Board(int[][] tiles) {
        if(tiles == null || tiles.length == 0) return;
        N = tiles.length;
        this.tiles = tiles;
    }

    // string representation of this board
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toString(N) + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(" "+Integer.toString(tiles[i][j]));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return N;
    }

    // number of tiles out of place
    public int hamming() {
        int res = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) continue;
                if (manhattan(i, j) != 0) res ++;
            }
        }
        return res;
    }

    private int manhattan(int i, int j) {
        int num = tiles[i][j];
        int row = (num - 1) / dimension();
        int col = (num - 1) % dimension();
        return Math.abs(row - i) + Math.abs(col - j);
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int res = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) continue;
                res += manhattan(i, j);
            }
        }
        return res;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        var that = (Board) y;
        if (that.dimension() != this.dimension()) return false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != that.tiles[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                if (neighbours == null) {
                    generateNeighbours();
                }
                return new NeighborInterator();
            }
        };
    }

    private void generateNeighbours() {
        var allNeighbours = new ArrayList<Board>();

        int i = 0, j = 0;
        while (tiles[i][j] != 0) {
            j ++;
            if (j == N) {
                i += 1;
                j = 0;
            }
        }

        int[][] diff = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int[] d : diff) {
            int nx = i + d[0];
            int ny = j + d[1];
            if (nx >= 0 && nx < N && ny >= 0 && ny < N) {
                var neighbourTiles = copyTiles();
                exchange(neighbourTiles, i, j, nx, ny);
                allNeighbours.add(new Board(neighbourTiles));
            }
        }

        neighbours = allNeighbours.toArray(new Board[allNeighbours.size()]);
    }

    private int[][] copyTiles() {
        int[][] twinTiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                twinTiles[i][j] = tiles[i][j];
            }
        }
        return twinTiles;
    }

    private void exchange(int[][] data, int i, int j, int p, int q) {
        var temp = data[i][j];
        data[i][j] = data[p][q];
        data[p][q] = temp;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinTiles = copyTiles();
        int i = 0, j = 0;
        while (tiles[i][j] == 0 || tiles[i][j+1] == 0) {
            j ++;
            if (j >= N - 1) {
                j = 0;
                i ++;
            }
        }
        exchange(twinTiles, i, j, i, j+1);
        return new Board(twinTiles);
    }

    private class NeighborInterator implements Iterator<Board> {
        int index;

        @Override
        public boolean hasNext() {
            return index < neighbours.length;
        }

        @Override
        public Board next() {
            if (hasNext()) {
                return neighbours[index++];
            }else {
                throw new NoSuchElementException();
            }
        }

    }

    // unit testing (not graded)
    public static void main(String[] args) {
        var board = new Board(new int[][] {{0,2},{3,1}});
        System.out.println(board.manhattan()+" "+board.hamming());
    }

}




