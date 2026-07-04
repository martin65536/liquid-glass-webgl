package defpackage;

import java.util.concurrent.Executor;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class vl extends ur implements Executor {
    public static final vl g = new ak();
    public static final ak h;

    /* JADX WARN: Type inference failed for: r0v0, types: [vl, ak] */
    static {
        y31 y31Var = y31.g;
        int i = d01.a;
        if (64 >= i) {
            i = 64;
        }
        h = y31Var.o(m20.H(i, 12, "kotlinx.coroutines.io.parallelism"));
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        throw new IllegalStateException("Cannot be invoked on Dispatchers.IO");
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        g(cr.e, runnable);
    }

    @Override // defpackage.ak
    public final void g(yj yjVar, Runnable runnable) {
        h.g(yjVar, runnable);
    }

    @Override // defpackage.ak
    public final String toString() {
        return "Dispatchers.IO";
    }
}
