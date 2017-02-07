package com.simon.agiledevelop.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.simon.agiledevelop.R;

/**
 * describe:
 *
 * @author Apeplan
 * @date 2017/2/6
 * @email hanzx1024@gmail.com
 */

public class AvatarImageView extends ImageView implements ViewTreeObserver
        .OnGlobalLayoutListener, ScaleGestureDetector.OnScaleGestureListener,View.OnTouchListener {


    private boolean isAutoScale;
    private boolean isOnce = true;
    private boolean isCanDrag;
    private Matrix mScaleMatrix;

    public float getScale() {
        return mScale;
    }

    /**
     * 控件类型选择
     */
    public enum TYPE {
        RECT, CIRCLE
    }

    /**
     * 头像截图类型，默认是矩形
     */
    private TYPE mType = TYPE.RECT;

    /**
     * 设置绘制的形状
     */
    public TYPE getmType() {
        return mType;
    }

    public void setmType(TYPE mType) {
        this.mType = mType;
    }

    /**
     * 水平方向与View的边距,可通过属性进行设置
     */
    private int mHorizontalPadding = 30;
    /**
     * 垂直方向与View的边距
     */
    private int mVerticalPadding;
    /**
     * 圆的直径或者矩形宽度
     */
    private int mWidthOrRadius = 30;
    /**
     * 默认的边界颜色
     */
    private int mBorderColor = Color.parseColor("#FFFFFF");
    /**
     * 边界的宽度
     */
    private int mBorderWid = 1;
    /**
     * 画笔
     */
    private Paint mRectPaint;

    public AvatarImageView(Context context) {
        this(context,null);
    }

    public AvatarImageView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public AvatarImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //  设置缩放模式为矩阵
        super.setScaleType(ScaleType.MATRIX);
        mScaleMatrix = new Matrix();
        mScaleGestureDetector = new ScaleGestureDetector(context, this);//手势
        this.setBackgroundColor(Color.BLACK);
        /**
         * 给手势添加双击监听事件
         */
        mGestureDetector = new GestureDetector(context, new GestureDetector
                .SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                //正在自动缩放,则不处理这次双击
                if (isAutoScale)
                    return true;
                //事件点击位置
                float x = e.getX();
                float y = e.getY();
                //小于2.0f，放大至2倍
                //Log.e("DoubleTap", getScale() + " , " + mScale);
                if (getScale() < SCALE_MID) {
                    AvatarImageView.this.postDelayed(new AutoScaleThread(x, y, SCALE_MID),
                            16);
                    isAutoScale = true;
                }
                //2.0f~4.0f
                else if (getScale() >= SCALE_MID && getScale() < SCALE_MAX) {
                    AvatarImageView.this.postDelayed(new AutoScaleThread(x, y, SCALE_MAX),
                            16);
                    isAutoScale = true;
                } else {
                    AvatarImageView.this.postDelayed(new AutoScaleThread(x, y, mScale), 16);
                    isAutoScale = true;
                }
                return true;
            }
        });

        this.mBorderWid = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mBorderWid,
                getResources().getDisplayMetrics());
        this.mWidthOrRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                mWidthOrRadius,
                getResources().getDisplayMetrics());
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AvatarImageView);
        this.mHorizontalPadding = (int) a.getDimension(R.styleable.AvatarImageView_avatar_width,
                30) + 5;
        a.recycle();
        this.mRectPaint = new Paint();
        mRectPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mType == TYPE.RECT) {
            //计算矩形截图区域宽度
            mWidthOrRadius = getWidth() - 2 * mHorizontalPadding;
            //计算垂直方向的间距
            mVerticalPadding = (getHeight() - mWidthOrRadius) / 2;
            //设置画笔的透明度以及颜色
            mRectPaint.setColor(Color.parseColor("#50000000"));
            mRectPaint.setStyle(Paint.Style.FILL);
            //绘制左边矩形
            canvas.drawRect(0, 0, mHorizontalPadding, getHeight(), mRectPaint);
            //绘制右边矩形
            canvas.drawRect(getWidth() - mHorizontalPadding, 0, getWidth(), getHeight(),
                    mRectPaint);
            //绘制上边矩形
            canvas.drawRect(mHorizontalPadding, 0, getWidth() - mHorizontalPadding,
                    mVerticalPadding, mRectPaint);
            //绘制下边矩形
            canvas.drawRect(mHorizontalPadding, getHeight() - mVerticalPadding, getWidth() -
                    mHorizontalPadding, getHeight(), mRectPaint);
            // 绘制截图边框
            mRectPaint.setColor(mBorderColor);
            mRectPaint.setStrokeWidth(mBorderWid);
            mRectPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(mHorizontalPadding, mVerticalPadding, getWidth() - mHorizontalPadding,
                    getHeight() - mVerticalPadding, mRectPaint);
        } else if (mType == TYPE.CIRCLE) {
            /**
             * 利用Xfermode进行设置：Xor挖空中间显示部分
             */
            //计算圆形的半径
            mWidthOrRadius = getWidth() - 2 * mHorizontalPadding;
            //计算垂直方向的间距
            mVerticalPadding = (getHeight() - mWidthOrRadius) / 2;
            //遮罩层的画笔设置
            mRectPaint.setColor(Color.parseColor("#50000000"));
            mRectPaint.setStyle(Paint.Style.FILL);
            //阴影层为整个屏幕大小(整个控件)
            RectF rectF = new RectF(0, 0, getWidth(), getHeight());
            // 绘制不透明的实心圆画笔
            Paint mPaintCirle = new Paint();
            mPaintCirle.setStrokeWidth(mWidthOrRadius / 2);
            mPaintCirle.setARGB(255, 0, 0, 0);//黑色不透明

            Paint mCirclePaint = new Paint();
            //圆形画笔设置
            mCirclePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
            //阴影层图像
            Bitmap mCircleBmp = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config
                    .ARGB_8888);
            Canvas mCanvas = new Canvas(mCircleBmp);
            //绘制阴影层（整个屏幕）
            mCanvas.drawRect(rectF, mRectPaint);
            //绘制实心圆,绘制完后，在mCanvas画布中，mPaintRect和mPaintCirle相交部分即被掏空
            mCanvas.drawCircle(getWidth() / 2, getHeight() / 2, mWidthOrRadius / 2, mPaintCirle);
            mCanvas.drawCircle(getWidth() / 2, getHeight() / 2, mWidthOrRadius / 2, mCirclePaint);
            //将扣空之后的阴影画布添加到原来的画布canvas中
            canvas.drawBitmap(mCircleBmp, null, rectF, new Paint());
            //绘制圆环
            mRectPaint.setColor(mBorderColor);
            mRectPaint.setStrokeWidth(mBorderWid);
            mRectPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, mWidthOrRadius / 2, mRectPaint);
        }
    }

    /**
     * View添加到Window，在视图树中添加GlobalLayout监听
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    /**
     * 对控件图片资源进行缩放，若大于屏幕长宽，则缩放至屏幕大小
     * 1：长比屏幕长；
     * 2：宽比屏幕框；
     * 3：长宽都长
     */
    @Override
    public void onGlobalLayout() {//整个视图树布局发生变化时触发(该方法会被调用多次)
        if (isOnce) {
            Drawable drawable = getDrawable();//获取图片资源
            if (drawable == null)
                return;
            int viewWid = getWidth();//控件宽
            int viewHei = getHeight();//控件高
            //图片宽高
            int imgWid = drawable.getIntrinsicWidth();
            int imgHei = drawable.getIntrinsicHeight();
            float scale = 1.0f;
            //图片宽度大小屏幕（控件），高度小于等于屏幕，则缩放至屏幕的宽或者高
            if (imgWid > viewWid && imgHei <= viewHei) {
                scale = viewWid * 1.0f / imgWid;
            }
            if (imgHei > viewHei && imgWid <= viewWid) {//图片高度大于屏幕
                scale = viewHei * 1.0f / imgHei;
            }
            // 如果宽和高都大于屏幕，则按比例最小适应屏幕大小
            if (imgWid > viewWid && imgHei > viewHei) {
                scale = Math.min(viewHei * 1.0f / imgHei, viewWid * 1.0f / imgWid);
            }
            mScale = scale;
            /**
             * 计算以中间矩形或者圆形的外接矩形最小区域变成为最小边界并计算缩放比例
             */
            //计算最小的缩放比例
            SCALE_MIN = Math.max(mWidthOrRadius * 1.0f / imgWid, mWidthOrRadius * 1.0f / imgHei)
                    > 0.0f ?
                    Math.max(mWidthOrRadius * 1.0f / imgWid, mWidthOrRadius * 1.0f / imgHei) : 1.0f;
            Log.e("SCALE", SCALE_MIN + ", " + SCALE_MAX + ", " + SCALE_MID);
            //将图片缩放并移动至屏幕中心
            mScaleMatrix.postTranslate((viewWid - imgWid) / 2, (viewHei - imgHei) / 2);
            mScaleMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
            setImageMatrix(mScaleMatrix);//重绘ImageView显示
            isOnce = false;
        }
    }

    /**
     * 最大、中等缩放比例
     */
    private static final float SCALE_MAX = 4.0f;
    private static final float SCALE_MID = 2.0f;
    private float SCALE_MIN;//最小缩放比例需要在控件显示后计算
    /**
     * 图片缩放比例：默认大小不变
     */
    private float mScale = 1.0f;
    /**
     * 缩放的手势检测
     */
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;

    /**
     * 根据手势的变化，缩放图片
     *
     * @param detector
     * @return
     */
    public boolean onScale(ScaleGestureDetector detector) {
        //获得当前的缩放比例
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();//获得手势缩放因子（将要缩放的比例）
        if (getDrawable() == null)
            return true;//不是一个缩放手势
        /**
         * 缩放范围
         * 1：放大比例
         * 2：缩小比例
         */
        if ((scale < SCALE_MAX && scaleFactor > 1.0f) || (scale >= mScale && scaleFactor < 1.0f)) {
            //缩小
            if ((scaleFactor * scale) < SCALE_MIN) {
                scaleFactor = SCALE_MIN / scale;
            }
            //当前手势要放大的比例大于最大比例，则最多扩大得到最大比例
            if (scaleFactor * scale > SCALE_MAX) {
                scaleFactor = SCALE_MAX / scale;
            }
            //以图片中心为缩放点
            //mScaleMatrix.postScale(scaleFactor,scaleFactor,getWidth()/2,getHeight()/2);
            //以手势点击处为中心点进行缩放
            mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector
                    .getFocusY());
            //边界判断
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
        }
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    /**
     * 在手势进行缩放时，需要进行边界检测，移动图片至屏幕中间，避免缩放出现白边
     */
    private void checkBorderAndCenterWhenScale() {
        RectF rectF = getMatrixRectF();
        //X、Y方向偏移量
        float deltaX = 0;
        float deltaY = 0;
        int wid = getWidth();
        int hei = getHeight();
        //当前图片宽大于屏幕，则控制范围
        if (rectF.width() > wid) {
            if (rectF.left > mHorizontalPadding) {//左边出现了空白区域
                // deltaX = -rectF.left;
                deltaX = -(rectF.left - mHorizontalPadding);//只是回到框的边界处
            }
            if (rectF.right < wid - mHorizontalPadding) {//右边出现空白区域
                //deltaX = wid - rectF.right;
                deltaX = wid - rectF.right - mHorizontalPadding;
            }
        }
        //当前图片高大于屏幕，则控制范围
        if (rectF.height() > hei) {
            if (rectF.top > mVerticalPadding) {
                deltaY = -(rectF.top - mVerticalPadding);
            }
            if (rectF.bottom < hei - mVerticalPadding) {
                deltaY = hei - rectF.bottom - mVerticalPadding;
            }
        }
        //若图片宽高小于屏幕，则让其居中
        if (rectF.width() < wid) {
            deltaX = wid * 0.5f - rectF.right + 0.5f * rectF.width();
        }
        if (rectF.height() < hei) {
            deltaY = hei * 0.5f - rectF.bottom + 0.5f * rectF.height();
        }
        //平移(应该加一个动画延迟)
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 根据当前的缩放矩阵计算图片当前的位置范围
     *
     * @return
     */
    private RectF getMatrixRectF() {
        Matrix m = mScaleMatrix;
        RectF rectF = new RectF();
        Drawable d = getDrawable();
        if (d != null) {
            //整张图的四个顶点
            rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            //根据矩阵变换图片的四个顶点位置
            m.mapRect(rectF);
        }
        return rectF;
    }

    /**
     * 移动滑动距离阈值
     */
    private float mTouchSlop;
    /**
     * 触点位置坐标
     */
    private float mLastX;
    private float mLastY;
    private int lastPointCount = 0;//上一次触点的个数
    /**
     * 在移动时需要判断当前图片宽高是否小于屏幕大小,需不需要进行调整移动
     */
    private boolean isCheckLeftAndRight = false;
    private boolean isCheckTopAndBottom = false;

    /**
     * 移动图片
     *
     * @param event
     * @return
     */



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);//手势接受Touch事件
        float x = 0;
        float y = 0;
        //触点个数并计算它们的均值
        final int pointCount = event.getPointerCount();
        for (int i = 0; i < pointCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x = x / pointCount;
        y = y / pointCount;
        //还不能拖动之前一直更新位置
        if (pointCount != lastPointCount) {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }
        lastPointCount = pointCount;
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - mLastX;//
                float dy = y - mLastY;
                if (!isCanDrag) {
                    isCanDrag = isCanDrag(dx, dy);
                }
                if (isCanDrag) {
                    //可以拖动
                    RectF rectF = getMatrixRectF();//当前图片位置
                    if (getDrawable() != null) {
                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                        //若图片宽度小于截图区域宽度，则禁止左右移动
                        if (rectF.width() < mWidthOrRadius) {
                            dx = 0;
                            isCheckLeftAndRight = false;
                        }
                        if (rectF.height() < mWidthOrRadius) {
                            dy = 0;
                            isCheckTopAndBottom = false;
                        }
                        mScaleMatrix.postTranslate(dx, dy);//移动
                        //检测移动是否小于屏幕，不让出现白边
                        checkMoveBounds();
                        setImageMatrix(mScaleMatrix);
                    }
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                lastPointCount = 0;
                break;
        }
        return true;
    }

    /**
     * 移动时，进行边界判断
     */
    private void checkMoveBounds() {
        //当前图片的位置
        RectF rectF = getMatrixRectF();
        float deltaX = 0, deltaY = 0;
        final float viewWidth = getWidth();
        final float viewHeight = getHeight();
        // 判断移动或缩放后，图片显示是否超出截图边界
        if (rectF.top > mVerticalPadding && isCheckTopAndBottom) {
            deltaY = -(rectF.top - mVerticalPadding);//需要偏移的距离
        }
        if (rectF.bottom < viewHeight - mVerticalPadding && isCheckTopAndBottom) {
            deltaY = viewHeight - (rectF.bottom + mVerticalPadding);
        }
        if (rectF.left > mHorizontalPadding && isCheckLeftAndRight) {
            deltaX = -(rectF.left - mHorizontalPadding);
        }
        if (rectF.right < viewWidth - mHorizontalPadding && isCheckLeftAndRight) {
            deltaX = viewWidth - (rectF.right + mHorizontalPadding);
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);//移动图像
    }

    /**
     * 判断当前移动的距离是否满足阈值
     *
     * @param dx
     * @param dy
     * @return
     */
    private boolean isCanDrag(float dx, float dy) {
        return Math.sqrt((dx * dx) + (dy * dy)) <= mTouchSlop;
    }

    /**
     * 缩放延迟线程（动画过度）
     */
    private class AutoScaleThread implements Runnable {
        static final float BIGGER = 1.07f;//每次放大的一个比例
        static final float SMALLER = 0.93f;//缩小
        /**
         * 缩放的位置坐标
         */
        private float x;
        private float y;
        private float mTargetScale;
        private float tmpScale;

        public AutoScaleThread(float x, float y, float scale) {
            this.x = x;
            this.y = y;
            this.mTargetScale = scale;
            //当前应该缩小还是放大
            if (getScale() < mTargetScale) {//当前值小于mTargetScale
                tmpScale = BIGGER;
            } else {
                tmpScale = SMALLER;
            }
        }

        /**
         * 传入目标缩放值，根据目标值与当前值，判断应该放大还是缩小
         */
        @Override
        public void run() {
            //每次都是以tmpScale与目标缩放值之间的比例进行缩放，每次放大一点
            mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
            //缩放之后的scale是否合法
            final float currentScale = getScale();
            Log.d("AutoTrhead", "currentScale：" + currentScale);
            if (((currentScale < mTargetScale) && (tmpScale > 1.0f)) ||
                    ((currentScale > mTargetScale) && (tmpScale < 1.0f))) {
                AvatarImageView.this.postDelayed(this, 16);
            } else {
                //这时比例已经超出目标scale了,需要还原
                final float deltaScale = mTargetScale / currentScale;
                mScaleMatrix.postScale(deltaScale, deltaScale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);
                isAutoScale = false;
            }
        }
    }

    /**
     * 按照当前的位置剪切头像
     *
     * @return
     */
    public Bitmap clipBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        //将屏幕上的图像绘制到当前canvas上
        draw(canvas);
        if (getmType() == TYPE.RECT) {
            //按照中间截图去的大小创建图像
            return Bitmap.createBitmap(bitmap, mHorizontalPadding, mVerticalPadding,
                    getWidth() - 2 * mHorizontalPadding, getHeight() - 2 * mVerticalPadding);
        } else if (getmType() == TYPE.CIRCLE) {
            return getCircleBitmap(Bitmap.createBitmap(bitmap, mHorizontalPadding, mVerticalPadding,
                    getWidth() - 2 * mHorizontalPadding, getHeight() - 2 * mVerticalPadding));
        }
        return null;
    }

    /**
     * 头像为圆形时,在矩形区域的基础之上剪切
     *
     * @return
     */
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap outBmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config
                .ARGB_8888);
        Canvas canvas = new Canvas(outBmp);
        final int color = 0xff424242;
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        int x = bitmap.getWidth();
        canvas.drawCircle(x / 2, x / 2, x / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return outBmp;
    }

}
