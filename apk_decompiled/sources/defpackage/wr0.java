package defpackage;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class wr0 implements ij, jk {
    public static final AtomicReferenceFieldUpdater f = AtomicReferenceFieldUpdater.newUpdater(wr0.class, Object.class, "result");
    public final ij e;
    private volatile Object result;

    public wr0(ij ijVar) {
        ik ikVar = ik.e;
        this.e = ijVar;
        this.result = ikVar;
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
        return this.e.r();
    }

    public final String toString() {
        return "SafeContinuation for " + this.e;
    }

    @Override // defpackage.ij
    public final void u(Object obj) {
        while (true) {
            Object obj2 = this.result;
            ik ikVar = ik.f;
            if (obj2 == ikVar) {
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f;
                while (!atomicReferenceFieldUpdater.compareAndSet(this, ikVar, obj)) {
                    if (atomicReferenceFieldUpdater.get(this) != ikVar) {
                        break;
                    }
                }
                return;
            }
            ik ikVar2 = ik.e;
            if (obj2 == ikVar2) {
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = f;
                ik ikVar3 = ik.g;
                while (!atomicReferenceFieldUpdater2.compareAndSet(this, ikVar2, ikVar3)) {
                    if (atomicReferenceFieldUpdater2.get(this) != ikVar2) {
                        break;
                    }
                }
                this.e.u(obj);
                return;
            }
            v7.o("Already resumed");
            return;
        }
    }
}
