package com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr;

import com.alibaba.excel.annotation.ExcelProperty;
import com.thunisoft.sfxz.zfjdpt.zfba.annotation.ExcelKey;
import com.thunisoft.sfxz.zfjdpt.zfba.annotation.ExcelRule;
import com.thunisoft.sfxz.zfjdpt.zfba.constant.NCodeConsts;
import lombok.Data;

import java.util.Date;

/**
 *
 * @description 行政强制基本信息VO
 * @author huangyi-1
 * @date 2021-7-27 11:48
 * @version 1.0.0
 */
@Data
public class XzqzJbxxVO extends AjdrBaseVO{

    //    案号
    @ExcelKey
    @ExcelRule(name = "案号",type=String.class,max = 100)
    @ExcelProperty( index = 1)
    private String ah;

    //    案件来源
    @ExcelRule(name = "案件来源",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_AJLY)
    @ExcelProperty( index = 2)
    private String qzajly;

    //    关联案件编号
    @ExcelRule(name = "关联案件编号",type=String.class,max = 100)
    @ExcelProperty( index = 3)
    private String glajAjbh;

    //    案件地址行政区划
    @ExcelRule(name = "案件地址行政区划",type=String.class,isXzqh=true)
    @ExcelProperty( index = 4)
    private String ajdzxzqh;

    //    案件详细地址
    @ExcelRule(name = "案件详细地址",type=String.class,max = 200)
    @ExcelProperty( index = 5)
    private String ajdd;

    //    行政执法主体名称
    @ExcelRule(name = "行政执法主体名称",type=String.class,max = 100)
    @ExcelProperty( index = 6)
    private String xzzfztmc;

    //    立案文书号
    @ExcelRule(name = "立案文书号",type=String.class,max = 100)
    @ExcelProperty( index = 7)
    private String lawsh;

    //    立案批准日期（2021-01-01）
    @ExcelRule(name = "立案批准日期",type=Date.class,max = 100)
    @ExcelProperty( index = 8 )
    private String lapzrq;

    //    是否公开
    @ExcelRule(name = "是否公开",type=String.class,max = 100,codeType= NCodeConsts.CODE_TYPE_SF)
    @ExcelProperty( index = 9)
    private String gkxx;

    //    公开日期（2021-01-01）
    @ExcelRule(name = "公开日期",type=Date.class,max = 100)
    @ExcelProperty( index = 10)
    private String gkrq;
}
