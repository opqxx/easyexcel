/**
 * @projectName les_zfba_bl
 * @package com.thunisoft.sfxz.zfjdpt.zfba.service.impl
 * @className com.thunisoft.sfxz.zfjdpt.zfba.service.impl.AjdrCheckServiceImpl
 * @copyright Copyright 2020 Thunisoft, Inc All rights reserved.
 */
package com.thunisoft.sfxz.zfjdpt.zfba.service.impl;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.thunisoft.artery.code.model.INormalCode;
import com.thunisoft.artery.code.service.INormalCodeService;
import com.thunisoft.artery.util.ArteryClassUtil;
import com.thunisoft.sfxz.zfjdpt.zfba.annotation.ExcelKey;
import com.thunisoft.sfxz.zfjdpt.zfba.annotation.ExcelRule;
import com.thunisoft.sfxz.zfjdpt.zfba.model.entity.AjdrAh;
import com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr.AjdrFieldExtVO;
import com.thunisoft.sfxz.zfjdpt.zfba.repository.AjdrAhRepository;
import com.thunisoft.sfxz.zfjdpt.zfba.repository.AjdrjlRepository;
import com.thunisoft.sfxz.zfjdpt.zfba.service.AjdrCheckService;
import com.thunisoft.sfxz.zfjdpt.zfba.utils.DateUtil;
import com.thunisoft.sfxz.zfjdpt.zfba.utils.ExcelBeanUtil;
import com.thunisoft.sfxz.zfjdpt.zfba.utils.XzqhUtil;
import lombok.Data;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.*;

/**
 * AjdrCheckServiceImpl
 * @description 案件导入校验serviceImpl
 * @author huangyi-1
 * @date 2021-7-27 16:22
 * @version 1.0
 */
@Service
public class AjdrCheckServiceImpl implements AjdrCheckService {

    @Autowired
    private AjdrAhRepository ajdrAhRepository;

    @Autowired
    private INormalCodeService normalCodeService;

