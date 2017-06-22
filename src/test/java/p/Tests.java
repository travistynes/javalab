package p;

import org.junit.Test;
import org.junit.Assert;

public class Tests {
    @Test
    public void test1() {
        Assert.assertTrue(Main.truthtest(true));
        Assert.assertFalse(Main.truthtest(false));
    }
}
