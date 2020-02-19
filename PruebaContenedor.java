import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

public class PruebaContenedor{
	public static void main(String[] args) throws IOException {

		ContenedorDeEnteros a = new ContenedorDeEnteros();
		int[] v;
		System.out.println("El contenedor a tiene "+a.cardinal()+" elementos.");
		for(int i=0; i<10; i++){
			a.insertar(i);
			a.buscar(i);
		}
		v = a.elementos();
		for(int i=0; i<a.cardinal(); i++) System.out.println(v[i]);
		a.vaciar();
		for(int i=0; i<100; i++){
			a.insertar(i);
			a.extraer(i);
		}

		PrintWriter output = new PrintWriter("salida3.txt");
		PruebaContenedor.insertionextractionTest(output);
	    PruebaContenedor.SuccessfullFetchTest(output);
		PruebaContenedor.UnSuccessfullFetchTest(output);
		output.close();
	}

	/*
	 * Metodo para transferir los datos del archivo a una matriz para organizarlos
	 * de 10000 en 10000 y hacer las operaciones pertinentes
	 * @param El archivo a utilizar, el numero de filas y el de columnas
	 */
	public static int [][] DataInserter(String file, int rows, int columns) throws IOException{
		RandomAccessFile dat = new RandomAccessFile(file, "r");
		int [][] values = new int[rows][columns];
		for (int i = 0; i < rows ;i++){
			for (int k = 0; k < columns; k++){
				values[i][k] = dat.readInt();
			}
		}
		dat.close();
		return values;
	}

	/*
	 * Metodo para transferir los datos del archivo a un array para
	 * realizar comodamente las busquedas
	 * @param El fichero a utilizar y el numero de columnas
	 */
	public static int [] DataInserter(String file, int columns) throws IOException{
		RandomAccessFile dat = new RandomAccessFile(file, "r");
		int [] values = new int[columns];
		for (int i = 0; i < columns; i++){
			values[i] = dat.readInt();
		}
		dat.close();
		return values;
	}

	/*
	 * Test de insercion y extraccion
	 * @param EL PrintWriter para hacer la salida
	 */
	public static void insertionextractionTest(PrintWriter output) throws IOException{
		long time;
		Printer("Insercion:", output);
		ContenedorDeEnteros contenedor = new ContenedorDeEnteros();
		int [][] values = DataInserter("datos.dat", 10, 10000);
		float [] elapsedtime = new float[10];

		for (int k = 0; k < 10; k++){
			time = System.nanoTime();
			for (int i = 0; i < 10000; i++){
				contenedor.insertar(values[k][i]);
			}
			elapsedtime[k] = ((System.nanoTime() - time)/10000000.f);
			Printer(elapsedtime[k] + " ms/10000 elementos", output);
		}
		
		Printer("\nExtraccion:", output);
		for (int k = 0; k < 10; k++){
			time = System.nanoTime();
			for (int i = 0; i < 10000; i++){
				contenedor.extraer(values[k][i]);
			}
			elapsedtime[k] = ((System.nanoTime() - time)/10000000.f);
			Printer(elapsedtime[k] + " ms/10000 elementos", output);
		}
	}

	/*
	 * Test busqueda exitosa
	 * @param PrinterWriter para imprimir
	 */
	public static void SuccessfullFetchTest(PrintWriter output) throws IOException{
		Printer("\nBusqueda exitosa:", output);
		long time = 0;
		int [] values = DataInserter("datos.dat", 100000);
		ContenedorDeEnteros contenedor = new ContenedorDeEnteros();
		float [] elapsedtime = new float[10];
		RandomAccessFile dat = new RandomAccessFile("datos.dat", "r");

		for (int k = 0; k < 10; k++){
			for (int i = 0; i < 10000; i++){
				contenedor.insertar(dat.readInt());
			}
			time = System.currentTimeMillis();
			for(int g = 0; g < 100; g++){
			    for(int j = 0; j < 10000*(k+1); j++) {
				    contenedor.buscar(values[j]);
			    }
			}
			elapsedtime[k] =  (System.currentTimeMillis() - time)/(1000.f*(k+1));
		    Printer(elapsedtime[k] + " ms/10000 elementos", output);
		}
		dat.close();
	}

	/*
	 * Test de busqueda infructuosa
	 * @param PrinterWriter para imprimir
	 */
	public static void UnSuccessfullFetchTest(PrintWriter output) throws IOException{
		Printer("\nBusqueda infructuosa:", output);
		long time = 0;
		int values[] = DataInserter("datos_no.dat", 20000);
		ContenedorDeEnteros contenedor = new ContenedorDeEnteros();
		RandomAccessFile dat = new RandomAccessFile("datos.dat", "r");
		float [] elapsedtime = new float[10];

		for (int k = 0; k < 10; k++){
			for (int i = 0; i < 10000; i++){
				contenedor.insertar(dat.readInt());
			}
			time = System.currentTimeMillis();
			for(int g = 0; g < 100; g++){
			    for(int j = 0; j < 20000; j++) {
				    contenedor.buscar(values[j]);
			    }
			}
			elapsedtime[k] =  (System.currentTimeMillis() - time)/2000.f;
			Printer(elapsedtime[k] + " ms/10000 elementos", output);
		}
		dat.close();
	}

	/*
	 * Metodo de impresion en consola y archivo.
	 * @param La string a imprimir y el PrintWriter a utilizar
	 */
	public static void Printer(String toPrint, PrintWriter output)throws IOException{
        System.out.println(toPrint);
        output.println(toPrint);
	}
}
