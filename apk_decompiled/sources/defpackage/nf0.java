package defpackage;

import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class nf0 {
    public final AtomicReference a = new AtomicReference(null);
    public final qf0 b = new qf0();

    public static final void a(nf0 nf0Var, if0 if0Var) {
        AtomicReference atomicReference = nf0Var.a;
        while (true) {
            if0 if0Var2 = (if0) atomicReference.get();
            if (if0Var2 != null && if0Var.a.compareTo(if0Var2.a) < 0) {
                throw new CancellationException("Current mutation had a higher priority");
            }
            while (!atomicReference.compareAndSet(if0Var2, if0Var)) {
                if (atomicReference.get() != if0Var2) {
                    break;
                }
            }
            if (if0Var2 != null) {
                if0Var2.b.a(new lm0("Mutation interrupted", 0));
                return;
            }
            return;
        }
    }
}
