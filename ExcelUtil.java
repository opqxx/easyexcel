/**
 * @projectName les_zfba_bl
 * @package com.thunisoft.sfxz.zfjdpt.zfba.utils
 * @className com.thunisoft.sfxz.zfjdpt.zfba.utils.ExcelUtil
 * @copyright Copyright 2020 Thunisoft, Inc All rights reserved.
 */
package com.thunisoft.sfxz.zfjdpt.zfba.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.fastjson.JSON;
import com.github.xiaoymin.knife4j.core.io.ResourceUtil;
import com.thunisoft.artery.util.ArteryClassUtil;
import com.thunisoft.artery.util.uuid.UUIDHelper;
import com.thunisoft.sfxz.zfjdpt.zfba.annotation.ExcelKey;
import com.thunisoft.sfxz.zfjdpt.zfba.annotation.ExcelRule;
import com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr.AjdrFieldExtVO;
import com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr.XzqzJbxxVO;
import com.thunisoft.sfxz.zfjdpt.zfba.service.XzqzAjdrService;
import com.thunisoft.sfxz.zfjdpt.zfba.service.impl.FileServiceImpl;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * ExcelUtil
 * @description Excel工具类
 * @author huangyi-1
 * @date 2021-7-27 18:58
 * @version 1.0
 */
@Component
public class ExcelUtil {

    @Autowired
    private FileServiceImpl fileServiceImpl;

    /**
     *
     * @description 生成Excel
     * @param sheetDatas 数据
     * @param template 模板地址
     * @return
     * @author huangyi-1
     * @date 2021-7-28 14:13
     * @version 1.0.0
     */
    public static File writeExcel(List<List> sheetDatas,String template)
        throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException,
        NoSuchFieldException {
        // 校验是否包含导出数据
        int errCount=0;
        for(List sheetData:sheetDatas){
            errCount = errCount + sheetData.size();
        }
        if(errCount==0){
            return null;
        }

        // 转换数据格式，封装消息到字段
        initSheetDatas(sheetDatas);
        InputStream inputStream = ExcelUtil.class.getClassLoader().getResourceAsStream(template);

        // 写入的文件路径
        File tempFile = File.createTempFile(UUIDHelper.getUuid(), ".xlsx" );
        ExcelWriterBuilder writerBuilder = EasyExcel.write(tempFile.getAbsolutePath(), XzqzJbxxVO.class).withTemplate(inputStream);
        writerBuilder.excelType(ExcelTypeEnum.XLSX);
        // 注册写入处理器
        writerBuilder.registerWriteHandler(new CellCommentWriteHandler());

        ExcelWriter writer = writerBuilder.build();
        for(int i=0;i<sheetDatas.size();i++){
            Class sheetClass = Object.class;
            if(sheetDatas.get(i).size()>0){
                sheetClass = sheetDatas.get(i).get(0).getClass();
            }
            WriteSheet writeSheet = EasyExcel.writerSheet(i ).head(sheetClass).build();
            writeSheet.setNeedHead(false);
            writer.write(sheetDatas.get(i), writeSheet);
        }
        // finish才会写入到文件中
        writer.finish();

        return tempFile;
    }

    /**
     *
     * @description 初始sheet表单数据
     * @param sheetDatas 数据
     * @author huangyi-1
     * @date 2021-7-28 11:16
     * @version 1.0.0
     */
    private static void initSheetDatas(List<List> sheetDatas)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {
        for(List sheetData:sheetDatas){
            for(Object data:sheetData){
                initSheetField(data);
            }
        }
    }

    /**
     *
     * @description 初始sheet字段数据
     * @param data 数据
     * @author huangyi-1
     * @date 2021-7-28 11:16
     * @version 1.0.0
     */
    private static void initSheetField(Object data)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {
        Field[] fields = ArteryClassUtil.getModelField(data.getClass());
        for(Field field:fields){
            if(field.getAnnotation(ExcelIgnore.class)!=null){
                continue;
            }
            Map<String, AjdrFieldExtVO> fieldInfo = ExcelBeanUtil.getBeanFieldInfo(data);
            AjdrFieldExtVO ajdrFieldExtVO = ExcelBeanUtil.getAjdrFieldExtVO(fieldInfo,field.getName());
            String value = (String)PropertyUtils.getProperty(data, field.getName());
            if(StringUtils.isNotBlank(ajdrFieldExtVO.getCodeValue())){
                value = ajdrFieldExtVO.getCodeValue();
            }
            Map<String,String> fieldDataMap = new HashMap<>();
            fieldDataMap.put("value",value);
            fieldDataMap.put("msg",ajdrFieldExtVO.getErrMsg());
            PropertyUtils.setProperty(data,field.getName(), JSON.toJSONString(fieldDataMap));
        }
    }
}


class CellCommentWriteHandler implements CellWriteHandler {

    @Override
    public void beforeCellCreate(
        WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        addCellComment(cell);
    }

    /**
     * 添加批注信息
     * @param cell
     */
    private void addCellComment(Cell cell) {
        String mapValue = cell.getStringCellValue();
        Map valueMap = JSON.parseObject(mapValue,Map.class);
        String value = (String)valueMap.get("value");
        String msg = (String)valueMap.get("msg");
        cell.setCellValue(value);
        if(StringUtils.isBlank(msg)){
            return;
        }
        Drawing<?> drawing = cell.getSheet().createDrawingPatriarch();
        Comment cellComment = drawing.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, cell.getColumnIndex(), cell.getRowIndex(), cell.getColumnIndex()+1, cell.getRowIndex()+1));
        cellComment.setString(new XSSFRichTextString(msg));
        cell.setCellComment(cellComment);
    }
}
