import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats 
{
    private final double[] probabilities;
    private final double CONFIDENCE_95;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if (n <= 0 || trials <= 0)
        {
            throw new IllegalArgumentException("Negative/Zero Values not accepted");
        }
        probabilities = new double[trials];
        CONFIDENCE_95 = 1.96;
        for(int i = 0; i < trials; i++)
        {
            Percolation p = new Percolation(n);
            int numSites = n*n;
            while (!p.percolates())
            {
                int row = StdRandom.uniform(n)+1;
                int col = StdRandom.uniform(n)+1;
                if (!p.isOpen(row, col))
                {
                    p.open(row, col);
                }
            }
            probabilities[i] = (double) p.numberOfOpenSites()/numSites;
        }

    }

    // sample mean of percolation threshold
    public double mean()
    {
        
        return StdStats.mean(probabilities);
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        if (probabilities.length <= 1) return Double.NaN;
        return StdStats.stddev(probabilities);

    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return mean()-((CONFIDENCE_95*stddev())/Math.sqrt(probabilities.length));

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return mean()+((CONFIDENCE_95*stddev())/Math.sqrt(probabilities.length));
    }

   // test client (see below)
   public static void main(String[] args)
   {
        if (args.length == 2)
        {
            int n = Integer.parseInt(args[0]);
            int T = Integer.parseInt(args[1]);
            PercolationStats ps = new PercolationStats(n, T);
            System.out.println("mean =\t"+ps.mean());
            System.out.println("stddev =\t"+ps.stddev());
            System.out.println("95% confidence interval =\t["+ps.confidenceLo()+","+ps.confidenceHi()+"]");
        }
        else
        {
            System.out.println("Usage:java-algs4 PercolationStats <grid_size> <number_of_trials>");
        }
   }

}