package com.example.SmartPillBE.repository;

import com.example.SmartPillBE.domain.MedicalHistory;
import com.example.SmartPillBE.domain.Profile;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MedicalHistoryRepository {

    private final EntityManager em;

    public void save(MedicalHistory medicalHistory){
        em.persist(medicalHistory);
    }

    public MedicalHistory findById(Long id){
        return em.find(MedicalHistory.class, id);
    }

    public List<MedicalHistory> getByProfile(Profile profile){
        return em.createQuery("select m from MedicalHistory m where m.profile = :profile", MedicalHistory.class)
                .setParameter("profile", profile)
                .getResultList();
    }

    public void delete(MedicalHistory medicalHistory){
        em.remove(medicalHistory);
    }
}
