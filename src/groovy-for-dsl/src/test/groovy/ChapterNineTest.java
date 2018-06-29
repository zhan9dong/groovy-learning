import junit.framework.TestCase;

import java.util.Stack;

public class ChapterNineTest extends TestCase {

   public void testStack() {
        Stack stack = new Stack();

        assert stack.size() == 0;
        stack.push("elem");
        assert stack.size() == 1;
        assert stack.peek().equals("elem");
    }
}