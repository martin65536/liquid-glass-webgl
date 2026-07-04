package defpackage;

import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class zt extends MetricAffectingSpan {
    public final /* synthetic */ int e;
    public final Object f;

    public /* synthetic */ zt(int i, Object obj) {
        this.e = i;
        this.f = obj;
    }

    @Override // android.text.style.CharacterStyle
    public final void updateDrawState(TextPaint textPaint) {
        int i = this.e;
        Object obj = this.f;
        switch (i) {
            case 0:
                textPaint.setFontFeatureSettings((String) obj);
                return;
            default:
                textPaint.setTypeface((Typeface) obj);
                return;
        }
    }

    @Override // android.text.style.MetricAffectingSpan
    public final void updateMeasureState(TextPaint textPaint) {
        int i = this.e;
        Object obj = this.f;
        switch (i) {
            case 0:
                textPaint.setFontFeatureSettings((String) obj);
                return;
            default:
                textPaint.setTypeface((Typeface) obj);
                return;
        }
    }
}
