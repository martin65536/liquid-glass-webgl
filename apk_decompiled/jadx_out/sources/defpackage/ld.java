package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ld extends gd {
    public final lv i;

    public ld(lv lvVar, os osVar, yj yjVar, int i, xb xbVar) {
        super(osVar, yjVar, i, xbVar);
        this.i = lvVar;
    }

    @Override // defpackage.gd
    public final gd a(yj yjVar, int i, xb xbVar) {
        return new ld(this.i, this.h, yjVar, i, xbVar);
    }

    @Override // defpackage.gd
    public final Object e(ps psVar, ij ijVar) {
        Object q = dl.q(new id(this, psVar, null), ijVar);
        if (q == ik.e) {
            return q;
        }
        return x31.a;
    }
}
