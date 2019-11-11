package com.fengying.ad.service;

import com.alibaba.fastjson.JSON;
import com.fengying.ad.Application;
import com.fengying.ad.constant.CommonStatus;
import com.fengying.ad.dao.AdPlanRepository;
import com.fengying.ad.dao.AdUnitRepository;
import com.fengying.ad.dao.CreativeRepository;
import com.fengying.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.fengying.ad.dao.unit_condition.AdUnitItRepository;
import com.fengying.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.fengying.ad.dao.unit_condition.CreativeUnitRepository;
import com.fengying.ad.dump.table.AdCreativeTable;
import com.fengying.ad.dump.table.AdPlanTable;
import com.fengying.ad.dump.table.AdUnitTable;
import com.fengying.ad.entity.AdPlan;
import com.fengying.ad.entity.AdUnit;
import com.fengying.ad.entity.Creative;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class},webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DumpDataService {

    @Autowired
    private AdPlanRepository adPlanRepository;
    @Autowired
    private AdUnitRepository adUnitRepository;
    @Autowired
    private CreativeUnitRepository creativeUnitRepository;
    @Autowired
    private AdUnitKeywordRepository adUnitKeywordRepository;
    @Autowired
    private AdUnitItRepository adUnitItRepository;
    @Autowired
    private AdUnitDistrictRepository adUnitDistrictRepository;
    @Autowired
    private CreativeRepository creativeRepository;

    private void dumpAdPlanTable(String filename) throws IOException {

        List<AdPlan> adPlans=adPlanRepository.findAllByPlanStatus(CommonStatus.VAlID.getStatus());
        if(CollectionUtils.isEmpty(adPlans)){
            return;
        }
        List<AdPlanTable> planTables=new ArrayList<>();
        adPlans.forEach(p->planTables.add(new AdPlanTable
                (p.getId(),
                p.getUserId(),
                p.getPlanStatus(),
                p.getStartDate(),
                p.getEndDate()
                )
        ));
        Path path= Paths.get(filename);
        try (BufferedWriter writer= Files.newBufferedWriter(path)){
            for(AdPlanTable planTable:planTables){
                writer.write(JSON.toJSONString(planTable));
                writer.newLine();
            }
            writer.close();
        } catch(IOException e) {
            log.error("dumpAdPlanTable error");
        }
    }

    private void dumpAdUnitTable(String filename){
        List<AdUnit> adUnits=adUnitRepository.findAllByUnitStatus(CommonStatus.VAlID.getStatus());
        if(CollectionUtils.isEmpty(adUnits)){
            return;
        }
        List<AdUnitTable> unitTables=new ArrayList<>();
        adUnits.forEach(p->unitTables.add(new AdUnitTable(
                p.getId(),p.getPlanId(),p.getPositionType(),p.getUnitStatus()
        )));
        Path path=Paths.get(filename);
        try(BufferedWriter writer=Files.newBufferedWriter(path)){
            for(AdUnitTable unitTable:unitTables){
                writer.write(JSON.toJSONString(unitTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("dumpAdUnitTable error");
        }
    }

    private void dumpAdCreativeTable(String filename){
        List<Creative> creatives=creativeRepository.findAll();
        if(CollectionUtils.isEmpty(creatives)){
            return;
        }
        List<AdCreativeTable> creativeTables=new ArrayList<>();
        creatives.forEach(p->creativeTables.add(new AdCreativeTable(
                p.getId(),p.getName(),p.getType(),p.getMaterialType(),p.getHeight(),p.getWidth(),p.getAuditStatus(),p.getUrl()
        )));
        Path path=Paths.get(filename);
        try(BufferedWriter writer=Files.newBufferedWriter(path)){
            for(AdCreativeTable adCreativeTable:creativeTables){
                writer.write(JSON.toJSONString(adCreativeTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("dumpAdCreativeTable error");
        }
    }
}
