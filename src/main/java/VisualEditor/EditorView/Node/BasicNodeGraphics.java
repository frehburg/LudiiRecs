package main.java.VisualEditor.EditorView.Node;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * Graphics of the basic node
 * @author nic0gin
 */
public class BasicNodeGraphics
{

    private int x;
    private int y;
    private int size;
    private String label;

    private Color outC = Color.gray;
    private Color inC = Color.red;

    final private double CORD_SCALE = 0.15;
    final private double SIZE_SCALE = 0.7;

    public BasicNodeGraphics(int x, int y, int size, String label)
    {
        this.x = x;
        this.y = y;
        this.size = size;
        this.label = label;
    }

    public void drawOuter(Graphics2D g2)
    {
        Shape outer = new Ellipse2D.Double(x, y, size, size);
        g2.fill(outer);
        g2.setColor(outC);
        g2.draw(outer);
    }

    public void drawInner(Graphics2D g2)
    {
        Shape inner = new Ellipse2D.Double(x+size*CORD_SCALE, y+size*CORD_SCALE, size*SIZE_SCALE, size*SIZE_SCALE);
        g2.fill(inner);
        g2.setColor(inC);
        g2.draw(inner);
    }

    public void drawOuter(Graphics2D g2, int x, int y)
    {
        Shape outer = new Ellipse2D.Double(x, y, size, size);
        g2.fill(outer);
        g2.setColor(outC);
        g2.draw(outer);
    }

    public void drawInner(Graphics2D g2, int x, int y)
    {
        Shape inner = new Ellipse2D.Double(x+size*CORD_SCALE, y+size*CORD_SCALE, size*SIZE_SCALE, size*SIZE_SCALE);
        g2.fill(inner);
        g2.setColor(inC);
        g2.draw(inner);
    }

    public void drawLabel(Graphics2D g2d, int x, int y)
    {
        x = x + size/4;
        y = y + size/2;

        Color textC = Color.black;
        Color rectC = Color.white;

        FontMetrics fm = g2d.getFontMetrics();
        Rectangle2D rect = fm.getStringBounds(label, g2d);
        g2d.setColor(rectC);
        g2d.fillRect(x, y - fm.getAscent(), (int) rect.getWidth(), (int) rect.getHeight());
        g2d.setColor(textC);
        g2d.drawString(label, x, y);
    }

    public void setInC(Color inC) {
        this.inC = inC;
    }

    public void setOutC(Color outC) {
        this.outC = outC;
    }
}
