package defpackage;

import android.text.TextPaint;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class tl {
    public static final ThreadLocal b = new ThreadLocal();
    public final TextPaint a;

    public tl() {
        TextPaint textPaint = new TextPaint();
        this.a = textPaint;
        textPaint.setTextSize(10.0f);
    }
}
