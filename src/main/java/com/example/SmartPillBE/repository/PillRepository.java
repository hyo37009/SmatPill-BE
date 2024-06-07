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

    public List<Pill> findByPrint(String print){
        return em.createQuery("select p from Pill p where p.printBack = :print or p.printFront = :print", Pill.class)
                .setParameter("print", print)
                .getResultList();
    }

    public List<Pill> findByShape(String shape){
        return em.createQuery("select p from Pill p where p.shape = :shape", Pill.class)
                .setParameter("shape", shape)
                .getResultList();
    }

    public List<Pill> findByLine(String line){
        return em.createQuery("select p from Pill p where p.lineBack = :line or p.lineFront = :line", Pill.class)
                .setParameter("line", line)
                .getResultList();


    }

    public List<Pill> findByColor(String color){
        return em.createQuery("select p from Pill p where p.colorFront = :color or p.colorBack = :color", Pill.class)
                .setParameter("color", color)
                .getResultList();
    }

    public List<Pill> findByColorAndShape(String color, String shape){
        return em.createQuery("select p from Pill p where (p.colorBack = :color or p.colorFront = :color) " +
                "and (p.shape = :shape)", Pill.class)
                .setParameter("color", color)
                .setParameter("shape", shape)
                .getResultList();
    }

    public List<Pill> findByAll(String color, String shape, String print){
        return em.createQuery("select p from Pill p where (p.colorBack = :color or p.colorFront = :color)" +
                        "and (p.shape = :shape) " +
                        "and (p.printFront = :print or p.printBack = :print)", Pill.class)
                .setParameter("color", color)
                .setParameter("shape", shape)
                .setParameter("print", print)
                .getResultList();
    }

}
