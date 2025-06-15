import java.util.Map;
import java.util.HashMap;

public class Token {
	
	private static Map<String, Token> palReservadas = new HashMap<>();
	public static Map<Integer, String> cod = new HashMap<>();
	
	/* Tokens obligatorios*/
	public static Token BOOLEAN = new Token(1); 
	public static Token FUNCTION = new Token(2); 
	public static Token IF = new Token(3);
	public static Token INPUT = new Token(4); 
	public static Token INT = new Token(5);
	public static Token OUTPUT = new Token(6);
	public static Token RETURN = new Token(7);
	public static Token STRING = new Token(8); 
	public static Token VAR = new Token(9);
	public static Token VOID = new Token(10); 
	public static Token WHILE = new Token(11);
	public static Token AUTODECREMENT = new Token(12); 
	public static Token CONST_INT = new Token(13); 
	public static Token STRING_LITERAL = new Token(14); 
	public static Token IDENTIFIER = new Token(15); 
	public static Token ASSIGN = new Token(16); // = 
	public static Token COMMA = new Token(17); // ,
	public static Token SEMICOLON = new Token(18); // ; 
	public static Token LPAREN = new Token(19); // ( 
	public static Token RPAREN = new Token(20); // )
	public static Token LBRACE = new Token(21); // {
	public static Token RBRACE = new Token(22); // }

	/* Tokens Grupo Operadores Aritméticos: Suma (+) */
	public static Token PLUS = new Token(23);

	/* Grupo Operadores Lógicos: Negación (!) */
	public static Token NOT = new Token(24);

	/* Grupo Operadores Relacionales: Distinto (!=) */
	public static Token NOT_EQUAL = new Token(25);

	/* Tokens opcionales */
	public static Token EOF = new Token(26);	
	
	
	static {
		palReservadas.put("boolean", BOOLEAN);
		palReservadas.put("function", FUNCTION);
		palReservadas.put("if", IF); 
		palReservadas.put("input", INPUT); 
		palReservadas.put("int", INT);
		palReservadas.put("output", OUTPUT); 
		palReservadas.put("return", RETURN); 
		palReservadas.put("string", STRING);
		palReservadas.put("var", VAR);
		palReservadas.put("void", VOID); 
		palReservadas.put("while", WHILE);
	}
	
	static {
		cod.put(1, "boolean"); 
		cod.put(2, "function");
		cod.put(3, "if"); 
		cod.put(4, "input"); 
		cod.put(5, "int");
		cod.put(6, "output");
		cod.put(7, "return");
		cod.put(8, "string");
		cod.put(9, "var");
		cod.put(10, "void");
		cod.put(11, "while");
		cod.put(12, "autodecremento: --");
		cod.put(13, "entero");
		cod.put(14, "cadena"); 
		cod.put(15, "identificador");
		cod.put(16, "=");
		cod.put(17, ",");
		cod.put(18, ";");
		cod.put(19, "parentesis izquierdo: (");
		cod.put(20, "parentesis derecho: )");
		cod.put(21, "llave izquierda: {");
		cod.put(22, "llave derecha: }"); 
		cod.put(23, "+");
		cod.put(24, "!"); 
		cod.put(25, "!=");
		cod.put(26, "eof");
	}

	public static Token getPR(String lex) {
		return palReservadas.get(lex);
	}
	
	private int id;
	private Object attr; // atributo

	public Token(int id, Object attr) {
		this.id = id;
		this.attr = attr;
	}
	public Token(int id) {
		this.id = id;
		this.attr = null;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Object getAtr() {
		return this.attr;
	}

	public void setAttr(Object attr) {
		this.attr = attr;
	}
	
	public static boolean esPR(String palabra) {
		return palReservadas.containsKey(palabra);
	}

//    @Override
//    public boolean equals(Object object) {
//        if (this == object)
//			return true;
//        if (object == null || getClass() != object.getClass())
//			return false;
//
//        Token token = (Token) object;
//
//		/* Comprueba sea mismo id*/
//        if (!id.equals(token.id))
//			return false;
//		/* Comprueba atributo*/
//        return (attr != null ? attr.equals(token.attr) : token.attr == null);
//    }

	@Override
	public String toString() {
		if(attr==null)
			return "<"+ this.id + ", >";
		return "<" + this.id + ", " + this.attr + ">";
	}
	
	public static void main(String args[]) {
		Token t = Token.getPR("BOOLEAN");
		System.out.println(t.toString());
	}
}