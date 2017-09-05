package org.aikodi.rejuse.java.collections;


/**
 * <p>The effect of this filter is the same as of a filter with the
 * conjunction of the criteria of the filters it was build with.</p>
 *
 * <center>
 *   <img src="doc-files/AndFilter.png"/>
 * </center>
 *
 * <p>Different filters can be applied to a collection consecutively.
 * It is however more performant if all criteria are applied to the elements
 * of the set at once. The AndFilter does just that. It either retains only the
 * objects satisfying all criteria, or it only discards the objects matching
 * all criteria.</p>
 *
 * <p>You can use <code>TypeFilter</code> like this:</p>
 * <pre><code>
 * new TypeFilter(new Filter[]{filter1, filter2, ...}).retain(collection);
 * </code></pre>
 * 
 * <pre><code>
 * new TypeFilter(myFilters).retain(collection);
 * </code></pre>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Jan Dockx
 * @author  Marko van Dooren 
 * @release $Name$
 */
public final class AndFilter extends Filter {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
   * <p>A new Filter that filters objects based on the criteria of all
   * the filters provided in <filters>.</p>
   *
   * @param  filters
   *         The array of filters to be used by this conjuntion filter.
   */
 /*@
	 @ public behavior
	 @
   @ // The given array of filters may not be null.
   @ pre filters != null;
   @ // The given array may only contain effective Filter objects
   @ pre (\forall int i; (i>=0) && (i<filters.length); filters[i] != null);
   @
   @ // The filters of this AndFilter are set to the given filters.
   @ post Arrays.equals(getFilters(),filters);
   @*/
  public AndFilter(Filter[] filters) {
    _filters = (Filter[]) filters.clone();
  }
  
  /**
	 * Return the filters used by this AndFilter.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @ post (\forall int i; (i>=0) && (i<\result.length); \result[i] != null);
	 @*/
  public final /*@ pure @*/ Filter[] getFilters() {
    return (Filter[]) _filters.clone();
  }
  
	/**
	 * The filters used by this AndFilter.
	 */
 /*@
   @ // _filters must be effective
   @ private invariant _filters != null;
   @ // _filters may only contain effective filters
   @ private invariant (\forall int i; (i>=0) && (i<_filters.length); _filters[i] != null);
   @*/
  private Filter[] _filters;

	/**
	 * See superclass
	 */
 /*@
   @ also public behavior
	 @
   @ post (\forall int i; i>=0 && i<getFilters().length;
	 @        getFilters()[i].criterion(element));
	 @*/
	public final /*@ pure @*/ boolean criterion(Object element) {
    boolean result = true;
    // the for loop stops when result == false
    for (int i = 0; result && (i < _filters.length); i++) {
      result = _filters[i].criterion(element);
    }
    return result;
  }
}

