package defpackage;

import android.graphics.Shader;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class rv0 extends CharacterStyle implements UpdateAppearance {
    public final qv0 e;
    public final float f;
    public final ik0 g = n30.B(new mw0(9205357640488583168L));
    public final ym h = n30.r(new f6(9, this));

    public rv0(qv0 qv0Var, float f) {
        this.e = qv0Var;
        this.f = f;
    }

    @Override // android.text.style.CharacterStyle
    public final void updateDrawState(TextPaint textPaint) {
        o20.C(textPaint, this.f);
        textPaint.setShader((Shader) this.h.getValue());
    }
}
