package com.simon.agiledevelop.progress;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * describe:
 *
 * @author Apeplan
 * @date 2017/1/12
 * @email hanzx1024@gmail.com
 */

public class ProgressResponseBody extends ResponseBody {

    private final ResponseBody responseBody;
    private final ProgressListener progressListener;
    private BufferedSource bufferedSource;

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {

        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }

        return bufferedSource;
    }

    private Source source(BufferedSource source) {
        return new ForwardingSource(source) {
            long totalBetysRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long betysRead = super.read(sink, byteCount);
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBetysRead += betysRead != -1 ? betysRead : 0;
                if (progressListener != null) {
                    progressListener.update(totalBetysRead, responseBody.contentLength(),
                            betysRead == -1);
                }
                return betysRead;
            }
        };
    }
}
