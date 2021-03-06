package com.github.zmzhou.easyboot.api.system.vo;

import org.springframework.beans.BeanUtils;

import com.github.zmzhou.easyboot.api.system.entity.SysRole;
import com.github.zmzhou.easyboot.framework.vo.BaseVo;

/**
 * @title SysRoleVo
 * @description 角色信息vo
 * @author zmzhou
 * @version 1.0
 * @date 2020/8/30 16:53
 */
public class SysRoleVo extends SysRole implements BaseVo {
    /** serialVersionUID */
    private static final long serialVersionUID = -4861375962137505383L;

    /**
     * Entity sys user.
     *
     * @return the sys user
     */
    @Override
    public SysRole toEntity(){
        SysRole e = new SysRole();
        BeanUtils.copyProperties(this, e);
        return e;
    }
}
