package com.example.fibonacci.services;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fibonacci.models.KnownNumberModel;
import com.example.fibonacci.repositories.KnownNumberRepository;

@Service
public class KnownNumberService {
  private final KnownNumberRepository knownValueRepository;
  
  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  public KnownNumberService(KnownNumberRepository knownValueRepository) {
    this.knownValueRepository = knownValueRepository;
  }

  // Devuelve valor conocido
  // Si valor no exite devuelve valor mas grande
  public KnownNumberModel getKnownValue(int number) {
    return knownValueRepository
            .findById(number)
            .orElse(getLastRecord());
  }
  
  // Devuelve valor conocido mas grande
  public KnownNumberModel getLastRecord() {
    Session session = null;
    Transaction transaction = null;
    KnownNumberModel result = null;

    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();

      String query =  "FROM KnownNumberModel kvm " +
                      "ORDER BY kvm.value DESC " + 
                      "LIMIT 1";

      result = session
                .createQuery(query, KnownNumberModel.class)
                .uniqueResult();
      
      transaction.commit();
      session.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }

    return result;
  }
  
  // Devuelve los 10 valores mas consultados
  public List<KnownNumberModel> getStatistics() {
    Session session = null;
    Transaction transaction = null;
    List<KnownNumberModel> result = null;
    
    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();

      String query =  "FROM KnownNumberModel kvm " +
                      "ORDER BY kvm.frecuency DESC";
      result = session
                .createQuery(query, KnownNumberModel.class)
                .setMaxResults(10)
                .getResultList();

      transaction.commit();
      session.close();  

    } catch (Exception e) {
      e.printStackTrace();
    }
    
    
    return result;
  }
  
  // Actualiza la frecuencia para un valor dado
  public KnownNumberModel updateFrecuency(int value) {
    Session session = null;
    Transaction transaction = null;
    KnownNumberModel model = null;

    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();
      
      model = session.get(KnownNumberModel.class, value);
      int frecuency = model.getFrecuency() + 1;
      session.evict(model);
      model.setFrecuency(frecuency);
      session.merge(model);
      
      transaction.commit();
      session.close();
    
    } catch (Exception e) {
      e.printStackTrace();
    }
    return model;
  }

  // Guarda valores calculados
  public void saveValues(List<KnownNumberModel> values) {
    knownValueRepository.saveAll(values);
  }

}
