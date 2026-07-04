package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class q5 {
    public static final /* synthetic */ int a = 0;

    static {
        boolean z;
        boolean z2;
        boolean z3;
        f31.f(4284900966L);
        boolean z4 = false;
        if (0.0f >= 0.0f) {
            z = true;
        } else {
            z = false;
        }
        if (0.0f >= 0.0f) {
            z2 = true;
        } else {
            z2 = false;
        }
        boolean z5 = z & z2;
        if (0.0f >= 0.0f) {
            z3 = true;
        } else {
            z3 = false;
        }
        boolean z6 = z5 & z3;
        if (0.0f >= 0.0f) {
            z4 = true;
        }
        if (!(z6 & z4)) {
            o00.a("Padding must be non-negative");
        }
    }
}
