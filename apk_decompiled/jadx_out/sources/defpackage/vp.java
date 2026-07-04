package defpackage;

import android.graphics.Paint;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class vp extends CharacterStyle implements UpdateAppearance {
    public final jc0 e;

    public vp(jc0 jc0Var) {
        this.e = jc0Var;
    }

    @Override // android.text.style.CharacterStyle
    public final void updateDrawState(TextPaint textPaint) {
        Paint.Join join;
        Paint.Cap cap;
        if (textPaint != null) {
            yr yrVar = yr.s;
            jc0 jc0Var = this.e;
            if (o20.e(jc0Var, yrVar)) {
                textPaint.setStyle(Paint.Style.FILL);
                return;
            }
            if (jc0Var instanceof cz0) {
                textPaint.setStyle(Paint.Style.STROKE);
                cz0 cz0Var = (cz0) jc0Var;
                textPaint.setStrokeWidth(cz0Var.s);
                textPaint.setStrokeMiter(cz0Var.t);
                int i = cz0Var.v;
                if (i == 0) {
                    join = Paint.Join.MITER;
                } else if (i == 1) {
                    join = Paint.Join.ROUND;
                } else if (i == 2) {
                    join = Paint.Join.BEVEL;
                } else {
                    join = Paint.Join.MITER;
                }
                textPaint.setStrokeJoin(join);
                int i2 = cz0Var.u;
                if (i2 == 0) {
                    cap = Paint.Cap.BUTT;
                } else if (i2 == 1) {
                    cap = Paint.Cap.ROUND;
                } else if (i2 == 2) {
                    cap = Paint.Cap.SQUARE;
                } else {
                    cap = Paint.Cap.BUTT;
                }
                textPaint.setStrokeCap(cap);
                textPaint.setPathEffect(null);
                return;
            }
            v7.k();
        }
    }
}
