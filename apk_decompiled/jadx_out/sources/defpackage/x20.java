package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class x20 implements lx {
    public long e;
    public float f;
    public float g;
    public float h;
    public float i;
    public float j;
    public float[] k;

    @Override // defpackage.mm
    public final float B() {
        return this.f;
    }

    @Override // defpackage.lx
    public final void F(zv0 zv0Var) {
        zv0Var.getClass();
    }

    @Override // defpackage.mm
    public final float G(float f) {
        return B() * f;
    }

    @Override // defpackage.mm
    public final /* bridge */ float O(long j) {
        return d3.e(this, j);
    }

    @Override // defpackage.mm
    public final /* bridge */ int S(float f) {
        return d3.c(f, this);
    }

    @Override // defpackage.mm
    public final /* bridge */ long Z(long j) {
        return d3.h(this, j);
    }

    @Override // defpackage.lx
    public final float c() {
        return this.h;
    }

    @Override // defpackage.mm
    public final /* bridge */ float d0(long j) {
        return d3.g(this, j);
    }

    @Override // defpackage.lx
    public final void e(float f) {
        this.j = f;
    }

    @Override // defpackage.lx
    public final void i(float f) {
        this.h = f;
    }

    @Override // defpackage.lx
    public final long j() {
        return this.e;
    }

    @Override // defpackage.mm
    public final long j0(float f) {
        return d3.i(o0(f), this);
    }

    @Override // defpackage.mm
    public final float o0(float f) {
        return f / B();
    }

    @Override // defpackage.lx
    public final void q(float f) {
        this.i = f;
    }

    public final void r(j2 j2Var, mm mmVar, gv gvVar) {
        mmVar.getClass();
        gvVar.getClass();
        r7 r7Var = (r7) j2Var.f;
        this.e = r7Var.v();
        this.f = mmVar.B();
        this.g = mmVar.y();
        gvVar.e(this);
        float f = this.j;
        float f2 = this.h;
        float f3 = this.i;
        if (f == 0.0f) {
            if (f2 != 0.0f && f3 != 0.0f) {
                j2Var.o(1.0f / f2, 1.0f / f3, 0L);
                return;
            }
            return;
        }
        float[] fArr = this.k;
        if (fArr == null) {
            fArr = m20.n();
            this.k = fArr;
        }
        if (fArr.length >= 16) {
            double d = f * 0.017453292519943295d;
            float sin = (float) Math.sin(d);
            float cos = (float) Math.cos(d);
            float f4 = cos * f2;
            float f5 = sin * f3;
            float f6 = (-sin) * f2;
            float f7 = cos * f3;
            float f8 = (f4 * f7) - (f5 * f6);
            if (f8 == 0.0f) {
                return;
            }
            float f9 = 1.0f / f8;
            fArr[0] = f7 * f9;
            fArr[1] = (-f5) * f9;
            fArr[4] = (-f6) * f9;
            fArr[5] = f4 * f9;
            r7Var.q().o(fArr);
        }
    }

    @Override // defpackage.lx
    public final float t() {
        return this.i;
    }

    @Override // defpackage.mm
    public final float y() {
        return this.g;
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

    @Override // defpackage.lx
    public final void T(long j) {
    }

    @Override // defpackage.lx
    public final void b(float f) {
    }

    @Override // defpackage.lx
    public final void b0(int i) {
    }

    @Override // defpackage.lx
    public final void d(te teVar) {
    }

    @Override // defpackage.lx
    public final void g(float f) {
    }

    @Override // defpackage.lx
    public final void h(long j) {
    }

    @Override // defpackage.lx
    public final void k(int i) {
    }

    @Override // defpackage.lx
    public final void m(boolean z) {
    }

    @Override // defpackage.lx
    public final void n(float f) {
    }

    @Override // defpackage.lx
    public final void o(long j) {
    }

    @Override // defpackage.lx
    public final void p(gh ghVar) {
    }

    @Override // defpackage.lx
    public final void s(float f) {
    }
}
