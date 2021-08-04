package com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr;

import com.alibaba.excel.annotation.ExcelProperty;
import com.thunisoft.sfxz.zfjdpt.zfba.annotation.ExcelKey;
import com.thunisoft.sfxz.zfjdpt.zfba.annotation.ExcelRule;
import com.thunisoft.sfxz.zfjdpt.zfba.constant.NCodeConsts;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @description 行政强制强制措施信息VO
 * @author huangyi-1
 * @date 2021-7-27 11:48
 * @version 1.0.0
 */
@Data
public class XzqzQzcsVO extends AjdrBaseVO{

    //    案号
    @ExcelKey
    @ExcelRule(name = "案号",type=String.class,max = 100)
    @ExcelProperty(index = 1)
    private String ah;

    //    是否属于监管事项目录
    @ExcelRule(name = "是否属于监管事项目录",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_SF)
    @ExcelProperty(index = 2)
    private String sfsyjgsxml;

    //    行政强制措施种类
    @ExcelRule(name = "行政强制措施种类",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_XZQZCSZL)
    @ExcelProperty(index = 3)
    private String xzqzcszl;

    //    行政强制措施种类（其他）
    @ExcelRule(name = "行政强制措施种类（其他）",type=String.class,max = 100)
    @ExcelProperty(index = 4)
    private String xzqzcszlqt;

    //    行政强制措施详情
    @ExcelRule(name = "行政强制措施详情",type=String.class,max = 200)
    @ExcelProperty(index = 5)
    private String xzqzcsxq;

    //当事人是否在场（查封）
    @ExcelRule(name = "当事人是否在场（查封）",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_SF)
    @ExcelProperty(index = 6)
    private String dsrsfzc;

    //见证人姓名（查封）
    @ExcelRule(name = "见证人姓名（查封）",type=String.class,max = 100)
    @ExcelProperty(index = 7)
    private String jzrxm;

    //联系方式（查封）
    @ExcelRule(name = "联系方式（查封）",type=String.class,max = 100)
    @ExcelProperty(index = 8)
    private String lxfs;

    //强制措施对象
    @ExcelRule(name = "强制措施对象",type=String.class,max = 100)
    @ExcelProperty(index = 9)
    private String qzcsdx;

    //设施、场所名称
    @ExcelRule(name = "设施、场所名称",type=String.class,max = 100)
    @ExcelProperty(index = 10)
    private String sscsmc;

    //设施、场所地址
    @ExcelRule(name = "设施、场所地址",type=String.class,max = 100)
    @ExcelProperty(index = 11)
    private String sscsdz;

    //财物名称、数量、金额（查封）
    @ExcelRule(name = "财物名称、数量、金额（查封）",type=String.class,max = 100)
    @ExcelProperty(index = 12)
    private String cwmcslje;

    //查封原因
    @ExcelRule(name = "查封原因",type=String.class,max = 200)
    @ExcelProperty(index = 13)
    private String cfyy;

    //当事人是否在场（扣押）
    @ExcelRule(name = "当事人是否在场（扣押）",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_SF)
    @ExcelProperty(index = 14)
    private String dsrsfzcky;

    //见证人姓名（扣押）
    @ExcelRule(name = "见证人姓名（扣押）",type=String.class,max = 100)
    @ExcelProperty(index = 15)
    private String jzrxmky;

    //联系方式（扣押）
    @ExcelRule(name = "联系方式（扣押）",type=String.class,max = 100)
    @ExcelProperty(index = 16)
    private String lxfsky;

    //财物名称、数量、金额（扣押）
    @ExcelRule(name = "财物名称、数量、金额（扣押）",type=String.class,max = 100)
    @ExcelProperty(index = 17)
    private String cwmcsljeky;

    //扣押原因
    @ExcelRule(name = "扣押原因",type=String.class,max = 200)
    @ExcelProperty(index = 18)
    private String cfyyky;

    //冻结金额（元）
    @ExcelRule(name = "冻结金额（元）",type=BigDecimal.class,max = 20)
    @ExcelProperty(index = 19)
    private String djje;

    //金额机构名称
    @ExcelRule(name = "金额机构名称",type=String.class,max = 100)
    @ExcelProperty(index = 20)
    private String jejgmc;

    //通知冻结日期
    @ExcelRule(name = "通知冻结日期",type=Date.class,max = 100)
    @ExcelProperty(index = 21)
    private String tzdjrq;

    //是否通知家属或者所在单位
    @ExcelRule(name = "是否通知家属或者所在单位",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_SF)
    @ExcelProperty(index = 22)
    private String sftzjshdw;

    //批准日期
    @ExcelRule(name = "批准日期（2021-1-1）",type=Date.class,max = 100)
    @ExcelProperty(index = 23)
    private String pzrq;

    //强制措施决定书文号
    @ExcelRule(name = "强制措施决定书文号",type=String.class,max = 100)
    @ExcelProperty(index = 24)
    private String qzcsjdwsh;

    //决定理由
    @ExcelRule(name = "决定理由",type=String.class,max = 200)
    @ExcelProperty(index = 25)
    private String jdly;

    //强制措施决定书送达日期
    @ExcelRule(name = "强制措施决定书送达日期（2021-1-1）",type= Date.class,max = 100)
    @ExcelProperty(index = 26)
    private String qzcsjdssdrq;

    //强制措施决定书送达方式
    @ExcelRule(name = "强制措施决定书送达方式",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_SDFS)
    @ExcelProperty(index = 27)
    private String qzcsjdssdfs;

    //强制措施期限起始日期
    @ExcelRule(name = "强制措施期限起始日期（2021-1-1）",type=Date.class,max = 100)
    @ExcelProperty(index = 28)
    private String qzcsqxqsrq;

    //强制措施期限截止日期
    @ExcelRule(name = "强制措施期限截止日期（2021-1-1）",type=Date.class,max = 100)
    @ExcelProperty(index = 29)
    private String qzcsqxjsrq;

    //实施强制措施日期
    @ExcelRule(name = "实施强制措施日期（2021-1-1）",type=Date.class,max = 100)
    @ExcelProperty(index = 30)
    private String ssqzcsrq;

    //是否延期
    @ExcelRule(name = "是否延期",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_SF)
    @ExcelProperty(index = 31)
    private String sfyq;

    //强制措施延长期限截止日期（2021-1-1）
    @ExcelRule(name = "强制措施延长期限截止日期（2021-1-1）",type=Date.class,max = 100)
    @ExcelProperty(index = 32)
    private String qzcsycjzrq;

    //实施强制措施结束日期（2021-1-1）
    @ExcelRule(name = "实施强制措施结束日期",type=Date.class,max = 100)
    @ExcelProperty(index = 33)
    private String ssqzcsjsrq;

    //强制措施处理决定
    @ExcelRule(name = "强制措施处理决定",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_QZCSCLJD)
    @ExcelProperty(index = 34)
    private String qzcscljd;

    //强制结果
    @ExcelRule(name = "强制结果",type=String.class,max = 100)
    @ExcelProperty(index = 35)
    private String qzjg;
}
