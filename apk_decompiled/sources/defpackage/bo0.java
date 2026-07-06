package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class bo0 extends z30 implements mv {
    public final /* synthetic */ long f;
    public final /* synthetic */ long g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public bo0(long j, long j2) {
        super(4);
        this.f = j;
        this.g = j2;
    }

    @Override // defpackage.mv
    public final Object h(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean z;
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
        if ((intValue & 145) != 144) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(intValue & 1, z)) {
            ef a = cf.a(new x7(16.0f, true, new v7(0)), x1.s, bwVar, 54);
            long j = bwVar.T;
            int i2 = (int) (j ^ (j >>> 32));
            ll0 l = bwVar.l();
            zc0 zc0Var = zc0.a;
            cd0 B = dl.B(bwVar, zc0Var);
            jh.c.getClass();
            di diVar = ih.b;
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(diVar);
            } else {
                bwVar.i0();
            }
            kf kfVar = ih.e;
            m20.F(kfVar, bwVar, a);
            kf kfVar2 = ih.d;
            m20.F(kfVar2, bwVar, l);
            Integer valueOf = Integer.valueOf(i2);
            kf kfVar3 = ih.f;
            m20.F(kfVar3, bwVar, valueOf);
            w3 w3Var = ih.g;
            m20.C(w3Var, bwVar);
            kf kfVar4 = ih.c;
            m20.F(kfVar4, bwVar, B);
            Object L = bwVar.L();
            dt0 dt0Var = ph.a;
            if (L == dt0Var) {
                L = ba0.o;
                bwVar.f0(L);
            }
            vu vuVar = (vu) L;
            long j2 = this.f;
            boolean e = bwVar.e(j2);
            Object L2 = bwVar.L();
            if (e || L2 == dt0Var) {
                L2 = new tj(5, j2);
                bwVar.f0(L2);
            }
            cd0 r = k81.r(k81.z(f31.t(zc0Var, c40Var, vuVar, (gv) L2, null, 504), 128.0f), 1.0f);
            pc0 d = ya.d(x1.k);
            long j3 = bwVar.T;
            int i3 = (int) (j3 ^ (j3 >>> 32));
            ll0 l2 = bwVar.l();
            cd0 B2 = dl.B(bwVar, r);
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(diVar);
            } else {
                bwVar.i0();
            }
            m20.F(kfVar, bwVar, d);
            m20.F(kfVar2, bwVar, l2);
            d3.x(i3, bwVar, kfVar3, bwVar, w3Var);
            m20.F(kfVar4, bwVar, B2);
            dl.b("alpha-masked progressive blur", null, new r11(this.g, d20.A(4294967296L, 16.0f), null, 16777212), 0, false, 0, 0, null, bwVar, 6, 1018);
            bwVar.p(true);
            bwVar.p(true);
        } else {
            bwVar.R();
        }
        return x31.a;
    }
}
