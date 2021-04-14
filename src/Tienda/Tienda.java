package Tienda;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Tienda {

	Scanner sc = new Scanner(System.in);
	List<VideoJuego> videojuegos;

	public Tienda(List<VideoJuego> videojuegos) {
		super();
		this.videojuegos = videojuegos;
	}

	/**
	 * Método parañadir un nuevo videojuego a la tienda.
	 */
	public void addVideojuego() {
		try {
			if(videojuegos.size() <= 9) {
				try {
					System.out.println("---Introduce los datos del videojuego---");
					System.out.println("Nombre del videojuego:");
					sc.nextLine();
					String name = sc.nextLine();
					System.out.println("Plataforma principal:");
					String plataforma = sc.nextLine();
					System.out.println("Fecha de lanzamiento:");
					System.out.println("Año:");
					int year = sc.nextInt();
					System.out.println("Mes:");
					int month = sc.nextInt();
					System.out.println("Dia:");
					int day = sc.nextInt();
					VideoJuego vj = new VideoJuego(name);
					if(vj.setPlataforma(plataforma) && vj.setFechaLanzamiento(LocalDate.of(year, month, day))) {
						vj.setPlataforma(plataforma);
						vj.setFechaLanzamiento(LocalDate.of(year, month, day));
						videojuegos.add(vj);
					}else {
						System.err.println("Algunos de los datos introducidos son erróneos.");
					}
					
				} catch (Exception e) {
					System.err.println("[ERROR]: Ha ocurrido un error al introducir alguno de los datos.");
					e.printStackTrace();
				}
			}else {
				System.out.println("Lo sentimos no se pueden añadir más de 10 videojuegos.");
			}
		} catch (Exception e) {
			System.err.println("[ERROR]: Ha ocurrido un error al comprobar el tamaño del array.");
		}
		

	}

	/**
	 * Método para mostrar todos los videojuegos.
	 */
	public void showVideojuegos(List<VideoJuego> v) {
		if (videojuegos.isEmpty()) {
			System.out.println("No hay ningún videojuego creado.");
		} else {
			System.out.println("--->Estos son los juegos actuales<---");
			v.stream().forEach(System.out::println);

			System.out.println("Total de video juegos: " + videojuegos.stream().count());

		}
	}

	/* Método para eliminar un videojuego */
	public void deleteVideojuego() {
		showVideojuegos(videojuegos);
		System.out.println("Introduzca el códido del videojuego que desea eliminar: ");
		int cod = sc.nextInt();
		if (videojuegos.stream().anyMatch(l -> l.getCOD().equals(cod))) {
			System.out.println("Estás seguro que quieres eliminar el videojuego - (SI/NO)");
			String confirmacion = sc.next();
			if (confirmacion.equalsIgnoreCase("SI")) {
				videojuegos.remove(videojuegos.stream().filter(l -> l.getCOD().equals(cod)).findAny().get());
				System.out.println("Videojuego eliminado con éxito.");
			} else {
				System.out.println("Se ha elejido no eliminar el videojuego.");
				System.out.println("Volviendo a menu.....");
			}
		} else {
			System.err.println("[ERROR]: El código introducido no corresponde con ningún videojuego.");
		}
	}

	/* Método para guardar en el archivo los datos de la colección */
	public void save(List<VideoJuego> list) {
		try {
			FileOutputStream fos = new FileOutputStream("./videojuegos.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			for (VideoJuego s : list) {
				oos.writeObject(s);
			}
			if (oos != null) {
				oos.close();
				fos.close();
			}

			System.out.println("Información guardada con éxito en el fichero videojuego.dat");
		} catch (IOException e) {
			System.err.println("[ERROR]: Ups ha ocurrido un error.");
			e.printStackTrace();
		}
	}

	/* Añadir datos desde archivo */
	public static void leerArchivo(String nombre, List<VideoJuego> lista) {
		try {
			File file = null;
			FileInputStream fe = null;
			ObjectInputStream ois = null;
			try {
				file = new File(nombre);
				if (file.exists()) {
					fe = new FileInputStream(file);
					ois = new ObjectInputStream(fe);
					while (true) {
						VideoJuego videojuego = null;
						videojuego = (VideoJuego) ois.readObject();
						lista.add(videojuego);
					}
				}
			} catch (EOFException e) {
				System.out.println("");
			} catch (FileNotFoundException fnf) {
				System.err.println("El fichero no se ha encontrado " + fnf);
			} catch (IOException e) {
				System.err.println("Error encontrado: ");
				e.printStackTrace();
			} catch (Throwable e) {
				System.err.println("Error de programa: " + e);
				e.printStackTrace();
			} finally {
				if (ois != null) {
					ois.close();
					fe.close();
				}
				VideoJuego.setContCOD(lista.get(lista.size() - 1).getCOD() + 1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Recuperar datos */

	public void recuperarDatos() throws IOException {
		// En esta parte vamos a duplicar nuestro archivo a uno temporal para comprobar
		// si son iguales.
		String ruta = "./videojuegos.dat";
		File origin = new File(ruta);
		File temp = File.createTempFile("./temp.dat", null);
		if (origin.exists()) {
			videojuegos.clear();
			try {
				InputStream in = new FileInputStream(origin);
				OutputStream out = new FileOutputStream(temp);
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		} else {
			System.err.println("[ERROR]: Cree un archivo antes de usar esta opción.");
			exit();
		}

		// Aquí comprobamos si son iguales nuestros archivos.
		Path p2 = Paths.get("./temp.dat");
		Path p1 = Paths.get(ruta);

		if (p1.equals(p2)) {
			System.out.println("Lo sentimos no ha realizado ningún cambio.");
		} else {
			System.out.println("Ha realizado cambios que no ha guardado en disco.");
			System.out.println("Si continúa, se restaurarán los datos y \n perderá los cambios no guardados.");
			System.out.println("¿Desea continuar con los cambios? (SI/NO)");
			String confirmacion = sc.next();
			if (confirmacion.equalsIgnoreCase("SI")) {
				leerArchivo(ruta, videojuegos);
				System.out.println("Datos restaurados con éxito!!");
			} else {
				System.out.println("Cancelando operación....");
			}

		}
		temp.deleteOnExit();
	}

	public void exit() {
		System.out.println("Hasta pronto!!");
		System.exit(0);
	}

	/* Este método vamos a utilizarlo para la unión de nuestro programa en un meú */
	public void menu() {
		inciarTest();
		try {
			do {
				System.out.println("========================================");
				System.out.println("=========Gestión de videojuegos=========");
				System.out.println("========================================");
				System.out.println("Que acción desea realizar: ");
				System.out.println("1.- Añadir un videojuego.");
				System.out.println("2.- Listar videojuegos.");
				System.out.println("3.- Borrar un videojuego.");
				System.out.println("4.- Guardar datos en fichero.");
				System.out.println("5.- Recuperar datos desde fichero.");
				System.out.println("0.- Salir");
				int num = sc.nextInt();
				switch (num) {
				case 1:
					addVideojuego();
					break;
				case 2:
					showVideojuegos(videojuegos);
					break;
				case 3:
					deleteVideojuego();
					break;
				case 4:
					save(videojuegos);
					break;
				case 5:
					recuperarDatos();
					break;
				default:
					exit();
					break;
				}
			} while (true);
		} catch (Exception e) {
			System.err.println("[ERROR]: Alguno de los valores que ha introducido son erróneos.");
			
		}
	}

	/*
	 * Con este método vamos a comprobar al incio del programa si existe algún
	 * archivo.
	 */
	public void inciarTest() {

		Path p = Paths.get("./videojuegos.dat");

		if (Files.notExists(p)) {
			// No existe
			try {
				System.out.println("No hay datos guardados!!");
				System.out.println("Al no haber datos guardados cuando seleccione la opción save se creará uno.");
				Files.createFile(p);
			} catch (IOException e) {
				System.err.println("[ERROR]: Ha ocurrido un error al comprobar si existe algún archivo guardado.");
				e.printStackTrace();
			}
		}else {
			try {
				leerArchivo("./videojuegos.dat", videojuegos);
				System.out.println("Datos cargados con éxito!!");
			} catch (Exception e) {
				System.err.println("No se ha cargado ningún archivo.");
			}
			
		}
	}

}
