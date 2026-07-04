package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class mp implements gv {
    public final /* synthetic */ int e;
    public final /* synthetic */ op f;

    public /* synthetic */ mp(op opVar, int i) {
        this.e = i;
        this.f = opVar;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        long j;
        int i = this.e;
        x31 x31Var = x31.a;
        op opVar = this.f;
        switch (i) {
            case 0:
                lx lxVar = (lx) obj;
                lxVar.getClass();
                lxVar.m(true);
                lxVar.F(opVar.t.g);
                lxVar.b0(1);
                return x31Var;
            case 1:
                up upVar = (up) obj;
                upVar.getClass();
                uc q = upVar.J().q();
                float g = opVar.D.g();
                if (g != 0.0f) {
                    q.d(g, g);
                }
                opVar.x.d(upVar, new mp(opVar, 4));
                if (g != 0.0f) {
                    float f = -g;
                    q.d(f, f);
                }
                return x31Var;
            case 2:
                up upVar2 = (up) obj;
                upVar2.getClass();
                hx hxVar = opVar.A;
                if (hxVar != null) {
                    float g2 = opVar.D.g();
                    int i2 = (int) g2;
                    long intBitsToFloat = ((((int) Float.intBitsToFloat((int) (upVar2.j() & 4294967295L))) + r7) & 4294967295L) | ((((int) Float.intBitsToFloat((int) (upVar2.j() >> 32))) + (i2 * 2)) << 32);
                    mp mpVar = opVar.E;
                    mpVar.getClass();
                    upVar2.M(hxVar, intBitsToFloat, new c(11, k81.E(opVar).A, mpVar));
                    if (g2 == 0.0f) {
                        j = 0;
                    } else {
                        long j2 = -i2;
                        j = (j2 & 4294967295L) | (j2 << 32);
                    }
                    if (!v10.a(hxVar.t, j)) {
                        hxVar.t = j;
                        hxVar.a.J((int) (j >> 32), (int) (j & 4294967295L), hxVar.u);
                    }
                    n20.r(upVar2, hxVar);
                }
                return x31Var;
            case 3:
                up upVar3 = (up) obj;
                upVar3.getClass();
                opVar.F.e(upVar3);
                gv gvVar = opVar.y;
                if (gvVar != null) {
                    gvVar.e(upVar3);
                }
                return x31Var;
            default:
                up upVar4 = (up) obj;
                upVar4.getClass();
                opVar.s.b(upVar4, opVar.z, (l40) opVar.C.getValue(), opVar.v);
                return x31Var;
        }
    }
}
