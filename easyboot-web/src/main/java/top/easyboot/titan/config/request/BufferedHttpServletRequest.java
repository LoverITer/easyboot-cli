package top.easyboot.titan.config.request;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import lombok.SneakyThrows;
import org.springframework.util.Assert;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 默认request的inputStream只能获取一次，因此自定义request对象，获取request inputStream
 *
 * @author: frank.huang
 * @date: 2021-11-07 17:04
 */
public class BufferedHttpServletRequest extends HttpServletRequestWrapper {

    private final ByteBuf buffer;

    private final AtomicBoolean isCached = new AtomicBoolean();

    public BufferedHttpServletRequest(HttpServletRequest request, int initialCapacity) {
        super(request);
        int contentLength = request.getContentLength();
        int min = Math.min(initialCapacity, contentLength);
        if (min < 0) {
            buffer = Unpooled.buffer(0);
        } else {
            buffer = Unpooled.buffer(min, contentLength);
        }
    }


    @Override
    public ServletInputStream getInputStream() throws IOException {
        //Only returning data from buffer if it is readonly, which means the underlying stream is EOF or closed.
        if (isCached.get()) {
            return new NettyServletInputStream(buffer);
        }
        return new ContentCachingInputStream(super.getInputStream());
    }

    public void release() {
        buffer.release();
    }

    private class ContentCachingInputStream extends ServletInputStream {

        private final ServletInputStream is;

        public ContentCachingInputStream(ServletInputStream is) {
            this.is = is;
        }

        @Override
        public int read() throws IOException {
            int ch = this.is.read();
            if (ch != -1) {
                //Stream is EOF, set this buffer to readonly state
                buffer.writeByte(ch);
            } else {
                isCached.compareAndSet(false, true);
            }
            return ch;
        }

        @Override
        public void close() throws IOException {
            //Stream is closed, set this buffer to readonly state
            try {
                is.close();
            } finally {
                isCached.compareAndSet(false, true);
            }
        }

        @Override
        public boolean isFinished() {
            throw new UnsupportedOperationException("Not yet implemented!");
        }

        @Override
        public boolean isReady() {
            throw new UnsupportedOperationException("Not yet implemented!");
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException("Not yet implemented!");
        }
    }

    private static class NettyServletInputStream extends ServletInputStream {

        private final ByteBufInputStream in;
        private final ByteBuf buffer;

        /**
         * Create a ServletInputStream based on the byteBuff. The byteBuf passed in is wrapped using {@link Unpooled#wrappedBuffer(ByteBuf)} }.
         *
         * @param byteBuf a readable ByteBuf
         */
        public NettyServletInputStream(ByteBuf byteBuf) {
            Assert.isTrue(byteBuf.isReadable(), "BytBuf is not readable!");
            buffer = Unpooled.wrappedBuffer(byteBuf);
            in = new ByteBufInputStream(buffer);
        }

        @Override
        public int read() throws IOException {
            return this.in.read();
        }

        @Override
        public int read(byte[] buf) throws IOException {
            return this.in.read(buf);
        }

        @Override
        public int read(byte[] buf, int offset, int len) throws IOException {
            return this.in.read(buf, offset, len);
        }

        @Override
        public void close() throws IOException {
            buffer.release();
        }

        @Override
        public boolean isFinished() {
            throw new UnsupportedOperationException("Not yet implemented!");
        }

        @SneakyThrows
        @Override
        public boolean isReady() {
            throw new UnsupportedOperationException("Not yet implemented!");
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException("Not yet implemented!");
        }
    }
}

