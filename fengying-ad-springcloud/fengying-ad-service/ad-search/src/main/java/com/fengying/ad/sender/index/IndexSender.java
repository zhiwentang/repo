package com.fengying.ad.sender.index;

import com.alibaba.fastjson.JSON;
import com.fengying.ad.dump.table.*;
import com.fengying.ad.handler.AdLeveLDataHandler;
import com.fengying.ad.index.DateLevel;
import com.fengying.ad.mysql.constant.Constant;
import com.fengying.ad.mysql.dto.MysqlRowData;
import com.fengying.ad.sender.ISender;
import com.fengying.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("indexSender")
public class IndexSender implements ISender {

    @Override
    public void sender(MysqlRowData rowData) {
        String level=rowData.getLevel();
        if(DateLevel.LEVEL2.getLevel().equals(level)){
            Level2RowData(rowData);
        }else if(DateLevel.LEVEL3.getLevel().equals(level)){
            Level3RowData(rowData);
        }else if(DateLevel.LEVEL4.getLevel().equals(level)){
            Level4RowData(rowData);
        }else {
            log.error("MysqlRowData error:{}", JSON.toJSONString(rowData));
        }
    }

    private void Level2RowData(MysqlRowData rowData){
        if(rowData.getTableName().equals(Constant.AD_PLAN_TABLE_INFO.TABLE_NAME)){
            List<AdPlanTable> planTables=new ArrayList<>();

            for (Map<String,String> fieldValueMap:rowData.getFieldValueMap()){
                AdPlanTable adPlanTable=new AdPlanTable();
                fieldValueMap.forEach((K,V)->{
                    switch (K){
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_ID:
                            adPlanTable.setId(Long.valueOf(V));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_USERID:
                            adPlanTable.setUserId(Long.valueOf(V));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_PLAN_STATUS:
                            adPlanTable.setPlanStatus(Integer.valueOf(V));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_START_DATE:
                            adPlanTable.setStartDate(CommonUtils.parseStringDate(V));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_END_DATE:
                            adPlanTable.setEndDate(CommonUtils.parseStringDate(V));
                            break;
                    }
                });
                planTables.add(adPlanTable);
            }
            planTables.forEach(p-> AdLeveLDataHandler.handlerLevel2(p,rowData.getOpType()));
        }else if(rowData.getTableName().equals(Constant.AD_CREATIVE_TABLE_INFO.TABLE_NAME)){
            List<AdCreativeTable> creativeTables=new ArrayList<>();

            for (Map<String,String> fieldValueMap:rowData.getFieldValueMap()){
                AdCreativeTable adCreativeTable=new AdCreativeTable();
                fieldValueMap.forEach((K,V)->{
                    switch (K){
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_ID:
                            adCreativeTable.setAdId(Long.valueOf(V));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_AUDIT_STATUS:
                            adCreativeTable.setAuditStatus(Integer.valueOf(V));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_HEIGHT:
                            adCreativeTable.setHeight(Integer.valueOf(V));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_MATERIAL_TYPE:
                            adCreativeTable.setMaterialTpye(Integer.valueOf(V));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_TYPE:
                            adCreativeTable.setType(Integer.valueOf(V));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_URL:
                            adCreativeTable.setAdUrl(V);
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_WIDTH:
                            adCreativeTable.setWidth(Integer.valueOf(V));
                            break;
                    }
                });
                creativeTables.add(adCreativeTable);
            }
            creativeTables.forEach(p-> AdLeveLDataHandler.handlerLevel2(p,rowData.getOpType()));
        }
    }


