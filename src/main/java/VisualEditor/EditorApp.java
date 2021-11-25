package main.java.VisualEditor;

import main.java.VisualEditor.GrammarModel.SyntaxBase;

public class EditorApp
{

    public static void main(String[] args)
    {

        SyntaxBase base = new SyntaxBase();
        base.generateSyntaxBase();
        System.out.println(base.toString());

    }

}
