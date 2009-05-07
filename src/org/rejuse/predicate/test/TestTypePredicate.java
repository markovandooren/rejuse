package org.rejuse.predicate.test;
import org.rejuse.junit.JutilTest;
import org.rejuse.junit.CVSRevision;
import org.rejuse.predicate.TypePredicate;
import org.rejuse.predicate.Or;
import org.rejuse.predicate.And;
import java.util.Collection;
import java.util.ArrayList;

public class TestTypePredicate extends JutilTest {

  public TestTypePredicate(String name) {
    super(name, new CVSRevision("1.3"));
  }

  public void setup() {
  }

  public void testTypePredicate() {
    Collection and = new ArrayList();
    Collection or = new ArrayList();
    and.add(new And());
    and.add(new And());
    and.add(new And());
    and.add(new And());
    or.add(new Or());
    or.add(new Or());
    or.add(new Or());
    assertTrue(new TypePredicate(And.class).forall(and));
    assertTrue(new TypePredicate(Or.class).forall(or));
    assertTrue(! new TypePredicate(And.class).exists(or));
    assertTrue(! new TypePredicate(Or.class).exists(and));
  }
}
