package com.heo.exam.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class MessageParam {

    @JsonProperty("touser")
    private String openid;

    @JsonProperty("template_id")
    private String templateId;

    private String page;

    @JsonProperty("form_id")
    private String formId;

    private Map<String,Map> data = new LinkedHashMap<>();

    @JsonIgnore
    private int i = 1;


    public MessageParam(String openid, String templateId, String page){
        this.openid = openid;
        this.templateId = templateId;
        this.page = page;
    }

    public MessageParam addData(String value){
        return addData(i++,value);
    }

    private MessageParam addData(Integer i,String value){
        Map<String,String> valueMap = new HashMap<>();
        valueMap.put("value",value);
        this.data.put("keyword"+i,valueMap);
        return this;
    }

    public String toJson(){

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
}

