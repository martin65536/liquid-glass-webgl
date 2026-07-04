package defpackage;

import android.window.BackEvent;
import android.window.OnBackAnimationCallback;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ih0 implements OnBackAnimationCallback {
    public final /* synthetic */ gh0 a;

    public ih0(gh0 gh0Var) {
        this.a = gh0Var;
    }

    public final void onBackCancelled() {
        gh0 gh0Var = this.a;
        e3 e3Var = gh0Var.a;
        if (e3Var != null) {
            if (!gh0Var.b) {
                e3Var.j(gh0Var, null);
            }
            wf0 wf0Var = (wf0) e3Var.b;
            wf0Var.getClass();
            if (gh0Var.equals(wf0Var.h) && -1 == wf0Var.g) {
                tf0 tf0Var = wf0Var.f;
                if (tf0Var == null) {
                    tf0Var = wf0Var.c(-1);
                }
                wf0Var.f = null;
                wf0Var.g = 0;
                wf0Var.h = null;
                if (tf0Var != null) {
                    tf0Var.a();
                }
                ky0 ky0Var = wf0Var.a;
                ky0Var.getClass();
                ky0Var.j(null, xf0.a);
            }
            gh0Var.b = false;
            return;
        }
        v7.o("This input is not added to any dispatcher.");
    }

    public final void onBackInvoked() {
        this.a.a();
    }

    public final void onBackProgressed(BackEvent backEvent) {
        backEvent.getClass();
        rf0 e = g30.e(backEvent);
        gh0 gh0Var = this.a;
        e3 e3Var = gh0Var.a;
        if (e3Var != null) {
            if (gh0Var.b) {
                wf0 wf0Var = (wf0) e3Var.b;
                wf0Var.getClass();
                if (gh0Var.equals(wf0Var.h) && -1 == wf0Var.g) {
                    tf0 tf0Var = wf0Var.f;
                    if (tf0Var == null) {
                        tf0Var = wf0Var.c(-1);
                    }
                    if (tf0Var != null) {
                        tf0Var.c(e);
                    }
                    ky0 ky0Var = wf0Var.a;
                    yf0 yf0Var = new yf0(e);
                    ky0Var.getClass();
                    ky0Var.j(null, yf0Var);
                    return;
                }
                return;
            }
            return;
        }
        v7.o("This input is not added to any dispatcher.");
    }

    public final void onBackStarted(BackEvent backEvent) {
        backEvent.getClass();
        rf0 e = g30.e(backEvent);
        gh0 gh0Var = this.a;
        e3 e3Var = gh0Var.a;
        if (e3Var != null) {
            if (!gh0Var.b) {
                e3Var.j(gh0Var, e);
                gh0Var.b = true;
                return;
            }
            return;
        }
        v7.o("This input is not added to any dispatcher.");
    }
}
