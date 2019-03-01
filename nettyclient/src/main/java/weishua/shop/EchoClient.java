package weishua.shop;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import weishua.shop.handler.EchoClientHandler;

import java.net.InetSocketAddress;

public class EchoClient {
    String host = "127.0.0.1";

    int port = 9999;

     public void start() throws InterruptedException {
         EventLoopGroup group = new NioEventLoopGroup();
         Bootstrap b = new Bootstrap();
         b.group(group)
                 .channel(NioSocketChannel.class)
                 .remoteAddress(new InetSocketAddress(host,port))
                 .handler(new ChannelInitializer<SocketChannel>() {
                     protected void initChannel(SocketChannel ch) throws Exception {
                         ch.pipeline().addLast(new EchoClientHandler());
                     }
                 });
         try {
             ChannelFuture f = b.connect().sync();
             f.channel().closeFuture().sync();
         } catch (InterruptedException e) {
             group.shutdownGracefully().sync();
         }
     }

    public static void main(String[] args) throws InterruptedException {

        new EchoClient().start();
    }

}
