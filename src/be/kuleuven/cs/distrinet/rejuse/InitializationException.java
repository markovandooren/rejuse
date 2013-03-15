package be.kuleuven.cs.distrinet.rejuse;

/**
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @author  Marko van Dooren
 */
public class InitializationException extends RuntimeException{
  
  /**
   * Initialize a new InitializationException with the given
   * message
   *
   * @param message
   *        The message
   */
 /*@
   @ public behavior
   @
   @ post getMessage() == message;
   @*/
  public InitializationException(String message) {
    super(message);
  }
}

