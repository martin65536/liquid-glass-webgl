package defpackage;

import java.util.concurrent.atomic.AtomicReference;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ly0 extends m0 {
    public final AtomicReference a = new AtomicReference(null);

    @Override // defpackage.m0
    public final boolean a(l0 l0Var) {
        AtomicReference atomicReference = this.a;
        if (atomicReference.get() != null) {
            return false;
        }
        atomicReference.set(o20.q);
        return true;
    }

    @Override // defpackage.m0
    public final ij[] b(l0 l0Var) {
        this.a.set(null);
        return o4.a;
    }
}
