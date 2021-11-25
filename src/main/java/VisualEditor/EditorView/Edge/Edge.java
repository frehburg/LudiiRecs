package main.java.VisualEditor.EditorView.Edge;

import main.java.VisualEditor.EditorView.PrimitiveNode.DragDropNode;

public class Edge
{
    private final DragDropNode from;
    private final DragDropNode to;
    private String label;

    //TODO: add abstraction
    public Edge(DragDropNode from,DragDropNode to)
    {
        this.from = from;
        this.to = to;
    }

    public Edge(DragDropNode from,DragDropNode to, String label)
    {
        this.from = from;
        this.to = to;
        this.label = label;
    }


}
