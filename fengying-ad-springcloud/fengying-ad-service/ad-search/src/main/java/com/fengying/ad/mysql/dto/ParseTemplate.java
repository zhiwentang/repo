package com.fengying.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
public class ParseTemplate {

    private String database;

    private Map<String,TableTemplate> tableTemplateMap=new HashMap<>();
}
