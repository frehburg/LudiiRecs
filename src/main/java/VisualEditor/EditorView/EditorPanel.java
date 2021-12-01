package main.java.VisualEditor.EditorView;

import main.java.VisualEditor.EditorView.Edge.Edge;
import main.java.VisualEditor.EditorView.PrimitiveNode.BasicNodeComponent;
import main.java.interfaces.iNode;
import main.java.interfaces.iTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// TODO: add scroll panels
// FIXME: does not render until screen is resized
public class EditorPanel extends JPanel implements MouseListener, MouseMotionListener
{

    private final iTree<iNode> NodeTree;
    private List<List<BasicNodeComponent>> nodeLayerList;
    private HashMap<Integer, BasicNodeComponent> nodeComponentHashMap;
    private List<Edge> edgeList;
    private static final int XSPACING = 70, YSPACING = 75;

    public EditorPanel(iTree<iNode> NodeTree)
    {
        nodeComponentHashMap = new HashMap<>();
        edgeList = new ArrayList<>();

        nodeLayerList = new ArrayList<>();
        this.NodeTree = NodeTree;
        addMouseListener(this);
        addMouseMotionListener(this);
        setVisible(true);

        addVisualNodes();
    }

    /**
     * Traverse node tree and add visual representation of nodes to the panel
     */
    private void addVisualNodes()
    {
        List<List<iNode>> nodes = NodeTree.layerTraversal();
        List<BasicNodeComponent> layer;

        for (List<iNode> l : nodes)
        {
            layer = new ArrayList<>();
            for (iNode n : l)
            {
                // add node component to the list of components to be drawn
                // FIXME: think of better implementation for the node id
                BasicNodeComponent nodeComponent = new BasicNodeComponent(n.getKeyword(), n.getId().hashCode());
                nodeComponent.setChildrenIDs(n.getChildren());
                nodeComponentHashMap.put(n.getId().hashCode(), nodeComponent);
                layer.add(nodeComponent);
            }
            nodeLayerList.add(layer);
        }

        // adding edges
        for (List<BasicNodeComponent> l : nodeLayerList)
        {
            for (BasicNodeComponent n : l)
            {

                List<Integer> childrenIDs = n.getChildrenIDs();
                if(childrenIDs != null)
                {
                    for (int id : childrenIDs)
                    {
                        edgeList.add(new Edge(n, nodeComponentHashMap.get(id)));
                    }
                }
            }
        }

    }

    private void addEdges()
    {

    }

    //-------------------------------------------------------------------------

    @Override
    public void paint(final Graphics g)
    {
        super.paintComponent(g);
        for (int yCoordinate = 0; yCoordinate < nodeLayerList.size(); yCoordinate++)
        {
            for (int xCoordinate = 0; xCoordinate < nodeLayerList.get(yCoordinate).size(); xCoordinate++)
            {
                BasicNodeComponent node = nodeLayerList.get(yCoordinate).get(xCoordinate);
                node.drawComponents(g, xCoordinate*XSPACING, yCoordinate*YSPACING);
            }
        }

        for (Edge edge : edgeList)
        {
            edge.drawEdge((Graphics2D) g);
        }

        //final Graphics2D g2d = (Graphics2D)g;
        //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Clear the view
        //g2d.setPaint(Color.white);
        //g2d.fillRect(0, 0, getWidth(), getHeight());
        revalidate();
        repaint();
    }

    //-------------------------------------------------------------------------


    //-------------------------------------------------------------------------

    @Override
    public void mouseClicked(MouseEvent arg0)
    {
    }

    @Override
    public void mouseEntered(MouseEvent arg0)
    {
    }

    @Override
    public void mouseExited(MouseEvent arg0)
    {
    }

    @Override
    public void mousePressed(MouseEvent arg0)
    {
        System.out.println("Mouse pressed at: " + arg0.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent arg0)
    {
        System.out.println("Mouse released at: " + arg0.getPoint());
    }

    @Override
    public void mouseDragged(MouseEvent arg0)
    {
        System.out.println("Mouse dragged to: " + arg0.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent arg0)
    {
    }
}
