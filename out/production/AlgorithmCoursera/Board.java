/* *****************************************************************************
 *  name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Iterator;

public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private int n;
    private int hamming = -1;
    private int manhattan = -1;
    private int[] tiles;
    private ArrayList<Board> neighbours;

    public Board(int[][] tiles) {
        if (tiles == null || tiles.length == 0) return;
        n = tiles.length;
        this.tiles = new int[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i * n + j] = tiles[i][j];
            }
        }
    }

    private Board(int[] tiles, int n) {
        this.n = n;
        this.tiles = new int[n * n];
        for (int i = 0; i < tiles.length; i++) {
            this.tiles[i] = tiles[i];
        }
    }

    // string representation of this board
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toString(n));
        for (int i = 0; i < n * n; i++) {
            if (i % n == 0) sb.append("\n");
            sb.append(" " + Integer.toString(tiles[i]));
        }

        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        if (hamming != -1) return hamming;
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i * n + j] == 0) continue;
                if (manhattan(i, j) != 0) res++;
            }
        }
        hamming = res;
        return res;
    }

    private int manhattan(int i, int j) {
        int num = tiles[i * n + j];
        int row = (num - 1) / dimension();
        int col = (num - 1) % dimension();
        return Math.abs(row - i) + Math.abs(col - j);
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhattan != -1) return manhattan;
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i * n + j] == 0) continue;
                res += manhattan(i, j);
            }
        }
        manhattan = res;
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
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i * n + j] != that.tiles[i * n + j]) return false;
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
                    generateneighbours();
                }
                return neighbours.iterator();
            }
        };
    }

    private void generateneighbours() {
        var allneighbours = new ArrayList<Board>();

        int i = 0, j = 0;
        while (tiles[i * n + j] != 0) {
            j++;
            if (j == n) {
                i += 1;
                j = 0;
            }
        }

        int[][] diff = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        for (int[] d : diff) {
            int nx = i + d[0];
            int ny = j + d[1];
            if (nx >= 0 && nx < n && ny >= 0 && ny < n) {
                var neighbourTiles = copyTiles();
                exchange(neighbourTiles, i, j, nx, ny);
                allneighbours.add(new Board(neighbourTiles, n));
            }
        }

        neighbours = allneighbours;
    }

    private int[] copyTiles() {
        int[] twinTiles = new int[n * n];
        for (int i = 0; i < n * n; i++) {
            twinTiles[i] = tiles[i];
        }
        return twinTiles;
    }

    private void exchange(int[] data, int i, int j, int p, int q) {
        var temp = data[i * n + j];
        data[i * n + j] = data[p * n + q];
        data[p * n + q] = temp;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[] twinTiles = copyTiles();
        int i = 0, j = 0;
        while (tiles[i * n + j] == 0 || tiles[i * n + j + 1] == 0) {
            j++;
            if (j >= n - 1) {
                j = 0;
                i++;
            }
        }
        exchange(twinTiles, i, j, i, j + 1);
        return new Board(twinTiles, n);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        var board = new Board(new int[][] { { 0, 2 }, { 3, 1 } });
        var board2 = new Board(new int[][] { { 1, 0 }, { 3, 2 } });
        System.out.println(board.manhattan() + " " + board.hamming());
        System.out.println(board2.equals(board));
    }

}


