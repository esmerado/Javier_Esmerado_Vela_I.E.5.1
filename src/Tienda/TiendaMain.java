package Tienda;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author esmer
 *
 */
public class TiendaMain {

	public static void main(String[] args) {
	
		List<VideoJuego> videojuegos = new ArrayList();
		Tienda t = new Tienda(videojuegos);
		
		t.menu();
		
		

	}

}
