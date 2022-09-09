package hr.fer.oprpp1.custom.collections;

import java.text.DecimalFormat;

/**
 * Support for working with complex numbers
 * 
 * @author Daniel_Ranogajec
 *
 */
public class ComplexNumber {

	double real;
	double imaginary;
	
	/**
	 * Creates new Complex Number
	 * @param real part of complex number
	 * @param imaginary part of complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Returns ComplexNumber with no imaginary part
	 * @param real part of complex number
	 * @return new Complex Number
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real,0);
	}
	
	/**
	 * Returns ComplexNumber with no real part
	 * @param imaginary part of complex number
	 * @return new Complex Number
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0,imaginary);
	}
	
	/**
	 * Returns Complex Number calculated by its magnitude and angle
	 * @param magnitude of Complex Number
	 * @param angle	of Complex Number
	 * @return new Complex Number
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		double real = magnitude * Math.cos(angle);
		double imaginary = magnitude * Math.sin(angle);
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Returns new Complex Number by parsing given string
	 * @param s string in format "<code>real</code>+<code>imaginary</code>i"
	 * @return new ComplexNumber
	 */
	public static ComplexNumber parse(String s) {
		String[] num = s.split("");
		int i;
		for (i = 0; i < num.length-1; i++) {
			if ((num[i].equals("+") || num[i].equals("-")) &&
					(num[i+1].equals("+") || num[i+1].equals("-")))
				throw new IllegalArgumentException("Multiple signs are not supported.");
		}
		for (i = 0; i < num.length-1; i++) {
			if (num[i].equals("i"))
				throw new IllegalArgumentException("Letter 'i' can't be placed before the magnitude of imaginary part.");
		}
		
		String real = "", imaginary = "";
		for (i = 1; i < num.length; i++) {
			if (num[i].equals("+") || num[i].equals("-"))
				break;
		}
		if (i == num.length) {
			if (num[num.length-1].equals("i")) {
				for (int k = 0; k < num.length-1; k++)
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
			for (j = i+1; j < s.length()-1; j++)
				imaginary += num[j];
			if (imaginary.equals("") || imaginary.equals("-"))
				imaginary += "1";
		}
		
		return new ComplexNumber(Double.parseDouble(real),Double.parseDouble(imaginary));
	}
	
	/**
	 * Getter function for getting real part of Complex Number
	 * @return real part of Complex Number
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Getter function for getting imaginary part of Complex Number
	 * @return imaginary part of Complex Number
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Returns magnitude of this ComplexNumber
	 * @return magnitude as double
	 */
	public double getMagnitude() {
		return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
	}
	
	/**
	 * Returns angle of this ComplexNumber
	 * @return angle in radians as double
	 */
	public double getAngle() {
		return Math.atan(imaginary/real);
	}
	
	/**
	 * Method for adding this with other Complex Number
	 * @param other complex number you want to add to this
	 * @return new ComplexNumber as sum of this and other
	 */
	public ComplexNumber add(ComplexNumber other) {
		return new ComplexNumber(this.real+other.getReal(), this.imaginary+other.getImaginary());
	}
	
	/**
	 * Method for subtracting this with other Complex Number
	 * @param other complex number you want to sub from this
	 * @return new ComplexNumber as subtraction of this and other
	 */
	public ComplexNumber sub(ComplexNumber other) {
		return new ComplexNumber(this.real-other.getReal(), this.imaginary-other.getImaginary());
	}
	
	/**
	 * Method for multiplying this with other Complex Number
	 * @param other complex number you want to multiply to this
	 * @return new ComplexNumber as multiplication of this and other
	 */
	public ComplexNumber mul(ComplexNumber other) {
		return new ComplexNumber(this.real*other.getReal() - this.imaginary*other.getImaginary(), this.imaginary*other.getReal() + this.real*other.getImaginary());
	}
	
	/**
	 * Method for dividing this with other Complex Number
	 * @param other complex number you want to divide from this
	 * @return new ComplexNumber as division of this and other
	 */
	public ComplexNumber div(ComplexNumber other) {
		ComplexNumber c1 = this.mul(new ComplexNumber(other.getReal(),other.getImaginary() * -1));
		ComplexNumber c2 = other.mul(new ComplexNumber(other.getReal(),other.getImaginary() * -1));
		
		return new ComplexNumber(c1.getReal()/c2.getReal(), c1.getImaginary()/c2.getReal());
	}
	
	/**
	 * Returns new Complex Number as an n-th power of this ComplexNumber
	 * @param n -th power
	 * @return new ComplexNumber which is n-th power of this ComplexNumber
	 */
	public ComplexNumber power(int n)  {
		if (n < 0) 
			throw new IllegalArgumentException("n must be >= 0");
		
		return new ComplexNumber(Math.pow(this.getMagnitude(), n) * Math.cos(this.getAngle()*n),
				Math.pow(this.getMagnitude(), n) * Math.sin(this.getAngle()*n));
	}
	
	/**
	 * Returns array of Complex Numbers after calculating its n-th root
	 * @param n -th root
	 * @return array of Complex Numbers which are n-th roots of this ComplexNumber
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) 
			throw new IllegalArgumentException("n must be > 0");
		
		ComplexNumber[] c = new ComplexNumber[n];
		double r = this.getMagnitude();
		double angle = this.getAngle();		
		for (int i = 0; i < n; i++) {
			double a = Math.pow(r, 1.0/n);
			ComplexNumber num = new ComplexNumber(a*Math.cos(angle/n + (2*Math.PI*i)/n),a*Math.sin(angle/n + (2*Math.PI*i)/n));
			c[i] = num;
		}
		return c;	
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String s = "";
		double realFormat = Math.round(real*100.0)/100.0;
		double imagFormat = Math.round(imaginary*100.0)/100.0;
		String realString = new DecimalFormat("#.##").format(real);
		String imagString = new DecimalFormat("#.##").format(imaginary);
		
		if (realFormat != 0)			//real != 0 or 0,00...1
			s += realString;
		if (imagFormat != 0) {
			if (imaginary < 0) {
				if (imagFormat != -1)
					s = s + imagString + "i";
				else 
					s += "-i";
			} else {
				if (imagFormat != 1) {
					if (s.equals(""))
						s = imagString + "i";
					else 
						s = s + "+" + imagString + "i"; 
				} else 
					if (s.equals(""))
						s = "i";
					else 
						s += "+i";
			}
		}
		if (s.equals(""))
			s = "0";
		s = s.replace(",", ".");
		
		return s;
	}
	
}
