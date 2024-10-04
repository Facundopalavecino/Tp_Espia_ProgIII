package Logic;

import java.util.Objects;

public class Espia {
	private String nombre;
	private String mensaje;
	private double peso;
	
	public Espia(String nombre, String mensaje, double peso) {
		this.nombre = nombre;
		this.mensaje = mensaje;
		this.peso = peso;
	}
	
	public Espia() {}

	public String getNombre() {
		return nombre;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
		
	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre,mensaje);
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
		
		return Objects.equals(nombre, other.nombre) && Objects.equals(mensaje, other.mensaje);
	}
	
}
