package com.vmall.common;

import java.math.BigDecimal;

/**
 * Created by GV
 * DATE:2017/6/24
 * TIME:下午12:05
 */
public class BigDecimalUtil {



    public static final BigDecimal add(double v1,double v2){
        BigDecimal bigDecimal1=new BigDecimal(Double.toString(v1));
        BigDecimal bigDecimal2=new BigDecimal(Double.toString(v2));
        return bigDecimal1.add(bigDecimal2);
    }
    public static final BigDecimal sub(double v1,double v2){
        BigDecimal bigDecimal1=new BigDecimal(Double.toString(v1));
        BigDecimal bigDecimal2=new BigDecimal(Double.toString(v2));
        return bigDecimal1.subtract(bigDecimal2);
    }

    public static final BigDecimal mul(double v1,double v2){
        BigDecimal bigDecimal1=new BigDecimal(Double.toString(v1));
        BigDecimal bigDecimal2=new BigDecimal(Double.toString(v2));
        return bigDecimal1.multiply(bigDecimal2);
    }

    public static final BigDecimal div(double v1,double v2){
        BigDecimal bigDecimal1=new BigDecimal(Double.toString(v1));
        BigDecimal bigDecimal2=new BigDecimal(Double.toString(v2));
        return bigDecimal1.divide(bigDecimal2,2,BigDecimal.ROUND_HALF_UP);//四舍五入,保留2位小数
    }


    public static void main(String[] args) {
        System.out.println(BigDecimalUtil.add(1.22,2));
    }
}
