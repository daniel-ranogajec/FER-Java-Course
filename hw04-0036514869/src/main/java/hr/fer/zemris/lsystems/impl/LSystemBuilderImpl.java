package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Arrays;
import java.util.stream.Collectors;

import hr.fer.oprpp1.custom.collections.Dictionary;
import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.*;

/**
 * Class that implements LSystemBuilder used for building drawing given patterns
 * 
 * @author Daniel_Ranogajec
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder{

	private Dictionary<Character, String> productions = new Dictionary<>();
	private Dictionary<Character, Command> commands = new Dictionary<>();
	private double unitLength = 0.1;
	private double unitLengthDegreeScaler = 1;
	private Vector2D origin = new Vector2D(0, 0);
	private double angle = 0;
	private String axiom = "";
	private static final Color defaultColor = Color.black;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public LSystem build() {
		return new LSystemNested();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LSystemBuilder configureFromText(String[] data) {
		for (String s : data) {
			String[] spt = s.split("\\s+");
			if (spt.length <= 1) {
				continue;
				
			} else if (spt[0].equalsIgnoreCase("origin")) {
				setOrigin(Double.parseDouble(spt[1]), Double.parseDouble(spt[2]));
				
			} else if (spt[0].equalsIgnoreCase("angle")) {
				setAngle(Double.parseDouble(spt[1]));
				
			} else if (spt[0].equalsIgnoreCase("axiom")) {
				setAxiom(spt[1]);
				
			} else if (spt[0].equalsIgnoreCase("unitLength")) {
				setUnitLength(Double.parseDouble(spt[1]));
				
			} else if (spt[0].equalsIgnoreCase("unitLengthDegreeScaler")) {
				String x = Arrays.stream(spt).skip(1).collect(Collectors.joining(""));
				String div[] = x.split("/");
				if (div.length > 1)
					setUnitLengthDegreeScaler(Double.parseDouble(div[0])/Double.parseDouble(div[1]));
				else
					setUnitLengthDegreeScaler(Double.parseDouble(div[0]));
				
			} else if (spt[0].equalsIgnoreCase("command")) {
				String x = Arrays.stream(spt).skip(2).collect(Collectors.joining(" "));
				registerCommand(spt[1].charAt(0), x);
				
			} else if (spt[0].equalsIgnoreCase("production")) {
				String x = Arrays.stream(spt).skip(2).collect(Collectors.joining(""));
				registerProduction(spt[1].charAt(0), x);
			} 
		}
		
		
		return this;
	}

	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if command is unknown
	 */
	@Override
	public LSystemBuilder registerCommand(char key, String value) {		
		commands.put(key, getCommand(value));
		return this;
	}

	/**
	 * Method used for parsing commands
	 * @param value 
	 * @return new Command
	 */
	private Command getCommand(String value) {
		String[] com = value.split(" ");
		if (com[0].equalsIgnoreCase("color")) {
			return new ColorCommand(Color.decode("#" + com[1]));
		} else if (com[0].equalsIgnoreCase("draw")) {
			return new DrawCommand(Double.parseDouble(com[1]));
		} else if (com[0].equalsIgnoreCase("rotate")) {
			return new RotateCommand(Double.parseDouble(com[1]));
		} else if (com[0].equalsIgnoreCase("scale")) {
			return new ScaleCommand(Double.parseDouble(com[1]));
		} else if (com[0].equalsIgnoreCase("skip")) {
			return new SkipCommand(Double.parseDouble(com[1]));
		} else if (com[0].equalsIgnoreCase("pop")) {
			return new PopCommand();
		} else if (com[0].equalsIgnoreCase("push")) {
			return new PushCommand();
		}
		throw new IllegalArgumentException("Illegal command used.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LSystemBuilder registerProduction(char key, String value) {
		productions.put(key, value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}
	
	/**
	 * Nested class used for building LSystem. It has methods for drawing and generating data.
	 * 
	 * @author Daniel_Ranogajec
	 *
	 */
	private class LSystemNested implements LSystem {
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void draw(int level, Painter painter) {
			Context ctx = new Context();
			TurtleState state = new TurtleState(origin.copy(), (new Vector2D(1,0)).rotated(Math.toRadians(angle)), 
					defaultColor, unitLength * Math.pow(unitLengthDegreeScaler, level));
			ctx.pushState(state);
			String s = generate(level);
			for (char c : s.toCharArray()) {
				if (commands.get(c) != null) {
					commands.get(c).execute(ctx, painter);
				}
			}
		
		}	
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String generate(int level) {
			StringBuilder sb = new StringBuilder(axiom);
			for (int i = 0; i < level; i++) {
				int cnt = 0;
				while (cnt < sb.length()) {
				
					String s = productions.get(sb.charAt(cnt));
					if (s != null) {
						sb.replace(cnt, cnt + 1, s);
						cnt += s.length();
					}
					cnt ++;
				}
					
			}
			return sb.toString();
		}
		
	}
	
	
}
