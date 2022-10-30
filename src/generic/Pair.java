package generic;

import java.io.Serializable;

public class Pair<U, V> implements Serializable
{
    private static final long serialVersionUID = 1312002L;

    private U first;
    private V second;

    public Pair(U first, V second)
    {
        this.first = first;
        this.second = second;
    }

    public U getFirst()
    {
        return first;
    }

    public V getSecond()
    {
        return second;
    }

    public void setSecond(V second)
    {
        this.second = second;
    }
}
