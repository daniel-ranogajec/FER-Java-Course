package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {

	public static void main(String[] args) {
		
		String[] postfix = args[0].split(" ");
		ObjectStack stack = new ObjectStack();
		
		for (String s : postfix) {
			if (checkNumber(s)) {
				stack.push(Integer.parseInt(s));
			} else {
				int x = (int)stack.pop();
				int y = (int)stack.pop();
				int z = 0;
				switch (s) {
				case "+" -> z = y+x;
				case "-" -> z = y-x;
				case "/" -> z = y/x;
				case "*" -> z = y*x;
				case "%" -> z = y%x;
				}
				stack.push(z);
			}
		}
		
		if (stack.size()!=1) 
			throw new Error();
		
		System.out.println(stack.pop());

	}

	private static boolean checkNumber(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}
	
}
