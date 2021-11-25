import main.java.parser.*;
import org.junit.Assert;
import org.junit.Test;

public class ParserTest {
    String[] x;
    LudemeType[] y1,y2;
    public void setup() {
        x = new String[]
                {
                        "(regions  P1 (union (sites Bottom) (sites {})))",
                        "{1 4 2 8 0}",
                        "\"17,W,S1,E5,S1,W\"",
                        "\"A1\"..\"B3\"",
                        "if:(is Occupied (site))",
                        "123",
                        "1.23",
                        ".326",
                        "673.",
                        "7..20",
                        "Mover",
                        "<Num>",
                        "<tigers>",
                        "True",
                        "false"

                };
        y2 = new LudemeType[]
                {
                        LudemeType.LUDEME,
                        LudemeType.COLLECTION,
                        LudemeType.STRING,
                        LudemeType.RANGE_FIELDS,
                        LudemeType.OPTIONAL_ARGUMENT_NAME,
                        LudemeType.INTEGER,
                        LudemeType.FLOAT,
                        LudemeType.FLOAT,
                        LudemeType.FLOAT,
                        LudemeType.RANGE_NUMBER,
                        LudemeType.ATTRIBUTE,
                        LudemeType.OPTION_LABEL,
                        LudemeType.OPTION_ARGUMENT_LABEL,
                        LudemeType.BOOLEAN,
                        LudemeType.BOOLEAN
                };
        y1 = new LudemeType[]
                {
                        LudemeType.PRE_LUDEME,
                        LudemeType.PRE_COLLECTION,
                        LudemeType.PRE_STRING,
                        LudemeType.PRE_STRING,
                        LudemeType.PRE_LOWERCASE,
                        LudemeType.PRE_NUMBER,
                        LudemeType.PRE_NUMBER,
                        LudemeType.PRE_NUMBER,
                        LudemeType.PRE_NUMBER,
                        LudemeType.PRE_NUMBER,
                        LudemeType.PRE_UPPERCASE,
                        LudemeType.PRE_OPTION,
                        LudemeType.PRE_OPTION,
                        LudemeType.PRE_UPPERCASE,
                        LudemeType.PRE_LOWERCASE
                };
    }
    @Test
    public void testPreClassify() {
        setup();
        for(int i = 0; i < x.length; i++) {
            Assert.assertEquals(y1[i], Parser.preClassify(x[i].charAt(0)));
        }
    }

    @Test
    public void testReClassify() {
        setup();
        for(int i = 0; i < x.length; i++) {
            System.out.println(x[i]);
            Assert.assertEquals(y2[i], Parser.reClassify(y1[i],x[i]));
        }
    }
}
