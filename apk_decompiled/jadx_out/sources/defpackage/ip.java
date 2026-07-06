package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class ip {
    public static final hp a;
    public static final hp b;

    static {
        int i = 3;
        ij ijVar = null;
        a = new hp(i, ijVar, 0);
        b = new hp(i, ijVar, 1);
    }

    public static final long a(long j) {
        float b2;
        float f = 0.0f;
        if (Float.isNaN(v41.b(j))) {
            b2 = 0.0f;
        } else {
            b2 = v41.b(j);
        }
        if (!Float.isNaN(v41.c(j))) {
            f = v41.c(j);
        }
        return o30.c(b2, f);
    }
}
