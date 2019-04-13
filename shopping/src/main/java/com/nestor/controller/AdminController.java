package com.nestor.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nestor.Constants;
import com.nestor.common.LogHttpInfo;
import com.nestor.entity.Admin;
import com.nestor.entity.Result;
import com.nestor.service.AdminService;
import com.nestor.util.CheckUtil;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private HttpServletRequest request;

    /**
     * <p>管理员登录接口</p>
     * 
     * @param admin
     * @return
     */
    @PostMapping(path = "/admins/login")
    @LogHttpInfo
    public Result<Boolean> login(@ModelAttribute Admin admin) {
        HttpSession session = request.getSession();

        // 基础验证
        CheckUtil.notEmpty(admin.getAdminName(), "用户名不能为空");
        CheckUtil.notEmpty(admin.getPassword(), "密码不能为空");

        Admin adminInDB = adminService.findByAdminNameAndPassword(admin.getAdminName(), admin.getPassword());
        if (adminInDB != null) {
            session.setAttribute("admin", adminInDB);
            session.setMaxInactiveInterval(3600);
            return new Result<Boolean>(true);
        } else {
            return new Result<Boolean>(false);
        }
    }

    /**
     * <p>非暴露接口，内部添加管理员接口</p>
     * 
     * @return
     */
    @PostMapping(path = "/admins")
    @LogHttpInfo
    public Result<?> add(@RequestBody Admin admin) {
        // 基础验证
        CheckUtil.notEmpty(admin.getAdminName(), "用户名不能为空");
        CheckUtil.notEmpty(admin.getPassword(), "密码不能为空");

        Map<String, String> map = new HashMap<>();
        map.put(Constants.ID, adminService.add(admin));

        return new Result<>(map);
    }

    /**
     * <p>从session中获取管理员信息</p>
     * 
     * @return
     */
    @GetMapping(path = "/admins/session")
    @LogHttpInfo
    public Result<Admin> findBySession() {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");

        return new Result<>(admin);
    }

    /**
     * <p>退出登录</p>
     * 
     * @return
     */
    @GetMapping(path = "/admins/logout")
    @LogHttpInfo
    public Result<Boolean> logout() {
        HttpSession session = request.getSession();
        session.removeAttribute("admin");
        return new Result<>(true);
    }
}
