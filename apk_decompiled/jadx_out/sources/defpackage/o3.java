package defpackage;

import android.os.Build;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class o3 implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        pe0 pe0Var = b4.S0;
        synchronized (pe0Var) {
            try {
                int i = Build.VERSION.SDK_INT;
                Object[] objArr = pe0Var.a;
                int i2 = pe0Var.b;
                int i3 = 0;
                if (i < 30) {
                    while (i3 < i2) {
                        b4 b4Var = (b4) objArr[i3];
                        boolean showLayoutBounds = b4Var.getShowLayoutBounds();
                        Class cls = b4.P0;
                        b4Var.setShowLayoutBounds(k81.v());
                        if (showLayoutBounds != b4Var.getShowLayoutBounds()) {
                            b4Var.post(new m3(b4Var, 2));
                        }
                        i3++;
                    }
                } else {
                    while (i3 < i2) {
                        b4 b4Var2 = (b4) objArr[i3];
                        b4Var2.post(new m3(b4Var2, 3));
                        i3++;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
