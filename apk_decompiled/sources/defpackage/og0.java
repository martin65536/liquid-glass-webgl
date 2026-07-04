package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class og0 {
    public static final oe0 a;

    static {
        oe0 oe0Var = xg0.a;
        a = new oe0();
    }

    public static final void a(bd0 bd0Var, int i, int i2) {
        if (bd0Var instanceof jm) {
            jm jmVar = (jm) bd0Var;
            int i3 = jmVar.s;
            b(bd0Var, i3 & i, i2);
            int i4 = (~i3) & i;
            for (bd0 bd0Var2 = jmVar.t; bd0Var2 != null; bd0Var2 = bd0Var2.j) {
                a(bd0Var2, i4, i2);
            }
            return;
        }
        b(bd0Var, i & bd0Var.g, i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void b(bd0 bd0Var, int i, int i2) {
        if (i2 != 0 || bd0Var.q0()) {
            if ((i & 2) != 0 && (bd0Var instanceof r40)) {
                m20.v((r40) bd0Var);
                if (i2 == 2) {
                    k81.B(bd0Var, 2).c1();
                }
            }
            if ((i & 128) != 0 && i2 != 2) {
                k81.E(bd0Var).B();
            }
            if ((4194304 & i) != 0 && i2 != 2) {
                k81.E(bd0Var).S(false);
            }
            if ((i & 256) != 0 && (bd0Var instanceof ww)) {
                if (i2 != 1) {
                    if (i2 == 2) {
                        k81.E(bd0Var).Y(r0.P - 1);
                    }
                } else {
                    z40 E = k81.E(bd0Var);
                    E.Y(E.P + 1);
                }
                if (i2 != 2) {
                    z40 E2 = k81.E(bd0Var);
                    if (E2.P != 0 && !E2.o() && !E2.p() && !E2.O) {
                        b4 b4Var = (b4) c50.a(E2);
                        c4 c4Var = b4Var.c0.e;
                        c4Var.getClass();
                        if (E2.P > 0) {
                            ((ef0) c4Var.f).b(E2);
                            E2.O = true;
                        }
                        b4Var.H(null);
                    }
                }
            }
            if ((i & 4) != 0 && (bd0Var instanceof tp)) {
                o20.t((tp) bd0Var);
            }
            if ((i & 8) != 0 && (bd0Var instanceof qu0)) {
                k81.E(bd0Var).u = true;
            }
            if ((i & 64) != 0 && (bd0Var instanceof jk0)) {
                d50 d50Var = k81.E((jk0) bd0Var).I;
                d50Var.p.t = true;
                ub0 ub0Var = d50Var.q;
                if (ub0Var != null) {
                    ub0Var.z = true;
                }
            }
            if ((i & 2048) != 0 && (bd0Var instanceof r9)) {
                ad0 ad0Var = ((r9) bd0Var).s;
                q00.b("applyFocusProperties called on wrong node");
                d3.z(ad0Var);
                throw null;
            }
            if ((i & 4096) != 0 && (bd0Var instanceof r9)) {
                r9 r9Var = (r9) bd0Var;
                ft ftVar = ((lt) ((b4) k81.F(r9Var)).getFocusOwner()).d;
                if (ftVar.d.a(r9Var)) {
                    ftVar.a();
                }
            }
            if ((i & 2097152) != 0 && (bd0Var instanceof k00) && i2 == 2) {
                ((k00) bd0Var).I();
            }
        }
    }

    public static final void c(bd0 bd0Var) {
        if (!bd0Var.r) {
            q00.b("autoInvalidateUpdatedNode called on unattached node");
        }
        a(bd0Var, -1, 0);
    }

    public static final int d(bd0 bd0Var) {
        int i;
        int i2 = bd0Var.g;
        if (i2 != 0) {
            return i2;
        }
        Class<?> cls = bd0Var.getClass();
        oe0 oe0Var = a;
        int d = oe0Var.d(cls);
        if (d >= 0) {
            return oe0Var.c[d];
        }
        if (bd0Var instanceof r40) {
            i = 3;
        } else {
            i = 1;
        }
        if (bd0Var instanceof tp) {
            i |= 4;
        }
        if (bd0Var instanceof qu0) {
            i |= 8;
        }
        if (bd0Var instanceof xm0) {
            i |= 16;
        }
        if (bd0Var instanceof ed0) {
            i |= 32;
        }
        if (bd0Var instanceof jk0) {
            i |= 64;
        }
        if (bd0Var instanceof j40) {
            i |= 4194432;
        } else if (bd0Var instanceof sc0) {
            i |= 128;
        }
        if (bd0Var instanceof ww) {
            i |= 256;
        }
        if (bd0Var instanceof pt) {
            i |= 1024;
        }
        boolean z = bd0Var instanceof r9;
        if (z) {
            i |= 2048;
        }
        if (z) {
            i |= 4096;
        }
        if (bd0Var instanceof x30) {
            i |= 8192;
        }
        if (bd0Var instanceof q3) {
            i |= 16384;
        }
        if (bd0Var instanceof ai) {
            i |= 32768;
        }
        if (bd0Var instanceof w21) {
            i |= 262144;
        }
        if (bd0Var instanceof hb) {
            i |= 524288;
        }
        if (bd0Var instanceof k00) {
            i |= 2097152;
        }
        if (bd0Var instanceof y50) {
            i |= 8388608;
        }
        oe0Var.g(i, cls);
        return i;
    }

    public static final int e(bd0 bd0Var) {
        if (bd0Var instanceof jm) {
            jm jmVar = (jm) bd0Var;
            int i = jmVar.s;
            for (bd0 bd0Var2 = jmVar.t; bd0Var2 != null; bd0Var2 = bd0Var2.j) {
                i |= e(bd0Var2);
            }
            return i;
        }
        return d(bd0Var);
    }

    public static final boolean f(int i) {
        boolean z;
        boolean z2 = false;
        if ((i & 128) != 0) {
            z = true;
        } else {
            z = false;
        }
        if ((i & 4194304) != 0) {
            z2 = true;
        }
        return z | z2;
    }
}
