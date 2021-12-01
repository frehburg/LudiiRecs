package main.java.VisualEditor.NodeController;

import main.java.VisualEditor.EditorView.PrimitiveNode.BasicNodeComponent;

import java.util.List;

import static main.java.VisualEditor.EditorView.PrimitiveNode.BasicNodeComponent.NODE_SIZE;

public class GraphDrawing
{
    private static final float LAYER_TREE_SPACING_FACTOR = 1.5f;
    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 600;

    public static void LayerTreeHeuristics(List<List<BasicNodeComponent>> nodeLayerList)
    {
        for (int Y = 0; Y < nodeLayerList.size(); Y++)
        {
            for (int X = 0; X < nodeLayerList.get(Y).size(); X++)
            {
                BasicNodeComponent node = nodeLayerList.get(Y).get(X);
                node.setX((int)(X* NODE_SIZE*1.5));
                node.setY((int)(Y* NODE_SIZE*1.5));
            }
        }
    }

    public static void LayerTreeBalanced(List<List<BasicNodeComponent>> nodeLayerList)
    {
        for (int Y = nodeLayerList.size() - 1; Y > 0 ; Y--)
        {
            for (int X = 0; X < nodeLayerList.get(Y).size(); X++)
            {
                BasicNodeComponent node = nodeLayerList.get(Y).get(X);
                node.setX((int)(NODE_SIZE*X*1.5));
                node.setY((int)((nodeLayerList.size() - Y)*NODE_SIZE));
            }
        }
    }
}
