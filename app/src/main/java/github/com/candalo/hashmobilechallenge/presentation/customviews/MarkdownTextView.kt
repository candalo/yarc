package github.com.candalo.hashmobilechallenge.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import io.noties.markwon.Markwon

class MarkdownTextView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet?,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attr, defStyleAttr) {
    override fun setText(text: CharSequence?, type: BufferType?) {
        if (text != null) {
            val markwon = Markwon.create(context)
            super.setText(markwon.toMarkdown(text.toString()), type)
        } else {
            super.setText(text, type)
        }
    }
}