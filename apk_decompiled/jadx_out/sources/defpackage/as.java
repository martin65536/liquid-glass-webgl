package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class as extends bd0 implements r40 {
    public gn s;
    public float t;

    @Override // defpackage.r40
    public final qc0 Y(ob0 ob0Var, kc0 kc0Var, long j) {
        int j2;
        int h;
        int i;
        int i2;
        if (si.d(j) && this.s != gn.e) {
            int round = Math.round(si.h(j) * this.t);
            int j3 = si.j(j);
            j2 = si.h(j);
            if (round < j3) {
                round = j3;
            }
            if (round <= j2) {
                j2 = round;
            }
            h = j2;
        } else {
            j2 = si.j(j);
            h = si.h(j);
        }
        if (si.c(j) && this.s != gn.f) {
            int round2 = Math.round(si.g(j) * this.t);
            int i3 = si.i(j);
            i = si.g(j);
            if (round2 < i3) {
                round2 = i3;
            }
            if (round2 <= i) {
                i = round2;
            }
            i2 = i;
        } else {
            int i4 = si.i(j);
            int g = si.g(j);
            i = i4;
            i2 = g;
        }
        em0 v = kc0Var.v(ti.a(j2, h, i, i2));
        return ob0Var.e0(v.e, v.f, fr.e, new k8(v, 2));
    }
}
