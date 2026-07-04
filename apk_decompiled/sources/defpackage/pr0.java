package defpackage;

import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class pr0 implements pc0, mr0 {
    public final w7 a;
    public final aa b;

    public pr0(w7 w7Var, aa aaVar) {
        this.a = w7Var;
        this.b = aaVar;
    }

    @Override // defpackage.mr0
    public final qc0 a(em0[] em0VarArr, rc0 rc0Var, int[] iArr, int i, int i2) {
        return rc0Var.e0(i, i2, fr.e, new wm(em0VarArr, this, i2, iArr));
    }

    @Override // defpackage.mr0
    public final void b(int i, rc0 rc0Var, int[] iArr, int[] iArr2) {
        this.a.d(rc0Var, i, iArr, rc0Var.getLayoutDirection(), iArr2);
    }

    @Override // defpackage.mr0
    public final long c(int i, int i2, int i3, boolean z) {
        if (!z) {
            return ti.a(i, i2, 0, i3);
        }
        return f31.y(i, i2, 0, i3);
    }

    @Override // defpackage.mr0
    public final int d(em0 em0Var) {
        return em0Var.f;
    }

    @Override // defpackage.pc0
    public final qc0 e(rc0 rc0Var, List list, long j) {
        return y20.o(this, si.j(j), si.i(j), si.h(j), si.g(j), rc0Var.S(this.a.a()), rc0Var, list, new em0[list.size()], list.size());
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof pr0) {
                pr0 pr0Var = (pr0) obj;
                if (!this.a.equals(pr0Var.a) || !this.b.equals(pr0Var.b)) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return true;
    }

    @Override // defpackage.mr0
    public final int f(em0 em0Var) {
        return em0Var.e;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.b.a) + (this.a.hashCode() * 31);
    }

    public final String toString() {
        return "RowMeasurePolicy(horizontalArrangement=" + this.a + ", verticalAlignment=" + this.b + ')';
    }
}
