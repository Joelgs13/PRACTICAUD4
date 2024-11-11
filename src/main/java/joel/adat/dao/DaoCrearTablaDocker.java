package joel.adat.dao;

import joel.adat.bbdd.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import joel.adat.model.*;

import java.io.*;
import java.util.List;

public class DaoCrearTablaDocker {

	/**
	 * Metodo para crear la BBDD usando Hibernate
	 * @param pathString
	 */
	public static void crearLaBBDD(String pathString) {
		// Crear la sesión de Hibernate
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			// Comienza la transacción
			transaction = session.beginTransaction();

			// Verificar si el archivo CSV existe y procesarlo
			File CSV = new File(pathString);
			if (CSV.isFile() && pathString.endsWith(".csv")) {
				try (BufferedReader br = new BufferedReader(new FileReader(CSV))) {
					String linea = br.readLine();
					if (linea != null && linea.equals("ID,Name,Sex,Age,Height,Weight,Team,NOC,Games,Year,Season,City,Sport,Event,Medal")) {
						linea = br.readLine();
						while (linea != null) {
							String[] leido = linea.split(",");

							// Verifica si el deportista ya existe y, si no, lo crea
							String deportistaId = DaoDeportista.conseguirIdDeportista(leido[1], leido[2].charAt(0), Float.parseFloat(leido[5]), Integer.parseInt(leido[4]));
							if (deportistaId == null) {
								// Usamos el constructor con todos los atributos
								ModeloDeportista deportista = new ModeloDeportista(leido[1], leido[2].charAt(0), Integer.parseInt(leido[5]), Integer.parseInt(leido[4]));
								session.save(deportista);
							}

							// Verifica si el deporte ya existe y, si no, lo crea
							String deporteId = DaoDeporte.conseguirIdDeporte(leido[12]);
							if (deporteId == null) {
								ModeloDeporte deporte = new ModeloDeporte(leido[12]);
								session.save(deporte);
							}

							// Verifica si el equipo ya existe y, si no, lo crea
							String equipoId = DaoEquipo.conseguirIdEquipo(leido[6], leido[7]);
							if (equipoId == null) {
								ModeloEquipo equipo = new ModeloEquipo(leido[6], leido[7]);
								session.save(equipo);
							}

							// Verifica si la olimpiada ya existe y, si no, la crea
							String olimpiadaId = DaoOlimpiada.conseguirIdOlimpiada(leido[8], Integer.parseInt(leido[9]), leido[10], leido[11]);
							if (olimpiadaId == null) {
								ModeloOlimpiada olimpiada = new ModeloOlimpiada(leido[8], Integer.parseInt(leido[9]), leido[10], leido[11]);
								session.save(olimpiada);
							}

							// Verifica si el evento ya existe y, si no, lo crea
							String eventoId = DaoEvento.conseguirIdEvento(leido[13], Integer.parseInt(olimpiadaId), Integer.parseInt(deporteId));
							if (eventoId == null) {
								ModeloEvento evento = new ModeloEvento(leido[13], Integer.parseInt(olimpiadaId), Integer.parseInt(deporteId));
								session.save(evento);
							}

							// Verifica si la participación ya existe y, si no, la crea
							String participacionId = DaoParticipacion.existeIdParticipacion(Integer.parseInt(deportistaId), Integer.parseInt(eventoId)) ? "existe" : "no existe";
							if (participacionId.equals("no existe")) {
								// Crear una participación usando los constructores completos
								ModeloParticipacion participacion = new ModeloParticipacion(
										new ModeloDeportista(Integer.parseInt(deportistaId)), // Suponemos que deportistaId es un Integer, si no, ajusta
										new ModeloEvento(Integer.parseInt(eventoId)), // Lo mismo para el evento
										new ModeloEquipo(Integer.parseInt(equipoId)), // Lo mismo para el equipo
										Integer.parseInt(leido[3]), // Edad
										leido[14] // Medalla
								);
								session.save(participacion);
							}

							linea = br.readLine();
						}
						System.out.println("La carga de la información se ha realizado correctamente");
					} else {
						System.out.println("El formato del CSV no es correcto.");
					}
				} catch (IOException e) {
					System.out.println("Error al procesar el archivo CSV.");
					e.printStackTrace();
				}
			} else {
				System.out.println("El archivo CSV no existe o no es válido.");
			}

			// Commit de la transacción
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}
