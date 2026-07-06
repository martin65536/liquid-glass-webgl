package defpackage;

import android.view.MotionEvent;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class z3 implements Runnable {
    public final /* synthetic */ int e;
    public final /* synthetic */ Object f;

    public /* synthetic */ z3(int i, Object obj) {
        this.e = i;
        this.f = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int actionMasked;
        int i = this.e;
        Object obj = this.f;
        switch (i) {
            case 0:
                b4 b4Var = (b4) obj;
                b4Var.removeCallbacks(this);
                MotionEvent motionEvent = b4Var.y0;
                if (motionEvent != null && (actionMasked = motionEvent.getActionMasked()) != 10 && actionMasked != 1) {
                    int i2 = 7;
                    if (actionMasked != 7 && actionMasked != 9) {
                        i2 = 2;
                    }
                    b4Var.K(motionEvent, i2, b4Var.z0, false);
                    return;
                }
                return;
            default:
                zx.a((pc) obj);
                return;
        }
    }
}
