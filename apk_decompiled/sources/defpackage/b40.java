package defpackage;

import java.util.concurrent.CancellationException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class b40 implements np0, bk {
    public final yj e;
    public final kv f;
    public final hj g;
    public dy0 h;

    public b40(yj yjVar, kv kvVar) {
        this.e = yjVar;
        this.f = kvVar;
        this.g = dl.d(yjVar.i(this));
    }

    @Override // defpackage.np0
    public final void d() {
        dy0 dy0Var = this.h;
        if (dy0Var != null) {
            CancellationException cancellationException = new CancellationException("Old job was still running!");
            cancellationException.initCause(null);
            dy0Var.a(cancellationException);
        }
        this.h = f31.G(this.g, null, this.f, 3);
    }

    @Override // defpackage.np0
    public final void f() {
        dy0 dy0Var = this.h;
        if (dy0Var != null) {
            dy0Var.E(new tu(1));
        }
        this.h = null;
    }

    @Override // defpackage.wj
    public final xj getKey() {
        return x1.B;
    }

    @Override // defpackage.yj
    public final yj i(yj yjVar) {
        return jc0.z(this, yjVar);
    }

    @Override // defpackage.yj
    public final wj j(xj xjVar) {
        return jc0.p(this, xjVar);
    }

    @Override // defpackage.np0
    public final void k() {
        dy0 dy0Var = this.h;
        if (dy0Var != null) {
            dy0Var.E(new tu(1));
        }
        this.h = null;
    }

    @Override // defpackage.bk
    public final void l(yj yjVar, Throwable th) {
        wh whVar = (wh) yjVar.j(wh.f);
        if (whVar != null) {
            k81.O(th, new f9(2, whVar, this));
        }
        bk bkVar = (bk) this.e.j(x1.B);
        if (bkVar != null) {
            bkVar.l(yjVar, th);
            return;
        }
        throw th;
    }

    @Override // defpackage.yj
    public final Object n(kv kvVar, Object obj) {
        return kvVar.d(obj, this);
    }

    @Override // defpackage.yj
    public final yj s(xj xjVar) {
        return jc0.x(this, xjVar);
    }
}
