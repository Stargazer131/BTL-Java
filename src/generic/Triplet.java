package generic;

public class Triplet <U, V, T extends Comparable<T>> implements Comparable<Triplet<U, V, T>>
{
    private U first;
    private V second;
    private T third;

    public Triplet(U first, V second, T third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public U getFirst()
    {
        return first;
    }

    public V getSecond()
    {
        return second;
    }

    public T getThird()
    {
        return third;
    }

    @Override
    public int compareTo(Triplet<U, V, T> that) 
    {
        return this.third.compareTo(that.third);
    }
}
