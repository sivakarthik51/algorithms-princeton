import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int sz;
    private Item[] rq;

    // construct an empty randomized queue
    public RandomizedQueue()
    {
        sz = 0;
        rq = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty()
    {
        return sz == 0;
    }


    // return the number of items on the randomized queue
    public int size()
    {
        return sz;
    }
    // add the item
    public void enqueue(Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException();
        }
        rq[sz++] = item;
        if(sz == rq.length)
        {
            Item[] nq = (Item[]) new Object[sz*2];
            for(int i = 0;i<rq.length;i++)
            {
                nq[i] = rq[i];
            }
            rq = nq;
        }
    }

    // remove and return a random item
    public Item dequeue()
    {
        if ( sz == 0 )
        {
            throw new NoSuchElementException();
        }
        int randNumber = StdRandom.uniform(sz);
        Item res = rq[randNumber];
        rq[randNumber] = rq[sz-1];
        rq[sz-1] = null;
        sz--;
        if(rq.length>4 &&  sz <= (rq.length/4))
        {
            Item[] nq = (Item[]) new Object[rq.length/2];
            for(int i = 0;i<sz;i++)
            {
                nq[i] = rq[i];
            }
            rq = nq;
        }
        return res;

    }

    // return a random item (but do not remove it)
    public Item sample()
    {
        if(sz == 0)
        {
            throw new NoSuchElementException();
        }
        int randNumber = StdRandom.uniform(sz);
        return rq[randNumber];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {
        return new RandomizedQueueIterator();
    }
    private class RandomizedQueueIterator implements Iterator<Item>
    {
        private int current = sz;
        final private int[] order;

        public RandomizedQueueIterator()
        {
            order = new int[sz];
            for(int i = 0;i<sz;i++)
            {
                order[i] = i;
            }
            StdRandom.shuffle(order);
        }

        public boolean hasNext()
        {
            return current>0;
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
            return rq[order[--current]];
        }
    }
    // unit testing (required)
    public static void main(String[] args)
    {
        RandomizedQueue<Integer> rQueue = new RandomizedQueue<>();
        rQueue.enqueue(10);
        rQueue.enqueue(30);
        rQueue.enqueue(100);
        rQueue.dequeue();
        rQueue.enqueue(200);
        for(int d : rQueue)
        {
            StdOut.println(d);
        }
        StdOut.println(rQueue.sample());
    }

}