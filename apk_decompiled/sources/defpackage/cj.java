package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class cj extends bd0 implements ai, sc0 {
    public dj0 s;
    public final hu0 t;
    public boolean u;
    public final wt0 v;
    public boolean x;
    public boolean z;
    public final ib w = new ib(0);
    public long y = -1;

    public cj(dj0 dj0Var, hu0 hu0Var, boolean z, wt0 wt0Var) {
        this.s = dj0Var;
        this.t = hu0Var;
        this.u = z;
        this.v = wt0Var;
    }

    public static final float D0(cj cjVar, ob obVar, long j) {
        float f;
        wo0 wo0Var;
        int compare;
        long j2 = cjVar.y;
        ef0 ef0Var = cjVar.w.a;
        int i = ef0Var.g - 1;
        Object[] objArr = ef0Var.e;
        wo0 wo0Var2 = null;
        if (i < objArr.length) {
            wo0Var = null;
            while (true) {
                if (i >= 0) {
                    wo0 wo0Var3 = (wo0) ((yi) objArr[i]).a.a();
                    if (wo0Var3 != null) {
                        long b = wo0Var3.b();
                        long J = d20.J(cjVar.E0());
                        f = 0.0f;
                        int ordinal = cjVar.s.ordinal();
                        if (ordinal != 0) {
                            if (ordinal == 1) {
                                compare = Float.compare(Float.intBitsToFloat((int) (b >> 32)), Float.intBitsToFloat((int) (J >> 32)));
                            } else {
                                v7.k();
                                return 0.0f;
                            }
                        } else {
                            compare = Float.compare(Float.intBitsToFloat((int) (b & 4294967295L)), Float.intBitsToFloat((int) (J & 4294967295L)));
                        }
                        if (compare <= 0) {
                            wo0Var = wo0Var3;
                        } else if (wo0Var == null) {
                            wo0Var = wo0Var3;
                        }
                    }
                    i--;
                } else {
                    f = 0.0f;
                    break;
                }
            }
        } else {
            f = 0.0f;
            wo0Var = null;
        }
        if (wo0Var == null) {
            if (cjVar.x) {
                wo0Var2 = (wo0) cjVar.v.a();
            }
            if (wo0Var2 == null) {
                return f;
            }
            wo0Var = wo0Var2;
        }
        long J2 = d20.J(j2);
        int ordinal2 = cjVar.s.ordinal();
        if (ordinal2 != 0) {
            if (ordinal2 == 1) {
                float f2 = wo0Var.a;
                return obVar.a(f2 - ((int) (j >> 32)), wo0Var.c - f2, Float.intBitsToFloat((int) (J2 >> 32)));
            }
            v7.k();
            return f;
        }
        float f3 = wo0Var.b;
        return obVar.a(f3 - ((int) (j & 4294967295L)), wo0Var.d - f3, Float.intBitsToFloat((int) (J2 & 4294967295L)));
    }

    public static boolean F0(cj cjVar, wo0 wo0Var, long j, long j2, int i) {
        if ((i & 1) != 0) {
            j = cjVar.E0();
        }
        long j3 = j;
        if ((i & 2) != 0) {
            j2 = 0;
        }
        long H0 = cjVar.H0(wo0Var, j3, j2);
        if (Math.abs(Float.intBitsToFloat((int) (H0 >> 32))) <= 0.5f && Math.abs(Float.intBitsToFloat((int) (H0 & 4294967295L))) <= 0.5f) {
            return true;
        }
        return false;
    }

    @Override // defpackage.sc0
    public final void C(long j) {
        int i;
        long j2;
        long E0 = E0();
        this.y = j;
        int ordinal = this.s.ordinal();
        if (ordinal != 0) {
            if (ordinal == 1) {
                i = o20.i((int) (j >> 32), (int) (E0 >> 32));
            } else {
                v7.k();
                return;
            }
        } else {
            i = o20.i((int) (j & 4294967295L), (int) (E0 & 4294967295L));
        }
        if (i < 0) {
            if (!this.u) {
                if (this.s == dj0.e) {
                    j2 = (((int) (E0 & 4294967295L)) - ((int) (j & 4294967295L))) & 4294967295L;
                } else {
                    j2 = (((int) (E0 >> 32)) - ((int) (j >> 32))) << 32;
                }
            } else {
                j2 = 0;
            }
            long j3 = j2;
            wo0 wo0Var = (wo0) this.v.a();
            if (wo0Var != null && !this.z && !this.x && F0(this, wo0Var, E0, 0L, 2) && !F0(this, wo0Var, 0L, j3, 1)) {
                this.x = true;
                G0(j3);
            }
        }
    }

    public final long E0() {
        long j = this.y;
        if (c20.a(j, -1L)) {
            return 0L;
        }
        return j;
    }

    public final void G0(long j) {
        gi giVar = qb.a;
        ob obVar = (ob) n20.p(this, giVar);
        if (this.z) {
            t00.c("launchAnimation called when previous animation was running");
        }
        f31.G(p0(), null, new bj(this, new f41(((ob) n20.p(this, giVar)).b()), obVar, j, null), 1);
    }

    public final long H0(wo0 wo0Var, long j, long j2) {
        long J = d20.J(j);
        int ordinal = this.s.ordinal();
        if (ordinal != 0) {
            if (ordinal == 1) {
                ob obVar = (ob) n20.p(this, qb.a);
                float f = wo0Var.a;
                return (Float.floatToRawIntBits(obVar.a(f - ((int) (j2 >> 32)), wo0Var.c - f, Float.intBitsToFloat((int) (J >> 32)))) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L);
            }
            v7.k();
            return 0L;
        }
        ob obVar2 = (ob) n20.p(this, qb.a);
        float f2 = wo0Var.b;
        float a = obVar2.a(f2 - ((int) (j2 & 4294967295L)), wo0Var.d - f2, Float.intBitsToFloat((int) (J & 4294967295L)));
        return (Float.floatToRawIntBits(0.0f) << 32) | (Float.floatToRawIntBits(a) & 4294967295L);
    }

    @Override // defpackage.bd0
    public final boolean q0() {
        return false;
    }
}
