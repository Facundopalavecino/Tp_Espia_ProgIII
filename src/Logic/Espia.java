package Logic;

import java.util.Objects;

public class Espia {
	private String nombre;
	
	public Espia(String nombre) {
		this.nombre = nombre;
	}
	
	public Espia() {}

	public String getNombre() {
		return nombre;
	}

	@Override
	public int hashCode() {
		return nombre.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Espia other = (Espia) obj;
		
		return Objects.equals(nombre, other.nombre);
	}
	
}
