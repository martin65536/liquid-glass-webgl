package defpackage;

import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class my {
    public final l40 a;
    public boolean b;
    public boolean c;
    public boolean d;
    public boolean e;
    public final pe0 f = new pe0();
    public final qg0 g = new qg0();
    public final le0 h = new le0(10);

    public my(l40 l40Var) {
        this.a = l40Var;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v2, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r16v6 */
    /* JADX WARN: Type inference failed for: r16v7 */
    /* JADX WARN: Type inference failed for: r16v8 */
    public final void a(long j, List list, boolean z) {
        le0 le0Var;
        long[] jArr;
        long[] jArr2;
        int i;
        ig0 ig0Var;
        ig0 ig0Var2;
        int size = list.size();
        qg0 qg0Var = this.g;
        qg0 qg0Var2 = qg0Var;
        boolean z2 = true;
        int i2 = 0;
        while (true) {
            le0Var = this.h;
            if (i2 >= size) {
                break;
            }
            bd0 bd0Var = (bd0) list.get(i2);
            if (bd0Var.r) {
                bd0Var.q = new u3(4, this, bd0Var);
                if (z2) {
                    ef0 ef0Var = qg0Var2.a;
                    ?? r14 = ef0Var.e;
                    int i3 = ef0Var.g;
                    int i4 = 0;
                    while (true) {
                        if (i4 < i3) {
                            ig0Var2 = r14[i4];
                            if (o20.e(((ig0) ig0Var2).c, bd0Var)) {
                                break;
                            } else {
                                i4++;
                            }
                        } else {
                            ig0Var2 = 0;
                            break;
                        }
                    }
                    ig0Var = ig0Var2;
                    if (ig0Var != null) {
                        ig0Var.i = true;
                        ig0Var.d.b(j);
                        if (z) {
                            Object d = le0Var.d(j);
                            if (d == null) {
                                d = new pe0();
                                le0Var.f(j, d);
                            }
                            ((pe0) d).a(ig0Var);
                        }
                        qg0Var2 = ig0Var;
                    } else {
                        z2 = false;
                    }
                }
                ig0Var = new ig0(bd0Var);
                ig0Var.d.b(j);
                if (z) {
                    Object d2 = le0Var.d(j);
                    if (d2 == null) {
                        d2 = new pe0();
                        le0Var.f(j, d2);
                    }
                    ((pe0) d2).a(ig0Var);
                }
                qg0Var2.a.b(ig0Var);
                qg0Var2 = ig0Var;
            }
            i2++;
        }
        if (z) {
            long[] jArr3 = le0Var.b;
            Object[] objArr = le0Var.c;
            long[] jArr4 = le0Var.a;
            int length = jArr4.length - 2;
            if (length >= 0) {
                int i5 = 0;
                while (true) {
                    long j2 = jArr4[i5];
                    if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                        int i6 = 8;
                        int i7 = 8 - ((~(i5 - length)) >>> 31);
                        int i8 = 0;
                        while (i8 < i7) {
                            if ((255 & j2) < 128) {
                                int i9 = (i5 << 3) + i8;
                                long j3 = jArr3[i9];
                                pe0 pe0Var = (pe0) objArr[i9];
                                ef0 ef0Var2 = qg0Var.a;
                                i = i6;
                                Object[] objArr2 = ef0Var2.e;
                                int i10 = ef0Var2.g;
                                jArr2 = jArr3;
                                for (int i11 = 0; i11 < i10; i11++) {
                                    ((ig0) objArr2[i11]).f(j3, pe0Var);
                                }
                            } else {
                                jArr2 = jArr3;
                                i = i6;
                            }
                            j2 >>= i;
                            i8++;
                            i6 = i;
                            jArr3 = jArr2;
                        }
                        jArr = jArr3;
                        if (i7 != i6) {
                            break;
                        }
                    } else {
                        jArr = jArr3;
                    }
                    if (i5 == length) {
                        break;
                    }
                    i5++;
                    jArr3 = jArr;
                }
            }
        }
        le0Var.a();
    }

    public final boolean b(c4 c4Var, boolean z) {
        kb0 kb0Var = (kb0) c4Var.f;
        l40 l40Var = this.a;
        qg0 qg0Var = this.g;
        boolean a = qg0Var.a(kb0Var, l40Var, c4Var, z);
        ef0 ef0Var = qg0Var.a;
        if (!a) {
            return false;
        }
        boolean z2 = true;
        this.b = true;
        Object[] objArr = ef0Var.e;
        int i = ef0Var.g;
        boolean z3 = false;
        for (int i2 = 0; i2 < i; i2++) {
            if (!((ig0) objArr[i2]).e(c4Var, z) && !z3) {
                z3 = false;
            } else {
                z3 = true;
            }
        }
        Object[] objArr2 = ef0Var.e;
        int i3 = ef0Var.g;
        boolean z4 = false;
        for (int i4 = 0; i4 < i3; i4++) {
            if (!((ig0) objArr2[i4]).d(c4Var) && !z4) {
                z4 = false;
            } else {
                z4 = true;
            }
        }
        qg0Var.b(c4Var);
        if (!z4 && !z3) {
            z2 = false;
        }
        this.b = false;
        if (this.e) {
            this.e = false;
            pe0 pe0Var = this.f;
            int i5 = pe0Var.b;
            for (int i6 = 0; i6 < i5; i6++) {
                d((bd0) pe0Var.f(i6));
            }
            pe0Var.d();
        }
        if (this.c) {
            this.c = false;
            c();
        }
        if (this.d) {
            this.d = false;
            qg0Var.a.g();
        }
        return z2;
    }

    public final void c() {
        if (this.b) {
            this.c = true;
            return;
        }
        qg0 qg0Var = this.g;
        ef0 ef0Var = qg0Var.a;
        Object[] objArr = ef0Var.e;
        int i = ef0Var.g;
        for (int i2 = 0; i2 < i; i2++) {
            ((ig0) objArr[i2]).c();
        }
        if (this.d) {
            this.d = true;
        } else {
            qg0Var.a.g();
        }
    }

    public final void d(bd0 bd0Var) {
        if (this.b) {
            this.e = true;
            this.f.a(bd0Var);
            return;
        }
        qg0 qg0Var = this.g;
        pe0 pe0Var = qg0Var.b;
        pe0Var.d();
        pe0Var.a(qg0Var);
        while (pe0Var.i()) {
            qg0 qg0Var2 = (qg0) pe0Var.k(pe0Var.b - 1);
            int i = 0;
            while (true) {
                ef0 ef0Var = qg0Var2.a;
                if (i < ef0Var.g) {
                    ig0 ig0Var = (ig0) ef0Var.e[i];
                    if (o20.e(ig0Var.c, bd0Var)) {
                        qg0Var2.a.j(ig0Var);
                        ig0Var.c();
                    } else {
                        pe0Var.a(ig0Var);
                        i++;
                    }
                }
            }
        }
    }
}
