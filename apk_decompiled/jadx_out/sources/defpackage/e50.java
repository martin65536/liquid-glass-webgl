package defpackage;

import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class e50 implements iz0, rc0 {
    public final /* synthetic */ h50 e;
    public final /* synthetic */ n50 f;

    public e50(n50 n50Var) {
        this.f = n50Var;
        this.e = n50Var.l;
    }

    @Override // defpackage.mm
    public final float B() {
        return this.e.f;
    }

    @Override // defpackage.rc0
    public final boolean D() {
        return this.e.D();
    }

    @Override // defpackage.mm
    public final float G(float f) {
        return this.e.B() * f;
    }

    @Override // defpackage.iz0
    public final List L(kv kvVar, Object obj) {
        f50 f50Var;
        n50 n50Var = this.f;
        z40 z40Var = n50Var.e;
        ve0 ve0Var = n50Var.k;
        z40 z40Var2 = (z40) ve0Var.g(obj);
        if (z40Var2 != null && ((bf0) z40Var.n()).e.i(z40Var2) < n50Var.h) {
            return z40Var2.I.p.m0();
        }
        ve0 ve0Var2 = n50Var.p;
        ve0 ve0Var3 = n50Var.n;
        ef0 ef0Var = n50Var.q;
        if (ef0Var.g < n50Var.i) {
            q00.a("Error: currentApproachIndex cannot be greater than the size of theapproachComposedSlotIds list.");
        }
        z40 z40Var3 = (z40) ve0Var.g(obj);
        int i = ef0Var.g;
        int i2 = n50Var.i;
        if (i == i2) {
            ef0Var.b(obj);
        } else {
            Object[] objArr = ef0Var.e;
            Object obj2 = objArr[i2];
            objArr[i2] = obj;
        }
        n50Var.i++;
        boolean b = ve0Var3.b(obj);
        if (!b && z40Var3 == null) {
            n50Var.k(obj, kvVar, false);
            ve0Var2.m(obj, n50Var.f(obj));
        } else {
            if (!b && z40Var3 != null) {
                n50Var.j(((bf0) z40Var.n()).e.i(z40Var3), ((bf0) z40Var.n()).e.g);
                n50Var.s++;
                ve0Var.k(obj);
                ve0Var3.m(obj, z40Var3);
                ve0Var2.m(obj, n50Var.f(obj));
                if (z40Var.E()) {
                    n50Var.h();
                }
            }
            z40 z40Var4 = (z40) ve0Var3.g(obj);
            hl0 hl0Var = null;
            if (z40Var4 != null) {
                f50Var = (f50) n50Var.j.g(z40Var4);
            } else {
                f50Var = null;
            }
            if (f50Var != null && f50Var.d) {
                n50Var.m(z40Var4, obj, false, kvVar);
            }
            if (f50Var != null) {
                hl0Var = f50Var.f;
            }
            if (hl0Var != null) {
                n50Var.d(f50Var, true);
            }
        }
        z40 z40Var5 = (z40) ve0Var3.g(obj);
        if (z40Var5 != null) {
            List m0 = z40Var5.I.p.m0();
            bf0 bf0Var = (bf0) m0;
            int i3 = bf0Var.e.g;
            for (int i4 = 0; i4 < i3; i4++) {
                ((oc0) bf0Var.get(i4)).j.b = true;
            }
            return m0;
        }
        return er.e;
    }

    @Override // defpackage.mm
    public final float O(long j) {
        h50 h50Var = this.e;
        h50Var.getClass();
        return d3.e(h50Var, j);
    }

    @Override // defpackage.mm
    public final int S(float f) {
        h50 h50Var = this.e;
        h50Var.getClass();
        return d3.c(f, h50Var);
    }

    @Override // defpackage.mm
    public final long Z(long j) {
        h50 h50Var = this.e;
        h50Var.getClass();
        return d3.h(h50Var, j);
    }

    @Override // defpackage.mm
    public final float d0(long j) {
        h50 h50Var = this.e;
        h50Var.getClass();
        return d3.g(h50Var, j);
    }

    @Override // defpackage.rc0
    public final qc0 e0(int i, int i2, Map map, gv gvVar) {
        return this.e.r(i, i2, map, null, gvVar);
    }

    @Override // defpackage.rc0
    public final m40 getLayoutDirection() {
        return this.e.e;
    }

    @Override // defpackage.mm
    public final long j0(float f) {
        return this.e.j0(f);
    }

    @Override // defpackage.mm
    public final float o0(float f) {
        return f / this.e.B();
    }

    @Override // defpackage.mm
    public final float y() {
        return this.e.g;
    }
}
