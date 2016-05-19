package createIbaits;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过数据库创建语句到ibaits描述文件
 * 1.字段名称
 * 2.字段类型
 *
 * PO类命名规则  tableName转成驼峰加PO
 * resultID   tableName转成驼峰+Result
 */
public class TableToIbaits {
    public static void main(String args[]){
        TableToIbaits tableToIbaits = new TableToIbaits();
        String sql ="CREATE TABLE `YY_BookingConfig_MtPush` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `mt_shop_id` int(11) DEFAULT NULL COMMENT '美团商家编号',\n" +
                "  `dp_shop_id` int(11) DEFAULT NULL COMMENT '点评商家编号',\n" +
                "  `created_time` datetime DEFAULT NULL COMMENT '创建时间',\n" +
                "  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',\n" +
                "  `lifetime` int(11) DEFAULT NULL COMMENT '重试生命周期',\n" +
                "  `booking_on` int(11) DEFAULT NULL COMMENT '是否开通预订,0关闭，1开通',\n" +
                "  `push_success` int(11) DEFAULT NULL COMMENT '推送是否成功 0失败  1成功',\n" +
                "  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',\n" +
                "  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `IX_mt_shop_id` (`mt_shop_id`),\n" +
                "  KEY `IX_dp_shop_id` (`dp_shop_id`),\n" +
                "  KEY `IX_updated_time` (`updated_time`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=125854 DEFAULT CHARSET=utf8;" ;
        SqlResolve sqlResolve = new SqlResolve();
        TableEntity tableEntity = sqlResolve.solve(sql);
        ClassCreator classCreator = new ClassCreator();
        ClassEntity classEntity = classCreator.createClassEntityFromTable(tableEntity);
        IbatiesMapperCreator creator = new IbatiesMapperCreator(classEntity,tableEntity);
        creator.startBuild("heheh","");
        creator.buildInsert("test");
        creator.endBuild();
        System.out.println(creator.getMapper());
        List<Integer> indexList = new ArrayList<Integer>();
        indexList.add(1); indexList.add(3);
        List<String> operators = new ArrayList<String>();
        WhereCondition condition = new WhereCondition();

    }





}
