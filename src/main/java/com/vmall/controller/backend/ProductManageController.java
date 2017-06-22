package com.vmall.controller.backend;

import com.vmall.common.Const;
import com.vmall.common.ResponseCode;
import com.vmall.common.ServerResponse;
import com.vmall.pojo.User;
import com.vmall.service.IProductService;
import com.vmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by GV
 * DATE:2017/6/20
 * TIME:下午4:47
 */
@Controller
@RequestMapping("")
public class ProductManageController {

    @Autowired
    IProductService iProductService;

    @Autowired
    IUserService iUserService;


    @RequestMapping(value ="list",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "page",defaultValue = "1") Integer page,
                               @RequestParam(value = "size",defaultValue = "10") Integer size){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),"未登陆");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.findProductList(page,size);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }

    }



    @RequestMapping(value ="search",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse search(){
       return null;
    }
    
    @RequestMapping(value ="upload",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse upload(){
       return null;
    }

    @RequestMapping(value ="detail",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse detail(){
       return null;
    }

    @RequestMapping(value ="set_sale_status",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse setSaleStatus(){
       return null;
    }

    @RequestMapping(value ="save",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse save(){
       return null;
    }

    @RequestMapping(value ="richtext_img_upload",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse richtextImgUpload(){
       return null;
    }
}
