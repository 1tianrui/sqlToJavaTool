package createIbaits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 通过数据库创建语句到ibaits描述文件
 * 1.字段名称
 * 2.字段类型
 *
 * PO类命名规则  tableName转成驼峰加PO
 * resultID   tableName转成驼峰+Result
 *
 *
 * 局限性，where 后的条件语句每个字段只能出现一次 ; 单个判断
 */
public class Test {
    public static void main(String args[]){
        String sql ="CREATE TABLE `YU_User_Coupon_Log` (\n" +
                "  `user_id` int(11) DEFAULT NULL,\n" +
                "  `record_id` int(11) DEFAULT NULL,\n" +
                "  `coupon_id` varchar(256) DEFAULT NULL,\n" +
                "  `phone` varchar(56) DEFAULT NULL,\n" +
                "  `activity_id` int(11) DEFAULT NULL,\n" +
                "  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',\n" +
                "  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',\n" +
                "  `create_time` datetime NOT NULL COMMENT '创建时间',\n" +
                "  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
                "  `coupon_group_id` varchar(256) DEFAULT NULL,\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  UNIQUE KEY `id` (`id`),\n" +
                "  UNIQUE KEY `user_id` (`user_id`,`activity_id`),\n" +
                "  KEY `phone` (`phone`)\n" +
                ") " ;
        List<String> sqls = new ArrayList<String>();
        sqls.add("insert");
        sqls.add("select * from YU where phone =  and created_time >  ");

        sqls.add("update table set coupon_id =  where activity_id =  and user_id = ");
        SqlToMapperTranslater toMapperTranslater = new SqlToMapperTranslater(sql,sqls,"全路径名称");
        toMapperTranslater.printJavaMap();
        toMapperTranslater.printIbatisMapper();
        toMapperTranslater.printJavaBean();
    }





}
