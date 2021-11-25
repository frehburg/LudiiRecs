package main.java.VisualEditor.EditorView.PrimitiveNode;

import javax.swing.*;
import java.awt.*;

public class CircleNodeComponent extends JComponent {

    private int h;
    private int w;
    private CircleNodeGraphics node;


    public CircleNodeComponent(int x, int y, int h, int w, Color inC, Color outC) {
        this.h = h;
        this.w = w;
        node = new CircleNodeGraphics(x,y, h, w, inC, outC);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        node.drawOuter(g2D);
        node.drawInner(g2D);
    }

    public void drawComponents(Graphics g, int x, int y) {
        node.drawOuter((Graphics2D) g, x, y);
        node.drawInner((Graphics2D) g, x, y);
    }

    public void setOutC(Color ouC) {
        node.setOutC(ouC);
    }

    public void setInC(Color inC) {
        node.setInC(inC);
    }

}
