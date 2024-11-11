package joel.adat.model;

import jakarta.persistence.*;

/**
 * Clase que representa un deportista, con su nombre, sexo, altura y peso.
 */
@Entity
@Table(name = "Deportista")
public class ModeloDeportista {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_deportista")
	private int idDeportista;

	@Column(name = "nombre", nullable = false, length = 150)
	private String nombreDeportista;

	@Column(name = "sexo", nullable = false, columnDefinition = "ENUM('M', 'F')")
	private char sexo;

	@Column(name = "altura")
	private int altura;

	@Column(name = "peso")
	private float peso;

	// Constructor vacío necesario para Hibernate
	public ModeloDeportista() {}

	/**
	 * Constructor que inicializa los atributos de un deportista.
	 *
	 * @param nombreDeportista El nombre completo del deportista.
	 * @param sexo El sexo del deportista ('M' para masculino, 'F' para femenino).
	 * @param altura La altura del deportista en centímetros.
	 * @param peso El peso del deportista en kilogramos.
	 */
	public ModeloDeportista(String nombreDeportista, char sexo, int altura, float peso) {
		this.nombreDeportista = nombreDeportista;
		this.sexo = sexo;
		this.altura = altura;
		this.peso = peso;
	}

	public int getIdDeportista() {
		return idDeportista;
	}

	public void setIdDeportista(int idDeportista) {
		this.idDeportista = idDeportista;
	}

	public String getNombreDeportista() {
		return nombreDeportista;
	}

	public void setNombreDeportista(String nombreDeportista) {
		this.nombreDeportista = nombreDeportista;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	/**
	 * Devuelve una representación en forma de cadena del deportista,
	 * incluyendo su nombre, sexo, altura y peso.
	 *
	 * @return Una cadena que representa al deportista con sus detalles.
	 */
	@Override
	public String toString() {
		return this.nombreDeportista + "," + this.sexo + "," + this.altura + "," + this.peso;
	}
}
