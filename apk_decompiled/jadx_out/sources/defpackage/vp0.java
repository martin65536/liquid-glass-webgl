package defpackage;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Build;
import defpackage.xp0;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class vp0 {
    /* JADX WARN: Multi-variable type inference failed */
    public static void a(Activity activity, z70 z70Var) {
        z70Var.getClass();
        if (activity instanceof j80) {
            l80 f = ((j80) activity).f();
            if (f instanceof l80) {
                f.d(z70Var);
            }
        }
    }

    public static void b(Activity activity) {
        if (Build.VERSION.SDK_INT >= 29) {
            xp0.a.Companion.getClass();
            activity.registerActivityLifecycleCallbacks(new xp0.a());
        }
        FragmentManager fragmentManager = activity.getFragmentManager();
        if (fragmentManager.findFragmentByTag("androidx.lifecycle.LifecycleDispatcher.report_fragment_tag") == null) {
            fragmentManager.beginTransaction().add(new xp0(), "androidx.lifecycle.LifecycleDispatcher.report_fragment_tag").commit();
            fragmentManager.executePendingTransactions();
        }
    }
}
