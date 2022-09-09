package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Program that asks user to enter roots and than starts fractal viewer and displays the fractal.
 * User will be asked to write roots until he writes "done".
 * 
 * @author Daniel_Ranogajec
 *
 */
public class Newton {

	public static void main(String[] args) {
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		List<Complex> numbers = new ArrayList<>();
		
		try(Scanner sc = new Scanner(System.in)) {
			while (true) {
				System.out.print("Root " + (numbers.size()+1) + "> ");
				String s = sc.nextLine().toLowerCase().trim();
				if (s.equalsIgnoreCase("done")) {
					if (numbers.size() < 2) {
						System.out.println("Please enter more numbers!");
					} else {
						System.out.println("Image of fractal will appear shortly. Thank you.");
						break;
					}
				} else {
					try {
						Complex cx = Complex.parse(s);
						numbers.add(cx);
					} catch (IllegalArgumentException ex) {
						System.out.println("Invalid number. Please try again!");
					}
				}
			}
		}
		
		Complex[] roots = new Complex[numbers.size()];
		for (int i = 0; i < numbers.size(); i++) 
			roots[i] = numbers.get(i);
		FractalViewer.show(new Producer(new ComplexRootedPolynomial(Complex.ONE, roots)));	
	
	}

	/**
	 * Static class that implements IFractalProducer
	 * 
	 * @author Daniel_Ranogajec
	 *
	 */
	public static class Producer implements IFractalProducer {

		private ComplexRootedPolynomial polynomialRooted;
		private ComplexPolynomial polynomial;
		private static final double convergenceTreshold = 0.001;
		private static final double rootTreshold = 0.001;
		private static final int maxIter = 100;

		/**
		 * Constructor that gets ComplexRootedPolynomial from inputed roots
		 * 
		 * @param polynomial
		 */
		public Producer(ComplexRootedPolynomial polynomial) {
			this.polynomialRooted = polynomial;
			this.polynomial = polynomial.toComplexPolynom();
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {

			int offset = 0;
			short[] data = new short[width * height];

			for(int y = 0; y < height; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					Complex zn = map_to_complex_plain(x, y, width, height, reMin, reMax, imMin, imMax);
					Complex znold;

					int iter = 0;
					do {
						znold = zn;
						zn = zn.sub(polynomial.apply(zn).divide(polynomial.derive().apply(zn)));
						iter++;
					} while(zn.sub(znold).module() > convergenceTreshold && iter < maxIter);
					int index = polynomialRooted.indexOfClosestRootFor(zn, rootTreshold);

					data[offset++] = (short) (index + 1);
				}
			}

			observer.acceptResult(data, (short)(polynomial.order()+1), requestNo);
		}

		/**
		 * Private method used for creating new Complex number
		 * @param x
		 * @param y
		 * @param width
		 * @param height
		 * @param reMin
		 * @param reMax
		 * @param imMin
		 * @param imMax
		 * @return new Complex
		 */
		private Complex map_to_complex_plain(int x, int y, int width, int height, double reMin,
				double reMax, double imMin, double imMax) {
			double cre = x / (width-1.0) * (reMax - reMin) + reMin;
			double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
			return new Complex(cre, cim);
		}
	}
	
}
