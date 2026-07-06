package defpackage;

import android.os.Trace;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class sq implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        try {
            int i = l21.a;
            Trace.beginSection("EmojiCompat.EmojiCompatInitializer.run");
            if (oq.k != null) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                oq.a().c();
            }
            Trace.endSection();
        } catch (Throwable th) {
            int i2 = l21.a;
            Trace.endSection();
            throw th;
        }
    }
}
