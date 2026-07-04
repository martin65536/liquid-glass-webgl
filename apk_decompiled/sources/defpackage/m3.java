package defpackage;

import android.os.Trace;
import android.view.MotionEvent;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class m3 implements Runnable {
    public final /* synthetic */ int e;
    public final /* synthetic */ b4 f;

    public /* synthetic */ m3(b4 b4Var, int i) {
        this.e = i;
        this.f = b4Var;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.e;
        b4 b4Var = this.f;
        switch (i) {
            case 0:
                a8 a8Var = b4Var.m;
                Trace.beginSection("AndroidOwner:outOfFrameExecutor");
                while (!a8Var.isEmpty()) {
                    try {
                        ((vu) a8Var.removeLast()).a();
                    } finally {
                        Trace.endSection();
                    }
                }
                return;
            case 1:
                b4Var.G0 = false;
                MotionEvent motionEvent = b4Var.y0;
                motionEvent.getClass();
                if (motionEvent.getActionMasked() == 10) {
                    b4Var.J(motionEvent);
                    return;
                } else {
                    v7.o("The ACTION_HOVER_EXIT event was not cleared.");
                    return;
                }
            case 2:
                b4.q(b4Var.getRoot());
                return;
            default:
                b4.q(b4Var.getRoot());
                return;
        }
    }
}
