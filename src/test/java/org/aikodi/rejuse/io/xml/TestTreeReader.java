package org.aikodi.rejuse.io.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
		public A(String name) {
			_name = name;
		}
		
		public String name() {
			return _name;
		}
		
		public void add(B b) {
			_b.add(b);
		}
		
		public List<B> bs() {
			return new ArrayList<>(_b);
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
				.open("a", () -> new A("A name"))
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
				.open("a", () -> new A("A name"))
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
	public void testSingleRootNodeFirstInputOneNodeWithAttributes() throws XMLStreamException {
		// GIVEN
		//   a tree reader that read 'a' nodes and set their names to the 
		//   'name' attribute of the tag.
		TreeReader<A, Nothing> first = TreeReader.<A, Nothing>builder()
				.open("a", n -> new A(n.attribute("name")))
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
	public void testSingleRootNodeFirstWithChildInputSingleChild() throws XMLStreamException {
		// GIVEN
		//   a tree reader that read 'a' nodes and set their names to 'A name'
		TreeReader<A, Nothing> first = TreeReader.<A, Nothing>builder()
				.open("a", () -> new A("A name"))
					.open("b", () -> new B("bee"))
					.close((a,b) -> a.add(b))
				.close()
				.build();

		// WHEN
		//   it reads an input that does not contain 'a' tags.
		A output = first.read(reader("<a><b></b></a>"));
		
		// THEN
		//   the output is not null.
	    assertNotNull(output);
	    //   the output has name 'A name'
	    assertEquals("A name", output.name());
	    //   the output has one child b.
	    assertEquals(1, output.bs().size());
	    //   with name 'bee'.
	    assertEquals("bee", output.bs().get(0).name());
	}
	
	@Test
	public void testSingleRootNodeFirstWithChildInputNoDirectChild() throws XMLStreamException {
		// GIVEN
		//   a tree reader that read 'a' nodes and set their names to 'A name'
		TreeReader<A, Nothing> first = TreeReader.<A, Nothing>builder()
				.open("a", () -> new A("A name"))
					.open("b", () -> new B("bee"))
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
	
//	@Test
//	public void testSingleRootNodeFirstInputMultipleNodes() throws XMLStreamException {
//		TreeReader<A, Nothing> first = TreeReader.<A, Nothing>builder()
//				.open("a", () -> new A("A name"))
//				.close()
//				.build();
//	    List<A> read = first.read(reader("<a></a><a></a><a></a><a></a><a></a><a></a><a></a>"));
//	    assertNotNull(read);
//	    assertEquals(7, read.size());
//	    for (int i = 0; i < 7; i++) {
//	    	assertEquals("A name", read.get(i).name());
//	    }
//	}

}
