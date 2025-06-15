import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Asem {
	
	private Token sig_tok;
	private ALex a;	
	private String parse;
	public static boolean zonaDec;
	BufferedWriter writer2;
	private String filenameTS;
	
	public Asem(String pathname, BufferedWriter wr, String filename) throws IOException {
		parse = "";
		filenameTS = filename;
		try {
			writer2 = wr;
			TS.tablaActual = TS.crearTS();
			TS.setGlobal(TS.tablaActual);
			
			a = new ALex(pathname);
			sig_tok = a.ALexico();
			writer2.write(sig_tok.toString());
			writer2.newLine();
			P();
			TS.destruirTS();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		P();
		parse += " 3";
		TS.guardarTabla("global", "", filename);
		if(sig_tok.getId() != 26)
			System.out.println("Error en la linea "  + a.linea);
	}
	
    public void P() throws IOException {
    	
    	if(FirstFollow.getFirst().get("B").contains(sig_tok.getId())) {
    		parse += "1 ";
    		B(null);
    		P();
    	}
    	else if(FirstFollow.getFirst().get("F").contains(sig_tok.getId())) {
    		parse += "2 ";
    		F();
    		P();
    	}
    }

    public String B(String tipoRet) throws IOException {
        String bTipo = "";
        
    	if(sig_tok.getId() == 3) {
    		// if
    		parse += "17 ";
    		equipara(3);
    		equipara(19);
    		String eTipo = E();
    		
    		if(!"boolean".equals(eTipo)) {
    			bTipo = "error";
    			System.out.println("Error en la linea " + a.linea + ": Condicion del if debe ser logica");
    		}
    		else {
    			equipara(20);
    			String sTipo = S(tipoRet);
    			bTipo = sTipo;
    		}
    		
    		
    	}
    	else if(sig_tok.getId() == 11) {
    		//while
    		parse += "18 ";
    		equipara(11);
    		equipara(19);
    		String eTipo =E();
    		equipara(20);
    		equipara(21);
    		String cTipo = C(tipoRet);
    		equipara(22);
    		
    		if( "boolean".equals(eTipo) ) bTipo = cTipo;
    		else {
    			bTipo = "error";
    			System.out.println("Error en la linea " + a.linea +": Condicion del while debe ser logica");
    		}
    	}
    	else if(sig_tok.getId() == 9) {
    		// var
    		zonaDec = true;
    		parse += "19 ";
//    		System.out.println(sig_tok.toString());
    		equipara(9);
    		Map<String, String> t = T();
    		String tTipo = t.get("tipo");
    		int tAncho = Integer.parseInt(t.get("ancho"));
    		int idPos = (int) sig_tok.getAtr();
    		equipara(15);
    		
    		TS.tablaActual.setTipo(idPos, tTipo);
    		TS.tablaActual.setDesp(idPos, TS.tablaActual.getDesp());
    		TS.tablaActual.setDespTabla(TS.tablaActual.getDesp() + tAncho);
    		zonaDec = false;
    		
    		equipara(18);
    		bTipo = "ok";
    	}
    	else if(FirstFollow.getFirst().get("S").contains(sig_tok.getId())) {
    		parse += "20 ";
    		bTipo = S(tipoRet);
    	}
    	else {
    		System.out.println("Error en la linea " + a.linea);
    	}
    	return bTipo;
    }

    public void F() throws IOException {
    	
    	if(sig_tok.getId() == 2) {
    		parse += "21 ";
    		equipara(2);
    		String hTipo = H();
    		
    		int idPos = (int)sig_tok.getAtr();
    		zonaDec = true;
    		
    		equipara(15);
    		
    		TS.setActual(TS.crearTS());
    		TS.setLocal(TS.tablaActual);
    		
    		TS.global().setTipo(idPos, "function");
    		TS.global().setEt(idPos, "Et_"+TS.getLexema(idPos));
    		TS.global().setTipoRet(idPos, hTipo);
    		
    		equipara(19);
    		zonaDec = false;
    		
    		Map<String, String> a = A();
    		TS.global().setNumParam(idPos, Integer.parseInt( a.get("numParam") ));
    		TS.global().setTipoParam(idPos, a.get("tipo"));
    		equipara(20);
    		equipara(21);
    		C(hTipo);
    		
    		TS.guardarTabla("local", TS.getLexema(idPos), filenameTS);
    		TS.destruirTS();
    		TS.tablaActual = TS.global();
    		
    		equipara(22);
    	}
    	else
    		System.out.println("Error en la linea "  + a.linea+": Se esperaba una funcion");
    }

    public Map<String, String> A() throws IOException {
    	
    	int numParam = 0;
    	String aTipo = "";
    	Map<String, String> a = new HashMap<String, String>();
    	
    	if(sig_tok.getId() == 10) {
    		parse += "24 ";
    		equipara(10);
    		aTipo = "void";
    	}
    	else if(FirstFollow.getFirst().get("T").contains(sig_tok.getId())) {
    		parse += "25 ";
    		zonaDec = true;
    		Map<String, String> t = T();
    		
    		String tipo = t.get("tipo");
    		int ancho = Integer.parseInt(t.get("ancho"));

    		int idPos = (int)sig_tok.getAtr();
    		
    		TS.tablaActual.setTipo(idPos, tipo);
    		TS.tablaActual.setDesp(idPos, TS.tablaActual.getDesp());
    		TS.tablaActual.setDespTabla(TS.tablaActual.getDesp() + ancho);
    		equipara(15);
    		
    		Map<String, String> k = K();
    		numParam = 1 + Integer.parseInt(k.get("numParam"));
    		if(!k.get("tipo").equals("")) aTipo = tipo + ", "+ k.get("tipo");
    		else aTipo = tipo;
    		
    		zonaDec = false;
    	}
    	else {
    		System.out.println("Error en la linea "  + this.a.linea+": Se esperaba la declaracion de los parametros de la funcion, pero se obtuvo "+Token.cod.get(sig_tok.getId()));
    	}
        a.put("tipo", aTipo);
        a.put("numParam", numParam+"");
        
        return a;
    }

    public String S(String tipoRet) throws IOException {
    	String sTipo = "";
    	
    	if(sig_tok.getId() == 15) {
    		//ID
    		parse += "4 ";
    		int idPos = (int)sig_tok.getAtr();
    		String idTipo = TS.buscaTipo(idPos);
    		String YtipoParam = "";
    		
    		if("function".equals(idTipo)) {
    			String idTipoParam = TS.global().getTipoParam(idPos); 
    			YtipoParam = idTipoParam;
    		}
    		
    		if(idTipo == null) {
    			// no ha sido declarada
    			TS.global().setTipo(idPos, "int");
    			TS.global().setDesp(idPos, TS.global().getDesp() );
    			TS.global().setDespTabla( TS.global().getDesp() + 1 );
    			idTipo = "int";
    		}
    		
    		equipara(15);
//    		Ytipo = idTipo;
    		
    		sTipo = Y(idTipo, YtipoParam);
    	}
    	else if(sig_tok.getId() == 6) {
    		// output
    		parse += "8 ";
    		equipara(6);
    		String eTipo = E();
    		equipara(18);
    		
    		if( "string".equals(eTipo) || "int".equals(eTipo)) sTipo = "ok";
    		else {
    			sTipo = "error";
    			System.out.println("Error en la linea "  + a.linea+": Solo se puede hacer output de enteros o cadenas");
    		}
    	}
    	else if(sig_tok.getId() == 4) {
    		// input
    		parse += "9 ";
    		equipara(4);
    		int idPos = 0;
    		if(sig_tok.getId()==15) {
    			idPos = (int)sig_tok.getAtr();
    		}
    		
    		String idTipo = TS.buscaTipo(idPos);
    		
    		if(sig_tok.getId()==19) {
    			System.out.println("Error en la linea "  + a.linea+": Input no puede llevar parentesis");
    		}
    		
    		equipara(15);
    		equipara(18);
    		
    		if("string".equals(idTipo) || "int".equals(idTipo)) sTipo = "ok";
    		else {
    			sTipo = "error";
    			System.out.println("Error en la linea "  + a.linea+": Solo se puede hacer input de cadenas o enteros");
    		}
    	}
    	else if(sig_tok.getId() == 7) {
    		// return
    		parse += "10 ";
    		equipara(7);
    		String xTipo = X();
    		equipara(18);
    		
    		if(tipoRet == null ) {
    			System.out.println("Error en la linea "  + a.linea+": El return no debe ir fuera de una funcion");
    			sTipo ="error";
    		}
    		else if(tipoRet.equals(xTipo) ) sTipo = "ok";
    		else {
    			sTipo = "ok";
    			System.out.println("Error en la linea "  + a.linea+": Devuelve un tipo incorrecto '"+ xTipo + "'. Deberia devolver '"+ tipoRet +"'");
    		}
    	}
    	else {
    		System.out.println("Error en la linea "  + a.linea+": Se esperaba alguna sentencia simple "+ Token.cod.get(sig_tok.getId()));
    	}
        return sTipo;
    }

    public String Y(String tipo, String tipoParam) throws IOException {
    	String Ytipo = "";
    	
    	if(sig_tok.getId() == 16) {
    		// =
    		parse += "5 ";
    		equipara(16);
    		String eTipo = E();
    		if(eTipo.equals(tipo)) Ytipo = "ok";
    		else {
    			Ytipo = "error";
    			System.out.println("Error en la linea "  + a.linea+": Asignacion no válida. No se puede asignar un valor de tipo '"+eTipo+"' a una variable de tipo '"+ tipo+"'.");
    		}
    		equipara(18);
    	}
    	else if(sig_tok.getId() == 19) {
    		// (
    		parse += "6 ";
    		equipara(19);
    		String lTipo = L();
    		equipara(20);
    		equipara(18);
    		if(tipoParam.equals(lTipo) || (tipoParam.equals("void")&&lTipo.equals("")) ) Ytipo ="ok";
    		else {
    			Ytipo = "error";
    			System.out.println("Error en la linea "  + a.linea+": Se llama a la funcion con parametros incorrectos");
    		}
    	}
    	else if(sig_tok.getId() == 12) {
    		// --
    		parse += "7 ";
    		equipara(12);
    		equipara(18);
    		
    		if(tipo.equals("int")) Ytipo = "ok";
    		else {
    			System.out.println("Error en la linea "  + a.linea+": Solo se puede hacer postdecremento de un entero");
    			Ytipo = "error";
    		}
    	}
    	else {
    		System.out.println("Error en la linea "  + a.linea+" No se puede poner" + Token.cod.get(sig_tok.getId()) + " despues de una variable");
    	}
    	
        return Ytipo;
    }

    public String E() throws IOException {
    	String eTipo = "";
    	if(FirstFollow.getFirst().get("R").contains(sig_tok.getId())) {
    		parse += "33 ";
    		String rTipo = R();
    		String gTipo = G();
    		
    		if( "vacio".equals(gTipo) ) eTipo = rTipo;
    		else if( "int".equals(rTipo) && "int".equals(gTipo)) eTipo = "boolean";
    		else {
    			//error
    		}
    	}
    	
        return eTipo;
    }

    public String L() throws IOException {
    	String lTipo = "";
    	
    	if(FirstFollow.getFirst().get("L").contains(sig_tok.getId())) {
    		parse += "13 ";
    		String eTipo = E();
    		String qTipo = Q();
    		
    		if( "".equals(qTipo) ) lTipo = eTipo;
    		else lTipo = eTipo + ", " + qTipo;
    		
    	}
    	else if(FirstFollow.getFollow().get("L").contains(sig_tok.getId())) {
    		parse += "14 ";
    	}
    	
        
        return lTipo;
    }

    public String X() throws IOException {
    	String xTipo ="";
    	
    	if(FirstFollow.getFirst().get("E").contains(sig_tok.getId())) {
    		parse += "11 ";
    		String eTipo = E();
    		xTipo = eTipo;
    	}
    	else if(FirstFollow.getFollow().get("X").contains(sig_tok.getId())) {
    		parse += "12 ";
    		xTipo = "void";
    	}
    	else {
    		System.out.println("Error en la linea "  + a.linea+": No se puede devolver "+Token.cod.get(sig_tok.getId()) );
    	}
        return xTipo;
    }

    public String Q() throws IOException {
    	String qTipo = "";
    	
    	if(sig_tok.getId() == 17) {
    		parse += "15 ";
    		equipara(17);
    		String eTipo = E();
    		String q1Tipo = Q();
    		
    		if(q1Tipo.equals("")) qTipo = eTipo;
    		else qTipo = eTipo + ", " +q1Tipo;
    	}
    	else if(FirstFollow.getFollow().get("Q").contains(sig_tok.getId())) {
    		parse += "16 ";
    		qTipo = "";
    	}
    	
        return qTipo;
    }

    public String C(String tipoRet) throws IOException {
    	String cTipo = "";
    	
    	if(FirstFollow.getFirst().get("B").contains(sig_tok.getId())) {
    		parse += "28 ";
    		String bTipo = B(tipoRet);
    		String c1Tipo = C(tipoRet);
    		
    		if(c1Tipo.equals("vacio")) cTipo = bTipo;
    		else if(bTipo.equals("ok") && c1Tipo.equals("ok") ) cTipo = "ok";
    		else {
    			cTipo ="error";
    		}
    	}
    	else if(FirstFollow.getFollow().get("C").contains(sig_tok.getId())) {
    		parse += "29 ";
    		cTipo = "vacio";
    	}
    	else {
    		if(sig_tok.getId() == 2)
    			System.out.println("Error en la linea " + a.linea + ": No se permite la definicion de funciones anidadas");
    	}
        return cTipo;
    }

    public Map<String, String> T() throws IOException {
    	Map<String, String> m = new HashMap<>();
    	String tipo = "";
    	String ancho = "";
    	
    	if(sig_tok.getId() == 5) {
    		//int
    		parse += "30 ";
    		equipara(5);
    		tipo = "int";
    		ancho = "1";
    	}
    	else if(sig_tok.getId() == 1) {
    		//boolean
    		parse += "31 ";
    		equipara(1);
    		tipo= "boolean";
    		ancho = "1";
    	}
    	else if(sig_tok.getId() == 8) {
    		//string
    		parse += "32 ";
    		equipara(8);
    		tipo = "string";
    		ancho = "64"; //verificar
    	}
    	else {
    		System.out.println("Error en la linea " + a.linea + ": " +Token.cod.get(sig_tok.getId()) + " no es un tipo de datos valido");
    	}
    	m.put("tipo", tipo);
    	m.put("ancho", ancho);
    	
        return m;
    }

    public String H() throws IOException {
    	String hTipo = "";
    	
    	if(FirstFollow.getFirst().get("T").contains(sig_tok.getId())) {
    		parse += "22 ";
    		hTipo = T().get("tipo");
    	}
    	else if(sig_tok.getId() == 10) {
    		parse += "23 ";
    		equipara(10);
    		hTipo = "void";
    		
    	}
        
        return hTipo;
    }

    public Map<String, String> K() throws IOException {
    	int numParam = 0;
    	String tipo = "";
    	Map<String, String> k = new HashMap<String, String>();
    	
    	if(sig_tok.getId() == 17) {
    		parse += "26 ";
    		equipara(17);
    		zonaDec = true;
    		
    		Map<String, String> t = T();
    		int idPos = (int)sig_tok.getAtr();
    		
    		equipara(15);
    		TS.tablaActual.setTipo(idPos, t.get("tipo"));
    		TS.tablaActual.setDesp(idPos, TS.tablaActual.getDesp());
    		TS.tablaActual.setDespTabla(TS.tablaActual.getDesp() + Integer.parseInt(t.get("ancho")));
    		zonaDec = false;
    		
    		Map<String, String> k1 = K();
    		numParam = 1 + Integer.parseInt(k1.get("numParam"));
    		if(!k1.get("tipo").equals("")) {
    			tipo = t.get("tipo") + ", " + k1.get("tipo");
//    			System.out.println("Tipo de t" + t.get("tipo"));
    		}
    		else  tipo = t.get("tipo");
    	}
    	else if(FirstFollow.getFollow().get("K").contains(sig_tok.getId())) {
    		parse += "27 ";
    		//nada
    	}
    	
    	k.put("tipo", tipo);
    	k.put("numParam", numParam+"");
    	
        return k;
    }

    public String R() throws IOException {
    	String rTipo = "";
    	if(FirstFollow.getFirst().get("U").contains(sig_tok.getId())) {
    		parse += "36 ";
    		String uTipo = U();
    		String oTipo = O();
    		
    		if(oTipo.equals("vacio")) rTipo = uTipo;
    		else if(uTipo.equals("int") && oTipo.equals("int")) rTipo = "int";
    		else {
    			if(!oTipo.equals("string") || !oTipo.equals("boolean")) System.out.println("Error en la linea " + a.linea);
    			else System.out.println("Error en la linea " + a.linea+ ": Tipos deben ser enteros en una suma");
    		}
    	}
        return rTipo;
    }

    public String G() throws IOException {
    	String gTipo = "";
    	
    	if(sig_tok.getId() == 25) {
    		parse += "34 ";
    		equipara(25);
    		String rTipo = R();
    		String g1Tipo = G();
    		if(rTipo.equals("int") && g1Tipo.equals("int")) gTipo = "int";
    		if(rTipo.equals("int") && g1Tipo.equals("vacio")) gTipo = "int";
    		else {
    			System.out.println("Error en la linea " + a.linea + ": La operacion != solo se puede realizar en enteros");
    		}
    	}
    	else if(FirstFollow.getFollow().get("G").contains(sig_tok.getId())) {
    		parse += "35 ";
    		gTipo = "vacio";
    	}
        return gTipo;
    }

    public String U() throws IOException {
    	String uTipo = "";
    	if(sig_tok.getId() == 24) {
    		parse += "39 ";
    		equipara(24);
    		String vTipo = V();
    		if(vTipo.equals("boolean")) uTipo = "boolean";
    		else System.out.println("Error en la linea "  + a.linea+ " Debe ser de tipo logico");
    	}
    	else if(FirstFollow.getFirst().get("V").contains(sig_tok.getId())) {
    		parse += "40 ";
    		uTipo = V();
    	}
        return uTipo;
    }

    public String O() throws IOException {
    	String oTipo = "";
    	if(sig_tok.getId() == 23) {
    		parse += "37 ";
    		equipara(23);
    		String uTipo = U();
    		String o1Tipo = O();
    		if(uTipo.equals("int") && o1Tipo.equals("vacio")) oTipo = "int";
    		else if( uTipo.equals("int") && o1Tipo.equals("int")) oTipo = "int";
    		else System.out.println("Error en la linea "  + a.linea+": Tipos deben ser enteros");
    	}
    	else if(FirstFollow.getFollow().get("O").contains(sig_tok.getId())) {
    		parse += "38 ";
    		oTipo = "vacio";
    	}
    	
        return oTipo;
    }

    public String V() throws IOException {
    	String vTipo ="";
    	
    	if(sig_tok.getId() == 15) {
    		parse += "41 ";
    		int idPos = (int)sig_tok.getAtr();
    		String idTipo = TS.buscaTipo(idPos);
    		String tipoParam = ""; 
    		
    		equipara(15);
    		
    		if( "function".equals(idTipo) ) tipoParam = TS.global().getTipoParam(idPos);
    		String dev = Z(idTipo, tipoParam, idPos);
    		
    		if( "function".equals(idTipo) ) {
    			vTipo = TS.global().getTipoRet(idPos);
    		}else {
    			vTipo = dev;
//    			System.out.println("dev: " +dev);
    		}
    	}
    	else if(sig_tok.getId() == 13) {
    		// int
    		parse += "42 ";
    		equipara(13);
    		vTipo = "int";
    	}
    	else if(sig_tok.getId() == 14) {
    		// string
    		parse += "43 ";
    		equipara(14);
    		vTipo = "string";
    	}
    	else if(sig_tok.getId() == 19) {
    		// (
    		parse += "44 ";
    		equipara(19);
    		vTipo = E();
    		equipara(20);
    	}
        return vTipo;
    }


    public String Z(String idTipo, String tipoParam, int idPos) throws IOException {
    	String dev = "";
    	
    	if(sig_tok.getId() == 19) {
    		// (
    		if(idTipo == null) {
    			System.out.println("Error en la linea " + a.linea+ ": Función '"+ TS.tablaActual.getLexema(idPos) + "' no declarada");
    		}
    		parse += "45 ";
    		equipara(19);
    		String lTipo = L();
    		equipara(20);
    		if(!lTipo.equals(tipoParam)) {
    			System.out.println("Error en la linea " + a.linea+ ": Llamada a funcion con parametros incorrectos");
    		}
    	}
    	else if(sig_tok.getId() == 12) {
    		// --
    		parse += "47";
    		equipara(12);
    		if(!idTipo.equals("int")) {
    			System.out.println("Error en la linea " + a.linea + ": Solo se puede hacer postdescremento de variables enteras");
    		}
    		dev = idTipo;
    	}
    	else if(FirstFollow.getFollow().get("Z").contains(sig_tok.getId())) {
    		parse += "46 ";
    		
    		if( idTipo == null || TS.buscaTipo(idPos) == null) {
    			// no ha sido declarada
    			TS.global().setTipo(idPos, "int");
    			TS.global().setDesp(idPos, TS.global().getDesp() );
    			TS.global().setDespTabla( TS.global().getDesp() + 1 );
    			dev = "int";
    			
    		}
    		else {
    			dev = idTipo;
    		}
    	}
    	else {
			System.out.print("Error en la linea "  + a.linea); //			Error();
			System.out.println(" Se esperaba una expresion simple no "  + Token.cod.get(sig_tok.getId()) );
		}
    	
    	return dev;
     
    }
    
	private void equipara(int i) throws IOException {
		
		if(sig_tok.getId() == i) {
			sig_tok = a.ALexico();
			writer2.write(sig_tok.toString());
			writer2.newLine();
		}
		else {
			System.out.print("Error en la linea "  + a.linea);
			System.out.println(": Se esperaba "+ Token.cod.get(i) +" pero se encontro " + "'" + Token.cod.get(sig_tok.getId()) + "'");
			return; // Aca los errores seran en la linea anterior
		}	
	}
	
	public static void main(String[] args) {
		
		 try(BufferedWriter wr = new BufferedWriter(new FileWriter(args[2]))) {
	            Asem ast = new Asem(args[0], wr, args[3]);
	            
	            try (BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
	            		) {
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