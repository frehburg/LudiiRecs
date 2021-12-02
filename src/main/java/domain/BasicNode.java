package main.java.domain;

import java.util.ArrayList;

public class BasicNode<T> {
    private BasicNode<T> parent;
    private ArrayList<BasicNode<T>> children;
    private T t;
    public BasicNode(T t) {
        this.t = t;
        this.children = new ArrayList<>();
    }

    public BasicNode<T> addChild(T t1) {
        BasicNode<T> child = new BasicNode<>(t1);
        this.children.add(child);
        return child;
    }

    //----------------


    public BasicNode<T> getParent() {
        return parent;
    }

    public ArrayList<BasicNode<T>> getChildren() {
        return children;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
