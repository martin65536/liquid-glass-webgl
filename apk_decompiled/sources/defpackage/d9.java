package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class d9 {
    public final e3 a;
    public final mh0 b;

    /* JADX WARN: Multi-variable type inference failed */
    public d9(e3 e3Var, mh0 mh0Var) {
        this.a = e3Var;
        this.b = mh0Var;
        if ((e3Var == null ? mh0Var : e3Var) != null) {
            return;
        }
        v7.m("At least one dispatcher (NavigationEventDispatcher or OnBackPressedDispatcher) must be non-null.");
        throw null;
    }
}
