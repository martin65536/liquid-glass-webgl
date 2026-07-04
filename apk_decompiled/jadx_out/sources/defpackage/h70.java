package defpackage;

import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class h70 implements qc0 {
    public final i70 a;
    public final int b;
    public final boolean c;
    public final float d;
    public final qc0 e;
    public final float f;
    public final boolean g;
    public final hk h;
    public final mm i;
    public final long j;
    public final List k;
    public final int l;
    public final int m;
    public final int n;
    public final dj0 o;
    public final int p;
    public final int q;

    public h70(i70 i70Var, int i, boolean z, float f, qc0 qc0Var, float f2, boolean z2, hk hkVar, mm mmVar, long j, List list, int i2, int i3, int i4, dj0 dj0Var, int i5, int i6) {
        this.a = i70Var;
        this.b = i;
        this.c = z;
        this.d = f;
        this.e = qc0Var;
        this.f = f2;
        this.g = z2;
        this.h = hkVar;
        this.i = mmVar;
        this.j = j;
        this.k = list;
        this.l = i2;
        this.m = i3;
        this.n = i4;
        this.o = dj0Var;
        this.p = i5;
        this.q = i6;
    }

    @Override // defpackage.qc0
    public final void a() {
        this.e.a();
    }

    @Override // defpackage.qc0
    public final int b() {
        return this.e.b();
    }

    @Override // defpackage.qc0
    public final gv c() {
        return this.e.c();
    }

    @Override // defpackage.qc0
    public final int d() {
        return this.e.d();
    }

    public final h70 e(int i, boolean z) {
        i70 i70Var;
        if (!this.g) {
            List list = this.k;
            if (!list.isEmpty() && (i70Var = this.a) != null) {
                int i2 = i70Var.l;
                int i3 = this.b - i;
                if (i3 >= 0 && i3 < i2) {
                    i70 i70Var2 = (i70) me.S(list);
                    i70 i70Var3 = (i70) me.X(list);
                    if (!i70Var2.n && !i70Var3.n) {
                        int i4 = i70Var2.j;
                        int i5 = this.m;
                        int i6 = this.l;
                        if (i < 0) {
                            if (Math.min((i4 + i70Var2.l) - i6, (i70Var3.j + i70Var3.l) - i5) <= (-i)) {
                                return null;
                            }
                        } else if (Math.min(i6 - i4, i5 - i70Var3.j) <= i) {
                            return null;
                        }
                        int size = list.size();
                        boolean z2 = false;
                        for (int i7 = 0; i7 < size; i7++) {
                            i70 i70Var4 = (i70) list.get(i7);
                            i70Var4.getClass();
                            int[] iArr = i70Var4.p;
                            if (!i70Var4.n) {
                                i70Var4.j += i;
                                int length = iArr.length;
                                for (int i8 = 0; i8 < length; i8++) {
                                    if ((i8 & 1) != 0) {
                                        iArr[i8] = iArr[i8] + i;
                                    }
                                }
                                if (z) {
                                    int size2 = i70Var4.b.size();
                                    for (int i9 = 0; i9 < size2; i9++) {
                                        d3.z(i70Var4.i.a.g(i70Var4.g));
                                    }
                                }
                            }
                        }
                        if (this.c || i > 0) {
                            z2 = true;
                        }
                        return new h70(this.a, i3, z2, i, this.e, this.f, this.g, this.h, this.i, this.j, list, this.l, this.m, this.n, this.o, this.p, this.q);
                    }
                    return null;
                }
                return null;
            }
            return null;
        }
        return null;
    }

    public final long f() {
        qc0 qc0Var = this.e;
        return (qc0Var.d() << 32) | (qc0Var.b() & 4294967295L);
    }

    @Override // defpackage.qc0
    public final Map r() {
        return this.e.r();
    }
}
