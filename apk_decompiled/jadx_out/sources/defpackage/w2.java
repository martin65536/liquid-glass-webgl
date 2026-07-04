package defpackage;

import androidx.compose.ui.input.pointer.PointerInputEventHandler;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class w2 extends z30 implements mv {
    public final /* synthetic */ y6 f;
    public final /* synthetic */ y6 g;
    public final /* synthetic */ y6 h;
    public final /* synthetic */ y6 i;
    public final /* synthetic */ hx j;
    public final /* synthetic */ hk k;
    public final /* synthetic */ y6 l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public w2(y6 y6Var, y6 y6Var2, y6 y6Var3, y6 y6Var4, hx hxVar, hk hkVar, y6 y6Var5) {
        super(4);
        this.f = y6Var;
        this.g = y6Var2;
        this.h = y6Var3;
        this.i = y6Var4;
        this.j = hxVar;
        this.k = hkVar;
        this.l = y6Var5;
    }

    @Override // defpackage.mv
    public final Object h(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean z;
        hk hkVar;
        int i;
        c40 c40Var = (c40) obj2;
        bw bwVar = (bw) obj3;
        int intValue = ((Number) obj4).intValue();
        ((cb) obj).getClass();
        c40Var.getClass();
        if ((intValue & 48) == 0) {
            if (bwVar.f(c40Var)) {
                i = 32;
            } else {
                i = 16;
            }
            intValue |= i;
        }
        int i2 = 0;
        int i3 = 1;
        if ((intValue & 145) != 144) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(intValue & 1, z)) {
            Object L = bwVar.L();
            Object obj5 = ph.a;
            if (L == obj5) {
                L = n2.g;
                bwVar.f0(L);
            }
            vu vuVar = (vu) L;
            y6 y6Var = this.f;
            boolean h = bwVar.h(y6Var);
            Object L2 = bwVar.L();
            if (h || L2 == obj5) {
                L2 = new o2(y6Var, i2);
                bwVar.f0(L2);
            }
            gv gvVar = (gv) L2;
            Object L3 = bwVar.L();
            if (L3 == obj5) {
                L3 = n2.h;
                bwVar.f0(L3);
            }
            vu vuVar2 = (vu) L3;
            y6 y6Var2 = this.g;
            boolean h2 = bwVar.h(y6Var2);
            y6 y6Var3 = this.h;
            boolean h3 = h2 | bwVar.h(y6Var3);
            y6 y6Var4 = this.i;
            boolean h4 = h3 | bwVar.h(y6Var4);
            Object L4 = bwVar.L();
            if (h4 || L4 == obj5) {
                L4 = new p2(y6Var2, y6Var3, y6Var4, 0);
                bwVar.f0(L4);
            }
            gv gvVar2 = (gv) L4;
            Object obj6 = this.j;
            boolean h5 = bwVar.h(obj6);
            Object L5 = bwVar.L();
            if (h5 || L5 == obj5) {
                L5 = new o(i3, obj6);
                bwVar.f0(L5);
            }
            cd0 s = f31.s(zc0.a, c40Var, vuVar, gvVar, vuVar2, null, null, gvVar2, null, (kv) L5, null, 3504);
            boolean h6 = bwVar.h(y6Var2) | bwVar.h(y6Var3) | bwVar.h(y6Var4);
            hk hkVar2 = this.k;
            boolean h7 = h6 | bwVar.h(hkVar2);
            Object L6 = bwVar.L();
            if (!h7 && L6 != obj5) {
                hkVar = hkVar2;
            } else {
                L6 = new t2(y6Var2, y6Var3, y6Var4, hkVar2, 0);
                hkVar = hkVar2;
                bwVar.f0(L6);
            }
            cd0 J = k81.J(uz0.a(s, hkVar, (PointerInputEventHandler) L6), 160.0f);
            pc0 d = ya.d(x1.k);
            long j = bwVar.T;
            int i4 = (int) (j ^ (j >>> 32));
            ll0 l = bwVar.l();
            cd0 B = dl.B(bwVar, J);
            jh.c.getClass();
            vu vuVar3 = ih.b;
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(vuVar3);
            } else {
                bwVar.i0();
            }
            m20.F(ih.e, bwVar, d);
            m20.F(ih.d, bwVar, l);
            m20.F(ih.f, bwVar, Integer.valueOf(i4));
            m20.C(ih.g, bwVar);
            m20.F(ih.c, bwVar, B);
            k81.b(jc0.C(-1637977171, new v2(0, y6Var, this.l), bwVar), bwVar, 6);
            bwVar.p(true);
        } else {
            bwVar.R();
        }
        return x31.a;
    }
}
