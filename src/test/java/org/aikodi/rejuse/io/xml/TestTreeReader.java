package org.aikodi.rejuse.io.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.aikodi.rejuse.action.Nothing;
import org.junit.Test;

public class TestTreeReader {

	private static class A {
		private String _name;
		private List<B> _b = new ArrayList<>();
		private List<A> _a = new ArrayList<>();
		public A(String name) {
			_name = name;
		}
		
		public String name() {
			return _name;
		}
		
		public void add(B b) {
			if (b == null) {
				throw new IllegalArgumentException();
			}
			_b.add(b);
		}
		
		public void addA(A a) {
			if (a == null) {
				throw new IllegalArgumentException();
			}
			_a.add(a);
		}
		
		public List<B> bs() {
			return new ArrayList<>(_b);
		}

		public List<A> as() {
			return new ArrayList<>(_a);
		}
	}
	
	private static class B {
		private String _name;
		public B(String name) {
			_name = name;
		}
		
		public String name() {
			return _name;
		}
	}
	
	private XMLStreamReader reader(String input) {
		try {
			InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8.name()));
			return javax.xml.stream.XMLInputFactory.newInstance().createXMLStreamReader(stream);
		} catch (UnsupportedEncodingException e) {
			throw new Error(e);
		} catch (XMLStreamException e) {
			throw new Error(e);
		} catch (FactoryConfigurationError e) {
			throw new Error(e);
		}
	}
	
	@Test
	public void testSingleRootNodeFirstInputOneNode() throws XMLStreamException {
		// GIVEN
		//   a tree reader that read 'a' nodes and set their names to 'A name'
		TreeReader<A, Nothing> first = TreeReader.<A, Nothing>builder()
				.child("a").construct(() -> new A("A name"))
				.close()
				.build();
		
		// WHEN
		//   it reads the input '<a></a>'
	    A output = first.read(reader("<a></a>"));
	    
	    // THEN
	    //   the result is not null.
	    assertNotNull(output);
	    //   the result has name 'A name'
	    assertEquals("A name", output.name());
	}

	@Test
	public void testSingleRootNodeFirstInputWrongNode() throws XMLStreamException {
		// GIVEN
		//   a tree reader that read 'a' nodes and set their names to 'A name'
		TreeReader<A, Nothing> first = TreeReader.<A, Nothing>builder()
				.child("a").construct(() -> new A("A name"))
				.close()
				.build();

		// WHEN
		//   it reads an input that does not contain 'a' tags.
		A output = first.read(reader("<b></b>"));
		
		// THEN
		//   the output is null.
	    assertNull(output);
	}

	@Test
	public void testSingleRootNodeFirstInputOneNodeWithAttribute() throws XMLStreamException {
		// GIVEN
		//   a tree reader that read 'a' nodes and set their names to the 
		//   'name' attribute of the tag.
		TreeReader<A, Nothing> first = TreeReader.<A, Nothing>builder()
				.child("a").construct(n -> new A(n.attribute("name")))
				.close()
				.build();
		
		// WHEN
		//   it reads an 'a' node with name 'b'.
	    A output = first.read(reader("<a name=\"b\"></a>"));
	    
	    // THEN
	    //   the result is not null
	    assertNotNull(output);
	    //   the result has the attribute value of the 'name' attribute: 'b'.
	    assertEquals("b", output.name());
	}

	@Test
	public void testSingleRootNodeFirstInputOneNodeWithManyAttributes() throws XMLStreamException {
		// GIVEN
		//   a tree reader that read 'a' nodes and set their names to the 
		//   'name' attribute of the tag.
		TreeReader<A, Nothing> first = TreeReader.<A, Nothing>builder()
				.child("a").construct(n -> new A(n.attribute("name")))
				.close()
				.build();
		
		// WHEN
		//   it reads an 'a' node with name 'b'.
	    A output = first.read(reader("<a firstAttribute=\"something\" name=\"b\"></a>"));
	    
	    // THEN
	    //   the result is not null
	    assertNotNull(output);
	    //   the result has the attribute value of the 'name' attribute: 'b'.
	    assertEquals("b", output.name());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSingleRootNodeFirstInputOneNodeWithWrongAttribute() throws XMLStreamException {
		// GIVEN
		//   a tree reader that read 'a' nodes and set their names to the 
		//   'name' attribute of the tag.
		TreeReader<A, Nothing> first = TreeReader.<A, Nothing>builder()
				.child("a").construct(n -> new A(n.attribute("name")))
				.close()
				.build();
		
		// WHEN
		//   it reads an 'a' node with attribute 'namee' set to 'b' .
	    A output = first.read(reader("<a namee=\"b\"></a>"));
	    
	    // THEN
	    //   the reader throws an exception
	}

	@Test
	public void testSingleRootNodeFirstWithChildInputSingleChild() throws XMLStreamException {
		// GIVEN
		//   a tree reader that reads 'a' nodes and sets their names to 'A name'.
		//   and reads child nodes 'b' and sets their names to 'bee'.
		TreeReader<A, Nothing> first = TreeReader.<A, Nothing>builder()
				.child("a").construct(() -> new A("A name"))
					.child("b").construct(() -> new B("bee"))
					.close((a,b) -> a.add(b))
				.close()
				.build();

		// WHEN
		//   it reads an input that contain a single 'a' tag with 
		//   a single nested 'b' tag.
		A output = first.read(reader("<a><b></b></a>"));
		
		// THEN
		//   the output is not null.
	    assertNotNull(output);
	    //   the output has name 'A name'
	    assertEquals("A name", output.name());
	    //   the output has one child b.
	    assertEquals(1, output.bs().size());
		//   which is not null.
	    assertNotNull(output.bs().get(0));
	    //   and has name 'bee'.
	    assertEquals("bee", output.bs().get(0).name());
	}
	
	@Test
	public void testSingleRootNodeFirstWithChildInputMultipleChildren() throws XMLStreamException {
		// GIVEN
		//   a tree reader that reads 'a' nodes and sets their names to 'A name'.
		//   and reads child nodes 'b' and sets their names to 'bee'.
		TreeReader<A, Nothing> first = TreeReader.<A, Nothing>builder()
				.child("a").construct(() -> new A("A name"))
					.child("b").construct(() -> new B("bee"))
					.close((a,b) -> a.add(b))
				.close()
				.build();

		// WHEN
		//   it reads an input that does not contain 'a' tags.
		A output = first.read(reader("<a><b></b><b></b><b></b><b></b><b></b><b></b><b></b></a>"));
		
		// THEN
		//   the output is not null.
	    assertNotNull(output);
	    //   the output has name 'A name'
	    assertEquals("A name", output.name());
	    //   the output has 7 b children.
	    assertEquals(7, output.bs().size());
	    for (int i = 0; i < 7; i++) {
	    	//   that are not null
	    	assertNotNull(output.bs().get(i));
		    //   and have name 'bee'.
	    	assertEquals("bee", output.bs().get(i).name());
	    }
	}

	@Test
	public void testSingleRootNodeFirstWithChildInputNoDirectChild() throws XMLStreamException {
		// GIVEN
		//   a tree reader that reads 'a' nodes and sets their names to 'A name'.
		//   and reads child nodes 'b' and sets their names to 'bee'.
		TreeReader<A, Nothing> first = TreeReader.<A, Nothing>builder()
				.child("a").construct(() -> new A("A name"))
					.child("b").construct(() -> new B("bee"))
					.close((a,b) -> a.add(b))
				.close()
				.build();

		// WHEN
		//   it reads an input that does not contain 'a' tags.
		A output = first.read(reader("<a><c><b></b></c></a>"));
		
		// THEN
		//   the output is not null.
	    assertNotNull(output);
	    //   the output has name 'A name'
	    assertEquals("A name", output.name());
	    //   the output has one child b.
	    assertEquals(0, output.bs().size());
	}
	
	@Test
	public void testSingleRootNodeFirstWithChildInputSingleChildWithAttribute() throws XMLStreamException {
		// GIVEN
		//   a tree reader that reads 'a' nodes and sets their names to 'A name'
		//   and reads children of node 'b' and sets their name to the 'name' attribute. 
		TreeReader<A, Nothing> first = TreeReader.<A, Nothing>builder()
				.child("a").construct(() -> new A("A name"))
					.child("b").construct(n -> new B(n.attribute("name")))
					.close((a,b) -> a.add(b))
				.close()
				.build();

		// WHEN
		//   it reads an input that contain a single 'a' tag with 
		//   a single nested 'b' tag with attribute 'name' set to 'qubie'.
		A output = first.read(reader("<a><b name=\"qubie\"></b></a>"));
		
		// THEN
		//   the output is not null.
	    assertNotNull(output);
	    //   the output has name 'A name'
	    assertEquals("A name", output.name());
	    //   the output has one child b.
	    assertEquals(1, output.bs().size());
		//   which is not null.
	    assertNotNull(output.bs().get(0));
	    //   and has name 'qubie'.
	    assertEquals("qubie", output.bs().get(0).name());
	}
	
	@Test
	public void testPredefinedTreeReader() throws XMLStreamException {
		// GIVEN
		//   a tree reader that reads 'v' nodes and sets their names to 'bee'. 
		TreeReader<B, Nothing> bReader = TreeReader.<B, Nothing>builder()
				.child("b").construct(() -> new B("bee"))
				.close()
				.build();
		// AND
		//   a tree reader that reads 'a' nodes and sets their names to 'A name'
		//   and reads children of node 'b' using the reader for 'b' nodes. 
		TreeReader<A, Nothing> aReader = TreeReader.<A, Nothing>builder()
				.child("a").construct(() -> new A("A name"))
					.open(bReader)
					.close((a,b) -> a.add(b))
				.close()
				.build();
		
		// WHEN
		//   it reads an input that contain a single 'a' tag with 
		//   a single nested 'b' tag.
		A output = aReader.read(reader("<a><b></b></a>"));
		
		// THEN
		//   the output is not null.
	    assertNotNull(output);
	    //   the output has name 'A name'
	    assertEquals("A name", output.name());
	    //   the output has one child b.
	    assertEquals(1, output.bs().size());
		//   which is not null.
	    assertNotNull(output.bs().get(0));
	    //   and has name 'bee'.
	    assertEquals("bee", output.bs().get(0).name());
	}

	@Test
	public void testPredefinedTreeReaderNoDirectMatch() throws XMLStreamException {
		// GIVEN
		//   a tree reader that reads 'v' nodes and sets their names to 'bee'. 
		TreeReader<B, Nothing> bReader = TreeReader.<B, Nothing>builder()
				.child("b").construct(() -> new B("bee"))
				.close()
				.build();
		// AND
		//   a tree reader that reads 'a' nodes and sets their names to 'A name'
		//   and reads children of node 'b' using the reader for 'b' nodes. 
		TreeReader<A, Nothing> aReader = TreeReader.<A, Nothing>builder()
				.child("a").construct(() -> new A("A name"))
					.open(bReader)
					.close((a,b) -> a.add(b))
				.close()
				.build();
		
		// WHEN
		//   it reads an input that contain a single 'a' tag but no 
		//   directly nested 'b' tag.
		A output = aReader.read(reader("<a><c><b></b></c></a>"));
		
		// THEN
		//   the output is not null.
	    assertNotNull(output);
	    //   the output has name 'A name'
	    assertEquals("A name", output.name());
	    //   the output has one child b.
	    assertEquals(0, output.bs().size());
	}

	@Test
	public void testPredefinedTreeReaderWithMultipleMatchingChildren() throws XMLStreamException {
		// GIVEN
		//   a tree reader that reads 'v' nodes and sets their names to 'bee'. 
		TreeReader<B, Nothing> bReader = TreeReader.<B, Nothing>builder()
				.child("b").construct(() -> new B("bee"))
				.close()
				.build();
		// AND
		//   a tree reader that reads 'a' nodes and sets their names to 'A name'
		//   and reads children of node 'b' using the reader for 'b' nodes. 
		TreeReader<A, Nothing> aReader = TreeReader.<A, Nothing>builder()
				.child("a").construct(() -> new A("A name"))
					.open(bReader)
					.close((a,b) -> a.add(b))
				.close()
				.build();
		
		// WHEN
		//   it reads an input that contain a single 'a' tag with 
		//   4 nested 'b' tags.
		A output = aReader.read(reader("<a><b></b><b></b><b></b><b></b></a>"));
		
		// THEN
		//   the output is not null.
	    assertNotNull(output);
	    //   the output has name 'A name'
	    assertEquals("A name", output.name());
	    //   the output has 4 children of type b.
	    assertEquals(4, output.bs().size());
		for (int i = 0; i < 4; i++) {
			// which are not null.
			assertNotNull(output.bs().get(i));
			// and have name 'bee'.
			assertEquals("bee", output.bs().get(i).name());
		}
	}
	
	@Test
	public void testMap() throws XMLStreamException {
		// GIVEN
		//   a tree reader that read 'a' nodes and set their names to 'A name'
		//   and we create a mapping reader that transforms it into a B whose 
		//   name is "B with " plus the name of the a element. 
		TreeReader<B, Nothing> first = TreeReader.<A, Nothing>builder()
				.child("a").construct(() -> new A("A name"))
				.close()
				.build()
				.map(a -> new B("B with " + a.name()));
		
		// WHEN
		//   it reads the input '<a></a>'
	    B output = first.read(reader("<a></a>"));
	    
	    // THEN
	    //   the result is not null.
	    assertNotNull(output);
	    //   the result is has name 'B with A name'
	    assertEquals("B with A name", output.name());
	}

	@Test
	public void testDescendantSingleRootNodeFirstInputOneNode() throws XMLStreamException {
		// GIVEN
		//   a tree reader that read 'a' nodes and set their names to 'A name'
		TreeReader<A, Nothing> first = TreeReader.<A, Nothing>builder()
				.descendant("a").construct(() -> new A("A name"))
				.close()
				.build();
		
		// WHEN
		//   it reads the input '<a></a>'
	    A output = first.read(reader("<a></a>"));
	    
	    // THEN
	    //   the result is not null.
	    assertNotNull(output);
	    //   the result has name 'A name'
	    assertEquals("A name", output.name());
	}


	@Test
	public void testDescendantSingleRootNodeFirstInputOneNestedNode() throws XMLStreamException {
		// GIVEN
		//   a tree reader that read 'a' nodes and set their names to 'A name'
		TreeReader<A, Nothing> first = TreeReader.<A, Nothing>builder()
				.descendant("a").construct(() -> new A("A name"))
				.close()
				.build();
		
		// WHEN
		//   it reads the input '<a></a>'
	    A output = first.read(reader("<b><a></a></b>"));
	    
	    // THEN
	    //   the result is not null.
	    assertNotNull(output);
	    //   the result has name 'A name'
	    assertEquals("A name", output.name());
	}

	@Test
	public void testDescendantNodeFirstInputTwoNestedNodes() throws XMLStreamException {
		// GIVEN
		//   a tree reader that read 'a' nodes and set their names to the value of the attribute 'name'
		TreeReader<A, Nothing> first = TreeReader.<A, Nothing>builder()
				.descendant("a").construct(n -> new A(n.attribute("name")))
				.close()
				.build();
		
		// WHEN
		//   it reads nested a nodes of which the outer node has name 'outer', and the other nodes have different names.
	    A output = first.read(reader("<a name=\"outer\"><a name=\"inner\"><a name=\"inner2\"></a></a></a>"));
	    
	    // THEN
	    //   the result is not null.
	    assertNotNull(output);
	    //   the result has the name 'outer'
	    assertEquals("outer", output.name());
	}

	@Test
	public void testDescendantNodeFirstInputTwoNestedNodesInRootNode() throws XMLStreamException {
		// GIVEN
		//   a tree reader that read 'a' nodes and set their names to the value of the attribute 'name'
		TreeReader<A, Nothing> first = TreeReader.<A, Nothing>builder()
				.descendant("a").construct(n -> new A(n.attribute("name")))
				.close()
				.build();
		
		// WHEN
		//   it reads nested 'a' nodes inside a 'b' node, of which the outer node has name 'outer'
		//   , and the other nodes have different names.
	    A output = first.read(reader("<b><a name=\"outer\"><a name=\"inner\"><a name=\"inner2\"></a></a></a></b>"));
	    
	    // THEN
	    //   the result is not null.
	    assertNotNull(output);
	    //   the result has the name 'outer'
	    assertEquals("outer", output.name());
	}

	@Test
	public void testDescendantNodeFirstInputPredefined() throws XMLStreamException {
		// GIVEN
		//   a tree reader that read 'a' nodes and set their names to 'A name'
		TreeReader<A, Nothing> first = TreeReader.<A, Nothing>builder()
				.descendant("a").construct(n -> new A(n.attribute("name")))
				.close()
				.build();
		
		// AND a tree reader that constructs A object from 'a' nodes,
		//     and uses the first reader to read its children.
		TreeReader<A, Nothing> second = TreeReader.<A, Nothing>builder()
				.child("a").construct(() -> new A("A name"))
					.open(first)
					.close((a,b) -> a.addA(b))
				.close()
				.build();
		
	
		// WHEN
		//   it reads the input an a node with two nested a nodes with names inner and inner2
		//   and inner has yet another nested 'a' node.
	    A output = second.read(reader("<a name=\"outer\"><a name=\"inner\"><a name=\"inner3\"></a></a><a name=\"inner2\"></a></a>"));
	    
	    // THEN
	    //   the result is not null.
	    assertNotNull(output);
	    //   the result has name 'A name'
	    assertEquals("A name", output.name());
	    //   the result has two nested A objects.
	    assertEquals(2, output.as().size());
	    //   the first one of which has name 'inner'
	    assertEquals("inner", output.as().get(0).name());
	    //   and no children (inner3)
	    assertEquals(0, output.as().get(0).as().size());
	    //   the second one of which has name 'inner2'
	    assertEquals("inner2", output.as().get(1).name());
	    
	}
}
