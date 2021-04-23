package com.xujie.miaosha.redis;

public class OrderKey extends BasePrefix {

    public OrderKey( String prefix ) {
        super( prefix);
    }

    public static OrderKey getMiaoshaOrderBuUidGid = new OrderKey("miaoUidGid");

}
