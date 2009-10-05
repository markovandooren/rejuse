package org.rejuse.junit;
import junit.framework.TestCase;

/**
 * <p>This is an extension for the junit <code>TestCase</code> class.</p>
 *
 * <center>
 *   <img src="doc-files/JutilTest.png"/>
 * </center>
 *
  * <p>This class adds revision checking to junit tests. The revision of a library class
 * is kept in a static string, which is automatically updated by CVS (or another revision
 * control management system). Each testclass passes a <a href="Revision.html"></code>Revision</code></a> object
 * to the constructor of this class, which represents the revision that is tested by that testclass.
 * When the library class is changed, the revisions will differ, and a 
 * <a href="RevisionError.html"><code>RevisionError</code></a> will be thrown. This way a developer knows
 * that he must check whether or not the testclass must be adapted.</p>
 *
 * <p>At this moment this class can only work with cvs revisions. Put the following
 * code in the library class:</p>
 * <pre><code>
 * public final static String CVS_REVISION ="$Revision$";
 * </code></pre>
 * <p>CVS will now automatically adapt the <code>CVS_REVISION</code> field when the cvs revision
 * is changed.</p>
 *
 * <p>The test class either pass a <code>Class</code> to a constructor to indicated the tested class
 * , or for Jutil.org testclasses, use the constructor which determines the testclass itself. For
 * the Jutil.org testclasses, the following scheme is used:</p>
 * <pre><code>org.jutiltest.X.TestY -> org.rejuse.X.T</code></pre>
 *
 * <p>A typical constructor of a testclass will look like this:</p>
 * <pre><code>
 * super(nameForJunit, new CVSRevision("x.x.x"));
 * </code></pre>
 * <p>Of course any <code>Revision</object> can be passed, but most people use CVS as
 * source control mechanism.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */
public abstract class JutilTest extends TestCase {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

 /*@
   @ public invariant getTestedRevision().equals(getTestedClassRevision());
   @*/

	/**
	 * <p>Initialize a new JutilTest with the given name and tested revision.</p>
	 * <p>This is a convenience constructor for Jutil.org test classes. The tested class
	 * will be set to the corresponding class in the library. This means, the class
	 * with package org.rejuse.X when the package of this class is org.jutiltest.X and
	 * with the name X when the name of this testclass is TestX.<p>
	 * <p>If you want to set the tested class yourself, use the other constructor.</p>
	 *
	 * @param name
	 *        The name of the method that has to be invoked by default.
	 * @param testedRevision
	 *        The revision of the tested class that is tested by this class.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre testedRevision != null;
	 @
	 @ // The tested class is the corresponding class in the org.jutil package
	 @ // with the name X, when the name of the testclass is TestX
	 @ post getTestedClass().getName().equals(
	 @        new Pattern("org\\.jutiltest\\").replacer("org.jutil").replace(
	 @          new Pattern("\\.Test[^\\.]*").replacer("").replace(
	 @            getClass().getName()
	 @          )
	 @        )
	 @      );
	 @ // The cvs revision of the tested class is assigned to getTestedClassRevision()
	 @ post	getTestedClassRevision().equals(
	 @        new CVSRevision(
	 @           getTestedClass().getField("CVS_REVISION").get(null).toString()
	 @        )
	 @      ); 
	 @ post getTestedRevision() == testedRevision;
	 @
	 @ // Throws an AssertionFailedError if the actual class does not contain a field named CVS_REVISION.
	 @ signals (AssertionFailedError) (\forall Field f; new Vector(Arrays.asList(getTestedClass().getFields())).contains(f);
	 @                                ! f.getName().equals("CVS_REVISION"));
	 @ // The default tested claas is not found.
	 @ signals (AssertionFailedError) (* There is no class with the name 
	 @                                   new Pattern("org\\.jutiltest\\").replacer("org.jutil").replace(
	 @                                      new Pattern("\\.Test[^\\.]*").replacer("").replace(
	 @                                         getClass().getName()
	 @                                      )
	 @                                   )
	 @                                 *);
	 @ // Throws a RevisionError if the tested revision doesn't match the revision of the tested class.
	 @ signals (RevisionError) ! getTestedRevision().equals(getTestedClassRevision());
	 @*/
	public JutilTest(String name, Revision testedRevision) {
		super(name);
		_testedRevision = testedRevision;
		try{
			String className = getClass().getName();
			String testedClassName = "org.jutil."+className.substring(14,className.length());
			int index = testedClassName.lastIndexOf(".");
			testedClassName = testedClassName.substring(0,index+1)+testedClassName.substring(index+5,testedClassName.length());
			_testedClass = Class.forName(testedClassName);
			_testedClassRevision = new CVSRevision(getTestedClass().getField("CVS_REVISION").get(null).toString()); 
		}
		catch(ClassNotFoundException exc) {
//			assertTrue(false);
		}
		catch(NoSuchFieldException exc) {
//			assertTrue(false);
		}
		catch(IllegalAccessException exc) {
//			assertTrue(false);
		}
//		checkRevision();
	}

