import java.util.HashMap;
import java.util.Map;

public class Tabla {
	
	static class Fila{
		String lexema;
		String tipo;
		int desp;
		int numParam;
		String tipoParam;
		String tipoRet;
		String etiqueta;
	}
	
	 Map<Integer, Fila> tabla = new HashMap<>();
	private int desp;
	
	public Tabla() {
		tabla = new HashMap<>();
		desp = 0;
	}
	
	public Integer buscaID(String lexema) {
	    for (Map.Entry<Integer, Tabla.Fila> entry : tabla.entrySet()) {
	        if (entry.getValue().lexema.equals(lexema)) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}

	Integer insertaID(String lexema) {
		Fila s = new Fila();
		s.lexema = lexema;
		
		if(TS.contadorTS == 2) {
			tabla.put(tabla.size()+400000, s);
			return tabla.size() - 1 + 400000;
		}
		else {
			tabla.put(tabla.size(), s);
			return tabla.size() - 1;
		}
	}
	
	Integer insertaIDglobal(String lexema) {
		Fila s = new Fila();
		s.lexema = lexema;
		tabla.put(tabla.size(), s);
		return tabla.size() - 1;
	}
	
	 public String buscaTipo(int pos) {
			if(tabla.get(pos) != null) return tabla.get(pos).tipo;
			else return null;
	}
	 
	public void setDesp(int i, int desp) {
		tabla.get(i).desp = desp;
	}
	
	public void setDespTabla(int d) {
		desp = d;
	}
	
	public void setTipo(int idPos, String tipo) {
		tabla.get(idPos).tipo = tipo;		
	}
	
	public void setTipoRet(int idPos, String tipo) {
		tabla.get(idPos).tipoRet = tipo;		
	}
	
	public void setEt(int idPos, String et) {
		tabla.get(idPos).etiqueta = et;		
	}
	
	public void setNumParam(int idPos, int numParam) {
		tabla.get(idPos).numParam = numParam;
	}
	
	public void setTipoParam(int idPos, String tipoParam) {
		tabla.get(idPos).tipoParam = tipoParam;
	}
	
	public String getTipoRet(int idPos) {
		return tabla.get(idPos).tipoRet;
	}
	
	public String getLexema(int idPos) {
		return tabla.get(idPos).lexema;
	}
	
	public String getTipoParam(int idPos) {
		return tabla.get(idPos).tipoParam;
	}
	
	public int getDesp() {
		return desp;
	}
	
//	public static void guardarTablaGlobal() {
//		try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\lala1\\Downloads\\ts.txt"))) {
//			// Encabezado de la TS
//			writer.write("Tabla de Simbolos Principal # " + contadorTS + " :");
//			writer.newLine(); // Salto de línea obligatorio
//
//			// Escribir cada lexema en el formato indicado
//			//			for (Entry<Integer, Tabla.Fila> entry : tabla.entrySet()) {
//			//				String lexema = entry.getKey();
//			writer.write(" * '");
//			//				writer.write(lexema);
//			writer.write("'");
//			writer.newLine(); // Salto de línea obligatorio
//			//			}
//
//			// Incrementar el contador para futuras TS
//			//			contadorTS++;
//
//			System.out.println("Tabla guardada exitosamente en ts.txt");
//		} catch (IOException e) {
//			System.err.println("Error al guardar la tabla en ts.txt: " + e.getMessage());
//		}
//	}

	

	
	
   
}
