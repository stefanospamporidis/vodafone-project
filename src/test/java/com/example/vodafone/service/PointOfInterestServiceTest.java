package com.example.vodafone.service;

import com.example.vodafone.entity.PointOfInterest;
import com.example.vodafone.exception.PointOfInterestNotFoundException;
import com.example.vodafone.repository.PointOfInterestRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.InjectMock;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
public class PointOfInterestServiceTest {

    @Inject
    private PointOfInterestService pointOfInterestService;

    @InjectMock
    private PointOfInterestRepository pointOfInterestRepository;

    @Test
    void testAddPOI() {
        // Given
        PointOfInterest poi = new PointOfInterest();
        poi.setName("Central Park");
        poi.setLatitude(40.785091);
        poi.setLongitude(-73.968285);

        // When
        pointOfInterestService.addPOI(poi);

        // Then
        verify(pointOfInterestRepository, times(1)).save(poi);
    }

    @Test
    void testGetNearestPOIFound() {
        // Given
        double latitude = 40.785091;
        double longitude = -73.968285;
        PointOfInterest nearestPOI = new PointOfInterest();
        nearestPOI.setId(1L);
        nearestPOI.setName("Central Park");
        nearestPOI.setLatitude(latitude);
        nearestPOI.setLongitude(longitude);

        when(pointOfInterestRepository.findNearest(latitude, longitude)).thenReturn(nearestPOI);

        // When
        PointOfInterest result = pointOfInterestService.getNearestPOI(latitude, longitude);

        // Then
        assertNotNull(result);
        assertEquals("Central Park", result.getName());
        verify(pointOfInterestRepository, times(1)).incrementRequestCount(nearestPOI.getId());
    }

    @Test
    void testGetPOIsWithRequestCountGreaterThanFound() {
        // Given
        int threshold = 5;
        List<PointOfInterest> expectedPoints = Arrays.asList(
                new PointOfInterest(1L, "POI 1", 40.000, -74.000, 10),
                new PointOfInterest(2L, "POI 2", 41.000, -75.000, 6)
        );

        when(pointOfInterestRepository.findByRequestCountGreaterThan(threshold)).thenReturn(expectedPoints);

        // When
        List<PointOfInterest> result = pointOfInterestService.getPOIsWithRequestCountGreaterThan(threshold);

        // Then
        assertEquals(2, result.size());
        assertEquals("POI 1", result.get(0).getName());
        verify(pointOfInterestRepository, times(1)).findByRequestCountGreaterThan(threshold);
    }

    @Test
    void testGetPOIsWithRequestCountGreaterThanNotFound() {
        // Given
        int threshold = 5;

        when(pointOfInterestRepository.findByRequestCountGreaterThan(threshold)).thenReturn(Collections.emptyList());

        // When
        PointOfInterestNotFoundException exception = assertThrows(PointOfInterestNotFoundException.class, () ->
                pointOfInterestService.getPOIsWithRequestCountGreaterThan(threshold)
        );

        // Then
        assertEquals("No points of interest found with a request count greater than " + threshold, exception.getMessage());
    }
}