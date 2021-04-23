package com.xujie.miaosha.services;

import com.xujie.miaosha.dao.GoodsDao;
import com.xujie.miaosha.domain.Goods;
import com.xujie.miaosha.domain.MiaoshaGoods;
import com.xujie.miaosha.vo.GoodsVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsService {

    @Resource
    private GoodsDao goodsDao;


    public List<GoodsVo> listGoodVo() {
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId( long goodsId ) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    public boolean reduceStock( GoodsVo goods ) {
        MiaoshaGoods g = new  MiaoshaGoods();
        g.setGoodsId(goods.getId());
        int stock = goodsDao.reduceStock(g);
        return stock > 0;
    }
}
