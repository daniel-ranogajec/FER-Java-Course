package demo;

import java.io.IOException;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;


public class KochIslandDemo {

	public static void main(String[] args) {
		LSystemViewer.showLSystem(getFile(LSystemBuilderImpl::new));
	}

	private static LSystem getFile(LSystemBuilderProvider provider) {
		FileReader fr = new FileReader();
		try {
			String[] data = fr.readFile("kochIsland");
			return provider.createLSystemBuilder().configureFromText(data).build();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
