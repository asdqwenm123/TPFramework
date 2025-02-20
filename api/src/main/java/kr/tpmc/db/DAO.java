package kr.tpmc.db;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import java.util.List;

public class DAO<T, I> {
    public void save(T entity) {
        Session session = HibernateLoader.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(entity);
        tx.commit();
        session.close();
    }

    public T getById(Class<T> clazz, I id) {
        Session session = HibernateLoader.getSessionFactory().openSession();
        T entity = session.get(clazz, id);
        session.close();
        return entity;
    }

    public List<T> getAll(Class<T> clazz) {
        Session session = HibernateLoader.getSessionFactory().openSession();

        Query<T> query = session.createQuery("from " + clazz.getName(), clazz);
        List<T> result = query.getResultList();
        session.close();

        return result;
    }

    public void update(T entity) {
        Session session = HibernateLoader.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.merge(entity);
        tx.commit();
        session.close();
    }

    public void delete(Class<T> clazz, I id) {
        Session session = HibernateLoader.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        T entity = session.get(clazz, id);
        if (entity != null) {
            session.remove(entity);
            tx.commit();
        }

        session.close();

    }

    public boolean existsById(Class<T> clazz, I id) {
        Session session = HibernateLoader.getSessionFactory().openSession();
        T entity = session.get(clazz, id);
        boolean exists = (entity != null);
        session.close();

        return exists;
    }

    public long count(Class<T> clazz) {
        Session session = HibernateLoader.getSessionFactory().openSession();
        Query<Long> query = session.createQuery("select count(*) from " + clazz.getName(), Long.class);
        long count = query.uniqueResult();
        session.close();

        return count;
    }

    public void deleteAll(Class<T> clazz) {
        Session session = HibernateLoader.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        MutationQuery query = session.createMutationQuery("delete from " + clazz.getName());
        query.executeUpdate();
        tx.commit();

        session.close();

    }

    public List<T> getByIds(Class<T> clazz, List<I> ids) {
        Session session = HibernateLoader.getSessionFactory().openSession();
        Query<T> query = session.createQuery("from " + clazz.getName() + " where id in :ids", clazz);
        query.setParameter("ids", ids);
        List<T> result = query.getResultList();

        session.close();

        return result;
    }

    public void saveAll(List<T> entities) {
        Session session = HibernateLoader.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        for (T entity : entities) {
            session.persist(entity);
        }
        tx.commit();
        session.close();
    }
}
