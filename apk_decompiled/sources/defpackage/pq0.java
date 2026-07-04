package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class pq0 implements lx {
    public int e;
    public float f = 1.0f;
    public float g = 1.0f;
    public float h = 1.0f;
    public float i;
    public float j;
    public long k;
    public long l;
    public float m;
    public float n;
    public long o;
    public zv0 p;
    public boolean q;
    public int r;
    public long s;
    public mm t;
    public m40 u;
    public gh v;
    public te w;
    public int x;
    public g30 y;

    public pq0() {
        long j = mx.a;
        this.k = j;
        this.l = j;
        this.n = 8.0f;
        this.o = s21.a;
        this.p = o20.o;
        this.r = 0;
        this.s = 9205357640488583168L;
        this.t = dl.e();
        this.u = m40.e;
        this.x = 3;
    }

    @Override // defpackage.mm
    public final float B() {
        return this.t.B();
    }

    @Override // defpackage.lx
    public final void F(zv0 zv0Var) {
        if (!o20.e(this.p, zv0Var)) {
            this.e |= 8192;
            this.p = zv0Var;
        }
    }

    @Override // defpackage.mm
    public final float G(float f) {
        return this.t.B() * f;
    }

    @Override // defpackage.mm
    public final /* synthetic */ float O(long j) {
        return d3.e(this, j);
    }

    @Override // defpackage.mm
    public final /* synthetic */ int S(float f) {
        return d3.c(f, this);
    }

    @Override // defpackage.lx
    public final void T(long j) {
        long j2 = this.o;
        int i = s21.b;
        if (j2 == j) {
            return;
        }
        this.e |= 4096;
        this.o = j;
    }

    @Override // defpackage.mm
    public final /* synthetic */ long Z(long j) {
        return d3.h(this, j);
    }

    @Override // defpackage.lx
    public final void b(float f) {
        if (this.h == f) {
            return;
        }
        this.e |= 4;
        this.h = f;
    }

    @Override // defpackage.lx
    public final void b0(int i) {
        if (this.r == i) {
            return;
        }
        this.e |= 32768;
        this.r = i;
    }

    @Override // defpackage.lx
    public final float c() {
        return this.f;
    }

    @Override // defpackage.lx
    public final void d(te teVar) {
        if (!o20.e(this.w, teVar)) {
            this.e |= 262144;
            this.w = teVar;
        }
    }

    @Override // defpackage.mm
    public final /* synthetic */ float d0(long j) {
        return d3.g(this, j);
    }

    @Override // defpackage.lx
    public final void e(float f) {
        if (this.m == f) {
            return;
        }
        this.e |= 1024;
        this.m = f;
    }

    @Override // defpackage.lx
    public final void g(float f) {
        if (this.j == f) {
            return;
        }
        this.e |= 16;
        this.j = f;
    }

    @Override // defpackage.lx
    public final void h(long j) {
        if (!se.c(this.k, j)) {
            this.e |= 64;
            this.k = j;
        }
    }

    @Override // defpackage.lx
    public final void i(float f) {
        if (this.f == f) {
            return;
        }
        this.e |= 1;
        this.f = f;
    }

    @Override // defpackage.lx
    public final long j() {
        return this.s;
    }

    @Override // defpackage.mm
    public final long j0(float f) {
        return d3.i(o0(f), this);
    }

    @Override // defpackage.lx
    public final void k(int i) {
        if (this.x == i) {
            return;
        }
        this.e |= 524288;
        this.x = i;
    }

    @Override // defpackage.lx
    public final void m(boolean z) {
        if (this.q != z) {
            this.e |= 16384;
            this.q = z;
        }
    }

    @Override // defpackage.lx
    public final void n(float f) {
        if (this.i == f) {
            return;
        }
        this.e |= 8;
        this.i = f;
    }

    @Override // defpackage.lx
    public final void o(long j) {
        if (!se.c(this.l, j)) {
            this.e |= 128;
            this.l = j;
        }
    }

    @Override // defpackage.mm
    public final float o0(float f) {
        return f / this.t.B();
    }

    @Override // defpackage.lx
    public final void p(gh ghVar) {
        if (!o20.e(this.v, ghVar)) {
            this.e |= 131072;
            this.v = ghVar;
        }
    }

    @Override // defpackage.lx
    public final void q(float f) {
        if (this.g == f) {
            return;
        }
        this.e |= 2;
        this.g = f;
    }

    public final void r() {
        i(1.0f);
        q(1.0f);
        b(1.0f);
        n(0.0f);
        g(0.0f);
        long j = mx.a;
        h(j);
        o(j);
        e(0.0f);
        s(8.0f);
        T(s21.a);
        F(o20.o);
        m(false);
        p(null);
        d(null);
        k(3);
        b0(0);
        this.s = 9205357640488583168L;
        this.y = null;
        this.e = 0;
    }

    @Override // defpackage.lx
    public final void s(float f) {
        if (this.n == f) {
            return;
        }
        this.e |= 2048;
        this.n = f;
    }

    @Override // defpackage.lx
    public final float t() {
        return this.g;
    }

    @Override // defpackage.mm
    public final float y() {
        return this.t.y();
    }

    @Override // defpackage.lx
    public final void a() {
    }

    @Override // defpackage.lx
    public final void f() {
    }

    @Override // defpackage.lx
    public final void l() {
    }
}
