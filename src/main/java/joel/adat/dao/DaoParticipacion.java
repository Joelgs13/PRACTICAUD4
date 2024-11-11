package joel.adat.dao;

import joel.adat.bbdd.HibernateUtil;
import joel.adat.model.ModeloDeportista;
import joel.adat.model.ModeloEquipo;
import joel.adat.model.ModeloEvento;
import joel.adat.model.ModeloParticipacion;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class DaoParticipacion {

    /**
     * Verifica si existe una participación en la base de datos con los IDs proporcionados.
     *
     * @param idDeportista El ID del deportista.
     * @param idEvento El ID del evento.
     * @return true si existe una participación, false si no existe.
     */
    public static boolean existeIdParticipacion(int idDeportista, int idEvento) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT 1 FROM ModeloParticipacion p WHERE p.deportista.idDeportista = :idDeportista AND p.evento.idEvento = :idEvento";
            Query query = session.createQuery(hql);
            query.setParameter("idDeportista", idDeportista);
            query.setParameter("idEvento", idEvento);
            return query.uniqueResult() != null;
        }
    }

    /**
     * Elimina una participación de la base de datos.
     *
     * @param idDeportista El ID del deportista.
     * @param idEvento El ID del evento.
     */
    public static void eliminarParticipacion(int idDeportista, int idEvento) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            String hql = "DELETE FROM ModeloParticipacion p WHERE p.deportista.idDeportista = :idDeportista AND p.evento.idEvento = :idEvento";
            Query query = session.createQuery(hql);
            query.setParameter("idDeportista", idDeportista);
            query.setParameter("idEvento", idEvento);
            query.executeUpdate();
            transaction.commit();
        }
    }

    /**
     * Actualiza la medalla de una participación en la base de datos.
     *
     * @param idDeportista El ID del deportista.
     * @param idEvento El ID del evento.
     * @param nuevaMedalla La nueva medalla obtenida.
     */
    public static void editMedal(int idDeportista, int idEvento, String nuevaMedalla) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            String hql = "UPDATE ModeloParticipacion p SET p.medalla = :medalla WHERE p.deportista.idDeportista = :idDeportista AND p.evento.idEvento = :idEvento";
            Query query = session.createQuery(hql);
            query.setParameter("medalla", nuevaMedalla);
            query.setParameter("idDeportista", idDeportista);
            query.setParameter("idEvento", idEvento);
            query.executeUpdate();
            transaction.commit();
        }
    }

    /**
     * Obtiene los IDs de los deportistas que participan en un evento específico.
     *
     * @param idEvento El ID del evento.
     * @return Una lista con los IDs de los deportistas.
     */
    public static List<Integer> darIdDeportista(int idEvento) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT p.deportista.idDeportista FROM ModeloParticipacion p WHERE p.evento.idEvento = :idEvento";
            Query query = session.createQuery(hql);
            query.setParameter("idEvento", idEvento);
            return query.list();
        }
    }

    /**
     * Crea un objeto ModeloParticipacion a partir de los IDs proporcionados.
     *
     * @param idDeportista El ID del deportista.
     * @param idEvento El ID del evento.
     * @return Un objeto ModeloParticipacion que representa la participación, o null si no existe.
     */
    public static ModeloParticipacion crearModeloParticipacion(int idDeportista, int idEvento) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM ModeloParticipacion p WHERE p.deportista.idDeportista = :idDeportista AND p.evento.idEvento = :idEvento";
            Query query = session.createQuery(hql);
            query.setParameter("idDeportista", idDeportista);
            query.setParameter("idEvento", idEvento);
            return (ModeloParticipacion) query.uniqueResult();
        }
    }

    /**
     * Obtiene los IDs de los eventos en los que un deportista ha participado.
     *
     * @param idDeportista El ID del deportista.
     * @return Una lista con los IDs de los eventos en los que el deportista ha participado.
     */
    public static List<Integer> getIdEvento(int idDeportista) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT p.evento.idEvento FROM ModeloParticipacion p WHERE p.deportista.idDeportista = :idDeportista";
            Query query = session.createQuery(hql);
            query.setParameter("idDeportista", idDeportista);
            return query.list();
        }
    }

    /**
     * Inserta una nueva participación en la base de datos.
     *
     * @param idDeportista El ID del deportista.
     * @param idEvento El ID del evento.
     * @param idEquipo El ID del equipo.
     * @param edad La edad del deportista en el evento.
     * @param medalla La medalla obtenida en el evento.
     */
    public static void aniadirParticipacion(int idDeportista, int idEvento, int idEquipo, int edad, String medalla) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            ModeloDeportista deportista = session.get(ModeloDeportista.class, idDeportista);
            ModeloEvento evento = session.get(ModeloEvento.class, idEvento);
            ModeloEquipo equipo = session.get(ModeloEquipo.class, idEquipo);

            ModeloParticipacion participacion = new ModeloParticipacion(deportista, evento, equipo, edad, medalla);
            session.save(participacion);
            transaction.commit();
        }
    }
}
