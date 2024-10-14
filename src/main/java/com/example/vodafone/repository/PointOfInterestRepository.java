package com.example.vodafone.repository;

import com.example.vodafone.entity.PointOfInterest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class PointOfInterestRepository {
    @Inject
    private EntityManager em;

    @Transactional
    public void save(PointOfInterest pointOfInterest) {
        em.persist(pointOfInterest);
    }

    @Transactional
    public List<PointOfInterest> findAll() {
        return em.createQuery("SELECT p FROM PointOfInterest p", PointOfInterest.class).getResultList();
    }

    @Transactional
    public PointOfInterest findNearest(double latitude, double longitude) {
        List<PointOfInterest> resultList = em.createQuery(
                        "SELECT p FROM PointOfInterest p ORDER BY ST_DistanceSphere(ST_MakePoint(p.longitude, p.latitude), ST_MakePoint(:longitude, :latitude))",
                        PointOfInterest.class)
                .setParameter("longitude", longitude)
                .setParameter("latitude", latitude)
                .setMaxResults(1)
                .getResultList();

        if (resultList.isEmpty()) {
            return null;
        }

        return resultList.get(0);
    }


    @Transactional
    public List<PointOfInterest> findByRequestCountGreaterThan(int threshold) {
        List<PointOfInterest> resultList = em.createQuery("SELECT p FROM PointOfInterest p WHERE p.requestCount > :threshold", PointOfInterest.class)
                .setParameter("threshold", threshold)
                .getResultList();

        return resultList;
    }

    @Transactional
    public void incrementRequestCount(Long id) {
        em.createQuery("UPDATE PointOfInterest p SET p.requestCount = p.requestCount + 1 WHERE p.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

}