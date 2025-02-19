package kr.tpmc.db;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import java.util.List;

public class DAO<T> {
    public void save(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(entity);
        tx.commit();
        session.close();
    }

    public T getById(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        T entity = (T) session.get(Object.class, id);
        session.close();
        return entity;
    }

    public List<T> getAll(Class<T> clazz) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<T> query = session.createQuery("from " + clazz.getName(), clazz);
        List<T> result = query.getResultList();
        session.close();

        return result;
    }

    public void update(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.merge(entity);
        tx.commit();
        session.close();
    }

    public void delete(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        T entity = (T) session.get(Object.class, id);
        if (entity != null) {
            session.remove(entity);
            tx.commit();
        }

        session.close();

    }

    public boolean existsById(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        T entity = (T) session.get(Object.class, id);
        boolean exists = (entity != null);
        session.close();

        return exists;
    }

    public long count(Class<T> clazz) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Long> query = session.createQuery("select count(*) from " + clazz.getName(), Long.class);
        long count = query.uniqueResult();
        session.close();

        return count;
    }

    public void deleteAll(Class<T> clazz) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        MutationQuery query = session.createMutationQuery("delete from " + clazz.getName());
        query.executeUpdate();
        tx.commit();

        session.close();

    }

    public List<T> getByIds(Class<T> clazz, List<Long> ids) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<T> query = session.createQuery("from " + clazz.getName() + " where id in :ids", clazz);
        query.setParameter("ids", ids);
        List<T> result = query.getResultList();

        session.close();

        return result;
    }

    public void saveAll(List<T> entities) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        for (T entity : entities) {
            session.persist(entity);
        }
        tx.commit();
        session.close();
    }
}
