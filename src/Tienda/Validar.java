package Tienda;

import java.time.DateTimeException;
import java.time.LocalDate;import java.util.Calendar;
import java.util.HashSet;

/**
 * 
 * @author esmer
 *
 */
public abstract class Validar{

	/*
	 * Método para saber si la fecha que introducimos es posterior a la actual.
	 */
	public static boolean validarFecha(LocalDate f) {
		if (f.isAfter(LocalDate.now())) {
			// Fecha no válida
			//throw new DateTimeException("Date Time Exception.");
			System.err.println("UPS algo ha ocurrido con la fecha....");
			return false;
		} else {
			// Fecha Válida
			return true;
		}
	}

	/**
	 * Método para comprobar si la platadorma introducida es válida.
	 * @param test
	 * @return
	 */
	public static boolean contains(String x) {

		for (Plataformas c : Plataformas.values()) {
			if (c.name().equals(x)) {
				return true;
			}
		}

		return false;
	}

}
