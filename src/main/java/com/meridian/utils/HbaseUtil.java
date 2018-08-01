package com.meridian.utils;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Reticence (liuyang_blue@qq.com)
 * @version 1.0
 * @date 2018-04-04 12:57
 * @parameter
 */
public class HbaseUtil {
    private static String SERIES = "s";
    private static String TABLENAME = "AF_TABLE";
    private static Connection conn;

    public static void init() {
        Configuration config = HBaseConfiguration.create();
        String hbaseIp = "10.1.1.119";
        config.set("hbase.zookeeper.quorum", hbaseIp);
        try {
            conn = ConnectionFactory.createConnection(config);
            createTable(TABLENAME, SERIES);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //创建表
    public static void createTable(String tableName, String seriesStr) throws IllegalArgumentException, IOException {
        Admin admin = null;
        TableName table = TableName.valueOf(tableName);
        try {
            admin = conn.getAdmin();
            if (!admin.tableExists(table)) {
                System.out.println(tableName + " table not Exists");
                HTableDescriptor descriptor = new HTableDescriptor(table);
                String[] series = seriesStr.split(",");
                for (String s : series) {
                    descriptor.addFamily(new HColumnDescriptor(s.getBytes()));
                }
                admin.createTable(descriptor);
            }
        } finally {
            IOUtils.closeQuietly(admin);
        }
    }

    //添加数据
//    public static void add(String rowKey, Map<String, Object> columns) throws IOException {
//        Table table = null;
//        try {
//            table = conn.getTable(TableName.valueOf(TABLENAME));
//            Put put = new Put(Bytes.toBytes(rowKey));
//            for (Map.Entry<String, Object> entry : columns.entrySet()) {
//                put.addColumn(SERIES.getBytes(), Bytes.toBytes(entry.getKey()),
//                        new ObjectAndByte().toByteArray(entry.getValue()));
//            }
//            table.put(put);
//        } finally {
//            IOUtils.closeQuietly(table);
//        }
//    }

    //根据rowkey获取数据
    public static Map<String, String> getAllValue(String rowKey) throws IllegalArgumentException, IOException {
        Table table = null;
        Map<String, String> resultMap = null;
        try {
            table = conn.getTable(TableName.valueOf(TABLENAME));
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addFamily(SERIES.getBytes());
            Result res = table.get(get);
            Map<byte[], byte[]> result = res.getFamilyMap(SERIES.getBytes());
            Iterator<Map.Entry<byte[], byte[]>> it = result.entrySet().iterator();
            resultMap = new HashMap<String, String>();
            while (it.hasNext()) {
                Map.Entry<byte[], byte[]> entry = it.next();
                resultMap.put(Bytes.toString(entry.getKey()), Bytes.toString(entry.getValue()));
            }
        } finally {
            IOUtils.closeQuietly(table);
        }
        return resultMap;
    }

    //根据rowkey和column获取数据
    public static String getValueBySeries(String rowKey, String column) throws IllegalArgumentException, IOException {
        Table table = null;
        String resultStr = null;
        try {
            table = conn.getTable(TableName.valueOf(TABLENAME));
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addColumn(Bytes.toBytes(SERIES), Bytes.toBytes(column));
            Result res = table.get(get);
            byte[] result = res.getValue(Bytes.toBytes(SERIES), Bytes.toBytes(column));
            resultStr = Bytes.toString(result);
        } finally {
            IOUtils.closeQuietly(table);
        }
        return resultStr;
    }

    //根据table查询所有数据
    public static void getValueByTable() throws Exception {
        Map<String, String> resultMap = null;
        Table table = null;
        try {
            table = conn.getTable(TableName.valueOf(TABLENAME));
            ResultScanner rs = table.getScanner(new Scan());
            for (Result r : rs) {
                System.out.println("获得到rowkey:" + new String(r.getRow()));
                for (KeyValue keyValue : r.raw()) {
                    System.out.println(
                            "列：" + new String(keyValue.getFamily()) + "====值:" + new String(keyValue.getValue()));
                }
            }
        } finally {
            IOUtils.closeQuietly(table);
        }
    }

    //删除表
    public static void dropTable(String tableName) throws IOException {
        Admin admin = null;
        TableName table = TableName.valueOf(tableName);
        try {
            admin = conn.getAdmin();
            if (admin.tableExists(table)) {
                admin.disableTable(table);
                admin.deleteTable(table);
            }
        } finally {
            IOUtils.closeQuietly(admin);
        }
    }
}
