package joel.adat.model;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Clase que representa un evento deportivo en el contexto de una olimpiada,
 * incluyendo el nombre del evento, el deporte al que pertenece y la olimpiada
 * en la que se realizó.
 */
@Entity
@Table(name = "Evento")
public class ModeloEvento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_evento")
	private int idEvento;

	@Column(name = "nombre", nullable = false, length = 150)
	private String nombreEvento;

	@ManyToOne
	@JoinColumn(name = "id_deporte", nullable = false)
	private ModeloDeporte deporte;

	@ManyToOne
	@JoinColumn(name = "id_olimpiada", nullable = false)
	private ModeloOlimpiada olimpiada;

	// Constructor vacío requerido por Hibernate
	public ModeloEvento() {}

	/**
	 * Constructor para inicializar un evento con su nombre, deporte y olimpiada asociada.
	 *
	 * @param nombreEvento El nombre del evento deportivo.
	 * @param deporte El deporte al que pertenece el evento.
	 * @param olimpiada La olimpiada en la que se realizó el evento.
	 */
	public ModeloEvento(String nombreEvento, ModeloDeporte deporte, ModeloOlimpiada olimpiada) {
		this.nombreEvento = nombreEvento;
		this.deporte = deporte;
		this.olimpiada = olimpiada;
	}

	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public String getNombreEvento() {
		return nombreEvento;
	}

	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}

	public ModeloDeporte getDeporte() {
		return deporte;
	}

	public void setDeporte(ModeloDeporte deporte) {
		this.deporte = deporte;
	}

	public ModeloOlimpiada getOlimpiada() {
		return olimpiada;
	}

	public void setOlimpiada(ModeloOlimpiada olimpiada) {
		this.olimpiada = olimpiada;
	}

	@Override
	public String toString() {
		return this.nombreEvento + ", " + this.deporte.getNombreDeporte() + ", " + this.olimpiada.getNombreOlimpiada();
	}

	@Override
	public int hashCode() {
		return Objects.hash(deporte, nombreEvento, olimpiada);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		ModeloEvento other = (ModeloEvento) obj;
		return Objects.equals(deporte, other.deporte) && Objects.equals(nombreEvento, other.nombreEvento)
				&& Objects.equals(olimpiada, other.olimpiada);
	}
}
