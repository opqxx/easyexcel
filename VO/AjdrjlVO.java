/**
 * @projectName les_zfba_bl
 * @package com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr
 * @className com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr.AjdrjlVo
 * @copyright Copyright 2020 Thunisoft, Inc All rights reserved.
 */
package com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr;

import com.thunisoft.sfxz.zfjdpt.zfba.model.entity.Ajdrjl;
import lombok.Data;

import java.util.List;

/**
 * AjdrjlVo
 * @description 案件导入记录VO
 * @author huangyi-1
 * @date 2021-7-29 10:52
 * @version 1.0
 */
@Data
public class AjdrjlVO extends Ajdrjl {

    /** 导入详情列表 */
    List<AjdrjlXqVO> jlxqList;
}
