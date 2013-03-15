package be.kuleuven.cs.distrinet.rejuse.math;

/**
 * This is a general utility class for mathematical stuff.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @author  Tom Schrijvers
 * @release $Name$
 */
public abstract class ExtMath {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

	/**
	 * Return the sign of x
	 *
	 * @param x
	 *        The number of which the sign is requested.
	 */
 /*@
   @ public behavior
	 @
	 @ post (x>0) ==> \result == 1;
   @ post (x == 0) ==> \result == 0;
	 @ post (x<0) ==> \result == -1;
	 @*/
	public static /*@ pure @*/ int sign(double x) {
		if (x>0) {
			return 1;
		}
    else if (x==0) {
      return 0;
    }
    else {
		  return -1;
    }
	}

	/**
	 * <p>Compute a^b when b is positive.
	 * This metod is significantly faster than ExtMath.pow, but introduces 
	 * slightly different result than ExtMath.pow (Although I've only seen 
	 * differences in the last digit up to now, and also ExtMath.pow doesn't 
	 * always return the exact same result as Matlab).</p>
	 *
	 * <p>To give you an idea, it is more than <b>10</b> times faster using 
	 * Sun jdk 1.4.0 on a celeron 366MHz and a P4 1.5GHz processor.<p>
	 *
	 * @param base
	 *        The number of which the exponent'th power is requested
	 * @param exponent
	 *        The exponent
	 */
 /*@
	 @ public behavior
	 @
	 @ pre exponent > 0;
	 @
	 @ post (* \result == java.lang.Math.pow(base,exponent) *);
	 @*/
  public static final /*@ pure @*/ long lpower(long base, long exponent){
    long result = base;
    long pos = exponent;
    while (pos > 1){
      pos = pos >> 1;
      result = result * result * (((exponent & pos) > ((exponent ^ pos) & pos)) ? base : 1);
    }
    return result;
  }

	/**
	 * <p>Compute a^b when b is positive.
	 * This metod is significantly faster than ExtMath.pow, but introduces 
	 * slightly different result than ExtMath.pow (Although I've only seen 
	 * differences in the last digit up to now, and also ExtMath.pow doesn't 
	 * always return the exact same result as Matlab).</p>
	 *
	 * <p>To give you an idea, it is more than <b>10</b> times faster using 
	 * Sun jdk 1.4.0 on a celeron 366MHz and a P4 1.5GHz processor.<p>
	 *
	 * @param base
	 *        The number of which the exponent'th power is requested
	 * @param exponent
	 *        The exponent
	 */
 /*@
	 @ public behavior
	 @
	 @ pre exponent > 0;
	 @
	 @ post (* \result == java.lang.Math.pow(base,exponent) *);
	 @*/
	public static final /*@ pure @*/ double power(double base, long exponent){
		double result = base;
		long pos = exponent;
		
		while (pos > 1){
			pos >>= 1;
			result *= result * (((exponent & pos) > ((exponent ^ pos) & pos)) ? base : 1);
		}
		return result;
	}


	// small benchmark, must be removed some time.
	public static void main(String[] args) {
		double result=0;
		double a=3.434283471834239;
		long c=13;
		long b=30;
		long d=3;
		System.out.println(java.lang.Math.pow(a,b));
		System.out.println(power(a,b));
		for(int i=1; i<=1000000; i++) {
			result = java.lang.Math.pow(c,d);
			result = lpower(c,d);
			result = java.lang.Math.pow(a,b);
			result = power(a,b);
		}
		
		int reps=100000;

		long startMath=System.currentTimeMillis();
		for(int i=1; i<=reps;i++) {
			result = java.lang.Math.pow(a,b);
		}
		long stopMath=System.currentTimeMillis();

		long startPower=System.currentTimeMillis();
		for(int i=1; i<=reps;i++) {
			result = power(a,b);
		}
		long stopPower=System.currentTimeMillis();
	  System.out.println("java.lang.Math.pow : "+(stopMath-startMath)+"ms");	
	  System.out.println("power : "+(stopPower-startPower)+"ms");	

		startMath=System.currentTimeMillis();
		for(int i=1; i<=reps;i++) {
			result = java.lang.Math.pow(c,d);
		}
		stopMath=System.currentTimeMillis();

		startPower=System.currentTimeMillis();
		for(int i=1; i<=reps;i++) {
			result = lpower(c,d);
		}
		stopPower=System.currentTimeMillis();
	  System.out.println("java.lang.Math.pow : "+(stopMath-startMath)+"ms");	
	  System.out.println("power : "+(stopPower-startPower)+"ms");	
	}
}
