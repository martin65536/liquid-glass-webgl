package defpackage;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ea extends gd0 {
    public final gv a;

    public ea(gv gvVar) {
        this.a = gvVar;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [fa, bd0] */
    @Override // defpackage.gd0
    public final bd0 e() {
        ?? bd0Var = new bd0();
        bd0Var.s = this.a;
        return bd0Var;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ea)) {
            return false;
        }
        if (this.a == ((ea) obj).a) {
            return true;
        }
        return false;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        ng0 ng0Var;
        fa faVar = (fa) bd0Var;
        gv gvVar = this.a;
        faVar.s = gvVar;
        if (faVar.e.r && (ng0Var = k81.B(faVar, 2).t) != null) {
            ng0Var.m1(gvVar, true);
        }
    }

    public final int hashCode() {
        return this.a.hashCode();
    }
}
