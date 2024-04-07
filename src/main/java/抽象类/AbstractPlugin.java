/**
 * @Project: testDeomInSpring
 * @ClassName: AbstractPlugin
 * @Date: 2024年 03月 20日 9:11
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package 抽象类;

/**
 * @Description:
 * @Date: 2024年 03月 20日 9:11
 * @Author: MR.Yu
 **/
public abstract class AbstractPlugin implements Pluginable {

    public String getPeerPluginName() {
        return peerPluginName;
    }

    public void setPeerPluginName(String peerPluginName) {
        this.peerPluginName = peerPluginName;
    }

    private String peerPluginName;

    @Override
    public void init() {
        System.out.println("AbstractPlugin init");
    }

    @Override
    public void destroy() {
        System.out.println("AbstractPlugin destroy");
    }
}
