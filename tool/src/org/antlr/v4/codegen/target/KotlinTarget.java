package org.antlr.v4.codegen.target;

import org.antlr.v4.codegen.CodeGenerator;
import org.antlr.v4.codegen.Target;
import org.antlr.v4.codegen.UnicodeEscapes;

import java.util.*;

public class KotlinTarget extends JavaTarget {

	public KotlinTarget(CodeGenerator codeGenerator) {
		super(codeGenerator);
		kotlinReservedWords.add("rule");
		kotlinReservedWords.add("parserRule");
	}

	/**
	 * Reserved Kotlin keywords.
	 */
	private static String[] kotlinKeywords = new String[] {
			"abstract",
			"catch",
			"class",
			"const",
			"else",
			"enum",
			"false",
			"for",
			"if",
			"implements",
			"import",
			"is",
			"interface",
			"null",
			"package",
			"private",
			"internal",
			"public",
			"return",
			"this",
			"throw",
			"true",
			"try",
			"while",
			"object",
	};

		/**
		 * @see Target.defaultCharValueEscape
		 * @see Target.getTargetCharValueEscape
		 */
		private static HashMap<Character, String> kotlinCharValueEscape;

		static  {
			kotlinCharValueEscape = new HashMap(defaultCharValueEscape);
			addEscapedChar(kotlinCharValueEscape, '$');

			kotlinCharValueEscape.put((char)11, "\\u000b"); // Vertical tab
			kotlinCharValueEscape.put((char)12, "\\u000c"); // Form feed
			kotlinCharValueEscape.put((char)14, "\\u000e"); // Shift out
			kotlinCharValueEscape.put((char)15, "\\u000f"); // Shift in
		}

	/**
	 * @see JavaTarget.reservedWords
	 * @see Target.getReservedWords
	 */
	private Set<String> kotlinReservedWords = new HashSet<>(Arrays.asList(kotlinKeywords));

	@Override
	public String getVersion() {
		return "4.13.1";
	}

	@Override
	public Set<String> getReservedWords() {
		return kotlinReservedWords;
	}

	@Override
	public Map<Character, String> getTargetCharValueEscape() {
		return kotlinCharValueEscape;
	}

	@Override
	public String encodeInt16AsCharEscape(int v) {
		if (v < Character.MIN_VALUE || v > Character.MAX_VALUE) {
			throw new IllegalStateException("Cannot encode the specified value: " + v);
		}

		return "\\u" + Integer.toHexString(v | 0x10000).substring(1, 5);
	}

	@Override
	protected void appendUnicodeEscapedCodePoint(int codePoint, StringBuilder sb) {
		UnicodeEscapes.appendEscapedCodePoint(sb, codePoint, "Java");
	}

}
