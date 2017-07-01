package com.vmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.vmall.common.Const;
import com.vmall.common.ResponseCode;
import com.vmall.common.ServerResponse;
import com.vmall.dao.CategoryMapper;
import com.vmall.dao.ProductMapper;
import com.vmall.pojo.Category;
import com.vmall.pojo.Product;
import com.vmall.service.ICategoryService;
import com.vmall.service.IProductService;
import com.vmall.util.DateTimeUtil;
import com.vmall.util.PropertiesUtil;
import com.vmall.vo.ProductDetailVo;
import com.vmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by GV
 * DATE:2017/6/20
 * TIME:下午7:11
 */
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ICategoryService iCategoryService;

    @Override
    public ServerResponse saveOrUpdateProduct(Product product) {
        if (product == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
//        和前端商订 子图url地址 以,分割 默认主图为第一张子图
        if (StringUtils.isNotBlank(product.getSubImages())) {
            String[] subImages = product.getSubImages().split(",");
            product.setMainImage(subImages[0]);
        }
//        添加
        if (product.getId() == null) {
            int rowCount = productMapper.insert(product);
            if (rowCount > 0) {
                return ServerResponse.createBySuccessMessage("添加商品成功");
            }
            return ServerResponse.createByErrorMessage("添加商品失败");
//        更新
        } else {
            int rowCount = productMapper.updateByPrimaryKey(product);
            if (rowCount > 0) {
                return ServerResponse.createBySuccessMessage("更新商品成功");
            }
            return ServerResponse.createByErrorMessage("更新商品失败");
        }
    }

    @Override
    public ServerResponse getProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("查找的商品已下架或删除");
        }
        return ServerResponse.createBySuccess(assembleProductDetailVo(product));
    }

    @Override
    public ServerResponse setProductStatus(Integer productId, Integer status) {
        if (productId == null || status == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("更新商品状态成功");
        }
        return ServerResponse.createByErrorMessage("更新商品状态失败");
    }

    @Override
    public ServerResponse findByProductNameAndProductId(String productName, Integer productId, Integer page, Integer size) {
        if (StringUtils.isNotBlank(productName)) {
            productName = new StringBuffer().append("%").append(productName).append("%").toString();
        }
        PageHelper.startPage(page, size);
        List<Product> productList = productMapper.selectByProductNameAndProductId(productName, productId);
        List<ProductListVo> listVos = Lists.newArrayList();
        for (Product product : productList) {
            ProductListVo productListVo = assembleProductList(product);
            listVos.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(listVos);
        return ServerResponse.createBySuccess(pageInfo);
    }


    @Override
    public ServerResponse<PageInfo> findProductList(Integer page, Integer size) {
//       声明pageHelper
        PageHelper.startPage(page, size);
//       执行自己的查询
        List<Product> list = productMapper.selectList();
        List<ProductListVo> listVos = Lists.newArrayList();

        for (Product product : list) {
            ProductListVo productListVo = assembleProductList(product);
            listVos.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(list);
        pageInfo.setList(listVos);
        return ServerResponse.createBySuccess(pageInfo);
    }


    private ProductListVo assembleProductList(Product product) {
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setPrice(product.getPrice());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setCategoryId(product.getCategoryId());

        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.ip", "10.211.55.7"));
        return productListVo;
    }


    private ProductDetailVo assembleProductDetailVo(Product product) {
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setName(product.getName());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());

        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.ip", "10.211.55.7"));
        productDetailVo.setParentCategoryId(categoryMapper.selectByPrimaryKey(product.getCategoryId()).getParentId());

        return productDetailVo;
    }

    /**
     * --------------------------------------------------以下门户接口----------------------------------------------------
     *
     * @param productId
     * @return
     */

    @Override
    public ServerResponse getPortalProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()) {
            return ServerResponse.createByErrorMessage("商品下架或删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    @Override
    public ServerResponse findByKeyWordAndCategoryId(String keyWord, Integer categoryId, int page, int size, String orderBy) {
        if (StringUtils.isBlank(keyWord) && categoryId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        //查找当前分类下所有的子孙分类Id 调用递归方法
        List<Integer> categoryIdList = Lists.newArrayList();
        if (categoryId != null) {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            //没有该分类,并且还没有关键字,这个时候返回一个空的结果集,不报错
            if (category == null && StringUtils.isBlank(keyWord)) {
                PageHelper.startPage(page, size);
                List<ProductListVo> productListVoList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVoList);
                return ServerResponse.createBySuccess(pageInfo);
            }
            categoryIdList= iCategoryService.getCatrgoryAndChildrenCategory(categoryId).getData();
        }

        if (StringUtils.isNotBlank(keyWord)){
            keyWord=new StringBuffer("%").append(keyWord).append("%").toString();
        }
        //1确定分页信息
        PageHelper.startPage(page, size);
        if (StringUtils.isNotBlank(orderBy)){
            //1.1前台约定此排序格式
            String [] orderBys=orderBy.split("_");
            PageHelper.orderBy(orderBys[0]+" "+orderBys[1]);
        }

        //2通过商品名关键字 和 分类ID列表 查询商品
        List<Product> productList = productMapper.selectByKeyWordAndCategoryId(StringUtils.isBlank(keyWord)?null:keyWord,categoryIdList.size()>0?categoryIdList:null);

        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product product:productList){
            productListVoList.add(assembleProductList(product));
        }
        //3整理返回数据
        PageInfo pageInfo=new PageInfo(productList);
        pageInfo.setList(productListVoList);

        return ServerResponse.createBySuccess(pageInfo);
    }


}
