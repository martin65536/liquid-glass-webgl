package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class dp extends gd0 {
    public static final pb b = new pb(2);
    public final kl a;

    public dp(kl klVar) {
        this.a = klVar;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [zo, fp, bd0] */
    @Override // defpackage.gd0
    public final bd0 e() {
        pb pbVar = jc0.b;
        pb pbVar2 = jc0.c;
        ?? zoVar = new zo(b, true, null, null);
        zoVar.N = this.a;
        zoVar.O = pbVar;
        zoVar.P = pbVar2;
        return zoVar;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && dp.class == obj.getClass() && o20.e(this.a, ((dp) obj).a)) {
            return true;
        }
        return false;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        boolean z;
        fp fpVar = (fp) bd0Var;
        pb pbVar = jc0.b;
        pb pbVar2 = jc0.c;
        kl klVar = fpVar.N;
        kl klVar2 = this.a;
        if (!o20.e(klVar, klVar2)) {
            fpVar.N = klVar2;
            z = true;
        } else {
            z = false;
        }
        boolean z2 = z;
        fpVar.O = pbVar;
        fpVar.P = pbVar2;
        fpVar.X0(b, true, null, null, z2);
    }

    public final int hashCode() {
        return ((jc0.c.hashCode() + ((jc0.b.hashCode() + (((((this.a.hashCode() * 31) + 1231) * 961) + 1237) * 31)) * 31)) * 31) + 1237;
    }
}
