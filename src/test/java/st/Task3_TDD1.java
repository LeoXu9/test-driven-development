package st;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Task3_TDD1 {
	
	private Parser parser;
	
	@Before
	public void setUp() {
		parser = new Parser();
	}
	
	@Test 
	// 4 names, 4 shortcuts, 4 types
	public void t1() {
		parser.addAll("option1 option2 option3 option4", "o1 o2 o3 o4", "String Integer Boolean Character");
		parser.parse("-o1=test -o2=33 -o3=true --option4=a");
		assertEquals(parser.getString("option1"), "test");
		assertEquals(parser.getInteger("option2"), 33);
		assertEquals(parser.getBoolean("option3"), true);
		assertEquals(parser.getCharacter("option4"), 'a');
	}
	
	@Test
	// 1 name, 1 shortcut, 1 type
	public void t2() {
		parser.addAll("option1", "o1", "String");
		parser.parse("--option1 a123");
		assertEquals(parser.getString("option1"), "a123");
	}
	
	@Test
	// 4 names, 4 shortcuts, 1 type
	public void t3() {
		parser.addAll("option1 option2 option3 option4", "o1 o2 o3 o4", "String");
		parser.parse("-o1=a123 -o2=b123 -o3=c123 -o4=d123");
		assertEquals(parser.getString("option1"), "a123");
		assertEquals(parser.getString("option2"), "b123");
		assertEquals(parser.getString("option3"), "c123");
		assertEquals(parser.getString("option4"), "d123");
	}
	
	@Test
	//4 names, 4 shortcuts, 2 types
	public void t4() {
		parser.addAll("option1 option2 option3 option4", "o1 o2 o3 o4", "String Integer");
		parser.parse("-o1=a123 -o2=123 -o3=124 -o4=125");
		assertEquals(parser.getString("option1"), "a123");
		assertEquals(parser.getInteger("option2"), 123);
		assertEquals(parser.getInteger("option3"), 124);
		assertEquals(parser.getInteger("option4"), 125);
	}
	
	@Test 
	// 1 name, 1 shortcuts, 2 types, 2nd type is omitted
	public void t5() {
		parser.addAll("option1", "o1", "String Integer");
		parser.parse("-o1=a123");
		assertEquals(parser.getString("option1"), "a123");
	}
	
	@Test
	// extra spaces are omitted
	public void t6() {
		parser.addAll("option1  option2  option3  option4", "o1   o2   o3   o4", "String    Integer");
		parser.parse("-o1=a123 -o2=123 -o3=124 -o4=125");
		assertEquals(parser.getString("option1"), "a123");
		assertEquals(parser.getInteger("option2"), 123);
		assertEquals(parser.getInteger("option3"), 124);
		assertEquals(parser.getInteger("option4"), 125);
	}
	
	@Test
	// 4 names, 3 shortcuts, 4 types
	public void t7() {
		parser.addAll("option1 option2 option3 option4", "o1 o2 o3", "String Integer Boolean Character");
		parser.parse("-o1=test -o2=33 -o3=true --option4=a");
		assertTrue(parser.optionExists("option4")&!parser.shortcutExists(""));
	}
	
	@Test 
	// 3 names, 4 shortcuts, 4 types
	public void t8() {
		parser.addAll("option1 option2 option3", "o1 o2 o3 o4", "String Integer Boolean Character");
		parser.parse("-o1=test -o2=33 -o3=true");
		assertEquals(parser.getString("option1"), "test");
		assertEquals(parser.getInteger("option2"), 33);
		assertEquals(parser.getBoolean("option3"), true);
	}
	
	@Test
	//digit as 1st character in option name
	//this option is omitted
	//along with the corresponding shortcut and type
	public void t9() {
		parser.addAll("1option option2 option3 option4", "o1 o2 o3", "String Integer Boolean Character");
		parser.parse("-o2=33 -o3=true");
		assertEquals(parser.getInteger("option2"), 33);
		assertEquals(parser.getBoolean("option3"), true);
	}
	
	@Test
	//@ in option name
	//this option is omitted
	//along with the corresponding shortcut and type
	public void t10() {
		parser.addAll("@option option2 option3 option4", "o1 o2 o3 o4", "String Integer Boolean Character");
		parser.parse("-o2=33 -o3=true -o4=a");
		assertEquals(parser.getInteger("option2"), 33);
		assertEquals(parser.getBoolean("option3"), true);
		assertEquals(parser.getCharacter("option4"), 'a');
	}
	
	@Test
	// @ in shortcut
	// this option is omitted
	// along with the corresponding shortcut and type
	public void t11() {
		parser.addAll("option1 option2 option3", "@o1 o2 o3", "String Integer Boolean Character");
		parser.parse("-o2=test -o3=33");
		assertEquals(parser.getString("option1"), "test");
		assertEquals(parser.getInteger("option2"), 33);
	}
	
	@Test
	// option names not in the correct form so will be omitted
	public void t12() {
		parser.addAll("option@-3 option1-3", "ob-4", "String");
		assertTrue(!parser.optionExists("optiona")&parser.optionExists("option1"));
	}
	
	@Test 
	// range value is not in the correct form so will be omitted
	public void t13() {
		parser.addAll("optiona-3 optionb-d", "o1-4", "String");
		assertTrue(!parser.optionExists("optiona")&parser.optionExists("optionb"));
	}
	
	@Test 
	// invalid ones are omitted
	public void t14() {
		parser.addAll("optiona-3 optionb-d", "o1-4", "String");
		parser.parse("--optionb=a123");
		assertEquals(parser.getString("o2"), "a123");
	}
	
	@Test 
	// range goes from one character before the hyphen
	public void t15() {
		parser.addAll("g129-11", "o1-3", "String");
		parser.parse("--g129=aa --g1210=bb --g1211=cc");
		assertEquals(parser.getString("g129"), "aa");
		assertEquals(parser.getString("g1210"), "bb");
		assertEquals(parser.getString("g1211"), "cc");
	}
	
	@Test 
	// decreasing range
	public void t16() {
		parser.addAll("g125-2", "oe-a", "String");
		parser.parse("--g125=aa --g124=bb --g123=cc --g122=dd");
		assertEquals(parser.getString("g125"), "aa");
		assertEquals(parser.getString("g124"), "bb");
		assertEquals(parser.getString("g123"), "cc");
		assertEquals(parser.getString("g122"), "dd");
	}
	
	@Test 
	// multiple option groups correspond to one shortcut group
	public void t17() {
		parser.addAll("option1-2 option3-4", "o1-4", "String");
		parser.parse("--option1=a123 --option2=b123 --option3=c123 --option4=d123");
		assertEquals(parser.getString("option1"), "a123");
		assertEquals(parser.getString("option2"), "b123");
		assertEquals(parser.getString("option3"), "c123");
		assertEquals(parser.getString("option4"), "d123");
	}
	
	@Test (expected=IllegalArgumentException.class) 
	// incorrect value in the last string input throws exception
	public void t18() {
		parser.addAll("option1-2 option3-4", "o1-4", "WrongType");
	}
}
