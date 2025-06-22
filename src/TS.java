import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TS {
	
	private static Tabla global;
	private static Tabla local;
	public static Tabla tablaActual;
	public static int contadorTS;
	private static int contadorLocales;

	
	public static Tabla crearTS() {
		if(contadorTS==1) contadorLocales++;
		contadorTS++;
		return new Tabla();
	}
	
	public static void setGlobal(Tabla t) {
		global = t;
	}
	public static void setLocal(Tabla t) {
		local = t;
	}
	public static void setActual(Tabla t) {
		tablaActual = t;
	}
	
	public static Integer buscaID(String lexema) {
		Integer pos = null;
		
    	pos = tablaActual.buscaID(lexema);

    	if(pos == null && contadorTS==2) {
    		pos = global.buscaID(lexema);
    	}
		return pos;
	}

	public static Integer insertaID(String lexema) {
		return tablaActual.insertaID(lexema);
	}
	
	public static String buscaTipo(Integer pos) {
		String tipo = null;
		if(pos >= 400000) tipo = local.buscaTipo(pos);
		else tipo = global.buscaTipo(pos);
		return tipo;
	}
	
	public static Tabla global() {
		return global;
	}
	
	public static void destruirTS() {
		tablaActual = null;
		if(contadorTS ==2) local = null;
		contadorTS--;
	}
	
	public static String getLexema(int idPos) {
		String lex = "";
		if(idPos >= 400000) lex= local.getLexema(idPos);
		else lex = global.getLexema(idPos);
		return lex;
	}

	

    public static void guardarTabla(String tipo, String name, String fileName) {
        boolean append = true;
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, append))) {
            if (tipo.equals("local")) {
                guardarTabla(writer, local, "TABLA LOCAL", name);
            } else if (tipo.equals("global")) {
                guardarTabla(writer, global, "TABLA GLOBAL", "");
            }
            
            System.out.println("Tabla " + tipo + " guardada exitosamente en " + fileName);
        } catch (IOException e) {
            System.err.println("Error al guardar la tabla " + tipo + " en " + fileName + ": " + e.getMessage());
        }
    }


    private static void guardarTabla(BufferedWriter writer, Tabla tabla, String titulo, String name) throws IOException {
        
    	writer.newLine();
    	
    	if(titulo.equals("TABLA LOCAL")) {
    		writer.write(titulo+ " #" + contadorLocales +":");
    	}else {
    		writer.write(titulo + " #" + 0 + ":");
    	}
    	
        writer.newLine();
        writer.newLine();

        for (Tabla.Fila f : tabla.tabla.values()) {
            writer.write("* LEXEMA : '" + f.lexema + "'");
            writer.newLine();
            writer.write("ATRIBUTOS:");
            writer.newLine();

            if (f.tipo != null && !f.tipo.isEmpty()) {
                writer.write("+ tipo : '" + f.tipo + "'");
                writer.newLine();
            }
            
            if(!"function".equals(f.tipo)) {
            	writer.write("+ despl : " + f.desp);
            	writer.newLine();
            }
            
            if("function".equals(f.tipo)) {

            	if (f.numParam >= 0) {
            		writer.write("+ numParam : " + f.numParam);
            		writer.newLine();
            	}

            	if (f.numParam>0 && f.tipoParam != null && !f.tipoParam.isEmpty()) {
            		String[] tiposParam = f.tipoParam.split(",");
            		for (int i = 0; i < tiposParam.length; i++) {
            			writer.write("+ TipoParam" + String.format("%02d", i+1) + " : '" + tiposParam[i].trim() + "'");
            			writer.newLine();
            		}
            	}

            	if (f.tipoRet != null && !f.tipoRet.isEmpty()) {
            		writer.write("+ TipoRetorno : '" + f.tipoRet + "'");
            		writer.newLine();
            	}

            	if (f.etiqueta != null && !f.etiqueta.isEmpty()) {
            		writer.write("+ EtiqFuncion : '" + f.etiqueta + "'");
            		writer.newLine();
            	}
            }

            writer.write("---------------------------------------------------");
            writer.newLine();
        }
    }
	
	public static void main(String[] args) {
	}

}
