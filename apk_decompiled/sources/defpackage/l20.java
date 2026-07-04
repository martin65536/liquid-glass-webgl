package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class l20 {
    public final int a;
    public final int b;
    public final r7 c;

    public l20(int i, int i2, r7 r7Var) {
        this.a = i;
        this.b = i2;
        this.c = r7Var;
        if (i < 0) {
            t00.a("startIndex should be >= 0");
        }
        if (i2 > 0) {
            return;
        }
        t00.a("size should be > 0");
    }
}
