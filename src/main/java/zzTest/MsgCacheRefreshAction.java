package zzTest;

import org.junit.Test;

/**
 * @author user
 * @Description
 * @create 2021-05-07 9:55
 * <p>
 * 模拟更新缓存的接口
 */
public class MsgCacheRefreshAction {

    private int flag = 412;


    //模拟接口
    @Test
    public void refresh() {
        refreshCache(flag);
    }


    private void refreshCache(int flag) {
        if (this.flag == 412) {
            //MsgCache.instance.initMsg();
            System.out.println(MsgCache.instance);
        }
    }


}
