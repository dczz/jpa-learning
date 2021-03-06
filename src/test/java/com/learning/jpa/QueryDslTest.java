package com.learning.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

class QueryDslTest {

    private final static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    private final static EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Test
    void should_query_custom_relation_info() {
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        saveOrder();
        transaction.commit();

        final JPAQueryFactory queryFactory = new JPAQueryFactory(() -> entityManager);
        QOrder qOrder = QOrder.order;
        final List<Order> fetch = queryFactory.selectFrom(qOrder).where(qOrder.id.isNotNull()).fetch();
        Assertions.assertFalse(fetch.isEmpty());
        final Order order = fetch.get(0);
        Assertions.assertEquals("order1", order.getName());
    }


    private void saveOrder() {
        final Address address = new Address("beijing", "chaoyangbeilu");
        Item item = new Item();
        item.setName("item1");
        final Order order = Order.create("order1", address, item);
        entityManager.persist(order);
    }
}
