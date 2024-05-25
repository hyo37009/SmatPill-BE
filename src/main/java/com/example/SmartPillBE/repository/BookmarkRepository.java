package com.example.SmartPillBE.repository;

import com.example.SmartPillBE.domain.Bookmark;
import com.example.SmartPillBE.domain.MedInfo;
import com.example.SmartPillBE.domain.Pill;
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

    public Bookmark findOne(Long id){
        return em.find(Bookmark.class, id);
    }

    public List<Bookmark> findByProfile(Profile profile){
        return em.createQuery("select b from Bookmark b where b.profile = :profile", Bookmark.class)
                .setParameter("profile", profile)
                .getResultList();
    }

    public Bookmark findByProfileAndPillNumber(Profile profile, Pill pill){
        return em.createQuery("select b from Bookmark b where b.profile = :profile and b.pill = :pill", Bookmark.class)
                .setParameter("pill", pill)
                .setParameter("profile", profile)
                .getSingleResult();
    }

    public void delete(Bookmark bookmark){
        em.remove(bookmark);
    }
}
