// package com.gzq.flash.server.handler;
//
// import com.gzq.flash.protocol.PacketCodeC;
// import com.gzq.flash.protocol.Packet;
// import com.gzq.flash.protocol.request.LoginRequestPacket;
// import com.gzq.flash.protocol.request.MessageRequestPacket;
// import com.gzq.flash.protocol.response.LoginResponsePacket;
// import com.gzq.flash.protocol.response.MessageResponsePacket;
// import io.netty.buffer.ByteBuf;
// import io.netty.channel.ChannelHandlerContext;
// import io.netty.channel.ChannelInboundHandlerAdapter;
//
// import java.util.Date;
//
// /**
//  * @author guozhiqiang
//  * @description
//  * @created 2018-09-27 18:20.
//  */
// public class ServerHandler extends ChannelInboundHandlerAdapter {
//
//
//     @Override
//     public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//         System.out.println(new Date() + ": 客户端开始登录……");
//         ByteBuf byteBuf = (ByteBuf) msg;
//         Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
//         if (packet instanceof LoginRequestPacket) {
//             // 登录流程
//             LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
//             LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
//             loginResponsePacket.setVersion(packet.getVersion());
//             if (valid(loginRequestPacket)) {
//                 loginResponsePacket.setSuccess(true);
//                 System.out.println(new Date() + ": 登录成功!");
//             }else {
//                 loginResponsePacket.setReason("账号密码校验失败");
//                 loginResponsePacket.setSuccess(false);
//                 System.out.println(new Date() + ": 登录失败!");
//             }
//             // 登录响应
//             ByteBuf response = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
//             ctx.channel().writeAndFlush(response);
//         } else if (packet instanceof MessageRequestPacket) {
//             // 客户端发来消息
//             MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
//             System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());
//             //服务端回复
//             MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
//             messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
//             ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
//             ctx.channel().writeAndFlush(responseByteBuf);
//         }
//     }
//
//     private boolean valid(LoginRequestPacket loginRequestPacket) {
//         return true;
//     }
// }
