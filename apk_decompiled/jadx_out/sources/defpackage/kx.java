package defpackage;

import android.os.Build;
import android.view.ViewParent;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class kx implements lj0 {
    public hx e;
    public final ex f;
    public final b4 g;
    public kv h;
    public vu i;
    public boolean k;
    public float[] m;
    public boolean n;
    public int r;
    public g30 t;
    public boolean u;
    public boolean v;
    public long j = 9223372034707292159L;
    public final float[] l = m20.n();
    public mm o = dl.e();
    public m40 p = m40.e;
    public final yc q = new yc();
    public long s = s21.a;
    public boolean w = true;
    public final q2 x = new q2(15, this);

    public kx(hx hxVar, ex exVar, b4 b4Var, kv kvVar, vu vuVar) {
        this.e = hxVar;
        this.f = exVar;
        this.g = b4Var;
        this.h = kvVar;
        this.i = vuVar;
    }

    public final float[] a() {
        float[] fArr = this.m;
        if (fArr == null) {
            fArr = m20.n();
            this.m = fArr;
        }
        if (!this.v) {
            if (Float.isNaN(fArr[0])) {
                return null;
            }
        } else {
            this.v = false;
            float[] b = b();
            if (this.w) {
                return b;
            }
            if (!y20.m(b, fArr)) {
                fArr[0] = Float.NaN;
                return null;
            }
        }
        return fArr;
    }

    public final float[] b() {
        boolean z = this.u;
        float[] fArr = this.l;
        if (z) {
            hx hxVar = this.e;
            long j = hxVar.v;
            jx jxVar = hxVar.a;
            if ((9223372034707292159L & j) == 9205357640488583168L) {
                j = o30.p(d20.J(this.j));
            }
            float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
            float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L));
            float C = jxVar.C();
            float w = jxVar.w();
            float F = jxVar.F();
            float K = jxVar.K();
            float N = jxVar.N();
            float c = jxVar.c();
            float t = jxVar.t();
            double d = F * 0.017453292519943295d;
            float sin = (float) Math.sin(d);
            float cos = (float) Math.cos(d);
            float f = -sin;
            float f2 = (w * cos) - (0.0f * sin);
            float f3 = (0.0f * cos) + (w * sin);
            double d2 = K * 0.017453292519943295d;
            float sin2 = (float) Math.sin(d2);
            float cos2 = (float) Math.cos(d2);
            float f4 = -sin2;
            float f5 = sin * sin2;
            float f6 = sin * cos2;
            float f7 = cos * sin2;
            float f8 = cos * cos2;
            float f9 = (f3 * sin2) + (C * cos2);
            float f10 = (f3 * cos2) + ((-C) * sin2);
            double d3 = N * 0.017453292519943295d;
            float sin3 = (float) Math.sin(d3);
            float cos3 = (float) Math.cos(d3);
            float f11 = -sin3;
            float f12 = (cos3 * f5) + (f11 * cos2);
            float f13 = ((f5 * sin3) + (cos2 * cos3)) * c;
            float f14 = sin3 * cos * c;
            float f15 = ((sin3 * f6) + (cos3 * f4)) * c;
            float f16 = f12 * t;
            float f17 = cos * cos3 * t;
            float f18 = ((cos3 * f6) + (f11 * f4)) * t;
            float f19 = f7 * 1.0f;
            float f20 = f * 1.0f;
            float f21 = f8 * 1.0f;
            if (fArr.length >= 16) {
                fArr[0] = f13;
                fArr[1] = f14;
                fArr[2] = f15;
                fArr[3] = 0.0f;
                fArr[4] = f16;
                fArr[5] = f17;
                fArr[6] = f18;
                fArr[7] = 0.0f;
                fArr[8] = f19;
                fArr[9] = f20;
                fArr[10] = f21;
                fArr[11] = 0.0f;
                float f22 = -intBitsToFloat;
                fArr[12] = ((f13 * f22) - (intBitsToFloat2 * f16)) + f9 + intBitsToFloat;
                fArr[13] = ((f14 * f22) - (intBitsToFloat2 * f17)) + f2 + intBitsToFloat2;
                fArr[14] = ((f22 * f15) - (intBitsToFloat2 * f18)) + f10;
                fArr[15] = 1.0f;
            }
            this.u = false;
            this.w = t20.A(fArr);
        }
        return fArr;
    }

    public final void c() {
        if (!this.n && !this.k) {
            this.g.invalidate();
            f(true);
        }
    }

    public final void d(long j) {
        boolean s = b4.s();
        b4 b4Var = this.g;
        if (s) {
            b4Var.N(-4.0f);
        }
        hx hxVar = this.e;
        if (!v10.a(hxVar.t, j)) {
            hxVar.t = j;
            hxVar.a.J((int) (j >> 32), (int) (j & 4294967295L), hxVar.u);
        }
        if (Build.VERSION.SDK_INT >= 26) {
            ViewParent parent = b4Var.getParent();
            if (parent != null) {
                parent.onDescendantInvalidated(b4Var, b4Var);
                return;
            }
            return;
        }
        b4Var.invalidate();
    }

    public final void e(long j) {
        if (!c20.a(j, this.j)) {
            if (b4.s()) {
                this.g.N(-4.0f);
            }
            this.j = j;
            c();
        }
    }

    public final void f(boolean z) {
        if (z != this.n) {
            this.n = z;
            b4 b4Var = this.g;
            pe0 pe0Var = b4Var.I;
            boolean z2 = b4Var.K;
            if (!z) {
                if (!z2) {
                    pe0Var.j(this);
                    pe0 pe0Var2 = b4Var.J;
                    if (pe0Var2 != null) {
                        pe0Var2.j(this);
                        return;
                    }
                    return;
                }
                return;
            }
            if (!z2) {
                pe0Var.a(this);
                return;
            }
            pe0 pe0Var3 = b4Var.J;
            if (pe0Var3 == null) {
                pe0Var3 = new pe0();
                b4Var.J = pe0Var3;
            }
            pe0Var3.a(this);
        }
    }

    public final void g() {
        b4.s();
        if (this.n) {
            if (this.s != s21.a && !c20.a(this.e.u, this.j)) {
                hx hxVar = this.e;
                float intBitsToFloat = Float.intBitsToFloat((int) (this.s >> 32)) * ((int) (this.j >> 32));
                float intBitsToFloat2 = Float.intBitsToFloat((int) (this.s & 4294967295L)) * ((int) (this.j & 4294967295L));
                long floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat) << 32);
                if (!ch0.c(hxVar.v, floatToRawIntBits)) {
                    hxVar.v = floatToRawIntBits;
                    hxVar.a.O(floatToRawIntBits);
                }
            }
            this.e.f(this.o, this.p, this.j, this.x);
            f(false);
        }
    }
}
