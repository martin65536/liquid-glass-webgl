package defpackage;

import java.util.concurrent.CancellationException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class un0 extends q implements ed, jv0 {
    public final zb j;

    public un0(yj yjVar, zb zbVar) {
        super(yjVar, true);
        this.j = zbVar;
    }

    @Override // defpackage.l30
    public final void E(CancellationException cancellationException) {
        this.j.g(cancellationException, true);
        D(cancellationException);
    }

    @Override // defpackage.l30, defpackage.d30
    public final void a(CancellationException cancellationException) {
        Object Q = Q();
        if (!(Q instanceof qf)) {
            if (!(Q instanceof k30) || !((k30) Q).f()) {
                if (cancellationException == null) {
                    cancellationException = new e30(G(), null, this);
                }
                E(cancellationException);
            }
        }
    }

    @Override // defpackage.jv0
    public final Object d(ij ijVar, Object obj) {
        return this.j.d(ijVar, obj);
    }

    @Override // defpackage.ed
    public final Object k(sz0 sz0Var) {
        zb zbVar = this.j;
        zbVar.getClass();
        return zb.D(zbVar, sz0Var);
    }

    @Override // defpackage.q
    public final void m0(Throwable th, boolean z) {
        if (!this.j.g(th, false) && !z) {
            o4.K(this.i, th);
        }
    }

    @Override // defpackage.q
    public final void n0(Object obj) {
        this.j.g(null, false);
    }

    @Override // defpackage.ed
    public final Object o() {
        return this.j.o();
    }

    @Override // defpackage.jv0
    public final Object q(Object obj) {
        return this.j.q(obj);
    }
}
