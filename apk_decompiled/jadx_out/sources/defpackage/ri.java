package defpackage;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ri implements lv0 {
    public final AtomicReference a;

    public ri(lv0 lv0Var) {
        this.a = new AtomicReference(lv0Var);
    }

    @Override // defpackage.lv0
    public final Iterator iterator() {
        lv0 lv0Var = (lv0) this.a.getAndSet(null);
        if (lv0Var != null) {
            return lv0Var.iterator();
        }
        v7.o("This sequence can be consumed only once.");
        return null;
    }
}
