package defpackage;

import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class i70 {
    public final int a;
    public final List b;
    public final z9 c;
    public final m40 d;
    public final int e;
    public final long f;
    public final Object g;
    public final Object h;
    public final c60 i;
    public int j;
    public final int k;
    public final int l;
    public final int m;
    public boolean n;
    public int o = Integer.MIN_VALUE;
    public final int[] p;

    public i70(int i, List list, z9 z9Var, m40 m40Var, int i2, int i3, int i4, long j, Object obj, Object obj2, c60 c60Var, long j2) {
        this.a = i;
        this.b = list;
        this.c = z9Var;
        this.d = m40Var;
        this.e = i4;
        this.f = j;
        this.g = obj;
        this.h = obj2;
        this.i = c60Var;
        int size = list.size();
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < size; i7++) {
            em0 em0Var = (em0) list.get(i7);
            i5 += em0Var.f;
            i6 = Math.max(i6, em0Var.e);
        }
        this.k = i5;
        int i8 = i5 + this.e;
        this.l = i8 >= 0 ? i8 : 0;
        this.m = i6;
        this.p = new int[this.b.size() * 2];
    }

    public final long a(int i) {
        if (i == 0 && this.b.size() == 0) {
            return this.j & 4294967295L;
        }
        int[] iArr = this.p;
        return (iArr[r5 + 1] & 4294967295L) | (iArr[i * 2] << 32);
    }

    public final void b(dm0 dm0Var) {
        if (this.o == Integer.MIN_VALUE) {
            t00.a("position() should be called first");
        }
        List list = this.b;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            em0 em0Var = (em0) list.get(i);
            int i2 = em0Var.f;
            long a = a(i);
            d3.z(this.i.a.g(this.g));
            dm0.I(dm0Var, em0Var, v10.c(a, this.f), null, 6);
        }
    }

    public final void c(int i, int i2, int i3) {
        this.j = i;
        this.o = i3;
        List list = this.b;
        int size = list.size();
        for (int i4 = 0; i4 < size; i4++) {
            em0 em0Var = (em0) list.get(i4);
            int i5 = i4 * 2;
            z9 z9Var = this.c;
            if (z9Var != null) {
                int a = z9Var.a(em0Var.e, i2, this.d);
                int[] iArr = this.p;
                iArr[i5] = a;
                iArr[i5 + 1] = i;
                i += em0Var.f;
            } else {
                t00.b("null horizontalAlignment when isVertical == true");
                throw new RuntimeException();
            }
        }
    }
}
