package defpackage;

import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ef implements pc0, mr0 {
    public final y7 a;
    public final z9 b;

    public ef(y7 y7Var, z9 z9Var) {
        this.a = y7Var;
        this.b = z9Var;
    }

    @Override // defpackage.mr0
    public final qc0 a(final em0[] em0VarArr, final rc0 rc0Var, final int[] iArr, int i, final int i2) {
        return rc0Var.e0(i2, i, fr.e, new gv() { // from class: df
            @Override // defpackage.gv
            public final Object e(Object obj) {
                dm0 dm0Var = (dm0) obj;
                em0[] em0VarArr2 = em0VarArr;
                int length = em0VarArr2.length;
                int i3 = 0;
                int i4 = 0;
                while (i3 < length) {
                    em0 em0Var = em0VarArr2[i3];
                    em0Var.getClass();
                    em0Var.A();
                    dm0.z(dm0Var, em0Var, this.b.a(em0Var.e, i2, rc0Var.getLayoutDirection()), iArr[i4]);
                    i3++;
                    i4++;
                }
                return x31.a;
            }
        });
    }

    @Override // defpackage.mr0
    public final void b(int i, rc0 rc0Var, int[] iArr, int[] iArr2) {
        this.a.e(i, rc0Var, iArr, iArr2);
    }

    @Override // defpackage.mr0
    public final long c(int i, int i2, int i3, boolean z) {
        if (!z) {
            return ti.a(0, i3, i, i2);
        }
        return f31.x(0, i3, i, i2);
    }

    @Override // defpackage.mr0
    public final int d(em0 em0Var) {
        return em0Var.e;
    }

    @Override // defpackage.pc0
    public final qc0 e(rc0 rc0Var, List list, long j) {
        return y20.o(this, si.i(j), si.j(j), si.g(j), si.h(j), rc0Var.S(this.a.a()), rc0Var, list, new em0[list.size()], list.size());
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof ef) {
                ef efVar = (ef) obj;
                if (!this.a.equals(efVar.a) || !this.b.equals(efVar.b)) {
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
        return em0Var.f;
    }

    public final int hashCode() {
        return Float.floatToIntBits(this.b.a) + (this.a.hashCode() * 31);
    }

    public final String toString() {
        return "ColumnMeasurePolicy(verticalArrangement=" + this.a + ", horizontalAlignment=" + this.b + ')';
    }
}
