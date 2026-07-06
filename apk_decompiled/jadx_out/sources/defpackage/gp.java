package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class gp extends gd0 {
    public static final pb d = new pb(2);
    public final ml a;
    public final lv b;
    public final lv c;

    public gp(ml mlVar, lv lvVar, lv lvVar2) {
        this.a = mlVar;
        this.b = lvVar;
        this.c = lvVar2;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [zo, kp, bd0] */
    @Override // defpackage.gd0
    public final bd0 e() {
        pb pbVar = d;
        dj0 dj0Var = dj0.e;
        ?? zoVar = new zo(pbVar, true, null, dj0Var);
        zoVar.N = this.a;
        zoVar.O = dj0Var;
        zoVar.P = this.b;
        zoVar.Q = this.c;
        return zoVar;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && gp.class == obj.getClass()) {
            gp gpVar = (gp) obj;
            if (o20.e(this.a, gpVar.a) && o20.e(this.b, gpVar.b) && o20.e(this.c, gpVar.c)) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        boolean z;
        boolean z2;
        kp kpVar = (kp) bd0Var;
        ml mlVar = kpVar.N;
        ml mlVar2 = this.a;
        if (!o20.e(mlVar, mlVar2)) {
            kpVar.N = mlVar2;
            z = true;
        } else {
            z = false;
        }
        dj0 dj0Var = kpVar.O;
        dj0 dj0Var2 = dj0.e;
        if (dj0Var != dj0Var2) {
            kpVar.O = dj0Var2;
            z2 = true;
        } else {
            z2 = z;
        }
        kpVar.P = this.b;
        kpVar.Q = this.c;
        kpVar.X0(d, true, null, dj0Var2, z2);
    }

    public final int hashCode() {
        return ((this.c.hashCode() + ((this.b.hashCode() + ((((((dj0.e.hashCode() + (this.a.hashCode() * 31)) * 31) + 1231) * 961) + 1237) * 31)) * 31)) * 31) + 1237;
    }
}
