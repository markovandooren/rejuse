package be.kuleuven.cs.distrinet.rejuse.java.reflect;


import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import be.kuleuven.cs.distrinet.rejuse.java.collections.Filter;
import be.kuleuven.cs.distrinet.rejuse.java.collections.SafeTransitiveClosure;

/**
 * Utility methods for class reflection.
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Jan Dockx
 * @author  Marko van Dooren
 * @release $Name$
 */
public class Classes {
  
	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
   * <p>All immediate syper types of <clazz>. These are the interfaces
   * and classes mentioned in the <code>implements</code> and <code>extends</code>
   * clauses, or <code>Object</code> when there is no <code>extends</code>.</p>
   * <p>If the this method is applied to class Class, the result is the empty set.</p>
   *
   * @param  clazz
   *         The class to get all immediate super types for.
   */
 /*@
   @ // The given Class must be effective
   @ pre clazz != null;
   @
   @ // The set contains elements of type Class
   @ post (\forall Object o; \result.contains(o); o instanceof Class);
   @ // The set contains no null references
   @ post (\forall Object o; \result.contains(o); o != null);
   @ // The set only contains immediate supertypes of <clazz>.
   @ post (\forall Class c; \result.contains(c); 
   @          c == clazz.getSuperclass() || Arrays.asList(clazz.getInterfaces()).contains(c));
   @ // If the this method is applied to class Class, the result is the empty set.
   @ post (clazz == Class.class) ==> \result.isEmpty();
   @*/
  static /*@ pure @*/ public Set getImmediateSuperTypes(Class clazz) {
    Set supertypes =
        new HashSet(Arrays.asList(clazz.getInterfaces()));
    Class superclass = clazz.getSuperclass();
    if (superclass != null) { // otherwise the tested class is Object
      supertypes.add(superclass);
    }
    return supertypes;
  }
  
  /**
   * <p>All syper types of <clazz>. This is the transitive closure of the immediate
   * super types of <clazz>.</p>
   *
   * @param  clazz
   *         The class to get all super types for.
   */
 /*@
   @ // The given Class must be effective
   @ pre clazz != null;
   @
   @ // The set contains elements of type Class
   @ post (\forall Object o; \result.contains(o); o instanceof Class);
   @ // The set contains no null references
   @ post (\forall Class c; \result.contains(c); c != null);
   @ // The set contains only super types of clazz.
   @ post (\forall Class c; \result.contains(c); c.isAssignableFrom(clazz));
   @ // The set contains <clazz>.
   @ post \result.contains(clazz);
   @ //If the this method is applied to class Object, the result
   @ //is the singleton {Object}.
   @ post (clazz == Object.class) ==> (\result.contains(Object.class)) &&
   @                                  (\result.size() == 1);
   @*/
//MvDMvDMvD: not true in case of an interface
//    @ // The result always contains Object.
//    @ post \result.contains(Object.class);
//MvDMvDMvD: not true in case of an interface
//    Object.class.isAssignableFrom(Interface.class)
//    @ //The set contains all the super types of <clazz>.
//    @ post (\forall Class c; c.isAssignableFrom(clazz) ; \result.contains(c));

  static public /*@ pure @*/ Set getSuperTypes(Class clazz) {
    return new SafeTransitiveClosure() {
                  public void addConnectedNodes(Object node, Set accumulator) {
                    accumulator.addAll(getImmediateSuperTypes((Class)node));
                  }
                }.closure(clazz);
  }
  
  /**
   * <p>All super classes of <clazz>, including <clazz> and <code>Object</code>.</p>
   * <p>If <clazz> is an interface, the set is empty.</p>
   *
   * @param  clazz
   *         The class to get all super classes for.
   * //MvDMvDMvD : The following 2 postconditions are not required I think.
   * @result If the this method is applied to class Object, the result
   *         is the singleton {Object}.
   *       | (clazz = Object.class) ==> result = {Object.class}
   * @result If <clazz> is an interface, the result is the empty set.
   *       | clazz.isInterface() ==> result.isEmpty()
   */
 /*@
   @ // The given Class must be effective
   @ pre clazz != null;
   @
   @ // The set contains elements of type Class
   @ post (\forall Object o; \result.contains(o); o instanceof Class);
   @ // The set contains no null references
   @ post (\forall Class c; \result.contains(c); c != null);
   @ // The set contains only classes, no interfaces.
   @ post (\forall Class c; \result.contains(c); ! c.isInterface());
   @ // The set contains only super classes of clazz.
   @ post (\forall Class c; \result.contains(c); c.isAssignableFrom(clazz));   
   @ // The set contains all the super classes of <clazz>.
   @ post (\forall Class c; (c.isAssignableFrom(clazz)) && (! c.isInterface()); \result.contains(c));
   @ // If <clazz> is an interface, the set is empty.
   @ post (clazz.isInterface()) ==> \result.size() == 0;
   @
   @ post (* The order of the result set is that subclasses are smaller
   @         than superclasses.*);
   @*/
  static public /*@ pure @*/ SortedSet getSuperClasses(Class clazz) {
    if (clazz.isInterface()) {
      return new TreeSet();
    }
    else {
      SortedSet result =
          new TreeSet(new Comparator() {
                              public int compare(Object o1, Object o2) {
                                Class c1 = (Class)o1;
                                Class c2 = (Class)o2;
                                if (c1.equals(c2)) {
                                  return 0;
                                }
                                else {
                                  return (((Class)o1).isAssignableFrom((Class)o2)) ? 1 : -1;
                                }
                              }
                            });
      Class newClass = clazz; // not an interface
      while (newClass != null) {
        result.add(newClass);
        newClass = newClass.getSuperclass(); // null if newClass was Object.class
      }
      return result;
    }
  }
  
