package joel.adat.dao;

import joel.adat.model.ModeloDeportista;
import joel.adat.bbdd.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona las operaciones relacionadas con la tabla Deportista en la base de datos usando Hibernate.
 * Proporciona métodos para insertar, consultar, y obtener información de los deportistas.
 */
public class DaoDeportista {

    /**
     * Crea un objeto `ModeloDeportista` a partir del ID de un deportista.
     *
     * @param id El ID del deportista.
     * @return Un objeto `ModeloDeportista` correspondiente al deportista con el ID proporcionado,
     *         o null si no se encuentra en la base de datos.
     */
    public static ModeloDeportista createDeportistaModel(String id) {
        ModeloDeportista deportista = null;
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Obtener el deportista desde la base de datos por su ID
            deportista = session.get(ModeloDeportista.class, id);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return deportista;
    }

    /**
     * Encuentra deportistas cuyo nombre coincida parcialmente con la cadena proporcionada.
     *
     * @param cadena La cadena con la que se busca coincidencias en el nombre del deportista.
     * @return Una lista de objetos `ModeloDeportista` que coinciden con el nombre proporcionado.
     */
    public static List<ModeloDeportista> findDeportistaName(String cadena) {
        List<ModeloDeportista> lst = new ArrayList<>();
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // HQL para obtener los deportistas que coinciden parcialmente con el nombre
            String hql = "FROM ModeloDeportista d WHERE d.nombre LIKE :cadena";
            Query<ModeloDeportista> query = session.createQuery(hql, ModeloDeportista.class);
            query.setParameter("cadena", "%" + cadena + "%");

            lst = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return lst;
    }

    /**
     * Inserta un nuevo deportista en la base de datos.
     *
     * @param nombre El nombre del deportista.
     * @param sexo El sexo del deportista (M/F).
     * @param edad La edad del deportista.
     */
    public static void insertDeportista(String nombre, String sexo, int edad) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Crear el nuevo deportista
            ModeloDeportista deportista = new ModeloDeportista(nombre, sexo.charAt(0), 0, 0); // Edad no está en la clase ModeloDeportista
            session.save(deportista);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el ID de un deportista basado en su nombre, sexo, peso y altura.
     *
     * @param nombreDeportista El nombre del deportista.
     * @param sexo El sexo del deportista (M/F).
     * @param peso El peso del deportista.
     * @param altura La altura del deportista.
     * @return El ID del deportista si existe, o null si no se encuentra.
     */
    public static String conseguirIdDeportista(String nombreDeportista, char sexo, float peso, int altura) {
        String deportistaId = null;
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // HQL para obtener el ID del deportista con los datos proporcionados
            String hql = "SELECT d.id FROM ModeloDeportista d WHERE d.nombre = :nombre AND d.sexo = :sexo " +
                    "AND d.peso = :peso AND d.altura = :altura";
            Query<String> query = session.createQuery(hql, String.class);
            query.setParameter("nombre", nombreDeportista);
            query.setParameter("sexo", sexo);
            query.setParameter("peso", peso);
            query.setParameter("altura", altura);

            deportistaId = query.uniqueResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return deportistaId;
    }

    /**
     * Inserta un nuevo deportista en la base de datos con los parámetros proporcionados.
     *
     * @param nombreDeportista El nombre del deportista.
     * @param sexo El sexo del deportista (M/F).
     * @param peso El peso del deportista.
     * @param altura La altura del deportista.
     */
    public static void aniadirDeportista(String nombreDeportista, char sexo, float peso, int altura) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Crear el nuevo deportista
            ModeloDeportista deportista = new ModeloDeportista(nombreDeportista, sexo, peso, altura);
            session.save(deportista);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
