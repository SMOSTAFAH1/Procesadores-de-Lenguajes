import java.util.HashMap;
import java.util.Map;

public class AFD {

	private static Map<Integer, Map<Character, Pair<Integer, Character>>> matriz = new HashMap<>();
	
	public static void agregarTransicion(int estadoInicial, char entrada, int estadoFinal, char accion) {
	    matriz.computeIfAbsent(estadoInicial, k -> new HashMap<>()).put(entrada, new Pair<>(estadoFinal, accion));
	}

	public static int estado(int estado, char entrada) {
	    if (matriz.containsKey(estado) && matriz.get(estado).containsKey(entrada)) {
	        return matriz.get(estado).get(entrada).getLeft();
	    }
	    // Lógica para casos especiales...
	    return -1;
	}

	public static char accion(int estado, char entrada) {
	    if (matriz.containsKey(estado) && matriz.get(estado).containsKey(entrada)) {
	        return matriz.get(estado).get(entrada).getRight();
	    }
	    // Lógica para casos especiales...
	    return 0;
	}

	static {
		//delimitadores
		agregarTransicion(0, ' ', 0, 'L');
		agregarTransicion(0, '\t', 0, 'L');
		agregarTransicion(0, '\n', 0, 'L');
		agregarTransicion(0, (char)13, 0, 'L');
		agregarTransicion(0, (char)65535, 23, 'W');
		agregarTransicion(0, (char)9, 0, 'L');
		
		//cadena
		agregarTransicion(0, '"', 15, 'A'); //inicio cadena
		
		for (char c1 = 0; c1 <= 127; ++c1) { // Recorre los caracteres del rango ASCII
		    if (c1 != '"' || c1!=13 || c1!='\n' || c1!='\t') { // Excluye las comillas dobles
		        agregarTransicion(15, c1, 15, 'B');
		    }
		}
		
		//las cadenas no pueden ser de mas de una linea
		agregarTransicion(15, '\n', -1, (char)128);
		agregarTransicion(15, (char)13, -1, (char)128);
		
		agregarTransicion(15, '"', 16, 'C'); // fin cadena
		
		//para numeros (digitos 0-9)
		for (char c = '0'; c <= '9'; ++c) {
		    agregarTransicion(0, c, 13, 'D');
		}
//		for (char c = '0'; c <= '9'; ++c) {
//		    agregarTransicion(13, c, 13, 'E');
//		}
		agregarTransicion(13, ' ', 14, 'F');//otro caracter
		
		for (char c = 0; c <= 127; ++c) {
			if( c >= '0' && c<='9')  agregarTransicion(13, c, 13, 'E');
			else agregarTransicion(13, c, 14, 'F');
		}
		
		//----IDs----
		//para letras A...Z y a...z
		for (char id = 'A'; id <= 'z'; ++id) {
		    if ((id >= 'A' && id <= 'Z') || (id >= 'a' && id <= 'z')) {
		        agregarTransicion(0, id, 11, 'G');
		    }
		}
		
		//para letras y numeros A...z y 0-9
//		for (char id = '0'; id <= 'z'; ++id) {
//		    if ((id >= '0' && id <= '9') || (id >= 'A' && id <= 'Z') || (id >= 'a' && id <= 'z')) {
//		        agregarTransicion(11, id, 11, 'H');
//		    }
//		}
		for (char id = 0; id <= 127; ++id) {
		    if ((id >= '0' && id <= '9') || (id >= 'A' && id <= 'Z') || (id >= 'a' && id <= 'z') || id == '_') {
		        agregarTransicion(11, id, 11, 'H');
		    }
		    else
		    	agregarTransicion(11, id, 12, 'I');
		}
		
		//autodrecremento
		agregarTransicion(0, '-', 1, 'J');
		agregarTransicion(1, '-', 2, 'K');
//		for (char c2 = 0; c2 <= 127; ++c2) {
//			if(c2!='=') agregarTransicion(20, c2, 21, 'M');
//		}
//		
		//not_equal
		agregarTransicion(0, '!', 20, 'L');
		agregarTransicion(20, ' ', 21, 'M');
		for (char c2 = 0; c2 <= 127; ++c2) {
			if(c2!='=') agregarTransicion(20, c2, 21, 'M');
		}
		agregarTransicion(20, '=', 22, 'N');
		agregarTransicion(20, (char)65535, 21, 'M'); //otro caracter luego de !
				
		//operadores
		agregarTransicion(0, '+', 3, 'O'); 
		agregarTransicion(0, '=', 4, 'P'); 
		agregarTransicion(0, ',', 5, 'Q'); 
		agregarTransicion(0, ';', 6, 'R'); 
		agregarTransicion(0, '(', 7, 'S'); 
		agregarTransicion(0, ')', 8, 'T'); 
		agregarTransicion(0, '{', 9, 'U'); 
		agregarTransicion(0, '}', 10, 'V');
		
		//comentarios
		agregarTransicion(0, '/', 17, 'L');
		agregarTransicion(17, '*', 18, 'L'); //inicio comentario
		
		for (char c2 = 0; c2 <= 127; ++c2) { // Recorre los caracteres del rango ASCII
		    if (c2 != '*') { // Excluye el *
		        agregarTransicion(18, c2, 18, 'L');
		    }
		}
		
		agregarTransicion(18, '\0', -1, (char)129);
		agregarTransicion(18, (char)65535, -1, (char)129);
		agregarTransicion(18, '*', 19, 'L');
		
		for (char c3 = 0; c3 <= 127; ++c3) { // Recorre los caracteres del rango ASCII
		    if (c3 != '*' && c3 != '/') { // Excluye el * y /
		        agregarTransicion(19, c3, 18, 'L');
		    }
		}
		
		agregarTransicion(19, '*', 19, 'L');
		agregarTransicion(19, '/', 0, 'L'); //fin comentario
		
		//EOF
		agregarTransicion(0, '\0', 23, 'W');		
	}
	
	 // Comprobamos si estamos en el estado final 
	 public static boolean esEstadoFinal(int estado) {
	        return (estado >= 2 && estado <= 10 ) || 
	        		estado == 12 || estado == 14 || estado == 16 ||
	        		estado == 21 || estado == 22 || 
	        		estado == 23;
	    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
