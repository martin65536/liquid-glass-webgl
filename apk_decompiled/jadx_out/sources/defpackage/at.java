package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class at extends sz0 implements kv {
    public /* synthetic */ int i;

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        return ((at) i((ij) obj2, Integer.valueOf(((Number) obj).intValue()))).k(x31.a);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [ij, at, sz0] */
    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        ?? sz0Var = new sz0(2, ijVar);
        sz0Var.i = ((Number) obj).intValue();
        return sz0Var;
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        boolean z;
        o30.x(obj);
        if (this.i > 0) {
            z = true;
        } else {
            z = false;
        }
        return Boolean.valueOf(z);
    }
}
