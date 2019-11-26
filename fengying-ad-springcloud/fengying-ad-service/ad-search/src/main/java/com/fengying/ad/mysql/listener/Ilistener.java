package com.fengying.ad.mysql.listener;

import com.fengying.ad.mysql.dto.BinlogRowData;

public interface Ilistener {

    void register();

    void onEvent(BinlogRowData eventData);
}
