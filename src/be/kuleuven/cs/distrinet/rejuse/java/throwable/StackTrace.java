package be.kuleuven.cs.distrinet.rejuse.java.throwable;


/**
 * <p>Utility methods to deal with the stack trace of throwables. This is nominally
 * only available as output on an OutputStream, and we want more.</p>
 *
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Jan Dockx
 * @author  Marko van Dooren
 * @release $Name$
 */
public class StackTrace {

	/* The revision of this class */
	public final static String CVS_REVISION ="$Revision$";

  /**
   * <p>Converts the stack trace of <t> into a list of names of active methods.</p>
   * <p>This method was based on
   * <code>private String junit.framework.TestSuite.exceptionToString(Throwable t)</code>
   * and
   * <code>public static String junit.runner.BaseTestRunner.filterStack(String stack)</code>
   * from JUnit.</p>
   *
   * @param  t
   *         The Throwable to get the stack trace of.
   * @return The stack trace of <t>, as a list of Strings. The Strings are the names
   *         of the active methods. They are ordered in the list starting from main,
   *         with the method during whose execution the throwable was generated the
   *         last entry.
// JDJDJD perhaps it would not be a bad idea to use new objects, holding a reference
            to the method and a string for the location in that file   
   */
  /*@
    @ pre   t != null;
    @
    @ // The result is not null.
    @ // The result does not contain null references.
    @ post (\result != null) &&
    @      (\forall Object o;\result.contains(o); o != null) &&
    @      (\forall Object o;\result.contains(o); o instanceof String);
    @*/
  public /*@ pure @*/ static java.util.List asList(Throwable t) {
    /* stupid Sun developers only allow to print the stack trace to a stream;
        there is no way to actually inspect it, not even as a String;
        a StringWriter emulates a writer we can write the stack trace to, and we
        can get the string from that */
    java.io.StringWriter stringWriter= new java.io.StringWriter();
    java.io.PrintWriter writer= new java.io.PrintWriter(stringWriter);
    t.printStackTrace(writer);
    /* now we are going to read the lines of the stack trace, each containing a method
        call, one by one; we use a BufferedReader for this; we simulate a stream
        from the above string with the StringReader */
    //JDJDJD I think I don't understand PipedReaders/Writers, but those should help here.
    java.io.StringReader stringReader = new java.io.StringReader(stringWriter.toString());
    java.io.BufferedReader reader = new java.io.BufferedReader(stringReader);
    java.util.ArrayList result = new java.util.ArrayList();
    try {
      reader.readLine();
        // skip the first line that contains the name and optional message of the Trowable
      String line = reader.readLine();
      while (line != null) {
        line = line.substring(4);
          // get rit of the "\tat " stuff in front of each method in the stack trace
        result.add(line);
        line = reader.readLine();
      }
    }
    catch (java.io.IOException ioExc) {
      /* I don't think this can happen, but we'll stop processing the trace
          if it does happen; this happens next */
    }
    result.trimToSize();
    /* the stack trace of a Throwable lists the calling methods, last one first; in the
        list we are producing, it makes more sense to have the main as the first entry */
    java.util.Collections.reverse(result);
    return result;
  }
}


