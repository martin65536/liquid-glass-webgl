package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class dm0 implements mm {
    public boolean e;

    public static void A(dm0 dm0Var, em0 em0Var, long j) {
        dm0Var.getClass();
        r(dm0Var, em0Var);
        em0Var.i0(v10.c(j, em0Var.i), 0.0f, null);
    }

    public static void C(dm0 dm0Var, em0 em0Var, int i, int i2) {
        long j = (i << 32) | (i2 & 4294967295L);
        if (dm0Var.v() != m40.e && dm0Var.x() != 0) {
            int x = (dm0Var.x() - em0Var.e) - ((int) (j >> 32));
            r(dm0Var, em0Var);
            em0Var.i0(v10.c((x << 32) | (((int) (j & 4294967295L)) & 4294967295L), em0Var.i), 0.0f, null);
        } else {
            r(dm0Var, em0Var);
            em0Var.i0(v10.c(j, em0Var.i), 0.0f, null);
        }
    }

    public static void E(dm0 dm0Var, em0 em0Var, int i, int i2) {
        int i3 = fm0.b;
        oj0 oj0Var = oj0.i;
        long j = (i << 32) | (i2 & 4294967295L);
        if (dm0Var.v() != m40.e && dm0Var.x() != 0) {
            int x = (dm0Var.x() - em0Var.e) - ((int) (j >> 32));
            r(dm0Var, em0Var);
            em0Var.i0(v10.c((x << 32) | (((int) (j & 4294967295L)) & 4294967295L), em0Var.i), 0.0f, oj0Var);
        } else {
            r(dm0Var, em0Var);
            em0Var.i0(v10.c(j, em0Var.i), 0.0f, oj0Var);
        }
    }

    public static void H(dm0 dm0Var, em0 em0Var, gv gvVar) {
        dm0Var.getClass();
        r(dm0Var, em0Var);
        em0Var.i0(v10.c(0L, em0Var.i), 0.0f, gvVar);
    }

    public static void I(dm0 dm0Var, em0 em0Var, long j, mp mpVar, int i) {
        gv gvVar = mpVar;
        if ((i & 4) != 0) {
            int i2 = fm0.b;
            gvVar = oj0.i;
        }
        dm0Var.getClass();
        r(dm0Var, em0Var);
        em0Var.i0(v10.c(j, em0Var.i), 0.0f, gvVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void r(dm0 dm0Var, em0 em0Var) {
        dm0Var.getClass();
        if (em0Var instanceof od0) {
            ((od0) em0Var).E(dm0Var.e);
        }
    }

    public static void z(dm0 dm0Var, em0 em0Var, int i, int i2) {
        dm0Var.getClass();
        r(dm0Var, em0Var);
        em0Var.i0(v10.c((i2 & 4294967295L) | (i << 32), em0Var.i), 0.0f, null);
    }

    @Override // defpackage.mm
    public final float G(float f) {
        return B() * f;
    }

    @Override // defpackage.mm
    public final /* synthetic */ float O(long j) {
        return d3.e(this, j);
    }

    @Override // defpackage.mm
    public final /* synthetic */ int S(float f) {
        return d3.c(f, this);
    }

    @Override // defpackage.mm
    public final /* synthetic */ long Z(long j) {
        return d3.h(this, j);
    }

    @Override // defpackage.mm
    public final /* synthetic */ float d0(long j) {
        return d3.g(this, j);
    }

    @Override // defpackage.mm
    public final long j0(float f) {
        return d3.i(f / B(), this);
    }

    @Override // defpackage.mm
    public final float o0(float f) {
        return f / B();
    }

    public float u(ty tyVar) {
        return Float.NaN;
    }

    public abstract m40 v();

    public abstract int x();
}
