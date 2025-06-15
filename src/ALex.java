import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ALex {
	
	private Integer estado;
	private char car;
	private BufferedReader reader;
	public int linea;
	
	public int lineaInicioCom;
	
	public ALex(String filePath) throws IOException{
		reader = new BufferedReader(new FileReader(filePath));
		car = (char)reader.read();
		linea = 1;
	}
	
	Token ALexico() throws IOException{
		estado = 0;
		String lexema="";
		int numero=0;
		Token token = null;
		
		while( !AFD.esEstadoFinal(estado) ) {
			char accion = AFD.accion(estado, car);
			estado = AFD.estado(estado, car);
			
			if(estado == 17) {
				lineaInicioCom = linea;
			}
			
			if(estado == -1) {
				//Error(accion);
				
				if(accion==129) {
					System.out.println("Comentario sin cerrar");
					System.out.println("Error en linea "+lineaInicioCom);
				}
				else if(accion==128) {
//					System.out.println("Cadena sin cerrar");
					System.out.println("Error en linea "+linea + ": " + "Cadena sin cerrar");
				}
				else {
//					System.out.println("Simbolo desconocido "+car);
					System.out.println("Error en linea "+linea + ": " + "Simbolo desconocido "+car);
				}
					
				break;
			}
			else{
				switch(accion) {
				case 'L': car=leer(); 
				break;
				
				//Cadenas
				case 'A': 
					lexema = ""; car=leer();
				break;
				
				case 'B': 
					lexema = lexema+car; car=leer();
				break;
				case 'C': 
					if(lexema.length()<=64) {
						car=leer(); 
						token = Token.STRING_LITERAL;
						token.setAttr('"'+lexema+'"');
					}
					else {
						System.out.println("Error en la linea " + linea +": Cadena fuera de rango '"+lexema+"") ;
					}
				break;
				
				//Numeros
				case 'D': 
					numero = car -'0'; car=leer();
				break;
				
				case 'E': 
					numero = numero*10 + Character.getNumericValue(car); car=leer();
				break;
				
				case 'F': 
					if(numero<=32767) {
						token = Token.CONST_INT; 
						token.setAttr(numero);
					}
					else
						System.out.println("Error en la linea " + linea + ": Numero fuera de rango "+numero);
				break;
					
				case 'G': 
					lexema = String.valueOf(car); car=leer();
				break;
				
				case 'H': 
					lexema = lexema+car; car=leer();
				break;
				
				case 'I': 
					if( Token.esPR(lexema) ) {
						token = Token.getPR(lexema);
					}
					else {
						if( Asem.zonaDec ) {
							Integer p = TS.buscaID(lexema);
							if(p==null) {
								p = TS.tablaActual.insertaID(lexema);
								token = Token.IDENTIFIER;
								token.setAttr(p);	
							}
							else {
								//p ya ha sido declarado, pero se tiene que ver si las variables son del mismo ambito
								Integer p1 = TS.tablaActual.buscaID(lexema);
								if(p1==null) {
									p1 = TS.tablaActual.insertaID(lexema);
									token = Token.IDENTIFIER;
									token.setAttr(p1);
								}
								else System.out.println("Error en la linea " + linea +": Identificador ya declarado");
							}
						}
						else {
							Integer p = TS.buscaID(lexema);
							if(p==null) {
								p = TS.global().insertaIDglobal(lexema);
							}
							token = Token.IDENTIFIER;
							token.setAttr(p);
						}
					}
				break;
				
				
				case 'J': car=leer();
				break;
				
				case 'K': car=leer(); token = Token.AUTODECREMENT;
				break;
				
				case 'M': token = Token.NOT; //no va leer xq ya esta en AFD
				break;
				
				case 'N': car=leer(); token = Token.NOT_EQUAL;
				break;
				
				case 'O': car=leer(); token = Token.PLUS;
				break;
				
				case 'P': car=leer(); token = Token.ASSIGN;
				break;
				
				case 'Q': car=leer(); token = Token.COMMA;
				break;
				
				case 'R': car=leer(); token = Token.SEMICOLON;
				break;
				
				case 'S': car=leer(); token = Token.LPAREN;
				break;
				
				case 'T': car=leer(); token = Token.RPAREN;
				break;
				
				case 'U': car=leer(); token = Token.LBRACE;
				break;
				
				case 'V': car=leer(); token = Token.RBRACE;
				break;
				
				case 'W': car=leer(); token = Token.EOF;
				break;
				}
			}

		}
		
		if(estado == 18 || estado == 19) {
			System.out.println("Error: Comentario sin cerrar. Abierto en la linea "+lineaInicioCom);
		}
		
		return token;
	}
	
	public char leer() throws IOException{
		char car = (char)reader.read();
		if(car == '\n' || car==(char)10)
			linea++;
//		return (char)reader.read();
		return car;
	}
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Uso: java ALex <archivo_entrada> <archivo_salida>");
			return;
		}
		
		// Inicializar las tablas de símbolos
		Tabla tablaGlobal = TS.crearTS();
		TS.setGlobal(tablaGlobal);
		TS.setActual(tablaGlobal);
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]))) {
			ALex aLex = new ALex(args[0]);
			Token token = new Token(0);

			while( token!=null && token.getId() != 26) {
				// Escribir cada token en una nueva línea del archivo de salida
				token=aLex.ALexico();
				if(token!=null) {
				writer.write(token.toString());
				writer.newLine();
				}
			}
			System.out.println("Tokens guardados exitosamente en " + args[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		TS.guardarTablaGlobal();

	}

}
