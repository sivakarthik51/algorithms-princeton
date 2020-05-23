import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

public class Solver {

    private MinPQ<Node> p1;
    private MinPQ<Node> p2;
    private final boolean solvable;
    private final int moves;
    private ArrayList<Board> solutions;


    private class Node implements Comparable<Node> {
        private Board board;
        private Node previous;
        private int moves;

        public Node(Board board, Node previous, int moves) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
        }
        public int compareTo(Node that)
        {
            return (this.board.manhattan() + this.moves) - (that.board.manhattan() + that.moves);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)
    {
        p1 = new MinPQ<>();
        p2 = new MinPQ<>();

        p1.insert( new Node(initial , null , 0 ) );
        p2.insert( new Node(initial.twin() , null, 0 ) );

        while ( true ) {
            Node t = p1.delMin();
            if ( t.board.isGoal() )
            {
                solvable = true;
                moves = t.moves;
                solutions = new ArrayList<>();
                while ( t != null )
                {
                    solutions.add(0,t.board);
                    t = t.previous;
                }
                break;
            }
            for ( Board b : t.board.neighbors() )
            {
                if ( t.previous == null || !b.equals(t.previous.board) )
                {
                    p1.insert(new Node(b,t,t.moves+1));
                }
            }
            t = p2.delMin();
            if ( t.board.isGoal() )
            {
                solvable = false;
                moves = -1;
                solutions = null;
                break;
            }
            for (Board b: t.board.neighbors())
            {
                if (t.previous == null || !b.equals(t.previous.board))
                {
                    p2.insert(new Node(b, t, t.moves + 1));
                }
            }

        }
    }   

    // is the initial board solvable? (see below)
    public boolean isSolvable()
    {
        return solvable;
    }

    // min number of moves to solve initial board
    public int moves()
    {
        return moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution()
    {
        return solutions;
    }

    // test client (see below) 
    public static void main(String[] args)
    {
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