package com.example.SmartPillBE.repository;

import com.example.SmartPillBE.domain.Pill;
import com.example.SmartPillBE.domain.Profile;
import com.example.SmartPillBE.domain.SideEffect;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SideEffectRepository {
    private final EntityManager em;

    public void save(SideEffect sideEffect){
        em.persist(sideEffect);
    }

    public SideEffect findOne(Long id){
        return em.find(SideEffect.class, id);
    }

    public List<SideEffect> findByProfile(Profile profile){
        return em.createQuery("select s from SideEffect s where s.profile = :profile", SideEffect.class)
                .setParameter("profile", profile)
                .getResultList();
    }

    public List<SideEffect> findByPill(Pill pill){
        // TODO
        return em.createQuery("select s from SideEffect s where s.pill = :pill", SideEffect.class)
                .setParameter("pill", pill)
                .getResultList();
    }


}
