package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class lo0 implements gv {
    public final /* synthetic */ int e;
    public final /* synthetic */ int f;
    public final /* synthetic */ Object g;
    public final /* synthetic */ Object h;

    public /* synthetic */ lo0(int i, int i2, Object obj, Object obj2) {
        this.e = i2;
        this.g = obj;
        this.f = i;
        this.h = obj2;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        sh shVar;
        sh shVar2;
        int i;
        boolean z;
        boolean z2;
        int i2;
        int i3 = this.e;
        int i4 = 0;
        Object obj2 = this.h;
        int i5 = this.f;
        Object obj3 = this.g;
        x31 x31Var = x31.a;
        switch (i3) {
            case 0:
                mo0 mo0Var = (mo0) obj3;
                oe0 oe0Var = (oe0) obj2;
                sh shVar3 = (sh) obj;
                if (mo0Var.e == i5 && o20.e(oe0Var, mo0Var.f) && (shVar3 instanceof yh)) {
                    long[] jArr = oe0Var.a;
                    int length = jArr.length - 2;
                    if (length >= 0) {
                        int i6 = 0;
                        while (true) {
                            long j = jArr[i6];
                            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                int i7 = 8;
                                int i8 = 8 - ((~(i6 - length)) >>> 31);
                                int i9 = i4;
                                while (i9 < i8) {
                                    if ((255 & j) < 128) {
                                        int i10 = (i6 << 3) + i9;
                                        Object obj4 = oe0Var.b[i10];
                                        if (oe0Var.c[i10] != i5) {
                                            z = true;
                                        } else {
                                            z = false;
                                        }
                                        if (z) {
                                            i = i7;
                                            yh yhVar = (yh) shVar3;
                                            shVar2 = shVar3;
                                            ve0 ve0Var = yhVar.k;
                                            t20.I(ve0Var, obj4, mo0Var);
                                            z2 = z;
                                            if (obj4 instanceof ym) {
                                                ym ymVar = (ym) obj4;
                                                if (!ve0Var.c(ymVar)) {
                                                    t20.J(yhVar.n, ymVar);
                                                }
                                                ve0 ve0Var2 = mo0Var.g;
                                                if (ve0Var2 != null) {
                                                    ve0Var2.k(obj4);
                                                }
                                            }
                                        } else {
                                            shVar2 = shVar3;
                                            z2 = z;
                                            i = i7;
                                        }
                                        if (z2) {
                                            oe0Var.f(i10);
                                        }
                                    } else {
                                        shVar2 = shVar3;
                                        i = i7;
                                    }
                                    j >>= i;
                                    i9++;
                                    i7 = i;
                                    shVar3 = shVar2;
                                }
                                shVar = shVar3;
                                if (i8 != i7) {
                                }
                            } else {
                                shVar = shVar3;
                            }
                            if (i6 != length) {
                                i6++;
                                shVar3 = shVar;
                                i4 = 0;
                            }
                        }
                    }
                }
                return x31Var;
            default:
                jt0 jt0Var = (jt0) obj3;
                em0 em0Var = (em0) obj2;
                dm0 dm0Var = (dm0) obj;
                int g = jt0Var.s.a.g();
                if (g < 0) {
                    g = 0;
                }
                if (g <= i5) {
                    i5 = g;
                }
                int i11 = -i5;
                boolean z3 = jt0Var.t;
                if (z3) {
                    i2 = 0;
                } else {
                    i2 = i11;
                }
                if (!z3) {
                    i11 = 0;
                }
                dm0Var.e = true;
                dm0.E(dm0Var, em0Var, i2, i11);
                dm0Var.e = false;
                return x31Var;
        }
    }
}
