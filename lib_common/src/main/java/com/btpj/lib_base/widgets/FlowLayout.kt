package com.btpj.lib_base.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.btpj.lib_base.R
import kotlin.math.max
import kotlin.math.min

/**
 * 流式布局
 *
 * @author LTP  2018/5/4
 */
class FlowLayout(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    /** 水平间距 */
    private var mChildHorizontalSpacing = 0

    /** 垂直间距 */
    private var mChildVerticalSpacing = 0

    /** 对齐方式，目前支持 [Gravity.CENTER_HORIZONTAL], [Gravity.LEFT] 和 [Gravity.RIGHT] */
    private var mGravity: Int = 0

    private var mMaxMode = LINES
    private var mMaximum = Integer.MAX_VALUE

    companion object {
        private const val LINES = 0
        private const val NUMBER = 1
    }

    /** 每一行的item数目，下标表示行下标，在onMeasured的时候计算得出，供onLayout去使用 */
    private lateinit var mItemNumberInEachLine: IntArray

    /** 每一行的item的宽度和（包括item直接的间距），下标表示行下标 */
    private lateinit var mWidthSumInEachLine: IntArray

    /** onMeasure过程中实际参与measure的子View个数 */
    private var measuredChildCount: Int = 0

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout)
        mChildHorizontalSpacing =
            typeArray.getDimensionPixelSize(R.styleable.FlowLayout_childHorizontalSpacing, 0)
        mChildVerticalSpacing =
            typeArray.getDimensionPixelSize(R.styleable.FlowLayout_childVerticalSpacing, 0)
        mGravity = typeArray.getInteger(R.styleable.FlowLayout_android_gravity, Gravity.START)
        val maxLines = typeArray.getInt(R.styleable.FlowLayout_android_maxLines, -1)
        if (maxLines >= 0) {
            setMaxLines(maxLines)
        }
        val maxNumber = typeArray.getInt(R.styleable.FlowLayout_maxNumber, -1)
        if (maxNumber >= 0) {
            setMaxNumber(maxNumber)
        }
        typeArray.recycle()
    }

    @SuppressLint("DrawAllocation", "SwitchIntDef")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        var maxLineHeight = 0
        var resultWidth: Int
        val resultHeight: Int
        val count = childCount
        mItemNumberInEachLine = IntArray(count)
        mWidthSumInEachLine = IntArray(count)
        var lineIndex = 0

        // 若FlowLayout指定了MATCH_PARENT或固定宽度，则需要使子View换行
        if (widthSpecMode == MeasureSpec.EXACTLY) {
            resultWidth = widthSpecSize
            measuredChildCount = 0
            // 下一个子View的position
            var childPositionX = paddingLeft
            var childPositionY = paddingTop
            // 子View的Right最大可达到的x坐标
            val childMaxRight = widthSpecSize - paddingRight

            for (i in 0 until count) {
                if (mMaxMode == NUMBER && measuredChildCount >= mMaximum) {
                    // 超出最多数量，则不再继续
                    break
                } else if (mMaxMode == LINES && lineIndex >= mMaximum) {
                    // 超出最多行数，则不再继续
                    break
                }

                val child = getChildAt(i)
                if (child.visibility == View.GONE) {
                    continue
                }

                val childLayoutParams = child.layoutParams
                val childWidthMeasureSpec = getChildMeasureSpec(
                    widthMeasureSpec,
                    paddingLeft + paddingRight,
                    childLayoutParams.width
                )
                val childHeightMeasureSpec = getChildMeasureSpec(
                    heightMeasureSpec,
                    paddingTop + paddingBottom,
                    childLayoutParams.height
                )
                child.measure(childWidthMeasureSpec, childHeightMeasureSpec)

                val childWidth = child.measuredWidth
                maxLineHeight = max(maxLineHeight, child.measuredHeight)
                // 需要换行
                if (childPositionX + childWidth > childMaxRight) {
                    // 如果换行后超出最大行数，则不再继续
                    if (mMaxMode == LINES) {
                        if (lineIndex + 1 >= mMaximum) {
                            break
                        }
                    }
                    // 后面每次加item都会加上一个space，这样的话每行都会为最后一个item多加一次space，所以在这里减一次
                    mWidthSumInEachLine[lineIndex] -= mChildHorizontalSpacing
                    lineIndex++ // 换行
                    childPositionX = paddingLeft // 下一行第一个item的x
                    childPositionY += maxLineHeight + mChildVerticalSpacing // 下一行第一个item的y
                }
                mItemNumberInEachLine[lineIndex]++
                mWidthSumInEachLine[lineIndex] += childWidth + mChildHorizontalSpacing
                childPositionX += childWidth + mChildHorizontalSpacing
                measuredChildCount++
            }
            // 如果最后一个item不是刚好在行末（即lineCount最后没有+1，也就是mWidthSumInEachLine[lineCount]非0），则要减去最后一个item的space
            if (mWidthSumInEachLine.isNotEmpty() && mWidthSumInEachLine[lineIndex] > 0) {
                mWidthSumInEachLine[lineIndex] -= mChildHorizontalSpacing
            }
            resultHeight = when (heightSpecMode) {
                MeasureSpec.UNSPECIFIED -> childPositionY + maxLineHeight + paddingBottom
                MeasureSpec.AT_MOST -> min(
                    childPositionY + maxLineHeight + paddingBottom,
                    heightSpecSize
                )
                else -> heightSpecSize
            }
        } else {
            // 不计算换行，直接一行铺开
            resultWidth = paddingLeft + paddingRight
            measuredChildCount = 0

            for (i in 0 until count) {
                if (mMaxMode == NUMBER) {
                    // 超出最多数量，则不再继续
                    if (measuredChildCount > mMaximum) {
                        break
                    }
                } else if (mMaxMode == LINES) {
                    // 超出最大行数，则不再继续
                    if (1 > mMaximum) {
                        break
                    }
                }
                val child = getChildAt(i)
                if (child.visibility == View.GONE) {
                    continue
                }
                val childLayoutParams = child.layoutParams
                val childWidthMeasureSpec = getChildMeasureSpec(
                    widthMeasureSpec,
                    paddingLeft + paddingRight,
                    childLayoutParams.width
                )
                val childHeightMeasureSpec = getChildMeasureSpec(
                    heightMeasureSpec,
                    paddingTop + paddingBottom,
                    childLayoutParams.height
                )
                child.measure(childWidthMeasureSpec, childHeightMeasureSpec)
                resultWidth += child.measuredWidth
                maxLineHeight = max(maxLineHeight, child.measuredHeight)
                measuredChildCount++
            }
            if (measuredChildCount > 0) {
                resultWidth += mChildHorizontalSpacing * (measuredChildCount - 1)
            }
            resultHeight = maxLineHeight + paddingTop + paddingBottom
            if (mItemNumberInEachLine.isNotEmpty()) {
                mItemNumberInEachLine[lineIndex] = count
            }
            if (mWidthSumInEachLine.isNotEmpty()) {
                mWidthSumInEachLine[0] = resultWidth
            }
        }
        setMeasuredDimension(resultWidth, resultHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val width = right - left
        // 按照不同gravity使用不同的布局，默认是left
        when (mGravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
            Gravity.START -> layoutWithGravityLeft(width)
            Gravity.END -> layoutWithGravityRight(width)
            Gravity.CENTER_HORIZONTAL -> layoutWithGravityCenterHorizontal(width)
            else -> layoutWithGravityLeft(width)
        }
    }

    /**
     * 将子View靠左布局
     */
    private fun layoutWithGravityLeft(parentWidth: Int) {
        val childMaxRight = parentWidth - paddingRight
        var childPositionX = paddingLeft
        var childPositionY = paddingTop
        var lineHeight = 0
        val childCount = childCount
        val childCountToLayout = min(childCount, measuredChildCount)
        for (i in 0 until childCountToLayout) {
            val child = getChildAt(i)
            if (child.visibility == View.GONE) {
                continue
            }
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
            lineHeight = max(lineHeight, childHeight)
            if (childPositionX + childWidth > childMaxRight) {
                childPositionX = paddingLeft
                childPositionY += lineHeight + mChildVerticalSpacing
                lineHeight = 0
            }
            child.layout(
                childPositionX,
                childPositionY,
                childPositionX + childWidth,
                childPositionY + childHeight
            )
            childPositionX += childWidth + mChildHorizontalSpacing
        }

        // 如果布局的子View少于childCount，则表示有一些子View不需要布局
        if (measuredChildCount < childCount) {
            for (i in measuredChildCount until childCount) {
                val child = getChildAt(i)
                if (child.visibility == View.GONE) {
                    continue
                }
                child.layout(0, 0, 0, 0)
            }
        }
    }

    /**
     * 将子View居中布局
     */
    private fun layoutWithGravityCenterHorizontal(parentWidth: Int) {
        var nextChildIndex = 0
        var nextChildPositionX: Int
        var nextChildPositionY = paddingTop
        var lineHeight = 0

        // 遍历每一行
        for (i in mItemNumberInEachLine.indices) {
            // 如果这一行已经没item了，则退出循环
            if (mItemNumberInEachLine[i] == 0) {
                break
            }

            if (nextChildIndex > measuredChildCount - 1) {
                break
            }

            // 遍历该行内的元素，布局每个元素
            nextChildPositionX =
                (parentWidth - paddingLeft - paddingRight - mWidthSumInEachLine[i]) / 2 + paddingLeft // 子 View 的最小 x 值
            for (j in nextChildIndex until nextChildIndex + mItemNumberInEachLine[i]) {
                val childView = getChildAt(j)
                if (childView.visibility == View.GONE) {
                    continue
                }
                val childWidth = childView.measuredWidth
                val childHeight = childView.measuredHeight
                childView.layout(
                    nextChildPositionX,
                    nextChildPositionY,
                    nextChildPositionX + childWidth,
                    nextChildPositionY + childHeight
                )
                lineHeight = max(lineHeight, childHeight)
                nextChildPositionX += childWidth + mChildHorizontalSpacing
            }

            // 一行结束了，整理一下，准备下一行
            nextChildPositionY += lineHeight + mChildVerticalSpacing
            nextChildIndex += mItemNumberInEachLine[i]
            lineHeight = 0
        }

        val childCount = childCount
        if (measuredChildCount < childCount) {
            for (i in measuredChildCount until childCount) {
                val childView = getChildAt(i)
                if (childView.visibility == View.GONE) {
                    continue
                }
                childView.layout(0, 0, 0, 0)
            }
        }
    }

    /**
     * 将子View靠右布局
     */
    private fun layoutWithGravityRight(parentWidth: Int) {
        var nextChildIndex = 0
        var nextChildPositionX: Int
        var nextChildPositionY = paddingTop
        var lineHeight = 0

        // 遍历每一行
        for (i in mItemNumberInEachLine.indices) {
            // 如果这一行已经没item了，则退出循环
            if (mItemNumberInEachLine[i] == 0) {
                break
            }

            if (nextChildIndex > measuredChildCount - 1) {
                break
            }

            // 遍历该行内的元素，布局每个元素
            nextChildPositionX =
                parentWidth - paddingRight - mWidthSumInEachLine[i] // 初始值为子 View 的最小 x 值
            for (j in nextChildIndex until nextChildIndex + mItemNumberInEachLine[i]) {
                val childView = getChildAt(j)
                if (childView.visibility == View.GONE) {
                    continue
                }
                val childWidth = childView.measuredWidth
                val childHeight = childView.measuredHeight
                childView.layout(
                    nextChildPositionX,
                    nextChildPositionY,
                    nextChildPositionX + childWidth,
                    nextChildPositionY + childHeight
                )
                lineHeight = max(lineHeight, childHeight)
                nextChildPositionX += childWidth + mChildHorizontalSpacing
            }

            // 一行结束了，整理一下，准备下一行
            nextChildPositionY += lineHeight + mChildVerticalSpacing
            nextChildIndex += mItemNumberInEachLine[i]
            lineHeight = 0
        }

        val childCount = childCount
        if (measuredChildCount < childCount) {
            for (i in measuredChildCount until childCount) {
                val childView = getChildAt(i)
                if (childView.visibility == View.GONE) {
                    continue
                }
                childView.layout(0, 0, 0, 0)
            }
        }
    }

    /**
     * 设置子 View 的对齐方式，目前支持 [Gravity.CENTER_HORIZONTAL], [Gravity.LEFT] 和 [Gravity.RIGHT]
     */
    fun setGravity(gravity: Int) {
        if (mGravity != gravity) {
            mGravity = gravity
            requestLayout()
        }
    }

    fun getGravity(): Int {
        return mGravity
    }

    /**
     * 获取最多可显示的行数
     *
     * @return 没有限制时返回-1
     */
    fun getMaxLines(): Int {
        return if (mMaxMode == LINES) mMaximum else -1
    }

    /**
     * 设置最多可显示的行数
     *
     * @param maxLines 最多可显示的行数
     */
    fun setMaxLines(maxLines: Int) {
        mMaximum = maxLines
        mMaxMode = LINES
        requestLayout()
    }

    /**
     * 获取最多可显示的子View个数
     */
    fun getMaxNumber(): Int {
        return if (mMaxMode == NUMBER) mMaximum else -1
    }

    /**
     * 设置最多可显示的子View个数
     *
     * @param maxNumber 最多可显示的子View个数
     */
    fun setMaxNumber(maxNumber: Int) {
        mMaximum = maxNumber
        mMaxMode = NUMBER
        requestLayout()
    }
}