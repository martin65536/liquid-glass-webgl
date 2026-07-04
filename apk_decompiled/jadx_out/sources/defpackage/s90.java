package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class s90 extends z30 implements lv {
    public final /* synthetic */ vu f;
    public final /* synthetic */ gv g;
    public final /* synthetic */ m9 h;
    public final /* synthetic */ long i;
    public final /* synthetic */ gg j;
    public final /* synthetic */ c40 k;
    public final /* synthetic */ boolean l;
    public final /* synthetic */ int m;
    public final /* synthetic */ long n;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public s90(vu vuVar, gv gvVar, m9 m9Var, long j, gg ggVar, c40 c40Var, boolean z, int i, long j2) {
        super(3);
        this.f = vuVar;
        this.g = gvVar;
        this.h = m9Var;
        this.i = j;
        this.j = ggVar;
        this.k = c40Var;
        this.l = z;
        this.m = i;
        this.n = j2;
    }

    public static final float f(hy0 hy0Var) {
        return ((Number) hy0Var.getValue()).floatValue();
    }

    @Override // defpackage.lv
    public final Object c(Object obj, Object obj2, Object obj3) {
        boolean z;
        boolean z2;
        Object obj4;
        int i;
        gb gbVar = (gb) obj;
        bw bwVar = (bw) obj2;
        int intValue = ((Number) obj3).intValue();
        gbVar.getClass();
        if ((intValue & 6) == 0) {
            if (bwVar.f(gbVar)) {
                i = 4;
            } else {
                i = 2;
            }
            intValue |= i;
        }
        int i2 = 0;
        if ((intValue & 19) != 18) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(intValue & 1, z)) {
            mm mmVar = (mm) bwVar.j(fi.h);
            float h = si.h(gbVar.b) - mmVar.G(8.0f);
            float f = this.m;
            float f2 = h / f;
            Object L = bwVar.L();
            Object obj5 = ph.a;
            if (L == obj5) {
                L = dl.a(0.0f, 0.01f);
                bwVar.f0(L);
            }
            y6 y6Var = (y6) L;
            boolean f3 = bwVar.f(mmVar);
            Object L2 = bwVar.L();
            if (f3 || L2 == obj5) {
                L2 = n30.r(new r90(y6Var, gbVar, mmVar, i2));
                bwVar.f0(L2);
            }
            hy0 hy0Var = (hy0) L2;
            if (bwVar.j(fi.n) == m40.e) {
                z2 = true;
            } else {
                z2 = false;
            }
            Object L3 = bwVar.L();
            if (L3 == obj5) {
                L3 = dl.r(bwVar);
                bwVar.f0(L3);
            }
            hk hkVar = (hk) L3;
            vu vuVar = this.f;
            boolean f4 = bwVar.f(vuVar);
            Object L4 = bwVar.L();
            if (f4 || L4 == obj5) {
                L4 = new fk0(((Number) vuVar.a()).intValue());
                bwVar.f0(L4);
            }
            fk0 fk0Var = (fk0) L4;
            boolean f5 = bwVar.f(hkVar);
            Object L5 = bwVar.L();
            if (!f5 && L5 != obj5) {
                obj4 = obj5;
            } else {
                float intValue2 = ((Number) vuVar.a()).intValue();
                int i3 = this.m;
                obj4 = obj5;
                L5 = new al(hkVar, intValue2, new he(i3 - 1), 0.001f, 1.3928572f, kf.q, new n90(i3, hkVar, fk0Var, y6Var), new p90(f2, z2, i3, hkVar, y6Var));
                bwVar.f0(L5);
            }
            al alVar = (al) L5;
            boolean f6 = bwVar.f(vuVar) | bwVar.f(fk0Var);
            Object L6 = bwVar.L();
            ij ijVar = null;
            if (f6 || L6 == obj4) {
                L6 = new d(vuVar, fk0Var, ijVar, 8);
                bwVar.f0(L6);
            }
            dl.i((kv) L6, bwVar, vuVar);
            boolean f7 = bwVar.f(fk0Var) | bwVar.h(alVar);
            Object obj6 = this.g;
            boolean f8 = f7 | bwVar.f(obj6);
            Object L7 = bwVar.L();
            if (f8 || L7 == obj4) {
                L7 = new f(fk0Var, alVar, obj6, ijVar, 10);
                bwVar.f0(L7);
            }
            dl.i((kv) L7, bwVar, alVar);
            boolean f9 = bwVar.f(hkVar);
            Object L8 = bwVar.L();
            if (f9 || L8 == obj4) {
                L8 = new k20(hkVar, new q90(z2, alVar, f2, hy0Var));
                bwVar.f0(L8);
            }
            k20 k20Var = (k20) L8;
            boolean f10 = bwVar.f(hy0Var);
            Object L9 = bwVar.L();
            if (f10 || L9 == obj4) {
                L9 = new l90(hy0Var, 0);
                bwVar.f0(L9);
            }
            zc0 zc0Var = zc0.a;
            cd0 w = k81.w(zc0Var, (gv) L9);
            Object L10 = bwVar.L();
            if (L10 == obj4) {
                L10 = di.H;
                bwVar.f0(L10);
            }
            vu vuVar2 = (vu) L10;
            Object L11 = bwVar.L();
            if (L11 == obj4) {
                L11 = w3.w;
                bwVar.f0(L11);
            }
            gv gvVar = (gv) L11;
            boolean h2 = bwVar.h(alVar);
            Object L12 = bwVar.L();
            int i4 = 5;
            if (h2 || L12 == obj4) {
                L12 = new uk(alVar, i4);
                bwVar.f0(L12);
            }
            gv gvVar2 = (gv) L12;
            long j = this.i;
            boolean e = bwVar.e(j);
            Object L13 = bwVar.L();
            int i5 = 3;
            if (e || L13 == obj4) {
                L13 = new tj(i5, j);
                bwVar.f0(L13);
            }
            cd0 C = dl.C(k81.r(k81.z(f31.s(w, this.h, vuVar2, gvVar, null, null, null, gvVar2, null, null, (gv) L13, 3000).b(k20Var.i), 64.0f), 1.0f), 4.0f);
            pr0 a = or0.a(o20.a, bwVar, 48);
            long j2 = bwVar.T;
            int i6 = (int) (j2 ^ (j2 >>> 32));
            ll0 l = bwVar.l();
            cd0 B = dl.B(bwVar, C);
            jh.c.getClass();
            vu vuVar3 = ih.b;
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(vuVar3);
            } else {
                bwVar.i0();
            }
            m20.F(ih.e, bwVar, a);
            m20.F(ih.d, bwVar, l);
            m20.F(ih.f, bwVar, Integer.valueOf(i6));
            m20.C(ih.g, bwVar);
            m20.F(ih.c, bwVar, B);
            Object obj7 = qr0.a;
            gg ggVar = this.j;
            ggVar.c(obj7, bwVar, 6);
            bwVar.p(true);
            qy0 qy0Var = h90.a;
            boolean h3 = bwVar.h(alVar);
            Object L14 = bwVar.L();
            if (h3 || L14 == obj4) {
                L14 = new vk(alVar, 5);
                bwVar.f0(L14);
            }
            boolean z3 = z2;
            o20.a(qy0Var.a((vu) L14), jc0.C(1881664298, new m90(this.k, hy0Var, this.h, alVar, this.i, k20Var, this.n, ggVar), bwVar), bwVar, 56);
            cd0 D = dl.D(zc0Var, 4.0f);
            boolean g = bwVar.g(z3) | bwVar.h(alVar) | bwVar.c(f2) | bwVar.f(hy0Var);
            Object L15 = bwVar.L();
            if (g || L15 == obj4) {
                L15 = new j90(z3, alVar, f2, hy0Var);
                bwVar.f0(L15);
            }
            cd0 b = k81.w(D, (gv) L15).b(k20Var.j).b(alVar.s);
            gf P = dl.P(this.h, this.k, bwVar, 0);
            Object L16 = bwVar.L();
            if (L16 == obj4) {
                L16 = di.G;
                bwVar.f0(L16);
            }
            vu vuVar4 = (vu) L16;
            boolean h4 = bwVar.h(alVar);
            Object L17 = bwVar.L();
            if (h4 || L17 == obj4) {
                L17 = new uk(alVar, 3);
                bwVar.f0(L17);
            }
            gv gvVar3 = (gv) L17;
            boolean h5 = bwVar.h(alVar);
            Object L18 = bwVar.L();
            if (h5 || L18 == obj4) {
                L18 = new vk(alVar, 2);
                bwVar.f0(L18);
            }
            vu vuVar5 = (vu) L18;
            boolean h6 = bwVar.h(alVar);
            Object L19 = bwVar.L();
            if (h6 || L19 == obj4) {
                L19 = new vk(alVar, 3);
                bwVar.f0(L19);
            }
            vu vuVar6 = (vu) L19;
            boolean h7 = bwVar.h(alVar);
            Object L20 = bwVar.L();
            if (h7 || L20 == obj4) {
                L20 = new vk(alVar, 4);
                bwVar.f0(L20);
            }
            vu vuVar7 = (vu) L20;
            boolean h8 = bwVar.h(alVar);
            Object L21 = bwVar.L();
            if (h8 || L21 == obj4) {
                L21 = new uk(alVar, 4);
                bwVar.f0(L21);
            }
            gv gvVar4 = (gv) L21;
            boolean h9 = bwVar.h(alVar);
            boolean z4 = this.l;
            boolean g2 = h9 | bwVar.g(z4);
            Object L22 = bwVar.L();
            if (g2 || L22 == obj4) {
                L22 = new k90(alVar, z4);
                bwVar.f0(L22);
            }
            ya.a(k81.r(k81.z(f31.s(b, P, vuVar4, gvVar3, vuVar5, vuVar6, vuVar7, gvVar4, null, null, (gv) L22, 2944), 56.0f), 1.0f / f), bwVar, 0);
        } else {
            bwVar.R();
        }
        return x31.a;
    }
}
