package server.repository;

import common.domain.Item;
import common.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.Optional;

public class ItemHibernateRepository implements IItemRepository{

    @Override
    public Optional<Item> findOne(Long aLong) {
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.find(Item.class, aLong));
        }
    }

    @Override
    public Iterable<Item> getAll() {
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Item", Item.class).getResultList();
        }
    }

    @Override
    public void save(Item entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(entity));
    }

    @Override
    public void delete(Long aLong) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Item item = session.createQuery("from Item where id = :id", Item.class)
                    .setParameter("id", aLong)
                    .getSingleResultOrNull();
            if (item != null) {
                session.remove(item);
                session.flush();
            }
        });
    }

    @Override
    public void update(Item entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (session.find(Item.class, entity.getId()) != null) {
                session.merge(entity);
                session.flush();
            }
        });
    }
}
