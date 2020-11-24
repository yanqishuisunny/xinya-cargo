package com.cargo.waybill.controller;

import com.cargo.waybill.entity.WaybillEntity;
import com.cargo.waybill.entity.WaybillStatusLogEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test {


    public static void main(String[] args) {
        WaybillStatusLogEntity waybillStatusLogEntity = new WaybillStatusLogEntity(); ;
//        List<WaybillStatusLogEntity> list = new ArrayList<>();
        Set<WaybillStatusLogEntity> list = new HashSet<>();
        for (int i = 0; i < 20 ; i++) {
            waybillStatusLogEntity.setWaybillId(i+"1111111");
            waybillStatusLogEntity.setWaybillNo(i+"1111111");
            waybillStatusLogEntity.setStatus(i);
           // waybillStatusLogEntity.setTrackingTime(LocalDateTime.now());
            list.add(waybillStatusLogEntity);
        }
        System.out.println(list.toString());
        for (int i = 0; i < list.size() ; i++) {

        }
    }
}



