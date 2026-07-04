package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class st extends uv implements kv {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v4, types: [ep0, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v4, types: [ct, g20, java.lang.Object] */
    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        boolean a;
        ot otVar = (ot) obj;
        ot otVar2 = (ot) obj2;
        tt ttVar = (tt) this.f;
        if (ttVar.r && (a = otVar2.a()) != otVar.a()) {
            e eVar = ttVar.v;
            if (eVar != null) {
                eVar.e(Boolean.valueOf(a));
            }
            rt rtVar = ut.s;
            if (a) {
                f31.G(ttVar.p0(), null, new m8(ttVar, (ij) null, 2), 3);
                ?? obj3 = new Object();
                o30.u(ttVar, new f9(3, obj3, ttVar));
                l60 l60Var = (l60) obj3.e;
                if (l60Var != null) {
                    l60Var.a();
                } else {
                    l60Var = null;
                }
                ttVar.x = l60Var;
                ng0 ng0Var = ttVar.y;
                if (ng0Var != null && ng0Var.P0().r && ttVar.r) {
                    d20.p(ttVar, rtVar);
                }
            } else {
                l60 l60Var2 = ttVar.x;
                if (l60Var2 != null) {
                    l60Var2.b();
                }
                ttVar.x = null;
                if (ttVar.r) {
                    d20.p(ttVar, rtVar);
                }
            }
            m20.w(ttVar);
            je0 je0Var = ttVar.u;
            if (je0Var != null) {
                ct ctVar = ttVar.w;
                if (a) {
                    if (ctVar != null) {
                        ttVar.G0(je0Var, new dt(ctVar));
                        ttVar.w = null;
                    }
                    ?? obj4 = new Object();
                    ttVar.G0(je0Var, obj4);
                    ttVar.w = obj4;
                } else if (ctVar != null) {
                    ttVar.G0(je0Var, new dt(ctVar));
                    ttVar.w = null;
                }
            }
        }
        return x31.a;
    }
}
