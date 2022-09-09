package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Program that asks user to enter roots and than starts fractal viewer and displays the fractal.
 * User can enter in arguments number of workers and tracks like: "--workers=N --tracks=K" or "-w N -t K".
 * User will be asked to write roots until he writes "done".
 * 
 * @author Daniel_Ranogajec
 *
 */
public class NewtonParallel {

	public static void main(String[] args) {

		int numberOfWorkers = Runtime.getRuntime().availableProcessors();
		int numberOfTracks = 4;

		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith("--workers=")) {
				try {
					numberOfWorkers = Integer.parseInt(args[i].substring(10));
				} catch (NumberFormatException ex) {
					System.out.println("Workers initialized wrong. Write in format \"--workers=N\"");
					return;
				}
			} else if (args[i].equalsIgnoreCase("-w")) {
				try {
					numberOfWorkers = Integer.parseInt(args[++i]);
				} catch (NumberFormatException ex) {
					System.out.println("Workers initialized wrong. Write in format \"-w N\"");
					return;

				}
			} else if (args[i].startsWith("--tracks=")) {
				try {
					numberOfTracks = Integer.parseInt(args[i].substring(9));
				} catch (NumberFormatException ex) {
					System.out.println("Tracks initialized wrong. Write in format \"--tracks=K\"");
					return;

				}
			} else if (args[i].equalsIgnoreCase("-t")) {
				try {
					numberOfTracks = Integer.parseInt(args[++i]);
				} catch (NumberFormatException ex) {
					System.out.println("Tracks initialized wrong. Write in format \"-t K\"");
					return;

				}
			} else {
				System.out.println("Unknown input. Please write like \"--workers=N --tracks=K\"");
				return;

			}
		}
		if (numberOfWorkers < 1 || numberOfTracks < 1) {
			System.out.println("Workers and tracks initialized wrongly.");
			return;
		}

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
		FractalViewer.show(new Producer(new ComplexRootedPolynomial(Complex.ONE, roots), numberOfWorkers, numberOfTracks));	
	}
	
	/**
	 * Static class that implements Runnable
	 * 
	 * @author Daniel_Ranogajec
	 *
	 */
	public static class Job implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		public static Job NO_JOB = new Job();

		private ComplexRootedPolynomial polynomialRooted;
		private ComplexPolynomial polynomial;
		private static final double convergenceTreshold = 0.001;
		private static final double rootTreshold = 0.001;
		private static final int maxIter = 100;

		public Job() {}

		public Job(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data, AtomicBoolean cancel, ComplexRootedPolynomial polynomialRooted) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			this.polynomialRooted = polynomialRooted;
			this.polynomial = polynomialRooted.toComplexPolynom();
		}

		@Override
		public void run() {
			int offset = yMin * width;
			for(int y = yMin; y <= yMax; y++) {
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
		 * @return
		 */
		private Complex map_to_complex_plain(int x, int y, int width, int height, double reMin,
				double reMax, double imMin, double imMax) {
			double cre = x / (width-1.0) * (reMax - reMin) + reMin;
			double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
			return new Complex(cre, cim);
		}
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
		private int numberOfWorkers;
		private int numberOfTracks;

		/**
		 * Constructor that gets ComplexRootedPolynomial from inputed roots, number of workers and number of tracks
		 * @param polynomial
		 * @param numberOfWorkers
		 * @param numberOfTracks
		 */
		public Producer(ComplexRootedPolynomial polynomial, int numberOfWorkers, int numberOfTracks) {
			this.polynomialRooted = polynomial;
			this.polynomial = polynomialRooted.toComplexPolynom();
			this.numberOfWorkers = numberOfWorkers;
			this.numberOfTracks = numberOfTracks;
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {

			short[] data = new short[width * height];
			int NumberYTracks = height / numberOfTracks;

			final BlockingQueue<Job> queue = new LinkedBlockingQueue<>();

			Thread[] workers = new Thread[numberOfWorkers];
			for(int i = 0; i < workers.length; i++) {
				workers[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							Job p = null;
							try {
								p = queue.take();
								if(p==Job.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			for(int i = 0; i < workers.length; i++) {
				workers[i].start();
			}

			for(int i = 0; i < numberOfTracks; i++) {
				int yMin = i*NumberYTracks;
				int yMax = (i+1)*NumberYTracks-1;
				if(i==numberOfTracks-1) {
					yMax = height-1;
				}
				Job job = new Job(reMin, reMax, imMin, imMax, width, height, yMin, yMax, polynomial.order()+1, data, cancel, polynomialRooted);
				while(true) {
					try {
						queue.put(job);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for(int i = 0; i < workers.length; i++) {
				while(true) {
					try {
						queue.put(Job.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}

			for(int i = 0; i < workers.length; i++) {
				while(true) {
					try {
						workers[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}

			observer.acceptResult(data, (short)(short)(polynomial.order()+1), requestNo);
		}
	}
}
