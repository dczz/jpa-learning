package com.learning.jpa;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

class BasicTet {

    private final static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    private final static EntityManager entityManager = entityManagerFactory.createEntityManager();

    @AfterAll
    static void close() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void should_persistence() {
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        saveOrder();
        transaction.commit();
    }

    @Test
    void order_edit_item() {
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        final Order order = entityManager.find(Order.class, 1L);
        order.removeItem(1L);
        transaction.commit();
    }

    @Test
    void order_edit_basic_info_to_aud() {
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        this.saveOrder();
        transaction.commit();
        transaction.begin();
        final Order order = entityManager.find(Order.class, 1L);
        order.setName("edit after");
        transaction.commit();
    }

    private void saveOrder() {
        final Address address = new Address("beijing", "chaoyangbeilu");
        Item item = new Item();
        item.setName("item1");
        final Order order = Order.create("order1", address, item);
        entityManager.persist(order);
    }
}
