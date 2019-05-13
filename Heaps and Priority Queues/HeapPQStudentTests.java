import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
  * Simple test cases for heaps and priority queues.
  * Write your own tests to ensure you cover all edge cases.
  *
  * @author CS 1332 TAs
  * @version 1.0
  */
public class HeapPQStudentTests {

    private static final int TIMEOUT = 200;
    private HeapInterface<Integer> maxHeap;
    private PriorityQueueInterface<Integer> maxPriorityQueue;

    @Before
    public void setUp() {
        maxHeap = new MaxHeap<>();
        maxPriorityQueue = new MaxPriorityQueue<>();
    }

    @Test(timeout = TIMEOUT)
    public void testHeap() {
        maxHeap.add(43);
        maxHeap.add(89);
        maxHeap.add(17);
        maxHeap.add(64);
        maxHeap.add(5);

        Integer[] expected = new Integer[11];
        expected[1] = 89;
        expected[2] = 64;
        expected[3] = 17;
        expected[4] = 43;
        expected[5] = 5;
        assertArrayEquals(expected,
                ((MaxHeap<Integer>) maxHeap).getBackingArray());

        assertEquals(new Integer(89), maxHeap.remove());
        assertEquals(new Integer(64), maxHeap.remove());
        assertEquals(3, maxHeap.size());
        assertFalse(maxHeap.isEmpty());
        assertEquals(new Integer(43), maxHeap.remove());
        assertEquals(new Integer(17), maxHeap.remove());
        assertEquals(new Integer(5), maxHeap.remove());
        assertTrue(maxHeap.isEmpty());
        Integer[] finalExpected = new Integer[11];
        assertArrayEquals(finalExpected,
                ((MaxHeap<Integer>) maxHeap).getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testPriorityQueue() {
        maxPriorityQueue.enqueue(43);
        maxPriorityQueue.enqueue(89);
        maxPriorityQueue.enqueue(17);
        maxPriorityQueue.enqueue(64);
        maxPriorityQueue.enqueue(5);

        assertEquals(new Integer(89), maxPriorityQueue.dequeue());
        assertEquals(new Integer(64), maxPriorityQueue.dequeue());
        assertEquals(3, maxPriorityQueue.size());
        assertFalse(maxPriorityQueue.isEmpty());
        assertEquals(new Integer(43), maxPriorityQueue.dequeue());
        assertEquals(new Integer(17), maxPriorityQueue.dequeue());
        assertEquals(new Integer(5), maxPriorityQueue.dequeue());
        assertTrue(maxPriorityQueue.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testResize() {
        for (int i = 0; i < 10; i++) {
            maxHeap.add(i * i * i - 74);
        }

        assertEquals(10, maxHeap.size());
        assertEquals(11, ((MaxHeap<Integer>) maxHeap).getBackingArray().length);

        maxHeap.add(10 * 10 * 10 - 74);

        assertEquals(11, maxHeap.size());
        assertEquals(23, ((MaxHeap<Integer>) maxHeap).getBackingArray().length);
    }
}
