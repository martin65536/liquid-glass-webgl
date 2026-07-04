package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class sj0 extends bd0 implements r40 {
    public float s;
    public float t;
    public float u;
    public float v;
    public boolean w;

    @Override // defpackage.r40
    public final qc0 Y(ob0 ob0Var, kc0 kc0Var, long j) {
        int c = d3.c(this.u, ob0Var) + d3.c(this.s, ob0Var);
        int c2 = d3.c(this.v, ob0Var) + d3.c(this.t, ob0Var);
        em0 v = kc0Var.v(ti.h(-c, -c2, j));
        return ob0Var.e0(ti.f(v.e + c, j), ti.e(v.f + c2, j), fr.e, new c(16, this, v));
    }
}
