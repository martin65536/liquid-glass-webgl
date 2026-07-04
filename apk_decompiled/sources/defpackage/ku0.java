package defpackage;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class ku0 extends ki implements wg0 {
    public static final /* synthetic */ AtomicIntegerFieldUpdater f = AtomicIntegerFieldUpdater.newUpdater(ku0.class, "cleanedAndPointers$volatile");
    private volatile /* synthetic */ int cleanedAndPointers$volatile;
    public final long e;

    public ku0(long j, ku0 ku0Var, int i) {
        super(ku0Var);
        this.e = j;
        this.cleanedAndPointers$volatile = i << 16;
    }

    @Override // defpackage.ki
    public final boolean f() {
        if (f.get(this) == k() && c() != null) {
            return true;
        }
        return false;
    }

    public final boolean j() {
        if (f.addAndGet(this, -65536) == k() && c() != null) {
            return true;
        }
        return false;
    }

    public abstract int k();

    public abstract void l(int i, yj yjVar);

    public final void m() {
        if (f.incrementAndGet(this) == k()) {
            h();
        }
    }

    public final boolean n() {
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater;
        int i;
        do {
            atomicIntegerFieldUpdater = f;
            i = atomicIntegerFieldUpdater.get(this);
            if (i == k() && c() != null) {
                return false;
            }
        } while (!atomicIntegerFieldUpdater.compareAndSet(this, i, 65536 + i));
        return true;
    }
}
