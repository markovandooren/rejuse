package org.aikodi.rejuse.event.test;

import java.util.EventListener;
import java.util.EventObject;

import org.aikodi.rejuse.event.EventSourceSupport;
import org.aikodi.rejuse.event.Notifier;
import org.aikodi.rejuse.junit.CVSRevision;
import org.aikodi.rejuse.junit.JutilTest;

/**
 * @path    $Source$
 * @version $Revision$
 * @date    $Date$
 * @state   $State$
 * @author  Jan Dockx
 * @release $Name$
 */
public class TestEventSourceSupport extends JutilTest {
  
  public TestEventSourceSupport(String name) {
    super(name, new CVSRevision("1.5"));
  }
  
  public void setUp() {
    // NOP
  }
  
  private class NANotifier implements Notifier {

    public void notifyEventListener(EventListener listener, EventObject event) {
      ((NAEventListener)listener).notification(event);
    }

  }
  
  private class NAEventListener implements EventListener {
    
    public void notification(EventObject event) {
      $counter++;
      $event = event;
    }

    public EventObject notificationCalled() {
      EventObject result = $event;
      $event = null;
      $counter = 0;
      return result;            
    }
    
    public int getCounter() {
      return $counter;
    }
    
    private int $counter = 0;
    private EventObject $event = null;
    
  }
  
  public void test_EventSourceSupport_() {
    assertTrue(new EventSourceSupport().isEmpty());
  }
  
  public void test_administration_and_notification() {
    EventSourceSupport ss = new EventSourceSupport();
    Notifier notifier = new NANotifier();
    EventObject eventA = new EventObject(this);
    EventObject eventB = new EventObject(this);
    EventObject eventC = new EventObject(this);
    NAEventListener el1 = new NAEventListener();
    ss.add(el1);
    assertTrue(! ss.isEmpty());
    ss.fireEvent(eventA, notifier);
    assertEquals(el1.getCounter(), 1);
    assertEquals(eventA, el1.notificationCalled());
    ss.add(el1);
    assertTrue(! ss.isEmpty());
    ss.fireEvent(eventB, notifier);
    assertEquals(el1.getCounter(), 1);
    assertEquals(eventB, el1.notificationCalled());
    NAEventListener el2 = new NAEventListener();
    ss.add(el2);
    assertTrue(! ss.isEmpty());
    ss.fireEvent(eventA, notifier);
    assertEquals(el1.getCounter(), 1);
    assertEquals(eventA, el1.notificationCalled());
    assertEquals(el2.getCounter(), 1);
    assertEquals(eventA, el2.notificationCalled());
    ss.add(el2);
    assertTrue(! ss.isEmpty());
    ss.fireEvent(eventC, notifier);
    assertEquals(el1.getCounter(), 1);
    assertEquals(eventC, el1.notificationCalled());
    assertEquals(el2.getCounter(), 1);
    assertEquals(eventC, el2.notificationCalled());
    NAEventListener el3 = new NAEventListener();
    NAEventListener el4 = new NAEventListener();
    ss.add(el3);
    ss.add(el4);
    ss.add(el3);
    assertTrue(! ss.isEmpty());
    ss.fireEvent(eventA, notifier);
    assertEquals(el1.getCounter(), 1);
    assertEquals(eventA, el1.notificationCalled());
    assertEquals(el2.getCounter(), 1);
    assertEquals(eventA, el2.notificationCalled());
    assertEquals(el3.getCounter(), 1);
    assertEquals(eventA, el3.notificationCalled());
    assertEquals(el4.getCounter(), 1);
    assertEquals(eventA, el4.notificationCalled());
    ss.remove(el2);
    assertTrue(! ss.isEmpty());
    ss.fireEvent(eventB, notifier);
    assertEquals(el1.getCounter(), 1);
    assertEquals(eventB, el1.notificationCalled());
    assertEquals(el2.getCounter(), 0);
    assertEquals(null, el2.notificationCalled());
    assertEquals(el3.getCounter(), 1);
    assertEquals(eventB, el3.notificationCalled());
    assertEquals(el4.getCounter(), 1);
    assertEquals(eventB, el4.notificationCalled());
    ss.remove(el1);
    assertTrue(! ss.isEmpty());
    ss.fireEvent(eventC, notifier);
    assertEquals(el1.getCounter(), 0);
    assertEquals(null, el1.notificationCalled());
    assertEquals(el2.getCounter(), 0);
    assertEquals(null, el2.notificationCalled());
    assertEquals(el3.getCounter(), 1);
    assertEquals(eventC, el3.notificationCalled());
    assertEquals(el4.getCounter(), 1);
    assertEquals(eventC, el4.notificationCalled());
    ss.remove(el1);
    assertTrue(! ss.isEmpty());
    ss.fireEvent(eventA, notifier);
    assertEquals(el1.getCounter(), 0);
    assertEquals(null, el1.notificationCalled());
    assertEquals(el2.getCounter(), 0);
    assertEquals(null, el2.notificationCalled());
    assertEquals(el3.getCounter(), 1);
    assertEquals(eventA, el3.notificationCalled());
    assertEquals(el4.getCounter(), 1);
    assertEquals(eventA, el4.notificationCalled());
    ss.remove(el4);
    assertTrue(! ss.isEmpty());
    ss.fireEvent(eventA, notifier);
    assertEquals(el1.getCounter(), 0);
    assertEquals(null, el1.notificationCalled());
    assertEquals(el2.getCounter(), 0);
    assertEquals(null, el2.notificationCalled());
    assertEquals(el3.getCounter(), 1);
    assertEquals(eventA, el3.notificationCalled());
    assertEquals(el4.getCounter(), 0);
    assertEquals(null, el4.notificationCalled());
    ss.remove(el3);
    assertTrue(ss.isEmpty());
    ss.fireEvent(eventC, notifier);
    assertEquals(el1.getCounter(), 0);
    assertEquals(null, el1.notificationCalled());
    assertEquals(el2.getCounter(), 0);
    assertEquals(null, el2.notificationCalled());
    assertEquals(el3.getCounter(), 0);
    assertEquals(null, el3.notificationCalled());
    assertEquals(el4.getCounter(), 0);
    assertEquals(null, el4.notificationCalled());
    ss.add(el1);
    assertTrue(! ss.isEmpty());
    ss.fireEvent(eventB, notifier);
    assertEquals(el1.getCounter(), 1);
    assertEquals(eventB, el1.notificationCalled());
  }

