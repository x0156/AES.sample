package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

/**
 *
 * @author x
 */
public class PipedStreamingOutput implements StreamingOutput {

    private final InputStream in;

    private final byte[] buffer;

    public static PipedStreamingOutput from(InputStream src) {
        return new PipedStreamingOutput(src);
    }

    public PipedStreamingOutput(InputStream src, int bufferSize) {
        this.in = src;
        buffer = new byte[bufferSize];
    }

    public PipedStreamingOutput(InputStream src) {
        this(src, 2048);
    }

    @Override
    public void write(OutputStream out) throws IOException, WebApplicationException {
        int n;
        try (InputStream src = this.in; OutputStream dest = out) {
            while ((n = src.read(buffer)) > -1) {
                dest.write(buffer, 0, n);
                dest.flush();
            }
        }
    }

}
