package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class d50 {
    public final z40 a;
    public boolean b;
    public boolean c;
    public boolean e;
    public boolean f;
    public boolean g;
    public int h;
    public int i;
    public boolean j;
    public boolean k;
    public int l;
    public boolean m;
    public boolean n;
    public int o;
    public ub0 q;
    public v40 d = v40.i;
    public final oc0 p = new oc0(this);

    public d50(z40 z40Var) {
        this.a = z40Var;
    }

    public final ng0 a() {
        return this.a.H.d;
    }

    public final void b() {
        v40 v40Var = this.a.I.d;
        v40 v40Var2 = v40.g;
        v40 v40Var3 = v40.h;
        if (v40Var == v40Var2 || v40Var == v40Var3) {
            if (this.p.D) {
                g(true);
            } else {
                f(true);
            }
        }
        if (v40Var == v40Var3) {
            ub0 ub0Var = this.q;
            if (ub0Var != null && ub0Var.x) {
                i(true);
            } else {
                h(true);
            }
        }
    }

    public final void c(long j) {
        ub0 ub0Var = this.q;
        if (ub0Var != null) {
            d50 d50Var = ub0Var.j;
            d50Var.d = v40.f;
            z40 z40Var = d50Var.a;
            d50Var.e = false;
            ub0Var.B = j;
            pj0 snapshotObserver = ((b4) c50.a(z40Var)).getSnapshotObserver();
            tb0 tb0Var = ub0Var.C;
            snapshotObserver.a.b(z40Var, snapshotObserver.b, tb0Var);
            d50Var.f = true;
            d50Var.g = true;
            boolean B = t20.B(z40Var);
            oc0 oc0Var = d50Var.p;
            if (B) {
                oc0Var.y = true;
                oc0Var.z = true;
            } else {
                oc0Var.x = true;
            }
            d50Var.d = v40.i;
        }
    }

    public final void d(int i) {
        boolean z;
        d50 d50Var;
        int i2 = this.l;
        this.l = i;
        boolean z2 = false;
        if (i2 == 0) {
            z = true;
        } else {
            z = false;
        }
        if (i == 0) {
            z2 = true;
        }
        if (z != z2) {
            z40 s = this.a.s();
            if (s != null) {
                d50Var = s.I;
            } else {
                d50Var = null;
            }
            if (d50Var != null) {
                int i3 = d50Var.l;
                if (i == 0) {
                    d50Var.d(i3 - 1);
                } else {
                    d50Var.d(i3 + 1);
                }
            }
        }
    }

    public final void e(int i) {
        boolean z;
        d50 d50Var;
        int i2 = this.o;
        this.o = i;
        boolean z2 = false;
        if (i2 == 0) {
            z = true;
        } else {
            z = false;
        }
        if (i == 0) {
            z2 = true;
        }
        if (z != z2) {
            z40 s = this.a.s();
            if (s != null) {
                d50Var = s.I;
            } else {
                d50Var = null;
            }
            if (d50Var != null) {
                int i3 = d50Var.o;
                if (i == 0) {
                    d50Var.e(i3 - 1);
                } else {
                    d50Var.e(i3 + 1);
                }
            }
        }
    }

    public final void f(boolean z) {
        if (this.k != z) {
            this.k = z;
            if (z && !this.j) {
                d(this.l + 1);
            } else if (!z && !this.j) {
                d(this.l - 1);
            }
        }
    }

    public final void g(boolean z) {
        if (this.j != z) {
            this.j = z;
            if (z && !this.k) {
                d(this.l + 1);
            } else if (!z && !this.k) {
                d(this.l - 1);
            }
        }
    }

    public final void h(boolean z) {
        if (this.n != z) {
            this.n = z;
            if (z && !this.m) {
                e(this.o + 1);
            } else if (!z && !this.m) {
                e(this.o - 1);
            }
        }
    }

    public final void i(boolean z) {
        if (this.m != z) {
            this.m = z;
            if (z && !this.n) {
                e(this.o + 1);
            } else if (!z && !this.n) {
                e(this.o - 1);
            }
        }
    }

    public final void j() {
        oc0 oc0Var = this.p;
        d50 d50Var = oc0Var.j;
        Object obj = oc0Var.u;
        z40 z40Var = this.a;
        if ((obj != null || d50Var.a().A() != null) && oc0Var.t) {
            oc0Var.t = false;
            oc0Var.u = d50Var.a().A();
            z40 s = z40Var.s();
            if (s != null) {
                z40.T(s, false, 7);
            }
        }
        ub0 ub0Var = this.q;
        if (ub0Var != null) {
            d50 d50Var2 = ub0Var.j;
            if (ub0Var.A == null) {
                qb0 N0 = d50Var2.a().N0();
                N0.getClass();
                if (N0.s.A() == null) {
                    return;
                }
            }
            if (ub0Var.z) {
                ub0Var.z = false;
                qb0 N02 = d50Var2.a().N0();
                N02.getClass();
                ub0Var.A = N02.s.A();
                if (t20.B(z40Var)) {
                    z40 s2 = z40Var.s();
                    if (s2 != null) {
                        z40.T(s2, false, 7);
                        return;
                    }
                    return;
                }
                z40 s3 = z40Var.s();
                if (s3 != null) {
                    z40.R(s3, false, 7);
                }
            }
        }
    }
}
