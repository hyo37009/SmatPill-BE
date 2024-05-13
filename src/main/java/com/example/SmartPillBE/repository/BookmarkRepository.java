package com.example.SmartPillBE.repository;

import com.example.SmartPillBE.domain.Bookmark;
import com.example.SmartPillBE.domain.MedInfo;
import com.example.SmartPillBE.domain.Profile;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookmarkRepository {

    private final EntityManager em;

    public void save(Bookmark bookmark){
        em.persist(bookmark);
    }

    public List<Bookmark> findByProfileId(int profileId){
        Profile profile = em.find(Profile.class, profileId);
        return em.createQuery("select b from Bookmark b where b.profile = :profile", Bookmark.class)
                .setParameter("profile", profile)
                .getResultList();
    }
}
