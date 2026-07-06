package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ph0 extends z30 implements gv {
    public final /* synthetic */ int f;
    public final /* synthetic */ pt g;
    public final /* synthetic */ pt h;
    public final /* synthetic */ int i;
    public final /* synthetic */ oj j;
    public final /* synthetic */ Object k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ph0(pt ptVar, pt ptVar2, Object obj, int i, oj ojVar, int i2) {
        super(1);
        this.f = i2;
        this.g = ptVar;
        this.h = ptVar2;
        this.k = obj;
        this.i = i;
        this.j = ojVar;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        int i = this.f;
        oj ojVar = this.j;
        int i2 = this.i;
        Object obj2 = this.k;
        pt ptVar = this.h;
        pt ptVar2 = this.g;
        switch (i) {
            case 0:
                y9 y9Var = (y9) obj;
                if (ptVar2 != ((lt) ((b4) k81.F(ptVar)).getFocusOwner()).f()) {
                    return Boolean.TRUE;
                }
                boolean G = d20.G(ptVar, (pt) obj2, i2, ojVar);
                Boolean valueOf = Boolean.valueOf(G);
                if (!G && y9Var.a()) {
                    return null;
                }
                return valueOf;
            default:
                y9 y9Var2 = (y9) obj;
                if (ptVar2 != ((lt) ((b4) k81.F(ptVar)).getFocusOwner()).f()) {
                    return Boolean.TRUE;
                }
                boolean M = t20.M(i2, ojVar, ptVar, (wo0) obj2);
                Boolean valueOf2 = Boolean.valueOf(M);
                if (!M && y9Var2.a()) {
                    return null;
                }
                return valueOf2;
        }
    }
}
