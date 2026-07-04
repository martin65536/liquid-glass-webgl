package defpackage;

import android.view.Choreographer;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class k6 extends sz0 implements kv {
    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        return ((k6) i((ij) obj2, (hk) obj)).k(x31.a);
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        return new sz0(2, ijVar);
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        o30.x(obj);
        return Choreographer.getInstance();
    }
}
