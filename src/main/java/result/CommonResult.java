package result;

import com.alibaba.fastjson.JSON;

/**
 * Created by jnkmhbl on 16/5/18.
 */
public class CommonResult {
    private int code ;
    private String data ;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    private CommonResult(int code,String data){
        this.code = code;
        this.data = data;
    }

    public String toJSON(){
        return JSON.toJSONString(this);
    }

    public static CommonResult buildResult(int code, String data){
        return new CommonResult(code,data);
    }
}
