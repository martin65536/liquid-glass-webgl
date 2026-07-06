package defpackage;

import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ds0 implements cs0 {
    public static final c4 i = new c4(21, new w4(11, 0), new pb(17));
    public final Map e;
    public final ve0 f;
    public es0 g;
    public final l h;

    public ds0(Map map) {
        this.e = map;
        long[] jArr = zs0.a;
        this.f = new ve0();
        this.h = new l(13, this);
    }

    @Override // defpackage.cs0
    public final void b(Object obj, gg ggVar, bw bwVar, int i2) {
        int i3;
        boolean z;
        int i4;
        int i5;
        int i6;
        bwVar.W(533563200);
        int i7 = 4;
        if ((i2 & 6) == 0) {
            if (bwVar.h(obj)) {
                i6 = 4;
            } else {
                i6 = 2;
            }
            i3 = i6 | i2;
        } else {
            i3 = i2;
        }
        if ((i2 & 48) == 0) {
            if (bwVar.h(ggVar)) {
                i5 = 32;
            } else {
                i5 = 16;
            }
            i3 |= i5;
        }
        if ((i2 & 384) == 0) {
            if (bwVar.h(this)) {
                i4 = 256;
            } else {
                i4 = 128;
            }
            i3 |= i4;
        }
        if ((i3 & 147) != 146) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i3 & 1, z)) {
            bwVar.X(obj);
            Object L = bwVar.L();
            dt0 dt0Var = ph.a;
            if (L == dt0Var) {
                l lVar = this.h;
                if (((Boolean) lVar.e(obj)).booleanValue()) {
                    Map map = (Map) this.e.get(obj);
                    qy0 qy0Var = gs0.a;
                    hs0 hs0Var = new hs0(new fs0(map, lVar));
                    bwVar.f0(hs0Var);
                    L = hs0Var;
                } else {
                    v7.i("Type of the key ", obj, " is not supported. On Android you can only use types which can be stored inside the Bundle.");
                    return;
                }
            }
            hs0 hs0Var2 = (hs0) L;
            o20.b(new eo0[]{gs0.a.a(hs0Var2), sa0.a.a(hs0Var2)}, ggVar, bwVar, (i3 & 112) | 8);
            boolean h = bwVar.h(this) | bwVar.h(obj) | bwVar.h(hs0Var2);
            Object L2 = bwVar.L();
            if (h || L2 == dt0Var) {
                L2 = new zi(this, obj, hs0Var2, i7);
                bwVar.f0(L2);
            }
            dl.f(x31.a, (gv) L2, bwVar);
            if (bwVar.y && bwVar.G.i == bwVar.z) {
                bwVar.z = -1;
                bwVar.y = false;
            }
            bwVar.p(false);
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new fb(this, obj, ggVar, i2, 5);
        }
    }
}
