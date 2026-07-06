package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class aq implements Comparable {
    public static final dt0 e = new dt0(28);
    public static final long f = f31.u(4611686018427387903L);
    public static final long g = f31.u(-4611686018427387903L);

    public static final long a(long j, long j2) {
        long j3 = j2 / 1000000;
        long h = f31.h(j, j3);
        if (-4611686018426L <= h && h < 4611686018427L) {
            long j4 = ((h * 1000000) + (j2 - (j3 * 1000000))) << 1;
            int i = bq.a;
            return j4;
        }
        return f31.u(h);
    }
}
