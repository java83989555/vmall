package com.vmall.controller.portal;

import com.vmall.common.ServerResponse;
import com.vmall.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by GV
 * DATE:2017/6/20
 * TIME:下午4:48
 */
@Controller
@RequestMapping("/product")
public class ProductController {


    @Autowired
    IProductService iProductService;

    @RequestMapping(value ="detail",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse detail(Integer productId){
        return iProductService.getPortalProductDetail(productId);
    }


    @RequestMapping(value ="list",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse list(@RequestParam(value = "keyWord",required = false) String keyWord,
                               @RequestParam(value = "categoryId",required = false) Integer categoryId,
                               @RequestParam(value = "page",defaultValue = "1") int page,
                               @RequestParam(value = "size",defaultValue = "10") int size,
                               @RequestParam(value = "orderBy",defaultValue = "")String orderBy){
       return iProductService.findByKeyWordAndCategoryId(keyWord,categoryId,page,size,orderBy);
    }

}
