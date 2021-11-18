import main.java.domain.NodeType;
import org.junit.Assert;
import org.junit.Test;

public class NodeTypeTest {
    @Test
    /**
     * Tests the full cartesian product of all combinations of the 4 discrete values
     */
    public void testEquals() {
        NodeType a = NodeType.ERR;
        NodeType b = NodeType.ROOT;
        NodeType c = NodeType.INNER;
        NodeType d = NodeType.LEAF;

        Assert.assertEquals(true, a.equals(a));
        Assert.assertEquals(false, b.equals(a));
        Assert.assertEquals(false, c.equals(a));
        Assert.assertEquals(false, d.equals(a));

        Assert.assertEquals(false, a.equals(b));
        Assert.assertEquals(true, b.equals(b));
        Assert.assertEquals(false, c.equals(b));
        Assert.assertEquals(false, d.equals(b));

        Assert.assertEquals(false, a.equals(c));
        Assert.assertEquals(false, b.equals(c));
        Assert.assertEquals(true, c.equals(c));
        Assert.assertEquals(false, d.equals(c));

        Assert.assertEquals(false, a.equals(d));
        Assert.assertEquals(false, b.equals(d));
        Assert.assertEquals(false, c.equals(d));
        Assert.assertEquals(true, d.equals(d));
    }
}
