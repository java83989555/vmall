package com.vmall.controller.portal;

import com.vmall.common.ServerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by GV
 * DATE:2017/6/20
 * TIME:下午4:48
 */
@Controller
@RequestMapping()
public class ProductController {


    @RequestMapping(value ="list",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse list(){
       return null;
    }


    @RequestMapping(value ="detail",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse detail(){
       return null;
    }



}
