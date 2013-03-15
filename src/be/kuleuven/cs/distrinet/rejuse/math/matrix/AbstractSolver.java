package be.kuleuven.cs.distrinet.rejuse.math.matrix;

/**
 * <p>A helper class for classes that solve systems of equations using
 *    matrices. That often involves solve a triangular system at the end.</p>
 *
 * @author  Marko van Dooren
 */
public abstract class AbstractSolver {
	
	/**
	 * <p>Solve the uppertriangular linear system of equations defined by
	 * <code>R * x = b</code></p>
	 *
	 * @param R
	 *        The uppertriangular matrix containing the coefficients of the
	 *        equations.
	 * @param b
	 *        A column containing the right-hand sides of the equations.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre R != null;
	 @ pre b != null;
	 @ pre R.isSquare();
	 @ pre R.isUpperTriangular();
	 @ pre b.size() == R.getNbRows();
	 @ pre (* R is nonsingular *);
	 @
	 @ post (* R.times(\result).equals(b) *);
	 @*/
	public /*@ pure @*/ Column backSubstitute(Matrix R, Column b) {
		int size = b.size();
		Column result = (Column) b.clone();
		for (int i=size; i >= 1; i--) {
			double bi = result.elementAt(i)/R.elementAt(i,i);
			result.setElementAt(i,bi);
			for (int j=i-1; j>= 1; j--) {
				double bj = result.elementAt(j) - R.elementAt(j,i) * bi;
				result.setElementAt(j,bj);
      }
    }
		return result;
  }

	/**
	 * <p>Solve the lowertriangular linear system of equations defined by
	 * <code>L * x = b</code></p>
	 *
	 * @param L
	 *        The lowertriangular matrix containing the coefficients of the
	 *        equations.
	 * @param b
	 *        A column containing the right-hand sides of the equations.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre L != null;
	 @ pre b != null;
	 @ pre L.isSquare();
	 @ pre L.isLowerTriangular();
	 @ pre b.size() == L.getNbRows();
	 @ pre (* L is nonsingular *);
	 @
	 @ post (* L.times(\result).equals(b) *);
	 @*/
	public /*@ pure @*/ Column forwardSubstitute(Matrix L, Column b) {
		int size = b.size();
		Column result = (Column) b.clone();
		for (int i=1; i <= size; i++) {
			double bi = result.elementAt(i)/L.elementAt(i,i);
			result.setElementAt(i,bi);
			for (int j=i+1; j<= size; j++) {
				double bj = result.elementAt(j) - L.elementAt(j,i) * bi;
				result.setElementAt(j,bj);
      }
    }
		return result;
  }
}