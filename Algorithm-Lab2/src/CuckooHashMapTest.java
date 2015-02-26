import java.util.HashMap;

import junit.framework.TestCase;
/**
 * 
 * @author Naeem Shareef
 * @version November
 */
public class CuckooHashMapTest extends TestCase
{    
    public void testConstructor()
    {
    	// Boundary Case
        CuckooHashMap<Integer, String> map = 
            new CuckooHashMap<Integer, String>(0);
        assertNotNull(map);
        assertEquals(0, map.size());
        assertTrue(map.isEmpty());
        assertNull(map.get(1));
        assertFalse(map.containsKey(1));
        assertFalse(map.containsValue("one"));
        
        // Typical Cases
        map = new CuckooHashMap<Integer, String>(1);
        assertNotNull(map);
        assertEquals(0, map.size());
        assertTrue(map.isEmpty());
        assertNull(map.get(0));
        assertNull(map.get(1));
        assertNull(map.getEntry(0));
        assertFalse(map.containsKey(1));
        assertFalse(map.containsValue("one"));
        
        map = new CuckooHashMap<Integer, String>(2);
        assertNotNull(map);
        assertEquals(0, map.size());
        assertTrue(map.isEmpty());
        assertNull(map.get(0));
        assertNull(map.get(1));
        assertNull(map.getEntry(0));
        assertNull(map.getEntry(1));
        assertFalse(map.containsKey(1));
        assertFalse(map.containsValue("one"));
    }
    
