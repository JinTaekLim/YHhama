package com.YH.yeohaenghama.domain.itinerary.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import lombok.Data;

import java.util.List;

@Data
public class ItineraryShowDTO {
    private Long itineraryId;
    private String name;
    private String type;
    private String transportation;
    private String area;
    private String startDate;
    private String endDate;
    private String expense;
    private AccountShowDTO account;
    private List<PlaceShowDTO> placeShowDTOs;

    public ItineraryShowDTO(Itinerary itinerary, AccountShowDTO account, List<PlaceShowDTO> placeShowDTOs) {
        this.itineraryId = itinerary.getId();
        this.name = itinerary.getName();
        this.type = itinerary.getType();
        this.transportation = itinerary.getTransportation();
        this.area = itinerary.getArea();
        this.startDate = itinerary.getStartDate();
        this.endDate = itinerary.getEndDate();
        this.expense = itinerary.getExpense();
        this.account = account;
        this.placeShowDTOs = placeShowDTOs;
    }

}
