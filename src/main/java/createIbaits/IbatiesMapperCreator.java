package createIbaits;

import org.springframework.cglib.core.CollectionUtils;

import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.List;

/**
 * Created by jnkmhbl on 16/5/16.
 */
public class IbatiesMapperCreator {
    private ClassEntity classEntity;
    private TableEntity tableEntity;
    private StringBuilder mapper;

    public StringBuilder getMapper() {
        return mapper;
    }

    public void setMapper(StringBuilder mapper) {
        this.mapper = mapper;
    }

    public IbatiesMapperCreator startBuild(String id,String fullPath){
       mapper.append(this.createHeader()+"\r\n");
       mapper.append(this.createResultMap(id,fullPath)+"\r\n");
       return this;
    }

    public IbatiesMapperCreator endBuild(){
        mapper.append("</sqlMap>");
        return this;
    }
    public IbatiesMapperCreator(ClassEntity classEntity, TableEntity tableEntity){
        this.classEntity = classEntity;
        this.tableEntity = tableEntity;
        mapper = new StringBuilder();
    }
    private  String createHeader(){
        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE sqlMap PUBLIC \"-//ibatis.apache.org//DTD SQL Map 2.0//EN\" \"http://ibatis.apache.org/dtd/sql-map-2.dtd\">\n" +
                "<sqlMap namespace=\""+classEntity.getClassName()+"\">";
        return header;
    }

    public IbatiesMapperCreator buildInsert(String id){
          mapper.append(this.createInsert(id));
          return this ;
    }
    public IbatiesMapperCreator buildUpdate(String id,List<Integer> updateIndexes,WhereCondition condition){
          mapper.append(this.createUpdate(id, updateIndexes, condition));
          return this;
    }
    public IbatiesMapperCreator buildSelect(String reusltType,String selectID,List<Integer> selectResultIndexes,WhereCondition condition,boolean pageAble){
         mapper.append(this.createSelect(selectID, selectResultIndexes, condition, pageAble,reusltType ));
         return this;
    }
    private  String createResultMap(String id,String classFullPath){
        StringBuilder builder = new StringBuilder();
        builder.append("<resultMap> id=\""+id+"\"  class=\""+classFullPath+"\">\r\n");
        for(int i =0 ;i<tableEntity.getColumn().size();i++){
            builder.append("<result column=\""+classEntity.getAttributeMap().get(i).getName()+"\" property=\""+tableEntity.getColumn().get(i).getName()+"\"/>\r\n");
        }
        builder.append("</resultMap>");
        return builder.toString();
    }
    private String createWhere(WhereCondition condition){

        List<Integer> compareIndexes = condition.getJudgeColumnIndexes();
        List<String> compartor =  condition.getJudgeOperators();
        List<Integer> inListIndexes = condition.getInListColumnIndexes();
        if((compareIndexes==null||compareIndexes.size()==0)&&(compartor==null || compartor.size()==0)&&(inListIndexes==null ||inListIndexes.size() == 0)){
            return "";
        }
        List<ColumnEntry> tableColumns = tableEntity.getColumn();
        List<ColumnEntry> classColumns = classEntity.getAttributeMap();
        StringBuilder builder = new StringBuilder();
        builder.append("where"+"\r\n");
        boolean addAnd = false;
        if(compareIndexes!=null && compareIndexes.size()>0)
        for(int i=0;i<compareIndexes.size();i++){
            int index = compareIndexes.get(i);
            builder.append(" "+tableColumns.get(index).getName()+""+compartor.get(i)+"#"+classColumns.get(index).getName()+"# ");
            if(i<(compareIndexes.size()-1)){
                builder.append(" AND "+"\r\n");
            }
            addAnd = true;
        }
        builder.append("\r\n");
        if(inListIndexes!=null && inListIndexes.size() > 0)
        for(int i=0;i<inListIndexes.size();i++){
            if(addAnd){
                builder.append("AND    ");
            }
            int index =  inListIndexes.get(i);
            builder.append("<iterate propertry=\""+classColumns.get(index).getName()+"List"+"\""+" open=\"(\" close=\")\" conjunction=\",\">"
                    +"#"+tableColumns.get(index).getName()+"List[]#"+
            "</iterate>"+"\r\n");
        }
        return builder.toString();
    }

