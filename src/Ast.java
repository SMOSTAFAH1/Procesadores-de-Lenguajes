import java.io.*;

public class Ast {
	
	private Token sig_tok;
	private ALex a;	
	private String parse;
	
	public Ast(String pathname) throws IOException {
		parse = "";
		try {
			a = new ALex(pathname);
			sig_tok = a.ALexico();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		P();
		parse += " 3";
		if(sig_tok.getId() != 26)
			System.out.println("Error en la linea "  + a.linea);
	}
	
	
    public void P() throws IOException {
    	
    	if(FirstFollow.getFirst().get("B").contains(sig_tok.getId())) {
    		parse += "1 ";
    		System.out.println("1");
    		B();
    		P();
    	}
    	else if(FirstFollow.getFirst().get("F").contains(sig_tok.getId())) {
    		parse += "2 ";
    		F();
    		P();
    	}
    }

    public void B() throws IOException {
        
    	if(sig_tok.getId() == 3) {
    		parse += "17 ";
    		equipara(3);
    		equipara(19);
    		E();
    		equipara(20);
    		S();
    	}
    	else if(sig_tok.getId() == 11) {
    		//while
    		parse += "18 ";
    		equipara(11);
    		equipara(19);
    		E();
    		equipara(20);
    		equipara(21);
    		C();
    		equipara(22);
    	}
    	else if(sig_tok.getId() == 9) {
    		parse += "19 ";
    		equipara(9);
    		T();
    		equipara(15);
    		equipara(18);
    	}
    	else if(FirstFollow.getFirst().get("S").contains(sig_tok.getId())) {
    		parse += "20 ";
    		S();
    	}
    }

    public void F() throws IOException {
    	
    	if(sig_tok.getId() == 2) {
    		parse += "21 ";
    		equipara(2);
    		H();
    		equipara(15);
    		equipara(19);
    		A();
    		equipara(20);
    		equipara(21);
    		C();
    		equipara(22);
    	}
    	else
    		System.out.println("Se esperaba una funcion");
        System.out.println("Método F");
    }

    public void A() throws IOException {
    	
    	if(sig_tok.getId() == 10) {
    		parse += "24 ";
    		equipara(10);
    	}
    	else if(FirstFollow.getFirst().get("T").contains(sig_tok.getId())) {
    		parse += "25 ";
    		T();
    		equipara(15);
    		K();
    	}
    	else {
    		System.out.println("Se esperaba la declaracion de los parametros de la fucnion, pero se obtuvo "+Token.cod.get(sig_tok.getId()));
    	}
        System.out.println("Método A");
    }

    public void S() throws IOException {
    	
    	if(sig_tok.getId() == 15) {
    		parse += "4 ";
    		equipara(15);
    		Y();
    	}
    	else if(sig_tok.getId() == 6) {
    		parse += "8 ";
    		equipara(6);
    		E();
    		equipara(18);
    	}
    	else if(sig_tok.getId() == 4) {
    		parse += "9 ";
    		equipara(4);
    		equipara(15);
    		equipara(18);
    	}
    	else if(sig_tok.getId() == 7) {
    		parse += "10 ";
    		equipara(7);
    		X();
    		equipara(18);
    	}
    	else {
    		System.out.println("Se esperaba alguna sentencia simple "+ Token.cod.get(sig_tok.getId()));
    	}
        System.out.println("Método S");
    }

    public void Y() throws IOException {
    	
    	if(sig_tok.getId() == 16) {
    		parse += "5 ";
    		equipara(16);
    		E();
    		equipara(18);
    	}
    	else if(sig_tok.getId() == 19) {
    		parse += "6 ";
    		equipara(19);
    		L();
    		equipara(20);
    		equipara(18);
    	}
    	else if(sig_tok.getId() == 12) {
    		parse += "7 ";
    		equipara(12);
    		equipara(18);
    	}
    	else {
    		System.out.println("No se puede poner" + Token.cod.get(sig_tok.getId()) + " despues de una variable");
    	}
        System.out.println("Método Y");
    }

    public void E() throws IOException {
    	
    	if(FirstFollow.getFirst().get("R").contains(sig_tok.getId())) {
    		parse += "33 ";
    		R();
    		G();
    	}
    	
        System.out.println("Método E");
    }

    public void L() throws IOException {
    	
    	if(FirstFollow.getFirst().get("L").contains(sig_tok.getId())) {
    		parse += "13 ";
    		E();
    		Q();
    		
    	}
    	else if(FirstFollow.getFollow().get("L").contains(sig_tok.getId())) {
    		parse += "14 ";
    	}
        System.out.println("Método L");
    }

    public void X() throws IOException {
    	
    	if(FirstFollow.getFirst().get("E").contains(sig_tok.getId())) {
    		parse += "11 ";
    		E();
    	}
    	else if(FirstFollow.getFollow().get("X").contains(sig_tok.getId())) {
    		parse += "12 ";
    	}
    	else {
    		System.out.println("No se puede devolver "+Token.cod.get(sig_tok.getId()) );
    	}
        System.out.println("Método X");
    }

    public void Q() throws IOException {
    	
    	if(sig_tok.getId() == 17) {
    		parse += "15 ";
    		equipara(17);
    		E();
    		Q();
    	}
    	else if(FirstFollow.getFollow().get("Q").contains(sig_tok.getId())) {
    		parse += "16 ";
    	}
    	
        System.out.println("Método Q");
    }

    public void C() throws IOException {
    	
    	if(FirstFollow.getFirst().get("B").contains(sig_tok.getId())) {
    		parse += "28 ";
    		B();
    		C();
    	}
    	else if(FirstFollow.getFollow().get("C").contains(sig_tok.getId())) {
    		parse += "29 ";
    	}
        System.out.println("Método C");
    }

    public void T() throws IOException {
    	
    	if(sig_tok.getId() == 5) {
    		parse += "30 ";
    		equipara(5);
    	}
    	else if(sig_tok.getId() == 1) {
    		parse += "31 ";
    		equipara(1);
    	}
    	else if(sig_tok.getId() == 8) {
    		parse += "32 ";
    		equipara(8);
    	}
    	else {
    		System.out.println(Token.cod.get(sig_tok.getId()) + " no es un tipo de datos valido");
    	}
        System.out.println("Método T");
    }

    public void H() throws IOException {
    	
    	if(FirstFollow.getFirst().get("T").contains(sig_tok.getId())) {
    		parse += "22 ";
    		T();
    	}
    	else if(sig_tok.getId() == 10) {
    		parse += "23 ";
    		equipara(10);
    		
    	}
        System.out.println("Método H");
    }

    public void K() throws IOException {
    	
    	if(sig_tok.getId() == 17) {
    		parse += "26 ";
    		equipara(17);
    		T();
    		equipara(15);
    		K();
    	}
    	else if(FirstFollow.getFollow().get("K").contains(sig_tok.getId())) {
    		parse += "27 ";
    	}
        System.out.println("Método K");
    }

    public void R() throws IOException {
    	
    	if(FirstFollow.getFirst().get("U").contains(sig_tok.getId())) {
    		parse += "36 ";
    		U();
    		O();
    	}
        System.out.println("Método R");
    }

    public void G() throws IOException {
    	
    	if(sig_tok.getId() == 25) {
    		parse += "34 ";
    		equipara(25);
    		R();
    		G();
    	}
    	else if(FirstFollow.getFollow().get("G").contains(sig_tok.getId())) {
    		parse += "35 ";
    	}
        System.out.println("Método G");
    }

    public void U() throws IOException {
    	
    	if(sig_tok.getId() == 24) {
    		parse += "39 ";
    		equipara(24);
    		V();
    	}
    	else if(FirstFollow.getFirst().get("V").contains(sig_tok.getId())) {
    		parse += "40 ";
    		V();
    	}
//    	if(FirstFollow.getFollow().get("U").contains(sig_tok.getId())) {
//    		parse += "41 ";
//    		
//    		//W();
//    	}
        System.out.println("Método U");
    }

    public void O() throws IOException {
    	
    	if(sig_tok.getId() == 23) {
    		parse += "37 ";
    		equipara(23);
    		U();
    		O();
    	}
    	else if(FirstFollow.getFollow().get("O").contains(sig_tok.getId())) {
    		parse += "38 ";
    	}
    	
        System.out.println("Método O");
    }

    public void V() throws IOException {
    	
    	if(sig_tok.getId() == 15) {
    		parse += "41 ";
    		equipara(15);
    		Z();
    	}
    	else if(sig_tok.getId() == 13) {
    		parse += "42 ";
    		equipara(13);
    	}
    	else if(sig_tok.getId() == 14) {
    		parse += "43 ";
    		equipara(14);
    	}
    	else if(sig_tok.getId() == 19) {
    		parse += "44 ";
    		equipara(19);
    		E();
    		equipara(20);
    	}
        System.out.println("Método V");
    }

//    public void W() throws IOException {
//    	
//    	if(sig_tok.getId() == 24) {
//    		parse += "40 ";
//    		equipara(24);
//    		V();
//    		W();
//    	}
//    	else if(FirstFollow.getFollow().get("W").contains(sig_tok.getId())) {
//    		parse += "41 ";
//    	}
//        System.out.println("Método W");
//    }

    public void Z() throws IOException {
    	
    	if(sig_tok.getId() == 19) {
    		parse += "45 ";
    		equipara(19);
    		L();
    		equipara(20);
    	}
    	else if(sig_tok.getId() == 12) {
    		parse += "47";
    		equipara(12);
    	}
    	else if(FirstFollow.getFollow().get("Z").contains(sig_tok.getId())) {
    		parse += "46 ";
    	}
    	else {
			System.out.println("Error en la linea "  + a.linea); //			Error();
			System.out.println("Se esperaba una expresion simple no "  + Token.cod.get(sig_tok.getId()) );
		}
     
    }
    
	private void equipara(int i) throws IOException {
		
		if(sig_tok.getId() == i) {
			sig_tok = a.ALexico();
		}
		else {
			System.out.println("Error en la linea "  + a.linea);
			System.out.println("Se esperaba "+ Token.cod.get(i) +" pero se encontro "+ Token.cod.get(sig_tok.getId()) );
			return; // Aca los errores seran en la linea anterior
		}	
	}
	
	public static void main(String[] args) {
		
		 try {
	            Ast ast = new Ast(args[0]);
	            
	            try (BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]))) {
	                writer.write("D " + ast.parse);
	            } catch (IOException e) {
	                System.out.println("Error escribiendo");
	                e.printStackTrace();
	            }
	            System.out.println("Parse guardado exitosamente");

	        } catch (IOException e) {
	            System.out.println("Error al procesar el archivo.");
	            e.printStackTrace();
	        }
		 
	}
}