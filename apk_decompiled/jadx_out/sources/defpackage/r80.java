package defpackage;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class r80 extends ak implements hm {
    public static final /* synthetic */ AtomicIntegerFieldUpdater l = AtomicIntegerFieldUpdater.newUpdater(r80.class, "runningWorkers$volatile");
    public final /* synthetic */ hm g;
    public final ak h;
    public final int i;
    public final bb0 j;
    public final Object k;
    private volatile /* synthetic */ int runningWorkers$volatile;

    /* JADX WARN: Multi-variable type inference failed */
    public r80(ak akVar, int i) {
        hm hmVar;
        if (akVar instanceof hm) {
            hmVar = (hm) akVar;
        } else {
            hmVar = null;
        }
        this.g = hmVar == null ? pl.a : hmVar;
        this.h = akVar;
        this.i = i;
        this.j = new bb0();
        this.k = new Object();
    }

    @Override // defpackage.hm
    public final un d(long j, f21 f21Var, yj yjVar) {
        return this.g.d(j, f21Var, yjVar);
    }

    @Override // defpackage.hm
    public final void f(long j, pc pcVar) {
        this.g.f(j, pcVar);
    }

    @Override // defpackage.ak
    public final void g(yj yjVar, Runnable runnable) {
        boolean z;
        Runnable q;
        this.j.a(runnable);
        if (l.get(this) < this.i) {
            synchronized (this.k) {
                AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = l;
                if (atomicIntegerFieldUpdater.get(this) >= this.i) {
                    z = false;
                } else {
                    atomicIntegerFieldUpdater.incrementAndGet(this);
                    z = true;
                }
            }
            if (z && (q = q()) != null) {
                this.h.g(this, new wx(this, q));
            }
        }
    }

    public final Runnable q() {
        while (true) {
            Runnable runnable = (Runnable) this.j.d();
            if (runnable == null) {
                synchronized (this.k) {
                    AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = l;
                    atomicIntegerFieldUpdater.decrementAndGet(this);
                    if (this.j.c() == 0) {
                        return null;
                    }
                    atomicIntegerFieldUpdater.incrementAndGet(this);
                }
            } else {
                return runnable;
            }
        }
    }

    @Override // defpackage.ak
    public final String toString() {
        return this.h + ".limitedParallelism(" + this.i + ')';
    }
}
