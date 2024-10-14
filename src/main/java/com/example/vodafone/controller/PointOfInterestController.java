package com.example.vodafone.controller;

import com.example.vodafone.dto.LocationRequestDto;
import com.example.vodafone.dto.PointOfInterestDto;
import com.example.vodafone.entity.PointOfInterest;
import com.example.vodafone.mapper.PointOfInterestMapper;
import com.example.vodafone.service.PointOfInterestService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/api/poi")
public class PointOfInterestController {

    @Inject
    PointOfInterestService pointOfInterestService;

    @Inject
    PointOfInterestMapper pointOfInterestMapper;

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPointOfInterest(PointOfInterest pointOfInterest) {
        pointOfInterestService.addPOI(pointOfInterest);
        PointOfInterestDto pointOfInterestDto = pointOfInterestMapper.toPointOfInterestDto(pointOfInterest);
        return Response.ok(pointOfInterestDto).build();
    }

     @POST
     @Path("/nearest")
     @Produces(MediaType.APPLICATION_JSON)
     @Consumes(MediaType.APPLICATION_JSON)
     public Response getNearestPoint(LocationRequestDto locationRequestDto) {
         PointOfInterest nearest = pointOfInterestService.getNearestPOI(locationRequestDto.getLatitude(), locationRequestDto.getLongitude());
         PointOfInterestDto pointOfInterestDto = pointOfInterestMapper.toPointOfInterestDto(nearest);
         return Response.ok(pointOfInterestDto).build();
     }

     @GET
     @Path("/counter")
     @Produces(MediaType.APPLICATION_JSON)
     @Consumes(MediaType.APPLICATION_JSON)
     public Response getPointOfInterestsByCounter(@QueryParam("threshold") int threshold) {
         List<PointOfInterest> points = pointOfInterestService.getPOIsWithRequestCountGreaterThan(threshold);
         List<PointOfInterestDto> pointOfInterestDtoList = points.stream()
                 .map(pointOfInterestMapper::toPointOfInterestDto)
                 .collect(Collectors.toList());
         return Response.ok(pointOfInterestDtoList).build();
     }

}
