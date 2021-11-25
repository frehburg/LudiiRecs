package main.java.VisualEditor.EditorView;

import main.java.interfaces.iTree;

import javax.swing.*;

import static main.java.VisualEditor.EditorApp.getTestTree;

public class EditorFrame extends JFrame
{

    public EditorFrame()
    {
        setTitle("Drag&Drop test");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // testing on hard-coded tree
        iTree hardCodedTree = getTestTree();
        add(new EditorPanel(hardCodedTree));

    }

}
