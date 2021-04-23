package com.xujie.miaosha.dao;

import com.xujie.miaosha.domain.MiaoshaGoods;
import com.xujie.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsDao {

    @Select("select g.*,mg.start_date,mg.end_date,mg.stock_count,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    List<GoodsVo> listGoodsVo();

    @Select("select g.*,mg.start_date,mg.end_date,mg.stock_count,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id where goods_id = #{goodsId}")
    GoodsVo getGoodsVoByGoodsId( @Param("goodsId") long goodsId );

    @Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count >0")
    int reduceStock( MiaoshaGoods g );

}
