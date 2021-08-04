/**
 * @projectName les_zfba_bl
 * @package com.thunisoft.sfxz.zfjdpt.zfba.utils
 * @className com.thunisoft.sfxz.zfjdpt.zfba.utils.ExcelBeanUtil
 * @copyright Copyright 2020 Thunisoft, Inc All rights reserved.
 */
package com.thunisoft.sfxz.zfjdpt.zfba.utils;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.thunisoft.artery.util.ArteryClassUtil;
import com.thunisoft.sfxz.zfjdpt.zfba.annotation.ExcelRule;
import com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr.AjdrFieldExtVO;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ExcelBeanUtil
 * @description ExcelBeanUtil
 * @author huangyi-1
 * @date 2021-7-28 16:27
 * @version 1.0
 */
public class ExcelBeanUtil {
    private static Logger logger = LoggerFactory.getLogger(ExcelBeanUtil.class);

    private ExcelBeanUtil(){}

    /**
     *
     * @description 复制excel bean
     * @param target
     * @param source
     * @author huangyi-1
     * @date 2021-7-28 15:49
     * @version 1.0.0
     */
    public static void copyExcelBean(Object target,Object source){
        Field[] fields = ArteryClassUtil.getModelField(source.getClass());
        for(Field field:fields){
            try {
                if (field.getAnnotation(ExcelIgnore.class) != null) {
                    continue;
                }
                String value = (String)PropertyUtils.getProperty(source, field.getName());
                if (StringUtils.isBlank(value)) {
                    continue;
                }
                ExcelRule excelRule = field.getAnnotation(ExcelRule.class);
                Class type = excelRule.type();
                if (type.equals(String.class)) {
                    PropertyUtils.setProperty(target, field.getName(), value);
                } else if (type.equals(Double.class)) {
                    PropertyUtils.setProperty(target, field.getName(), Double.parseDouble(value));
                } else if (type.equals(Date.class)) {
                    PropertyUtils.setProperty(target, field.getName(), DateUtil.convertExcelDate(value));
                } else if (type.equals(BigDecimal.class)) {
                    PropertyUtils.setProperty(target, field.getName(), new BigDecimal(value));
                } else if (type.equals(Long.class)) {
                    PropertyUtils.setProperty(target, field.getName(), Long.parseLong(value));
                } else if (type.equals(BigInteger.class)) {
                    PropertyUtils.setProperty(target, field.getName(), new BigInteger(value));
                }
            }catch(Exception e){
                logger.debug("ExcelBeanUtil 复制对象发生错误，可以忽略",e);
            }
        }
    }

    /**
     *
     * @description 获取实体字段描述信息
     * @param obj
     * @return
     * @author huangyi-1
     * @date 2021-7-28 19:51
     * @version 1.0.0
     */
    public static Map<String, AjdrFieldExtVO> getBeanFieldInfo(Object obj)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, AjdrFieldExtVO> fieldInfo =(Map<String, AjdrFieldExtVO>) PropertyUtils.getProperty(obj, "fieldInfo");
        if(fieldInfo==null){
            fieldInfo = new HashMap<>();
            PropertyUtils.setProperty(obj, "fieldInfo", fieldInfo);
        }
        return fieldInfo;
    }

    public static AjdrFieldExtVO getAjdrFieldExtVO(Map<String, AjdrFieldExtVO> fieldInfo, String fieldName){
        AjdrFieldExtVO ajdrFieldExtVO = fieldInfo.get(fieldName);
        if(ajdrFieldExtVO==null){
            ajdrFieldExtVO = new AjdrFieldExtVO();
            fieldInfo.put(fieldName,ajdrFieldExtVO);
        }
        return ajdrFieldExtVO;
    }
}
