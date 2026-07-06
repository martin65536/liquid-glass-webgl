package defpackage;

import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class gd implements vv {
    public final yj e;
    public final int f;
    public final xb g;
    public final os h;

    public gd(os osVar, yj yjVar, int i, xb xbVar) {
        this.e = yjVar;
        this.f = i;
        this.g = xbVar;
        this.h = osVar;
    }

    public abstract gd a(yj yjVar, int i, xb xbVar);

    @Override // defpackage.os
    public final Object b(ps psVar, ij ijVar) {
        yj z;
        int i = this.f;
        ij ijVar2 = null;
        ik ikVar = ik.e;
        x31 x31Var = x31.a;
        if (i == -3) {
            yj r = ijVar.r();
            Boolean bool = Boolean.FALSE;
            w4 w4Var = new w4(6, (byte) 0);
            yj yjVar = this.e;
            if (!((Boolean) yjVar.n(w4Var, bool)).booleanValue()) {
                z = r.i(yjVar);
            } else {
                z = f31.z(r, yjVar, false);
            }
            if (o20.e(z, r)) {
                Object e = e(psVar, ijVar);
                if (e == ikVar) {
                    return e;
                }
            } else {
                x1 x1Var = x1.A;
                if (o20.e(z.j(x1Var), r.j(x1Var))) {
                    yj r2 = ijVar.r();
                    if (!(psVar instanceof kv0) && !(psVar instanceof vg0)) {
                        psVar = new ts(psVar, r2);
                    }
                    Object L = jc0.L(z, psVar, k81.N(z), new fd(this, ijVar2, 1), ijVar);
                    if (L == ikVar) {
                        return L;
                    }
                }
            }
            return x31Var;
        }
        Object q = dl.q(new f(psVar, this, ijVar2, 2), ijVar);
        if (q != ikVar) {
            q = x31Var;
        }
        if (q == ikVar) {
            return q;
        }
        return x31Var;
    }

    @Override // defpackage.vv
    public final os c(yj yjVar, int i, xb xbVar) {
        yj yjVar2 = this.e;
        yj i2 = yjVar.i(yjVar2);
        xb xbVar2 = xb.e;
        xb xbVar3 = this.g;
        int i3 = this.f;
        if (xbVar == xbVar2) {
            if (i3 != -3) {
                if (i != -3) {
                    if (i3 != -2) {
                        if (i != -2) {
                            i += i3;
                            if (i < 0) {
                                i = Integer.MAX_VALUE;
                            }
                        }
                    }
                }
                i = i3;
            }
            xbVar = xbVar3;
        }
        if (o20.e(i2, yjVar2) && i == i3 && xbVar == xbVar3) {
            return this;
        }
        return a(i2, i, xbVar);
    }

    public os d() {
        return null;
    }

    public abstract Object e(ps psVar, ij ijVar);

    public final String f() {
        ArrayList arrayList = new ArrayList(4);
        cr crVar = cr.e;
        yj yjVar = this.e;
        if (yjVar != crVar) {
            arrayList.add("context=" + yjVar);
        }
        int i = this.f;
        if (i != -3) {
            arrayList.add("capacity=" + i);
        }
        xb xbVar = xb.e;
        xb xbVar2 = this.g;
        if (xbVar2 != xbVar) {
            arrayList.add("onBufferOverflow=" + xbVar2);
        }
        return getClass().getSimpleName() + '[' + me.W(arrayList, ", ", null, null, null, 62) + ']';
    }

    public final String toString() {
        return this.h + " -> " + f();
    }
}
