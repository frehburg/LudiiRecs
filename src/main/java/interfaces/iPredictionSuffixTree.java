package main.java.interfaces;

public interface iPredictionSuffixTree<T>{

    /**
     * Returns true, if there is a matching path in this that starts at one of the children of the root,
     * throws NullPOinterException if the path is empty or null
     * @param t
     * @return
     * @throws NullPointerException
     */
    boolean find(iPath<T> t) throws NullPointerException;

    /**
     * Inserts the path t into the PST, throws NullPOinterException if the path is empty or null
     * @param t
     * @throws NullPointerException
     */
    void insert(iPath<T> t) throws NullPointerException;


}