  public void test_toString() {
    EventSourceSupport ss = new EventSourceSupport();
    NAEventListener el1 = new NAEventListener();
    ss.add(el1);
    ss.add(el1);
    NAEventListener el2 = new NAEventListener();
    ss.add(el2);
    NAEventListener el3 = new NAEventListener();
    ss.add(el3);
    NAEventListener el4 = new NAEventListener();
    ss.add(el4);
    ss.add(el3);
    ss.add(el4);
    String result = ss.toString();
    int el1i1 = result.indexOf(el1.toString());
    int el1i2 = result.indexOf(el1.toString(), el1i1 + 1);
    int el2i1 = result.indexOf(el2.toString());
    int el2i2 = result.indexOf(el2.toString(), el2i1 + 1);
    int el3i1 = result.indexOf(el3.toString());
    int el3i2 = result.indexOf(el3.toString(), el3i1 + 1);
    int el4i1 = result.indexOf(el4.toString());
    int el4i2 = result.indexOf(el4.toString(), el4i1 + 1);
    assertTrue(el1i1 != -1);
    assertTrue(el1i2 == -1);
    assertTrue(el2i1 != -1);
    assertTrue(el2i2 == -1);
    assertTrue(el3i1 != -1);
    assertTrue(el3i2 == -1);
    assertTrue(el4i1 != -1);
    assertTrue(el4i2 == -1);
  }
    
  public void tearDown() {
    //nothing actually
  }

}
