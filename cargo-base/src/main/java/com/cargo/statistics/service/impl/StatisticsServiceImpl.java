package com.cargo.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cargo.car.mapper.CarMapper;
import com.cargo.car.vo.CarInCountVo;
import com.cargo.statistics.entity.MultipleEntity;
import com.cargo.statistics.mapper.MultipleMapper;
import com.cargo.statistics.service.StatisticsService;
import com.cargo.statistics.vo.CountVo;
import com.cargo.statistics.vo.CountsVo;
import com.cargo.user.mapper.DriverInformationMapper;
import com.cargo.user.vo.DriverInCountVo;
import com.commom.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    CarMapper carMapper;

    @Autowired
    DriverInformationMapper driverInformationMapper;

    @Autowired
    MultipleMapper multipleMapper;



    /**
     * 统计注册的数量
     *
     * @param type
     * @return
     */
    @Override
    public CountsVo counts(String type) {
        CountsVo countsVo = new CountsVo();
        //查询时间段内新加的车辆
        //拿到当前时间的整点时间

        Thread car = new Thread(() -> {
            LocalDateTime dateTime = LocalDateTime.now();
            LocalDateTime start = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), dateTime.getHour(), 0, 0);
            List<CarInCountVo> list;
            List<CountVo> carCount = new ArrayList<>();
            CountVo countVo;
            if ("小时".equals(type)) {
                LocalDateTime end = start.minusHours(12);
//                list = carMapper.hours(start, end);
                          list = carMapper.hours(null, null);
                if (!ObjectUtils.isEmpty(list)) {
                    //根据起始时间算每两个中间的差距
                    //每次比较和上一个时间中的差距
                    System.out.println();
                    LocalDateTime nextTime = start;
                    for (CarInCountVo carInCountVo : list) {
                        LocalDateTime date = DateUtil.hoursToLocalDateTime(carInCountVo.getHour());
                        countVo = new CountVo();
                        countVo.setDay(Integer.valueOf(carInCountVo.getHour()));
                        countVo.setHourTime(date);
                        countVo.setCount(carInCountVo.getCount());
                        carCount.add(countVo);
                        Duration dur = Duration.between(date, nextTime);
                        //比较完后插入数据
                        if (dur.toHours() > 1) {
                            for (int i = 1; i < dur.toHours() + 1; i++) {
                                LocalDateTime dateTime1 = date.plusHours(i);
                                countVo = new CountVo();
                                countVo.setDay(Integer.valueOf(DateUtil.hoursToLocalDateTime(dateTime1)));
                                countVo.setHourTime(dateTime1);
                                countVo.setCount(0);
                                carCount.add(countVo);
                            }
                        }
                        nextTime = date;
                    }
                    //对list进行排序
                    carCount.sort((x, y) -> {
                        return x.getDayTime().compareTo(y.getDayTime());
                    });
                    countsVo.setCarCount(carCount);
                }
            } else if ("天".equals(type)) {
                LocalDateTime end = start.minusDays(12);
//                list = carMapper.days(start, end);
                list = carMapper.days(null, null);
                if (!ObjectUtils.isEmpty(list)) {
                    //根据起始时间算每两个中间的差距
                    //每次比较和上一个时间中的差距
                    LocalDate nextTime = start.toLocalDate();
                    for (CarInCountVo carInCountVo : list) {
                        LocalDate date = DateUtil.dayToLocalDateTime(carInCountVo.getDay());
                        countVo = new CountVo();
                        countVo.setDay(Integer.valueOf(carInCountVo.getDay()));
                        countVo.setDayTime(date);
                        countVo.setCount(carInCountVo.getCount());
                        carCount.add(countVo);
                        long l = nextTime.toEpochDay() - date.toEpochDay();
                        //比较完后插入数据
                        if (l > 1) {
                            for (int i = 1; i < l; i++) {
                                LocalDate dateTime1 = date.plusDays(i);
                                countVo = new CountVo();
                                countVo.setDay(Integer.valueOf(DateUtil.dayToLocalDateTime(dateTime1)));
                                countVo.setDayTime(dateTime1);
                                countVo.setCount(0);
                                carCount.add(countVo);
                            }
                        }
                        nextTime = date;
                    }
                    //对list进行排序
                    carCount.sort((x, y) -> {
                        return x.getDayTime().compareTo(y.getDayTime());
                    });
                    countsVo.setCarCount(carCount);
                }
            } else {
                start = LocalDateTime.now();
                LocalDateTime end = start.minusMonths(12);
                list = carMapper.months(null, null);
                if (!ObjectUtils.isEmpty(list)) {
                    //根据起始时间算每两个中间的差距
                    //每次比较和上一个时间中的差距
                    LocalDate nextTime = start.toLocalDate();
                    for (CarInCountVo carInCountVo : list) {
                        LocalDate date = DateUtil.mouthToLocalDateTime(carInCountVo.getMonth() + "01");
//                    LocalDate date = LocalDate.of(Integer.valueOf(carInCountVo.getMonth().substring(0,4)),Integer.valueOf(carInCountVo.getMonth().substring(4,6)),1);
                        countVo = new CountVo();
                        countVo.setDay(Integer.valueOf(carInCountVo.getMonth()));
                        countVo.setDayTime(date);
                        countVo.setCount(carInCountVo.getCount());
                        carCount.add(countVo);
                        Period period = Period.between(nextTime, date);
                        long l = period.getMonths();
                        System.out.println(l + "==============================");
                        //比较完后插入数据
                        if (l > 1) {
                            for (int i = 1; i < l; i++) {
                                LocalDate dateTime1 = date.plusMonths(i);
                                countVo = new CountVo();
                                countVo.setDay(Integer.valueOf(DateUtil.mouthToLocalDateTime(dateTime1)));
                                countVo.setMouthTime(dateTime1);
                                countVo.setCount(0);
                                carCount.add(countVo);
                            }
                        }
                        nextTime = date;
                    }
                    //对list进行排序
                    carCount.sort((x, y) -> {
                        return x.getDayTime().compareTo(y.getDayTime());
                    });
                    countsVo.setCarCount(carCount);
                }
            }
        }, "car");
        car.start();


        Thread user = new Thread(() -> {
            LocalDateTime dateTime = LocalDateTime.now();
            LocalDateTime start = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), dateTime.getHour(), 0, 0);
            List<DriverInCountVo> driverCount;
            List<CountVo> userCount = new ArrayList<>();
            CountVo countVo;
            if ("小时".equals(type)) {
                LocalDateTime end = start.minusHours(12);
//                driverCount = driverInformationMapper.hours(start, end);
                driverCount = driverInformationMapper.hours(null, null);
                if (!ObjectUtils.isEmpty(driverCount)) {
                    //根据起始时间算每两个中间的差距
                    //每次比较和上一个时间中的差距
                    System.out.println();
                    LocalDateTime nextTime = start;
                    for (DriverInCountVo driverInCountVo : driverCount) {
                        LocalDateTime date = DateUtil.hoursToLocalDateTime(driverInCountVo.getHour());
                        countVo = new CountVo();
                        countVo.setDay(Integer.valueOf(driverInCountVo.getHour()));
                        countVo.setHourTime(date);
                        countVo.setCount(driverInCountVo.getCount());
                        userCount.add(countVo);
                        Duration dur = Duration.between(date, nextTime);
                        //比较完后插入数据
                        if (dur.toHours() > 1) {
                            for (int i = 1; i < dur.toHours() + 1; i++) {
                                LocalDateTime dateTime1 = date.plusHours(i);
                                countVo = new CountVo();
                                countVo.setDay(Integer.valueOf(DateUtil.hoursToLocalDateTime(dateTime1)));
                                countVo.setHourTime(dateTime1);
                                countVo.setCount(0);
                                userCount.add(countVo);
                            }
                        }
                        nextTime = date;
                    }
                    //对list进行排序
                    userCount.sort((x, y) -> {
                        return x.getDayTime().compareTo(y.getDayTime());
                    });
                    countsVo.setUserCount(userCount);
                }
            } else if ("天".equals(type)) {
                LocalDateTime end = start.minusDays(12);
//                driverCount = driverInformationMapper.days(start, end);
                driverCount = driverInformationMapper.days(null, null);
                if (!ObjectUtils.isEmpty(driverCount)) {
                    //根据起始时间算每两个中间的差距
                    //每次比较和上一个时间中的差距
                    LocalDate nextTime = start.toLocalDate();
                    for (DriverInCountVo driverInCountVo : driverCount) {
                        LocalDate date = DateUtil.dayToLocalDateTime(driverInCountVo.getDay());
                        countVo = new CountVo();
                        countVo.setDay(Integer.valueOf(driverInCountVo.getDay()));
                        countVo.setDayTime(date);
                        countVo.setCount(driverInCountVo.getCount());
                        userCount.add(countVo);
                        long l = nextTime.toEpochDay() - date.toEpochDay();
                        //比较完后插入数据
                        if (l > 1) {
                            for (int i = 1; i < l; i++) {
                                LocalDate dateTime1 = date.plusDays(i);
                                countVo = new CountVo();
                                countVo.setDay(Integer.valueOf(DateUtil.dayToLocalDateTime(dateTime1)));
                                countVo.setDayTime(dateTime1);
                                countVo.setCount(0);
                                userCount.add(countVo);
                            }
                        }
                        nextTime = date;
                    }
                    //对list进行排序
                    userCount.sort((x, y) -> {
                        return x.getDayTime().compareTo(y.getDayTime());
                    });
                    countsVo.setUserCount(userCount);
                }
            } else {
                start = LocalDateTime.now();
                LocalDateTime end = start.minusMonths(12);
//                driverCount = driverInformationMapper.months(start, end);
                driverCount = driverInformationMapper.months(null, null);
                if (!ObjectUtils.isEmpty(driverCount)) {
                    //根据起始时间算每两个中间的差距
                    //每次比较和上一个时间中的差距
                    LocalDate nextTime = start.toLocalDate();
                    for (DriverInCountVo driverInCountVo : driverCount) {
                        LocalDate date = DateUtil.mouthToLocalDateTime(driverInCountVo.getMonth() + "01");
                        countVo = new CountVo();
                        countVo.setDay(Integer.valueOf(driverInCountVo.getMonth()));
                        countVo.setDayTime(date);
                        countVo.setCount(driverInCountVo.getCount());
                        userCount.add(countVo);
                        Period period = Period.between(nextTime, date);
                        long l = period.getMonths();
                        //比较完后插入数据
                        if (l > 1) {
                            for (int i = 1; i < l; i++) {
                                LocalDate dateTime1 = date.plusMonths(i);
                                countVo = new CountVo();
                                countVo.setDay(Integer.valueOf(DateUtil.mouthToLocalDateTime(dateTime1)));
                                countVo.setMouthTime(dateTime1);
                                countVo.setCount(0);
                                userCount.add(countVo);
                            }
                        }
                        nextTime = date;
                    }
                    //对list进行排序
                    userCount.sort((x, y) -> {
                        return x.getDayTime().compareTo(y.getDayTime());
                    });
                    countsVo.setUserCount(userCount);
                }
            }
        }, "user");
        user.start();
        try {
            car.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            user.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //查询需要翻得倍数和需要加的底数  造假需要
        QueryWrapper<MultipleEntity> multipleEntityQueryWrapper = new QueryWrapper<>();
        multipleEntityQueryWrapper.eq("is_start",1);
        List<MultipleEntity> entityList = multipleMapper.selectList(multipleEntityQueryWrapper);
        if (!ObjectUtils.isEmpty(entityList)) {
            for (MultipleEntity multipleEntity:entityList){
                if(1 == multipleEntity.getType()){
                    if (!ObjectUtils.isEmpty(countsVo.getCarCount())) {
                        List<CountVo> carCount = countsVo.getCarCount();
                        for (CountVo cc:carCount) {
                            int i = cc.getCount() + multipleEntity.getCount();
                            cc.setCount(i*multipleEntity.getMultiple());
                        }
                    }

                }
                if(2 == multipleEntity.getType()){
                    if (!ObjectUtils.isEmpty(countsVo.getCarCount())) {
                        List<CountVo> carCount = countsVo.getUserCount();
                        for (CountVo cc:carCount) {
                            int i = cc.getCount() + multipleEntity.getCount();
                            cc.setCount(i*multipleEntity.getMultiple());
                        }
                    }
                }
            }

        }
        return countsVo;
    }

    public static void main(String[] args) {
       LocalDateTime dateTime = LocalDateTime.now();
//        LocalDateTime of = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), dateTime.getHour(), 0,0);
//        System.out.println(of.minusHours(12));
//        System.out.println(of.minusMonths(12));
        LocalDateTime start = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), dateTime.getHour(), 0, 0);

        LocalDateTime date = DateUtil.hoursToLocalDateTime("2020110919");
        System.out.println(start.toString());
        System.out.println(date.toString());
        Duration dur= Duration.between( date,start );
        System.out.println(dur.toHours());
    }
}
