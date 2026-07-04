package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class p50 extends bd0 implements jk0 {
    public float s;
    public boolean t;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.lang.Object, nr0] */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.lang.Object, nr0] */
    /* JADX WARN: Type inference failed for: r2v6 */
    @Override // defpackage.jk0
    public final Object l0(Object obj) {
        ?? r2;
        if (obj instanceof nr0) {
            r2 = (nr0) obj;
        } else {
            r2 = 0;
        }
        if (r2 == 0) {
            r2 = new Object();
            r2.a = 0.0f;
            r2.b = true;
        }
        r2.a = this.s;
        r2.b = this.t;
        return r2;
    }
}
