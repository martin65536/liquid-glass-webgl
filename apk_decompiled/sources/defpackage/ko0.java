package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ko0 implements iy0, os, vv {
    public final /* synthetic */ ky0 e;

    public ko0(ky0 ky0Var) {
        this.e = ky0Var;
    }

    @Override // defpackage.os
    public final Object b(ps psVar, ij ijVar) {
        this.e.b(psVar, ijVar);
        return ik.e;
    }

    @Override // defpackage.vv
    public final os c(yj yjVar, int i, xb xbVar) {
        if ((((i >= 0 && i < 2) || i == -2) && xbVar == xb.f) || ((i == 0 || i == -3) && xbVar == xb.e)) {
            return this;
        }
        return new gd(this, yjVar, i, xbVar);
    }

    @Override // defpackage.iy0
    public final Object getValue() {
        return this.e.getValue();
    }
}
