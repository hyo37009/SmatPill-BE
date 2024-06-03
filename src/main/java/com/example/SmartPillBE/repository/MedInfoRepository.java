package com.example.SmartPillBE.repository;

import com.example.SmartPillBE.domain.MedInfo;
import com.example.SmartPillBE.domain.Profile;
import com.example.SmartPillBE.domain.doaseTime.medPeriod;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MedInfoRepository {

    private final EntityManager em;

    public void save(MedInfo medInfo) {
        em.persist(medInfo);
    }

    public MedInfo findOne(Long medInfoId) {
        return em.find(MedInfo.class, medInfoId);
    }

    public List<MedInfo> findByProfileId(int profileId) {
        Profile profile = em.find(Profile.class, profileId);
        return em.createQuery("select m from MedInfo m where m.profile = :profile", MedInfo.class)
                .setParameter("profile", profile)
                .getResultList();
    }

    public void delete(MedInfo medInfo){
        em.remove(medInfo);
    }

    public List<MedInfo> findByDate(Profile profile, LocalDate date){
        return em.createQuery("select m from MedInfo  m where m.profile = :profile and m.date = :date", MedInfo.class)
                .setParameter("profile", profile)
                .setParameter("date", date)
                .getResultList();
    }

    public List<MedInfo> findByDateAndPeriod(Profile profile, LocalDate date, medPeriod period){
        return em.createQuery("select m from MedInfo m " +
                        "where m.profile = :profile " +
                        "and m.date = :startDate " +
                        "and m.dosageTime.medPeriod = :period", MedInfo.class)
                .setParameter("profile", profile)
                .setParameter("startDate", date)
                .setParameter("period", period)
                .getResultList();
    }
}
