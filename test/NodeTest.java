import main.java.domain.Node;
import main.java.domain.NodeType;
import main.java.interfaces.iNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class NodeTest {
    private iNode singleNode, childrenNode;

    @Test
    public void testAddChild() {
        setup();

        //test if everything works when adding a child to a single node
        singleNode.addChild("leaf");
        ArrayList<iNode> singleNodeChildren = (ArrayList<iNode>)singleNode.getChildren();
        //test nodetypes
        Assert.assertEquals(NodeType.ROOT, singleNode.getNodeType());
        Assert.assertEquals(NodeType.LEAF, singleNodeChildren.get(0).getNodeType());
        //test children list
        Assert.assertEquals(1, singleNode.getChildrenSize());
        Assert.assertEquals(0, singleNodeChildren.get(0).getChildrenSize());
        //test keyword
        Assert.assertEquals("keyword", singleNode.getKeyword());
        Assert.assertEquals("leaf", singleNodeChildren.get(0).getKeyword());

        //test if everything works when adding a child to an inner node
        ArrayList<iNode> childrenNodeChildren = (ArrayList<iNode>)childrenNode.getChildren();
        iNode inner = childrenNodeChildren.get(0);
        inner.addChild("leaf2");
        ArrayList<iNode> innerNodeChildren = (ArrayList<iNode>)inner.getChildren();
        //test nodetypes
        Assert.assertEquals(NodeType.ROOT, childrenNode.getNodeType());
        Assert.assertEquals(NodeType.INNER, inner.getNodeType());
        for(iNode c : innerNodeChildren) {
            Assert.assertEquals(NodeType.LEAF, c.getNodeType());
        }
        //test children list
        Assert.assertEquals(1, childrenNode.getChildrenSize());
        Assert.assertEquals(2, inner.getChildrenSize());
        for(iNode c : innerNodeChildren) {
            Assert.assertEquals(0, c.getChildrenSize());
        }
        //test keywords
        Assert.assertEquals("root", childrenNode.getKeyword());
        Assert.assertEquals("inner", inner.getKeyword());
        Assert.assertEquals("leaf", innerNodeChildren.get(0).getKeyword());
        Assert.assertEquals("leaf2", innerNodeChildren.get(1).getKeyword());
    }

    @Test
    public void testGetKeyWord() {
        setup();
        Assert.assertEquals("keyword", singleNode.getKeyword());
    }

    @Test
    public void testGetParent() {
        setup();
        ArrayList<iNode> children = (ArrayList<iNode>)childrenNode.getChildren();
        Assert.assertEquals(true, children.get(0).getParent().equals(childrenNode));

        Assert.assertEquals(true, singleNode.getParent().equals(Node.SUPER_NODE));
    }

    @Test
    public void testGetChildren() {
        //mainly need to check that you cannot alter the children list from the received one
    }

    @Test(expected = NullPointerException.class)
    public void testNullNode() {
        new Node(null);
        iNode n = new Node("");
        Assert.assertEquals(true, singleNode.isNull(n));
        n = new Node("        ");
        Assert.assertEquals(true, singleNode.isNull(n));
    }

    @Test
    public void testIsNull(){
        setup();
        iNode n = null;
        Assert.assertEquals(true, singleNode.isNull(n));

        n = new Node(" keyword");
        Assert.assertEquals(false, singleNode.isNull(n));

        n = new Node("keyword");
        Assert.assertEquals(false, n.isNull(n));
    }

    @Test
    public void testClone() {
        setup();
        iNode clone = singleNode.clone();

        Assert.assertEquals(false, singleNode.equals(clone));
        Assert.assertEquals(singleNode.getKeyword(), clone.getKeyword());
        Assert.assertEquals(true, singleNode.getParent().equals(clone.getParent()));
        System.out.println(clone.toString());
        System.out.println(singleNode.toString());
        Assert.assertEquals(singleNode.getChildren(), clone.getChildren());
        Assert.assertEquals(singleNode.getNodeType(), clone.getNodeType());
    }

    @Test
    public void testToString() {
        setup();
        String expected = "(Node id: 2, keyword: root, parent: SUPER_NODE, nodeType: "
                +NodeType.ROOT + ", children: [inner, ])";
        String actual = childrenNode.toString();
    }

    private void setup() {
        // create a node that is just a node
        singleNode = new Node("keyword");
        // create a node with 1 child that has another child
        childrenNode = new Node("root");
        childrenNode.addChild("inner");
        ArrayList<iNode> children = (ArrayList<iNode>)childrenNode.getChildren();
        children.get(0).addChild("leaf");
    }

}
