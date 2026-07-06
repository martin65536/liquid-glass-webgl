package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class ct0 extends q implements jk {
    public final ij j;

    public ct0(ij ijVar, yj yjVar) {
        super(yjVar, true);
        this.j = ijVar;
    }

    @Override // defpackage.l30
    public void A(Object obj) {
        n20.N(t20.w(this.j), o20.y(obj));
    }

    @Override // defpackage.l30
    public void B(Object obj) {
        this.j.u(o20.y(obj));
    }

    @Override // defpackage.l30
    public final boolean V() {
        return true;
    }

    @Override // defpackage.jk
    public final jk f() {
        ij ijVar = this.j;
        if (ijVar instanceof jk) {
            return (jk) ijVar;
        }
        return null;
    }
}
