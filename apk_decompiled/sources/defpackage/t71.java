package defpackage;

import android.os.Build;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class t71 implements s71 {
    public final nm b;

    public t71() {
        nm nmVar;
        if (Build.VERSION.SDK_INT >= 34) {
            nmVar = om.e;
        } else {
            nmVar = x1.C;
        }
        this.b = nmVar;
        jc0.h(1, 2, 4, 8, 16, 32, 64, 128);
    }
}
