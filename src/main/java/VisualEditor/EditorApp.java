package main.java.VisualEditor;

import main.java.Utils.FileUtils;
import main.java.VisualEditor.EditorView.EditorFrame;
import main.java.domain.Node;
import main.java.domain.Tree;
import main.java.interfaces.iNode;
import main.java.interfaces.iTree;
import main.java.parser.Parser;

import java.io.File;
import java.io.FileNotFoundException;


public class EditorApp
{

    public static void main(String[] args)
    {

        //SyntaxBase base = new SyntaxBase();
        //base.generateSyntaxBase();
        //System.out.println(base.toString());

        // Hard-coding tic-tac-toe tree
        //------------------------------------------------------
        String fileName = "\\res\\Tic-Tac-Toe.lud";
        File f = new File(fileName);
        String contents;
        if(FileUtils.isFileDotLud(fileName)) {
            try {
                contents = FileUtils.getContents(f);

                iTree tree = Parser.getTree(contents);
                new EditorFrame(tree);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static iTree getTestTree()
    {

        iNode root = new Node("root");

        iNode game = root.addChild("game");

        game.addChild("\"Tic-Tac-Toe\"");

        iNode players = game.addChild("player");
        players.addChild("2");

        iNode equipment = game.addChild("equipment");
        iNode collection = equipment.addChild("{}");
        iNode board = collection.addChild("board");
        iNode square = board.addChild("square");
        square.addChild("3");
        iNode piece1 = collection.addChild("piece");
        piece1.addChild("\"Disc\"");
        piece1.addChild("P1");
        iNode piece2 = collection.addChild("piece");
        piece2.addChild("\"Cross\"");
        piece2.addChild("P2");

        iNode rules = game.addChild("rules");

        iNode play = rules.addChild("play");
        iNode move = play.addChild("move");
        iNode add = move.addChild("Add");
        iNode to = move.addChild("to");
        iNode sites = to.addChild("sites");
        iNode empty = sites.addChild("Empty");

        iNode end = rules.addChild("end");
        iNode if_node = end.addChild("if");
        iNode is = if_node.addChild("is");
        is.addChild("Line");
        is.addChild("3");
        iNode result = end.addChild("result");
        result.addChild("Mover");
        result.addChild("Win");

        // game tree
        Tree testTree = new Tree(root);
        return testTree;

    }
}
