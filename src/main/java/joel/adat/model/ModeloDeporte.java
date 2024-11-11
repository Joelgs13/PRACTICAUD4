package joel.adat.model;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * Clase que representa un deporte, con su nombre.
 */
@Entity
@Table(name = "Deporte")
public class ModeloDeporte {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_deporte")
	private int idDeporte;

	@Column(name = "nombre", nullable = false, length = 100)
	private String nombreDeporte;

	// Constructor vacío necesario para Hibernate
	public ModeloDeporte() {}

	/**
	 * Constructor que inicializa el nombre del deporte.
	 *
	 * @param nombre El nombre del deporte.
	 */
	public ModeloDeporte(String nombre) {
		this.nombreDeporte = nombre;
	}

	/**
	 * Obtiene el nombre del deporte.
	 *
	 * @return Una cadena que representa el nombre del deporte.
	 */
	public String getNombreDeporte() {
		return nombreDeporte;
	}

	public void setNombreDeporte(String nombreDeporte) {
		this.nombreDeporte = nombreDeporte;
	}

	public int getIdDeporte() {
		return idDeporte;
	}

	public void setIdDeporte(int idDeporte) {
		this.idDeporte = idDeporte;
	}

	/**
	 * Devuelve una representación en forma de cadena del deporte.
	 *
	 * @return Una cadena que representa el nombre del deporte.
	 */
	@Override
	public String toString() {
		return this.nombreDeporte;
	}

	/**
	 * Calcula el código hash para el deporte, basado en el nombre del deporte.
	 *
	 * @return El código hash de la instancia de ModeloDeporte.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(nombreDeporte);
	}

	/**
	 * Compara esta instancia de ModeloDeporte con otro objeto para determinar si son iguales,
	 * basándose en el nombre del deporte.
	 *
	 * @param obj El objeto a comparar con la instancia actual.
	 * @return true si los objetos son iguales; false en caso contrario.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		ModeloDeporte other = (ModeloDeporte) obj;
		return Objects.equals(nombreDeporte, other.nombreDeporte);
	}
}
