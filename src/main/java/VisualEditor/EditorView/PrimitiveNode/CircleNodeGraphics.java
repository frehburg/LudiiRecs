package main.java.VisualEditor.EditorView.PrimitiveNode;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class CircleNodeGraphics {

    private int x;
    private int y;
    private int w;
    private int h;
    private Color outC;
    private Color inC;

    final private double CORD_SCALE = 0.15;
    final private double SIZE_SCALE = 0.7;

    public CircleNodeGraphics(int x, int y, int w, int h, Color outC, Color inC) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.outC = outC;
        this.inC = inC;
    }

    public void drawOuter(Graphics2D g2) {
        Shape outer = new Ellipse2D.Double(x, y, w, h);
        g2.fill(outer);
        g2.setColor(outC);
        g2.draw(outer);
    }

    public void drawInner(Graphics2D g2) {
        Shape inner = new Ellipse2D.Double(x+w*CORD_SCALE, y+h*CORD_SCALE, w*SIZE_SCALE, h*SIZE_SCALE);
        g2.fill(inner);
        g2.setColor(inC);
        g2.draw(inner);
    }

    public void drawOuter(Graphics2D g2, int x, int y) {
        Shape outer = new Ellipse2D.Double(x, y, w, h);
        g2.fill(outer);
        g2.setColor(outC);
        g2.draw(outer);
    }

    public void drawInner(Graphics2D g2, int x, int y) {
        Shape inner = new Ellipse2D.Double(x+w*CORD_SCALE, y+h*CORD_SCALE, w*SIZE_SCALE, h*SIZE_SCALE);
        g2.fill(inner);
        g2.setColor(inC);
        g2.draw(inner);
    }

    public void setInC(Color inC) {
        this.inC = inC;
    }

    public void setOutC(Color outC) {
        this.outC = outC;
    }
}
