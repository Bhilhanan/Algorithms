import java.util.ArrayList;
import java.util.Stack;

import junit.framework.TestCase;


public class Lab1Test extends TestCase {

	private Stack<Integer> listToStack(ArrayList<Integer> list)
	{
		Stack<Integer> stack = new Stack<Integer>();
		for (int i = 0; i < list.size(); i++) {
			stack.push(list.get(i));
		}
		return stack;
	}
	/*
	 * Test for boundary cases and on inputs of increasing size
	 */
	public void testBySize()
	{
		System.out.println("Input NULL boundary case.");
		Stack<Integer> exitTrack = Lab1.trainSort(null);
		assertNotNull(exitTrack);
		assertTrue(exitTrack.empty());
		
		// Input size 0
		System.out.println("Input size 0.");
		ArrayList<Integer> list = RandomInputGenerator.genInTrack(0, 100);
		Stack<Integer> inputTrack = listToStack(list);
		exitTrack = Lab1.trainSort(inputTrack);
		assertNotNull(inputTrack);
		assertTrue(inputTrack.empty());
		assertNotNull(exitTrack);
		assertTrue(exitTrack.empty());
		
		// Input sizes 1 to 20
		for (int n = 1; n <= 20; n++) {
			System.out.println("Input size " + n);
			list = RandomInputGenerator.genInTrack(n, 100);
			inputTrack = listToStack(list);
			System.out.println("Input track: " + list);
			
			exitTrack = Lab1.trainSort(inputTrack);
			
			assertNotNull(inputTrack);
			assertTrue(inputTrack.empty());
			
			assertNotNull(exitTrack);
			assertFalse(exitTrack.empty());
			assertEquals(n, exitTrack.size());
			
			for (int i = 0; i < list.size(); i++) {
				assertTrue(exitTrack.contains(list.get(i)));
			}
			
			if (n > 1) {
				for (int i = 0; i < list.size() - 1; i++) {
					Integer pre = exitTrack.get(i);
					Integer post = exitTrack.get(i + 1);
					assertTrue(pre.intValue() <= post.intValue());
				}
			}
		}

		// Test case from lab write-up
		System.out.println("Lab write-up case:");
		inputTrack = new Stack<Integer>();
		inputTrack.push(7);
		inputTrack.push(6);
		inputTrack.push(2);
		inputTrack.push(9);
		inputTrack.push(4);
		
		Stack<Integer> outputTrack = Lab1.trainSort(inputTrack);
		
		assertNotNull(outputTrack);
		assertFalse(outputTrack.empty());
		assertEquals(5, outputTrack.size());
		assertEquals(9, outputTrack.pop().intValue());
		assertEquals(7, outputTrack.pop().intValue());
		assertEquals(6, outputTrack.pop().intValue());
		assertEquals(4, outputTrack.pop().intValue());
		assertEquals(2, outputTrack.pop().intValue());
	}
}
