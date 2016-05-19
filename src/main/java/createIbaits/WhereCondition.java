package createIbaits;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianrui03 on 16/5/16.
 */
public class WhereCondition {
    private List<Integer> judgeColumnIndexes ;
    private List<String>  judgeOperators;
    private List<Integer> inListColumnIndexes ;
    public WhereCondition(){
        judgeColumnIndexes = new ArrayList<Integer>();
        judgeOperators = new ArrayList<String>();
        inListColumnIndexes = new ArrayList<Integer>();
    }

    public List<Integer> getJudgeColumnIndexes() {
        return judgeColumnIndexes;
    }

    public void setJudgeColumnIndexes(List<Integer> judgeColumnIndexes) {
        this.judgeColumnIndexes = judgeColumnIndexes;
    }

    public List<String> getJudgeOperators() {
        return judgeOperators;
    }

    public void setJudgeOperators(List<String> judgeOperators) {
        this.judgeOperators = judgeOperators;
    }

    public List<Integer> getInListColumnIndexes() {
        return inListColumnIndexes;
    }

    public void setInListColumnIndexes(List<Integer> inListColumnIndexes) {
        this.inListColumnIndexes = inListColumnIndexes;
    }
}
