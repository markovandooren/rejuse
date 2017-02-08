package org.aikodi.rejuse.java.reflect;


import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

import org.aikodi.rejuse.java.collections.Filter;
import org.aikodi.rejuse.java.collections.SafeAccumulator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * Utility methods for method reflection.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Jan Dockx
 * @release $Name$
 */
public class Methods {
  
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
   * <p>All methods that are usable in the class. These are all the methods
   * declared in <class>, and all public, protected and package accessible
   * methods defined in super types (classes or interfaces).</p>
   * <p>Since the result is a set, overwritten methods only occur once.</p>
   * <p>The term methods in this context includes instance and class methods,
   * but not constructors.<p>
   *
   * @param  clazz
   *         The class to get all applicable methods for.
   * @return instances of java.lang.reflect.AccessibleObject
   //JDJDJD Java 1.2
   * @return instances of java.lang.reflect.Member
   */
 /*@
   @ // The given Class must be effective.
   @ pre clazz != null;
   @
   @ // The result is Member
   @ post \result instanceof Member;
   @*/
  //MvDMvDMvD : ask Jan what this should do. I think it behaves weird.
  static public /*@ pure @*/ Set getAllApplicableMethods(Class clazz) {
    return (Set)new SafeAccumulator() {
                      public Object initialAccumulator() {
                        return new HashSet();
                      }
                      public Object accumulate(Object element, Object acc) {
                        Class clazzElement = (Class)element;
                        Set allMethods = (Set)acc;
                        Set declaredMethods = new HashSet(Arrays.asList(clazzElement.getDeclaredMethods()));
                        allMethods.addAll(declaredMethods);
                        return allMethods;
                      }
                    }.accumulate(Classes.getSuperTypes(clazz));
  }

  //MvDMvDMvD : change into predicates.
  
  public static final Filter PUBLIC_ACCESS_FILTER =
      new Filter() {
            public boolean criterion(Object element) {
              return (Modifier.isPublic(((Member)element).getModifiers()));
            }
          };

  public static final Filter PROTECTED_ACCESS_FILTER =
      new Filter() {
            public boolean criterion(Object element) {
              return (Modifier.isProtected(((Member)element).getModifiers()));
            }
          };

  public static final Filter PACKAGE_ACCESS_FILTER =
      new Filter() {
            public boolean criterion(Object element) {
              int modifiers = ((Member)element).getModifiers();
              return (!(Modifier.isPrivate(modifiers) || Modifier.isProtected(modifiers) || Modifier.isPublic(modifiers)));
            }
          };

  public static final Filter PRIVATE_ACCESS_FILTER =
      new Filter() {
            public boolean criterion(Object element) {
              return (Modifier.isPrivate(((Member)element).getModifiers()));
            }
          };

  public static final Filter ABSTRACT_FILTER =
      new Filter() {
            public boolean criterion(Object element) {
              return (Modifier.isAbstract(((Member)element).getModifiers()));
            }
          };

}

