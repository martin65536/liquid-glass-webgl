package defpackage;

import android.content.Context;
import android.os.Build;
import android.widget.EdgeEffect;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class e5 {
    public final mm a;
    public long b = 9205357640488583168L;
    public final iq c;
    public final ik0 d;
    public final boolean e;
    public boolean f;
    public long g;
    public long h;
    public final jm i;

    public e5(Context context, mm mmVar, long j, tj0 tj0Var) {
        dx dxVar;
        this.a = mmVar;
        iq iqVar = new iq(context, f31.P(j));
        this.c = iqVar;
        this.d = new ik0(x31.a, x1.S);
        this.e = true;
        this.g = 0L;
        this.h = -1L;
        d5 d5Var = new d5(0, this);
        pm0 pm0Var = uz0.a;
        yz0 yz0Var = new yz0(null, null, d5Var);
        if (Build.VERSION.SDK_INT >= 31) {
            dxVar = new dx(yz0Var, this, iqVar);
        } else {
            dxVar = new dx(yz0Var, this, iqVar, tj0Var);
        }
        this.i = dxVar;
    }

    public final void a() {
        boolean z;
        iq iqVar = this.c;
        EdgeEffect edgeEffect = iqVar.d;
        boolean z2 = true;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            z = !edgeEffect.isFinished();
        } else {
            z = false;
        }
        EdgeEffect edgeEffect2 = iqVar.e;
        if (edgeEffect2 != null) {
            edgeEffect2.onRelease();
            if (edgeEffect2.isFinished() && !z) {
                z = false;
            } else {
                z = true;
            }
        }
        EdgeEffect edgeEffect3 = iqVar.f;
        if (edgeEffect3 != null) {
            edgeEffect3.onRelease();
            if (edgeEffect3.isFinished() && !z) {
                z = false;
            } else {
                z = true;
            }
        }
        EdgeEffect edgeEffect4 = iqVar.g;
        if (edgeEffect4 != null) {
            edgeEffect4.onRelease();
            if (edgeEffect4.isFinished() && !z) {
                z2 = false;
            }
            z = z2;
        }
        if (z) {
            d();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:70:0x0137, code lost:
    
        if (r4 == r6) goto L51;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x01b9  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object b(long r19, defpackage.gu0 r21, defpackage.jj r22) {
        /*
            Method dump skipped, instructions count: 483
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.e5.b(long, gu0, jj):java.lang.Object");
    }

    public final long c() {
        long j = this.b;
        if ((9223372034707292159L & j) == 9205357640488583168L) {
            j = o30.p(this.g);
        }
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32)) / Float.intBitsToFloat((int) (this.g >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L)) / Float.intBitsToFloat((int) (this.g & 4294967295L));
        return (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat) << 32);
    }

    public final void d() {
        if (this.e) {
            this.d.setValue(x31.a);
        }
    }

    public final float e(long j) {
        float f;
        float intBitsToFloat = Float.intBitsToFloat((int) (c() >> 32));
        int i = (int) (j & 4294967295L);
        float intBitsToFloat2 = Float.intBitsToFloat(i) / Float.intBitsToFloat((int) (this.g & 4294967295L));
        EdgeEffect b = this.c.b();
        float f2 = -intBitsToFloat2;
        float f3 = 1.0f - intBitsToFloat;
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 31) {
            f2 = p7.d(b, f2, f3);
        } else {
            b.onPull(f2, f3);
        }
        float intBitsToFloat3 = Float.intBitsToFloat((int) (4294967295L & this.g)) * (-f2);
        if (i2 >= 31) {
            f = p7.c(b);
        } else {
            f = 0.0f;
        }
        if (f == 0.0f) {
            return intBitsToFloat3;
        }
        return Float.intBitsToFloat(i);
    }

    public final float f(long j) {
        float f;
        float intBitsToFloat = Float.intBitsToFloat((int) (c() & 4294967295L));
        int i = (int) (j >> 32);
        float intBitsToFloat2 = Float.intBitsToFloat(i) / Float.intBitsToFloat((int) (this.g >> 32));
        EdgeEffect c = this.c.c();
        float f2 = 1.0f - intBitsToFloat;
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 31) {
            intBitsToFloat2 = p7.d(c, intBitsToFloat2, f2);
        } else {
            c.onPull(intBitsToFloat2, f2);
        }
        float intBitsToFloat3 = Float.intBitsToFloat((int) (this.g >> 32)) * intBitsToFloat2;
        if (i2 >= 31) {
            f = p7.c(c);
        } else {
            f = 0.0f;
        }
        if (f == 0.0f) {
            return intBitsToFloat3;
        }
        return Float.intBitsToFloat(i);
    }

    public final float g(long j) {
        float f;
        float intBitsToFloat = Float.intBitsToFloat((int) (c() & 4294967295L));
        int i = (int) (j >> 32);
        float intBitsToFloat2 = Float.intBitsToFloat(i) / Float.intBitsToFloat((int) (this.g >> 32));
        EdgeEffect d = this.c.d();
        float f2 = -intBitsToFloat2;
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 31) {
            f2 = p7.d(d, f2, intBitsToFloat);
        } else {
            d.onPull(f2, intBitsToFloat);
        }
        float intBitsToFloat3 = Float.intBitsToFloat((int) (this.g >> 32)) * (-f2);
        if (i2 >= 31) {
            f = p7.c(d);
        } else {
            f = 0.0f;
        }
        if (f == 0.0f) {
            return intBitsToFloat3;
        }
        return Float.intBitsToFloat(i);
    }

    public final float h(long j) {
        float f;
        float intBitsToFloat = Float.intBitsToFloat((int) (c() >> 32));
        int i = (int) (j & 4294967295L);
        float intBitsToFloat2 = Float.intBitsToFloat(i) / Float.intBitsToFloat((int) (this.g & 4294967295L));
        EdgeEffect e = this.c.e();
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 31) {
            intBitsToFloat2 = p7.d(e, intBitsToFloat2, intBitsToFloat);
        } else {
            e.onPull(intBitsToFloat2, intBitsToFloat);
        }
        float intBitsToFloat3 = Float.intBitsToFloat((int) (4294967295L & this.g)) * intBitsToFloat2;
        if (i2 >= 31) {
            f = p7.c(e);
        } else {
            f = 0.0f;
        }
        if (f == 0.0f) {
            return intBitsToFloat3;
        }
        return Float.intBitsToFloat(i);
    }

    public final void i(long j) {
        boolean a = mw0.a(this.g, 0L);
        boolean a2 = mw0.a(j, this.g);
        this.g = j;
        if (!a2) {
            long G = (jc0.G(Float.intBitsToFloat((int) (j & 4294967295L))) & 4294967295L) | (jc0.G(Float.intBitsToFloat((int) (j >> 32))) << 32);
            iq iqVar = this.c;
            iqVar.c = G;
            EdgeEffect edgeEffect = iqVar.d;
            if (edgeEffect != null) {
                edgeEffect.setSize((int) (G >> 32), (int) (G & 4294967295L));
            }
            EdgeEffect edgeEffect2 = iqVar.e;
            if (edgeEffect2 != null) {
                edgeEffect2.setSize((int) (G >> 32), (int) (G & 4294967295L));
            }
            EdgeEffect edgeEffect3 = iqVar.f;
            if (edgeEffect3 != null) {
                edgeEffect3.setSize((int) (G & 4294967295L), (int) (G >> 32));
            }
            EdgeEffect edgeEffect4 = iqVar.g;
            if (edgeEffect4 != null) {
                edgeEffect4.setSize((int) (G & 4294967295L), (int) (G >> 32));
            }
            EdgeEffect edgeEffect5 = iqVar.h;
            if (edgeEffect5 != null) {
                edgeEffect5.setSize((int) (G >> 32), (int) (G & 4294967295L));
            }
            EdgeEffect edgeEffect6 = iqVar.i;
            if (edgeEffect6 != null) {
                edgeEffect6.setSize((int) (G >> 32), (int) (G & 4294967295L));
            }
            EdgeEffect edgeEffect7 = iqVar.j;
            if (edgeEffect7 != null) {
                edgeEffect7.setSize((int) (G & 4294967295L), (int) (G >> 32));
            }
            EdgeEffect edgeEffect8 = iqVar.k;
            if (edgeEffect8 != null) {
                edgeEffect8.setSize((int) (4294967295L & G), (int) (G >> 32));
            }
        }
        if (!a && !a2) {
            a();
        }
    }
}
