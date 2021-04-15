package Tienda;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 
 * @author esmer
 *
 */

public class VideoJuego extends Validar implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Atributos
	 */
	private Integer COD;
	private String nombre;
	private LocalDate fechaLanzamiento;
	private Plataformas plataforma;
	private static Integer contCOD = 0;

	public VideoJuego(String nombre) {
		this.nombre = nombre;
		contCOD++;
		COD = contCOD;
	}

	public VideoJuego(String nombre, LocalDate fechaLanzamiento, Plataformas plataforma) {
		this.nombre = nombre;
		this.fechaLanzamiento = fechaLanzamiento;
		this.plataforma = plataforma;
		contCOD++;
		COD = contCOD;
	}

	public String getNombre() {
		return nombre;
	}

	/**
	 * En este método comprueba si ya hay algún nombre, si ya hay uno te deja
	 * cambiar el nombre pero si no hay ninguno salta la excepción.
	 * 
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		if (nombre.isBlank()) {
			throw new IllegalArgumentException(
					"[ERROR]: No se puede establecer un nuevo nombre si no existe ninguno!!");
		} else {
			this.nombre = nombre;
		}
	}

	public LocalDate getFechaLanzamiento() {
		return fechaLanzamiento;
	}

	/**
	 * Este método nos comprueba con 
	 * @param fechaLanzamiento
	 * @return
	 */
	public boolean setFechaLanzamiento(LocalDate fechaLanzamiento) {

		try {
			if (Validar.validarFecha(fechaLanzamiento)) {
				this.fechaLanzamiento = fechaLanzamiento;
				return true;
			} else {
				System.err.println("[ERROR]: La fecha introducida no puede ser posterior a la fecha actual.");
				return false;
			}
		}catch (Exception e) {
			System.err.println("La fecha introducida es errónea.");
			return false;
		}
	}

	public Plataformas getPlataforma() {
		return plataforma;
	}

	public boolean setPlataforma(String x) {
		if (Validar.contains(x.toUpperCase())) {
			this.plataforma = Plataformas.valueOf(x.toUpperCase());
			return true;
		} else {
			System.err.println("La plataforma introducida no existe.");
			return false;
			// He decidido no introducir esta excepción ya que al lanzarla me paraba el
			// programa
			// por lo que optado por no añadirla.
			// throw new IllegalArgumentException();
		}

	}

	public Integer getCOD() {
		return COD;
	}

	public void setCOD(Integer cOD) {
		COD = cOD;
	}

	public static Integer getContCOD() {
		return contCOD;
	}

	public static void setContCOD(Integer contCOD) {
		VideoJuego.contCOD = contCOD;
	}

	@Override
	public String toString() {
		return "Código: " + COD + "\nNombre: " + nombre + "\nFechaLanzamiento: " + fechaLanzamiento + "\nPlataforma:"
				+ plataforma + "\n ========================================";
	}

}