	/**
	 * Initialize a new JutilTest with the given name, tested class and tested revision.
	 *
	 * @param name
	 *        The name of the method that has to be invoked by default.
	 * @param testedClass
	 *        The class that is tested by this testclass.
	 * @param testedRevision
	 *        The revision of the tested class that is tested by this class.
	 */
 /*@
	 @ public behavior
	 @
	 @ pre testedRevision != null;
	 @ pre testedClass != null;
	 @
	 @ // The tested class of this JutilTest is set to the given class.
	 @ post getTestedClass() == testedClass;
	 @ // The cvs revision of the tested class is assigned to getTestedClassRevision()
	 @ post	getTestedClassRevision().equals(
	 @        new CVSRevision(
	 @           getTestedClass().getField("CVS_REVISION").get(null).toString()
	 @        )
	 @      ); 
	 @ post getTestedRevision() == testedRevision;
	 @
	 @ // Throws an AssertionFailedError if the actual class does not contain a field named CVS_REVISION.
	 @ signals (AssertionFailedError) (\forall Field f; new Vector(Arrays.asList(getTestedClass().getFields())).contains(f);
	 @                                ! f.getName().equals("CVS_REVISION"));
	 @ // Throws a RevisionError if the tested revision doesn't match the revision of the tested class.
	 @ signals (RevisionError) ! getTestedRevision().equals(getTestedClassRevision());
	 @*/
	public JutilTest(String name, Class testedClass, Revision testedRevision) {
		super(name);
		_testedRevision = testedRevision;
		try{
		  _testedClass = testedClass;
			_testedClassRevision = new CVSRevision(getTestedClass().getField("CVS_REVISION").get(null).toString()); 
		}
	catch(NoSuchFieldException exc) {
//			assertTrue(false);
		}
		catch(IllegalAccessException exc) {
//			assertTrue(false);
		}
//		checkRevision();
	}

	/**
	 * Check the version of the tested class. 
	 */
 /*@
	 @ private behavior
	 @
	 @ // Throws a RevisionError if the tested revision doesn't match the revision of the tested class.
	 @ signals (RevisionError) ! getTestedRevision().equals(getTestedClassRevision());
	 @*/
	private void checkRevision() {
		if(! getTestedRevision().equals(getTestedClassRevision())) {
			throw new RevisionError("Tested Class Version Mismatch: Testing revision "+getTestedRevision()+ " while the latest revision is "+getTestedClassRevision());
		}
	}

	/**
	 * Return the revision of the tested class that is tested by this class. 
	 * This is not necessarily the same revision as the revision of the tested class.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @*/
	public /*@ pure @*/ Revision getTestedRevision() {
		return _testedRevision;
	}


	/**
	 * Return the field representing the revision of the tested class.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @*/
	public /*@ pure @*/ Revision getTestedClassRevision() {
		return _testedClassRevision;
	}

	/**
	 * Return the class that is tested by this test class.
	 */
 /*@
	 @ public behavior
	 @
	 @ post \result != null;
	 @*/
	public /*@ pure @*/ Class getTestedClass() {
		return _testedClass;
	}

 /*@
   @ private invariant _testedRevision != null;
	 @*/
	private Revision _testedRevision;

 /*@
   @ private invariant _testedClassRevision != null;
	 @*/
	private Revision _testedClassRevision;

 /*@
   @ private invariant _testedClass != null;
	 @*/
	private Class _testedClass;
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
