package com.example.SmartPillBE.repository;


import com.example.SmartPillBE.domain.Pill;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class PillRepository {
    private final EntityManager em;

//    public void save(Pill pill) {
//        em.persist(pill);
//    }

    public Pill findOne(String pillNumber) {
        return em.find(Pill.class, pillNumber);
    }
}
