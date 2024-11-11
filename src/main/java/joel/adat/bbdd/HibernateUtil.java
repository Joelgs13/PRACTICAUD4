package joel.adat.bbdd;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * La clase {@code HibernateUtil} gestiona la configuración y conexión a la base de datos
 * a través de Hibernate. Proporciona métodos para obtener el `SessionFactory` y abrir sesiones.
 *
 * <p>Esta clase utiliza el archivo `hibernate.cfg.xml` para cargar los parámetros de conexión y
 * configuración de Hibernate.</p>
 *
 * <p>Incluye métodos para abrir y cerrar sesiones y gestionar transacciones de forma segura.</p>
 *
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    /**
     * Configura y construye el `SessionFactory` de Hibernate.
     * Este bloque estático garantiza que la configuración de Hibernate se cargue una vez
     * y que el `SessionFactory` esté listo para ser utilizado.
     */
    static {
        try {
            // Cargar la configuración de Hibernate desde `hibernate.cfg.xml`
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            // Construir el `SessionFactory` con un registro de servicios
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Error en la creación de `SessionFactory`: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Obtiene el `SessionFactory` activo.
     *
     * @return El `SessionFactory` configurado.
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Cierra el `SessionFactory` y libera todos los recursos asociados.
     */
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    /**
     * Abre una nueva sesión de Hibernate.
     *
     * @return Una nueva sesión de Hibernate.
     */
    public static Session openSession() {
        return sessionFactory.openSession();
    }
}
