package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
final class yz extends gd0 {
    public final je0 a;
    public final a00 b;

    public yz(je0 je0Var, a00 a00Var) {
        this.a = je0Var;
        this.b = a00Var;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [zz, jm, bd0] */
    @Override // defpackage.gd0
    public final bd0 e() {
        im a = this.b.a(this.a);
        ?? jmVar = new jm();
        jmVar.u = a;
        jmVar.D0(a);
        return jmVar;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof yz)) {
            return false;
        }
        yz yzVar = (yz) obj;
        if (o20.e(this.a, yzVar.a) && o20.e(this.b, yzVar.b)) {
            return true;
        }
        return false;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        zz zzVar = (zz) bd0Var;
        im a = this.b.a(this.a);
        zzVar.E0(zzVar.u);
        zzVar.u = a;
        zzVar.D0(a);
    }

    public final int hashCode() {
        return this.b.hashCode() + (this.a.hashCode() * 31);
    }
}
