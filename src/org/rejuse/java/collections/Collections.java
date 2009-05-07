package org.rejuse.java.collections;
import java.util.Collection;
import java.util.Map;
/**
 * <p>A utility class for perform operations on collections similar
 * to java.util.Collections.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class Collections {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

 /*@ 
   @ public invariant_redundantly
   @                  (\forall Collection c; c != null;
   @                    (\forall Object o1; c.contains(o1);
   @                      (\exists Object o2; containsExplicitly(c, o2);
   @                        ((o1 == null) && (o2 == null)) ||
   @                        ((o1 != null) && (o1.equals(o2))))));
   @
   @ public invariant_redundantly
   @                  (\forall Collection c; c!= null; 
   @                    (\forall Object o2; containsExplicitly(c, o2);
   @                      c.contains(o2)));
   @*/
  
  /**
   * Check whether or not the given collection contains the given element
   * when using == for comparison instead of equals().
   *
   * @param collection
   *        The collection
   * @param element
   *        The element
   */
 /*@
   @ public behavior
   @
   @ pre collection != null;
   @
   @ post \result == (\exists int i; i >= 0 && i < collection.toArray().length;
   @                     collection.toArray()[i] == element);
   @*/
  public static /*@ pure @*/ boolean containsExplicitly(Collection collection, Object element) {
    Object[] array = collection.toArray();
    boolean found = false;
    for (int i=0 ; i < array.length && (! found); i++) {
      if(array[i] == element) {
        found = true;
      }
    }
    return found; 
  }

  /**
   * Return the number of times <element> has been added to <collection>.
   *
   * @param element
   *        The element of which the number of occurrences is requested.
   * @param collection
   *        The collection in which the number of occurrences has to be counted.
   */
 /*@
   @ public behavior
   @
   @ pre collection != null;
   @
   @ post \result == (\num_of int i; i >= 0 && i < collection.toArray().length;
   @                    collection.toArray()[i] == element);
   @*/
  public static /*@ pure @*/ int nbExplicitOccurrences(Object element, Collection collection) {
    if(collection == null) {
      return 0;
    }
    Object[] array = collection.toArray();
    int count = 0;
    for(int i=0; i < array.length; i++) {
      if(array[i]==element) {
        count++;
      }
    }
    return count;
  }
    
  // MvDMvDMvD:
  // It inherits from java.util.Collections
  // * in order to have all functionality in a single class.
  
  /**
   * Check whether the two given collections contain the same elements.
   * Test are done with ==
   *
   * @param first
   *        The first collection
   * @param second
   *        The second collection
   */
 /*@
   @ public behavior
   @
   @ // True if both collections are null
   @ post ((first == null) && (second == null)) ==> \result == true;
   @
   @ // False if only one of both is null.
   @ post ((first == null) || (second == null)) &&
   @      !((first == null) && (second == null)) ==> \result ==false;
   @
   @ // If both collections are non-null, true if both collections
   @ // have the same size and contains the exact same elements.
   @ post ((first != null) && (second != null)) ==>
   @        \result == (first.size() == second.size()) &&
   @        (\forall Object o; containsExplicitly(first, o);
   @          nbExplicitOccurrences(o, first) == nbExplicitOccurrences(o, second));
   @*/
  public static /*@ pure @*/ boolean identical(final Collection first, final Collection second) {
    //MvDMvDMvD : add testcase for {A,B,B} and {A,A,B}
    if ((first == null) && (second == null)) {
      return true;
    }
    if ((first == null) || (second == null)) {
      return false;
    }
    if (first.size() != second.size()) {
      return false;
    }
    // Bigger indent makes it look better.
     return new ForAll() {
              public boolean criterion(final Object element) {
               // MvDMvDMvD : inefficient, but works for now
               return nbExplicitOccurrences(element, first) == nbExplicitOccurrences(element, second);
             }
    }.in(first);
  }
  
  /**
   * Check whether the two given maps contain the same entries.
   * Test are done with ==
   *
   * @param first
   *        The first map
   * @param second
   *        The second map
   */
 /*@
   @ // True if both maps are null
   @ post ((first == null) && (second == null)) ==> \result == true;
   @
   @ // False if only one of both is null.
   @ post ((first == null) || (second == null)) &&
   @      !((first == null) && (second == null)) ==> \result ==false;
   @
   @ // If both maps are non-null, true if both maps
   @ // have the same size and contains the same (key,value) pairs.
   @ post ((first != null) && (second != null)) ==>
   @        \result == (first.size() == second.size()) &&
   @                   (\forall Object k; first.containsKey(k);
   @                       second.containsKey(k) && second.get(k) == first.get(k)) &&
   @        first.size() == second.size();
   @ post (\forall Map.Entry e1; first.entrySet().contains(e1);
   @        (\exists Map.Entry e2; second.entrySet().contains(e2);
   @          (e1.getKey() == e2.getKey()) && (first.get(e1) == second.get(e2))));
   @ // MvDMvDMvD : this spec is not complete enough : second.containsKey(k)
   @ // is too vague
   @ // a,a,b will be equal to b,b,a
   @*/
  public static /*@ pure @*/ boolean identical(final Map first, final Map second) {
    if ((first == null) && (second == null)) {
      return true;
    }
    if ((first == null) || (second == null)) {
      return false;
    }
    if (first.size() != second.size()) {return false;}
    // Bigger indent makes it look better.
     return new ForAll() {
              public boolean criterion(final Object element) {
                return new Exists() {
                         public boolean criterion(Object element2) {
                          return (((Map.Entry)element).getValue() == ((Map.Entry)element2).getValue()) &&
                                 (((Map.Entry)element).getKey() == ((Map.Entry)element2).getKey());
                        }
                }.in(second.entrySet());
             }
    }.in(first.entrySet());

  }
}
/*<copyright>Copyright (C) 1997-2001. This software is copyrighted by 
the people and entities mentioned after the "@author" tags above, on 
behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
after the "@date" tags above. All rights reserved.
This software is published under the terms of the JUTIL.ORG Software
License version 1.1 or later, a copy of which has been included with
this distribution in the LICENSE file, which can also be found at
http://org-jutil.sourceforge.net/LICENSE. This software is distributed WITHOUT 
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
or FITNESS FOR A PARTICULAR PURPOSE. See the JUTIL.ORG Software 
License for more details.
For more information, please see http://org-jutil.sourceforge.net/</copyright>*/
