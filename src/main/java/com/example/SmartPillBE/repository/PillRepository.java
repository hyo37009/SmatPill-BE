package com.example.SmartPillBE.repository;


import com.example.SmartPillBE.domain.Pill;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PillRepository {
    private final EntityManager em;

//    public void save(Pill pill) {
//        em.persist(pill);
//    }

    public Pill findByNumber(String pillNumber) {
        return em.find(Pill.class, pillNumber);
    }

    public List<Pill> findByName(String name) {
        return em.createQuery("select p from Pill p where p.pillName like concat('%', :name, '%') ", Pill.class)
                .setParameter("name", name)
                .getResultList();
    }
}
