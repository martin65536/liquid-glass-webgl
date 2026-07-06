package defpackage;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ua extends gd0 {
    public final ba a;

    public ua(ba baVar) {
        this.a = baVar;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [va, bd0] */
    @Override // defpackage.gd0
    public final bd0 e() {
        ?? bd0Var = new bd0();
        bd0Var.s = this.a;
        return bd0Var;
    }

    public final boolean equals(Object obj) {
        ua uaVar;
        if (this == obj) {
            return true;
        }
        if (obj instanceof ua) {
            uaVar = (ua) obj;
        } else {
            uaVar = null;
        }
        if (uaVar != null && this.a.equals(uaVar.a)) {
            return true;
        }
        return false;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        ((va) bd0Var).s = this.a;
    }

    public final int hashCode() {
        return (this.a.hashCode() * 31) + 1237;
    }
}
