package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class h90 {
    public static final qy0 a = new do0(di.F);

    public static final void a(qr0 qr0Var, vu vuVar, cd0 cd0Var, gg ggVar, bw bwVar, int i) {
        int i2;
        boolean z;
        cd0 cd0Var2;
        int i3;
        int i4;
        int i5;
        qr0Var.getClass();
        vuVar.getClass();
        bwVar.W(-1903380770);
        if ((i & 6) == 0) {
            if (bwVar.f(qr0Var)) {
                i5 = 4;
            } else {
                i5 = 2;
            }
            i2 = i5 | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            if (bwVar.h(vuVar)) {
                i4 = 32;
            } else {
                i4 = 16;
            }
            i2 |= i4;
        }
        int i6 = i2 | 384;
        if ((i & 3072) == 0) {
            if (bwVar.h(ggVar)) {
                i3 = 2048;
            } else {
                i3 = 1024;
            }
            i6 |= i3;
        }
        if ((i6 & 1171) != 1170) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i6 & 1, z)) {
            vu vuVar2 = (vu) bwVar.j(a);
            ad adVar = new ad();
            zc0 zc0Var = zc0.a;
            cd0 b = n20.j(n20.l(zc0Var, adVar), null, new cr0(4), vuVar).b(k81.m);
            qr0Var.getClass();
            cd0 b2 = b.b(new o50(1.0f));
            boolean f = bwVar.f(vuVar2);
            Object L = bwVar.L();
            if (f || L == ph.a) {
                L = new q2(17, vuVar2);
                bwVar.f0(L);
            }
            cd0 w = k81.w(b2, (gv) L);
            int i7 = (i6 & 7168) | 432;
            ef a2 = cf.a(new x7(2.0f, false, new v7(2)), x1.s, bwVar, 54);
            long j = bwVar.T;
            int i8 = (int) ((j >>> 32) ^ j);
            ll0 l = bwVar.l();
            cd0 B = dl.B(bwVar, w);
            jh.c.getClass();
            di diVar = ih.b;
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(diVar);
            } else {
                bwVar.i0();
            }
            m20.F(ih.e, bwVar, a2);
            m20.F(ih.d, bwVar, l);
            m20.F(ih.f, bwVar, Integer.valueOf(i8));
            m20.C(ih.g, bwVar);
            m20.F(ih.c, bwVar, B);
            ggVar.c(ff.a, bwVar, Integer.valueOf(((i7 >> 6) & 112) | 6));
            bwVar.p(true);
            cd0Var2 = zc0Var;
        } else {
            bwVar.R();
            cd0Var2 = cd0Var;
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new g90(qr0Var, vuVar, cd0Var2, ggVar, i);
        }
    }
}
