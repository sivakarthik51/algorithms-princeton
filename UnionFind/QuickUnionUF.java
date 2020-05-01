
public class QuickUnionUF 
{
    private int[] id;

    public QuickUnionUF(int N)
    {
        id = new int[N];
        for(int i = 0;i<N;i++)
        {
            id[i] = i;
        }
    }
    private int root(int i)
    {
        while(i != id[i]) i = id[i];
        return i;
    }

    public boolean connected(int p, int q)
    {
        return root(p) == root(q);
    }
    public void union(int p, int q)
    {
        int proot = root(p);
        int qroot = root(q);
        id[proot] = qroot;
    }
    public static void main(String[] args) 
    {
        QuickUnionUF q = new QuickUnionUF(10);
        q.union(1, 2);
        q.union(2, 3);
        q.union(3, 5);
        System.out.println(q.connected(1, 6));
    }
}