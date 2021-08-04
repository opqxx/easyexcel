package com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * XzqzblDblQuery
 *
 * @description 行政强制办理待办理查询对象
 * @author zhainy
 * @date 2019/9/8 11:01
 * @version 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("案件导入查询对象")
public class AjdrjlQuery {

    @ApiModelProperty(value = "limit", required = true)
    private Integer limit;

    @ApiModelProperty(value = "offset", required = true)
    private Integer offset;

    private String sort;

    @ApiModelProperty("执法行为类别")
    private String zfxwlb;

    @ApiModelProperty("任务创建时间起日")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rwcjsjStart;

    @ApiModelProperty("任务创建时间止日")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rwcjsjEnd;

    @ApiModelProperty("任务完成时间起日")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rwwcsjStart;

    @ApiModelProperty("任务完成时间止日")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rwwcsjEnd;

    @ApiModelProperty("状态")
    private String zt;
}
