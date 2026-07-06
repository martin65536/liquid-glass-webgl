package defpackage;

import java.util.concurrent.CancellationException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class rg0 extends r implements d30 {
    public static final rg0 f = new r(x1.L);

    @Override // defpackage.d30
    public final boolean b() {
        return true;
    }

    @Override // defpackage.d30
    public final boolean e() {
        return false;
    }

    @Override // defpackage.d30
    public final sd h(l30 l30Var) {
        return sg0.e;
    }

    @Override // defpackage.d30
    public final CancellationException m() {
        throw new IllegalStateException("This job is always active");
    }

    @Override // defpackage.d30
    public final un p(gv gvVar) {
        return sg0.e;
    }

    public final String toString() {
        return "NonCancellable";
    }

    @Override // defpackage.d30
    public final Object w(jj jjVar) {
        throw new UnsupportedOperationException("This job is always active");
    }

    @Override // defpackage.d30
    public final un y(boolean z, boolean z2, e eVar) {
        return sg0.e;
    }

    @Override // defpackage.d30
    public final void a(CancellationException cancellationException) {
    }
}
