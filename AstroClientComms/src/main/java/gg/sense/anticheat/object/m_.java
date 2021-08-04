package gg.sense.anticheat.object;

//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.handler.codec.base64.Base64;
//
//import java.nio.charset.StandardCharsets;

public class m_ {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
//    public static n9 b(String string) {
//        ByteBuf byteBuf = Unpooled.wrappedBuffer((byte[])string.getBytes(StandardCharsets.UTF_8));
//        try {
//            n9 n92;
//            ByteBuf uJ2 = new ByteBuf(Base64.decode((ByteBuf)byteBuf));
//            try {
//                n92 = uJ2.();
//                uJ2.release();
//            }
//            catch (Throwable throwable) {
//                uJ2.release();
//                throw throwable;
//            }
//            return n92;
//        }
//        finally {
//            byteBuf.release();
//        }
//    }
//
//    public static n9 a(String string) {
//        try {
//            return m_.b(string);
//        }
//        catch (IOException iOException) {
//            iOException.printStackTrace();
//            return null;
//        }
//    }
}