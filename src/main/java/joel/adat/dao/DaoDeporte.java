package joel.adat.dao;

import joel.adat.model.ModeloDeporte;
import joel.adat.bbdd.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona las operaciones relacionadas con la tabla Deporte en la base de datos usando Hibernate.
 * Proporciona métodos para insertar, consultar y obtener información sobre los deportes.
 */
public class DaoDeporte {

    /**
     * Obtiene una lista de deportes asociados a una olimpiada específica.
     *
     * @param idOlimpiada El ID de la olimpiada.
     * @return Una lista de objetos `ModeloDeporte` asociados a la olimpiada.
     */
    public static List<ModeloDeporte> listaDeportesPorOlimpiada(int idOlimpiada) {
        List<ModeloDeporte> lst = new ArrayList<>();
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // HQL para obtener los deportes asociados a una olimpiada
            String hql = "SELECT DISTINCT e.deporte FROM ModeloEvento e WHERE e.olimpiada.id = :idOlimpiada";
            Query<ModeloDeporte> query = session.createQuery(hql, ModeloDeporte.class);
            query.setParameter("idOlimpiada", idOlimpiada);
            lst = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return lst;
    }

    /**
     * Obtiene el ID de un deporte a partir de su nombre. Si el deporte no existe, lo inserta en la base de datos.
     *
     * @param nombreDeporte El nombre del deporte.
     * @return El ID del deporte.
     */
    public static int getDeporteId(String nombreDeporte) {
        Integer deporteId = null;
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Intentar obtener el ID del deporte por nombre
            String hql = "SELECT d.id FROM ModeloDeporte d WHERE d.nombre = :nombreDeporte";
            Query<Integer> query = session.createQuery(hql, Integer.class);
            query.setParameter("nombreDeporte", nombreDeporte);
            deporteId = query.uniqueResult();

            // Si no existe, se inserta el nuevo deporte
            if (deporteId == null) {
                ModeloDeporte deporte = new ModeloDeporte(nombreDeporte);
                deporteId = (Integer) session.save(deporte);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return (deporteId != null) ? deporteId : -1;
    }

    /**
     * Inserta un nuevo deporte en la base de datos.
     *
     * @param nombreDeporte El nombre del deporte.
     */
    public static void aniadirDeporte(String nombreDeporte) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Crear el nuevo deporte y guardarlo
            ModeloDeporte deporte = new ModeloDeporte(nombreDeporte);
            session.save(deporte);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el ID de un deporte a partir de su nombre.
     *
     * @param nombre El nombre del deporte.
     * @return El ID del deporte, o null si no se encuentra.
     */
    public static Integer conseguirIdDeporte(String nombre) {
        Integer deporteId = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Consulta para obtener el ID del deporte por su nombre
            String hql = "SELECT d.id FROM ModeloDeporte d WHERE d.nombre = :nombre";
            Query<Integer> query = session.createQuery(hql, Integer.class);
            query.setParameter("nombre", nombre);
            deporteId = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deporteId;
    }

    /**
     * Crea un objeto `ModeloDeporte` a partir del ID de un deporte.
     *
     * @param id El ID del deporte.
     * @return Un objeto `ModeloDeporte` correspondiente al deporte con el ID proporcionado,
     *         o null si no se encuentra en la base de datos.
     */
    public static ModeloDeporte createDeporteModel(int id) {
        ModeloDeporte deporte = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Obtener el deporte desde la base de datos por su ID
            deporte = session.get(ModeloDeporte.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deporte;
    }
}