    private String createSelect(String selectID,List<Integer> selectResultIndexes,WhereCondition condition,boolean pageAble,String resultType){
        List<ColumnEntry> tableColumns = tableEntity.getColumn();
        List<ColumnEntry> classColumns = classEntity.getAttributeMap();

        StringBuilder selectBuilder = new StringBuilder();
        selectBuilder.append("<select id=\""+selectID+"\" parameterClass=\"map\" resultMap=\"" + resultType + "\"\r\n");
        StringBuilder selectContent = new StringBuilder();
        for(int i = 0;i < tableColumns.size() ;i++){
            selectContent.append(tableColumns.get(i).getName());
            if(i < tableColumns.size()-1){
                selectContent.append(" , ");
            }
            if(i<selectResultIndexes.size()-1){
                selectContent.append(",");
            }
        }
        selectBuilder.append("SELECT "+selectContent+" from "+tableEntity.getTableName()+"\r\n");
        selectBuilder.append(createWhere(condition));
        if(pageAble) {
            selectBuilder.append(" limit start=#start# ,pageSize=#pageSize#");
        }
        return selectBuilder.toString();
    }

    private String createInsert(String id){
        StringBuilder insertBuilder = new StringBuilder();
        insertBuilder.append("<insert id=\""+id+"\" paramClass=\""+classEntity.getClassName()+"\">\r\n");
        insertBuilder.append("INSERT INTO "+tableEntity.getTableName()+"(");
        List<ColumnEntry> keys = tableEntity.getColumn();
        for(int i=1;i<keys.size();i++){
            String columnName = keys.get(i).getName() ;
            if(!columnName.equalsIgnoreCase("id")) {
                insertBuilder.append(columnName);
            }else{
                continue ;
            }
            if(i < keys.size() -1){
                insertBuilder.append(",");
            }
        }
        insertBuilder.append(")\r\n");
        insertBuilder.append("VALUES (");
        List<ColumnEntry> classEntries = classEntity.getAttributeMap();
        for(int i=1;i<keys.size();i++){
            String javaEntry = classEntries.get(i).getName();
            if(!javaEntry.equalsIgnoreCase("id")) {
                insertBuilder.append("#" + javaEntry + "#");
            }else{
                continue;
            }
            if(i < keys.size() -1){
                insertBuilder.append(",");
            }
        }
        insertBuilder.append(")\r\n");
        insertBuilder.append("</insert>");
        return insertBuilder.toString();
    }

    private String createUpdate(String id,List<Integer> updateIndexes,WhereCondition condition){
        StringBuilder updateBuilder = new StringBuilder();
        updateBuilder.append("<update id=\""+id+"\" parameterClass=\"map\">\r\n");
        updateBuilder.append("UPDATE "+classEntity.getClassName()+"\r\n");
        updateBuilder.append("set\r\n");
        List<ColumnEntry> tableColumns = tableEntity.getColumn();
        List<ColumnEntry> classColumns = classEntity.getAttributeMap();

        for( int i=0;i< updateIndexes.size();i++){
            int index = updateIndexes.get(i);
            updateBuilder.append(tableColumns.get(index).getName()+" = #"+classColumns.get(index).getName()+"#\r\n");
        }
        updateBuilder.append(createWhere(condition));
        updateBuilder.append("</update>");

        return updateBuilder.toString();

    }

    public void wirteToFile(String fileName)throws Exception{
        File file = new File(fileName);
        FileWriter writer = new FileWriter(file);
        writer.write(this.mapper.toString().toCharArray());
        writer.flush();
        writer.close();
    }



}
