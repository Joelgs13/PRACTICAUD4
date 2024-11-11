package joel.adat.dao;

import joel.adat.model.ModeloOlimpiada;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import joel.adat.bbdd.HibernateUtil;

import java.util.ArrayList;

public class DaoOlimpiada {

    // Insert a new Olimpiada
    public static void aniadirOlimpiada(String nombre, int anio, String temporada, String ciudad) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            ModeloOlimpiada olimpiada = new ModeloOlimpiada();
            olimpiada.setNombreOlimpiada(nombre);
            olimpiada.setAnio(anio);
            olimpiada.setTemporada(temporada);
            olimpiada.setCiudad(ciudad);
            session.save(olimpiada);
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

    // Get a list of Olimpiadas by season
    public static ArrayList<ModeloOlimpiada> listaOlimpiadasPorTemporada(int temp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ArrayList<ModeloOlimpiada> lst = new ArrayList<>();
        String temporada = (temp == 2) ? "Summer" : "Winter";

        try {
            String hql = "FROM ModeloOlimpiada WHERE temporada = :temporada";
            Query<ModeloOlimpiada> query = session.createQuery(hql, ModeloOlimpiada.class);
            query.setParameter("temporada", temporada);
            lst = (ArrayList<ModeloOlimpiada>) query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return lst;
    }

    // Get the ID of an Olimpiada by its details
    public static String conseguirIdOlimpiada(String nombre, int anio, String temporada, String ciudad) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String id = null;

        try {
            String hql = "SELECT idOlimpiada FROM ModeloOlimpiada WHERE nombre = :nombre AND anio = :anio AND temporada = :temporada AND ciudad = :ciudad";
            Query<Integer> query = session.createQuery(hql, Integer.class);
            query.setParameter("nombre", nombre);
            query.setParameter("anio", anio);
            query.setParameter("temporada", temporada);
            query.setParameter("ciudad", ciudad);
            id = String.valueOf(query.uniqueResult());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return id;
    }

    // Get a single Olimpiada by ID
    public static ModeloOlimpiada createOlimpiadaModel(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ModeloOlimpiada olimpiada = null;

        try {
            olimpiada = session.get(ModeloOlimpiada.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return olimpiada;
    }

    // Get a list of Olimpiadas based on season
    public static ArrayList<ModeloOlimpiada> listOlimpiadasByTemp(int temp) {
        return listaOlimpiadasPorTemporada(temp);
    }
}
