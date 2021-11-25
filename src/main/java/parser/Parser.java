package main.java.parser;

import main.java.domain.Tree;

public class Parser {
    public static Tree getTree(String gameDescription) {
        gameDescription = preProcessing(gameDescription);
        Tree t = parse(gameDescription);
        t = postProcessing(t);
        return t;
    }

    private static String preProcessing(String gameDescription) {
        return gameDescription;
    }

    private static Tree parse(String gameDescription) {
        //1. Create a node with the whole gameDescription as the keyword
        return null;
    }

    private static Tree postProcessing(Tree t) {
        return null;
    }
}
