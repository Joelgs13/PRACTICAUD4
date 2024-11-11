package joel.adat.dao;

import joel.adat.model.ModeloDeporte;
import joel.adat.model.ModeloEvento;
import joel.adat.model.ModeloOlimpiada;
import joel.adat.bbdd.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que maneja las operaciones relacionadas con la tabla de Eventos en la base de datos usando Hibernate.
 * Proporciona métodos para insertar, consultar y obtener eventos relacionados con olimpiadas y deportes.
 */
public class DaoEvento {

    /**
     * Inserta un nuevo evento en la base de datos.
     *
     * @param nombreEvento El nombre del evento.
     * @param idOlimpiada El ID de la olimpiada asociada al evento.
     * @param idDeporte El ID del deporte asociado al evento.
     */
    public static void aniadirEvento(String nombreEvento, int idOlimpiada, int idDeporte) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Crear el nuevo evento
            ModeloDeporte deporte = session.get(ModeloDeporte.class, idDeporte);
            ModeloOlimpiada olimpiada = session.get(ModeloOlimpiada.class, idOlimpiada);

            ModeloEvento evento = new ModeloEvento(nombreEvento, deporte, olimpiada);

            // Guardar el evento en la base de datos
            session.save(evento);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Crea una lista de objetos `ModeloEvento` basados en el deporte y la olimpiada proporcionados.
     *
     * @param idDeporte El ID del deporte.
     * @param idOlimpiada El ID de la olimpiada.
     * @return Una lista de eventos correspondientes al deporte y la olimpiada dados.
     */
    public static List<ModeloEvento> crearListaModelosPorDeporteYOlimpiada(int idDeporte, int idOlimpiada) {
        List<ModeloEvento> lst = new ArrayList<>();
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Crear la consulta HQL para obtener eventos según deporte y olimpiada
            String hql = "FROM ModeloEvento e WHERE e.deporte.id = :idDeporte AND e.olimpiada.id = :idOlimpiada";
            Query<ModeloEvento> query = session.createQuery(hql, ModeloEvento.class);
            query.setParameter("idDeporte", idDeporte);
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
     * Obtiene una lista de eventos a partir de una lista de IDs de eventos.
     *
     * @param lstId Una lista de IDs de eventos.
     * @return Una lista de objetos `ModeloEvento` correspondientes a los IDs dados.
     */
    public static List<ModeloEvento> listaModelosPorId(List<String> lstId) {
        List<ModeloEvento> lst = new ArrayList<>();
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Itera por la lista de IDs y obtiene los eventos
            for (String id : lstId) {
                ModeloEvento evento = session.get(ModeloEvento.class, Integer.parseInt(id));
                if (evento != null) {
                    lst.add(evento);
                }
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return lst;
    }

    /**
     * Crea un objeto `ModeloEvento` a partir de un ID de evento.
     *
     * @param id El ID del evento.
     * @return Un objeto `ModeloEvento` correspondiente al evento con el ID proporcionado.
     */
    public static ModeloEvento createById(int id) {
        Transaction transaction = null;
        ModeloEvento evento = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            evento = session.get(ModeloEvento.class, id);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return evento;
    }

    /**
     * Obtiene el ID de un evento a partir de su nombre, ID de olimpiada e ID de deporte.
     *
     * @param nombreEvento El nombre del evento.
     * @param idOlimpiada El ID de la olimpiada asociada.
     * @param idDeporte El ID del deporte asociado.
     * @return El ID del evento si existe, o null si no se encuentra.
     */
    public static String conseguirIdEvento(String nombreEvento, int idOlimpiada, int idDeporte) {
        Transaction transaction = null;
        String idEvento = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "SELECT e.id FROM ModeloEvento e WHERE e.nombre = :nombreEvento " +
                    "AND e.olimpiada.id = :idOlimpiada AND e.deporte.id = :idDeporte";
            Query<String> query = session.createQuery(hql, String.class);
            query.setParameter("nombreEvento", nombreEvento);
            query.setParameter("idOlimpiada", idOlimpiada);
            query.setParameter("idDeporte", idDeporte);

            idEvento = query.uniqueResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return idEvento;
    }
}
