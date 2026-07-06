package defpackage;

import android.view.Choreographer;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class p6 implements Choreographer.FrameCallback {
    public final /* synthetic */ pc e;
    public final /* synthetic */ gv f;

    public p6(pc pcVar, q6 q6Var, gv gvVar) {
        this.e = pcVar;
        this.f = gvVar;
    }

    @Override // android.view.Choreographer.FrameCallback
    public final void doFrame(long j) {
        Object jq0Var;
        try {
            jq0Var = this.f.e(Long.valueOf(j));
        } catch (Throwable th) {
            jq0Var = new jq0(th);
        }
        this.e.u(jq0Var);
    }
}
