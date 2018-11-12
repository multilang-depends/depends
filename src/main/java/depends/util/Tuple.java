package depends.util;

public class Tuple<X, Y>
{
    public X x;
    public Y y;
    public Tuple(X x, Y y)
    {
        this.x = x;
        this.y = y;
    }

    public Tuple() {
    }

	public void setX(X x) {
        this.x = x;
    }

    public void setY(Y y) {
        this.y = y;
    }

    @Override
    public String toString()
    {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }

        if (!(other instanceof Tuple))
        {
            return false;
        }

        Tuple<X,Y> other_ = (Tuple<X,Y>) other;

        // this may cause NPE if nulls are valid values for x or y. The logic may be improved to handle nulls properly, if needed.
        return other_.x.equals(this.x) && other_.y.equals(this.y);
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
        return result;
    }
}
