package edu.utdesign.rwc.vmsp.esb;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import org.apache.camel.ProducerTemplate;

import Netty.BeaconClientInitializer;

public class PbxTcpClient implements Runnable {
   private ProducerTemplate template;

   static final boolean SSL = System.getProperty("ssl") != null;
   static final String HOST = System.getProperty("host", "127.0.0.1");
   static final int PORT = Integer.parseInt(System.getProperty("port",
         SSL ? "8992" : "8023"));

   public PbxTcpClient(ProducerTemplate template) {
      this.template = template;
   }

   @Override
   public void run() {

      EventLoopGroup group = new NioEventLoopGroup();
      try {
         // Configure SSL.
         final SslContext sslCtx;
         if (SSL) {
            sslCtx = SslContext
                  .newClientContext(InsecureTrustManagerFactory.INSTANCE);
         } else {
            sslCtx = null;
         }

         Bootstrap b = new Bootstrap();
         b.group(group)
               .channel(NioSocketChannel.class)
               .handler(
                     new BeaconClientInitializer(sslCtx, template, this.HOST,
                           this.PORT));

         // Start the connection attempt.
         Channel ch = b.connect(HOST, PORT).sync().channel();
         
         // periodically ping server
         while (true) {
            String ping = "ping ";
            // Sends the received line to the server.
            ch.writeAndFlush(ping + "\r\n");
            Thread.sleep(1000);

         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         group.shutdownGracefully();
      }
   }

}
