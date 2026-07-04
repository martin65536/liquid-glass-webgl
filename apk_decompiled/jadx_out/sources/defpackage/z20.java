package defpackage;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class z20 extends h30 {
    public static final /* synthetic */ AtomicIntegerFieldUpdater m = AtomicIntegerFieldUpdater.newUpdater(z20.class, "_invoked$volatile");
    private volatile /* synthetic */ int _invoked$volatile = 0;
    public final e l;

    public z20(e eVar) {
        this.l = eVar;
    }

    @Override // defpackage.h30
    public final boolean r() {
        return true;
    }

    @Override // defpackage.h30
    public final void s(Throwable th) {
        if (m.compareAndSet(this, 0, 1)) {
            this.l.e(th);
        }
    }
}
