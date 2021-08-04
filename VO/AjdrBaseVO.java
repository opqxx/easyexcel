/**
 * @projectName les_zfba_bl
 * @package com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr
 * @className com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr.AjdrBaseVO
 * @copyright Copyright 2020 Thunisoft, Inc All rights reserved.
 */
package com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr;

import com.alibaba.excel.annotation.ExcelIgnore;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * AjdrBaseVO
 * @description 案件导入基础VO
 * @author huangyi-1
 * @date 2021-7-27 11:46
 * @version 1.0
 */
@Data
public class AjdrBaseVO {

    /** 数据校验结果 */
    @ExcelIgnore
    public boolean check;

    /** 字段描述信息 */
    @ExcelIgnore
    public Map<String,AjdrFieldExtVO> fieldInfo;
}
