package com.btpj.lib_base.widgets

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.btpj.lib_base.R
import com.btpj.lib_base.databinding.LayoutTitleBinding
import com.btpj.lib_base.utils.ScreenUtil

/**
 * 封装的Title标题栏
 * 比Toolbar更好用,当然没有Toolbar那么强大,不过通常的功能均能更好的满足,不满足的再用Toolbar就行了
 *
 * @author LTP 16/9/19.
 */
class TitleLayout(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var mBinding: LayoutTitleBinding

    init {
        // 自定义TitleLayout的相关属性
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleLayout)
        val titleBackgroundColor = typedArray.getColor(
            R.styleable.TitleLayout_titleBackgroundColor,
            ContextCompat.getColor(context, R.color.purple_500)
        )
        val backIconRes =
            typedArray.getResourceId(R.styleable.TitleLayout_backIconRes, R.drawable.ic_back)
        val isShowBack = typedArray.getBoolean(R.styleable.TitleLayout_isShowBack, true)
        val titleTextColor = typedArray.getColor(
            R.styleable.TitleLayout_titleTextColor,
            ContextCompat.getColor(context, R.color._ffffff)
        )
        val titleTextSize = typedArray.getDimensionPixelSize(
            R.styleable.TitleLayout_titleTextSize,
            ScreenUtil.sp2px(context, 20f)
        )
        val titleText = typedArray.getString(R.styleable.TitleLayout_titleText)
        typedArray.recycle()

        // 自定义TitleLayout的布局
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_title,
            this,
            true
        )

        // 设置TitleLayout的背景色
        mBinding.clTitleBar.setBackgroundColor(titleBackgroundColor)

        // TitleBar的返回键
        mBinding.ivBack.apply {
            visibility = if (isShowBack) View.VISIBLE else View.GONE
            setImageResource(backIconRes)
            setOnClickListener { (context as Activity).onBackPressed() }
        }

        // TitleBar的标题文本
        mBinding.tvTitleText.apply {
            setTextColor(titleTextColor)
            setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize.toFloat())
            text = titleText
            isSelected = true
        }
    }

    /**
     * 设置返回键图标
     *
     * @param resId 返回键图标Id
     */
    fun setBackIcon(resId: Int): TitleLayout {
        mBinding.ivBack.setImageResource(resId)
        return this
    }

    /**
     * 设置Title背景色
     *
     * @param titleBackgroundColor Title背景色
     */
    fun setTitleBackgroundColor(titleBackgroundColor: Int): TitleLayout {
        mBinding.clTitleBar.setBackgroundColor(titleBackgroundColor)
        return this
    }

    /**
     * 设置Title左侧的返回键是否显示
     *
     * @param isVisible Title左侧的返回键是否显示
     */
    fun setBackVisible(isVisible: Boolean): TitleLayout {
        mBinding.ivBack.visibility = if (isVisible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * 设置Title中间的标题文本名
     *
     * @param titleText Title中间的标题文本名
     */
    fun setTitleText(titleText: String): TitleLayout {
        mBinding.tvTitleText.text = titleText
        return this
    }

    /**
     * 设置Title中间的标题文本颜色
     *
     * @param titleTextColor Title中间的标题文本颜色
     */
    fun setTitleTextColor(titleTextColor: Int): TitleLayout {
        mBinding.tvTitleText.setTextColor(titleTextColor)
        return this
    }

    /**
     * 设置Title中间的标题文本大小
     *
     * @param titleTextSize Title中间的标题文本大小
     */
    fun setTitleTextSize(titleTextSize: Int): TitleLayout {
        mBinding.tvTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize.toFloat())
        return this
    }

    /**
     * 设置Title右测的TextView编辑菜单
     *
     * @param text            Title右测的TextView编辑菜单文本
     * @param onClickListener 菜单点击回调
     */
    fun setRightView(text: String, onClickListener: OnClickListener): TitleLayout {
        mBinding.apply {
            ivMenu.visibility = View.GONE
            tvMenu.apply {
                visibility = View.VISIBLE
                this.text = text
                setOnClickListener(onClickListener)
            }
        }
        return this
    }

    /**
     * 设置Title右测的TextView编辑菜单
     *
     * @param rightViewBackground   Title右测的TextView背景色
     */
    fun setRightViewBackground(rightViewBackground: Int): TitleLayout {
        mBinding.apply {
            ivMenu.visibility = View.GONE
            tvMenu.apply {
                visibility = View.VISIBLE
                setBackgroundColor(rightViewBackground)
            }
        }
        return this
    }

    /**
     * 设置Title右测的TextView编辑菜单
     *
     * @param text            Title右测的TextView编辑菜单文本
     * @param textColor       Title右测的TextView编辑菜单文本颜色
     * @param onClickListener 菜单点击回调
     */
    fun setRightView(text: String, textColor: Int, onClickListener: OnClickListener): TitleLayout {
        mBinding.apply {
            ivMenu.visibility = View.GONE
                .apply {
                    tvMenu.apply {
                        visibility = View.VISIBLE
                        this.text = text
                        setTextColor(textColor)
                        setOnClickListener(onClickListener)
                    }
                }
        }
        return this
    }

    /**
     * 设置Title右测的TextView编辑菜单
     *
     * @param text            Title右测的TextView编辑菜单文本
     */
    fun setRightView(text: String): TitleLayout {
        mBinding.apply {
            ivMenu.visibility = View.GONE
                .apply {
                    tvMenu.apply {
                        visibility = View.VISIBLE
                        this.text = text
                    }
                }
        }
        return this
    }

    /**
     * 设置Title右测的ImageView编辑菜单
     *
     * @param imageRes        Title右测的ImageView编辑菜单ImageViewResource
     * @param onClickListener 菜单点击回调
     */
    fun setRightView(imageRes: Int, onClickListener: OnClickListener): TitleLayout {
        mBinding.apply {
            tvMenu.visibility = View.GONE
            ivMenu.apply {
                visibility = View.VISIBLE
                setImageResource(imageRes)
                setOnClickListener(onClickListener)
            }
        }
        return this
    }

    /**
     * 设置Title右测的ImageView编辑菜单
     *
     * @param imageRes        Title右测的ImageView编辑菜单ImageViewResource
     */
    fun setRightView(imageRes: Int): TitleLayout {
        mBinding.apply {
            tvMenu.visibility = View.GONE
            ivMenu.apply {
                visibility = View.VISIBLE
                setImageResource(imageRes)
            }
        }
        return this
    }

    /**
     * 设置Title右侧的菜单上的提示红点是否显示
     *
     * @param isVisible 红点是否显示
     */
    fun setRedViewVisible(isVisible: Boolean): TitleLayout {
        mBinding.viewRed.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        return this
    }

    /**
     * 设置左键的点击事件
     */
    fun setLeftOnclick(l: OnClickListener): TitleLayout {
        mBinding.ivBack.setOnClickListener(l)
        return this
    }
}
