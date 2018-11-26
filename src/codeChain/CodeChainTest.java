package codeChain;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class CodeChainTest {
	CodeChain c;
	CodeChain.TestHook codeChainTestHook;
	CodeChain.Block.BlockTestHook blockTestHook1;
	CodeChain.Block.BlockTestHook blockTestHook2;
	@Before
	public void setUp() throws Exception {
		c = new CodeChain();
		codeChainTestHook =  c.new TestHook();
		LinkedList<Class<?>> testBlockNeededType1 = new LinkedList<Class<?>>();
		testBlockNeededType1.add(String.class);
		LinkedList<Class<?>> testBlockNeededType2 = new LinkedList<Class<?>>();
		testBlockNeededType2.add(Integer.class);
		CodeChain.Block testBlock1 = codeChainTestHook.BlockConstructor(testBlockNeededType1, 
			map -> {
				ArgumentMap result = new ArgumentMap();
				String s = map.get(String.class);
				result.put(s);
				return result;}
			);
		CodeChain.Block testBlock2 = codeChainTestHook.BlockConstructor(testBlockNeededType2, 
				map -> {
					ArgumentMap result = new ArgumentMap();
					String s = map.get(String.class);
					result.put(s);
					return result;}
				);
		blockTestHook1 = testBlock1.new BlockTestHook();
		blockTestHook2 = testBlock2.new BlockTestHook();
	}

	@Test
	public void testAddToMap() {
		ArgumentMap input = new ArgumentMap();
		String testString = new String("Test");
		Integer testInteger = new Integer(5);
		input.put(testString);
		input.put(testInteger);
		codeChainTestHook.addToMap(input);
		HashMap<Class<?>, Object> allowableArgument = codeChainTestHook.getAllowableArguments();
		assertEquals(testString, allowableArgument.get(String.class));
		assertEquals(testInteger, allowableArgument.get(Integer.class));
	}
	@Test
	public void testBlockSetArguments() {
		/*
		 * Nominal Case: all needed types exist in the codeChain
		 */
		ArgumentMap input = new ArgumentMap();
		String testString = new String("Test");
		input.put(testString);
		codeChainTestHook.addToMap(input);
		try {
			blockTestHook1.setArguments();
			ArgumentMap result = blockTestHook1.execute();
			assertEquals(testString, result.get(String.class));
		} catch (ArgumentTypeException e) {
			fail("Unexpected Exception Occurred");
		}
		
		/*
		 * Branch Coverage: needed type not in the code chain
		 * expect to throw an exception
		 */
		try {
			blockTestHook2.setArguments();
			fail("Unexpected Exception Occurred");
		} catch (ArgumentTypeException e) {
		}

	}
	
	@Test
	public void testBlockExecute() {
		ArgumentMap input = new ArgumentMap();
		String testString = new String("Test");
		input.put(testString);
		codeChainTestHook.addToMap(input);
		try {
			blockTestHook1.setArguments();
			ArgumentMap result = blockTestHook1.execute();
			assertEquals(testString, result.get(String.class));
		} catch (ArgumentTypeException e) {
			fail("Unexpected Exception Occurred");
		}

	}
	
	@Test
	public void testBuildBlock() {
		LinkedList<Class<?>> testBlockNeededType = new LinkedList<Class<?>>();
		c.buildBlock(testBlockNeededType, 
				map -> {
					ArgumentMap result = new ArgumentMap();
					String s = map.get(String.class);
					Integer i = map.get(Integer.class);
					result.put(s);
					result.put(i);
					return result;}
				);
		assertNotNull(codeChainTestHook.getBlockList().get(0));
	}
	
	@Test
	public void testExecute() {
		
		/*
		 * Nominal Case:All neededTypes exist, Successfully added block
		 */
		ArgumentMap input = new ArgumentMap();
		LinkedList<Class<?>> testBlockNeededType = new LinkedList<Class<?>>();
		testBlockNeededType.add(String.class);
		testBlockNeededType.add(Integer.class);
		String testString = new String("Test");
		Integer testInteger = new Integer(5);
		input.put(testString);
		input.put(testInteger);
		c.buildBlock(testBlockNeededType, 
				map -> {
					ArgumentMap result = new ArgumentMap();
					String s = map.get(String.class);
					Integer i = map.get(Integer.class);
					result.put(s);
					result.put(i);
					return result;}
				);
		try {
			ArgumentMap result;
			result = c.execute(input);
			assertEquals(testString, result.get(String.class));
			assertEquals(testInteger, result.get(Integer.class));
		} catch (ArgumentTypeException e) {
			fail("Unexpected Exception Occurred");
		}

		/*
		 * Branch Coverage: No block in the codeChain, expected to return a null
		 */
		CodeChain c2 = new CodeChain();
		try {
			ArgumentMap result2 = c2.execute(input);
			assertNull(result2);
		} catch (ArgumentTypeException e) {
			fail("Unexpected Exception Occurred");
		}
	}

}
