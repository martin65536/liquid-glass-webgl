package defpackage;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class sn0 extends ar {
    final /* synthetic */ tn0 this$0;

    /* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
    /* loaded from: classes.dex */
    public static final class a extends ar {
        final /* synthetic */ tn0 this$0;

        public a(tn0 tn0Var) {
            this.this$0 = tn0Var;
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPostResumed(Activity activity) {
            activity.getClass();
            this.this$0.a();
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPostStarted(Activity activity) {
            activity.getClass();
            tn0 tn0Var = this.this$0;
            int i = tn0Var.e + 1;
            tn0Var.e = i;
            if (i == 1 && tn0Var.h) {
                tn0Var.j.d(z70.ON_START);
                tn0Var.h = false;
            }
        }
    }

    public sn0(tn0 tn0Var) {
        this.this$0 = tn0Var;
    }

    @Override // defpackage.ar, android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        activity.getClass();
        if (Build.VERSION.SDK_INT < 29) {
            int i = xp0.f;
            Fragment findFragmentByTag = activity.getFragmentManager().findFragmentByTag("androidx.lifecycle.LifecycleDispatcher.report_fragment_tag");
            findFragmentByTag.getClass();
            ((xp0) findFragmentByTag).e = this.this$0.l;
        }
    }

    @Override // defpackage.ar, android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        activity.getClass();
        tn0 tn0Var = this.this$0;
        int i = tn0Var.f - 1;
        tn0Var.f = i;
        if (i == 0) {
            Handler handler = tn0Var.i;
            handler.getClass();
            handler.postDelayed(tn0Var.k, 700L);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPreCreated(Activity activity, Bundle bundle) {
        activity.getClass();
        xi.i(activity, new a(this.this$0));
    }

    @Override // defpackage.ar, android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        activity.getClass();
        tn0 tn0Var = this.this$0;
        int i = tn0Var.e - 1;
        tn0Var.e = i;
        if (i == 0 && tn0Var.g) {
            tn0Var.j.d(z70.ON_STOP);
            tn0Var.h = true;
        }
    }
}
