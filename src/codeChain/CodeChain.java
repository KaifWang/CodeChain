package codeChain;

import java.util.LinkedList;

public class CodeChain {
	private LinkedList<Block> blockList = new LinkedList<Block>();
	
	public void add(Block block) {
		blockList.add(block);
	}
}
