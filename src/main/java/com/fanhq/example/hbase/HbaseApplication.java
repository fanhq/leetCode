package com.fanhq.example.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fanhaiqiu
 * @date 2019/9/29
 */
public class HbaseApplication {

    private static Admin admin;

    public static void main(String[] args) throws Exception {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "10.19.3.194,10.19.3.195,10.19.3.196");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        Connection connection = ConnectionFactory.createConnection(configuration);
        Admin admin = connection.getAdmin();
        TableName tableName = TableName.valueOf("testtable");
        System.out.println(admin.tableExists(tableName));
        admin.close();
        connection.close();
    }


    public static Connection initHbase() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "10.19.3.194:2181");
        Connection connection = ConnectionFactory.createConnection(configuration);
        return connection;
    }

    public static void createTable(String tableNmae, String[] cols) throws IOException {

        TableName tableName = TableName.valueOf(tableNmae);
        admin = initHbase().getAdmin();
        if (admin.tableExists(tableName)) {
            System.out.println("表已存在！");
        } else {
            HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
            for (String col : cols) {
                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(col);
                hTableDescriptor.addFamily(hColumnDescriptor);
            }
            admin.createTable(hTableDescriptor);
        }
    }

    public static void insertData(String tableName, User user) throws IOException {
        TableName tablename = TableName.valueOf(tableName);
        Put put = new Put(("user-" + user.getId()).getBytes());
        //参数：1.列族名  2.列名  3.值
        put.addColumn("information".getBytes(), "username".getBytes(), user.getUsername().getBytes());
        put.addColumn("information".getBytes(), "age".getBytes(), user.getAge().getBytes());
        put.addColumn("information".getBytes(), "gender".getBytes(), user.getGender().getBytes());
        put.addColumn("contact".getBytes(), "phone".getBytes(), user.getPhone().getBytes());
        put.addColumn("contact".getBytes(), "email".getBytes(), user.getEmail().getBytes());
        //HTable table = new HTable(initHbase().getConfiguration(),tablename);已弃用
        Table table = initHbase().getTable(tablename);
        table.put(put);
    }

    public static List<User> getAllData(String tableName) {

        Table table = null;
        List<User> list = new ArrayList<User>();
        try {
            table = initHbase().getTable(TableName.valueOf(tableName));
            ResultScanner results = table.getScanner(new Scan());
            User user = null;
            for (Result result : results) {
                String id = new String(result.getRow());
                System.out.println("用户名:" + new String(result.getRow()));
                user = new User();
                for (Cell cell : result.rawCells()) {
                    String row = Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
                    String colName = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
                    String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    user.setId(row);
                    if (colName.equals("username")) {
                        user.setUsername(value);
                    }
                    if (colName.equals("age")) {
                        user.setAge(value);
                    }
                    if (colName.equals("gender")) {
                        user.setGender(value);
                    }
                    if (colName.equals("phone")) {
                        user.setPhone(value);
                    }
                    if (colName.equals("email")) {
                        user.setEmail(value);
                    }
                }
                list.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
