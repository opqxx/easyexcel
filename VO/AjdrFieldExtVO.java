/**
 * @projectName les_zfba_bl
 * @package com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr
 * @className com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr.AjdrFieldExtVO
 * @copyright Copyright 2020 Thunisoft, Inc All rights reserved.
 */
package com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr;

import lombok.Data;

/**
 * AjdrFieldExtVO
 * @description 案件导入字段扩展信息
 * @author huangyi-1
 * @date 2021-7-28 19:45
 * @version 1.0
 */
@Data
public class AjdrFieldExtVO {

    /** 错误信息 */
    private String errMsg;

    /** 单值代码中文值 */
    private String codeValue;
}
