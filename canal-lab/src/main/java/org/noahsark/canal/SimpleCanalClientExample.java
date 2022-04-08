package org.noahsark.canal;

import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Canal 客户端
 *
 * @author zhangxt
 * @date 2022/04/07 18:42
 **/
public class SimpleCanalClientExample {

    protected static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static void main(String args[]) {
        // 创建链接
        /*CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("192.168.7.115",
                11111), "example", "", "");*/
        //基于zookeeper动态获取canal server的地址，建立链接，其中一台server发生crash，可以支持failover
        CanalConnector connector = CanalConnectors.newClusterConnector("192.168.7.115:2181", "example", "", "");

        int batchSize = 1000;
        int emptyCount = 0;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            int totalEmptyCount = 120;
            while (emptyCount < totalEmptyCount) {
                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    System.out.println("empty count : " + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                } else {
                    emptyCount = 0;
                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
                    printEntry(message.getEntries());
                }

                connector.ack(batchId); // 提交确认
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }

            System.out.println("empty too many times, exit");
        } finally {
            connector.disconnect();
        }
    }

    private static void printEntry(List<Entry> entrys) {
        for (Entry entry : entrys) {
            /*if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }*/

            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN
                    || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                handleTransaction(entry);
                continue;
            }

            RowChange rowChage = null;
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            if (eventType == CanalEntry.EventType.QUERY || rowChage.getIsDdl()) {
                System.out.println(String.format(" sql ----> {}", rowChage.getSql()));
                continue;
            }

            for (RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == EventType.DELETE) {
                    printColumn(rowData.getBeforeColumnsList());
                } else if (eventType == EventType.INSERT) {
                    printColumn(rowData.getAfterColumnsList());
                } else {
                    System.out.println("-------&gt; before");
                    printColumn(rowData.getBeforeColumnsList());
                    System.out.println("-------&gt; after");
                    printColumn(rowData.getAfterColumnsList());
                }
            }
        }
    }

    private static void printColumn(List<Column> columns) {
        for (Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }

    private static void handleTransaction(CanalEntry.Entry entry) {
        long executeTime = entry.getHeader().getExecuteTime();
        long delayTime = new Date().getTime() - executeTime;
        String executeDateTime = new SimpleDateFormat(DATE_FORMAT).format(new Date(executeTime));
        if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN) {
            CanalEntry.TransactionBegin begin;
            try {
                begin = CanalEntry.TransactionBegin.parseFrom(entry.getStoreValue());
            } catch (InvalidProtocolBufferException ex) {
                throw new RuntimeException("parse event error , data:" + entry.toString(), ex);
            }
            System.out.println(String.format(" BEGIN ----> Thread id: %s", begin.getThreadId()));
            System.out.println(String.format("logFileName: %s ,logFileOffset: %d ,executeTime: %s ,delayTime: %d s",
                    entry.getHeader().getLogfileName(),
                    entry.getHeader().getLogfileOffset(),
                    executeDateTime,
                    new BigDecimal(delayTime).divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_UP)));
        } else if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
            CanalEntry.TransactionEnd end;
            try {
                end = CanalEntry.TransactionEnd.parseFrom(entry.getStoreValue());
            } catch (InvalidProtocolBufferException ex) {
                throw new RuntimeException("parse event error , data:" + entry.toString(), ex);
            }
            System.out.println(String.format("logFileName: %s ,logFileOffset: %d ,executeTime: %s ,delayTime: %d s",
                    entry.getHeader().getLogfileName(),
                    entry.getHeader().getLogfileOffset(),
                    executeDateTime,
                    new BigDecimal(delayTime).divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_UP)));
            System.out.println(String.format(" END ----> Transaction id :{}", end.getTransactionId()));
        }
    }


}
