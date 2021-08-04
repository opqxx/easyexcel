package com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr;

import com.alibaba.excel.annotation.ExcelProperty;
import com.thunisoft.sfxz.zfjdpt.zfba.annotation.ExcelKey;
import com.thunisoft.sfxz.zfjdpt.zfba.annotation.ExcelRule;
import com.thunisoft.sfxz.zfjdpt.zfba.constant.NCodeConsts;
import lombok.Data;

import java.util.Date;

/**
 *
 * @description 行政强制当事人信息VO
 * @author huangyi-1
 * @date 2021-7-27 11:48
 * @version 1.0.0
 */
@Data
public class XzqzDsrxxVO extends AjdrBaseVO{

    //    案号
    @ExcelKey
    @ExcelRule(name = "案号",type=String.class,max = 100)
    @ExcelProperty(index = 1)
    private String ah;

    //    当事人类型
    @ExcelRule(name = "当事人类型",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_DSRLX)
    @ExcelProperty(index = 2)
    private String zfdxlb;

    //    当事人名称
    @ExcelRule(name = "当事人名称",type=String.class,max = 100)
    @ExcelProperty(index = 3)
    private String dsrmc;

    //    当事人证件类型
    @ExcelRule(name = "当事人证件类型",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_ZJLX)
    @ExcelProperty(index = 4)
    private String zjlx;

    //    当事人证件号码
    @ExcelRule(name = "当事人证件号码",type=String.class,max = 100)
    @ExcelProperty(index = 5)
    private String zjhm;

    //当事人联系电话
    @ExcelRule(name = "当事人联系电话",type=String.class,max = 100)
    @ExcelProperty(index = 6)
    private String lxdh;

    //出生日期
    @ExcelRule(name = "出生日期",type= Date.class,max = 100)
    @ExcelProperty(index = 7)
    private String csrq;

    //住所（住址）行政区划
    @ExcelRule(name = "住所（住址）行政区划",type=String.class,isXzqh=true)
    @ExcelProperty(index = 8)
    private String zsdxzqh;

    //住所（住址）
    @ExcelRule(name = "住所（住址）",type=String.class,max = 200)
    @ExcelProperty(index = 9)
    private String zsdz;

    //注册地址行政区划
    @ExcelRule(name = "注册地址行政区划",type=String.class,isXzqh=true)
    @ExcelProperty(index = 10)
    private String zcdxzqh;

    //注册地址
    @ExcelRule(name = "注册地址",type=String.class,max = 200)
    @ExcelProperty(index = 11)
    private String zcdz;

    //经营地址行政区划
    @ExcelRule(name = "经营地址行政区划",type=String.class,isXzqh=true)
    @ExcelProperty(index = 12)
    private String jydxzqh;

    //经营地址
    @ExcelRule(name = "经营地址",type=String.class,max = 200)
    @ExcelProperty(index = 13)
    private String jydz;
}
