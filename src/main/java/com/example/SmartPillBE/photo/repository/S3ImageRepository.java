package com.example.SmartPillBE.photo.repository;

import com.example.SmartPillBE.domain.Profile;
import com.example.SmartPillBE.photo.domain.S3Image;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class S3ImageRepository implements JpaRepository<S3Image, Long> {

    private final EntityManager em;

    public List<S3Image> findByProfileAndCategory(Profile profile, String category){
        return em.createQuery("select s from S3Image s " +
                "where s.profile = :profile " +
                "and s.category = :category", S3Image.class)
                .setParameter("profile", profile)
                .setParameter("category", category)
                .getResultList();
    }

    public List<S3Image> findByCategory(String category){
        return em.createQuery("select s from S3Image s " +
                        "where s.category = :category", S3Image.class)
                .setParameter("category", category)
                .getResultList();
    }

    public void saveProfileImg(S3Image image){
        List<S3Image> images = em.createQuery("select s from S3Image s " +
                        "where s.profile = :profile and s.category = :category", S3Image.class)
                .setParameter("profile", image.getProfile())
                .setParameter("category", "profile")
                .getResultList();
        if (!images.isEmpty()){
            for (S3Image s3Image : images) {
                em.remove(s3Image);
            }
        }
        em.persist(image);
    }

    @Override
    public <S extends S3Image> S save(S entity) {
        em.persist(entity);
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends S3Image> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends S3Image> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<S3Image> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public S3Image getOne(Long aLong) {
        return null;
    }

    @Override
    public S3Image getById(Long aLong) {
        return null;
    }

    @Override
    public S3Image getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends S3Image> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends S3Image> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends S3Image> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends S3Image> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends S3Image> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends S3Image> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends S3Image, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends S3Image> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<S3Image> findById(Long id) {
        return Optional.ofNullable(em.find(S3Image.class, id));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<S3Image> findAll() {
        return null;
    }

    @Override
    public List<S3Image> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(S3Image entity) {
        em.remove(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends S3Image> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<S3Image> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<S3Image> findAll(Pageable pageable) {
        return null;
    }
}
