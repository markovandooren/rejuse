package org.rejuse.predicate;

import java.util.Collection;

/**
 * <p>A class of predicate that check whether or not an object conforms to
 * a certain type.</p>
 *
 * <center><img src="doc-files/TypePredicate.png"/></center>
 *
 * <p>This class is typically used as follows:</p>
 * <pre><code>
 * TotalPredicate predicate = new TypePredicate(MyClass.class);
 * </code></pre>
 *
 * <p>When a <code>TypePredicate</code> is used as a constant in an interface,
 * the <code>.class</code> operator will return <code>null</code>, so in that
 * case you must pass the name of the class as a <code>String</code>:</p>
 * <pre><code>
 * TotalPredicate predicate = new TypePredicate("mypackage.MyClass");
 * </code></pre>
 *
 * @author Marko van Dooren
 * @author Jan Dockx
 * @version $Revision$
 * @path $Source$
 * @date $Date$
 * @state $State$
 * @release $Name$
 */
public class TypePredicate<T,C extends T> extends SafePredicate<T> {

    /* The revision of this class */
    public static final String CVS_REVISION = "$Revision$";

    /**
     * <p>Initialize a new TypePredicate that checks whether or not objects
     * are from a give type.</p>
     *
     * @param type The type.
     */
    /*@
      @ public behavior
      @
    @ pre type != null;
    @
    @ post getType() == type;
    @*/
    public TypePredicate(Class<C> type) {
        _type = type;
    }

    /**
     * <p>Initialize a new TypePredicate based on the type with the given name.</p>
     *
     * @param name The name of the type.
     */
   /*@
     @ public behavior
     @
     @ pre name != null;
     @ pre (* <name> must be a valid classname *);
     @
     @ // The type of this TypeFilter is set to the type
     @ // with the given name.
     @ post getType() == Class.forName(name);
     @
     @ signals (LinkageError) (* something went wrong *);
     @ signals (ExceptionInInitializerError) (* something went wrong *);
     @ signals (IllegalArgumentException) (* Illegal Class Name *);
     @*/
    public TypePredicate(String name) throws LinkageError, ExceptionInInitializerError, IllegalArgumentException {
        try {
            _type = (Class<C>)Class.forName(name);
        } catch (ClassNotFoundException exc) {
            throw new IllegalArgumentException("Illegal class name : "+name);
        }
    }

    /**
     * Return the type of this TypePredicate.
     */
    /*@
      @ public behavior
      @
      @ post \result != null;
      @*/
    public /*@ pure @*/ Class<C> getType() {
        return _type;
    }

    /*@
      @ also public behavior
      @
      @ post \result == getType().isInstance(object);
      @*/
    @Override public /*@ pure @*/ boolean eval(T object) {
        return getType().isInstance(object);
    }

    /**
     * The type to be used in the filter.
     */
    /*@
    @ private invariant _type != null;
    @*/
    private Class<C> _type;


    public <X extends T, L extends Collection<C>,K extends Collection<X>> L filterReturn(K collection) {
        super.filter((K)collection);
        return (L)collection;
    }
}
/*
 * <copyright>Copyright (C) 1997-2001. This software is copyrighted by
 * the people and entities mentioned after the "@author" tags above, on
 * behalf of the JUTIL.ORG Project. The copyright is dated by the dates
 * after the "@date" tags above. All rights reserved.
 * This software is published under the terms of the JUTIL.ORG Software
 * License version 1.1 or later, a copy of which has been included with
 * this distribution in the LICENSE file, which can also be found at
 * http://org-jutil.sourceforge.net/LICENSE. This software is distributed
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the JUTIL.ORG Software License for more details. For more information,
 * please see http://org-jutil.sourceforge.net/</copyright>
 */
