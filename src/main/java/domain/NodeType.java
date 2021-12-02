package main.java.domain;

public enum NodeType {
    ERR(-1),
    ROOT(0),
    INNER(1),
    LEAF(2),
    SUPER(Integer.MAX_VALUE);

    private final int id;

    NodeType(int id){
        this.id = id;
    }

    public boolean equals(NodeType nodeType) {
        return this.id == nodeType.id;
    }
}
