import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation 
{
    private boolean[] grid;
    private final WeightedQuickUnionUF wqf;
    private final int size;
    private int numOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n <= 0)
        {
            throw new IllegalArgumentException("Grid Size must be greater than 0");
        }
        
        int lastElement = n*n+2;
        grid = new boolean[lastElement];
        wqf = new WeightedQuickUnionUF(lastElement); 
        numOpenSites = 0;
        size = n;

    }

    private boolean isInvalidCoordinates(int row, int col)
    {
        return (row > size || row < 1 || col > size || col < 1);
    }

    private int xyTo1D(int row, int col)
    {
        if (isInvalidCoordinates(row, col))
        {
            throw new IllegalArgumentException();
        }
        return size*(row-1)+col;
        
    }
    
    private void connect(int p, int q)
    {
        wqf.union(p, q);
    }
    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if (isInvalidCoordinates(row, col))
        {
            throw new IllegalArgumentException();   
        }
        
        int idx = xyTo1D(row, col);
        if (!grid[idx]) {
            grid[idx] = true;
            numOpenSites++;
        }
        if (row == 1) 
        {
            connect(0, idx);
        }

        if (row == size) 
        {
            connect(size*size+1, idx);
        }

        if (row > 1 && isOpen(row-1, col)) 
        {
            connect(xyTo1D(row-1, col), idx);
        }
        if (row < size && isOpen(row+1, col)) 
        {
            connect(xyTo1D(row+1, col), idx);
        }
        if (col > 1 && isOpen(row, col-1)) 
        {
            connect(xyTo1D(row, col-1), idx);
        }
        if (col < size && isOpen(row, col+1))
        { 
            connect(xyTo1D(row, col+1), idx);
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        
        if (isInvalidCoordinates(row, col))
        {
            throw new IllegalArgumentException();
            
        }
        int idx = xyTo1D(row, col);
        return grid[idx];
        
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        if (isInvalidCoordinates(row, col))
        {
            throw new IllegalArgumentException();
        }
        return wqf.find(xyTo1D(row, col)) == wqf.find(0) ? true : false;
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return wqf.find(size*size+1) == wqf.find(0) ? true : false;
    }
    public static void main(String[] args)
    {
        Percolation p = new Percolation(4);
        p.open(1,1);
        p.open(2,1);
        p.open(3,1);
        System.out.println(p.isFull(4, 1));

    }
    
}