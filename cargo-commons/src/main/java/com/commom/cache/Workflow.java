package com.commom.cache;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author 梁建军
 * 创建日期： 2019/8/21
 * 创建时间： 13:42
 * @version 1.0
 * @since 1.0
 */
public class Workflow<T> {


    /**
     * 允许下跳状态
     */
    private Set<Workflow> next = new HashSet<>();

    /**
     * 订单状态
     */
    private final T t;

    private boolean cancel = false;

    private final Map<T, Workflow<T>> workflowMap;
    /**
     * 是否允许跳过
     */
    private boolean skip = false;

    private Workflow(Map<T, Workflow<T>> workflowMap, T t) {
        this.workflowMap = workflowMap;
        this.t = t;
        workflowMap.put(t, this);

    }

    public static <T> Workflow<T> get(Map<T, Workflow<T>> workflowMap, T t) {
        Workflow<T> workflow = workflowMap.get(t);
        if (workflow == null) {
            workflow = new Workflow<>(workflowMap, t);
        }
        return workflow;
    }

    public boolean isNext(T orderStatus) {
        final Workflow workflow = workflowMap.get(orderStatus);
        return next.contains(workflow);
    }


    public Workflow<T> setNext(T orderStatus) {
        Workflow<T> workflow = workflowMap.get(orderStatus);
        if (workflow == null) {
            workflow = new Workflow<>(workflowMap, orderStatus);
            workflowMap.put(orderStatus, workflow);
        }
        next.add(workflow);
        return workflow;
    }

    public Workflow<T> addNext(T orderStatus) {
        setNext(orderStatus);
        return this;
    }

    public boolean isCancel() {
        return cancel;
    }

    public Workflow setCancel(boolean cancel) {
        this.cancel = cancel;
        return this;
    }

    public Set<Workflow> getNext() {
        return next;
    }

    public T get() {
        return t;
    }
}
