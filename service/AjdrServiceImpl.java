/**
 * @projectName les_zfba_bl
 * @package com.thunisoft.sfxz.zfjdpt.zfba.service.impl
 * @className com.thunisoft.sfxz.zfjdpt.zfba.service.impl.AjdrServiceImpl
 * @copyright Copyright 2020 Thunisoft, Inc All rights reserved.
 */
package com.thunisoft.sfxz.zfjdpt.zfba.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.thunisoft.artery.util.uuid.UUIDHelper;
import com.thunisoft.sfxz.les.security.AuthContextHolder;
import com.thunisoft.sfxz.zfjdpt.zfba.enums.AjdrEnum;
import com.thunisoft.sfxz.zfjdpt.zfba.enums.AjdrZtEnum;
import com.thunisoft.sfxz.zfjdpt.zfba.enums.SfEnum;
import com.thunisoft.sfxz.zfjdpt.zfba.model.entity.Ajdrjl;
import com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr.AjdrjlQuery;
import com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr.AjdrjlVO;
import com.thunisoft.sfxz.zfjdpt.zfba.model.vo.ajdr.AjdrjlXqVO;
import com.thunisoft.sfxz.zfjdpt.zfba.repository.AjdrjlRepository;
import com.thunisoft.sfxz.zfjdpt.zfba.service.AjdrService;
import com.thunisoft.sfxz.zfjdpt.zfba.service.XzqzAjdrService;
import com.thunisoft.sfxz.zfjdpt.zfba.task.AjdrTask;
import com.thunisoft.sfxz.zfjdpt.zfba.utils.ExcelUtil;
import com.thunisoft.sfxz.zfjdpt.zfba.utils.SessionUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * AjdrServiceImpl
 * @description 案件导入service实现类
 * @author huangyi-1
 * @date 2021-7-21 11:47
 * @version 1.0
 */
