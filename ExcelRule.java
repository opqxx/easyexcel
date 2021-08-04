/**
 * @projectName les_zfba_bl
 * @package com.thunisoft.sfxz.zfjdpt.zfba.annotation
 * @className com.thunisoft.sfxz.zfjdpt.zfba.annotation.ExcelRule
 * @copyright Copyright 2020 Thunisoft, Inc All rights reserved.
 */
package com.thunisoft.sfxz.zfjdpt.zfba.annotation;

import java.lang.annotation.*;

/**
 * ExcelRule
 * @description Excel校验规则
 * @author huangyi-1
 * @date 2021-7-27 10:11
 * @version 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelRule {
    String name();
    Class type();
    int max() default -1;
    String codeType() default "";
    boolean isXzqh() default false;
}
