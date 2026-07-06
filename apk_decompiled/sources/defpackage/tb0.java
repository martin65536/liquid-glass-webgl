package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class tb0 extends z30 implements vu {
    public final /* synthetic */ int f;
    public final /* synthetic */ ub0 g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ tb0(ub0 ub0Var, int i) {
        super(0);
        this.f = i;
        this.g = ub0Var;
    }

    @Override // defpackage.vu
    public final Object a() {
        qb0 N0;
        int i = this.f;
        x31 x31Var = x31.a;
        ub0 ub0Var = this.g;
        switch (i) {
            case 0:
                d50 d50Var = ub0Var.j;
                d50Var.h = 0;
                ef0 w = d50Var.a.w();
                Object[] objArr = w.e;
                int i2 = w.g;
                for (int i3 = 0; i3 < i2; i3++) {
                    ub0 ub0Var2 = ((z40) objArr[i3]).I.q;
                    ub0Var2.getClass();
                    ub0Var2.l = ub0Var2.m;
                    ub0Var2.m = Integer.MAX_VALUE;
                    if (ub0Var2.n == x40.f) {
                        ub0Var2.n = x40.g;
                    }
                }
                z40 z40Var = d50Var.a;
                z40 z40Var2 = d50Var.a;
                ef0 w2 = z40Var.w();
                Object[] objArr2 = w2.e;
                int i4 = w2.g;
                for (int i5 = 0; i5 < i4; i5++) {
                    ub0 ub0Var3 = ((z40) objArr2[i5]).I.q;
                    ub0Var3.getClass();
                    ub0Var3.u.getClass();
                }
                v00 v00Var = ub0Var.I().V;
                if (v00Var != null) {
                    boolean z = v00Var.o;
                    bf0 bf0Var = (bf0) z40Var2.m();
                    int i6 = bf0Var.e.g;
                    for (int i7 = 0; i7 < i6; i7++) {
                        qb0 N02 = ((z40) bf0Var.get(i7)).H.d.N0();
                        if (N02 != null) {
                            N02.o = z;
                        }
                    }
                }
                v00 v00Var2 = ub0Var.I().V;
                v00Var2.getClass();
                v00Var2.w0().a();
                if (ub0Var.I().V != null) {
                    bf0 bf0Var2 = (bf0) z40Var2.m();
                    int i8 = bf0Var2.e.g;
                    for (int i9 = 0; i9 < i8; i9++) {
                        qb0 N03 = ((z40) bf0Var2.get(i9)).H.d.N0();
                        if (N03 != null) {
                            N03.o = false;
                        }
                    }
                }
                ef0 w3 = z40Var2.w();
                Object[] objArr3 = w3.e;
                int i10 = w3.g;
                for (int i11 = 0; i11 < i10; i11++) {
                    ub0 ub0Var4 = ((z40) objArr3[i11]).I.q;
                    ub0Var4.getClass();
                    int i12 = ub0Var4.l;
                    int i13 = ub0Var4.m;
                    if (i12 != i13 && i13 == Integer.MAX_VALUE) {
                        ub0Var4.n0(true);
                    }
                }
                ef0 w4 = z40Var2.w();
                Object[] objArr4 = w4.e;
                int i14 = w4.g;
                for (int i15 = 0; i15 < i14; i15++) {
                    ub0 ub0Var5 = ((z40) objArr4[i15]).I.q;
                    ub0Var5.getClass();
                    a50 a50Var = ub0Var5.u;
                    a50Var.getClass();
                    a50Var.c = false;
                }
                return x31Var;
            case 1:
                d50 d50Var2 = ub0Var.j;
                dm0 dm0Var = null;
                if (!t20.B(d50Var2.a) && !d50Var2.c) {
                    ng0 ng0Var = d50Var2.a().u;
                    if (ng0Var != null && (N0 = ng0Var.N0()) != null) {
                        dm0Var = N0.p;
                    }
                } else {
                    ng0 ng0Var2 = d50Var2.a().u;
                    if (ng0Var2 != null) {
                        dm0Var = ng0Var2.p;
                    }
                }
                if (dm0Var == null) {
                    dm0Var = ((b4) c50.a(d50Var2.a)).getPlacementScope();
                }
                qb0 N04 = d50Var2.a().N0();
                N04.getClass();
                dm0.A(dm0Var, N04, ub0Var.r);
                return x31Var;
            default:
                qb0 N05 = ub0Var.j.a().N0();
                N05.getClass();
                N05.v(ub0Var.B);
                return x31Var;
        }
    }
}
