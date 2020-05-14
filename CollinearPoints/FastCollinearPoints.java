import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints 
{

    private ArrayList<LineSegment> segmentsList = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points)
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
        Point[] pointsCopy = Arrays.copyOf(points,nPoints);

        for ( int i = 0 ; i < nPoints ; i++ )
        {
            Point p = points[i];
            Arrays.sort(pointsCopy);
            Arrays.sort(pointsCopy,p.slopeOrder());

            int min = 0;

            while ( min < nPoints && p.slopeTo(pointsCopy[min]) == Double.NEGATIVE_INFINITY) min++;

            if ( min != 1 ) throw new IllegalArgumentException();

            int max = min;

            while ( min < nPoints )
            {
                while ( max < nPoints && p.slopeTo(pointsCopy[max]) == p.slopeTo(pointsCopy[min])) max++;

                if ( max - min >= 3)
                {
                    Point pMin = pointsCopy[min].compareTo(p) < 0 ? pointsCopy[min] : p;
                    Point pMax = pointsCopy[max-1].compareTo(p) > 0 ? pointsCopy[max-1] : p;
                    if ( p == pMin )
                    {
                        segmentsList.add(new LineSegment(pMin, pMax));
                    }
                }
                min = max;

            }

        }
    }
    // the number of line segments
    public int numberOfSegments()
    {
        return segmentsList.size();
    }
    // the line segments
    public LineSegment[] segments()
    {
        LineSegment[] segmentsArray = new LineSegment[segmentsList.size()];
        return segmentsList.toArray(segmentsArray);
    }
 }