/**
 * @projectName les_zfba_bl
 * @package com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr
 * @className com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr.AjdrjlXqVO
 * @copyright Copyright 2020 Thunisoft, Inc All rights reserved.
 */
package com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr;

import lombok.Data;

/**
 * AjdrjlXqVO
 * @description 案件导入记录详情vo
 * @author huangyi-1
 * @date 2021-7-28 11:33
 * @version 1.0
 */
@Data
public class AjdrjlXqVO {

    /** 案件类型 */
    private String ajlx;

    /** 成功条数 */
    private int cgts;

    /** 失败条数 */
    private int sbts;
}
