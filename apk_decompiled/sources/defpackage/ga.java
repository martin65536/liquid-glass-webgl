package defpackage;

import java.util.concurrent.locks.LockSupport;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ga extends q {
    public final Thread j;
    public final nr k;

    public ga(yj yjVar, Thread thread, nr nrVar) {
        super(yjVar, true);
        this.j = thread;
        this.k = nrVar;
    }

    @Override // defpackage.l30
    public final void A(Object obj) {
        Thread currentThread = Thread.currentThread();
        Thread thread = this.j;
        if (!o20.e(currentThread, thread)) {
            LockSupport.unpark(thread);
        }
    }
}
