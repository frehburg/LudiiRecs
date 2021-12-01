package main.java.VisualEditor.EditorView;

import main.java.interfaces.iTree;

import javax.swing.*;

public class EditorFrame extends JFrame
{

    public EditorFrame(iTree tree)
    {
        setTitle("Drag&Drop test");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        add(new JScrollPane(new EditorPanel(tree)));

    }

}
