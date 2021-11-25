package main.java.VisualEditor.EditorView.Edge;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class EdgeGraphics
{
    private int x;
    private int y;
    private int size;

    private Color outC = Color.gray;
    private Color inC = Color.red;

    final private double CORD_SCALE = 0.15;
    final private double SIZE_SCALE = 0.7;

    public EdgeGraphics(int x, int y, int size, String label) {
        this.x = x;
        this.y = y;
        this.size = size;

    }

    public void drawEdge(Graphics2D g2) {
        Shape outer = new Ellipse2D.Double(x, y, size, size);
        g2.fill(outer);
        g2.setColor(outC);
        g2.draw(outer);
    }

}
