package defpackage;

import android.content.Context;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class xn0 implements Runnable {
    public final /* synthetic */ int e;
    public final /* synthetic */ Context f;

    public /* synthetic */ xn0(Context context, int i) {
        this.e = i;
        this.f = context;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [java.util.concurrent.Executor, java.lang.Object] */
    @Override // java.lang.Runnable
    public final void run() {
        int i = this.e;
        Context context = this.f;
        switch (i) {
            case 0:
                new ThreadPoolExecutor(0, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue()).execute(new xn0(context, 1));
                return;
            default:
                o4.b0(context, new Object(), o4.g, false);
                return;
        }
    }
}
