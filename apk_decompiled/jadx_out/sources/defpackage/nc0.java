package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class nc0 extends z30 implements vu {
    public final /* synthetic */ int f;
    public final /* synthetic */ oc0 g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ nc0(oc0 oc0Var, int i) {
        super(0);
        this.f = i;
        this.g = oc0Var;
    }

    @Override // defpackage.vu
    public final Object a() {
        dm0 placementScope;
        int i = this.f;
        x31 x31Var = x31.a;
        oc0 oc0Var = this.g;
        switch (i) {
            case 0:
                d50 d50Var = oc0Var.j;
                d50Var.i = 0;
                ef0 w = d50Var.a.w();
                Object[] objArr = w.e;
                int i2 = w.g;
                for (int i3 = 0; i3 < i2; i3++) {
                    oc0 oc0Var2 = ((z40) objArr[i3]).I.p;
                    oc0Var2.l = oc0Var2.m;
                    oc0Var2.m = Integer.MAX_VALUE;
                    oc0Var2.w = false;
                    if (oc0Var2.p == x40.f) {
                        oc0Var2.p = x40.g;
                    }
                }
                z40 z40Var = d50Var.a;
                z40 z40Var2 = d50Var.a;
                ef0 w2 = z40Var.w();
                Object[] objArr2 = w2.e;
                int i4 = w2.g;
                for (int i5 = 0; i5 < i4; i5++) {
                    ((z40) objArr2[i5]).I.p.A.getClass();
                }
                if (oc0Var.I().o) {
                    bf0 bf0Var = (bf0) z40Var2.m();
                    int i6 = bf0Var.e.g;
                    for (int i7 = 0; i7 < i6; i7++) {
                        ((z40) bf0Var.get(i7)).H.d.o = true;
                    }
                }
                oc0Var.I().w0().a();
                if (oc0Var.I().o) {
                    bf0 bf0Var2 = (bf0) z40Var2.m();
                    int i8 = bf0Var2.e.g;
                    for (int i9 = 0; i9 < i8; i9++) {
                        ((z40) bf0Var2.get(i9)).H.d.o = false;
                    }
                }
                ef0 w3 = z40Var2.w();
                Object[] objArr3 = w3.e;
                int i10 = w3.g;
                for (int i11 = 0; i11 < i10; i11++) {
                    z40 z40Var3 = (z40) objArr3[i11];
                    d50 d50Var2 = z40Var3.I;
                    if (d50Var2.p.l != z40Var3.t()) {
                        z40Var2.M();
                        z40Var2.z();
                        if (z40Var3.t() == Integer.MAX_VALUE) {
                            if (d50Var2.c || t20.B(z40Var3)) {
                                ub0 ub0Var = d50Var2.q;
                                ub0Var.getClass();
                                ub0Var.n0(false);
                            }
                            d50Var2.p.p0();
                        }
                    }
                }
                ef0 w4 = z40Var2.w();
                Object[] objArr4 = w4.e;
                int i12 = w4.g;
                for (int i13 = 0; i13 < i12; i13++) {
                    a50 a50Var = ((z40) objArr4[i13]).I.p.A;
                    a50Var.getClass();
                    a50Var.c = false;
                }
                return x31Var;
            case 1:
                oc0Var.j.a().v(oc0Var.E);
                return x31Var;
            default:
                d50 d50Var3 = oc0Var.j;
                ng0 ng0Var = d50Var3.a().u;
                if (ng0Var == null || (placementScope = ng0Var.p) == null) {
                    placementScope = ((b4) c50.a(d50Var3.a)).getPlacementScope();
                }
                gv gvVar = oc0Var.J;
                if (gvVar == null) {
                    ng0 a = d50Var3.a();
                    long j = oc0Var.K;
                    float f = oc0Var.L;
                    placementScope.getClass();
                    dm0.r(placementScope, a);
                    a.i0(v10.c(j, a.i), f, null);
                } else {
                    ng0 a2 = d50Var3.a();
                    long j2 = oc0Var.K;
                    float f2 = oc0Var.L;
                    placementScope.getClass();
                    dm0.r(placementScope, a2);
                    a2.i0(v10.c(j2, a2.i), f2, gvVar);
                }
                return x31Var;
        }
    }
}
