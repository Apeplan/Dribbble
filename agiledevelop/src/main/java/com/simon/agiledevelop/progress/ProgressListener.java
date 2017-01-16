package com.simon.agiledevelop.progress;

/**
 * describe:
 *
 * @author Apeplan
 * @date 2017/1/12
 * @email hanzx1024@gmail.com
 */

public interface ProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
