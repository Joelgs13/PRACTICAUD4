package joel.adat.model;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Clase que representa una olimpiada, con detalles sobre su nombre, año, temporada y ciudad.
 */
@Entity
@Table(name = "Olimpiada")
public class ModeloOlimpiada {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_olimpiada")
	private int idOlimpiada;

	@Column(name = "nombre", nullable = false, length = 100)
	private String nombreOlimpiada;

	@Column(name = "anio", nullable = false)
	private int anio;

	@Column(name = "temporada", nullable = false, length = 10)
	private String temporada;

	@Column(name = "ciudad", nullable = false, length = 100)
	private String ciudad;

	// Constructor vacío requerido por Hibernate
	public ModeloOlimpiada() {}

	/**
	 * Constructor que inicializa los detalles de una olimpiada.
	 *
	 * @param nombreOlimpiada El nombre oficial de la olimpiada.
	 * @param anio El año en que se celebró la olimpiada.
	 * @param temporada La temporada en la que se celebró ("Winter" o "Summer").
	 * @param ciudad La ciudad donde tuvo lugar la olimpiada.
	 */
	public ModeloOlimpiada(String nombreOlimpiada, int anio, String temporada, String ciudad) {
		this.nombreOlimpiada = nombreOlimpiada;
		this.anio = anio;
		this.temporada = temporada;
		this.ciudad = ciudad;
	}

	public int getIdOlimpiada() {
		return idOlimpiada;
	}

	public void setIdOlimpiada(int idOlimpiada) {
		this.idOlimpiada = idOlimpiada;
	}

	public String getNombreOlimpiada() {
		return nombreOlimpiada;
	}

	public void setNombreOlimpiada(String nombreOlimpiada) {
		this.nombreOlimpiada = nombreOlimpiada;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public String getTemporada() {
		return temporada;
	}

	public void setTemporada(String temporada) {
		this.temporada = temporada;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	@Override
	public String toString() {
		return this.nombreOlimpiada + "," + this.anio + "," + this.temporada + "," + this.ciudad;
	}

	@Override
	public int hashCode() {
		return Objects.hash(anio, ciudad, nombreOlimpiada, temporada);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		ModeloOlimpiada other = (ModeloOlimpiada) obj;
		return anio == other.anio && Objects.equals(ciudad, other.ciudad)
				&& Objects.equals(nombreOlimpiada, other.nombreOlimpiada) && Objects.equals(temporada, other.temporada);
	}
}
