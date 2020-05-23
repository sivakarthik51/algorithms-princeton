import java.util.ArrayList;
import java.util.Arrays;

public class Board 
{

    private final int n;
    private final int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
    {
        n = tiles.length;
        this.tiles = new int[n][n];
        for ( int i = 0 ; i < n ; i++ )
        {
            this.tiles[i] = Arrays.copyOf(tiles[i],n);
        }
    }
                                           
    // string representation of this board
    public String toString()
    {
        StringBuilder boardString = new StringBuilder();
        boardString.append(dimension());
        boardString.append("\n");
        for ( int i = 0 ; i < n ; i++ )
        {
            for ( int j = 0 ; j < n ; j++ )
            {
                boardString.append(" ");
                boardString.append(tiles[i][j]);
                boardString.append(" ");
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }

    // board dimension n
    public int dimension()
    {
        return n;
    }

    // number of tiles out of place
    public int hamming()
    {
        int hamDistance = 0;
        for ( int i = 0 ; i < n ; i++ )
        {
            for ( int j = 0 ; j < n ; j++ )
            {
                if ( tiles[i][j] != ( i * n + j + 1 ) && tiles[i][j] != 0 )
                {
                    hamDistance++;
                }
            }
        }
        return hamDistance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan()
    {
        int manDistance = 0;
        for ( int i = 0 ; i < n ; i++ )
        {
            for ( int j = 0 ; j < n ; j++ )
            {
                if ( tiles[i][j] != 0 )
                {
                    manDistance += ( Math.abs( ( ( tiles[i][j] - 1 ) / n )  - i ) ) +  ( Math.abs( ( ( tiles[i][j] - 1 ) % n )  - j ) );
                }
            }
        }
        return manDistance;

    }

    // is this board the goal board?
    public boolean isGoal()
    {
        for ( int i = 0 ; i < n ; i++ )
        {
            for ( int j = 0 ; j < n ; j++ )
            {
                if ( tiles[i][j] != ( i * n + j + 1 ) && tiles[i][j] != 0 )
                {
                    return false;
                }
            }
        }
        return true;

    }

    // does this board equal y?
    public boolean equals(Object y)
    {
        if ( y == this )
            return true;
        if ( y == null || y.getClass() != getClass() || y.dimension() != dimension())
        {
            return false;
        }
        for ( int i = 0 ; i < n ; i++ )
        {
            for ( int j = 0 ; j < n ; j++ )
            {
                if ( tiles[i][j] != ((Board) y).tiles[i][j] )
                    return false;
            }
        }
        return true;
        
    }

    // all neighboring boards
    public Iterable<Board> neighbors()
    {
        int bRow = 0;
        int bCol = 0;
        for ( int i = 0 ;i < n ; i++ )
        {
            for ( int j = 0 ; j < n ; j++ )
            {
                if ( tiles[i][j] == 0)
                {
                    bRow = i;
                    bCol = j;
                    break;
                }
            }
        }
        ArrayList<Board> neighbourBoards = new ArrayList<>();

        if ( bRow > 0 ) {
            Board nBoard = new Board(tiles);
            nBoard.exchangeTiles(bRow, bCol , bRow - 1, bCol );
            neighbourBoards.add(nBoard);
        }

        if ( bRow < n - 1 ) {
            Board nBoard = new Board(tiles);
            nBoard.exchangeTiles(bRow, bCol , bRow + 1, bCol );
            neighbourBoards.add(nBoard);
        }

        if ( bCol > 0 ) {
            Board nBoard = new Board(tiles);
            nBoard.exchangeTiles(bRow, bCol , bRow , bCol - 1  );
            neighbourBoards.add(nBoard);
        }

        if ( bCol < n - 1 ) {
            Board nBoard = new Board(tiles);
            nBoard.exchangeTiles(bRow, bCol , bRow , bCol + 1 );
            neighbourBoards.add(nBoard);
        }

        return neighbourBoards;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin()
    {
        int[][] twinBoard = new int[n][n];

        for ( int i = 0 ; i < n ; i++ )
        {
            for ( int j = 0 ; j < n ; j++ )
            {
                twinBoard[i][j] = tiles[i][j];
            }
        }

        if (twinBoard[0][0] != 0 && twinBoard[0][1] != 0)
        {
            twinBoard[0][0] = tiles[0][1];
            twinBoard[0][1] = tiles[0][0];
        }
        else {
            twinBoard[1][0] = tiles[1][1]
            twinBoard[1][1] = tiles[1][0];
        }

        return new Board(twinBoard);

    }

    private void exchangeTiles(int sourcei , int sourcej , int targeti , int targetj )
    {
        if ( sourcei < 0 || sourcei >= n || sourcej < 0 || sourcej >= n || targeti < 0 || targeti >= n || targetj < 0 || targetj >= n  )
            throw new IllegalArgumentException();

        int temp = tiles[sourcei][sourcej];
        tiles[sourcei][sourcej] = tiles[targeti][targetj];
        tiles[targeti][targetj] = temp;
    }

    // unit testing (not graded)
    public static void main(String[] args)
    {
        
    }

}