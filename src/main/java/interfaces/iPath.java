package main.java.interfaces;

import java.util.Collection;
import java.util.Iterator;

public interface iPath <T> extends Collection, Iterator {
    /**
     * Tries to set the current pointer to an element t in the path,
     * if it exists.
     * Returns true if successful, false if not
     * @param t
     * @return
     */
    boolean setCurrent(T t);


    /**
     * Retrieves the first element of the path if exists, throws NullPointerException,
     * if the path is empty.
     * @return
     * @throws NullPointerException
     */
    T getFirst() throws NullPointerException;
    /**
     * Retrieves the last element of the path if exists, throws NullPointerException,
     * if the path is empty.
     * @return
     * @throws NullPointerException
     */
    T getLast() throws NullPointerException;
    /**
     * Retrieves the current element of the path if exists, throws NullPointerException,
     * if the path is empty or the current pointer has not been set yet.
     * @return
     * @throws NullPointerException
     */
    T getCurrent() throws NullPointerException;

    /**
     * Returns true if the current pointer is on the first element in the path.
     * Throws a NullPointerException if the current pointer has not been set yet.
     *@return
     *@throws NullPointerException
     */
    boolean isFirst() throws NullPointerException;
    /**
     * Returns true if the current pointer is on the first element in the path.
     * Throws a NullPointerException if the current pointer has not been set yet.
     *@return
     *@throws NullPointerException
     */
    boolean isLast() throws NullPointerException;
}
