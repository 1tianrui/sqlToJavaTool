import com.alibaba.fastjson.JSON;
import createIbaits.SqlParam;
import createIbaits.SqlParamCode;
import createIbaits.WhereCondition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jnkmhbl on 16/5/18.
 */
public class TestClass {

    public static void main(String args[]){
        SqlParam  selectParam = new SqlParam();
        selectParam.setId("query");
        selectParam.setType(SqlParamCode.SELECT);

        SqlParam updateParam = new SqlParam();
        updateParam.setId("update");
        List<Integer> indexes = new ArrayList<Integer>();
        indexes.add(2); indexes.add(5); indexes.add(7);
        updateParam.setPreIndex(indexes);
        List<Integer> inIndexes = new ArrayList<Integer>();
        inIndexes.add(4);
        List<Integer> judgeIndexes = new ArrayList<Integer>();
        judgeIndexes.add(6);
        WhereCondition updateCondition = new WhereCondition();
        updateCondition.setInListColumnIndexes(inIndexes);
        updateCondition.setJudgeColumnIndexes(judgeIndexes);
        updateCondition.setJudgeOperators(Arrays.asList(">"));
        updateParam.setCondition(updateCondition);
        updateParam.setType(SqlParamCode.UPDATE);

        SqlParam insertParam = new SqlParam();
        insertParam.setType(SqlParamCode.INSERT);
        insertParam.setId("insert");

        List<SqlParam> params = new ArrayList<SqlParam>();
        params.add(selectParam);params.add(updateParam); params.add(insertParam);
        System.out.println(JSON.toJSONString(params));


    }
}
