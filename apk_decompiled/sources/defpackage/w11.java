package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class w11 {
    public static final ThreadLocal a = new ThreadLocal();

    public static nr a() {
        ThreadLocal threadLocal = a;
        nr nrVar = (nr) threadLocal.get();
        if (nrVar == null) {
            ha haVar = new ha(Thread.currentThread());
            threadLocal.set(haVar);
            return haVar;
        }
        return nrVar;
    }
}
