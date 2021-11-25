package main.java.VisualEditor;

import main.java.domain.Node;
import main.java.interfaces.iNode;

public class EditorApp
{

    public static void main(String[] args)
    {

        //SyntaxBase base = new SyntaxBase();
        //base.generateSyntaxBase();
        //System.out.println(base.toString());
        iNode root = new Node("root");
        iNode node1 = root.addChild("game");

    }

}
