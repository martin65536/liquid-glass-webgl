package defpackage;

import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class p70 implements es0, cs0 {
    public final fs0 e;
    public final ds0 f;
    public final we0 g;

    public p70(es0 es0Var, Map map, ds0 ds0Var) {
        l lVar = new l(8, es0Var);
        qy0 qy0Var = gs0.a;
        this.e = new fs0(map, lVar);
        this.f = ds0Var;
        we0 we0Var = at0.a;
        this.g = new we0();
    }

    @Override // defpackage.es0
    public final r7 a(String str, vu vuVar) {
        return this.e.a(str, vuVar);
    }

    @Override // defpackage.cs0
    public final void b(Object obj, gg ggVar, bw bwVar, int i) {
        int i2;
        boolean z;
        int i3;
        int i4;
        int i5;
        bwVar.W(-858296452);
        if ((i & 6) == 0) {
            if (bwVar.h(obj)) {
                i5 = 4;
            } else {
                i5 = 2;
            }
            i2 = i5 | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            if (bwVar.h(ggVar)) {
                i4 = 32;
            } else {
                i4 = 16;
            }
            i2 |= i4;
        }
        if ((i & 384) == 0) {
            if (bwVar.h(this)) {
                i3 = 256;
            } else {
                i3 = 128;
            }
            i2 |= i3;
        }
        if ((i2 & 147) != 146) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i2 & 1, z)) {
            this.f.b(obj, ggVar, bwVar, i2 & 126);
            boolean h = bwVar.h(this) | bwVar.h(obj);
            Object L = bwVar.L();
            if (h || L == ph.a) {
                L = new c(12, this, obj);
                bwVar.f0(L);
            }
            dl.f(obj, (gv) L, bwVar);
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new fb(this, obj, ggVar, i, 3);
        }
    }

    @Override // defpackage.es0
    public final boolean c(Object obj) {
        return this.e.c(obj);
    }

    @Override // defpackage.es0
    public final Map d() {
        we0 we0Var = this.g;
        Object[] objArr = we0Var.b;
        long[] jArr = we0Var.a;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j) < 128) {
                            Object obj = objArr[(i << 3) + i3];
                            ds0 ds0Var = this.f;
                            if (ds0Var.f.k(obj) == null) {
                                ds0Var.e.remove(obj);
                            }
                        }
                        j >>= 8;
                    }
                    if (i2 != 8) {
                        break;
                    }
                }
                if (i == length) {
                    break;
                }
                i++;
            }
        }
        return this.e.d();
    }

    @Override // defpackage.es0
    public final Object e(String str) {
        return this.e.e(str);
    }
}
