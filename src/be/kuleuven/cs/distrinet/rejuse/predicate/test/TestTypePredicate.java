package be.kuleuven.cs.distrinet.rejuse.predicate.test;
import be.kuleuven.cs.distrinet.rejuse.junit.CVSRevision;
import be.kuleuven.cs.distrinet.rejuse.junit.JutilTest;
import be.kuleuven.cs.distrinet.rejuse.predicate.And;
import be.kuleuven.cs.distrinet.rejuse.predicate.Or;
import be.kuleuven.cs.distrinet.rejuse.predicate.TypePredicate;

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
