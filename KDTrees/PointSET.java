import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    private TreeSet<Point2D> points;
    public PointSET()
    {
        points = new TreeSet<Point2D>();
    }
    public boolean isEmpty()
    {
        return points.isEmpty();
    }
    public int size()
    {
        return points.size();
    }
    public void insert(Point2D p)
    {
        if ( p == null )
        {
            throw new IllegalArgumentException();
        }
        if ( !points.contains(p) )
        {
            points.add(p);
        }


    }
    public boolean contains(Point2D p)
    {
        if ( p == null )
        {
            throw new IllegalArgumentException();
        }
        return points.contains(p);
    }

    public void draw()
    {
        for ( Point2D p : points )
        {
            p.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect)
    {
        if ( rect == null )
        {
            throw new IllegalArgumentException();
        }
        TreeSet<Point2D> pRect = new TreeSet<Point2D>();
        for (Point2D p : points.tailSet(new Point2D(rect.xmin(),rect.ymin())))
        {
            if ( p.x() <= rect.xmax() && p.y() <= rect.ymax() )
            {
                pRect.add(p);
            }
        }
        return pRect;
    }
    public Point2D nearest(Point2D p)
    {
        if ( p == null )
        {
            throw new IllegalArgumentException();
        }
        if ( isEmpty() )
        {
            return null;
        }
        Point2D nearestPoint = points.first();
        double nearestValue = nearestPoint.distanceSquaredTo(p);
        double checkVal;
        for ( Point2D point : points )
        {
            checkVal = point.distanceSquaredTo(p);
            if ( checkVal < nearestValue )
            {
                nearestPoint = point;
                nearestValue = checkVal;
            }
        }
        return nearestPoint;
    }
 
    
 }