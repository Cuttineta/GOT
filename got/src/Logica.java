import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Logica {

	private File carpeta;
	private File[] archivos;
	private List<String> palabras;
	private Map<String, Double> mapeoPalabras;

	public void cargarCarpeta(String ruta) throws InvalidPathException {

		palabras = new LinkedList<String>();
		carpeta = new File(ruta);

		if (carpeta.isDirectory()) {

			archivos = carpeta.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".txt");
				}
			});
		} else {
			throw new InvalidPathException("Ruta invalida");
		}

		for (int i = 0; i < archivos.length; i++) {

			try {
				leerPalabras(archivos[i]);
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}

	}

	private void leerPalabras(File archivo) throws IOException {
		BufferedReader entrada = new BufferedReader(new FileReader(archivo));
		String renglon = entrada.readLine();
		while (renglon != null) {
			StringTokenizer st = new StringTokenizer(renglon);
			while (st.hasMoreTokens()) {
				palabras.add(st.nextToken());
			}
			renglon = entrada.readLine();

		}

		listarPalabras();

	}

	private void listarPalabras() {

		List<String> palabrasSinSimbolos = new LinkedList<String>();
		Iterator<String> it = palabras.iterator();

		while (it.hasNext()) {
			String act = it.next();
			// String reemplazo = act.replaceAll("[^\\dA-Za-z·ÈÌÛ˙¡…Õ”⁄Ò—]", "");
			String reemplazo = eliminarSimbolos(act);
			palabrasSinSimbolos.add(reemplazo.toLowerCase());
		}

		palabras = palabrasSinSimbolos;

	}

	private String eliminarSimbolos(String palabra) {
		String salida = "";
		for (int i = 0; i < palabra.length(); i++) {
			int t = palabra.charAt(i);
			if (esValido(t)) {
				salida += palabra.charAt(i);

			}

		}
		return salida;

	}

	private boolean esValido(int ascii) {
		return ((ascii >= 97 && ascii <= 122) || (ascii >= 65) && (ascii <= 90) || ascii == 225 || ascii == 233
				|| ascii == 237 || ascii == 243 || ascii == 250 || ascii == 193 || ascii == 201 || ascii == 205
				|| ascii == 211 || ascii == 218 || ascii == 220 || ascii == 209);
	}

	public void mapearPalabras() {

		mapeoPalabras = new HashMap<String, Double>();
		int cantApariciones = 0;
		double porcentaje = 0;
		double totalPalabras = palabras.size();
		for (String p : palabras) {

			cantApariciones = cantAp(p, palabras);
			porcentaje = cantApariciones * 100 / totalPalabras;
			mapeoPalabras.put(p, porcentaje);

		}

	}

	private int cantAp(String p, List<String> palabras) {
		int salida = 0;

		for (String pal : palabras) {
			if (p.equals(pal)) {
				salida++;
			}
		}

		return salida;

	}

	public Map<String, Double> ordenarEntradas() {

		ValueComparator vc = new ValueComparator(mapeoPalabras);
		TreeMap<String, Double> mapaOrdenado = new TreeMap<String, Double>(vc);
		mapaOrdenado.putAll(mapeoPalabras);

		return mapaOrdenado;
	}

}
