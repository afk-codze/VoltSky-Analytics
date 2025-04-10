package com.sapienza.controller;

import com.sapienza.dto.AnomalyDto;
import com.sapienza.model.Datastream;
import com.sapienza.service.AnomalyService;
import com.sapienza.service.DatastreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController()
public class RestApiController {

    @Autowired
    DatastreamService datastreamService;

    @Autowired
    AnomalyService anomaliesService;

    //API
    @GetMapping("/api/datastream/realtime")
    public  List<Datastream> getAllDatastreamsRealtime() {
        //returns all data in the last 2 hours

        return datastreamService.getAllDatastreamFromGivenTime(LocalDateTime.now().minusHours(2));
    }

    @GetMapping("/api/anomalies/realtime")
    public  List<AnomalyDto> getLastTenAnomaliesRealtime() {

        return anomaliesService.getMostRecentAnomaliesRealtime(10);
    }


    // Time range

    @GetMapping("/api/datastream/timeRange")
    public  List<Datastream> getAllDatastreamInTimeRange(
            @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") LocalDateTime end) {

        return datastreamService.getAllDatastreamInTimeRange(start,end) ;
    }

    @GetMapping("/api/anomalies/timeRange")
    public  List<AnomalyDto> getLastTenAnomaliesTimeRange(
            @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") LocalDateTime end

    ) {

        return anomaliesService.getMostRecentAnomaliesTimeRange(10,start,end);
    }



}
