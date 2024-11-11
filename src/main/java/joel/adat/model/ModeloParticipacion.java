package joel.adat.model;

import jakarta.persistence.*;

/**
 * Clase que representa la participación de un deportista en un evento específico,
 * incluyendo su equipo, edad y tipo de medalla obtenida.
 */
@Entity
@Table(name = "Participacion")
public class ModeloParticipacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_participacion")
	private int idParticipacion;

	@ManyToOne
	@JoinColumn(name = "id_deportista", nullable = false)
	private ModeloDeportista deportista;

	@ManyToOne
	@JoinColumn(name = "id_evento", nullable = false)
	private ModeloEvento evento;

	@ManyToOne
	@JoinColumn(name = "id_equipo", nullable = false)
	private ModeloEquipo equipo;

	@Column(name = "edad", nullable = false)
	private int edad;

	@Column(name = "medalla", nullable = false, length = 10)
	private String medalla;

	// Constructor vacío requerido por Hibernate
	public ModeloParticipacion() {}

	/**
	 * Constructor que inicializa la participación de un deportista en un evento.
	 *
	 * @param deportista El modelo del deportista que participa.
	 * @param evento El modelo del evento en el que participa el deportista.
	 * @param equipo El modelo del equipo al que pertenece el deportista.
	 * @param edad La edad del deportista en el momento de la participación.
	 * @param medalla La medalla obtenida por el deportista (puede ser "NA" si no se obtuvo medalla).
	 */
	public ModeloParticipacion(ModeloDeportista deportista, ModeloEvento evento, ModeloEquipo equipo, int edad, String medalla) {
		this.deportista = deportista;
		this.evento = evento;
		this.equipo = equipo;
		this.edad = edad;
		this.medalla = medalla;
	}

	// Getters y setters
	public int getIdParticipacion() {
		return idParticipacion;
	}

	public void setIdParticipacion(int idParticipacion) {
		this.idParticipacion = idParticipacion;
	}

	public ModeloDeportista getDeportista() {
		return deportista;
	}

	public void setDeportista(ModeloDeportista deportista) {
		this.deportista = deportista;
	}

	public ModeloEvento getEvento() {
		return evento;
	}

	public void setEvento(ModeloEvento evento) {
		this.evento = evento;
	}

	public ModeloEquipo getEquipo() {
		return equipo;
	}

	public void setEquipo(ModeloEquipo equipo) {
		this.equipo = equipo;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getMedalla() {
		return medalla;
	}

	public void setMedalla(String medalla) {
		this.medalla = medalla;
	}

	@Override
	public String toString() {
		return this.deportista.getNombreDeportista() + "," + this.deportista.getAltura() + "," + this.deportista.getPeso() +
				"," + this.edad + "," + this.equipo.getNombreEquipo() + "," + this.medalla;
	}
}
