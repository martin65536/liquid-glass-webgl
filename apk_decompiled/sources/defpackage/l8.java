package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class l8 extends bd0 implements r40 {
    public float s;

    public final long D0(long j, boolean z) {
        int round;
        int g = si.g(j);
        if (g != Integer.MAX_VALUE && (round = Math.round(g * this.s)) > 0) {
            if (!z || jc0.u(round, g, j)) {
                return (round << 32) | (g & 4294967295L);
            }
            return 0L;
        }
        return 0L;
    }

    public final long E0(long j, boolean z) {
        int round;
        int h = si.h(j);
        if (h != Integer.MAX_VALUE && (round = Math.round(h / this.s)) > 0) {
            if (!z || jc0.u(h, round, j)) {
                return (h << 32) | (round & 4294967295L);
            }
            return 0L;
        }
        return 0L;
    }

    public final long F0(long j, boolean z) {
        int i = si.i(j);
        int round = Math.round(i * this.s);
        if (round > 0) {
            if (!z || jc0.u(round, i, j)) {
                return (round << 32) | (i & 4294967295L);
            }
            return 0L;
        }
        return 0L;
    }

    public final long G0(long j, boolean z) {
        int j2 = si.j(j);
        int round = Math.round(j2 / this.s);
        if (round > 0) {
            if (!z || jc0.u(j2, round, j)) {
                return (j2 << 32) | (round & 4294967295L);
            }
            return 0L;
        }
        return 0L;
    }

    @Override // defpackage.r40
    public final qc0 Y(ob0 ob0Var, kc0 kc0Var, long j) {
        boolean z;
        boolean z2 = true;
        long E0 = E0(j, true);
        if (c20.a(E0, 0L)) {
            E0 = D0(j, true);
            if (c20.a(E0, 0L)) {
                E0 = G0(j, true);
                if (c20.a(E0, 0L)) {
                    E0 = F0(j, true);
                    if (c20.a(E0, 0L)) {
                        E0 = E0(j, false);
                        if (c20.a(E0, 0L)) {
                            E0 = D0(j, false);
                            if (c20.a(E0, 0L)) {
                                E0 = G0(j, false);
                                if (c20.a(E0, 0L)) {
                                    E0 = F0(j, false);
                                    if (c20.a(E0, 0L)) {
                                        E0 = 0;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!c20.a(E0, 0L)) {
            int i = (int) (E0 >> 32);
            int i2 = (int) (4294967295L & E0);
            if (i >= 0) {
                z = true;
            } else {
                z = false;
            }
            if (i2 < 0) {
                z2 = false;
            }
            if (!(z & z2)) {
                s00.a("width and height must be >= 0");
            }
            j = ti.g(i, i, i2, i2);
        }
        em0 v = kc0Var.v(j);
        return ob0Var.e0(v.e, v.f, fr.e, new k8(v, 0));
    }
}