@Service
public class AjdrServiceImpl implements AjdrService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AjdrjlRepository ajdrjlRepository;

    @Autowired
    private XzqzAjdrService xzqzAjdrService;

    @Autowired
    private FileServiceImpl fileServiceImpl;

    @Autowired
    private AjdrTask ajdrTask;

    /**
     *
     * @description 执行案件导入
     * @param zfxwlx 执法行为类型
     * @param file 文件
     * @return
     * @author huangyi-1
     * @date 2021-7-27 17:48
     * @version 1.0.0
     */
    @Override public void startAjdr(String zfxwlx, MultipartFile file){
        // 新增任务
        String bh = addAjdrjl(zfxwlx);

        // 异步执行任务
        ajdrTask.executeAjdrTask(AuthContextHolder.getContext(),bh,zfxwlx,file);
    }

    @Override public void doAjdr(String jlbh,String zfxwlx, MultipartFile file) {
        updateAjdrjl(jlbh,null,null,AjdrZtEnum.ZXZ.getCode());

        // 导入模板数据
        Map<String,List> ajdrjgMap = importAndSave(zfxwlx,file);

        if(ajdrjgMap==null){
            updateAjdrjl(jlbh,null,null,AjdrZtEnum.ZXCW.getCode());
            return ;
        }

        // 处理导入结果，生成结果Excel
        List<AjdrjlXqVO> ajdrXqList = new ArrayList<>();
        String sbwjdz = initAjdrXq(zfxwlx,ajdrjgMap,ajdrXqList);
        // 任务完成
        updateAjdrjl(jlbh,ajdrXqList,sbwjdz,AjdrZtEnum.WC.getCode());
    }

    /**
     *
     * @description 导入并保存数据
     * @param zfxwlx 执法行为类型
     * @param file 文件
     * @return
     * @author huangyi-1
     * @date 2021-8-2 14:56
     * @version 1.0.0
     */
    private Map<String,List> importAndSave(String zfxwlx, MultipartFile file){
        Map<String,List> ajdrjgMap = null;
        try {
            //行政处罚
            if(StringUtils.equals(AjdrEnum.XZCF.getType(),zfxwlx)){

            }
            //行政检查
            else if(StringUtils.equals(AjdrEnum.XZJC.getType(),zfxwlx)){

            }
            //行政强制
            else if(StringUtils.equals(AjdrEnum.XZQZ.getType(),zfxwlx)){
                ajdrjgMap = xzqzAjdrService.xzqzDr(file);
            }
            //行政许可
            else if(StringUtils.equals(AjdrEnum.XZXK.getType(),zfxwlx)){

            }
        }catch (Exception e){
            // 上传模板或数据异常，情况太多，无法获取的部分抛出异常
            logger.error("案件导入异常,非程序错误，可忽略",e);
        }
        return ajdrjgMap;
    }

    /**
     *
     * @description 初始案件导入详情
     * @param zfxwlx
     * @param ajdrjgMap
     * @param ajdrXqList
     * @return
     * @author huangyi-1
     * @date 2021-7-28 14:10
     * @version 1.0.0
     */
    private String initAjdrXq(String zfxwlx,Map<String,List> ajdrjgMap,List<AjdrjlXqVO> ajdrXqList){
        if(ajdrjgMap==null){
            return null;
        }
        // 上报错误列表，生成excel用
        List<List> errSbxxList = new ArrayList();
        String filePath = null;
        InputStream templateInputStrem = null;
        try {
            Iterator<String> keyIt = ajdrjgMap.keySet().iterator();
            while (keyIt.hasNext()) {
                String key = keyIt.next();
                AjdrjlXqVO ajdrXq = new AjdrjlXqVO();
                ajdrXq.setAjlx(key);
                List errSbxx = new ArrayList();
                List ajdrJlList = ajdrjgMap.get(key);
                for (Object ajdrJl : ajdrJlList) {
                    boolean check = (boolean)PropertyUtils.getProperty(ajdrJl, "check");
                    if (check) {
                        ajdrXq.setSbts(ajdrXq.getSbts() + 1);
                        errSbxx.add(ajdrJl);
                    } else {
                        ajdrXq.setCgts(ajdrXq.getCgts() + 1);
                    }
                }
                errSbxxList.add(errSbxx);
                ajdrXqList.add(ajdrXq);
            }

            // 生成excel
            File tempFile = ExcelUtil.writeExcel(errSbxxList, AjdrEnum.getByType(zfxwlx).getTemplate());
            if (tempFile != null) {
                templateInputStrem = new FileInputStream(tempFile);
                // 文件入库
                filePath = fileServiceImpl.storeFile(templateInputStrem, AjdrEnum.getByType(zfxwlx).getName() + ".xlsx");

                IOUtils.closeQuietly(templateInputStrem);
                // 删除临时文件
                tempFile.deleteOnExit();
            }
        }catch(Exception e){
            logger.error("案件导入生成返回数据异常",e);
        }finally {
            IOUtils.closeQuietly(templateInputStrem);
        }
        return filePath;
    }

    /**
     *
     * @description 新增案件导入记录
     * @param zfxwlx 执法行为类型
     * @return
     * @author huangyi-1
     * @date 2021-7-22 16:40
     * @version 1.0.0
     */
    private String addAjdrjl(String zfxwlx){
        Ajdrjl ajdrjl = new Ajdrjl();
        ajdrjl.setBh(UUIDHelper.getUuid());
        ajdrjl.setZfxwlb(zfxwlx);
        ajdrjl.setRwcjsj(new Date());
        ajdrjl.setZt(AjdrZtEnum.DD.getCode());
        ajdrjl.setCjsj(new Date());
        ajdrjl.setCjdw(SessionUtil.getCurrentCorpId());
        ajdrjl.setCjbm(SessionUtil.getCurrentDeptId());
        ajdrjl.setCjr(SessionUtil.getCurrentUserId());
        ajdrjl.setSfsc(SfEnum.SF_F.getCode());
        ajdrjlRepository.save(ajdrjl);
        return ajdrjl.getBh();
    }

    /**
     *
     * @description 更新案件导入记录
     * @param bh 编号
     * @return
     * @author huangyi-1
     * @date 2021-7-22 16:47
     * @version 1.0.0
     */
    private void updateAjdrjl(String bh,List<AjdrjlXqVO> ajdrXqList,String sbwjdz,String zt){
        Optional<Ajdrjl> drjlVal = ajdrjlRepository.findById(bh);
        if (!drjlVal.isPresent()) {
            return;
        }
        Ajdrjl ajdrjl = drjlVal.get();

        ajdrjl.setZt(zt);
        if(CollectionUtils.isNotEmpty(ajdrXqList)){
            ajdrjl.setDrxq(JSONArray.toJSONString(ajdrXqList));
        }
        if(StringUtils.isNotBlank(sbwjdz)){
            ajdrjl.setSbwjdz(sbwjdz);
        }
//        ajdrjl.setZt(AjdrZtEnum.WC.getCode());
        if(StringUtils.equals(zt,AjdrZtEnum.WC.getCode())){
            ajdrjl.setRwwcsj(new Date());
        }
        ajdrjl.setGxr(SessionUtil.getCurrentUserId());
        ajdrjl.setGxsj(new Date());
        ajdrjlRepository.save(ajdrjl);
    }

    /**
     *
     * @description 查询案件导入记录
     * @param
     * @return
     * @author huangyi-1
     * @date 2021-7-22 15:21
     * @version 1.0.0
     */
    @Override public Page<Ajdrjl> queryAjdrjlList(AjdrjlQuery query) {
        Specification<Ajdrjl> specification = (Specification<Ajdrjl>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (null != query.getRwcjsjStart()) {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rwcjsj").as(Date.class), query.getRwcjsjStart()));
            }
            if (null != query.getRwcjsjEnd()) {
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("rwcjsj").as(Date.class), query.getRwcjsjEnd()));
            }

            if (null != query.getRwwcsjStart()) {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rwwcsj").as(Date.class),query.getRwwcsjStart()));
            }
            if (null != query.getRwwcsjEnd()) {
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("rwwcsj").as(Date.class),query.getRwwcsjEnd()));
            }

            if (StringUtils.isNotBlank(query.getZfxwlb())) {
                predicateList.add(criteriaBuilder.equal(root.get("zfxwlb"), query.getZfxwlb()));
            }
            if (StringUtils.isNotBlank(query.getZt())) {
                predicateList.add(criteriaBuilder.equal(root.get("zt"), query.getZt()));
            }
            // 排序
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("rwcjsj")));
            // 固定的条件
            predicateList.add(criteriaBuilder.equal(root.get("cjr"), SessionUtil.getCurrentUserId()));
            Predicate[] predicates = new Predicate[predicateList.size()];
            return criteriaBuilder.and(predicateList.toArray(predicates));
        };

        return ajdrjlRepository.findAll(specification, PageRequest.of(query.getOffset() / query.getLimit(), query.getLimit()));
    }

    @Override public AjdrjlVO getAjdrjlXq(String bh) throws InvocationTargetException, IllegalAccessException {
        Optional<Ajdrjl> drjlVal = ajdrjlRepository.findById(bh);
        if(!drjlVal.isPresent()){
            return new AjdrjlVO();
        }
        Ajdrjl drjl = ajdrjlRepository.findById(bh).get();
        AjdrjlVO ajdrjlVO = new AjdrjlVO();
        BeanUtils.copyProperties(ajdrjlVO,drjl);
        ajdrjlVO.setJlxqList(new ArrayList<>());
        String drxq = ajdrjlVO.getDrxq();
        if(StringUtils.isNotBlank(drxq)){
            ajdrjlVO.setJlxqList(JSONArray.parseArray(drxq,AjdrjlXqVO.class));
        }
        return ajdrjlVO;
    }
}
