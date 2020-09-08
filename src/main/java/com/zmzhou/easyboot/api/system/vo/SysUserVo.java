package com.zmzhou.easyboot.api.system.vo;

import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.zmzhou.easyboot.api.system.entity.SysUser;
import com.zmzhou.easyboot.framework.entity.OptionsVo;

import lombok.Data;

/**
 * The type Sys user vo.
 *
 * @author zmzhou
 * @version 1.0
 * @title SysUserVo
 * @description 用户管理vo类
 * @date 2020 /8/30 17:18
 */
@Data
public class SysUserVo extends SysUser implements BaseVo {
    /** serialVersionUID */
    private static final long serialVersionUID = 5874469802059198167L;

    /** 用户的角色code */
    private Set<String> roles;
    /** 用户的角色code */
    private Set<OptionsVo> roleList;
    /**
     * Entity sys user.
     *
     * @return the sys user
     */
    @Override
    public SysUser toEntity(){
        SysUser e = new SysUser();
        BeanUtils.copyProperties(this, e);
        return e;
    }
}
