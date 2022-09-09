package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Support for working with complex rooted polynomials
 * 
 * @author Daniel_Ranogajec
 *
 */
public class ComplexRootedPolynomial {
	
	private Complex constant;
	private List<Complex> roots;
	
	/**
	 * Creates new ComplexRootedPolynomial with given Complex constant and Complex array for root numbers 
	 * @param factors array of Complex numbers
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		if (constant == null || roots == null)
			throw new NullPointerException();
		
		this.constant = constant;
		this.roots = new ArrayList<>();
		for (Complex complex : roots) {
			this.roots.add(complex);
		}
	}
	
	/**
	 * Method that computes polynomial value at given point z
	 * @param z Complex number
	 * @return new Complex
	 */
	public Complex apply(Complex z) {
		Complex num = constant;
		for (Complex complex : roots) {
			num = num.multiply(z.sub(complex));
		}
		return num;
	}
	
	/**
	 * Method that converts this representation to ComplexPolynomial type
	 * @return new ComplexPolynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial polynomial = new ComplexPolynomial(this.constant);
		for (Complex complex : roots) {
			polynomial = polynomial.multiply(new ComplexPolynomial(complex.negate(), Complex.ONE));	// (-complex + x)
		}
		return polynomial;
	}

	/**
	 * Method that finds index of closest root for given complex nuber z that is within treshold
	 * @param z Complex number
	 * @param treshold as double
	 * @return index of closest root, -1 if there is no such root
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		if (z == null)
			throw new NullPointerException();
		
		double minTreshold = treshold;
		for (Complex complex : roots) {
			if (z.sub(complex).module() < minTreshold) {
				minTreshold = z.sub(complex).module();
			}
		}
		for (int i = 0; i < roots.size(); i++)
			if (z.sub(roots.get(i)).module() == minTreshold)
				return i;
		
		return -1;
	}
	
	@Override
	public String toString() {
		String s = "(" + constant + ")";
		for (int i = 0; i < roots.size(); i++) {
			s += "*(z-(" + roots.get(i) +"))";
		}
		return s;
	}
}
