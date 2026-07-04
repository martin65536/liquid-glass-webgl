package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class k extends sz0 implements kv {
    public final /* synthetic */ int i;
    public final /* synthetic */ de j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ k(de deVar, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.j = deVar;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        hk hkVar = (hk) obj;
        ij ijVar = (ij) obj2;
        switch (i) {
            case 0:
                ((k) i(ijVar, hkVar)).k(x31Var);
                return x31Var;
            default:
                ((k) i(ijVar, hkVar)).k(x31Var);
                return x31Var;
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        int i = this.i;
        de deVar = this.j;
        switch (i) {
            case 0:
                return new k(deVar, ijVar, 0);
            default:
                return new k(deVar, ijVar, 1);
        }
    }

    /* JADX WARN: Type inference failed for: r8v2, types: [wy, java.lang.Object] */
    @Override // defpackage.s9
    public final Object k(Object obj) {
        int i = this.i;
        x31 x31Var = x31.a;
        de deVar = this.j;
        switch (i) {
            case 0:
                o30.x(obj);
                if (deVar.F == null) {
                    ?? obj2 = new Object();
                    je0 je0Var = deVar.u;
                    if (je0Var != null) {
                        f31.G(deVar.p0(), null, new d(je0Var, obj2, null, 0), 3);
                    }
                    deVar.F = obj2;
                }
                return x31Var;
            default:
                o30.x(obj);
                wy wyVar = deVar.F;
                if (wyVar != null) {
                    xy xyVar = new xy(wyVar);
                    je0 je0Var2 = deVar.u;
                    if (je0Var2 != null) {
                        f31.G(deVar.p0(), null, new d(je0Var2, xyVar, null, 1), 3);
                    }
                    deVar.F = null;
                }
                return x31Var;
        }
    }
}