    public void testGetPutNoReHash()
    {
    	// Bucket size 1
    	CuckooHashMap<Integer, String> map = new CuckooHashMap<Integer, String>(1);
    	assertNull(map.put(0, "zero-1"));
    	assertEquals(1, map.size());
    	assertFalse(map.isEmpty());
    	assertEquals("zero-1", map.get(0));
    	assertEquals(0, map.getEntry(0).getKey().intValue());
    	assertEquals("zero-1", map.getEntry(0).getValue());
    	assertNull(map.get(1));
        assertTrue(map.containsKey(0));
        assertTrue(map.containsValue("zero-1"));
    	
    	String key = map.put(0, "zero-2");
    	assertEquals("zero-1", key);
    	assertEquals(1, map.size());
    	assertFalse(map.isEmpty());
    	assertEquals("zero-2", map.get(0));
    	assertEquals(0, map.getEntry(0).getKey().intValue());
    	assertEquals("zero-2", map.getEntry(0).getValue());
        assertTrue(map.containsKey(0));
        assertTrue(map.containsValue("zero-2"));
        assertFalse(map.containsValue("zero-1"));
    	assertNull(map.get(3));
    	
    	map = new CuckooHashMap<Integer, String>(1);
    	assertNull(map.put(1, "one"));
    	assertEquals(1, map.size());
    	assertFalse(map.isEmpty());
    	assertEquals("one", map.get(1));
    	assertEquals(1, map.getEntry(0).getKey().intValue());
    	assertEquals("one", map.getEntry(0).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
    	
    	// Bucket size 2, fill at index 0 then index 1
    	map = new CuckooHashMap<Integer, String>(2);
    	assertNull(map.put(0, "zero-1"));
    	assertEquals(1, map.size());
    	assertFalse(map.isEmpty());
    	assertEquals("zero-1", map.get(0));
    	assertEquals(0, map.getEntry(0).getKey().intValue());
    	assertEquals("zero-1", map.getEntry(0).getValue());
        assertTrue(map.containsKey(0));
        assertTrue(map.containsValue("zero-1"));
    	assertNull(map.getEntry(1));
    	assertNull(map.get(1));
    	
    	assertNull(map.put(1, "one"));
    	assertEquals(2, map.size());
    	assertFalse(map.isEmpty());
    	assertEquals("one", map.get(1));
    	assertEquals("zero-1", map.get(0));
    	assertEquals(0, map.getEntry(0).getKey().intValue());
    	assertEquals("zero-1", map.getEntry(0).getValue());
    	assertEquals(1, map.getEntry(1).getKey().intValue());
    	assertEquals("one", map.getEntry(1).getValue());
        assertTrue(map.containsKey(0));
        assertTrue(map.containsValue("zero-1"));
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
    
    	key = map.put(0, "zero-2");
    	assertEquals("zero-1", key);
    	assertEquals(2, map.size());
    	assertFalse(map.isEmpty());
    	assertEquals("zero-2", map.get(0));
    	assertEquals(0, map.getEntry(0).getKey().intValue());
    	assertEquals("zero-2", map.getEntry(0).getValue());
        assertTrue(map.containsKey(0));
        assertTrue(map.containsValue("zero-2"));
    	assertNull(map.get(3));
    	
    	// Bucket size 2, fill at index 1 then index 0
    	map = new CuckooHashMap<Integer, String>(2);
    	assertNull(map.put(1, "one"));
    	assertEquals(1, map.size());
    	assertFalse(map.isEmpty());
    	assertEquals("one", map.get(1));
    	assertNull(map.getEntry(0));
    	assertNull(map.get(0));
    	assertEquals(1, map.getEntry(1).getKey().intValue());
    	assertEquals("one", map.getEntry(1).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
    	
    	assertNull(map.put(0, "zero-1"));
    	assertEquals(2, map.size());
    	assertFalse(map.isEmpty());
    	assertEquals(0, map.getEntry(0).getKey().intValue());
    	assertEquals("zero-1", map.getEntry(0).getValue());
    	assertEquals("zero-1", map.get(0));
    	assertEquals(1, map.getEntry(1).getKey().intValue());
    	assertEquals("one", map.getEntry(1).getValue());
    	assertEquals("one", map.get(1));
        assertTrue(map.containsKey(0));
        assertTrue(map.containsValue("zero-1"));
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
    	
    	key = map.put(0, "zero-2");
    	assertEquals("zero-1", key);
    	assertEquals(2, map.size());
    	assertFalse(map.isEmpty());
    	assertEquals("zero-2", map.get(0));
    	assertEquals(0, map.getEntry(0).getKey().intValue());
    	assertEquals("zero-2", map.getEntry(0).getValue());
        assertTrue(map.containsKey(0));
        assertTrue(map.containsValue("zero-2"));
    	assertNull(map.get(3));
    }
    
    public void testSimpleCuckooHashingNoReHash()
    {
    	// put(0, "zero);put(4, "four");
    	CuckooHashMap<Integer, String> map = new CuckooHashMap<Integer, String>(4);
    	assertNull(map.put(0, "zero-1"));
    	assertEquals(1, map.size());
    	assertEquals("zero-1", map.get(0));
    	assertEquals(0, map.getEntry(0).getKey().intValue());
    	assertEquals("zero-1", map.getEntry(0).getValue());
        assertTrue(map.containsKey(0));
        assertTrue(map.containsValue("zero-1"));
    	
    	assertNull(map.put(4, "four"));
    	assertEquals(2, map.size());
    	assertEquals(4, map.getEntry(0).getKey().intValue());
    	assertEquals("four", map.getEntry(0).getValue());
    	assertEquals(0, map.getEntry(3).getKey().intValue());
    	assertEquals("zero-1", map.getEntry(3).getValue());
    	assertNull(map.getEntry(1));
    	assertNull(map.getEntry(2));
    	assertEquals("zero-1", map.get(0));
    	assertEquals("four", map.get(4));
        assertTrue(map.containsKey(4));
        assertTrue(map.containsValue("four"));
        
        assertEquals("zero-1", map.put(0, "zero-2"));
    	assertEquals(2, map.size());
    	assertEquals("zero-2", map.get(0));
    	assertEquals(4, map.getEntry(0).getKey().intValue());
    	assertEquals("four", map.getEntry(0).getValue());
    	assertEquals(0, map.getEntry(3).getKey().intValue());
    	assertEquals("zero-2", map.getEntry(3).getValue());
    	assertNull(map.getEntry(1));
    	assertNull(map.getEntry(2));
    	assertEquals("four", map.get(4));
    	assertTrue(map.containsKey(0));
        assertTrue(map.containsKey(4));
        assertTrue(map.containsValue("four"));
        assertFalse(map.containsValue("zero-1"));
        assertTrue(map.containsValue("zero-2"));
        
    	assertNull(map.put(1, "one"));
    	assertEquals(3, map.size());
    	assertEquals("one", map.get(1));
    	assertEquals(4, map.getEntry(0).getKey().intValue());
    	assertEquals("four", map.getEntry(0).getValue());
    	assertEquals(1, map.getEntry(1).getKey().intValue());
    	assertEquals("four", map.getEntry(0).getValue());
    	assertEquals(0, map.getEntry(3).getKey().intValue());
    	assertEquals("zero-2", map.getEntry(3).getValue());
    	assertNull(map.getEntry(2));
    	assertEquals("four", map.get(4));
    	assertTrue(map.containsKey(0));
        assertTrue(map.containsKey(4));
        assertTrue(map.containsValue("four"));
        assertFalse(map.containsValue("zero-1"));
        assertTrue(map.containsValue("zero-2"));
        
        // Test new cuckoo collision with wrap-around
        map = new CuckooHashMap<Integer, String>(2);
    	assertNull(map.put(0, "zero-1"));
    	assertEquals(1, map.size());
    	assertEquals("zero-1", map.get(0));
    	assertEquals(0, map.getEntry(0).getKey().intValue());
    	assertEquals("zero-1", map.getEntry(0).getValue());
    	assertNull(map.getEntry(1));
        assertTrue(map.containsKey(0));
        assertTrue(map.containsValue("zero-1"));
        
    	assertNull(map.put(6, "six"));
    	assertEquals(2, map.size());
    	assertEquals("zero-1", map.get(0));
    	assertEquals("six", map.get(6));
    	assertEquals("six", map.getEntry(0).getValue());
    	assertEquals(6, map.getEntry(0).getKey().intValue());
    	assertEquals("zero-1", map.getEntry(1).getValue());
    	assertEquals(0, map.getEntry(1).getKey().intValue());
        assertTrue(map.containsKey(0));
        assertTrue(map.containsValue("zero-1"));
        assertTrue(map.containsKey(6));
        assertTrue(map.containsValue("six"));
        
        // replace value at key = 0
        assertEquals("zero-1", map.put(0, "zero-2")); 
        assertEquals(2, map.size());
    	assertEquals("zero-2", map.get(0));
    	assertEquals("six", map.get(6));
    	assertEquals("six", map.getEntry(0).getValue());
    	assertEquals(6, map.getEntry(0).getKey().intValue());
    	assertEquals("zero-2", map.getEntry(1).getValue());
    	assertEquals(0, map.getEntry(1).getKey().intValue());
        assertTrue(map.containsKey(0));
        assertTrue(map.containsValue("zero-2"));
        assertTrue(map.containsKey(6));
        assertTrue(map.containsValue("six"));
        
        // new cuckoo hashmap
        map = new CuckooHashMap<Integer, String>(4);
    	assertNull(map.put(3, "three"));
        assertEquals(1, map.size());
    	assertEquals("three", map.get(3));
    	assertEquals(3, map.getEntry(3).getKey().intValue());
    	assertEquals("three", map.getEntry(3).getValue());
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three"));
        
    	assertNull(map.put(7, "seven"));
        assertEquals(2, map.size());
    	assertEquals("seven", map.get(7));
    	assertEquals("three", map.get(3));
    	assertNull(map.getEntry(0));
    	assertNull(map.getEntry(1));
    	assertEquals("three", map.getEntry(2).getValue());
    	assertEquals(3, map.getEntry(2).getKey().intValue());
    	assertEquals("seven", map.getEntry(3).getValue());
    	assertEquals(7, map.getEntry(3).getKey().intValue());
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three"));
        assertTrue(map.containsKey(7));
        assertTrue(map.containsValue("seven")); 	
    }

    public void testNonSimpleCuckooHashingNoReHash()
    {
    	CuckooHashMap<Integer, String> map = new CuckooHashMap<Integer, String>(5);
    	assertNull(map.put(1, "one"));
        assertEquals(1, map.size());
    	assertEquals("one", map.get(1));
    	assertEquals(1, map.getEntry(1).getKey().intValue());
    	assertEquals("one", map.getEntry(1).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
        
    	assertNull(map.put(3, "three"));
        assertEquals(2, map.size());
    	assertEquals("one", map.get(1));
    	assertEquals("three", map.get(3));
    	assertEquals(1, map.getEntry(1).getKey().intValue());
    	assertEquals("one", map.getEntry(1).getValue());
    	assertEquals(3, map.getEntry(3).getKey().intValue());
    	assertEquals("three", map.getEntry(3).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three"));
        
    	assertNull(map.put(8, "eight"));
        assertEquals(3, map.size());
    	assertEquals("one", map.get(1));
    	assertEquals("three", map.get(3));
    	assertEquals("eight", map.get(8));
    	assertEquals(3, map.getEntry(1).getKey().intValue());
    	assertEquals("three", map.getEntry(1).getValue());
    	assertEquals(8, map.getEntry(3).getKey().intValue());
    	assertEquals("eight", map.getEntry(3).getValue());
    	assertEquals(1, map.getEntry(4).getKey().intValue());
    	assertEquals("one", map.getEntry(4).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three"));
        assertTrue(map.containsKey(8));
        assertTrue(map.containsValue("eight"));   
    }
    
    public void testNonSimpleCuckooHashingReHash1()
    {
    	CuckooHashMap<Integer, String> map = new CuckooHashMap<Integer, String>(5);
    	
    	assertNull(map.put(1, "one"));
        assertEquals(1, map.size());
    	assertEquals("one", map.get(1));
    	assertEquals(1, map.getEntry(1).getKey().intValue());
    	assertEquals("one", map.getEntry(1).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
        
    	assertNull(map.put(3, "three"));
        assertEquals(2, map.size());
    	assertEquals("one", map.get(1));
    	assertEquals("three", map.get(3));
    	assertEquals(1, map.getEntry(1).getKey().intValue());
    	assertEquals("one", map.getEntry(1).getValue());
    	assertEquals(3, map.getEntry(3).getKey().intValue());
    	assertEquals("three", map.getEntry(3).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three"));
        
    	assertNull(map.put(8, "eight"));
        assertEquals(3, map.size());
    	assertEquals("one", map.get(1));
    	assertEquals("three", map.get(3));
    	assertEquals("eight", map.get(8));
    	assertEquals(3, map.getEntry(1).getKey().intValue());
    	assertEquals("three", map.getEntry(1).getValue());
    	assertEquals(8, map.getEntry(3).getKey().intValue());
    	assertEquals("eight", map.getEntry(3).getValue());
    	assertEquals(1, map.getEntry(4).getKey().intValue());
    	assertEquals("one", map.getEntry(4).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three"));
        assertTrue(map.containsKey(8));
        assertTrue(map.containsValue("eight"));  
        
        // rehash occurs here
        assertNull(map.put(4, "four"));
        assertEquals(4, map.size());
    	assertEquals("one", map.get(1));
    	assertEquals("three", map.get(3));
    	assertEquals("four", map.get(4));
    	assertEquals("eight", map.get(8));
    	assertEquals(1, map.getEntry(1).getKey().intValue());
    	assertEquals("one", map.getEntry(1).getValue());
    	assertEquals(3, map.getEntry(3).getKey().intValue());
    	assertEquals("three", map.getEntry(3).getValue());
    	assertEquals(4, map.getEntry(4).getKey().intValue());
    	assertEquals("four", map.getEntry(4).getValue());
    	assertEquals(8, map.getEntry(8).getKey().intValue());
    	assertEquals("eight", map.getEntry(8).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three"));
        assertTrue(map.containsKey(4));
        assertTrue(map.containsValue("four"));
        assertTrue(map.containsKey(8));
        assertTrue(map.containsValue("eight"));  
        
        assertNull(map.put(6, "six"));
        assertEquals(5, map.size());
    	assertEquals("one", map.get(1));
    	assertEquals("three", map.get(3));
    	assertEquals("four", map.get(4));
    	assertEquals("six", map.get(6));
    	assertEquals("eight", map.get(8));
    	assertEquals(1, map.getEntry(1).getKey().intValue());
    	assertEquals("one", map.getEntry(1).getValue());
    	assertEquals(3, map.getEntry(3).getKey().intValue());
    	assertEquals("three", map.getEntry(3).getValue());
    	assertEquals(4, map.getEntry(4).getKey().intValue());
    	assertEquals("four", map.getEntry(4).getValue());
    	assertEquals(6, map.getEntry(6).getKey().intValue());
    	assertEquals("six", map.getEntry(6).getValue());
    	assertEquals(8, map.getEntry(8).getKey().intValue());
    	assertEquals("eight", map.getEntry(8).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three"));
        assertTrue(map.containsKey(4));
        assertTrue(map.containsValue("four"));
        assertTrue(map.containsKey(6));
        assertTrue(map.containsValue("six"));
        assertTrue(map.containsKey(8));
        assertTrue(map.containsValue("eight"));  
    }
    
    public void testNonSimpleCuckooHashingReHash2()
    {
    	CuckooHashMap<Integer, String> map = new CuckooHashMap<Integer, String>(5);
    	assertNull(map.put(1, "one"));
        assertEquals(1, map.size());
    	assertEquals("one", map.get(1));
    	assertEquals(1, map.getEntry(1).getKey().intValue());
    	assertEquals("one", map.getEntry(1).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
        
    	assertNull(map.put(3, "three"));
        assertEquals(2, map.size());
    	assertEquals("one", map.get(1));
    	assertEquals("three", map.get(3));
    	assertEquals(1, map.getEntry(1).getKey().intValue());
    	assertEquals("one", map.getEntry(1).getValue());
    	assertEquals(3, map.getEntry(3).getKey().intValue());
    	assertEquals("three", map.getEntry(3).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three"));
        
    	assertNull(map.put(8, "eight"));
        assertEquals(3, map.size());
    	assertEquals("one", map.get(1));
    	assertEquals("three", map.get(3));
    	assertEquals("eight", map.get(8));
    	assertEquals(3, map.getEntry(1).getKey().intValue());
    	assertEquals("three", map.getEntry(1).getValue());
    	assertEquals(8, map.getEntry(3).getKey().intValue());
    	assertEquals("eight", map.getEntry(3).getValue());
    	assertEquals(1, map.getEntry(4).getKey().intValue());
    	assertEquals("one", map.getEntry(4).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three"));
        assertTrue(map.containsKey(8));
        assertTrue(map.containsValue("eight"));  
        
        // rehash occurs here
        assertNull(map.put(4, "four"));
        assertEquals(4, map.size());
    	assertEquals("one", map.get(1));
    	assertEquals("three", map.get(3));
    	assertEquals("four", map.get(4));
    	assertEquals("eight", map.get(8));
    	
    	assertEquals(1, map.getEntry(1).getKey().intValue());
    	assertEquals("one", map.getEntry(1).getValue());
    	assertEquals(3, map.getEntry(3).getKey().intValue());
    	assertEquals("three", map.getEntry(3).getValue());
    	assertEquals(4, map.getEntry(4).getKey().intValue());
    	assertEquals("four", map.getEntry(4).getValue());
    	assertEquals(8, map.getEntry(8).getKey().intValue());
    	assertEquals("eight", map.getEntry(8).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three"));
        assertTrue(map.containsKey(4));
        assertTrue(map.containsValue("four"));
        assertTrue(map.containsKey(8));
        assertTrue(map.containsValue("eight"));  
        
        assertNull(map.put(5, "five"));
        assertEquals(5, map.size());
    	assertEquals("one", map.get(1));
    	assertEquals("three", map.get(3));
    	assertEquals("four", map.get(4));
    	assertEquals("five", map.get(5));
    	assertEquals("eight", map.get(8));
    	assertEquals(1, map.getEntry(1).getKey().intValue());
    	assertEquals("one", map.getEntry(1).getValue());
    	assertEquals(3, map.getEntry(3).getKey().intValue());
    	assertEquals("three", map.getEntry(3).getValue());
    	assertEquals(4, map.getEntry(4).getKey().intValue());
    	assertEquals("four", map.getEntry(4).getValue());
    	assertEquals(5, map.getEntry(5).getKey().intValue());
    	assertEquals("five", map.getEntry(5).getValue());
    	assertEquals(8, map.getEntry(8).getKey().intValue());
    	assertEquals("eight", map.getEntry(8).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three"));
        assertTrue(map.containsKey(4));
        assertTrue(map.containsValue("four"));
        assertTrue(map.containsKey(5));
        assertTrue(map.containsValue("five"));
        assertTrue(map.containsKey(8));
        assertTrue(map.containsValue("eight"));  
        
        assertNull(map.put(11, "eleven"));
        assertEquals(6, map.size());
    	assertEquals("one", map.get(1));
    	assertEquals("three", map.get(3));
    	assertEquals("four", map.get(4));
    	assertEquals("five", map.get(5));
    	assertEquals("eight", map.get(8));
    	assertEquals("eleven", map.get(11));
    	assertEquals(11, map.getEntry(1).getKey().intValue());
    	assertEquals("eleven", map.getEntry(1).getValue());
    	assertEquals(3, map.getEntry(3).getKey().intValue());
    	assertEquals("three", map.getEntry(3).getValue());
    	assertEquals(1, map.getEntry(4).getKey().intValue());
    	assertEquals("one", map.getEntry(4).getValue());
    	assertEquals(5, map.getEntry(5).getKey().intValue());
    	assertEquals("five", map.getEntry(5).getValue());
    	assertEquals(4, map.getEntry(7).getKey().intValue());
    	assertEquals("four", map.getEntry(7).getValue());
    	assertEquals(8, map.getEntry(8).getKey().intValue());
    	assertEquals("eight", map.getEntry(8).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three"));
        assertTrue(map.containsKey(4));
        assertTrue(map.containsValue("four"));
        assertTrue(map.containsKey(5));
        assertTrue(map.containsValue("five"));
        assertTrue(map.containsKey(8));
        assertTrue(map.containsValue("eight"));  
        assertTrue(map.containsKey(11));
        assertTrue(map.containsValue("eleven"));  
    }
    
    public void testNonSimpleCuckooHashingReHash3()
    {
    	CuckooHashMap<Integer, String> map = new CuckooHashMap<Integer, String>(5);
    	assertNull(map.put(1, "one-1"));
        assertEquals(1, map.size());
    	assertEquals("one-1", map.get(1));
    	assertEquals(1, map.getEntry(1).getKey().intValue());
    	assertEquals("one-1", map.getEntry(1).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one-1"));
        
    	assertNull(map.put(3, "three"));
        assertEquals(2, map.size());
    	assertEquals("one-1", map.get(1));
    	assertEquals("three", map.get(3));
    	assertEquals(1, map.getEntry(1).getKey().intValue());
    	assertEquals("one-1", map.getEntry(1).getValue());
    	assertEquals(3, map.getEntry(3).getKey().intValue());
    	assertEquals("three", map.getEntry(3).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one-1"));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three"));
        
    	assertNull(map.put(8, "eight"));
        assertEquals(3, map.size());
    	assertEquals("one-1", map.get(1));
    	assertEquals("three", map.get(3));
    	assertEquals("eight", map.get(8));
    	assertEquals(3, map.getEntry(1).getKey().intValue());
    	assertEquals("three", map.getEntry(1).getValue());
    	assertEquals(8, map.getEntry(3).getKey().intValue());
    	assertEquals("eight", map.getEntry(3).getValue());
    	assertEquals(1, map.getEntry(4).getKey().intValue());
    	assertEquals("one-1", map.getEntry(4).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one-1"));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three"));
        assertTrue(map.containsKey(8));
        assertTrue(map.containsValue("eight"));  
        
        // rehash occurs here
        assertNull(map.put(6, "six"));
        assertEquals(4, map.size());
    	assertEquals("one-1", map.get(1));
    	assertEquals("three", map.get(3));
    	assertEquals("six", map.get(6));
    	assertEquals("eight", map.get(8));
    	assertEquals(1, map.getEntry(1).getKey().intValue());
    	assertEquals("one-1", map.getEntry(1).getValue());
    	assertEquals(3, map.getEntry(3).getKey().intValue());
    	assertEquals("three", map.getEntry(3).getValue());
    	assertEquals(6, map.getEntry(6).getKey().intValue());
    	assertEquals("six", map.getEntry(6).getValue());
    	assertEquals(8, map.getEntry(8).getKey().intValue());
    	assertEquals("eight", map.getEntry(8).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one-1"));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three"));
        assertTrue(map.containsKey(6));
        assertTrue(map.containsValue("six"));
        assertTrue(map.containsKey(8));
        assertTrue(map.containsValue("eight")); 
        
        // rehash occurs here
        assertEquals("one-1", map.put(1, "one-2"));
        assertEquals(4, map.size());
    	assertEquals("one-2", map.get(1));
    	assertEquals("three", map.get(3));
    	assertEquals("six", map.get(6));
    	assertEquals("eight", map.get(8));
    	assertEquals(1, map.getEntry(1).getKey().intValue());
    	assertEquals("one-2", map.getEntry(1).getValue());
    	assertEquals(3, map.getEntry(3).getKey().intValue());
    	assertEquals("three", map.getEntry(3).getValue());
    	assertEquals(6, map.getEntry(6).getKey().intValue());
    	assertEquals("six", map.getEntry(6).getValue());
    	assertEquals(8, map.getEntry(8).getKey().intValue());
    	assertEquals("eight", map.getEntry(8).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one-2"));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three"));
        assertTrue(map.containsKey(6));
        assertTrue(map.containsValue("six"));
        assertTrue(map.containsKey(8));
        assertTrue(map.containsValue("eight")); 
        assertFalse(map.containsValue("one-1"));
    }
    
    public void testRemove()
    {
    	CuckooHashMap<Integer, String> map = new CuckooHashMap<Integer, String>(1);
    	
    	assertNull(map.put(0, "zero"));
        assertEquals(1, map.size());
    	assertEquals("zero", map.get(0));
    	assertEquals(0, map.getEntry(0).getKey().intValue());
    	assertEquals("zero", map.getEntry(0).getValue());
        assertTrue(map.containsKey(0));
        assertTrue(map.containsValue("zero"));
        
        assertEquals("zero", map.remove(0));
        assertEquals(0, map.size());
        assertNull(map.get(0));
    	assertNull(map.getEntry(0));
    	assertFalse(map.containsKey(0));
        assertFalse(map.containsValue("zero"));
        
        // new map
        map = new CuckooHashMap<Integer, String>(2);
    	assertNull(map.put(0, "zero"));
    	assertNull(map.put(2, "two"));
        assertEquals(2, map.size());
    	assertEquals(2, map.getEntry(0).getKey().intValue());
    	assertEquals("two", map.getEntry(0).getValue());
    	assertEquals(0, map.getEntry(1).getKey().intValue());
    	assertEquals("zero", map.getEntry(1).getValue());
    	
        assertEquals("zero", map.remove(0));
        assertEquals(1, map.size());
        assertNull(map.get(0));
    	assertNull(map.getEntry(1));
    	assertFalse(map.containsKey(0));    	
        assertFalse(map.containsValue("zero"));
    	assertEquals(2, map.getEntry(0).getKey().intValue());
    	assertEquals("two", map.getEntry(0).getValue());
    	
        assertEquals("two", map.remove(2));
        assertEquals(0, map.size());
        assertNull(map.get(0));
        assertNull(map.get(2));
    	assertNull(map.getEntry(0));
    	assertNull(map.getEntry(1));
    	assertFalse(map.containsKey(0));
    	assertFalse(map.containsKey(2));
        assertFalse(map.containsValue("zero"));
        assertFalse(map.containsValue("two"));
        
        // new map
    	map = new CuckooHashMap<Integer, String>(5);
    	assertNull(map.put(1, "one"));
        assertEquals(1, map.size());
    	assertEquals("one", map.get(1));
    	assertEquals(1, map.getEntry(1).getKey().intValue());
    	assertEquals("one", map.getEntry(1).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
        
    	assertNull(map.put(3, "three"));
        assertEquals(2, map.size());
    	assertEquals("one", map.get(1));
    	assertEquals("three", map.get(3));
    	assertEquals(1, map.getEntry(1).getKey().intValue());
    	assertEquals("one", map.getEntry(1).getValue());
    	assertEquals(3, map.getEntry(3).getKey().intValue());
    	assertEquals("three", map.getEntry(3).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three"));
        
    	assertNull(map.put(8, "eight"));
        assertEquals(3, map.size());
    	assertEquals("one", map.get(1));
    	assertEquals("three", map.get(3));
    	assertEquals("eight", map.get(8));
    	assertEquals(3, map.getEntry(1).getKey().intValue());
    	assertEquals("three", map.getEntry(1).getValue());
    	assertEquals(8, map.getEntry(3).getKey().intValue());
    	assertEquals("eight", map.getEntry(3).getValue());
    	assertEquals(1, map.getEntry(4).getKey().intValue());
    	assertEquals("one", map.getEntry(4).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three"));
        assertTrue(map.containsKey(8));
        assertTrue(map.containsValue("eight"));  
        
    	assertEquals("one", map.remove(1));
        assertEquals(2, map.size());
    	assertNull(map.get(1));
    	assertEquals("three", map.get(3));
    	assertEquals("eight", map.get(8));
    	assertEquals(3, map.getEntry(1).getKey().intValue());
    	assertEquals("three", map.getEntry(1).getValue());
    	assertEquals(8, map.getEntry(3).getKey().intValue());
    	assertEquals("eight", map.getEntry(3).getValue());
    	assertNull(map.getEntry(4));
        assertFalse(map.containsKey(1));
        assertFalse(map.containsValue("one"));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three"));
        assertTrue(map.containsKey(8));
        assertTrue(map.containsValue("eight"));
    }
    
    public void testPutAllSimple()
    {
    	HashMap<Integer, String> hashmap = new HashMap<Integer, String> ();
    	hashmap.put(0, "zero");
    	hashmap.put(1, "one");
    	hashmap.put(2, "two");
    	hashmap.put(3, "three-1");
    	hashmap.put(4, "four");
    	
    	CuckooHashMap<Integer, String> map = new CuckooHashMap<Integer, String>(5);
    	assertEquals(0, map.size());
    	assertTrue(map.isEmpty());
    	
    	map.putAll(hashmap);
    	
    	assertFalse(map.isEmpty());
    	assertEquals(5, map.size());
    	
    	assertEquals("zero", map.get(0));
    	assertEquals("one", map.get(1));
    	assertEquals("two", map.get(2));
    	assertEquals("three-1", map.get(3));
    	assertEquals("four", map.get(4));
    	
    	assertEquals(0, map.getEntry(0).getKey().intValue());
    	assertEquals("zero", map.getEntry(0).getValue());
    	assertEquals(1, map.getEntry(1).getKey().intValue());
    	assertEquals("one", map.getEntry(1).getValue());
    	assertEquals(2, map.getEntry(2).getKey().intValue());
    	assertEquals("two", map.getEntry(2).getValue());
    	assertEquals(3, map.getEntry(3).getKey().intValue());
    	assertEquals("three-1", map.getEntry(3).getValue());
    	assertEquals(4, map.getEntry(4).getKey().intValue());
    	assertEquals("four", map.getEntry(4).getValue());
        assertTrue(map.containsKey(0));
        assertTrue(map.containsValue("zero"));
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
        assertTrue(map.containsKey(2));
        assertTrue(map.containsValue("two"));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three-1"));
        assertTrue(map.containsKey(4));
        assertTrue(map.containsValue("four"));  
        
        hashmap = new HashMap<Integer, String> ();
    	hashmap.put(3, "three-2");
    	map.putAll(hashmap);
    	assertEquals(5, map.size());
    	
    	assertEquals("zero", map.get(0));
    	assertEquals("one", map.get(1));
    	assertEquals("two", map.get(2));
    	assertEquals("three-2", map.get(3));
    	assertEquals("four", map.get(4));
    	
    	assertEquals(0, map.getEntry(0).getKey().intValue());
    	assertEquals("zero", map.getEntry(0).getValue());
    	assertEquals(1, map.getEntry(1).getKey().intValue());
    	assertEquals("one", map.getEntry(1).getValue());
    	assertEquals(2, map.getEntry(2).getKey().intValue());
    	assertEquals("two", map.getEntry(2).getValue());
    	assertEquals(3, map.getEntry(3).getKey().intValue());
    	assertEquals("three-2", map.getEntry(3).getValue());
    	assertEquals(4, map.getEntry(4).getKey().intValue());
    	assertEquals("four", map.getEntry(4).getValue());
        assertTrue(map.containsKey(0));
        assertTrue(map.containsValue("zero"));
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
        assertTrue(map.containsKey(2));
        assertTrue(map.containsValue("two"));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three-2"));
        assertTrue(map.containsKey(4));
        assertTrue(map.containsValue("four")); 
    }
  
    public void testPutAllNonSimple()
    {
    	HashMap<Integer, String> hashmap = new HashMap<Integer, String> ();
    	hashmap.put(1, "one");
    	hashmap.put(3, "three");
    	hashmap.put(8, "eight");
    	hashmap.put(4, "four");
    	hashmap.put(5, "five");	  
    	hashmap.put(11, "eleven");
    	
    	CuckooHashMap<Integer, String> map = new CuckooHashMap<Integer, String>(5);
    	map.putAll(hashmap); 
        
    	assertEquals("one", map.get(1));
    	assertEquals("three", map.get(3));
    	assertEquals("four", map.get(4));
    	assertEquals("five", map.get(5));
    	assertEquals("eight", map.get(8));
    	assertEquals("eleven", map.get(11));
    	assertEquals(11, map.getEntry(1).getKey().intValue());
    	assertEquals("eleven", map.getEntry(1).getValue());   
    	assertEquals(3, map.getEntry(3).getKey().intValue());
    	assertEquals("three", map.getEntry(3).getValue());
    	assertEquals(1, map.getEntry(4).getKey().intValue());
    	assertEquals("one", map.getEntry(4).getValue());
    	assertEquals(5, map.getEntry(5).getKey().intValue());
    	assertEquals("five", map.getEntry(5).getValue());
    	assertEquals(4, map.getEntry(7).getKey().intValue());
    	assertEquals("four", map.getEntry(7).getValue());
    	assertEquals(8, map.getEntry(8).getKey().intValue());
    	assertEquals("eight", map.getEntry(8).getValue());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("one"));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsValue("three"));
        assertTrue(map.containsKey(4));
        assertTrue(map.containsValue("four"));
        assertTrue(map.containsKey(5));
        assertTrue(map.containsValue("five"));
        assertTrue(map.containsKey(8));
        assertTrue(map.containsValue("eight"));  
        assertTrue(map.containsKey(11));
        assertTrue(map.containsValue("eleven"));  
    }
    
    /*
     * CuckooHashMap does not all null keys or values
     */
    public void testNullInput()
    {
    	CuckooHashMap<String, Integer> map = new CuckooHashMap<String, Integer>(1);
    	
    	boolean flag = false;
    	try
    	{
    		map.get(null);
    	}
    	catch (NullPointerException e)
    	{
    		flag = true;
    	}
    	assertTrue(flag);
    	
    	flag = false;
    	try
    	{
    		map.containsKey(null);
    	}
    	catch (NullPointerException e)
    	{
    		flag = true;
    	}
    	assertTrue(flag);
    	
    	flag = false;
    	try
    	{
    		map.containsValue(null);
    	}
    	catch (NullPointerException e)
    	{
    		flag = true;
    	}
    	assertTrue(flag);
    	
    	flag = false;
    	try
    	{
    		map.put(null, 1);
    	}
    	catch (NullPointerException e)
    	{
    		flag = true;
    	}
    	assertTrue(flag);
    	
    	flag = false;
    	try
    	{
    		map.put("test", null);
    	}
    	catch (NullPointerException e)
    	{
    		flag = true;
    	}
    	assertTrue(flag);
    	
    	flag = false;
    	try
    	{
    		map.remove(null);
    	}
    	catch (NullPointerException e)
    	{
    		flag = true;
    	}
    	assertTrue(flag);
    	
    	flag = false;
    	try
    	{
    		map.putAll(null);
    	}
    	catch (NullPointerException e)
    	{
    		flag = true;
    	}
    	assertTrue(flag);
    }
}
