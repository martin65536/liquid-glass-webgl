package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class ww0 {
    public ax0 a;
    public long b;
    public boolean c;
    public int d;

    public ww0(long j, ax0 ax0Var) {
        int i;
        int numberOfTrailingZeros;
        this.a = ax0Var;
        this.b = j;
        ts0 ts0Var = cx0.a;
        if (j != 0) {
            ax0 d = d();
            long j2 = d.g;
            long[] jArr = d.h;
            if (jArr != null) {
                j = jArr[0];
            } else {
                long j3 = d.f;
                if (j3 != 0) {
                    numberOfTrailingZeros = Long.numberOfTrailingZeros(j3);
                } else {
                    long j4 = d.e;
                    if (j4 != 0) {
                        j2 += 64;
                        numberOfTrailingZeros = Long.numberOfTrailingZeros(j4);
                    }
                }
                j = numberOfTrailingZeros + j2;
            }
            synchronized (cx0.c) {
                i = cx0.f.a(j);
            }
        } else {
            i = -1;
        }
        this.d = i;
    }

    public static void q(ww0 ww0Var) {
        cx0.b.C(ww0Var);
    }

    public final void a() {
        synchronized (cx0.c) {
            b();
            p();
        }
    }

    public void b() {
        cx0.d = cx0.d.b(g());
    }

    public abstract void c();

    public ax0 d() {
        return this.a;
    }

    public abstract gv e();

    public abstract boolean f();

    public long g() {
        return this.b;
    }

    public int h() {
        return 0;
    }

    public abstract gv i();

    public final ww0 j() {
        r7 r7Var = cx0.b;
        ww0 ww0Var = (ww0) r7Var.p();
        r7Var.C(this);
        return ww0Var;
    }

    public abstract void k();

    public abstract void l();

    public abstract void m();

    public abstract void n(ny0 ny0Var);

    public final void o() {
        int i = this.d;
        if (i >= 0) {
            cx0.v(i);
            this.d = -1;
        }
    }

    public void p() {
        o();
    }

    public void r(ax0 ax0Var) {
        this.a = ax0Var;
    }

    public void s(long j) {
        this.b = j;
    }

    public void t(int i) {
        throw new IllegalStateException("Updating write count is not supported for this snapshot");
    }

    public abstract ww0 u(gv gvVar);
}
