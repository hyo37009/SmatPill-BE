package com.example.SmartPillBE.repository;

import com.example.SmartPillBE.domain.Profile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileRepository {
    @PersistenceContext
    EntityManager em;

    public void save(Profile profile){
        em.persist(profile);
    }

    public Profile findProfileByName(String name){
        return em.createQuery("select p from Profile p where p.name :name", Profile.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public Profile findOne(int id){
        return em.find(Profile.class, id);
    }
}
