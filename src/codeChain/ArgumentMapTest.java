package codeChain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ArgumentMapTest {

	ArgumentMap map;
	Integer i;
	String s;
	@Before
	public void setUp() throws Exception {
		map = new ArgumentMap();
		i = new Integer(5);
		s = new String("abc");
	}

	@Test
	public void testPut() {

		map.put(i);
		assertEquals(i, map.get(Integer.class));
	}
	
	@Test
	public void testGet() {

		map.put(s);
		assertEquals(s, map.get(String.class));
	}
	
	@Test
	public void testValues() {
		map.put(i);
		map.put(s);
		assertEquals(i, map.values().toArray()[0]);
		assertEquals(s, map.values().toArray()[1]);
	}

}
