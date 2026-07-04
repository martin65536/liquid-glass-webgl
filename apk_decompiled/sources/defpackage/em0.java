package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class em0 {
    public int e;
    public int f;
    public long g = 0;
    public long h = fm0.a;
    public long i = 0;

    public abstract Object A();

    public int f0() {
        return (int) (this.g & 4294967295L);
    }

    public int g0() {
        return (int) (this.g >> 32);
    }

    public final void h0() {
        this.e = n30.j((int) (this.g >> 32), si.j(this.h), si.h(this.h));
        this.f = n30.j((int) (this.g & 4294967295L), si.i(this.h), si.g(this.h));
        int i = this.e;
        long j = this.g;
        this.i = (((i - ((int) (j >> 32))) / 2) << 32) | (4294967295L & ((r0 - ((int) (j & 4294967295L))) / 2));
    }

    public abstract void i0(long j, float f, gv gvVar);

    public final void k0(long j) {
        if (!c20.a(this.g, j)) {
            this.g = j;
            h0();
        }
    }

    public final void l0(long j) {
        if (!si.b(this.h, j)) {
            this.h = j;
            h0();
        }
    }
}
