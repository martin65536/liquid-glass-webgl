package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class dh0 implements t7 {
    public final t7 e;
    public final int f;
    public int g;

    public dh0(t7 t7Var, int i) {
        this.e = t7Var;
        this.f = i;
    }

    @Override // defpackage.t7
    public final void a(int i, Object obj) {
        int i2;
        if (this.g == 0) {
            i2 = this.f;
        } else {
            i2 = 0;
        }
        this.e.a(i + i2, obj);
    }

    @Override // defpackage.t7
    public final void b(Object obj) {
        this.g++;
        this.e.b(obj);
    }

    @Override // defpackage.t7
    public final void c() {
        this.e.c();
    }

    @Override // defpackage.t7
    public final void d(int i, Object obj) {
        int i2;
        if (this.g == 0) {
            i2 = this.f;
        } else {
            i2 = 0;
        }
        this.e.d(i + i2, obj);
    }

    @Override // defpackage.t7
    public final void f(int i, int i2, int i3) {
        int i4;
        if (this.g == 0) {
            i4 = this.f;
        } else {
            i4 = 0;
        }
        this.e.f(i + i4, i2 + i4, i3);
    }

    @Override // defpackage.t7
    public final void h(int i, int i2) {
        int i3;
        if (this.g == 0) {
            i3 = this.f;
        } else {
            i3 = 0;
        }
        this.e.h(i + i3, i2);
    }

    @Override // defpackage.t7
    public final void i(kv kvVar, Object obj) {
        this.e.i(kvVar, obj);
    }

    @Override // defpackage.t7
    public final void j() {
        if (this.g <= 0) {
            rh.a("OffsetApplier up called with no corresponding down");
        }
        this.g--;
        this.e.j();
    }

    @Override // defpackage.t7
    public final /* synthetic */ void e() {
    }
}
