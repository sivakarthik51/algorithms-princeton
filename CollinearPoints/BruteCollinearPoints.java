import java.util.ArrayList;
import java.util.Arrays; 

public class BruteCollinearPoints {

    private final LineSegment[] lineSegments;

    private final ArrayList<LineSegment> lineSegmentsList;

    // Finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points)
    {
        if(points == null)
        {
            throw new IllegalArgumentException();
        }
        int nPoints = points.length;
        for( int i = 0 ; i < nPoints ; i++ )
        {
            if(points[i] == null)
            {
                throw new IllegalArgumentException();
            }
        }
        for ( int i = 0 ; i < nPoints ; i++)
        {
            for ( int j =  i + 1 ; j < nPoints ; j++)
            {
                if ( points[i].compareTo(points[j]) == 0 )
                {
                    throw new IllegalArgumentException();
                }
            }
        }
        lineSegmentsList = new ArrayList<>();
        Point[] pointsCopy = Arrays.copyOf(points, nPoints);
        Arrays.sort(pointsCopy);

        
        for( int i = 0 ; i < nPoints - 3 ; i++ )
        {
            
            for( int j = i + 1 ; j < nPoints - 2 ; j++ )
            {
                
                for ( int k = j + 1 ; k < nPoints - 1 ; k++ )
                {
                    
                    for ( int l = k + 1 ; l < nPoints ; l++ )
                    {
                       
                        if ( ( pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i].slopeTo(pointsCopy[k]) )  && ( pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i].slopeTo(pointsCopy[l]) ) )
                        {
                            LineSegment lineSegment = new LineSegment(pointsCopy[i],pointsCopy[l]);
                            lineSegmentsList.add(lineSegment);
                            
                        }

                        
                    }
                }
            }
        }
        lineSegments = lineSegmentsList.toArray(new LineSegment[lineSegmentsList.size()]);

    }
     // the number of line segments
    public int numberOfSegments()
    {
        return lineSegments.length;
    }
    // the line segments
    public LineSegment[] segments()
    {
        return lineSegments;
    }

 }