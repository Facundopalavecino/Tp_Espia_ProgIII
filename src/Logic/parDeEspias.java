package Logic;

import java.util.Objects;

public class parDeEspias {
		Espia espia1;
		Espia espia2;
		double probabilidad;
		
		public parDeEspias (Espia e1, Espia e2, double prob) {
			this.espia1 = e1;
			this.espia2 = e2;
			this.probabilidad = prob;
		}

		public Espia getEspia1() {
			return espia1;
		}

		public Espia getEspia2() {
			return espia2;
		}

		public double getProbabilidad() {
			return probabilidad;
		}
		
		@Override
		public String toString() {
			return "Ciudades: " + espia1.getNombre()+ ", " + espia2.getNombre() + ", Costo de la Conexion: " + probabilidad;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(espia1, espia2, probabilidad);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			parDeEspias otro = (parDeEspias) obj;
			return Objects.equals(espia1, otro.espia1) && Objects.equals(espia2, otro.getEspia2()) 	
					&& Double.doubleToLongBits(probabilidad) == Double.doubleToLongBits(probabilidad);
		}
}
