package com.fengying.ad.mysql.dto;

import com.fengying.ad.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MysqlRowData {

    private String tableName;
    private String level;
    private OpType opType;

    private List<Map<String,String>> fieldValueMap=new ArrayList<>();
}
