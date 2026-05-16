package com.mei.framework.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.mei.common.constant.Constants;
import com.mei.common.constant.UserConstants;
import com.mei.common.core.domain.entity.SysUser;
import com.mei.common.core.domain.model.RegisterBody;
import com.mei.common.utils.DateUtils;
import com.mei.common.utils.MessageUtils;
import com.mei.common.utils.SecurityUtils;
import com.mei.common.utils.StringUtils;
import com.mei.framework.manager.AsyncManager;
import com.mei.framework.manager.factory.AsyncFactory;
import com.mei.system.service.ISysUserService;

/**
 * 注册校验方法
 *
 * @author ruoyi
 */
@Component
public class SysRegisterService
{
    /** 默认注册角色ID：普通角色（common） */
    private static final Long DEFAULT_ROLE_ID = 2L;

    @Autowired
    private ISysUserService userService;

    /**
     * 注册
     */
    public String register(RegisterBody registerBody)
    {
        String msg = "", username = registerBody.getUsername(), password = registerBody.getPassword();
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);

        if (StringUtils.isEmpty(username))
        {
            msg = "用户名不能为空";
        }
        else if (StringUtils.isEmpty(password))
        {
            msg = "用户密码不能为空";
        }
        else if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            msg = "账户长度必须在2到20个字符之间";
        }
        else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            msg = "密码长度必须在5到20个字符之间";
        }
        else if (!userService.checkUserNameUnique(sysUser))
        {
            msg = "保存用户'" + username + "'失败，注册账号已存在";
        }
        else
        {
            sysUser.setNickName(username);
            sysUser.setPwdUpdateDate(DateUtils.getNowDate());
            sysUser.setPassword(SecurityUtils.encryptPassword(password));
            boolean regFlag = userService.registerUser(sysUser);
            if (!regFlag)
            {
                msg = "注册失败,请联系系统管理人员";
            }
            else
            {
                // 注册成功后自动分配"普通角色"（roleId=2）
                userService.insertUserAuth(sysUser.getUserId(), new Long[]{ DEFAULT_ROLE_ID });
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.REGISTER, MessageUtils.message("user.register.success")));
            }
        }
        return msg;
    }
}
