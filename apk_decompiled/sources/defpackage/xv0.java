package defpackage;

import android.graphics.BlurMaskFilter;
import android.graphics.Paint;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class xv0 extends bd0 implements tp {
    public bw0 s;
    public vu t;
    public hx u;
    public final r5 v = o4.f();

    public xv0(bw0 bw0Var, vu vuVar) {
        this.s = bw0Var;
        this.t = vuVar;
    }

    @Override // defpackage.tp
    public final void R(b50 b50Var) {
        BlurMaskFilter blurMaskFilter;
        yc ycVar = b50Var.e;
        sv0 sv0Var = (sv0) this.t.a();
        if (sv0Var == null) {
            b50Var.r();
            return;
        }
        long j = sv0Var.b;
        float f = sv0Var.a;
        hx hxVar = this.u;
        if (hxVar != null) {
            long v = ycVar.f.v();
            m40 layoutDirection = b50Var.getLayoutDirection();
            final float G = b50Var.G(f);
            final float G2 = b50Var.G(Float.intBitsToFloat((int) (j >> 32)));
            final float G3 = b50Var.G(Float.intBitsToFloat((int) (j & 4294967295L)));
            float f2 = 4.0f * G;
            long ceil = (((int) Math.ceil((Float.intBitsToFloat((int) (v >> 32)) + f2) + G2)) << 32) | (((int) Math.ceil(Float.intBitsToFloat((int) (v & 4294967295L)) + f2 + G3)) & 4294967295L);
            final g30 b = this.s.g.b(v, layoutDirection, b50Var);
            long j2 = sv0Var.c;
            r5 r5Var = this.v;
            r5Var.c(j2);
            float G4 = b50Var.G(f);
            r5Var.getClass();
            Paint paint = r5Var.a;
            if (G4 > 0.0f) {
                blurMaskFilter = new BlurMaskFilter(G4, BlurMaskFilter.Blur.NORMAL);
            } else {
                blurMaskFilter = null;
            }
            paint.setMaskFilter(blurMaskFilter);
            hxVar.g(sv0Var.d);
            int i = sv0Var.e;
            jx jxVar = hxVar.a;
            if (jxVar.u() != i) {
                jxVar.k(i);
            }
            b50Var.M(hxVar, ceil, new gv() { // from class: wv0
                @Override // defpackage.gv
                public final Object e(Object obj) {
                    g30 g30Var = b;
                    xv0 xv0Var = this;
                    up upVar = (up) obj;
                    upVar.getClass();
                    float f3 = G * 2.0f;
                    float f4 = G2;
                    float f5 = f3 + f4;
                    float f6 = G3;
                    float f7 = f3 + f6;
                    ((j2) upVar.J().f).q(f5, f7);
                    try {
                        uc q = upVar.J().q();
                        o30.m(q, g30Var, xv0Var.v);
                        q.d(-f4, -f6);
                        o30.m(q, g30Var, vv0.a);
                        q.d(f4, f6);
                        ((j2) upVar.J().f).q(-f5, -f7);
                        return x31.a;
                    } catch (Throwable th) {
                        ((j2) upVar.J().f).q(-f5, -f7);
                        throw th;
                    }
                }
            });
            float f3 = 2.0f * (-G);
            ((j2) ycVar.f.f).q(f3, f3);
            try {
                n20.r(b50Var, hxVar);
            } finally {
                float f4 = -f3;
                ((j2) ycVar.f.f).q(f4, f4);
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
        hx a = ((n5) k81.C(this)).a();
        jx jxVar = a.a;
        if (jxVar.E() != 1) {
            jxVar.H(1);
        }
        this.u = a;
    }

    @Override // defpackage.bd0
    public final void v0() {
        ex C = k81.C(this);
        hx hxVar = this.u;
        if (hxVar != null) {
            ((n5) C).c(hxVar);
            this.u = null;
        }
    }

    @Override // defpackage.tp
    public final /* bridge */ void m0() {
    }
}
