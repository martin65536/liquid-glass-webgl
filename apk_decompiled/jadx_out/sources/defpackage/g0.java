package defpackage;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class g0 extends o4 {
    public final AtomicReferenceFieldUpdater k;
    public final AtomicReferenceFieldUpdater l;
    public final AtomicReferenceFieldUpdater m;
    public final AtomicReferenceFieldUpdater n;
    public final AtomicReferenceFieldUpdater o;

    public g0(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater3, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater4, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater5) {
        this.k = atomicReferenceFieldUpdater;
        this.l = atomicReferenceFieldUpdater2;
        this.m = atomicReferenceFieldUpdater3;
        this.n = atomicReferenceFieldUpdater4;
        this.o = atomicReferenceFieldUpdater5;
    }

    @Override // defpackage.o4
    public final void U(i0 i0Var, i0 i0Var2) {
        this.l.lazySet(i0Var, i0Var2);
    }

    @Override // defpackage.o4
    public final void V(i0 i0Var, Thread thread) {
        this.k.lazySet(i0Var, thread);
    }

    @Override // defpackage.o4
    public final boolean p(j0 j0Var, f0 f0Var) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        do {
            atomicReferenceFieldUpdater = this.n;
            if (atomicReferenceFieldUpdater.compareAndSet(j0Var, f0Var, f0.b)) {
                return true;
            }
        } while (atomicReferenceFieldUpdater.get(j0Var) == f0Var);
        return false;
    }

    @Override // defpackage.o4
    public final boolean q(j0 j0Var, Object obj, Object obj2) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        do {
            atomicReferenceFieldUpdater = this.o;
            if (atomicReferenceFieldUpdater.compareAndSet(j0Var, obj, obj2)) {
                return true;
            }
        } while (atomicReferenceFieldUpdater.get(j0Var) == obj);
        return false;
    }

    @Override // defpackage.o4
    public final boolean r(j0 j0Var, i0 i0Var, i0 i0Var2) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        do {
            atomicReferenceFieldUpdater = this.m;
            if (atomicReferenceFieldUpdater.compareAndSet(j0Var, i0Var, i0Var2)) {
                return true;
            }
        } while (atomicReferenceFieldUpdater.get(j0Var) == i0Var);
        return false;
    }
}
