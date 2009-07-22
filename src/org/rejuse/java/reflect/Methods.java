package org.rejuse.java.reflect;


import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.rejuse.java.SafeAccumulator;
import org.rejuse.java.collections.Filter;


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
