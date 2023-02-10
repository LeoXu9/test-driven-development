package st;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class Task2_2_FunctionalTest {

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
	
	@Test
	public void t_equals() {
		Option o1 = new Option("input", Type.STRING);
		Option o2 = new Option("input2", Type.STRING);
		parser.addOption(o1, "i");
		parser.addOption(o2, "i2");
		assertFalse(o1.equals(null));
	}
	
	@Test
	public void t_equals2() {
		Option o1 = new Option("input", Type.STRING);
		parser.addOption(o1, "i");
		o1.equals(o1);
	}
	
	@Test (expected=ClassCastException.class)
	public void t_equals3() {
		Option o1 = new Option("input", Type.STRING);
		Option o2 = new Option("input2", Type.STRING);
		parser.addOption(o1, "i");
		parser.addOption(o2, "i2");
		String a = "a";
		assertFalse(o1.equals(a));
	}
	
	@Test
	public void t_equals4() {
		Option o1 = new Option("input", Type.STRING);
		Option o2 = new Option("input2", Type.INTEGER);
		parser.addOption(o1, "i");
		parser.addOption(o2, "i2");
		assertFalse(o1.equals(o2));
	}
	
	@Test
	public void t_equals5() {
		Option o1 = new Option("input", Type.STRING);
		parser.addOption(o1, "i");
		Option o2 = new Option("input", Type.INTEGER);
		parser.addOption(o2, "i2");
		assertTrue(o1.equals(o2));
	}
	
	@Test
	public void t_equals6() {
		Option o1 = new Option("input", Type.STRING);
		Option o2 = new Option("input2", Type.INTEGER);
		parser.addOption(o1, "i");
		parser.addOption(o2, "i2");
		assertFalse(o1.equals(o2));
	}
	
	@Test
	public void t_equals7() {
		Option o1 = new Option("input", Type.STRING);
		Option o2 = new Option("input2", Type.INTEGER);
		parser.addOption(o1, "i");
		parser.addOption(o2, "i2");
		assertFalse(o1.equals(o2));
	}
	
	@Test
	public void t_parse_unescaped() {
		Option o1 = new Option("input", Type.BOOLEAN);
		parser.addOption(o1, "i");
		parser.parse("--input \'a");
	}
	
	@Test
	public void t_boolean_explicit_conversion() {
		Option o1 = new Option("input", Type.BOOLEAN);
		parser.addOption(o1, "i");
		parser.parse("--input 0");
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void t_shortcut_invalid() {
		Option o1 = new Option("input", Type.STRING);
		parser.addOption(o1, "@");
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void t_shortcut_invalid2() {
		Option o1 = new Option("input", Type.STRING);
		parser.addOption(o1, null);
	}
	
	@Test
	public void t_replace1() {
		Option o1 =  new Option ("option1", Type.STRING);
		parser.addOption (o1, "o1");
		parser.parse("--option1=OldText");
		parser.replace ("--option1", "Old", "New");
		assertEquals(o1.getValue(),"NewText");
	}
	
	@Test
	public void t_replace2() {
		Option o1 =  new Option ("option1", Type.STRING);
		parser.addOption (o1, "o1");
		parser.parse("--option1=OldText");
		parser.replace ("-o1", "Old", "New");
		assertEquals(o1.getValue(),"NewText");
	}
	
	@Test (expected=RuntimeException.class)
	public void t_replace3() {
		Option o1 =  new Option ("option1", Type.STRING);
		parser.addOption (o1, "o1");
		parser.parse("--option1=OldText");
		parser.replace ("-o2", "Old", "New");
		assertEquals(o1.getValue(),"NewText");
	}
	
	@Test
	public void t_replace4() {
		Option o1 =  new Option ("option1", Type.STRING);
		parser.addOption (o1, "o1");
		parser.parse("--option1=OldText");
		parser.replace ("o1", "Old", "New");
		assertEquals(o1.getValue(),"NewText");
	}
	
	@Test (expected=RuntimeException.class)
	public void t_replace5() {
		Option o1 =  new Option ("option1", Type.STRING);
		parser.addOption (o1, "o1");
		parser.parse("--option1=OldText");
		parser.replace ("--o2", "Old", "New");
		assertEquals(o1.getValue(),"NewText");
	}
	
	@Test (expected=RuntimeException.class)
	public void t_replace6() {
		Option o1 =  new Option ("option1", Type.STRING);
		parser.addOption (o1, "o1");
		parser.parse("--option1=OldText");
		parser.replace ("o2", "Old", "New");
		assertEquals(o1.getValue(),"NewText");
	}
	
	@Test
	public void t_spec16_1_no() {
		parser.addOption(new Option("input", Type.STRING), "i");
		parser.addOption(new Option("i2", Type.STRING), "input");
		parser.parse("--input 1.txt");
		parser.parse("--i2 2.txt");
		assertEquals(parser.getString("-input"), "2.txt");
	}
	
	@Test
	public void t_func() {
		Option o1 = new Option("in", Type.STRING);
		o1.setName("input");
		parser.addOption(o1);
		parser.setShortcut("o1", "i");
		parser.parse("--input 1.txt");
		parser.toString();
		parser.optionExists("in");
		parser.shortcutExists("in");
		parser.optionOrShortcutExists("in");
		parser.optionOrShortcutExists("i");
		assertEquals(parser.getString("input"), "1.txt");
	}

	@Test
	public void t_parse_O() {
		parser.addOption(new Option("input", Type.STRING), "i");
		parser.addOption(new Option("output", Type.STRING), "O");
		parser.parse("--input 1.txt --output=2.txt");
		parser.parse("O");
		assertEquals(parser.getString("input"), "1.txt");
		assertEquals(parser.getString("O"), "");
	}
	
	@Test
	public void t1_spec15_null() {
		Option o1 = new Option("input", Type.STRING);
		parser.addOption(o1,"i");
		assertEquals(parser.parse(null), -1);
	}
	
	@Test
	public void t1_spec15() {
		parser.addOption(new Option("input", Type.STRING),"i");
		parser.parse("");
	}
	
	@Test
	public void t1_spec13_4_no() {
		parser.addOption(new Option("output", Type.INTEGER), "o");
		parser.addOption(new Option("o", Type.INTEGER), "O");
		parser.parse("-o 18");
		parser.parse("--o 18");
		assertEquals(parser.getInteger("output"), 18);
		assertEquals(parser.getInteger("o"), 18);
	}

	@Test
	public void t1_int_no() {
		parser.addOption(new Option("input", Type.INTEGER),"i");
		parser.parse("--input 18");
		assertEquals(parser.getInteger("input"), 18);
	}

	@Test
	public void t2_true_no() {
		parser.addOption(new Option("input", Type.BOOLEAN),"i");
		parser.parse("--input 100000");
		assertEquals(parser.getBoolean("input"), true);
	}
	
	@Test
	public void t3_string_no() {
		parser.addOption(new Option("input", Type.STRING),"i");
		parser.parse("--input 1.txt");
		assertEquals(parser.getString("input"), "1.txt");
	}
	
	@Test
	public void t3_string_hello_world_no() {
		parser.addOption(new Option("input", Type.STRING),"i");
		parser.parse("--input Helloworld!");
		assertEquals(parser.getString("input"), "Helloworld!");
	}
	
	@Test
	public void t3_string_empty_no() {
		parser.addOption(new Option("input", Type.STRING),"i");
		parser.parse("--input ");
		assertEquals(parser.getString("input"), "");
	}
	
	@Test
	public void t_getInteger_0_no() {
		parser.addOption(new Option("input", Type.STRING),"i");
		parser.parse("--input asd");
		parser.getInteger("input");
	}
	
	@Test
	public void t_getInteger_false() {
		parser.addOption(new Option("input", Type.BOOLEAN), "i");
		parser.parse("--input false");
		parser.getInteger("input");
	}
	
	@Test
	public void t_getInteger_true_no() {
		parser.addOption(new Option("input", Type.BOOLEAN), "i");
		parser.parse("--input true");
		parser.getBoolean("input");
	}
	
	@Test
	public void t_getInteger_char() {
		parser.addOption(new Option("input", Type.CHARACTER), "i");
		parser.parse("--input a");
		parser.getInteger("input");
	}
	
	@Test
	public void t_opt_exists_no() {
		Option o1 = new Option("input", Type.CHARACTER);
		parser.addOption(o1, "i");
		parser.addOption(o1, "");
	}
	
	@Test
	public void t_ex2() {
		Option o1 = new Option("i", Type.CHARACTER);
		parser.addOption(o1, "i");
		parser.optionOrShortcutExists("i");
	}
	
	@Test
	public void t_ex4_no() {
		Option o1 = new Option("input", Type.CHARACTER);
		parser.addOption(o1, "i");
		parser.optionOrShortcutExists("i");
	}
	
	@Test
	public void t_setsc2() {
		Option o1 = new Option("input", Type.CHARACTER);
		parser.addOption(o1);
		parser.setShortcut("input","i");
	}
	
	@Test
	public void t_setsc_no() {
		parser.setShortcut("s2", "a");
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void t_isOptionValid() {
		Option o1 = new Option(null, Type.CHARACTER);
		parser.addOption(o1, "i");
		parser.addOption(o1, "");
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void t_isOptionValid2() {
		Option o1 = new Option("", Type.CHARACTER);
		parser.addOption(o1, "i");
		parser.addOption(o1, "");
	}
	
	@Test (expected=RuntimeException.class)
	public void t_getstring() {
		parser.addOption(new Option("input", Type.STRING), "i");
		parser.parse("--input --true");
		parser.getString("--input");
	}
	
	@Test
	public void t_notype_no() {
		try{Option o1 = new Option("input", Type.NOTYPE);
		}catch(IllegalArgumentException i){fail("");}
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void t_notype2_no() {
		parser.addOption(new Option("input", Type.NOTYPE), "i");
	}
	
	@Test
	public void t4_upper_no() {
		parser.addOption(new Option("input", Type.CHARACTER),"i");
		for(char c = 'A'; c <= 'Z'; ++c) {
			parser.parse("--input "+c);
			assertEquals(parser.getCharacter("input"), c);
		}
	}
	

	@Test
	public void t4_lower() {
		parser.addOption(new Option("input", Type.CHARACTER),"i");
		for(char c = 'a'; c <= 'z'; ++c) {
			parser.parse("--input "+c);
			assertEquals(parser.getCharacter("input"), c);
		}
	}
}
