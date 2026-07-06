package defpackage;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Choreographer;
import java.util.Random;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class yx implements Choreographer.FrameCallback {
    public final /* synthetic */ int e = 1;
    public final /* synthetic */ Object f;

    @Override // android.view.Choreographer.FrameCallback
    public final void doFrame(long j) {
        Handler handler;
        int i = this.e;
        Object obj = this.f;
        switch (i) {
            case 0:
                bm bmVar = mn.a;
                ((pc) obj).H(yb0.a, Long.valueOf(j));
                return;
            default:
                Context context = (Context) obj;
                if (Build.VERSION.SDK_INT >= 28) {
                    handler = Handler.createAsync(Looper.getMainLooper());
                } else {
                    handler = new Handler(Looper.getMainLooper());
                }
                handler.postDelayed(new xn0(context, 0), new Random().nextInt(Math.max(1000, 1)) + 5000);
                return;
        }
    }
}
