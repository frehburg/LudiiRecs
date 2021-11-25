package main.java.VisualEditor.EditorView;

import main.java.interfaces.iNode;
import main.java.interfaces.iTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.GeneralPath;

public class EditorPanel extends JPanel implements MouseListener, MouseMotionListener
{

    private final iTree<iNode> NodeTree;

    public EditorPanel(iTree<iNode> NodeTree)
    {
        this.NodeTree = NodeTree;
        addMouseListener(this);
        addMouseMotionListener(this);
        addVisualNodes();
    }

    /**
     * Traverse node tree and add visual representation of nodes to the panel
     */
    private void addVisualNodes()
    {

    }

    //-------------------------------------------------------------------------

    @Override
    public void paint(final Graphics g)
    {
        final Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Clear the view
        g2d.setPaint(Color.white);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        drawTest(g2d);
    }

    //-------------------------------------------------------------------------

    void drawTest(final Graphics2D g2d)
    {
        g2d.setColor(new Color(255, 0, 0));

        final Point[] pts =
                {
                        new Point(100, 100),
                        new Point(300, 100),
                        new Point(300, 600),
                        new Point(500, 600),
                };

        for (int n = 0; n < pts.length - 1; n++)
            g2d.drawLine(pts[n].x, pts[n].y, pts[n+1].x, pts[n+1].y);

        g2d.setColor(new Color(0, 127, 255));

        final GeneralPath path = new GeneralPath();
        path.moveTo(pts[0].x, pts[0].y);
        path.curveTo(pts[1].x, pts[1].y, pts[2].x, pts[2].y, pts[3].x, pts[3].y);
        g2d.draw(path);
    }

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
