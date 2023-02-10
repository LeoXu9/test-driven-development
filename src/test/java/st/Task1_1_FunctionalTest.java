package st;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class Task1_1_FunctionalTest {

	private Parser parser;
	
	@Before
	public void setUp() {
		parser = new Parser();
	}

	@Test
	public void bug1() {
		Option o1 = new Option("input", Type.INTEGER);
		parser.addOption(o1);
		assertFalse(parser.shortcutExists(""));
	}
	@Test
	public void bug2() {
		parser.addOption(new Option("input", Type.BOOLEAN), "i");
		parser.parse("--input false");
		assertFalse(parser.getBoolean("input"));
	}
	@Test
	public void bug3() {
		parser.addOption(new Option("input", Type.BOOLEAN), "i");
		parser.parse("--input falsee");
		assertEquals(parser.getInteger("input"), 1);
	}
	@Test
	public void bug4() {
		parser.addOption(new Option("input", Type.STRING), "inputinputinputinput");
		assertTrue(parser.shortcutExists("inputinputinputinput"));
	}
	@Test
	public void bug5() {
		parser.addOption(new Option("input", Type.INTEGER), "i");
		parser.parse("--input=-18");
		//assertEquals(parser.getInteger("input"), -18);
		try{assertEquals(parser.getInteger("input"), -18);
		}catch(Exception e){
			fail("Arithmetic Exception");
		}
	}
	@Test
	public void bug6() {
		Option o1 = new Option("input", Type.STRING);
		Option o2 = new Option("input2", Type.STRING);
		parser.addOption(o1, "i");
		parser.addOption(o2, "i2");
		assertFalse(o1.equals(o2));
	}
	@Test
	public void bug7() {
		Option o1 = new Option("input", Type.STRING);
		parser.addOption(o1, "i");
		parser.parse("--input 12345");
		assertEquals(parser.getInteger("input"), 12345);
	}
	@Test
	public void bug8() {
		Option o1 = new Option("output", Type.INTEGER);
		parser.addOption (o1, "o11" );
		parser.parse("--output 20");
		Option o2 = new Option("output", Type.STRING);
		parser . addOption (o2, "o22" );
		assertEquals(o1.getType(), Type.STRING);
	}

	@Test
	public void bug9() {
		Option o1 = new Option("input", Type.STRING);
		parser.addOption(o1,"i");
		assertEquals(parser.parse(" "), 0);
	}
	@Test
	public void bug10() {
		parser.addOption(new Option("input", Type.CHARACTER), "i");
		parser.parse("--input ");
		assertEquals(parser.getCharacter("input"), '\0');
	}
	@Test (expected=IllegalArgumentException.class)
	public void bug11() {
		Option o1 = new Option("AA@aa", Type.STRING);
		parser.addOption(o1, "i");
		parser.parse("--AA@aa 1.txt");
		assertEquals(parser.getString("AA@aa"), "1.txt");
	}
	@Test
	public void bug12() {
		Option o1 =  new Option ( "opt1" , Type . STRING );
		parser.addOption (o1, "o1");
		parser.parse("--opt1=OldText");
		parser.replace ( "-o1" , "Old" , "New" );
		assertEquals(o1.getValue(),"NewText");
	}
	@Test
	public void bug13() {
		parser.addOption(new Option("input", Type.STRING), "i");
		parser.parse("--input='=1=1'");
		assertEquals(parser.getString("input"), "=1=1");
	}
	@Test
	public void bug14() {
		Option o1 = new Option("input", Type.STRING);
		parser.addOption(o1, "i");
		parser.parse("--input 1.txt\\n");
		assertEquals(parser.getString("input"), "1.txt\\n");
	}
	@Test
	public void bug15() {
		Option o1 = new Option("input", Type.STRING);
		parser.addOption(o1, "i");
		parser.parse("--input 1111111111111111");
		assertEquals(parser.getInteger("input"), 0);
	}
	@Test (expected=NullPointerException.class)
	public void bug16() {
		Option o1 = new Option("input", Type.STRING);
		parser.addOption(o1, "i");
		parser.parse("--input 1.txt");
		parser.getString(null);
	}
	@Test
	public void bug17() {
		Option o1 = new Option("inputinputinputinputinputinputinput", Type.STRING);
		parser.addOption(o1);
		assertTrue(parser.optionExists("inputinputinputinputinputinputinput"));
	}
	@Test
	public void bug18() {
		Option o1 =  new Option ( "opt1" , Type . STRING );
		parser.addOption (o1, "o1");
		parser.parse("--opt1=OldText");
		parser.replace ( "opt1   " , "Old" , "New" );
		assertEquals(o1.getValue(), "NewText");
	}
	@Test
	public void bug19() {
		try{
			Option o1 = new Option("input", Type.STRING);
			parser.addOption(o1, "i");
			parser.parse("--input \"-a\"");
			assertEquals(parser.getString("input"), "-a");
		}
		catch(Exception e) {
			fail();
		}
	}
	@Test
	public void bug20() {
		parser.addOption(new Option("input", Type.STRING), "i");
		parser.parse("--input {D_QUOTE}A{S_QUOTE}{SPACE}{DASH} and {EQUALS}.");
		assertEquals(parser.getString("input"), "\"A' -");
	}
}
