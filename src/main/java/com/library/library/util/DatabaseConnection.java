package com.library.library.util;

import com.library.library.service.DatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

@Slf4j
@Deprecated(since="02/26/2022")
public abstract class DatabaseConnection {

  protected static EntityManagerFactory entityManagerFactory;
  protected static EntityManager entityManager;

  @Autowired
  DatabaseService databaseService;

  public void connect() {
    if (entityManagerFactory == null || !entityManagerFactory.isOpen()) {
      String database = databaseService.getDatabase();
      Properties properties = databaseService.getProperties();
      entityManagerFactory = Persistence.createEntityManagerFactory(database, properties);
      log.info("Opened entity manager factory");
    }

    if (entityManager == null || !entityManager.isOpen()) {
      entityManager = entityManagerFactory.createEntityManager();
      log.info("Opened entity manager " + entityManagerFactory.isOpen() + " " + entityManager.isOpen());
    }
  }

  public void disconnect() {
    if (entityManager.isOpen()) {
      entityManager.close();
      log.info("Closed entity manager factory");
    }

    if (entityManagerFactory.isOpen()) {
      entityManagerFactory.close();
      log.info("Closed entity manager");
    }
  }

  public void begin() {
    this.connect();
    entityManager.getTransaction().begin();
  }

  public void commit() {
    entityManager.getTransaction().commit();
    this.disconnect();
  }

  public void persist(Object object) {
    entityManager.persist(object);
  }

}
