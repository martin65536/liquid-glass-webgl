package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class p01 implements z6 {
    public final s41 a;
    public final c4 b;
    public final Object c;
    public final Object d;
    public final i7 e;
    public final i7 f;
    public final i7 g;
    public long h;
    public i7 i;

    public p01(c7 c7Var, c4 c4Var, Object obj, Object obj2, i7 i7Var) {
        i7 c;
        this.a = c7Var.c(c4Var);
        this.b = c4Var;
        this.c = obj2;
        this.d = obj;
        this.e = (i7) ((gv) c4Var.f).e(obj);
        gv gvVar = (gv) c4Var.f;
        this.f = (i7) gvVar.e(obj2);
        if (i7Var != null) {
            c = dl.p(i7Var);
        } else {
            c = ((i7) gvVar.e(obj)).c();
        }
        this.g = c;
        this.h = -1L;
    }

    @Override // defpackage.z6
    public final boolean a() {
        this.a.a();
        return false;
    }

    @Override // defpackage.z6
    public final Object b(long j) {
        if (!d3.a(this, j)) {
            i7 d = this.a.d(j, this.e, this.f, this.g);
            int b = d.b();
            for (int i = 0; i < b; i++) {
                if (Float.isNaN(d.a(i))) {
                    en0.b("AnimationVector cannot contain a NaN. " + d + ". Animation: " + this + ", playTimeNanos: " + j);
                }
            }
            return ((gv) this.b.g).e(d);
        }
        return this.c;
    }

    @Override // defpackage.z6
    public final long c() {
        if (this.h < 0) {
            this.h = this.a.f(this.e, this.f, this.g);
        }
        return this.h;
    }

    @Override // defpackage.z6
    public final c4 d() {
        return this.b;
    }

    @Override // defpackage.z6
    public final Object e() {
        return this.c;
    }

    @Override // defpackage.z6
    public final i7 f(long j) {
        if (!d3.a(this, j)) {
            return this.a.c(j, this.e, this.f, this.g);
        }
        i7 i7Var = this.i;
        if (i7Var == null) {
            i7 e = this.a.e(this.e, this.f, this.g);
            this.i = e;
            return e;
        }
        return i7Var;
    }

    @Override // defpackage.z6
    public final /* synthetic */ boolean g(long j) {
        return d3.a(this, j);
    }

    public final String toString() {
        return "TargetBasedAnimation: " + this.d + " -> " + this.c + ",initial velocity: " + this.g + ", duration: " + (c() / 1000000) + " ms,animationSpec: " + this.a;
    }
}
