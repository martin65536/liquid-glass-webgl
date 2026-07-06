package defpackage;

import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class bb implements pc0 {
    public final ba a;
    public final boolean b;

    public bb(ba baVar, boolean z) {
        this.a = baVar;
        this.b = z;
    }

    /* JADX WARN: Type inference failed for: r4v3, types: [cp0, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v2, types: [cp0, java.lang.Object] */
    @Override // defpackage.pc0
    public final qc0 e(final rc0 rc0Var, final List list, long j) {
        long j2;
        boolean isEmpty = list.isEmpty();
        fr frVar = fr.e;
        if (isEmpty) {
            return rc0Var.e0(si.j(j), si.i(j), frVar, new pb(6));
        }
        if (this.b) {
            j2 = j;
        } else {
            j2 = j & (-8589934589L);
        }
        if (list.size() == 1) {
            final kc0 kc0Var = (kc0) list.get(0);
            kc0Var.A();
            final em0 v = kc0Var.v(j2);
            final int max = Math.max(si.j(j), v.e);
            final int max2 = Math.max(si.i(j), v.f);
            return rc0Var.e0(max, max2, frVar, new gv() { // from class: za
                @Override // defpackage.gv
                public final Object e(Object obj) {
                    ya.b((dm0) obj, em0.this, kc0Var, rc0Var.getLayoutDirection(), max, max2, this.a);
                    return x31.a;
                }
            });
        }
        final em0[] em0VarArr = new em0[list.size()];
        final ?? obj = new Object();
        obj.e = si.j(j);
        final ?? obj2 = new Object();
        obj2.e = si.i(j);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            kc0 kc0Var2 = (kc0) list.get(i);
            kc0Var2.A();
            em0 v2 = kc0Var2.v(j2);
            em0VarArr[i] = v2;
            obj.e = Math.max(obj.e, v2.e);
            obj2.e = Math.max(obj2.e, v2.f);
        }
        return rc0Var.e0(obj.e, obj2.e, frVar, new gv() { // from class: ab
            @Override // defpackage.gv
            public final Object e(Object obj3) {
                dm0 dm0Var = (dm0) obj3;
                em0[] em0VarArr2 = em0VarArr;
                int length = em0VarArr2.length;
                int i2 = 0;
                int i3 = 0;
                while (i3 < length) {
                    int i4 = i2;
                    em0 em0Var = em0VarArr2[i3];
                    em0Var.getClass();
                    ya.b(dm0Var, em0Var, (kc0) list.get(i4), rc0Var.getLayoutDirection(), obj.e, obj2.e, this.a);
                    i3++;
                    i2 = i4 + 1;
                }
                return x31.a;
            }
        });
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof bb) {
                bb bbVar = (bb) obj;
                if (!this.a.equals(bbVar.a) || this.b != bbVar.b) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int hashCode = this.a.hashCode() * 31;
        if (this.b) {
            i = 1231;
        } else {
            i = 1237;
        }
        return hashCode + i;
    }

    public final String toString() {
        return "BoxMeasurePolicy(alignment=" + this.a + ", propagateMinConstraints=" + this.b + ')';
    }
}
