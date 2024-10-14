package com.example.vodafone.service;

import com.example.vodafone.entity.PointOfInterest;
import com.example.vodafone.exception.PointOfInterestNotFoundException;
import com.example.vodafone.repository.PointOfInterestRepository;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class PointOfInterestService {
    @Inject
    PointOfInterestRepository pointOfInterestRepository;

    @CacheInvalidate(cacheName = "my-cache")
    public void addPOI(PointOfInterest pointOfInterest) {
        pointOfInterestRepository.save(pointOfInterest);
    }

    @CacheResult(cacheName = "my-cache")
    public PointOfInterest getNearestPOI(double latitude, double longitude) {
        PointOfInterest nearest = pointOfInterestRepository.findNearest(latitude, longitude);
        if (nearest == null) {
            throw new PointOfInterestNotFoundException("No point of interest found near the provided coordinates");
        }
        pointOfInterestRepository.incrementRequestCount(nearest.getId());
        return nearest;
    }

    public List<PointOfInterest> getPOIsWithRequestCountGreaterThan(int threshold) {
        List<PointOfInterest> points = pointOfInterestRepository.findByRequestCountGreaterThan(threshold);
        if (points.isEmpty()) {
            throw new PointOfInterestNotFoundException("No points of interest found with a request count greater than " + threshold);
        }
        return points;
    }

}
