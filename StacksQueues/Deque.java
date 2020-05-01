import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    //Creating a Node private Class for Linked List implementation.
    private class Node
    {
        Item value;
        Node next;
        Node previous;
    }

    //First Node in the Deque
    private Node first;

    //Last Node in the Deque
    private Node last;

    //Size of the Deque
    private int sz = 0;

    // construct an empty deque
    public Deque()
    {
        first = null;
        sz = 0;
    }

    // is the deque empty?
    public boolean isEmpty()
    {
        return first == null;
    }

    // return the number of items on the deque
    public int size()
    {
        return sz;
    }

    // add the item to the front
    public void addFirst(Item item)
    {
        if ( item == null )
        {
            throw new IllegalArgumentException();
        }
        
        Node n = new Node();
        n.value = item;
        n.next = first;
        n.previous = null;
        if ( sz > 0 )
        {
            first.previous = n;   
        }
        first = n;
        if(sz == 0)
        {
            last = first;
        }
        sz++;
    }

    // add the item to the back
    public void addLast(Item item)
    {
        if ( item == null )
        {
            throw new IllegalArgumentException();
        }
        Node n = new Node();
        n.value = item;
        n.next = null;
        if(sz>0)
        {
            last.next = n;
        }
        else if ( sz == 0 )
        {
            first = last;
        }
        last = n;
        sz++;
    }

    // remove and return the item from the front
    public Item removeFirst()
    {
        if(sz == 0)
        {
            throw new NoSuchElementException();
        }
        Item res = first.value;
        Node n = first.next;
        first = null;
        first = n;
        if(sz == 1)
        {
            last = null;
        }
        sz--;
        return res;


    }

    // remove and return the item from the back
    public Item removeLast()
    {
        if(sz == 0)
        {
            throw new NoSuchElementException();
        }
        Item res = last.value;
        Node n = last.previous;
        last = null;
        last = n;
        if(sz == 1)
        {
            first = null;
        }
        sz--;
        return res;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>
    {
        private Node current = first;
        public boolean hasNext()
        {
            return current != null;
        }
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
        public Item next()
        {
            if(!hasNext())
            {
                throw new NoSuchElementException();
            }
            Item item = current.value;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        Deque<Integer> d = new Deque<>();
        d.addFirst(10);
        d.addFirst(20);
        d.addLast(30);
        int first = d.removeFirst();
        first = d.removeFirst();
        first = d.removeFirst();
        //int last = d.removeLast();

        System.out.println("First:"+first);
        //System.out.println("Last:"+last);

        for(Integer i : d){
            System.out.println(i);
        }        
        System.out.println(d.size());
        d.addFirst(20);
        d.removeLast();
        d.removeFirst();

        System.out.println(d.isEmpty());

    }

}