    /**
     *
     * @description 校验规则
     * @param lists 数据
     * @return
     * @author huangyi-1
     * @date 2021-7-27 16:31
     * @version 1.0.0
     */
    @Override
    public void checkRules(List... lists)
        throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        List<String> keys = new ArrayList<>();
        // 校验数据
        for(int i=0;i<lists.length;i++){
            checkData(lists[i],keys,i==0);
        }
        // 同步设置失败数据
        List<String> errKeys = new ArrayList<>();
        for(int i=0;i<lists.length;i++){
            for(Object obj:lists[i]){
                String key = getKeyValue(obj);
                boolean ckeck = (boolean)PropertyUtils.getProperty(obj, "check");
                if(ckeck && StringUtils.isNotBlank(key) && !errKeys.contains(key)){
                    errKeys.add(key);
                }
            }
        }
        List<String> rightKeys = new ArrayList<>();
        for(int i=0;i<lists.length;i++){
            for(Object obj:lists[i]){
                String key = getKeyValue(obj);
                if(errKeys.contains(key)){
                    PropertyUtils.setProperty(obj,"check", true);
                }else if(!rightKeys.contains(key) && StringUtils.isNotBlank(key)){
                    rightKeys.add(key);
                }
            }
        }
        List<AjdrAh> ajdrAhs = new ArrayList<>();
        for(String key : rightKeys){
            AjdrAh ah = new AjdrAh();
            ah.setAh(key);
            ajdrAhs.add(ah);
        }
        if(CollectionUtils.isNotEmpty(ajdrAhs)){
            ajdrAhRepository.saveAll(ajdrAhs);
        }
    }

    /**
     *
     * @description 校验行数据
     * @param datas 数据
     * @param keys keys
     * @return
     * @author huangyi-1
     * @date 2021-7-27 16:31
     * @version 1.0.0
     */
    private void checkData(List<Object> datas,List<String> keys,boolean sfzb)
        throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        for(Object data:datas){
            // 校验主键信息
            checkDataKey(data,keys,sfzb);
            // 校验数据项
            checkFieldsRule(data);
        }
    }

    /**
     *
     * @description 校验主键信息
     * @param
     * @return
     * @author huangyi-1
     * @date 2021-7-27 16:03
     * @version 1.0.0
     */
    private String checkDataKey(Object data,List<String> keys,boolean sfzb)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {
        Field[] fields = ArteryClassUtil.getModelField(data.getClass());
        // 主键字段
        Field keyField = null;
        for(Field field:fields){
            if(field.getAnnotation(ExcelKey.class)!=null){
                keyField = field;
            }
        }
        String key = (String)PropertyUtils.getProperty(data, keyField.getName());
        if(sfzb && !keys.contains(key)){
            keys.add(key);
        }else if(sfzb && keys.contains(key)){
            setFieldMsg(data,keyField.getName(),"案件编号重复");
            return null;
        }

        // 非空判断
        if(StringUtils.isBlank(key)){
            setFieldMsg(data,keyField.getName(),"案件编号不能为空");
            return null;
        }
        // 长度校验
        if(key.length()>100){
            setFieldMsg(data,keyField.getName(),"案件编号长度不能超过100");
            return null;
        }
        // 是否重复
        if(sfzb && !ajdrAhRepository.findById(key).equals(Optional.empty())){
            setFieldMsg(data,keyField.getName(),"该案件编号已有导入记录");
            return key;
        }
        // 是否有关联主表信息
        if(!sfzb && !keys.contains(key)){
            setFieldMsg(data,keyField.getName(),"该案件编号无关联主表信息");
            return null;
        }
        return key;
    }

    /**
     *
     * @description 获取数据主键
     * @param data
     * @return
     * @author huangyi-1
     * @date 2021-7-28 18:37
     * @version 1.0.0
     */
    private String getKeyValue(Object data)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Field[] fields = ArteryClassUtil.getModelField(data.getClass());
        // 主键字段
        Field keyField = null;
        for(Field field:fields){
            if(field.getAnnotation(ExcelKey.class)!=null){
                keyField = field;
            }
        }
        return (String)PropertyUtils.getProperty(data, keyField.getName());
    }

    /**
     *
     * @description 校验字段
     * @param data 数据
     * @return
     * @author huangyi-1
     * @date 2021-7-27 16:33
     * @version 1.0.0
     */
    private void checkFieldsRule(Object data)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {
        Field[] fields = ArteryClassUtil.getModelField(data.getClass());
        for(Field field:fields){
            if(field.getAnnotation(ExcelKey.class)!=null
                || field.getAnnotation(ExcelIgnore.class)!=null){
                continue;
            }
            ExcelRule excelRule = field.getAnnotation(ExcelRule.class);
            String value = (String)PropertyUtils.getProperty(data, field.getName());
            if(excelRule==null || StringUtils.isBlank(value)){
                continue;
            }

            // 校验数据类型
            String typeErrMsg = checkFieldType(excelRule.type(),value);
            if(StringUtils.isNotBlank(typeErrMsg)){
                setFieldMsg(data,field.getName(),typeErrMsg);
                continue;
            }

            // 校验长度
            int max = excelRule.max();
            if(max!=-1 && value.length()>max){
                setFieldMsg(data,field.getName(),"字段长度不能超过"+max);
            }

            // 校验单值代码
            String codeType = excelRule.codeType();
            if(StringUtils.isNotBlank(codeType)){
                String code = getCodeByName(codeType,value);
                setFieldCodeValue(data,field.getName(),value);
                if(StringUtils.isBlank(code)){
                    setFieldMsg(data,field.getName(),"数据错误，请选择正确数据");
                    continue;
                }else {
                    PropertyUtils.setProperty(data, field.getName(),code);
                }
            }

            // 校验行政区划
            boolean isXzqh = excelRule.isXzqh();
            if(isXzqh && XzqhUtil.getXzqhs(value)[0]==null){
                setFieldMsg(data,field.getName(),"请填写正确行政区划代码");
                continue;
            }
        }
    }

    /**
     *
     * @description 校验字段类型
     * @param type 类型
     * @param value 值
     * @return
     * @author huangyi-1
     * @date 2021-7-27 17:34
     * @version 1.0.0
     */
    private String checkFieldType(Class type, String value){
        String errMsg = "数据格式错误";
        try{
            if(type.equals(Date.class) && DateUtil.convertExcelDate(value)==null){
                return errMsg;
            } else if(type.equals(Integer.class)){
                Integer.parseInt(value);
            } else if(type.equals(Double.class)){
                Double.parseDouble(value);
            } else if(type.equals(Long.class)){
                Long.parseLong(value);
            } else if(type.equals(BigDecimal.class)){
                new BigDecimal(value);
            }
        }catch(Exception e){
            return errMsg;
        }
        return null;
    }

    /**
     *
     * @description 设置字段消息
     * @param fieldName 字段
     * @param msg 消息
     * @return
     * @author huangyi-1
     * @date 2021-7-27 15:20
     * @version 1.0.0
     */
    private void setFieldMsg(Object obj, String fieldName,String msg)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {
        PropertyUtils.setProperty(obj, "check", true);
        Map<String, AjdrFieldExtVO> fieldInfo = ExcelBeanUtil.getBeanFieldInfo(obj);
        AjdrFieldExtVO ajdrFieldExtVO = ExcelBeanUtil.getAjdrFieldExtVO(fieldInfo,fieldName);
        ajdrFieldExtVO.setErrMsg(msg);
    }

    /**
     *
     * @description 设置字段代码值
     * @param
     * @author huangyi-1
     * @date 2021-7-28 11:44
     * @version 1.0.0
     */
    private void setFieldCodeValue(Object data,String fieldName,String codeValue)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, AjdrFieldExtVO> fieldInfo = ExcelBeanUtil.getBeanFieldInfo(data);
        AjdrFieldExtVO ajdrFieldExtVO = ExcelBeanUtil.getAjdrFieldExtVO(fieldInfo,fieldName);
        ajdrFieldExtVO.setCodeValue(codeValue);
    }

    /**
     *
     * @description 根据名称获取码值
     * @param codeType 代码类型
     * @param name 名称
     * @return
     * @author huangyi-1
     * @date 2021-7-22 10:43
     * @version 1.0.0
     */
    public String getCodeByName(String codeType,String name){
        if(org.apache.commons.lang3.StringUtils.isEmpty(name)){
            return null;
        }
        List<? extends INormalCode> codeList = normalCodeService.getCodeList(codeType);
        for(INormalCode code:codeList){
            if(org.apache.commons.lang3.StringUtils.equals(name,code.getName())){
                return code.getCode();
            }
        }
        return null;
    }

}
