package createIbaits;

import org.springframework.cglib.core.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jnkmhbl on 16/7/30.
 */
public class SqlToMapperTranslater {
    private String createTableSql  ;
    private List<String> sqls ;
    private IbatiesMapperCreator creator ;
    private ClassEntity classEntity ;
    private TableEntity tableEntity ;
    private  List<String>  javaMapList = new ArrayList<String>();


    public SqlToMapperTranslater(String createTableSql,List<String> sqls, String fullPath){
        this.createTableSql = createTableSql ;
        this.sqls = sqls ;
        SqlResolve sqlResolve = new SqlResolve();
        tableEntity = sqlResolve.solve(createTableSql);
        ClassCreator classCreator = new ClassCreator();
        classEntity = classCreator.create(tableEntity);
        creator = new IbatiesMapperCreator(classEntity,tableEntity);
        this.generateMapper(fullPath);
    }

    private void generateMapper(String fullPath){
        creator.startBuild("编号",fullPath );
        for(String sql : sqls){
           int type = getType(sql);
           if(type  < 0){
               System.out.println("error sql type");
           }
            if(type == SqlParamCode.INSERT){
                 creator.buildInsert("写入插入编号");
            }
            if(type ==  SqlParamCode.UPDATE){
                List<Integer> updateIndexes = this.getFieldsToDeal(sql);
                WhereCondition whereCondition = buildWhereCondition(sql) ;
                creator.buildUpdate("写入updateid", updateIndexes, whereCondition);
                javaMapList.add(buildJavaMap(updateIndexes,whereCondition));
            }
            if(type == SqlParamCode.SELECT){
                WhereCondition whereCondition = buildWhereCondition(sql) ;
                boolean pageAble = sql.contains("limit");
                creator.buildSelect(classEntity.getClassName(),"写入select编号",null,whereCondition,pageAble);
                javaMapList.add(buildJavaMap(null,whereCondition,pageAble));
            }
        }
        creator.endBuild();
    }

    private String buildJavaMap(List<Integer> indexList,WhereCondition condition){
        StringBuilder builder = new StringBuilder();
        builder.append("\r\n\r\nMap<String,Object> param = new HashMap<String,Object>();\r\n");
        if(indexList != null && indexList.size() != 0){
            for(int index :indexList){
                builder.append("param.put(\""+classEntity.getAttributeMap().get(index).getName()+"\",###);\r\n");
            }
        }
        List<Integer> judgeIndexList =  condition.getJudgeColumnIndexes();
        if(judgeIndexList != null && judgeIndexList.size() != 0){
            for(int index :judgeIndexList){
                builder.append("param.put(\""+classEntity.getAttributeMap().get(index).getName()+"\",###);\r\n");
            }
        }

        List<Integer> inIndexLit = condition.getInListColumnIndexes();
        if(inIndexLit != null && inIndexLit.size() != 0){
            for(int index :inIndexLit){
                builder.append("param.put(\""+classEntity.getAttributeMap().get(index).getName()+"\",###);\r\n");
            }
        }
        return builder.toString();
    }

    private String buildJavaMap(List<Integer> indexList,WhereCondition condition,boolean pageAble){
       String content = this.buildJavaMap(indexList, condition);
        if(pageAble)
        content = content +"map.put(\"pageNum\",###);\r\nmap.put(\"pageSize\",###)";
        return content;
    }
    private WhereCondition buildWhereCondition(String sql){
         int whereIndex = sql.indexOf("where")+5;
         String whereFields = sql.substring(whereIndex, sql.length() - 1);
        List<String> operators = new ArrayList<String>();
        List<Integer> columns = new ArrayList<Integer>();
        List<Integer> inColumns = new ArrayList<Integer>();
        int index =0 ;
        for(ColumnEntry entry : tableEntity.getColumn()) {
            if (!whereFields.contains(entry.getName())) {
                continue;
            }
            int lastIndex = TrStringHelper.containsName(whereFields, entry.getName());
            if (lastIndex < 0)
                continue;
            int columnIndex = lastIndex + 1;
            while (true) {
                if (whereFields.charAt(columnIndex) == ' ')
                    columnIndex++;
                else
                    break;
            }
            int endIndex = columnIndex + 4;
            if (columnIndex + 4 > whereFields.length() - 1) {
                endIndex = whereFields.length();
            }
            String operator = whereFields.substring(columnIndex, endIndex);

            if (operator.contains(">=")) {
                operators.add(">=");
            } else if (operator.contains("<=")) {
                operators.add("<=");
            } else if (operator.contains("=")) {
                operators.add("=");
            } else if (operator.contains("<")) {
                operators.add("<");
            } else if (operator.contains(">")) {
                operators.add(">");
            } else if (operator.contains("in")) {
                inColumns.add(index);
                continue;
            } else {
                System.out.println("operator parse error");
            }
            columns.add(index);

            index++;
        }

        WhereCondition condition = new WhereCondition();
        condition.setJudgeOperators(operators);
        condition.setInListColumnIndexes(inColumns);
        condition.setJudgeColumnIndexes(columns);
        return condition ;
    }
    private List<Integer> getFieldsToDeal(String sql){
        List<Integer> indexList = new ArrayList<Integer>();
        int whereIndex = sql.indexOf("where");
        int setIndex = sql.indexOf("set")+3;
        String updateFields = sql.substring(setIndex, whereIndex);
        List<ColumnEntry> entries = tableEntity.getColumn();

        int index =0 ;
        for(ColumnEntry entry : entries){
            if(TrStringHelper.containsName(updateFields, entry.getName()) > 0){
                indexList.add(index);
            }
            index ++;
        }

        return indexList;
    }

    public void printJavaBean(){
        StringBuilder builder = new StringBuilder(1024);
        builder.append("public class " + classEntity.getClassName() + "{\r\n");
        List<ColumnEntry> entries = classEntity.getAttributeMap();
        for(ColumnEntry entry :entries){
           builder.append("private "+entry.getType()+"  "+ entry.getName()+";\r\n");
        }
        builder.append("}");
        System.out.println(builder.toString());
    }

    private int getType(String sql){
        if(sql.contains("insert")){
            return SqlParamCode.INSERT;
        }
        else if(sql.contains("select")){
            return SqlParamCode.SELECT ;
        }
        else if(sql.contains("update")){
            return SqlParamCode.UPDATE ;
        }
        return  -1 ;
    }



    public void printIbatisMapper(){
       System.out.println(creator.getMapper());
    }


    public void printJavaMap(){
      System.out.println(javaMapList.toString());
    }

}
