package joel.adat.dao;

import joel.adat.bbdd.HibernateUtil;
import joel.adat.model.ModeloEquipo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Clase que maneja las operaciones relacionadas con la tabla de Equipos en la base de datos usando Hibernate.
 * Proporciona métodos para insertar, consultar y obtener información sobre equipos.
 */
public class DaoEquipo {

    /**
     * Inserta un nuevo equipo en la base de datos.
     *
     * @param nombre El nombre del equipo.
     * @param iniciales Las iniciales del equipo.
     */
    public static void aniadirEquipo(String nombre, String iniciales) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Crear un nuevo modelo de equipo
            ModeloEquipo equipo = new ModeloEquipo(nombre, iniciales);

            // Guardar el equipo en la base de datos
            session.save(equipo);

            // Confirmar la transacción
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el ID de un equipo a partir de su nombre y sus iniciales.
     *
     * @param nombre El nombre del equipo.
     * @param iniciales Las iniciales del equipo.
     * @return El ID del equipo si existe, o null si no se encuentra.
     */
    public static Integer conseguirIdEquipo(String nombre, String iniciales) {
        Integer idEquipo = null;
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // HQL para obtener el ID del equipo por nombre e iniciales
            String hql = "SELECT e.idEquipo FROM ModeloEquipo e WHERE e.nombreEquipo = :nombre AND e.iniciales = :iniciales";
            Query<Integer> query = session.createQuery(hql, Integer.class);
            query.setParameter("nombre", nombre);
            query.setParameter("iniciales", iniciales);

            // Obtener el resultado
            idEquipo = query.uniqueResult();

            // Confirmar la transacción
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return idEquipo;
    }

    /**
     * Obtiene un equipo completo a partir de su ID.
     *
     * @param id El ID del equipo.
     * @return El equipo con el ID proporcionado, o null si no se encuentra.
     */
    public static ModeloEquipo obtenerEquipoPorId(int id) {
        ModeloEquipo equipo = null;
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Buscar el equipo por ID utilizando Hibernate
            equipo = session.get(ModeloEquipo.class, id);

            // Confirmar la transacción
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return equipo;
    }

    /**
     * Obtiene todos los equipos de la base de datos.
     *
     * @return Una lista de todos los equipos en la base de datos.
     */
    public static List<ModeloEquipo> obtenerTodosLosEquipos() {
        List<ModeloEquipo> equipos = null;
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Consulta HQL para obtener todos los equipos
            String hql = "FROM ModeloEquipo";
            Query<ModeloEquipo> query = session.createQuery(hql, ModeloEquipo.class);

            equipos = query.list();

            // Confirmar la transacción
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return equipos;
    }
}
