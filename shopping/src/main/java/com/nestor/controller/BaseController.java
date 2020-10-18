package com.nestor.controller;

import com.nestor.common.LogHttpInfo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * base controller
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2020/10/18
 */
@LogHttpInfo
public abstract class BaseController {

    @Autowired
    protected HttpServletRequest request;

}
