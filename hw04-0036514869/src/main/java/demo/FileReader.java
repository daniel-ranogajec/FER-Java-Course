package demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

public class FileReader {

	public String[] readFile(String name) throws IOException {

		ArrayIndexedCollection<String> list = new ArrayIndexedCollection<>();
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(("examples/" + name + ".txt"));
		Scanner sc = new Scanner(is);
		
		while(sc.hasNextLine())  {  
			list.add(sc.nextLine());
		}  
		
		sc.close();
		
		Object[] elems = list.toArray();
		String[] s = new String[elems.length];
		for (int i = 0; i < elems.length; i++) 
			s[i] = elems[i].toString();
		
		return s;
	}
}
