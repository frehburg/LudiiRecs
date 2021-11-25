package main.java.VisualEditor.EditorView;

import main.java.interfaces.iNode;

import javax.swing.*;

public class BasicNode extends JComponent implements VisualNode
{
    private int x,y;
    private final iNode NodeBack;

    public BasicNode(iNode NodeBack)
    {
        this.NodeBack = NodeBack;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public iNode getNodeBack() {
        return NodeBack;
    }
}
