package server.repository;

import common.domain.User;
import common.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.Optional;

public class UserHibernateRepository implements IUserRepository{
    @Override
    public Optional<User> findOneByUsername(String username) {
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createQuery("from User where username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResultOrNull());
        }
    }

    @Override
    public Optional<User> findOne(Long aLong) {
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createQuery("from User where id = :id", User.class)
                    .setParameter("id", aLong)
                    .getSingleResultOrNull());
        }
    }

    @Override
    public Iterable<User> getAll() {
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).getResultList();
        }
    }

    @Override
    public void save(User entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(entity));
    }

    @Override
    public void delete(Long aLong) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            User user = session.createQuery("from User where id = :id", User.class)
                    .setParameter("id", aLong)
                    .getSingleResultOrNull();
            if (user != null) {
                session.remove(user);
                session.flush();
            }
        });
    }

    @Override
    public void update(User entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (session.find(User.class, entity.getId()) != null) {
                session.merge(entity);
                session.flush();
            }
        });
    }
}
