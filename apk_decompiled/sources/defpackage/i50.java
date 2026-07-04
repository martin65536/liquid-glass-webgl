package defpackage;

import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class i50 implements qc0 {
    public final /* synthetic */ int a;
    public final /* synthetic */ qc0 b;
    public final /* synthetic */ n50 c;
    public final /* synthetic */ int d;
    public final /* synthetic */ qc0 e;

    public /* synthetic */ i50(qc0 qc0Var, n50 n50Var, int i, qc0 qc0Var2, int i2) {
        this.a = i2;
        this.c = n50Var;
        this.d = i;
        this.e = qc0Var2;
        this.b = qc0Var;
    }

    @Override // defpackage.qc0
    public final void a() {
        int i;
        int i2 = this.a;
        qc0 qc0Var = this.e;
        int i3 = this.d;
        n50 n50Var = this.c;
        switch (i2) {
            case 0:
                n50Var.i = i3;
                qc0Var.a();
                ef0 ef0Var = n50Var.q;
                ve0 ve0Var = n50Var.p;
                long[] jArr = ve0Var.a;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i4 = 0;
                    while (true) {
                        long j = jArr[i4];
                        if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i5 = 8;
                            int i6 = 8 - ((~(i4 - length)) >>> 31);
                            int i7 = 0;
                            while (i7 < i6) {
                                if ((255 & j) < 128) {
                                    int i8 = (i4 << 3) + i7;
                                    Object obj = ve0Var.b[i8];
                                    fz0 fz0Var = (fz0) ve0Var.c[i8];
                                    int i9 = ef0Var.i(obj);
                                    if (i9 < 0 || i9 >= n50Var.i) {
                                        if (i9 >= 0) {
                                            Object obj2 = jc0.n;
                                            i = i5;
                                            Object[] objArr = ef0Var.e;
                                            Object obj3 = objArr[i9];
                                            objArr[i9] = obj2;
                                        } else {
                                            i = i5;
                                        }
                                        if (n50Var.n.b(obj)) {
                                            fz0Var.a();
                                        }
                                        ve0Var.l(i8);
                                        j >>= i;
                                        i7++;
                                        i5 = i;
                                    }
                                }
                                i = i5;
                                j >>= i;
                                i7++;
                                i5 = i;
                            }
                            if (i6 != i5) {
                            }
                        }
                        if (i4 != length) {
                            i4++;
                        }
                    }
                }
                n50Var.g(n50Var.h);
                return;
            default:
                n50Var.h = i3;
                qc0Var.a();
                if (n50Var.e.l == null) {
                    n50Var.g(n50Var.h);
                    return;
                }
                return;
        }
    }

    @Override // defpackage.qc0
    public final int b() {
        switch (this.a) {
            case 0:
                return this.b.b();
            default:
                return this.b.b();
        }
    }

    @Override // defpackage.qc0
    public final gv c() {
        switch (this.a) {
            case 0:
                return this.b.c();
            default:
                return this.b.c();
        }
    }

    @Override // defpackage.qc0
    public final int d() {
        switch (this.a) {
            case 0:
                return this.b.d();
            default:
                return this.b.d();
        }
    }

    @Override // defpackage.qc0
    public final Map r() {
        switch (this.a) {
            case 0:
                return this.b.r();
            default:
                return this.b.r();
        }
    }
}
