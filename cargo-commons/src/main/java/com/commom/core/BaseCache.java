package com.commom.core;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;



public abstract class BaseCache<K,V> implements ICache<K, V>, InitializingBean, DisposableBean {

    /**
     * 上下文对象
     */
    private Context context = null;


    public Context getContext() {
        return this.context;
    }

    protected void createContext() {
        context = new Context(this);
    }

    /**
     * 初始化方法
     */
    public void afterPropertiesSet() throws Exception {
        this.createContext();
    }

    /**
     * 销毁方法
     */
    @Override
    public void destroy() throws Exception {

    }
}

