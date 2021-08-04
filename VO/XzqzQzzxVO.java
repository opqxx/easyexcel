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
 * @description 行政强制强制执行信息VO
 * @author huangyi-1
 * @date 2021-7-27 11:48
 * @version 1.0.0
 */
@Data
public class XzqzQzzxVO extends AjdrBaseVO{

    //    案号
    @ExcelKey
    @ExcelRule(name = "案号",type=String.class,max = 100)
    @ExcelProperty(index = 1)
    private String ah;

    //    是否属于监管事项目录
    @ExcelRule(name = "是否属于监管事项目录",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_SF)
    @ExcelProperty(index = 2)
    private String sfsyjgsxml;

    //    行政强制执行方式
    @ExcelRule(name = "行政强制执行方式",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_XZQZZXFS)
    @ExcelProperty(index = 3)
    private String xzqzzxfs;

    //   行政强制执行方式（其他）
    @ExcelRule(name = "行政强制执行方式（其他）",type=String.class,max = 100)
    @ExcelProperty(index = 4)
    private String xzqzzxfsqt;

    //    催告书送达日期（2021-1-1）
    @ExcelRule(name = "催告书送达日期",type= Date.class,max = 100)
    @ExcelProperty(index = 5)
    private String cgssdrq;

    //催告书送达方式
    @ExcelRule(name = "催告书送达方式",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_SDFS)
    @ExcelProperty(index = 6)
    private String cgssdfs;

    //原决定缴纳金额（元）
    @ExcelRule(name = "原决定缴纳金额（元）",type=Double.class,max = 20)
    @ExcelProperty(index = 7)
    private String yjdjnje;

    //原决定缴纳截止日期（2021-1-1）
    @ExcelRule(name = "原决定缴纳截止日期",type=Date.class,max = 100)
    @ExcelProperty(index = 8)
    private String yjdjnjzrq;

    //加处罚款或滞纳金额（元）
    @ExcelRule(name = "加处罚款或滞纳金额（元）",type=Double.class,max = 20)
    @ExcelProperty(index = 9)
    private String jcfkhznje;

    //二次催告书送达日期（2021-1-1）
    @ExcelRule(name = "二次催告书送达日期",type=Date.class,max = 100)
    @ExcelProperty(index = 10)
    private String eccgssdrq;

    //二次催告书送达方式
    @ExcelRule(name = "二次催告书送达方式",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_SDFS)
    @ExcelProperty(index = 11)
    private String eccgssdfs;

    //是否申请法院强制执行
    @ExcelRule(name = "是否申请法院强制执行",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_SF)
    @ExcelProperty(index = 12)
    private String sfsqfyqzzx;

    //批准日期（2021-1-1）
    @ExcelRule(name = "批准日期",type=Date.class,max = 100)
    @ExcelProperty(index = 13)
    private String pzrq;

    //强制执行决定书文号
    @ExcelRule(name = "强制执行决定书文号",type=String.class,max = 100)
    @ExcelProperty(index = 14)
    private String qzzxjdwsh;

    //决定理由
    @ExcelRule(name = "决定理由",type=String.class,max = 200)
    @ExcelProperty(index = 15)
    private String jdly;

    //强制执行决定书送达日期（2021-1-1）
    @ExcelRule(name = "强制执行决定书送达日期",type=Date.class,max = 100)
    @ExcelProperty(index = 16)
    private String qzzxjdssdrq;

    //强制执行决定书送达方式
    @ExcelRule(name = "强制执行决定书送达方式",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_SDFS)
    @ExcelProperty(index = 17)
    private String qzzxjdssdfs;

    //行政强制执行详情
    @ExcelRule(name = "行政强制执行详情",type=String.class,max = 300)
    @ExcelProperty(index = 18)
    private String xzqzzxxq;

    //划拨金额
    @ExcelRule(name = "划拨金额",type=BigDecimal.class,max = 20)
    @ExcelProperty(index = 19)
    private String hbje;

    //划拨金融机构名称
    @ExcelRule(name = "划拨金融机构名称",type=String.class,max = 100)
    @ExcelProperty(index = 20)
    private String hbjejrjgmc;

    //划拨通知书送达日期（2021-1-1）
    @ExcelRule(name = "划拨通知书送达日期",type=Date.class,max = 100)
    @ExcelProperty(index = 21)
    private String hbtzssdrq;

    //拍卖地点
    @ExcelRule(name = "拍卖地点",type=String.class,max = 100)
    @ExcelProperty(index = 22)
    private String pmdd;

