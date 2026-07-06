package defpackage;

import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class oy0 implements ny0 {
    public final o8 e = new AtomicInteger(0);

    @Override // defpackage.ny0
    public /* synthetic */ py0 b(py0 py0Var, py0 py0Var2, py0 py0Var3) {
        return null;
    }

    public final boolean e(int i) {
        if ((this.e.get() & i) != 0) {
            return true;
        }
        return false;
    }

    public final void f(int i) {
        o8 o8Var;
        int i2;
        do {
            o8Var = this.e;
            i2 = o8Var.get();
            if ((i2 & i) != 0) {
                return;
            }
        } while (!o8Var.compareAndSet(i2, i2 | i));
    }
}