    private void Level3RowData(MysqlRowData rowData){
        if(rowData.getTableName().equals(Constant.AD_UNIT_TABLE_INFO.TABLE_NAME)){
            List<AdUnitTable> unitTables=new ArrayList<>();

            for(Map<String,String> fieldValueMap:rowData.getFieldValueMap()){
                AdUnitTable adUnitTable=new AdUnitTable();
                fieldValueMap.forEach((K,V)->{
                    switch (K){
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_ID:
                            adUnitTable.setUnitId(Long.valueOf(V));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_PLAN_ID:
                            adUnitTable.setPlanId(Long.valueOf(V));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_POSITION_TYPE:
                            adUnitTable.setPositionType(Integer.valueOf(V));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_UNIT_STATUS:
                            adUnitTable.setUnitStatus(Integer.valueOf(V));
                            break;
                    }
                });
                unitTables.add(adUnitTable);
            }
            unitTables.forEach(p->AdLeveLDataHandler.handlerLevel3(p,rowData.getOpType()));
        }else if(rowData.getTableName().equals(Constant.AD_CREATIVE_UNIT_TABLE_INFO.TABLE_NAME)){
            List<AdCreativeUnitTable> creativeUnitTables=new ArrayList<>();
            for (Map<String,String> fieldValueMap:rowData.getFieldValueMap()){
                AdCreativeUnitTable adCreativeUnitTable=new AdCreativeUnitTable();
                fieldValueMap.forEach((K,V)->{
                    switch (K){
                        case Constant.AD_CREATIVE_UNIT_TABLE_INFO.COLUMN_UNIT_ID:
                            adCreativeUnitTable.setUnitId(Long.valueOf(V));
                            break;
                        case Constant.AD_CREATIVE_UNIT_TABLE_INFO.COLUMN_CREATIVE_ID:
                            adCreativeUnitTable.setAdId(Long.valueOf(V));
                            break;
                    }
                });
                creativeUnitTables.add(adCreativeUnitTable);
            }
            creativeUnitTables.forEach(p->AdLeveLDataHandler.handlerLevel3(p,rowData.getOpType()));
        }
    }

    private void Level4RowData(MysqlRowData rowData){
        if(rowData.getTableName().equals(Constant.AD_UNIT_DISTRICT_TABLE_INFO.TABLE_NAME)){
            List<AdUnitDistrictTable> unitDistrictTables=new ArrayList<>();
            for(Map<String,String> fieldValueMap:rowData.getFieldValueMap()){
                AdUnitDistrictTable adUnitDistrictTable=new AdUnitDistrictTable();
                fieldValueMap.forEach((K,V)->{
                    switch (K){
                        case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_UNIT_ID:
                            adUnitDistrictTable.setUnitId(Long.valueOf(V));
                            break;
                        case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_CITY:
                            adUnitDistrictTable.setCity(V);
                            break;
                        case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_PROVINCE:
                            adUnitDistrictTable.setProvince(V);
                            break;
                    }
                });
                unitDistrictTables.add(adUnitDistrictTable);
            }
            unitDistrictTables.forEach(p->AdLeveLDataHandler.handlerLevel4(p,rowData.getOpType()));
        }else if(rowData.getTableName().equals(Constant.AD_UNIT_IT_TABLE_INFO.TABLE_NAME)){
            List<AdUnitItTable> unitItTables=new ArrayList<>();
            for(Map<String,String> fieldValueMap:rowData.getFieldValueMap()){
                AdUnitItTable adUnitItTable=new AdUnitItTable();
                fieldValueMap.forEach((K,V)->{
                    switch (K){
                        case Constant.AD_UNIT_IT_TABLE_INFO.COLUMN_UNIT_ID:
                            adUnitItTable.setUnitId(Long.valueOf(V));
                            break;
                        case Constant.AD_UNIT_IT_TABLE_INFO.COLUMN_IT_TAG:
                            adUnitItTable.setItTag(V);
                            break;
                    }
                });
                unitItTables.add(adUnitItTable);
            }
            unitItTables.forEach(p->AdLeveLDataHandler.handlerLevel4(p,rowData.getOpType()));
        }else if(rowData.getTableName().equals(Constant.AD_UNIT_KEYWORD_TABLE_INFO.TABLE_NAME)){
            List<AdUnitKeywordTable> unitKeywordTables=new ArrayList<>();
            for(Map<String,String> fieldValueMap:rowData.getFieldValueMap()){
                AdUnitKeywordTable adUnitKeywordTable=new AdUnitKeywordTable();
                fieldValueMap.forEach((K,V)->{
                    switch (K){
                        case Constant.AD_UNIT_KEYWORD_TABLE_INFO.COLUMN_UNIT_ID:
                            adUnitKeywordTable.setUnitId(Long.valueOf(V));
                            break;
                        case Constant.AD_UNIT_KEYWORD_TABLE_INFO.COLUMN_KEYWORD:
                            adUnitKeywordTable.setKeyword(V);
                            break;
                    }
                });
                unitKeywordTables.add(adUnitKeywordTable);
            }
            unitKeywordTables.forEach(p->AdLeveLDataHandler.handlerLevel4(p,rowData.getOpType()));
        }
    }
}
