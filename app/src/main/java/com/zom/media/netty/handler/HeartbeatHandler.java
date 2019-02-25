package com.zom.media.netty.handler;

import android.util.Log;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

/**
 * Created by USERA on 2019/2/22.
 */

public class HeartbeatHandler  extends ChannelHandlerAdapter {

    private String TAG = "HeartbeatHandler";

    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled
            .unreleasableBuffer(Unpooled.copiedBuffer("{\"name\": \"heartbeat\",\"data\": \"\"}", CharsetUtil.UTF_8));

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                Log.e(TAG, ctx.channel().remoteAddress() + " 读超时");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                Log.e(TAG, ctx.channel().remoteAddress() + " 写超时");
                ctx.close();
            } else if (event.state() == IdleState.ALL_IDLE) {
            }
//            ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(
//                    ChannelFutureListener.CLOSE_ON_FAILURE);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }


}
