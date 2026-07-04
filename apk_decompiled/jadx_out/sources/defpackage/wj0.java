package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class wj0 extends bd0 implements r40, tp {
    public uj0 s;
    public boolean t;
    public ba u;
    public dt0 v;
    public float w;
    public te x;

    public static boolean D0(long j) {
        if (!mw0.a(j, 9205357640488583168L) && (Float.floatToRawIntBits(Float.intBitsToFloat((int) (j & 4294967295L))) & Integer.MAX_VALUE) < 2139095040) {
            return true;
        }
        return false;
    }

    public static boolean E0(long j) {
        if (!mw0.a(j, 9205357640488583168L) && (Float.floatToRawIntBits(Float.intBitsToFloat((int) (j >> 32))) & Integer.MAX_VALUE) < 2139095040) {
            return true;
        }
        return false;
    }

    @Override // defpackage.tp
    public final void R(b50 b50Var) {
        float intBitsToFloat;
        float intBitsToFloat2;
        long j;
        yc ycVar = b50Var.e;
        long d = this.s.d();
        if (E0(d)) {
            intBitsToFloat = Float.intBitsToFloat((int) (d >> 32));
        } else {
            intBitsToFloat = Float.intBitsToFloat((int) (ycVar.f.v() >> 32));
        }
        if (D0(d)) {
            intBitsToFloat2 = Float.intBitsToFloat((int) (d & 4294967295L));
        } else {
            intBitsToFloat2 = Float.intBitsToFloat((int) (ycVar.f.v() & 4294967295L));
        }
        long floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat) << 32) | (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L);
        if (Float.intBitsToFloat((int) (ycVar.f.v() >> 32)) == 0.0f || Float.intBitsToFloat((int) (ycVar.f.v() & 4294967295L)) == 0.0f) {
            j = 0;
        } else {
            j = d20.H(floatToRawIntBits, this.v.f(floatToRawIntBits, ycVar.f.v()));
        }
        long a = this.u.a((Math.round(Float.intBitsToFloat((int) (j >> 32))) << 32) | (Math.round(Float.intBitsToFloat((int) (j & 4294967295L))) & 4294967295L), (Math.round(Float.intBitsToFloat((int) (ycVar.f.v() >> 32))) << 32) | (Math.round(Float.intBitsToFloat((int) (ycVar.f.v() & 4294967295L))) & 4294967295L), b50Var.getLayoutDirection());
        float f = (int) (a >> 32);
        float f2 = (int) (a & 4294967295L);
        ((j2) ycVar.f.f).q(f, f2);
        try {
            this.s.c(b50Var, j, this.w, this.x);
            ((j2) ycVar.f.f).q(-f, -f2);
            b50Var.r();
        } catch (Throwable th) {
            ((j2) ycVar.f.f).q(-f, -f2);
            throw th;
        }
    }

    @Override // defpackage.r40
    public final qc0 Y(ob0 ob0Var, kc0 kc0Var, long j) {
        boolean z;
        long a;
        int j2;
        int i;
        float intBitsToFloat;
        float intBitsToFloat2;
        boolean z2 = false;
        if (si.d(j) && si.c(j)) {
            z = true;
        } else {
            z = false;
        }
        if (si.f(j) && si.e(j)) {
            z2 = true;
        }
        if (((this.t && this.s.d() != 9205357640488583168L) || !z) && !z2) {
            long d = this.s.d();
            if (E0(d)) {
                j2 = Math.round(Float.intBitsToFloat((int) (d >> 32)));
            } else {
                j2 = si.j(j);
            }
            if (D0(d)) {
                i = Math.round(Float.intBitsToFloat((int) (d & 4294967295L)));
            } else {
                i = si.i(j);
            }
            long floatToRawIntBits = (Float.floatToRawIntBits(ti.f(j2, j)) << 32) | (Float.floatToRawIntBits(ti.e(i, j)) & 4294967295L);
            if (this.t && this.s.d() != 9205357640488583168L) {
                if (!E0(this.s.d())) {
                    intBitsToFloat = Float.intBitsToFloat((int) (floatToRawIntBits >> 32));
                } else {
                    intBitsToFloat = Float.intBitsToFloat((int) (this.s.d() >> 32));
                }
                if (!D0(this.s.d())) {
                    intBitsToFloat2 = Float.intBitsToFloat((int) (floatToRawIntBits & 4294967295L));
                } else {
                    intBitsToFloat2 = Float.intBitsToFloat((int) (this.s.d() & 4294967295L));
                }
                long floatToRawIntBits2 = (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat) << 32);
                floatToRawIntBits = (Float.intBitsToFloat((int) (floatToRawIntBits >> 32)) == 0.0f || Float.intBitsToFloat((int) (floatToRawIntBits & 4294967295L)) == 0.0f) ? 0L : d20.H(floatToRawIntBits2, this.v.f(floatToRawIntBits2, floatToRawIntBits));
            }
            a = si.a(j, ti.f(Math.round(Float.intBitsToFloat((int) (floatToRawIntBits >> 32))), j), 0, ti.e(Math.round(Float.intBitsToFloat((int) (floatToRawIntBits & 4294967295L))), j), 0, 10);
        } else {
            a = si.a(j, si.h(j), 0, si.g(j), 0, 10);
        }
        em0 v = kc0Var.v(a);
        return ob0Var.e0(v.e, v.f, fr.e, new p3(v, 4));
    }

    @Override // defpackage.bd0
    public final boolean q0() {
        return false;
    }

    public final String toString() {
        return "PainterModifier(painter=" + this.s + ", sizeToIntrinsics=" + this.t + ", alignment=" + this.u + ", alpha=" + this.w + ", colorFilter=" + this.x + ')';
    }

    @Override // defpackage.tp
    public final /* synthetic */ void m0() {
    }
}
