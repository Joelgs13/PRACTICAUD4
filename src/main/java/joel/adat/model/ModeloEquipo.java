package joel.adat.model;

import jakarta.persistence.*;

/**
 * Clase que representa un equipo en el contexto de eventos deportivos,
 * con su nombre oficial e iniciales.
 */
@Entity
@Table(name = "Equipo")
public class ModeloEquipo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_equipo")
	private int idEquipo;

	@Column(name = "nombre", nullable = false, length = 50)
	private String nombreEquipo;

	@Column(name = "iniciales", nullable = false, length = 3)
	private String iniciales;

	// Constructor vacío necesario para Hibernate
	public ModeloEquipo() {}

	/**
	 * Constructor que inicializa el equipo con su nombre e iniciales.
	 *
	 * @param nombreEquipo El nombre oficial del equipo.
	 * @param iniciales Las iniciales que representan al equipo.
	 */
	public ModeloEquipo(String nombreEquipo, String iniciales) {
		this.nombreEquipo = nombreEquipo;
		this.iniciales = iniciales;
	}

	public int getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(int idEquipo) {
		this.idEquipo = idEquipo;
	}

	public String getNombreEquipo() {
		return nombreEquipo;
	}

	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}

	public String getIniciales() {
		return iniciales;
	}

	public void setIniciales(String iniciales) {
		this.iniciales = iniciales;
	}

	@Override
	public String toString() {
		return "Equipo [nombreEquipo=" + nombreEquipo + ", iniciales=" + iniciales + "]";
	}
}
