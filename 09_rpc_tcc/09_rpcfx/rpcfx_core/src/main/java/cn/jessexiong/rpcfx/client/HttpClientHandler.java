package cn.jessexiong.rpcfx.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

public class HttpClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg -> "+msg);
        if(msg instanceof FullHttpResponse){
            FullHttpResponse response = (FullHttpResponse)msg;
            ByteBuf buf = response.content();
            String result = buf.toString(CharsetUtil.UTF_8);
            System.out.println("response -> "+result);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        URI uri = new URI("/user/get");
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, uri.toASCIIString());
        request.headers().add(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
        request.headers().add(HttpHeaderNames.CONTENT_LENGTH,request.content().readableBytes());
        ctx.writeAndFlush(request);
    }
}
