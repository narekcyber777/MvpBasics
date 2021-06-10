package com.narcyber.mvpbasics.utils;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class TextCustomizer {
    private final TextView textView;
    private TextCustomizerListener textCustomizerListener;

    private TextCustomizer(Builder builder) {
        this.textView = builder.textView;
        if (builder.textCustomizerListener != null)
            this.textCustomizerListener = builder.textCustomizerListener;
    }

    public interface TextCustomizerListener {
        void onClick(String eventName);
    }

    public static class Builder {
        private final TextView textView;
        private Spannable word;
        private TextCustomizerListener textCustomizerListener;

        public Builder(TextView textView) {
            this.textView = textView;
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }

        public Builder setText(String text) {
            this.word = new SpannableString(text);
            return this;
        }

        public Builder addSpace(int length) {
            final StringBuilder stringBuilder = new StringBuilder(" ");
            for (int i = 0; i < length; i++) {
                stringBuilder.append(" ");
            }
            this.word = new SpannableString(stringBuilder.toString());
            return this;
        }

        public Builder push() {
            if (word != null) {
                if (textView.getText().toString().isEmpty()) {
                    textView.setText(word);
                } else {
                    textView.append(word);
                }
            }
            return this;
        }

        public Builder setCallBack(TextCustomizerListener textCustomizerListener) {
            if (textCustomizerListener != null) {
                this.textCustomizerListener = textCustomizerListener;
            }
            return this;
        }

        public Builder makeClickable(String eventName) {
            if (word != null) {
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        if (textCustomizerListener != null) {
                            textCustomizerListener.onClick(eventName);
                        }
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(Color.parseColor("#2196F3"));
                        ds.setUnderlineText(false);
                        ds.bgColor = Color.WHITE;
                        textView.invalidate();
                    }
                };

                clickableSpan.updateDrawState(textView.getPaint());
                word.setSpan(clickableSpan, 0, word.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return this;
        }

        public Builder isBold(boolean isBold) {
            if (isBold) {
                StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
                word.setSpan(boldSpan, 0, word.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                StyleSpan normalSpan = new StyleSpan(Typeface.NORMAL);
                word.setSpan(normalSpan, 0, word.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return this;
        }

        public Builder setColor(int color) {
            if (word != null) {
                word.setSpan(new ForegroundColorSpan(color), 0, word.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return this;
        }

        public TextCustomizer build() {
            return new TextCustomizer(this);
        }
    }

}
