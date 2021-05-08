package zzTest;

import java.util.Date;

/**
 * @author user
 * @Description
 * @create 2021-05-07 9:49
 * <p>
 * 模拟缓存类 实现
 */
public class MsgCache {

    public static MsgCache instance = new MsgCache();                  //在别的地方调用它的时候才会初始化

    private String msg = "缓存信息";

    //构造器私有
    private MsgCache() {
        System.out.println("进入构造方法了");
        //缓存初始化操作
        initMsg();
    }

    public void initMsg() {
        this.msg="重新缓存后的信息-"+ new Date().toLocaleString();

        System.out.println("刷新成功！");
        System.out.println(this.msg);
    }

}
