package org.aikodi.rejuse.predicate;

import org.aikodi.rejuse.action.Nothing;

/**
 * A convenience class of predicate thay do not throw exceptions.
 *
 * @deprecated Simply use Nothing as the exception parameter. Lack of
 *             Multiple inheritance makes SafePredicate too annoying to use.
 * @author  Marko van Dooren
 */
public abstract class SafePredicate<T> extends AbstractPredicate<T,Nothing> {


}