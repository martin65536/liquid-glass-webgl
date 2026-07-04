package defpackage;

import androidx.compose.ui.input.pointer.PointerInputEventHandler;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class da0 extends z30 implements lv {
    public final /* synthetic */ vu f;
    public final /* synthetic */ c40 g;
    public final /* synthetic */ m9 h;
    public final /* synthetic */ he i;
    public final /* synthetic */ float j;
    public final /* synthetic */ gv k;
    public final /* synthetic */ long l;
    public final /* synthetic */ long m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public da0(vu vuVar, c40 c40Var, m9 m9Var, he heVar, float f, gv gvVar, long j, long j2) {
        super(3);
        this.f = vuVar;
        this.g = c40Var;
        this.h = m9Var;
        this.i = heVar;
        this.j = f;
        this.k = gvVar;
        this.l = j;
        this.m = j2;
    }

    @Override // defpackage.lv
    public final Object c(Object obj, Object obj2, Object obj3) {
        boolean z;
        boolean z2;
        boolean z3;
        Object obj4;
        int i;
        Object obj5;
        int i2;
        gb gbVar = (gb) obj;
        bw bwVar = (bw) obj2;
        int intValue = ((Number) obj3).intValue();
        gbVar.getClass();
        if ((intValue & 6) == 0) {
            if (bwVar.f(gbVar)) {
                i2 = 4;
            } else {
                i2 = 2;
            }
            intValue |= i2;
        }
        if ((intValue & 19) != 18) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(intValue & 1, z)) {
            int h = si.h(gbVar.b);
            if (bwVar.j(fi.n) == m40.e) {
                z2 = true;
            } else {
                z2 = false;
            }
            Object L = bwVar.L();
            dt0 dt0Var = ph.a;
            Object obj6 = L;
            if (L == dt0Var) {
                hk r = dl.r(bwVar);
                bwVar.f0(r);
                obj6 = r;
            }
            hk hkVar = (hk) obj6;
            Object L2 = bwVar.L();
            Object obj7 = L2;
            if (L2 == dt0Var) {
                ik0 B = n30.B(Boolean.FALSE);
                bwVar.f0(B);
                obj7 = B;
            }
            af0 af0Var = (af0) obj7;
            boolean f = bwVar.f(hkVar);
            Object L3 = bwVar.L();
            he heVar = this.i;
            vu vuVar = this.f;
            if (f || L3 == dt0Var) {
                float floatValue = ((Number) vuVar.a()).floatValue();
                kf kfVar = kf.r;
                gv gvVar = this.k;
                al alVar = new al(hkVar, floatValue, heVar, this.j, 1.5f, kfVar, new o6(6, gvVar, af0Var), new ca0(heVar, h, gvVar, z2, af0Var));
                bwVar.f0(alVar);
                L3 = alVar;
            }
            al alVar2 = (al) L3;
            boolean f2 = bwVar.f(vuVar) | bwVar.h(alVar2);
            Object L4 = bwVar.L();
            Object obj8 = L4;
            if (f2 || L4 == dt0Var) {
                d dVar = new d(vuVar, alVar2, null, 10);
                bwVar.f0(dVar);
                obj8 = dVar;
            }
            dl.i((kv) obj8, bwVar, alVar2);
            zc0 zc0Var = zc0.a;
            c40 c40Var = this.g;
            cd0 H = f31.H(zc0Var, c40Var);
            pc0 d = ya.d(x1.g);
            long j = bwVar.T;
            int i3 = (int) (j ^ (j >>> 32));
            ll0 l = bwVar.l();
            cd0 B2 = dl.B(bwVar, H);
            jh.c.getClass();
            di diVar = ih.b;
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(diVar);
            } else {
                bwVar.i0();
            }
            m20.F(ih.e, bwVar, d);
            m20.F(ih.d, bwVar, l);
            m20.F(ih.f, bwVar, Integer.valueOf(i3));
            m20.C(ih.g, bwVar);
            m20.F(ih.c, bwVar, B2);
            cd0 l2 = n20.l(zc0Var, new ad());
            uy uyVar = o20.o;
            cd0 k = k81.k(l2, this.l, uyVar);
            boolean f3 = bwVar.f(heVar) | bwVar.d(h) | bwVar.g(z2) | bwVar.h(alVar2);
            gv gvVar2 = this.k;
            boolean f4 = f3 | bwVar.f(gvVar2);
            Object L5 = bwVar.L();
            if (f4 || L5 == dt0Var) {
                boolean z4 = z2;
                y90 y90Var = new y90(heVar, h, z4, alVar2, gvVar2);
                z2 = z4;
                bwVar.f0(y90Var);
                L5 = y90Var;
            }
            ya.a(k81.r(k81.z(uz0.a(k, hkVar, (PointerInputEventHandler) L5), 6.0f), 1.0f), bwVar, 0);
            cd0 z5 = k81.z(k81.k(n20.l(zc0Var, new ad()), this.m, uyVar), 6.0f);
            boolean h2 = bwVar.h(alVar2);
            Object L6 = bwVar.L();
            if (!h2 && L6 != dt0Var) {
                z3 = true;
                obj4 = L6;
            } else {
                z3 = true;
                qw qwVar = new qw(true ? 1 : 0, alVar2);
                bwVar.f0(qwVar);
                obj4 = qwVar;
            }
            ya.a(o4.M(z5, (lv) obj4), bwVar, 0);
            bwVar.p(z3);
            boolean d2 = bwVar.d(h) | bwVar.h(alVar2) | bwVar.g(z2);
            Object L7 = bwVar.L();
            Object obj9 = L7;
            if (d2 || L7 == dt0Var) {
                z90 z90Var = new z90(h, alVar2, z2);
                bwVar.f0(z90Var);
                obj9 = z90Var;
            }
            cd0 b = k81.w(zc0Var, (gv) obj9).b(alVar2.s);
            boolean h3 = bwVar.h(alVar2);
            Object L8 = bwVar.L();
            if (!h3 && L8 != dt0Var) {
                i = 0;
                obj5 = L8;
            } else {
                i = 0;
                aa0 aa0Var = new aa0(alVar2, i);
                bwVar.f0(aa0Var);
                obj5 = aa0Var;
            }
            gf P = dl.P(this.h, o4.W(c40Var, (kv) obj5, bwVar), bwVar, i);
            Object L9 = bwVar.L();
            Object obj10 = L9;
            if (L9 == dt0Var) {
                ba0 ba0Var = ba0.g;
                bwVar.f0(ba0Var);
                obj10 = ba0Var;
            }
            vu vuVar2 = (vu) obj10;
            boolean h4 = bwVar.h(alVar2);
            Object L10 = bwVar.L();
            Object obj11 = L10;
            if (h4 || L10 == dt0Var) {
                uk ukVar = new uk(alVar2, 9);
                bwVar.f0(ukVar);
                obj11 = ukVar;
            }
            gv gvVar3 = (gv) obj11;
            boolean h5 = bwVar.h(alVar2);
            Object L11 = bwVar.L();
            int i4 = 7;
            Object obj12 = L11;
            if (h5 || L11 == dt0Var) {
                vk vkVar = new vk(alVar2, i4);
                bwVar.f0(vkVar);
                obj12 = vkVar;
            }
            vu vuVar3 = (vu) obj12;
            Object L12 = bwVar.L();
            Object obj13 = L12;
            if (L12 == dt0Var) {
                ba0 ba0Var2 = ba0.h;
                bwVar.f0(ba0Var2);
                obj13 = ba0Var2;
            }
            vu vuVar4 = (vu) obj13;
            boolean h6 = bwVar.h(alVar2);
            Object L13 = bwVar.L();
            int i5 = 8;
            Object obj14 = L13;
            if (h6 || L13 == dt0Var) {
                vk vkVar2 = new vk(alVar2, i5);
                bwVar.f0(vkVar2);
                obj14 = vkVar2;
            }
            vu vuVar5 = (vu) obj14;
            boolean h7 = bwVar.h(alVar2);
            Object L14 = bwVar.L();
            Object obj15 = L14;
            if (h7 || L14 == dt0Var) {
                uk ukVar2 = new uk(alVar2, i4);
                bwVar.f0(ukVar2);
                obj15 = ukVar2;
            }
            gv gvVar4 = (gv) obj15;
            boolean h8 = bwVar.h(alVar2);
            Object L15 = bwVar.L();
            Object obj16 = L15;
            if (h8 || L15 == dt0Var) {
                uk ukVar3 = new uk(alVar2, i5);
                bwVar.f0(ukVar3);
                obj16 = ukVar3;
            }
            ya.a(k81.K(f31.s(b, P, vuVar2, gvVar3, vuVar3, vuVar4, vuVar5, gvVar4, null, null, (gv) obj16, 2944), 40.0f, 24.0f), bwVar, 0);
        } else {
            bwVar.R();
        }
        return x31.a;
    }
}
