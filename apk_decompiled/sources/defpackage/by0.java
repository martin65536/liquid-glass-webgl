package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class by0 implements ij, jk {
    public final ij e;
    public final yj f;

    public by0(ij ijVar, yj yjVar) {
        this.e = ijVar;
        this.f = yjVar;
    }

    @Override // defpackage.jk
    public final jk f() {
        ij ijVar = this.e;
        if (ijVar instanceof jk) {
            return (jk) ijVar;
        }
        return null;
    }

    @Override // defpackage.ij
    public final yj r() {
        return this.f;
    }

    @Override // defpackage.ij
    public final void u(Object obj) {
        this.e.u(obj);
    }
}
