package defpackage;

import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class u9 extends MetricAffectingSpan {
    public final /* synthetic */ int e;
    public final float f;

    public /* synthetic */ u9(float f, int i) {
        this.e = i;
        this.f = f;
    }

    @Override // android.text.style.CharacterStyle
    public final void updateDrawState(TextPaint textPaint) {
        int i = this.e;
        float f = this.f;
        switch (i) {
            case 0:
                textPaint.baselineShift += (int) Math.ceil(textPaint.ascent() * f);
                return;
            default:
                textPaint.setTextSkewX(textPaint.getTextSkewX() + f);
                return;
        }
    }

    @Override // android.text.style.MetricAffectingSpan
    public final void updateMeasureState(TextPaint textPaint) {
        int i = this.e;
        float f = this.f;
        switch (i) {
            case 0:
                textPaint.baselineShift += (int) Math.ceil(textPaint.ascent() * f);
                return;
            default:
                textPaint.setTextSkewX(textPaint.getTextSkewX() + f);
                return;
        }
    }
}
