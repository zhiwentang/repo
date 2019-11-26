package com.fengying.ad.mysql.listener;

import com.fengying.ad.mysql.TemplateHolder;
import com.fengying.ad.mysql.dto.BinlogRowData;
import com.fengying.ad.mysql.dto.TableTemplate;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    private String dbName;
    private String tableName;

    private Map<String,Ilistener> listenerMap=new HashMap<>();

    @Autowired
    private TemplateHolder templateHolder;

    private String getKey(String dbName,String tableName){
        return dbName+":"+tableName;
    }

    public void register(String _dbName,String _tableName,Ilistener ilistener){
        log.info("register:{}-{}",_dbName,_tableName);
        this.listenerMap.put(getKey(_dbName,_tableName),ilistener);
    }

    @Override
    public void onEvent(Event event) {

        EventType type=event.getHeader().getEventType();
        log.debug("event type:{}",type);

        if(type== EventType.TABLE_MAP){
            TableMapEventData data=event.getData();
            this.tableName=data.getTable();
            this.dbName=data.getDatabase();
            return;
        }

        if(type!=EventType.EXT_WRITE_ROWS&&
               type!=EventType.EXT_DELETE_ROWS&&
               type!=EventType.EXT_UPDATE_ROWS){
            return;
        }

        //表名或者库名是否填充
        if(StringUtils.isEmpty(tableName)||StringUtils.isEmpty(dbName)){
            log.error("no meta data event");
            return;
        }

        //找出感兴趣的表对应的监听器
        String key=getKey(this.dbName,this.tableName);
        Ilistener listener=this.listenerMap.get(key);
        if(null==listener){
            log.debug("skip:{}",key);
        }
        log.info("trigger event:{}",type.name());

        try {
            BinlogRowData rowData=buildRowData(event.getData());
            if(rowData==null){
                return;
            }
            rowData.setEventType(type);
            listener.onEvent(rowData);

        }catch (Exception ex){
            ex.printStackTrace();
            log.error(ex.getMessage());
        }finally {
            this.tableName="";
            this.dbName="";
        }

    }

    private List<Serializable[]> getAfterValues(EventData eventData){
        if(eventData instanceof WriteRowsEventData){
            return ((WriteRowsEventData)eventData).getRows();
        }
        if(eventData instanceof UpdateRowsEventData){
            return ((UpdateRowsEventData)eventData).getRows().stream().map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        }
        if(eventData instanceof  DeleteRowsEventData){
            return ((DeleteRowsEventData)eventData).getRows();
        }
        return Collections.emptyList();
    }

    //将event的data类型转换为自定义的BinlogRowdata类型
    private BinlogRowData buildRowData(EventData eventData){

        TableTemplate table=templateHolder.getTable(tableName);
        if(null==table){
            log.warn("table {} not found",tableName);
            return null;
        }
        List<Map<String,String>> afterMapList=new ArrayList<>();
        for (Serializable[] after:getAfterValues(eventData)){
            Map<String,String> afterMap=new HashMap<>();

            int collen=after.length;
            for(int ix=0;ix<collen;++ix){
                //取出当前位置对应的列名
                String colName=table.getPosMap().get(ix);
                //如果没有则不关心这个列
                if(colName==null){
                    log.debug("ignore position:{}",ix);
                    continue;
                }
                String colValue=after[ix].toString();
                afterMap.put(colName,colValue);
            }
            afterMapList.add(afterMap);
        }
        BinlogRowData rowData=new BinlogRowData();
        rowData.setAfter(afterMapList);
        rowData.setTable(table);
        return rowData;
    }
}
