package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class v2 extends z30 implements kv {
    public final /* synthetic */ int f;
    public final /* synthetic */ Object g;
    public final /* synthetic */ Object h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public v2(vu vuVar, String str, int i) {
        super(2);
        this.f = 3;
        this.g = vuVar;
        this.h = str;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        boolean z;
        boolean z2;
        int i = this.f;
        x31 x31Var = x31.a;
        Object obj3 = this.h;
        Object obj4 = this.g;
        switch (i) {
            case 0:
                bw bwVar = (bw) obj;
                int intValue = ((Number) obj2).intValue();
                y6 y6Var = (y6) obj3;
                if ((intValue & 3) != 2) {
                    z = true;
                } else {
                    z = false;
                }
                if (bwVar.O(intValue & 1, z)) {
                    String str = "luminance:\n" + (Math.round(((Number) ((y6) obj4).d()).floatValue() * 100.0f) / 100.0d);
                    r11 r11Var = new r11(se.g, d20.A(4294967296L, 16.0f), null, 16744444);
                    boolean h = bwVar.h(y6Var);
                    Object L = bwVar.L();
                    if (h || L == ph.a) {
                        L = new u2(0, y6Var);
                        bwVar.f0(L);
                    }
                    dl.b(str, null, r11Var, 0, false, 0, 0, (u2) L, bwVar, 0, 762);
                } else {
                    bwVar.R();
                }
                return x31Var;
            case 1:
                int intValue2 = ((Number) obj).intValue();
                su0 su0Var = (su0) obj2;
                t4 t4Var = (t4) obj3;
                if (!((tu0) obj4).b.b(su0Var.f)) {
                    t4Var.o(intValue2, su0Var);
                    t4Var.l.q(x31Var);
                }
                return x31Var;
            case 2:
                long j = ((ch0) obj2).a;
                ((um0) obj).getClass();
                al alVar = (al) obj4;
                alVar.f.c(alVar, new c20(((yz0) ((ym0) obj3)).B), new ch0(j));
                return x31Var;
            case 3:
                ((Number) obj2).intValue();
                o4.e((vu) obj4, (String) obj3, (bw) obj, d20.O(49));
                return x31Var;
            case 4:
                bw bwVar2 = (bw) obj;
                int intValue3 = ((Number) obj2).intValue();
                if ((intValue3 & 3) != 2) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (bwVar2.O(1 & intValue3, z2)) {
                    Boolean bool = (Boolean) ((f50) obj4).g.getValue();
                    boolean booleanValue = bool.booleanValue();
                    kv kvVar = (kv) obj3;
                    bwVar2.X(bool);
                    boolean g = bwVar2.g(booleanValue);
                    if (booleanValue) {
                        kvVar.d(bwVar2, 0);
                    } else {
                        if (bwVar2.l != 0) {
                            rh.a("No nodes can be emitted before calling deactivateToEndGroup");
                        }
                        if (!bwVar2.S) {
                            if (!g) {
                                bwVar2.Q();
                            } else {
                                qw0 qw0Var = bwVar2.G;
                                int i2 = qw0Var.g;
                                int i3 = qw0Var.h;
                                qh qhVar = bwVar2.M;
                                qhVar.getClass();
                                qhVar.d(false);
                                qhVar.b.w.G(xh0.c);
                                f31.i(bwVar2.s, i2, i3);
                                bwVar2.G.t();
                            }
                        }
                    }
                    if (bwVar2.y && bwVar2.G.i == bwVar2.z) {
                        bwVar2.z = -1;
                        bwVar2.y = false;
                    }
                    bwVar2.p(false);
                } else {
                    bwVar2.R();
                }
                return x31Var;
            default:
                uc ucVar = (uc) obj;
                hx hxVar = (hx) obj2;
                ng0 ng0Var = (ng0) obj4;
                z40 z40Var = ng0Var.s;
                if (z40Var.F()) {
                    ng0Var.L = ucVar;
                    ng0Var.K = hxVar;
                    pj0 snapshotObserver = ((b4) c50.a(z40Var)).getSnapshotObserver();
                    pq0 pq0Var = ng0.Q;
                    snapshotObserver.a.b(ng0Var, w3.C, (mg0) obj3);
                    ng0Var.O = false;
                } else {
                    ng0Var.O = true;
                }
                return x31Var;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ v2(int i, Object obj, Object obj2) {
        super(2);
        this.f = i;
        this.g = obj;
        this.h = obj2;
    }
}
