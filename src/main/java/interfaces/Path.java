package main.java.interfaces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Path<T> implements iPath {
    private int current;
    private ArrayList<T> nodes;
    public static final int NO_CURRENT = -1;

    public Path() {
        nodes = new ArrayList<>();
        current = NO_CURRENT;
    }
    @Override
    public boolean setCurrent(Object o) {
        if(this.contains(o)) {
            current = nodes.indexOf(o);
            return true;
        }
        return false;
    }

    @Override
    public Object getFirst() throws NullPointerException {
        if(!nodes.isEmpty()) {
            return nodes.get(0);
        }
        throw new NullPointerException("The path is empty");
    }

    @Override
    public Object getLast() throws NullPointerException {
        if(!nodes.isEmpty()) {
            return nodes.get(nodes.size() - 1);
        }
        throw new NullPointerException("The path is empty");
    }

    @Override
    public Object getCurrent() throws NullPointerException {
        if(hasNext()){
            return nodes.get(current);
        }
        throw new NullPointerException("Err");
    }

    @Override
    public boolean hasNext() {
        if(current == NO_CURRENT) {
            throw new NullPointerException("The current pointer has not been set yet");
        }
        return current < nodes.size();
    }

    @Override
    public Object next() {
        if(hasNext()) {
            current++;
        } else {
            throw new NullPointerException("There is no next element");
        }
        return getCurrent();
    }


    @Override
    public boolean isFirst() throws NullPointerException {
        if(current == NO_CURRENT) {
            throw new NullPointerException("The current pointer has not been set yet");
        }
        return current == 0;
    }

    @Override
    public boolean isLast() throws NullPointerException {
        if(current == NO_CURRENT) {
            throw new NullPointerException("The current pointer has not been set yet");
        }
        return current == nodes.size() - 1;
    }

    @Override
    public int size() {
        return nodes.size();
    }

    @Override
    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return nodes.contains(o);
    }

    @Override
    public Iterator iterator() {
        return this;
    }

    @Override
    public Object[] toArray() {
        return nodes.toArray();
    }

    @Override
    /**
     * Adds the element to the end of the path
     */
    public boolean add(Object o) {
        if(o != null) {
            T t = (T) o;
            return nodes.add(t);
        }
        throw new IllegalArgumentException("Wrong type provided");
    }

    @Override
    public boolean remove(Object o) {
        boolean b = nodes.remove(o);
        if(this.isEmpty()) {
            current = NO_CURRENT;
        }
        return b;
    }

    @Override
    public boolean addAll(Collection c) {
        return nodes.addAll(c);
    }


    @Override
    public void clear() {
        nodes = new ArrayList<>();
        current = NO_CURRENT;
    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        boolean b = false;
        for(Object o : c) {
            if(this.contains(o)) {
                this.remove(o);
                b = true;
            }
        }
        return b;
    }

    @Override
    public boolean containsAll(Collection c) {
        for(Object o : c) {
            if(!this.contains(o)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return null;
    }
}
