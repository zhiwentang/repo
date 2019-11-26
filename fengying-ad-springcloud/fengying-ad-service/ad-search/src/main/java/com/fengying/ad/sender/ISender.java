package com.fengying.ad.sender;

import com.fengying.ad.mysql.dto.MysqlRowData;

public interface ISender {

    void sender(MysqlRowData rowData);
}