    //拍卖日期（2021-1-1）
    @ExcelRule(name = "拍卖日期",type=Date.class,max = 100)
    @ExcelProperty(index = 23)
    private String pmrq;

    //拍卖所得处理
    @ExcelRule(name = "拍卖所得处理",type=String.class,max = 100)
    @ExcelProperty(index = 24)
    private String pmsdcl;

    //代履行人
    @ExcelRule(name = "代履行人",type=String.class,max = 100)
    @ExcelProperty(index = 25)
    private String dlxr;

    //代履行费用
    @ExcelRule(name = "代履行费用",type=BigDecimal.class,max = 20)
    @ExcelProperty(index = 26)
    private String dlxfy;

    //监督人
    @ExcelRule(name = "监督人",type=String.class,max = 30)
    @ExcelProperty(index = 27)
    private String jdr;

    //代履行日期
    @ExcelRule(name = "代履行日期",type=Date.class,max = 100)
    @ExcelProperty(index = 28)
    private String dlxrq;

    //是否中止执行
    @ExcelRule(name = "是否中止执行",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_SF)
    @ExcelProperty(index = 29)
    private String sfzz;

    //中止执行日期
    @ExcelRule(name = "中止执行日期",type=Date.class,max = 100)
    @ExcelProperty(index = 30)
    private String zzzxrq;

    //中止执行原因
    @ExcelRule(name = "中止执行原因",type=String.class,max = 1000)
    @ExcelProperty(index = 31)
    private String zzzxyy;

    //恢复执行日期（2021-1-1）
    @ExcelRule(name = "恢复执行日期",type=Date.class,max = 100)
    @ExcelProperty(index = 32)
    private String hfzxrq;

    //实施强制执行日期（2021-1-1）
    @ExcelRule(name = "实施强制执行日期",type=Date.class,max = 100)
    @ExcelProperty(index = 33)
    private String ssqzzxrq;

    //实施强制执行结束日期（2021-1-1）
    @ExcelRule(name = "实施强制执行结束日期",type=Date.class,max = 100)
    @ExcelProperty(index = 34)
    private String ssqzjsrq;

    //是否执行协议
    @ExcelRule(name = "是否执行协议",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_SF)
    @ExcelProperty(index = 35)
    private String sfzxxy;

    //协议签订日期（2021-1-1）
    @ExcelRule(name = "协议签订日期",type=Date.class,max = 100)
    @ExcelProperty(index = 36)
    private String xyqdrq;

    //协议内容
    @ExcelRule(name = "协议内容",type=String.class,max = 10000)
    @ExcelProperty(index = 37)
    private String xynr;

    //协议执行情况
    @ExcelRule(name = "协议执行情况",type=String.class,max = 1000)
    @ExcelProperty(index = 38)
    private String xyzxqk;

    //是否执行回转
    @ExcelRule(name = "是否执行回转",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_SF)
    @ExcelProperty(index = 39)
    private String sfzxhz;

    //执行回转日期（2021-1-1）
    @ExcelRule(name = "执行回转日期",type=Date.class,max = 100)
    @ExcelProperty(index = 40)
    private String zxhzrq;

    //执行回转原因
    @ExcelRule(name = "执行回转原因",type=String.class,max = 1000)
    @ExcelProperty(index = 41)
    private String zxhzyy;

    //执行回转情况
    @ExcelRule(name = "执行回转情况",type=String.class,max = 100)
    @ExcelProperty(index = 42)
    private String zxhzqk;

    //是否终结执行
    @ExcelRule(name = "是否终结执行",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_SF)
    @ExcelProperty(index = 43)
    private String sfzjzx;

    //终结执行原因
    @ExcelRule(name = "终结执行原因",type=String.class,max = 1000,codeType= "30050")
    @ExcelProperty(index = 44)
    private String zjzxyy;

    //终结通知书送达日期（2021-1-1）
    @ExcelRule(name = "终结通知书送达日期",type=Date.class,max = 100)
    @ExcelProperty(index = 45)
    private String zjtzssdrq;

    //终结通知书送达方式
    @ExcelRule(name = "终结通知书送达方式",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_SDFS)
    @ExcelProperty(index = 46)
    private String zjtzssdfs;

    //强制结果
    @ExcelRule(name = "强制结果",type=String.class,max = 100)
    @ExcelProperty(index = 47)
    private String qzjg;
}
