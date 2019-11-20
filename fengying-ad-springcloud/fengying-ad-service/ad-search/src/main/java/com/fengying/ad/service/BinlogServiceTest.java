package com.fengying.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;


public class BinlogServiceTest {
    public static void main(String[] args) throws Exception {
        BinaryLogClient client=new BinaryLogClient(
                "127.0.0.1",3306,"root","tzw4587429"
        );
        client.registerEventListener(event -> {
            EventData data=event.getData();
            if(data instanceof UpdateRowsEventData){
                System.out.println("update...........");
                System.out.println(data.toString());
            }else if(data instanceof WriteRowsEventData){
                System.out.println("write..........");
                System.out.println(data.toString());
            }else if(data instanceof DeleteRowsEventData){
                System.out.println("delete.........");
                System.out.println(data.toString());
            }
        });

        client.connect();
    }
}
