import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
    private Node root;
    private static final boolean H = true;
    private static final boolean V = false;
    private Point2D nearestPoint;
    private double minDistance;

    private class Node 
    {
        private Node left,right;
        private boolean orientation;
        private int size;
        private Point2D point;

        public Node(Point2D pt, boolean orient)
        {
            point = pt;
            orientation = orient;
            size = 1;
            left = null;
            right = null;
        }
    }
    public KdTree()
    {
        root = null;
    }
    public boolean isEmpty()
    {
        return root == null;
    }
    public int size()
    {
        if ( isEmpty() ) return 0;
        return root.size;
    }
    private int size(Node n)
    {
        if ( n == null )
        {
            return 0;
        }
        return n.size;
    }
    public void insert(Point2D p)
    {
        if ( p == null )
        {
            throw new IllegalArgumentException();
        }
        root = insert(root,p,V);
    }
    private Node insert(Node n,Point2D p,boolean orientation)
    {
        if ( n == null )
        {
            return new Node(p,orientation);
        }
        if ( p.compareTo(n.point) == 0)
        {
            return n;
        }
        double checkVal;
        if ( orientation == V)
        {
            checkVal = p.x() - n.point.x();
        }
        else
        {
            checkVal = p.y() - n.point.y();
        }

        if ( checkVal < 0 )
        {
            n.left = insert(n.left,p,!orientation);
        }
        else
        {
            n.right = insert(n.right,p,!orientation);
        }
        n.size = 1 + size(n.left) + size(n.right);
        return n;

    }
    public boolean contains(Point2D p)
    {
        if ( p == null )
        {
            throw new IllegalArgumentException();
        }
        
        return contains(root,p);
    }
    private boolean contains(Node n, Point2D p)
    {
        if ( n == null ) return false;

        if ( p.compareTo(n.point) == 0 ) return true;

        double checkVal;

        if ( n.orientation == V )
        {
            checkVal = p.x() - n.point.x();
        }
        else
        {
            checkVal = p.y() - n.point.y();
        }

        if ( checkVal < 0 )
        {
            return contains(n.left,p);
        }
        else
        {
            return contains(n.right,p);
        }
    }
    public void draw()
    {
        draw(root);
    }
    private void draw(Node n) 
    {
        if ( n == null ) return;

        StdDraw.setPenColor(StdDraw.BLACK);
        n.point.draw();
        if ( n.orientation == V )
        {
            StdDraw.setPenColor(StdDraw.BOOK_BLUE);
            StdDraw.line(n.point.x(),0,n.point.x(),1);
        }
        else
        {
            StdDraw.setPenColor(StdDraw.BOOK_RED);
            StdDraw.line(0,n.point.y(),1,n.point.y());
        }
    }
    public Iterable<Point2D> range(RectHV rect)
    {
        if ( rect == null )
        {
            throw new IllegalArgumentException();
        }
        ArrayList<Point2D> points = new ArrayList<>();
        range(root,rect,points);
        return points;
    }
    private void range(Node n, RectHV rect,ArrayList<Point2D> points)
    {
        if ( n == null ) return;
        if ( rect.contains(n.point) )
        {
            points.add(n.point);
        }
        if ( n.orientation == V )
        {
            if (  n.point.x() > rect.xmax()  )
            {
                range(n.left,rect,points);
            }
            else if (  n.point.x() <= rect.xmin() )
            {
                range(n.right,rect,points);
            }
            else
            {
                range(n.left,rect,points);
                range(n.right,rect,points);
            }
        }
        else
        {
            if( n.point.y() > rect.ymax() )
            {
                range(n.left,rect,points);
            }
            else if ( n.point.y() <= rect.ymin() )
            {
                range(n.right,rect,points);
            }
            else
            {
                range(n.left,rect,points);
                range(n.right,rect,points);
            }
        }
    }
    public Point2D nearest(Point2D p)
    {
        if ( p == null )
        {
            throw new IllegalArgumentException();
        }
        nearestPoint = null;
        minDistance = Double.POSITIVE_INFINITY;
        nearest(root,p);
        return nearestPoint;
    }
    private void nearest(Node n, Point2D p)
    {
        if ( n == null ) return;

        double distance = p.distanceTo(n.point);

        if ( distance < minDistance )
        {
            minDistance = distance;
            nearestPoint = n.point;
        }

        if ( n.orientation == V )
        {
            if ( p.x() < n.point.x() )
            {
                nearest(n.left,p);
           
                if ( n.point.x() - p.x() <= minDistance )
                {
                    nearest(n.right,p);
                }
            }
            else 
            {
                nearest(n.right,p);
                if ( p.x() - n.point.x() <= minDistance )
                {
                    nearest(n.left,p);
                }
            }
        }
        else
        {
            if ( p.y() < n.point.y() )
            {
                nearest(n.left,p);
                if ( n.point.y() - p.y() <= minDistance )
                {
                    nearest(n.right,p);
                }
            }
            else
            {
                nearest(n.right,p);
                if ( p.y() - n.point.y() <= minDistance )
                {
                    nearest(n.left,p);
                } 
            }
        }

    }
 }