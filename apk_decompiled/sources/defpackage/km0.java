package defpackage;

import android.os.Build;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class km0 {
    public static final jm0 a;

    static {
        jm0 dt0Var;
        if (Build.VERSION.SDK_INT >= 24) {
            dt0Var = new r7(1);
        } else {
            dt0Var = new dt0(10);
        }
        a = dt0Var;
    }
}
