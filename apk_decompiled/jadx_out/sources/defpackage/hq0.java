package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class hq0 extends s9 {
    public hq0(ij ijVar) {
        super(ijVar);
        if (ijVar != null && ijVar.r() != cr.e) {
            v7.m("Coroutines with restricted suspension must have EmptyCoroutineContext");
            throw null;
        }
    }

    @Override // defpackage.ij
    public final yj r() {
        return cr.e;
    }
}
