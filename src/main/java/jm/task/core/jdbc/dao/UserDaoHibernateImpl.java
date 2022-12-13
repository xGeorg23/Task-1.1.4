package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final SessionFactory SESSION_FACTORY = Util.getSessionFactory();
    private Transaction transaction;

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users" +
            "(id BIGSERIAL PRIMARY KEY, name TEXT, lastName TEXT," +
            "age INTEGER)";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS users";
    private static final String GET_ALL_USERS = "from User users";
    private static final String CLEAN_TABLE = "TRUNCATE TABLE users";

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = SESSION_FACTORY.openSession()) {

            transaction = session.beginTransaction();
            session.createSQLQuery(CREATE_TABLE).executeUpdate();
            transaction.commit();

            System.out.println("Таблица создана!");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = SESSION_FACTORY.openSession()) {

            transaction = session.beginTransaction();
            session.createSQLQuery(DROP_TABLE).executeUpdate();
            transaction.commit();

            System.out.println("Таблица удалена!");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = SESSION_FACTORY.openSession()) {
            transaction = session.beginTransaction();

            User user = new User(name, lastName, age);

            session.save(user);
            transaction.commit();

            System.out.println("User с именем- " + name + " добавлен в базу данных");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        try (Session session = SESSION_FACTORY.openSession()) {
            transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {

        try (Session session = SESSION_FACTORY.openSession()) {
            List<User> users = session.createQuery(GET_ALL_USERS, User.class).getResultList();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public void cleanUsersTable() {

        try (Session session = SESSION_FACTORY.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(CLEAN_TABLE).executeUpdate();
            transaction.commit();

            System.out.println("Таблица очищена!");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}