package hr.fer.zemris.math;

/**
 * Support for working with complex numbers
 * 
 * @author Daniel_Ranogajec
 *
 */
public class Complex {

	public static final Complex ZERO = new Complex(0,0);
	public static final Complex ONE = new Complex(1,0);
	public static final Complex ONE_NEG = new Complex(-1,0);
	public static final Complex IM = new Complex(0,1);
	public static final Complex IM_NEG = new Complex(0,-1);
	
	private final double real;
	private final double imaginary;
	
	/**
	 * Creates new Complex Number
	 * @param real part of complex number
	 * @param imaginary part of complex number
	 */
	public Complex(double re, double im) {
		this.real = re;
		this.imaginary = im;
	}
	
	/**
	 * Creates new Complex Number equal to 0
	 */
	public Complex() {
		this(0,0);
	}
	
	/**
	 * Returns module of this Complex number
	 * @return module as double
	 */
	public double module() {
		return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
	}
	
	/**
	 * Method for multiplying this with other Complex number
	 * @param other complex number you want to multiply to this
	 * @return new ComplexNumber as multiplication of this and other
	 */
	public Complex multiply(Complex other) {
		return new Complex(this.real*other.getReal() - this.imaginary*other.getImaginary(), this.imaginary*other.getReal() + this.real*other.getImaginary());
	}
	
	/**
	 * Method for dividing this with other Complex number
	 * @param other complex number you want to divide from this
	 * @return new ComplexNumber as division of this and other
	 */
	public Complex divide(Complex other) {
		Complex c1 = this.multiply(new Complex(other.getReal(),other.getImaginary() * -1));
		Complex c2 = other.multiply(new Complex(other.getReal(),other.getImaginary() * -1));
		
		return new Complex(c1.getReal()/c2.getReal(), c1.getImaginary()/c2.getReal());
	}
	
	/**
	 * Method for adding this with other Complex number
	 * @param other complex number you want to add to this
	 * @return new ComplexNumber as sum of this and other
	 */
	public Complex add(Complex other) {
		return new Complex(this.real+other.getReal(), this.imaginary+other.getImaginary());
	}

	/**
	 * Method for subtracting this with other Complex number
	 * @param other complex number you want to sub from this
	 * @return new ComplexNumber as subtraction of this and other
	 */
	public Complex sub(Complex other) {
		return new Complex(this.real-other.getReal(), this.imaginary-other.getImaginary());

	}
	
	/**
	 * Method for returning -this
	 * @return negative this Complex number
	 */
	public Complex negate() {
		return new Complex(-this.real, -this.imaginary);
	}
	
	/**
	 * Returns new Complex Number as an n-th power of this ComplexNumber
	 * @param n -th power
	 * @return new ComplexNumber which is n-th power of this ComplexNumber
	 */
	public Complex power(int n)  {
		if (n < 0) 
			throw new IllegalArgumentException("n must be >= 0");
		
		double angle = Math.atan2(this.imaginary, this.real);
		return new Complex(Math.pow(this.module(), n) * Math.cos(angle*n), Math.pow(this.module(),n) * Math.sin(angle*n));
	}
	
	/**
	 * Returns array of Complex Numbers after calculating its n-th root
	 * @param n -th root
	 * @return array of Complex Numbers which are n-th roots of this ComplexNumber
	 */
	public Complex[] root(int n) {
		if (n <= 0) 
			throw new IllegalArgumentException("n must be > 0");
		
		Complex[] c = new Complex[n];
		double r = this.module();
		double angle = Math.atan(imaginary/real);		
		for (int i = 0; i < n; i++) {
			double a = Math.pow(r, 1.0/n);
			Complex num = new Complex(a*Math.cos(angle/n + (2*Math.PI*i)/n),a*Math.sin(angle/n + (2*Math.PI*i)/n));
			c[i] = num;
		}
		return c;	
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		double realFormat = Math.round(real*100.0)/100.0;
		double imagFormat = Math.round(imaginary*100.0)/100.0;
		
		if (imagFormat < 0) 
			return String.format("%s-i%s", realFormat, -imagFormat);
		else 
			return String.format("%s+i%s", realFormat, imagFormat);
	}

	/**
	 * Getter for real part of Complex number
	 * @return real as double
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Getter for imaginary part of Complex number
	 * @return imaginary as double
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Returns new Complex number by parsing given string
	 * @param s string in format "<code>real</code>+i<code>imaginary</code>"
	 * @return new Complex
	 */
	public static Complex parse(String s) {
		s = s.replaceAll("\\s+", "");
		String[] num = s.split("");
		int i;
		for (i = 0; i < num.length-1; i++) {
			if ((num[i].equals("+") || num[i].equals("-")) &&
					(num[i+1].equals("+") || num[i+1].equals("-")))
				throw new IllegalArgumentException("Multiple signs are not supported.");
		}
		
		String real = "", imaginary = "";
		for (i = 1; i < num.length; i++) {
			if (num[i].equals("+") || num[i].equals("-"))
				break;
		}
		if (i == num.length) {
			if (num[0].equals("i")) {
				for (int k = 1; k < num.length; k++)
					imaginary += num[k];
				if (imaginary.equals("") || imaginary.equals("-"))
					imaginary += "1";
			} else if (num.length > 1 && num[1].equals("i")){
				imaginary += num[0];
				for (int k = 2; k < num.length; k++)
					imaginary += num[k];
				if (imaginary.equals("") || imaginary.equals("-"))
					imaginary += "1";
			} else {
				real = s;
			}
			if (real.equals("")) 
				real = "0";
			if (imaginary.equals(""))
				imaginary = "0";
		} else {
			int j;
			for (j=0; j < i; j++) 
				real += num[j];
			if (num[i].equals("-"))
				imaginary += "-";
			for (j = i+2; j < s.length(); j++)
				imaginary += num[j];
			if (imaginary.equals("") || imaginary.equals("-"))
				imaginary += "1";
		}
		
		return new Complex(Double.parseDouble(real),Double.parseDouble(imaginary));
	}
}
