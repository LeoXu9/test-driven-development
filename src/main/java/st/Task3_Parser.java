package st;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

	private OptionMap optionMap;

	public Parser() {
		optionMap = new OptionMap();
	}

	public void addOption(Option option, String shortcut) {
		optionMap.store(option, shortcut);
	}

	public void addOption(Option option) {
		optionMap.store(option, "");
	}

	public void addAll(String options, String shortcuts, String types) {

		// ------------------Initialisation------------------
		options = options.trim().replaceAll(" +", " ");
		shortcuts = shortcuts.trim().replaceAll(" +", " ");
		types = types.trim().replaceAll(" +", " ");
		List<String> expandedOptions = new ArrayList<String>();
		List<String> expandedShortcuts = new ArrayList<String>();
		List<String> expandedTypes = new ArrayList<String>();
		String[] splitResult1 = options.split(" ");
		String[] splitResult2 = shortcuts.split(" ");
		String[] splitResult3 = types.split(" ");
		List<Integer> invalid = new ArrayList<Integer>();

		// ------------------Option and Type Expansion------------------
		for (int i = 0; i < splitResult1.length; i++) {
			if (splitResult3.length > i) {
				expandedTypes.add(splitResult3[i]);
			}
			String option = splitResult1[i];
			if (option.equals("") | option.equals(null)) {
				// input is null, empty or blank
				throw new IllegalArgumentException("options cannot be empty");
			} else if (!option.contains("-")) {
				// option is not group initialisation
				if (!option.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
					expandedOptions.add(option);
					invalid.add(i);
				} else {
					expandedOptions.add(option);
				}

			} else if (option.contains("-")) {
				// group initialisation
				if (!option.matches("(([A-Za-z0-9_])+(([A-Z]-[A-Z]+)|[a-z]-[a-z]+|[0-9]-[0-9]+))")) {
					expandedOptions.add(option);
					invalid.add(i);
				} else {
					// split in two parts (base and range)
					int hyphenIndex = option.indexOf('-');
					String base = option.substring(0, hyphenIndex - 1);
					String range = option.substring(hyphenIndex - 1);

					// find the start and end of the range
					String rangeStart = range.substring(0, 1);
					String rangeEnd = range.substring(2);

					// expand the range into a list (lower case letters)
					List<String> rangeList = new ArrayList<String>();

					char[] alphabetLower = "abcdefghijklmnopqrstuvwxyz".toCharArray();
					if (Character.isLetter(rangeStart.charAt(0)) & Character.isLowerCase(rangeStart.charAt(0))) {
						char start = rangeStart.charAt(0);
						char end = rangeEnd.charAt(0);
						int startIndex = new String(alphabetLower).indexOf(start);
						int endIndex = new String(alphabetLower).indexOf(end);
						if (startIndex < endIndex) {
							for (int k = startIndex; k <= endIndex; k++) {
								rangeList.add(Character.toString(alphabetLower[k]));
							}
						} else if (startIndex > endIndex) {
							for (int l = startIndex; l >= endIndex; l--) {
								rangeList.add(Character.toString(alphabetLower[l]));
							}
						} else {
							throw new IllegalArgumentException("range start is equal to end");
						}
					}

					// expand the range into a list (upper case letters)
					char[] alphabetUpper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
					if (Character.isLetter(rangeStart.charAt(0)) & Character.isUpperCase(rangeStart.charAt(0))) {
						char start = rangeStart.charAt(0);
						char end = rangeEnd.charAt(0);
						int startIndex = new String(alphabetUpper).indexOf(start);
						int endIndex = new String(alphabetUpper).indexOf(end);
						if (startIndex < endIndex) {
							for (int k = startIndex; k <= endIndex; k++) {
								rangeList.add(Character.toString(alphabetUpper[k]));
							}
						} else if (startIndex > endIndex) {
							for (int l = startIndex; l >= endIndex; l--) {
								rangeList.add(Character.toString(alphabetUpper[l]));
							}
						} else {
							throw new IllegalArgumentException("range start is equal to end");
						}
					}

					// expand the range into a list (numbers)
					if (Character.isDigit(rangeStart.charAt(0))) {
						int start = Integer.parseInt(rangeStart);
						int end = Integer.parseInt(rangeEnd);
						if (start < end) {
							for (int k = start; k <= end; k++) {
								rangeList.add(Integer.toString(k));
							}
						} else if (start > end) {
							for (int l = start; l >= end; l--) {
								rangeList.add(Integer.toString(l));
							}
						} else {
							throw new IllegalArgumentException("range start is equal to end");
						}
					}
					for (int c = 0; c < rangeList.size(); c++) {
						expandedOptions.add(base.concat(rangeList.get(c)));
					}
				}
			}

		}

		// ------------------Shortcut Expansion------------------
		for (int y = 0; y < splitResult2.length; y++) {
			String shortcut = splitResult2[y];
			if (shortcut.equals("") | shortcut.equals(null)) {
				// input is null, empty or blank
				throw new IllegalArgumentException("shortcuts cannot be empty");
			} else if (!shortcut.contains("-")) {
				// shortcut is not group initialisation
				if (!shortcut.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
				} else {
					expandedShortcuts.add(shortcut);
				}
			} else if (shortcut.contains("-")) {
				// group initialisation
				if (!shortcut.matches("(([A-Za-z0-9_])+(([A-Z]-[A-Z]+)|[a-z]-[a-z]+|[0-9]-[0-9]+))")) {
				} else {
					// split in two parts (base and range)
					int hyphenIndex = shortcut.indexOf('-');
					String base = shortcuts.substring(0, hyphenIndex - 1);
					String range = shortcuts.substring(hyphenIndex - 1);

					// find the start and end of the range
					String rangeStart = range.substring(0, 1);
					String rangeEnd = range.substring(2);

					// expand the range into a list (lower case letters)
					List<String> rangeList2 = new ArrayList<String>();

					char[] alphabetLower = "abcdefghijklmnopqrstuvwxyz".toCharArray();
					if (Character.isLetter(rangeStart.charAt(0)) & Character.isLowerCase(rangeStart.charAt(0))) {
						char start = rangeStart.charAt(0);
						char end = rangeEnd.charAt(0);
						int startIndex = new String(alphabetLower).indexOf(start);
						int endIndex = new String(alphabetLower).indexOf(end);
						if (startIndex < endIndex) {
							for (int k = startIndex; k <= endIndex; k++) {
								rangeList2.add(Character.toString(alphabetLower[k]));
							}
						} else if (startIndex > endIndex) {
							for (int l = startIndex; l >= endIndex; l--) {
								rangeList2.add(Character.toString(alphabetLower[l]));
							}
						} else {
							throw new IllegalArgumentException("range start is equal to end");
						}
					}

					// expand the range into a list (upper case letters)
					char[] alphabetUpper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
					if (Character.isLetter(rangeStart.charAt(0)) & Character.isUpperCase(rangeStart.charAt(0))) {
						char start = rangeStart.charAt(0);
						char end = rangeEnd.charAt(0);
						int startIndex = new String(alphabetUpper).indexOf(start);
						int endIndex = new String(alphabetUpper).indexOf(end);
						if (startIndex < endIndex) {
							for (int k = startIndex; k <= endIndex; k++) {
								rangeList2.add(Character.toString(alphabetUpper[k]));
							}
						} else if (startIndex > endIndex) {
							for (int l = startIndex; l >= endIndex; l--) {
								rangeList2.add(Character.toString(alphabetUpper[l]));
							}
						} else {
							throw new IllegalArgumentException("range start is equal to end");
						}
					}

					// expand the range into a list (numbers)
					if (Character.isDigit(rangeStart.charAt(0))) {
						int start = Integer.parseInt(rangeStart);
						int end = Integer.parseInt(rangeEnd);
						if (start < end) {
							for (int k = start; k <= end; k++) {
								rangeList2.add(Integer.toString(k));
							}
						} else if (start > end) {
							for (int l = start; l >= end; l--) {
								rangeList2.add(Integer.toString(l));
							}
						} else {
							throw new IllegalArgumentException("range start is equal to end");
						}
					}
					for (int d = 0; d < rangeList2.size(); d++) {
						expandedShortcuts.add(base.concat(rangeList2.get(d)));
					}
				}
			}
		}

		// --------------------------------Reorganising
		// Lists----------------------------------

		if (expandedOptions.size() > expandedShortcuts.size()) {
			for (int i = expandedShortcuts.size(); i < expandedOptions.size(); i++) {
				expandedShortcuts.add("");
			}
		}

		if (expandedOptions.size() < expandedShortcuts.size()) {
			int diff = expandedShortcuts.size() - expandedOptions.size();
			for (int i = 0; i < diff; i++) {
				expandedShortcuts.remove(expandedShortcuts.size() - 1);
			}
		}

		if (expandedTypes.size() > expandedOptions.size()) {
			int diff = expandedTypes.size() - expandedOptions.size();
			for (int i = 0; i < diff; i++) {
				expandedTypes.remove(expandedTypes.size() - 1);
			}
		}
		if (expandedTypes.size() < expandedOptions.size()) {
			String lastType = expandedTypes.get(expandedTypes.size() - 1);
			for (int i = expandedTypes.size(); i < expandedOptions.size(); i++) {
				expandedTypes.add(lastType);
			}
		}
		if (invalid.size() > 0) {
			for (int x = expandedOptions.size() - 1; x >= 0; x--) {
				if (invalid.get(invalid.size() - 1) == x) {
					expandedOptions.remove(x);
					expandedShortcuts.remove(x);
					expandedTypes.remove(x);
					invalid.remove(invalid.size() - 1);
				}
			}
		}
		for (int z = 0; z < expandedOptions.size(); z++) {
			switch (expandedTypes.get(z)) {
			case ("String"):
				optionMap.store(new Option(expandedOptions.get(z), Type.STRING), expandedShortcuts.get(z));
				break;
			case ("Integer"):
				optionMap.store(new Option(expandedOptions.get(z), Type.INTEGER), expandedShortcuts.get(z));
				break;
			case ("Character"):
				optionMap.store(new Option(expandedOptions.get(z), Type.CHARACTER), expandedShortcuts.get(z));
				break;
			case ("Boolean"):
				optionMap.store(new Option(expandedOptions.get(z), Type.BOOLEAN), expandedShortcuts.get(z));
				break;
			default:
				throw new IllegalArgumentException("");
			}
		}

	}

	public boolean optionExists(String key) {
		return optionMap.optionExists(key);
	}

	public boolean shortcutExists(String key) {
		return optionMap.shortcutExists(key);
	}

	public boolean optionOrShortcutExists(String key) {
		return optionMap.optionOrShortcutExists(key);
	}

	public int getInteger(String optionName) {
		String value = getString(optionName);
		Type type = getType(optionName);
		int result;
		switch (type) {
		case STRING:
		case INTEGER:
			try {
				result = Integer.parseInt(value);
			} catch (Exception e) {
				try {
					new BigInteger(value);
				} catch (Exception e1) {
				}
				result = 0;
			}
			break;
		case BOOLEAN:
			result = getBoolean(optionName) ? 1 : 0;
			break;
		case CHARACTER:
			result = (int) getCharacter(optionName);
			break;
		default:
			result = 0;
		}
		return result;
	}

	public boolean getBoolean(String optionName) {
		String value = getString(optionName);
		return !(value.toLowerCase().equals("false") || value.equals("0") || value.equals(""));
	}

	public String getString(String optionName) {
		return optionMap.getValue(optionName);
	}

	public char getCharacter(String optionName) {
		String value = getString(optionName);
		return value.equals("") ? '\0' : value.charAt(0);
	}

	public void setShortcut(String optionName, String shortcutName) {
		optionMap.setShortcut(optionName, shortcutName);
	}

	public void replace(String variables, String pattern, String value) {

		variables = variables.replaceAll("\\s+", " ");

		String[] varsArray = variables.split(" ");

		for (int i = 0; i < varsArray.length; ++i) {
			String varName = varsArray[i];
			String var = (getString(varName));
			var = var.replace(pattern, value);
			if (varName.startsWith("--")) {
				String varNameNoDash = varName.substring(2);
				if (optionMap.optionExists(varNameNoDash)) {
					optionMap.setValueWithOptionName(varNameNoDash, var);
				}
			} else if (varName.startsWith("-")) {
				String varNameNoDash = varName.substring(1);
				if (optionMap.shortcutExists(varNameNoDash)) {
					optionMap.setValueWithOptionShortcut(varNameNoDash, var);
				}
			} else {
				if (optionMap.optionExists(varName)) {
					optionMap.setValueWithOptionName(varName, var);
				}
				if (optionMap.shortcutExists(varName)) {
					optionMap.setValueWithOptionShortcut(varName, var);
				}
			}

		}
	}

	private List<CustomPair> findMatches(String text, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		// Check all occurrences
		List<CustomPair> pairs = new ArrayList<CustomPair>();
		while (matcher.find()) {
			CustomPair pair = new CustomPair(matcher.start(), matcher.end());
			pairs.add(pair);
		}
		return pairs;
	}

	public int parse(String commandLineOptions) {
		if (commandLineOptions == null) {
			return -1;
		}
		int length = commandLineOptions.length();
		if (length == 0) {
			return -2;
		}

		List<CustomPair> singleQuotePairs = findMatches(commandLineOptions, "(?<=\')(.*?)(?=\')");
		List<CustomPair> doubleQuote = findMatches(commandLineOptions, "(?<=\")(.*?)(?=\")");
		List<CustomPair> assignPairs = findMatches(commandLineOptions, "(?<=\\=)(.*?)(?=[\\s]|$)");

		for (CustomPair pair : singleQuotePairs) {
			String cmd = commandLineOptions.substring(pair.getX(), pair.getY());
			cmd = cmd.replaceAll("\"", "{D_QUOTE}").replaceAll(" ", "{SPACE}").replaceAll("-", "{DASH}").replaceAll("=",
					"{EQUALS}");

			commandLineOptions = commandLineOptions.replace(commandLineOptions.substring(pair.getX(), pair.getY()),
					cmd);
		}

		for (CustomPair pair : doubleQuote) {
			String cmd = commandLineOptions.substring(pair.getX(), pair.getY());
			cmd = cmd.replaceAll("\'", "{S_QUOTE}").replaceAll(" ", "{SPACE}").replaceAll("-", "{DASH}").replaceAll("=",
					"{EQUALS}");

			commandLineOptions = commandLineOptions.replace(commandLineOptions.substring(pair.getX(), pair.getY()),
					cmd);
		}

		for (CustomPair pair : assignPairs) {
			String cmd = commandLineOptions.substring(pair.getX(), pair.getY());
			cmd = cmd.replaceAll("\"", "{D_QUOTE}").replaceAll("\'", "{S_QUOTE}").replaceAll("-", "{DASH}");
			commandLineOptions = commandLineOptions.replace(commandLineOptions.substring(pair.getX(), pair.getY()),
					cmd);
		}

		commandLineOptions = commandLineOptions.replaceAll("--", "-+").replaceAll("\\s+", " ");

		String[] elements = commandLineOptions.split("-");

		for (int i = 0; i < elements.length; ++i) {
			String entry = elements[i];

			if (entry.isBlank()) {
				continue;
			}

			String[] entrySplit = entry.split("[\\s=]", 2);

			boolean isKeyOption = entry.startsWith("+");
			String key = entrySplit[0];
			key = isKeyOption ? key.substring(1) : key;
			String value = "";

			if (entrySplit.length > 1 && !entrySplit[1].isBlank()) {
				String valueWithNoise = entrySplit[1].trim();
				value = valueWithNoise.split(" ")[0];
			}

			// Explicitly convert boolean.
			if (getType(key) == Type.BOOLEAN && (value.toLowerCase().equals("false") || value.equals("0"))) {
				value = "";
			}

			value = value.replace("{S_QUOTE}", "\'").replace("{D_QUOTE}", "\"").replace("{SPACE}", " ")
					.replace("{DASH}", "-").replace("{EQUALS}", "=");

			boolean isUnescapedValueInQuotes = (value.startsWith("\'") && value.endsWith("\'"))
					|| (value.startsWith("\"") && value.endsWith("\""));

			value = value.length() > 1 && isUnescapedValueInQuotes ? value.substring(1, value.length() - 1) : value;

			if (isKeyOption) {
				optionMap.setValueWithOptionName(key, value);
			} else {
				optionMap.setValueWithOptionShortcut(key, value);

			}
		}

		return 0;

	}

	private Type getType(String option) {
		Type type = optionMap.getType(option);
		return type;
	}

	@Override
	public String toString() {
		return optionMap.toString();
	}

	private class CustomPair {

		CustomPair(int x, int y) {
			this.x = x;
			this.y = y;
		}

		private int x;
		private int y;

		public int getX() {
			return this.x;
		}

		public int getY() {
			return this.y;
		}
	}
}
