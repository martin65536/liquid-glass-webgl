package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class bm extends ur {
    public static final bm h;
    public gk g;

    /* JADX WARN: Type inference failed for: r0v0, types: [ak, bm] */
    static {
        int i = s01.c;
        int i2 = s01.d;
        long j = s01.e;
        String str = s01.a;
        ?? akVar = new ak();
        akVar.g = new gk(i, i2, j, str);
        h = akVar;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        throw new UnsupportedOperationException("Dispatchers.Default cannot be closed");
    }

    @Override // defpackage.ak
    public final void g(yj yjVar, Runnable runnable) {
        gk.c(this.g, runnable, 6);
    }

    @Override // defpackage.ak
    public final String toString() {
        return "Dispatchers.Default";
    }
}
