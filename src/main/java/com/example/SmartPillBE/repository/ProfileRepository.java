package com.example.SmartPillBE.repository;

import com.example.SmartPillBE.domain.Profile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfileRepository {
    @PersistenceContext
    EntityManager em;

    public void save(Profile profile){
        em.persist(profile);
    }

    public Profile findProfileByName(String name){
        return em.createQuery("select p from Profile p where p.name = :name", Profile.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public Profile findOne(int id){
        return em.find(Profile.class, id);
    }

    public List<Profile> findAll() {
        return em.createQuery("select p from Profile p", Profile.class)
                .getResultList();
    }

    public int countAll() {
        return em.createQuery("select p from Profile p", Profile.class)
                .getResultList()
                .size();
    }

    public boolean isEmpty(){
        return findAll().isEmpty();
    }

    public void delete(Profile profile){
        em.remove(profile);
    }

}