  /**
   * Check whether <t1> and <t2> are defined in the same package.
   *
   * @param  t1
   *         The first type to compare.
   * @param  t2
   *         The second type to compare.
   */
 /*@
   @ // The types to compare are not null.
   @ pre (t1 != null) && (t2 != null);
   @
   @ // The result is true if the 2 given classes are defined
   @ // in the same package. False otherwise.
   @ post \result == (t1.getPackage().getName().equals(t2.getPackage().getName()));
   @ // null is returned if no package can be found for one
   @ // of the classes.
   @ post (t1.getPackage() == null || t2.getPackage() == null) ==> (\result == false);
   @*/
  public static /*@ pure @*/ boolean areInSamePackage(Class t1, Class t2) {
    try {
    /*<jdk>*/
    /*<jdk1.1.8>
    return packageName(t1).equals(packageName(t2));
    </jdk1.1.8>*/
    /*<jdk1.3>*/
    return t1.getPackage().getName().equals(t2.getPackage().getName());
    /*</jdk1.3>*/
    /*</jdk>*/
    }
    catch(NullPointerException exc) {
      return false;
    }
  }

  /**
   * The fully qualified package name of type <t>. The empty string
   * represents the unnamed package.
   *
   * @param  t
   *         The type to get the fully qualified package name for.
   * //MvDMvDMvD : for JDK 1.2+ this can be specified in terms of
   * getPackage().getName()
   * @return The fully qualified package name of <t>. The empty string
   *         represents the unnamed package.
   */
 /*@
   @ // The given class must be effective.
   @ pre t != null;
   @
   @ // The result is never null.
   @ post \result != null;
   @ // The result never contains illegal characters.
   @ post Character.isJavaIdentifierStart(\result.charAt(1));
   @ post (\forall int i; (i>0) && (i<\result.length()); Character.isJavaIdentifierPart(\result.charAt(i)));
   @*/
  //MvDMvDMvD: not consistent with getPackage().getName().
  // for inner classes getPackage() returns null, and thus it
  // has no packageName. Here the 1.2+ code returns "" and 1.1.8
  // returns the packgeName of the outer class.
  public static /*@ pure @*/ String packageName(Class t) {
    /*<jdk>*/
    /*<jdk1.1.8>    
    String typeName = t.getName();
    int i = typeName.lastIndexOf('.');
    if (i == -1) { // type name doesn't contain a period: unnamed package
      return "";
    }
    else {
      return typeName.substring(0, i); // doesn't contain the final dot
    }
    </jdk1.1.8>*/
    /*<jdk1.3>*/
    Package pack = t.getPackage();
    if (pack != null) {
      return t.getPackage().getName();
    }
    else {
      return "";
    }
    /*</jdk1.3>*/
    /*</jdk>*/
  }

  //MvDMvDMvD : change into predicates
  
  public static final Filter PUBLIC_ACCESS_FILTER =
      new Filter() {
            public boolean criterion(Object element) {
              return (Modifier.isPrivate(((Class)element).getModifiers()));
            }
          };

  public static final Filter PROTECTED_ACCESS_FILTER =
      new Filter() {
            public boolean criterion(Object element) {
              return (Modifier.isProtected(((Class)element).getModifiers()));
            }
          };

  public static final Filter PACKAGE_ACCESS_FILTER =
      new Filter() {
            public boolean criterion(Object element) {
              int modifiers = ((Class)element).getModifiers();
              return (!(Modifier.isPrivate(modifiers) || Modifier.isProtected(modifiers) || Modifier.isPublic(modifiers)));
            }
          };

  public static final Filter PRIVATE_ACCESS_FILTER =
      new Filter() {
            public boolean criterion(Object element) {
              return (Modifier.isPrivate(((Class)element).getModifiers()));
            }
          };

}

