package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Support for working with complex polynomials
 * 
 * @author Daniel_Ranogajec
 *
 */
public class ComplexPolynomial {

	private List<Complex> factors;
	
	/**
	 * Creates new ComplexPolynomial with given Complex array 
	 * @param factors array of Complex numbers
	 */
	public ComplexPolynomial(Complex ...factors) {
		if (factors == null)
			throw new NullPointerException();
		
		if (factors.length == 0)
			throw new IllegalArgumentException();
		
		this.factors = new ArrayList<>();
		this.factors.addAll(Arrays.asList(factors));
	}

	/**
	 * Returns order of this polynom
	 * @return order
	 */
	public short order() {
		return (short) (factors.size() - 1);
	}

	/**
	 * Method that multiplies this with other ComplexPolynomial
	 * @param p ComplexPolynomial that you want to multiply with this ComplexPolynomial
	 * @return new ComplexPolynomial
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		if (p == null)
			throw new NullPointerException();
		
		Complex[] newFactors = new Complex[this.factors.size() + p.factors.size() - 1];
		for (int i = 0; i < newFactors.length; i++)
			newFactors[i] = Complex.ZERO;
		
		for(int i = 0; i < factors.size(); i++) {
			for (int j = 0; j < p.factors.size(); j++) {					
				newFactors[i+j] = newFactors[i+j].add(this.factors.get(i).multiply(p.factors.get(j)));
			}
		}
		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Method that computes first derivative of this polynomial
	 * @return first derivative of this polyonimal as new ComplexPolynomial
	 */
	public ComplexPolynomial derive() {
		if (this.factors.size() == 1) 
			return new ComplexPolynomial(Complex.ZERO);
		
		Complex[] newFactors = new Complex[this.factors.size() - 1];
		for (int i = order(); i > 0; i--) 
			newFactors[i-1] = factors.get(i).multiply(new Complex(i,0));

		return new ComplexPolynomial(newFactors);
	}
	
	/**
	 * Method that computes polynomial value at given point z
	 * @param z Complex number
	 * @return new Complex
	 */
	public Complex apply(Complex z) {
		if (z == null)
			throw new NullPointerException();
		
		Complex num = Complex.ZERO;
		for (int i = order(); i >= 0; i--) 
			num = num.add(z.power(i).multiply(factors.get(i)));
		return num;
	}
	
	@Override
	public String toString() {
		String s = "";
		for (int i = order(); i >= 0; i--) {
			if (i != 0)
				s += "(" + this.factors.get(i) + ")*z^" + i + "+";
			else
				s += "(" + this.factors.get(i) + ")";
		}
		return s;
	}
}
