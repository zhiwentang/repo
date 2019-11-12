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
import com.fengying.ad.dump.DConstant;
import com.fengying.ad.dump.table.*;
import com.fengying.ad.entity.AdPlan;
import com.fengying.ad.entity.AdUnit;
import com.fengying.ad.entity.Creative;
import com.fengying.ad.entity.unit_condition.AdUnitDistrict;
import com.fengying.ad.entity.unit_condition.AdUnitIt;
import com.fengying.ad.entity.unit_condition.AdUnitKeyword;
import com.fengying.ad.entity.unit_condition.CreativeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
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

    @Test
    public void dumpAdTableData() {
        dumpAdPlanTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR,DConstant.AD_PLAN)
        );
        dumpAdUnitTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR,DConstant.AD_UNIT)
        );
        dumpAdCreativeTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR,DConstant.AD_CREATIVE)
        );
        dumpAdCreativeUnitTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR,DConstant.AD_CREATIVE_UNIT)
        );
        dumpAdUnitItTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR,DConstant.AD_UNIT_IT)
        );
        dumpAdUnitDistrictTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR,DConstant.AD_UNIT_DISTRICT)
        );
        dumpAdUnitKeywordTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR,DConstant.AD_UNIT_KEYWORD)
        );
    }

    private void dumpAdPlanTable(String filename) {

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

    private void dumpAdCreativeUnitTable(String filename){
        List<CreativeUnit> creativeUnits=creativeUnitRepository.findAll();
        if(CollectionUtils.isEmpty(creativeUnits)){
            return;
        }
        List<AdCreativeUnitTable> creativeUnitTables=new ArrayList<>();
        creativeUnits.forEach(p->creativeUnitTables.add(new AdCreativeUnitTable(p.getCreativeId(),p.getUnitId())));
        Path path=Paths.get(filename);
        try(BufferedWriter writer=Files.newBufferedWriter(path)){
            for(AdCreativeUnitTable adCreativeUnitTable:creativeUnitTables){
                writer.write(JSON.toJSONString(adCreativeUnitTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("dumpAdCreativeUnitTable error");
        }
    }

    private void dumpAdUnitItTable(String filename){
        List<AdUnitIt> unitIts=adUnitItRepository.findAll();
        if(CollectionUtils.isEmpty(unitIts)){
            return;
        }
        List<AdUnitItTable> unitItTables=new ArrayList<>();
        unitIts.forEach(u->unitItTables.add(new AdUnitItTable(
                u.getUnitId(),u.getItTag()
        )));
        Path path=Paths.get(filename);
        try(BufferedWriter writer=Files.newBufferedWriter(path)){
            for(AdUnitItTable adUnitItTable:unitItTables){
                writer.write(JSON.toJSONString(adUnitItTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("dumpAdUnitItTable error");
        }
    }

    private void  dumpAdUnitDistrictTable(String filename){
        List<AdUnitDistrict> unitDistricts=adUnitDistrictRepository.findAll();
        if(CollectionUtils.isEmpty(unitDistricts)){
            return;
        }
        List<AdUnitDistrictTable> unitDistrictTables=new ArrayList<>();
        unitDistricts.forEach(u->unitDistrictTables.add(new AdUnitDistrictTable
                        (u.getUnitId(),u.getProvince(),u.getCity())
        ));
        Path path=Paths.get(filename);
        try(BufferedWriter writer=Files.newBufferedWriter(path)){
            for(AdUnitDistrictTable adUnitDistrictTable:unitDistrictTables){
                writer.write(JSON.toJSONString(adUnitDistrictTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("dumpAdUnitDistrictTable error");
        }
    }

    private void dumpAdUnitKeywordTable(String filename){
        List<AdUnitKeyword> unitKeywords=adUnitKeywordRepository.findAll();
        if(CollectionUtils.isEmpty(unitKeywords)){
            return;
        }
        List<AdUnitKeywordTable> unitKeywords1=new ArrayList<>();
        unitKeywords.forEach(u->unitKeywords1.add(new AdUnitKeywordTable(
                u.getUnitId(),u.getKeyword()
        )));
        Path path=Paths.get(filename);
        try(BufferedWriter writer=Files.newBufferedWriter(path)){
            for(AdUnitKeywordTable adUnitKeywordTable:unitKeywords1){
                writer.write(JSON.toJSONString(adUnitKeywordTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("dumpAdUnitKeywordTable error");
        }

    }
}
