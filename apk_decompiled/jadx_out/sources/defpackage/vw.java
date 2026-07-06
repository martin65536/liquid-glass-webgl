package defpackage;

import androidx.compose.ui.input.pointer.PointerInputEventHandler;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class vw extends z30 implements mv {
    public final /* synthetic */ y6 f;
    public final /* synthetic */ y6 g;
    public final /* synthetic */ y6 h;
    public final /* synthetic */ hk i;
    public final /* synthetic */ ek0 j;
    public final /* synthetic */ ek0 k;
    public final /* synthetic */ ek0 l;
    public final /* synthetic */ ek0 m;
    public final /* synthetic */ ek0 n;
    public final /* synthetic */ af0 o;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public vw(y6 y6Var, y6 y6Var2, y6 y6Var3, hk hkVar, ek0 ek0Var, ek0 ek0Var2, ek0 ek0Var3, ek0 ek0Var4, ek0 ek0Var5, af0 af0Var) {
        super(4);
        this.f = y6Var;
        this.g = y6Var2;
        this.h = y6Var3;
        this.i = hkVar;
        this.j = ek0Var;
        this.k = ek0Var2;
        this.l = ek0Var3;
        this.m = ek0Var4;
        this.n = ek0Var5;
        this.o = af0Var;
    }

    @Override // defpackage.mv
    public final Object h(Object obj, Object obj2, Object obj3, Object obj4) {
        int i;
        boolean z;
        hk hkVar;
        int i2;
        int i3;
        cb cbVar = (cb) obj;
        c40 c40Var = (c40) obj2;
        bw bwVar = (bw) obj3;
        int intValue = ((Number) obj4).intValue();
        cbVar.getClass();
        c40Var.getClass();
        if ((intValue & 6) == 0) {
            if (bwVar.f(cbVar)) {
                i3 = 4;
            } else {
                i3 = 2;
            }
            i = i3 | intValue;
        } else {
            i = intValue;
        }
        if ((intValue & 48) == 0) {
            if (bwVar.f(c40Var)) {
                i2 = 32;
            } else {
                i2 = 16;
            }
            i |= i2;
        }
        if ((i & 147) != 146) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i & 1, z)) {
            cd0 K = jc0.K(dl.F(zc0.a, 48.0f, 13), jc0.q);
            Object L = bwVar.L();
            dt0 dt0Var = ph.a;
            if (L == dt0Var) {
                L = new mw(this.j, 0);
                bwVar.f0(L);
            }
            vu vuVar = (vu) L;
            Object L2 = bwVar.L();
            if (L2 == dt0Var) {
                L2 = new nw(this.k, this.l, this.m, this.n);
                bwVar.f0(L2);
            }
            gv gvVar = (gv) L2;
            Object L3 = bwVar.L();
            if (L3 == dt0Var) {
                L3 = di.z;
                bwVar.f0(L3);
            }
            vu vuVar2 = (vu) L3;
            y6 y6Var = this.f;
            boolean h = bwVar.h(y6Var);
            y6 y6Var2 = this.g;
            boolean h2 = h | bwVar.h(y6Var2);
            y6 y6Var3 = this.h;
            boolean h3 = h2 | bwVar.h(y6Var3);
            Object L4 = bwVar.L();
            if (h3 || L4 == dt0Var) {
                L4 = new p2(y6Var, y6Var2, y6Var3, 1);
                bwVar.f0(L4);
            }
            cd0 s = f31.s(K, c40Var, vuVar, gvVar, vuVar2, null, null, (gv) L4, null, null, null, 4016);
            boolean h4 = bwVar.h(y6Var) | bwVar.h(y6Var2) | bwVar.h(y6Var3);
            hk hkVar2 = this.i;
            boolean h5 = h4 | bwVar.h(hkVar2);
            Object L5 = bwVar.L();
            if (!h5 && L5 != dt0Var) {
                hkVar = hkVar2;
            } else {
                L5 = new t2(y6Var, y6Var2, y6Var3, hkVar2, 1);
                hkVar = hkVar2;
                bwVar.f0(L5);
            }
            ya.a(cbVar.a(k81.J(uz0.a(s, hkVar, (PointerInputEventHandler) L5), 256.0f), x1.h), bwVar, 0);
            k81.b(jc0.C(-2107932395, new pw(cbVar, c40Var, this.o, this.j, this.k, this.l, this.m, this.n), bwVar), bwVar, 6);
            k81.b(jc0.C(-1862897908, new uw(c40Var, cbVar, this.i, this.f, this.g, this.h, this.o, this.j, this.k, this.l, this.m, this.n), bwVar), bwVar, 6);
        } else {
            bwVar.R();
        }
        return x31.a;
    }
}
