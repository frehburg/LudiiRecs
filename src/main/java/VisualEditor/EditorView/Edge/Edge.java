package main.java.VisualEditor.EditorView.Edge;

import main.java.VisualEditor.EditorView.Node.VisualNode;
import main.java.interfaces.iNode;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Edge
{
    private final VisualNode from;
    private final VisualNode to;
    private final String label;
    private final EdgeComponent edgeComponent;

    //TODO: add abstraction
    public Edge(VisualNode from, VisualNode to)
    {
        this.from = from;
        this.to = to;
        label = null;
        this.edgeComponent = new EdgeComponent(from.getCoords(), to.getCoords());
    }

    public Edge(VisualNode from, VisualNode to, String label)
    {
        this.from = from;
        this.to = to;
        this.label = label;
        this.edgeComponent = new EdgeComponent(from.getCoords(), to.getCoords(), label);
    }

    public EdgeComponent getGraphEdge() {
        return this.edgeComponent;
    }

    public static List<Edge> setParentChildrenEdges(iNode from, List<iNode> to)
    {
        List<Edge> edgeList = new ArrayList<>();
        Point2D S = ((VisualNode) from).getCoords();
        for(iNode c : to)
        {
            edgeList.add(new Edge((VisualNode) from, (VisualNode) c));
        }

        return edgeList;
    }

    public void drawEdge(Graphics2D g2d)
    {
        Point2D A = from.getCoords();
        Point2D B = to.getCoords();
        edgeComponent.drawEdge(g2d, A, B);
    }
}
