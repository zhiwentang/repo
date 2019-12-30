package com.fengying.ad.index;

import com.alibaba.fastjson.JSON;
import com.fengying.ad.dump.DConstant;
import com.fengying.ad.dump.table.*;
import com.fengying.ad.handler.AdLeveLDataHandler;
import com.fengying.ad.mysql.constant.OpType;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

//将索引从文件中加载到内存
@Component
@DependsOn("dateTable")
public class IndexFileLoader {

    @PostConstruct
    public void init(){
        List<String> adPlanStrings=loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR,DConstant.AD_PLAN)
        );
        adPlanStrings.forEach(p-> AdLeveLDataHandler.handlerLevel2(JSON.parseObject
                (p, AdPlanTable.class), OpType.ADD));

        List<String> adCreativeStrings=loadDumpData(
                String.format("%s%s",DConstant.DATA_ROOT_DIR,DConstant.AD_CREATIVE)
        );
        adCreativeStrings.forEach(p->AdLeveLDataHandler.handlerLevel2(JSON.parseObject(
                p, AdCreativeTable.class),OpType.ADD));

        List<String> adUnitStrings=loadDumpData(
                String.format("%s%s",DConstant.DATA_ROOT_DIR,DConstant.AD_UNIT)
        );
        adUnitStrings.forEach(p->AdLeveLDataHandler.handlerLevel3(
                JSON.parseObject(p, AdUnitTable.class),OpType.ADD
        ));

        List<String> adCreativeUnitStrings=loadDumpData(
                String.format("%s%s",DConstant.DATA_ROOT_DIR,DConstant.AD_CREATIVE_UNIT)
        );
        adCreativeUnitStrings.forEach(p->AdLeveLDataHandler.handlerLevel3(
                JSON.parseObject(p, AdCreativeUnitTable.class),OpType.ADD
        ));

        List<String> adUnitDistrictStrings=loadDumpData(
                String.format("%s%s",DConstant.DATA_ROOT_DIR,DConstant.AD_UNIT_DISTRICT)
        );
        adUnitDistrictStrings.forEach(p->AdLeveLDataHandler.handlerLevel4(
                JSON.parseObject(p, AdUnitDistrictTable.class),OpType.ADD
        ));

        List<String> adUnitKeywordStrings=loadDumpData(
                String.format("%s%s",DConstant.DATA_ROOT_DIR,DConstant.AD_UNIT_KEYWORD)
        );
        adUnitKeywordStrings.forEach(p->AdLeveLDataHandler.handlerLevel4(
                JSON.parseObject(p,AdUnitKeywordTable.class),OpType.ADD
        ));

        List<String> adUnitItStrings=loadDumpData(
                String.format("%s%s",DConstant.DATA_ROOT_DIR,DConstant.AD_UNIT_IT)
        );
        adUnitItStrings.forEach(p->AdLeveLDataHandler.handlerLevel4(
                JSON.parseObject(p,AdUnitItTable.class),OpType.ADD
        ));

    }

    private List<String> loadDumpData(String filename){

        try (BufferedReader br= Files.newBufferedReader(Paths.get(filename));){
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
