
public class QuickFindUF
{
    private int[] id;

    public QuickFindUF(int N)
    {
        id = new int[N];
        for(int i = 0;i<N;i++)
        {
            id[i] = i;
        }
    }
    public boolean connected(int p, int q)
    {
        return id[p] == id[q];
    }

    public void union(int p,int q)
    {
        int pid = id[p];
        int qid = id[q];
        for(int i = 0;i<id.length;i++)
        {
            if(id[i] == pid) id[i] = qid;
        }
    }
    public static void main(String args[])
    {
        QuickFindUF q = new QuickFindUF(10);
        q.union(0,5);
        q.union(2, 3);
        System.out.println(q.connected(0, 5));
        System.out.println(q.connected(8, 7));

    }


}