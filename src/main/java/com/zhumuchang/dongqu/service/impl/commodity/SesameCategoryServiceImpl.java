package com.zhumuchang.dongqu.service.impl.commodity;


import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhumuchang.dongqu.api.bean.PageBean;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCategory;
import com.zhumuchang.dongqu.api.dto.commodity.req.ReqCategoryPageDto;
import com.zhumuchang.dongqu.api.dto.commodity.req.ReqOneParamDto;
import com.zhumuchang.dongqu.api.dto.commodity.resp.RespAppCategoryPageDto;
import com.zhumuchang.dongqu.api.dto.commodity.resp.RespCategoryPageDto;
import com.zhumuchang.dongqu.api.dto.user.ResultDto;
import com.zhumuchang.dongqu.api.service.commodity.SesameCategoryService;
import com.zhumuchang.dongqu.commons.constants.ConstantsUtils;
import com.zhumuchang.dongqu.commons.enumapi.BusinessEnum;
import com.zhumuchang.dongqu.commons.exception.BusinessException;
import com.zhumuchang.dongqu.commons.interceptor.TokenUser;
import com.zhumuchang.dongqu.mapper.commodity.SesameCategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 品类 服务实现类
 * </p>
 *
 * @author sx
 * @since 2022-03-31
 */
@Slf4j
@Service
public class SesameCategoryServiceImpl extends ServiceImpl<SesameCategoryMapper, SesameCategory> implements SesameCategoryService {

    @Autowired
    private SesameCategoryMapper sesameCategoryMapper;

    /**
     * 新增品类
     *
     * @param param     品类名称
     * @param tokenUser tokenUser
     * @return 异常信息
     */
    @Override
    public String addCategory(ReqOneParamDto param, TokenUser tokenUser) {
        if (null == param || Objects.isNull(param.getParam()) || StringUtils.isBlank(String.valueOf(param.getParam())) || null == tokenUser) {
            log.info("新增品类 - 参数为空 - param={}, tokenUser={}", JSONObject.toJSONString(param), JSONObject.toJSONString(tokenUser));
            return "参数为空";
        }
        String name = String.valueOf(param.getParam());
        //判断品类是否已经存在（name条件）
        Integer countByName = sesameCategoryMapper.countByName(name);
        if (countByName.compareTo(ConstantsUtils.CODE_0) > 0) {
            log.info("新增品类 - 判断是否有重名品类 - 有 - param={}, tokenUser={}", JSONObject.toJSONString(param), JSONObject.toJSONString(tokenUser));
            return "品类名称已存在";
        }
        //获取已有品类集合，用于设置序号字段
        Integer count = sesameCategoryMapper.countByNotDel();
        SesameCategory category = new SesameCategory();
        category.setOpenId(IdUtil.simpleUUID())
                .setName(name)
                .setSort(count + 1)
                .setEnable(ConstantsUtils.CODE_0)
                .setDelFlag(ConstantsUtils.CODE_1)
                .setCreatedId(tokenUser.getUserId())
                .setCreatedName(tokenUser.getUserName())
                .setCreatedTime(LocalDateTime.now())
                .setUpdatedId(tokenUser.getUserId())
                .setUpdatedName(tokenUser.getUserName())
                .setUpdatedTime(LocalDateTime.now());
        boolean save = this.save(category);
        if (!save) {
            log.info("新增品类 - 新增失败 - param={}, tokenUser={}", JSONObject.toJSONString(param), JSONObject.toJSONString(tokenUser));
            return "新增失败";
        }
        return null;
    }

    /**
     * 停启用品类
     *
     * @param param     品类ID
     * @param tokenUser tokenUser
     * @return 响应对象
     */
    @Override
    public void enableCategory(ReqOneParamDto param, TokenUser tokenUser) {
        if (null == param || Objects.isNull(param.getParam()) || StringUtils.isBlank(String.valueOf(param.getParam())) || null == tokenUser) {
            log.info("停启用品类 - 参数为空 - param={}, tokenUser={}", JSONObject.toJSONString(param), JSONObject.toJSONString(tokenUser));
            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL);
        }
        String openId = String.valueOf(param.getParam());
        SesameCategory sesameCategory = sesameCategoryMapper.getByOpenId(openId);
        if (null == sesameCategory || ConstantsUtils.CODE_0.equals(sesameCategory.getDelFlag())) {
            log.info("停启用品类 - 品类不存在或品类已删除 - param={}, tokenUser={}", JSONObject.toJSONString(param), JSONObject.toJSONString(tokenUser));
            throw new BusinessException(BusinessEnum.PARAM_ERROR);
        }

        Integer enable;
        if (null == sesameCategory.getEnable() || ConstantsUtils.CODE_0.equals(sesameCategory.getEnable())) {
            enable = ConstantsUtils.CODE_1;
        } else {
            enable = ConstantsUtils.CODE_0;
        }
        Boolean update = sesameCategoryMapper.updateEnableById(sesameCategory.getId(), enable, tokenUser.getUserId());
        if (!update) {
            log.info("停启用品类 - 更新失败 - param={}, tokenUser={}", JSONObject.toJSONString(param), JSONObject.toJSONString(tokenUser));
            throw new BusinessException(BusinessEnum.FAIL);
        }
    }

    /**
     * 品类分页列表
     *
     * @param param 请求参数
     * @return 品类分页列表
     */
    @Override
    public ResultDto categoryPage(ReqCategoryPageDto param) {
        if (null == param) {
            param = new ReqCategoryPageDto();
        }
        Page<RespCategoryPageDto> page = new Page<>(param.getCurrent(), param.getSize());
        page = sesameCategoryMapper.categoryPage(page, param.getCategoryName(), param.getCategoryEnable(),
                param.getStartTime(), param.getEndTime());
        return new ResultDto(BusinessEnum.SUCCESS, page);
    }

    /**
     * 根据ID获取品类详情
     *
     * @param openId 品类对外ID
     * @return 品类详情
     */
    @Override
    public RespCategoryPageDto categoryDetailByOpenId(String openId) {
        if (StringUtils.isBlank(openId)) {
            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL);
        }
        RespCategoryPageDto resp = sesameCategoryMapper.categoryDetailByOpenId(openId);
        if (null == resp) {
            throw new BusinessException(BusinessEnum.DATA_NOT_FOUND);
        }
        return resp;
    }

    /**
     * 根据对外ID删除品类
     *
     * @param openId 品类对外ID
     */
    @Override
    public void delCategoryByOpenId(TokenUser tokenUser, String openId) {
        if (StringUtils.isBlank(openId) || null == tokenUser) {
            throw new BusinessException(BusinessEnum.PARAM_NULL_FAIL);
        }
        RespCategoryPageDto resp = sesameCategoryMapper.categoryDetailByOpenId(openId);
        if (null == resp) {
            log.info("根据对外ID删除品类 - 品类不存在或已删除 - openId={}", openId);
        }
        sesameCategoryMapper.delCategoryByOpenId(tokenUser.getUserId(), openId);
    }

    /**
     * app端获取品类分页列表
     *
     * @return 品类分页列表
     */
    @Override
    public Page<RespAppCategoryPageDto> appCategoryPage(PageBean param) {
        Page<RespAppCategoryPageDto> page = new Page<>(param.getCurrent(), param.getSize());
        page = sesameCategoryMapper.appCategoryPage(page);
        return page;
    }

}
