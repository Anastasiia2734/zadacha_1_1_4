package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20), lastname VARCHAR(20), age TINYINT NOT NULL)";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS users";
    private static final String CLEAR_TABLE = "DELETE FROM users";

    public UserDaoHibernateImpl() {

    }

    SessionFactory sessionFactory = Util.getSessionFactory();

    @Override
    public void createUsersTable() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.createNativeQuery(CREATE_TABLE).executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            {
                if (session != null) {
                    session.close();
                }
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(DROP_TABLE).executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        if (name == null || lastName == null) {
            throw new IllegalArgumentException("Name and last name cannot be null");
        }
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User userObj = new User();
            userObj.setName(name);
            userObj.setLastName(lastName);
            userObj.setAge(age);
            session.save(userObj);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User userObj = session.get(User.class, id);
            if (userObj != null) {
                session.delete(userObj);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            users = session.createQuery("FROM User", User.class).list();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return users;
    }


    @Override
    public void cleanUsersTable() {
Session session = null;
try {
    session = sessionFactory.openSession();
    session.beginTransaction();
    session.createNativeQuery(CLEAR_TABLE).executeUpdate();
    session.getTransaction().commit();
} catch (Exception e) {
    e.printStackTrace();
} finally {
    if (session != null && session.isOpen()) {
        session.close();
    }
}
    }
}
