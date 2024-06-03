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

    public List<MedInfo> findByProfileAndPeriod(Profile profile, medPeriod period){
//        Profile profile = em.find(Profile.class, profileId);
        return em.createQuery("select m from MedInfo m where m.profile = :profile and m.dosageTime.medPeriod = :period", MedInfo.class)
                .setParameter("profile", profile)
                .setParameter("period", period)
                .getResultList();
    }

    public void delete(MedInfo medInfo){
        em.remove(medInfo);
    }

    public List<MedInfo> findByDate(Profile profile, LocalDate date){
        return em.createQuery("select m from MedInfo  m where m.profile = :profile and m.startDate = :date", MedInfo.class)
                .setParameter("profile", profile)
                .setParameter("date", date)
                .getResultList();
    }

    public List<MedInfo> findByDateAndPeriod(Profile profile, LocalDate date, int howLong){
        return em.createQuery("select m from MedInfo m " +
                        "where m.profile = :profile " +
                        "and m.startDate >= :startDate and m.startDate <= :endDate", MedInfo.class)
                .setParameter("profile", profile)
                .setParameter("startDate", date)
                .setParameter("endDate", date.plusDays(howLong - 1))
                .getResultList();
    }
}
