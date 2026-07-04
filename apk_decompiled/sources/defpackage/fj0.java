package defpackage;

import android.graphics.RectF;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class fj0 extends g30 {
    public final y5 a;

    public fj0(y5 y5Var) {
        this.a = y5Var;
    }

    @Override // defpackage.g30
    public final wo0 r() {
        y5 y5Var = this.a;
        if (y5Var.b == null) {
            y5Var.b = new RectF();
        }
        RectF rectF = y5Var.b;
        rectF.getClass();
        y5Var.a.computeBounds(rectF, true);
        return new wo0(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }
}
