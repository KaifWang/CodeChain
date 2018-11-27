package codeChain;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

public class StressTest {

	@Test
	public void stressTest() {
		/*
		 * Initial argument integer 10 and double 5.5
		 * first block add them together and return a double 15.5
		 * second block convert the double 15.5 to string
		 * third block takes no input and does a side effect and produce a result of a boolean value true
		 * fourth block use the string from second block and the integer from the first block
		 * to produce a sentence if the boolean value from the third block is true;
		 * "10 plus 5.5 equals 15.5"
		 */
		CodeChain c = new CodeChain();
		LinkedList<Class<?>> neededType1 = new LinkedList<Class<?>>();
		LinkedList<Class<?>> neededType2 = new LinkedList<Class<?>>();
		LinkedList<Class<?>> neededType3 = new LinkedList<Class<?>>();
		LinkedList<Class<?>> neededType4 = new LinkedList<Class<?>>();
		neededType1.add(Double.class);
		neededType1.add(Integer.class);
		neededType2.add(Double.class);
		neededType4.add(String.class);
		neededType4.add(Integer.class);
		neededType4.add(Boolean.class);
		
		
		c.buildBlock(neededType1, 
				map ->{Integer i = map.get(Integer.class);
					   Double d = map.get(Double.class);
					   Double resultD = i + d;
					   ArgumentMap result = new ArgumentMap();
					   result.put(resultD);
					   return result;}
				);
		
		c.buildBlock(neededType2, 
				map ->{
					   Double d = map.get(Double.class);
					   String s = d.toString();
					   ArgumentMap result = new ArgumentMap();
					   result.put(s);
					   return result;}
				);
		
		c.buildBlock(neededType3, 
				map ->{
					   Boolean b = new Boolean(true);
					   ArgumentMap result = new ArgumentMap();
					   result.put(b);
					   return result;});
		
		c.buildBlock(neededType4, 
				map ->{Integer i = map.get(Integer.class);
					   Boolean b = map.get(Boolean.class);
					   String s = map.get(String.class);
					   String message = new String(i + " plus 5.5 equals " + s);
					   ArgumentMap result = new ArgumentMap();
					   if(b) {
						   result.put(message);
					   }
					   return result;}
				);
		
		ArgumentMap initialArgument = new ArgumentMap();		
		initialArgument.put(new Integer(10));
		initialArgument.put(new Double(5.5));
		
		try {
			assertEquals("10 plus 5.5 equals 15.5",c.execute(initialArgument).get(String.class));
		} catch (ArgumentTypeException e) {
			fail("Unexpected Exception occurred");
		}
	}

}
