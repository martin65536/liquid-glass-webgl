package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class n21 {
    public final long a;
    public final long b;
    public final boolean c;

    public n21(long j, long j2, boolean z) {
        this.a = j;
        this.b = j2;
        this.c = z;
    }

    public final n21 a(n21 n21Var) {
        boolean z;
        long g = ch0.g(this.a, n21Var.a);
        long max = Math.max(this.b, n21Var.b);
        if (!this.c && !n21Var.c) {
            z = false;
        } else {
            z = true;
        }
        return new n21(g, max, z);
    }
}
