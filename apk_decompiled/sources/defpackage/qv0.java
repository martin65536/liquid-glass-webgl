package defpackage;

import android.graphics.Paint;
import android.graphics.Shader;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class qv0 extends jc0 {
    public j2 s;
    public long t = 9205357640488583168L;

    public abstract Shader N(long j);

    @Override // defpackage.jc0
    public final void g(float f, long j, r5 r5Var) {
        Shader shader;
        Paint paint = r5Var.a;
        j2 j2Var = this.s;
        Shader shader2 = null;
        if (j2Var == null || !mw0.a(this.t, j)) {
            if (mw0.c(j)) {
                this.s = null;
                this.t = 9205357640488583168L;
                j2Var = null;
            } else {
                j2Var = this.s;
                if (j2Var == null) {
                    j2Var = new j2(26, false);
                    this.s = j2Var;
                }
                j2Var.f = N(j);
                this.s = j2Var;
                this.t = j;
            }
        }
        long e = f31.e(paint.getColor());
        long j2 = se.b;
        if (!se.c(e, j2)) {
            r5Var.c(j2);
        }
        Shader shader3 = r5Var.c;
        if (j2Var != null) {
            shader = (Shader) j2Var.f;
        } else {
            shader = null;
        }
        if (!o20.e(shader3, shader)) {
            if (j2Var != null) {
                shader2 = (Shader) j2Var.f;
            }
            r5Var.c = shader2;
            paint.setShader(shader2);
        }
        if (paint.getAlpha() / 255.0f == f) {
            return;
        }
        r5Var.a(f);
    }
}
