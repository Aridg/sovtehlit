package code.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by Алексей on 21.04.2017.
 */
public class HibernateSessionFactory {
    private static SessionFactory ourSessionFactory;

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void init(){
        try {
            ourSessionFactory = new Configuration().
                    configure("hibernate.cfg.xml").
                    buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void shutdown() {
        ourSessionFactory.close();
    }
}
