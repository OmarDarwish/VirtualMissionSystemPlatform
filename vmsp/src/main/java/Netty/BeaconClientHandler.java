/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
//package io.netty.example.beacon;

package Netty;

import java.io.File;
import java.io.FileWriter;

import org.apache.camel.ProducerTemplate;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Handles a client-side channel.
 */
@Sharable
public class BeaconClientHandler extends SimpleChannelInboundHandler<String> {

   ProducerTemplate template;
   StringBuilder builder;

   public BeaconClientHandler(ProducerTemplate template) {
      this.template = template;
      builder = new StringBuilder();
   }

   @Override
   protected void channelRead0(ChannelHandlerContext ctx, String msg)
         throws Exception {
      // Check to see if it's a new update
      if (msg.indexOf("<?xml") != -1)
         builder = new StringBuilder();
      
      //add the new line
      if (!msg.isEmpty())
         builder.append(msg.trim());

      // Check to see if it's the end of an update
      if (msg.indexOf("</PBX>") != -1)
         template.sendBody(builder.toString());

   }

   @Override
   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
      cause.printStackTrace();
      ctx.close();
   }
}
