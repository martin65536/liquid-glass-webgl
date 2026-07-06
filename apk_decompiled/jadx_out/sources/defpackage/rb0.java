package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class rb0 implements l40 {
    public final qb0 e;

    public rb0(qb0 qb0Var) {
        this.e = qb0Var;
    }

    @Override // defpackage.l40
    public final l40 C() {
        qb0 N0;
        if (!Q()) {
            q00.b("LayoutCoordinate operations are only valid when isAttached is true");
        }
        ng0 ng0Var = this.e.s.s.H.d.u;
        if (ng0Var != null && (N0 = ng0Var.N0()) != null) {
            return N0.v;
        }
        return null;
    }

    @Override // defpackage.l40
    public final long P(l40 l40Var, long j) {
        return R(l40Var, j);
    }

    @Override // defpackage.l40
    public final boolean Q() {
        return this.e.s.P0().r;
    }

    @Override // defpackage.l40
    public final long R(l40 l40Var, long j) {
        boolean z = l40Var instanceof rb0;
        qb0 qb0Var = this.e;
        if (z) {
            qb0 qb0Var2 = ((rb0) l40Var).e;
            ng0 ng0Var = qb0Var2.s;
            ng0Var.Z0();
            qb0 N0 = qb0Var.s.L0(ng0Var).N0();
            if (N0 != null) {
                long b = v10.b(v10.c(qb0Var2.H0(N0, false), f31.L(j)), qb0Var.H0(N0, false));
                return (Float.floatToRawIntBits((int) (b >> 32)) << 32) | (Float.floatToRawIntBits((int) (b & 4294967295L)) & 4294967295L);
            }
            qb0 k = y20.k(qb0Var2);
            long c = v10.c(v10.c(qb0Var2.H0(k, false), k.t), f31.L(j));
            qb0 k2 = y20.k(qb0Var);
            long b2 = v10.b(c, v10.c(qb0Var.H0(k2, false), k2.t));
            long floatToRawIntBits = Float.floatToRawIntBits((int) (b2 >> 32));
            long floatToRawIntBits2 = Float.floatToRawIntBits((int) (b2 & 4294967295L)) & 4294967295L;
            ng0 ng0Var2 = k2.s.u;
            ng0Var2.getClass();
            ng0 ng0Var3 = k.s.u;
            ng0Var3.getClass();
            return ng0Var2.R(ng0Var3, floatToRawIntBits2 | (floatToRawIntBits << 32));
        }
        qb0 k3 = y20.k(qb0Var);
        ng0 ng0Var4 = k3.s;
        long R = R(k3.v, j);
        float f = (int) (k3.t & 4294967295L);
        long f2 = ch0.f(R, (4294967295L & Float.floatToRawIntBits(f)) | (Float.floatToRawIntBits((int) (r5 >> 32)) << 32));
        if (!ng0Var4.P0().r) {
            q00.b("LayoutCoordinate operations are only valid when isAttached is true");
        }
        ng0Var4.Z0();
        ng0 ng0Var5 = ng0Var4.u;
        if (ng0Var5 != null) {
            ng0Var4 = ng0Var5;
        }
        return ch0.g(f2, ng0Var4.R(l40Var, 0L));
    }

    @Override // defpackage.l40
    public final wo0 U(l40 l40Var, boolean z) {
        return this.e.s.U(l40Var, z);
    }

    @Override // defpackage.l40
    public final long X() {
        qb0 qb0Var = this.e;
        return (qb0Var.e << 32) | (qb0Var.f & 4294967295L);
    }

    public final long a() {
        qb0 qb0Var = this.e;
        qb0 k = y20.k(qb0Var);
        return ch0.f(R(k.v, 0L), qb0Var.s.R(k.s, 0L));
    }

    @Override // defpackage.l40
    public final long u(long j) {
        return this.e.s.u(ch0.g(0L, a()));
    }

    @Override // defpackage.l40
    public final long z(long j) {
        return this.e.s.z(ch0.g(j, a()));
    }
}
