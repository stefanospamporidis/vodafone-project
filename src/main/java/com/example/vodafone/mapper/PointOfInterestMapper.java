package com.example.vodafone.mapper;
import com.example.vodafone.dto.PointOfInterestDto;
import com.example.vodafone.entity.PointOfInterest;
import org.mapstruct.Mapper;


@Mapper(componentModel = "jakarta")
public interface PointOfInterestMapper {

    PointOfInterestDto toPointOfInterestDto(PointOfInterest pointOfInterest);

}
