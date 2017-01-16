/*
 * Copyright (C) 2015 Qiujuer <qiujuer@live.cn>
 * WebSite http://www.qiujuer.net
 * Created 10/15/2015
 * Changed 12/06/2015
 * Author Qiujuer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.simon.agiledevelop.state.loading;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * describe: A drawable to draw loading
 *
 * @author Simon Han
 * @date 2016.10.19
 * @email hanzx1024@gmail.com
 */

public class LoadingCircleDrawable extends LoadingDrawable {
    private static final int ANGLE_ADD = 5;
    private static final int MIN_ANGLE_SWEEP = 3;
    private static final int MAX_ANGLE_SWEEP = 255;

    private RectF mBackgroundOval = new RectF();
    private RectF mForegroundOval = new RectF();

    private float mStartAngle;
    private float mSweepAngle;
    private int mAngleIncrement = 3;

    public LoadingCircleDrawable() {
        super();
    }

    public LoadingCircleDrawable(int minSize) {
        super(minSize);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

        int centerX = bounds.centerX();
        int centerY = bounds.centerY();

        int center = Math.min(centerX, centerY);
        int maxStrokeWidth = (int) Math.max(mForegroundPaint.getStrokeWidth(), mBackgroundPaint.getStrokeWidth());

        int areRadius = center - ((maxStrokeWidth) >> 1) - 1;
        mBackgroundOval.set(centerX - areRadius, centerY - areRadius, centerX + areRadius, centerY + areRadius);

        areRadius = center - ((maxStrokeWidth) >> 1) - 1;
        mForegroundOval.set(centerX - areRadius, centerY - areRadius, centerX + areRadius, centerY + areRadius);
    }

    @Override
    protected void onProgressChange(float progress) {
        mStartAngle = 0;
        mSweepAngle = 360 * progress;
    }

    @Override
    protected void refresh(long startTime, long curTime, long timeLong) {
        final float angle = ANGLE_ADD;
        mStartAngle += angle;

        if (mStartAngle > 360) {
            mStartAngle -= 360;
        }

        if (mSweepAngle > MAX_ANGLE_SWEEP) {
            mAngleIncrement = -mAngleIncrement;
        } else if (mSweepAngle < MIN_ANGLE_SWEEP) {
            mSweepAngle = MIN_ANGLE_SWEEP;
            return;
        } else if (mSweepAngle == MIN_ANGLE_SWEEP) {
            mAngleIncrement = -mAngleIncrement;
            getNextForegroundColor();
        }
        mSweepAngle += mAngleIncrement;
    }

    @Override
    protected void drawBackground(Canvas canvas, Paint backgroundPaint) {
        canvas.drawArc(mBackgroundOval, 0, 360, false, backgroundPaint);
    }

    @Override
    protected void drawForeground(Canvas canvas, Paint foregroundPaint) {
        canvas.drawArc(mForegroundOval, mStartAngle, -mSweepAngle, false, foregroundPaint);
    }
}