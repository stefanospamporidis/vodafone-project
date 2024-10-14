package com.example.vodafone.controller;

import com.example.vodafone.dto.LocationRequestDto;
import com.example.vodafone.entity.PointOfInterest;
import com.example.vodafone.service.PointOfInterestService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@QuarkusTest
public class PointOfInterestControllerTest {

    @InjectMock
    private PointOfInterestService pointOfInterestService;

    @Test
    void testAddPointOfInterest() {

        PointOfInterest poi = new PointOfInterest();
        poi.setName("Central Park");
        poi.setLatitude(40.785091);
        poi.setLongitude(-73.968285);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(poi)
                .when()
                .post("/api/poi/add")
                .then()
                .statusCode(200)
                .body("name", equalTo("Central Park"))
                .body("latitude", equalTo(40.785091f))
                .body("longitude", equalTo(-73.968285f));
    }

    @Test
    void testGetNearestPoint() {

        LocationRequestDto locationRequestDto = new LocationRequestDto();
        locationRequestDto.setLatitude(40.785091);
        locationRequestDto.setLongitude(-73.968285);

        PointOfInterest nearestPOI = new PointOfInterest();
        nearestPOI.setId(1L);
        nearestPOI.setName("Central Park");
        nearestPOI.setLatitude(40.785091);
        nearestPOI.setLongitude(-73.968285);
        nearestPOI.setRequestCount(5);

        when(pointOfInterestService.getNearestPOI(40.785091, -73.968285)).thenReturn(nearestPOI);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(locationRequestDto)
                .when()
                .post("/api/poi/nearest")
                .then()
                .statusCode(200)
                .body("name", equalTo("Central Park"))
                .body("latitude", equalTo(40.785091f))
                .body("longitude", equalTo(-73.968285f));
    }

    @Test
    void testGetPointOfInterestsByCounter() {
        // Given
        int threshold = 5;
        List<PointOfInterest> points = Arrays.asList(
                new PointOfInterest(1L, "POI 1", 40.000, -74.000, 10),
                new PointOfInterest(2L, "POI 2", 41.000, -75.000, 6)
        );

        when(pointOfInterestService.getPOIsWithRequestCountGreaterThan(threshold)).thenReturn(points);

        given()
                .queryParam("threshold", threshold)
                .when()
                .get("/api/poi/counter")
                .then()
                .statusCode(200)
                .body("$", hasSize(2)) // expecting 2 results
                .body("[0].name", equalTo("POI 1"))
                .body("[1].name", equalTo("POI 2"));
    }
}
