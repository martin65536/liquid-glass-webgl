package defpackage;

import android.graphics.BlurMaskFilter;
import android.graphics.Paint;
import android.graphics.RuntimeShader;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class fy extends bd0 implements tp {
    public bw0 s;
    public vu t;
    public hx u;
    public final r5 v;
    public y5 w;
    public final wb0 x;

    public fy(bw0 bw0Var, vu vuVar) {
        this.s = bw0Var;
        this.t = vuVar;
        r5 f = o4.f();
        f.e(1);
        this.v = f;
        this.x = new wb0(1);
    }

    @Override // defpackage.tp
    public final void R(b50 b50Var) {
        y5 y5Var;
        BlurMaskFilter blurMaskFilter;
        RuntimeShader runtimeShader;
        yc ycVar = b50Var.e;
        dy dyVar = (dy) this.t.a();
        if (dyVar != null) {
            float f = dyVar.a;
            ky kyVar = dyVar.d;
            if (f > 0.0f) {
                b50Var.r();
                hx hxVar = this.u;
                if (hxVar != null) {
                    long ceil = ((((int) Math.ceil(Float.intBitsToFloat((int) (r8 >> 32)))) + 2) << 32) | ((((int) Math.ceil(Float.intBitsToFloat((int) (r8 & 4294967295L)))) + 2) & 4294967295L);
                    g30 b = this.s.g.b(ycVar.f.v(), b50Var.getLayoutDirection(), b50Var);
                    if (b instanceof hj0) {
                        y5Var = this.w;
                        if (y5Var == null) {
                            y5Var = a6.a();
                            this.w = y5Var;
                        }
                    } else {
                        y5Var = null;
                    }
                    long a = kyVar.a();
                    r5 r5Var = this.v;
                    r5Var.c(a);
                    float G = b50Var.G(f);
                    float b2 = mw0.b(b50Var.j()) / 2.0f;
                    if (G > b2) {
                        G = b2;
                    }
                    r5Var.a.setStrokeWidth(((float) Math.ceil(G)) * 2.0f);
                    float G2 = b50Var.G(dyVar.b);
                    r5Var.getClass();
                    Paint paint = r5Var.a;
                    if (G2 > 0.0f) {
                        blurMaskFilter = new BlurMaskFilter(G2, BlurMaskFilter.Blur.NORMAL);
                    } else {
                        blurMaskFilter = null;
                    }
                    paint.setMaskFilter(blurMaskFilter);
                    if (y20.n()) {
                        h6 b3 = kyVar.b(b50Var, this.s.g, this.x);
                        Paint paint2 = r5Var.a;
                        if (b3 != null) {
                            runtimeShader = b3.a;
                        } else {
                            runtimeShader = null;
                        }
                        paint2.setShader(runtimeShader);
                    }
                    hxVar.g(dyVar.c);
                    int u = kyVar.u();
                    jx jxVar = hxVar.a;
                    if (jxVar.u() != u) {
                        jxVar.k(u);
                    }
                    b50Var.M(hxVar, ceil, new zi(b, y5Var, this, 1));
                    ((j2) ycVar.f.f).q(-1.0f, -1.0f);
                    try {
                        n20.r(b50Var, hxVar);
                        return;
                    } finally {
                        ((j2) ycVar.f.f).q(1.0f, 1.0f);
                    }
                }
                return;
            }
        }
        b50Var.r();
    }

    @Override // defpackage.bd0
    public final boolean q0() {
        return false;
    }

    @Override // defpackage.bd0
    public final void t0() {
        this.u = ((n5) k81.C(this)).a();
    }

    @Override // defpackage.bd0
    public final void v0() {
        ex C = k81.C(this);
        hx hxVar = this.u;
        if (hxVar != null) {
            ((n5) C).c(hxVar);
            this.u = null;
        }
        this.w = null;
        this.x.e.clear();
    }

    @Override // defpackage.tp
    public final /* bridge */ void m0() {
    }
}
