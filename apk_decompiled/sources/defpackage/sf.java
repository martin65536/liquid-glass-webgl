package defpackage;

import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.kyant.backdrop.catalog.MainActivity;
import java.util.LinkedHashSet;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class sf implements vu {
    public final /* synthetic */ int e;
    public final /* synthetic */ MainActivity f;

    public /* synthetic */ sf(MainActivity mainActivity, int i) {
        this.e = i;
        this.f = mainActivity;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [vf0, java.lang.Object] */
    @Override // defpackage.vu
    public final Object a() {
        Bundle bundle;
        int i = this.e;
        MainActivity mainActivity = this.f;
        switch (i) {
            case 0:
                mainActivity.reportFullyDrawn();
                return x31.a;
            case 1:
                return new uu(mainActivity.j, new sf(mainActivity, 0));
            case 2:
                ?? obj = new Object();
                e3 e3Var = ((lh0) ((mh0) mainActivity.w.getValue()).b.getValue()).c;
                if (((LinkedHashSet) e3Var.d).add(obj)) {
                    ((wf0) e3Var.b).a(e3Var, obj, -1);
                }
                return obj;
            case 3:
                Application application = mainActivity.getApplication();
                if (mainActivity.getIntent() != null) {
                    bundle = mainActivity.getIntent().getExtras();
                } else {
                    bundle = null;
                }
                return new qs0(application, mainActivity, bundle);
            case 4:
                mh0 mh0Var = new mh0(new n(3, mainActivity));
                if (Build.VERSION.SDK_INT >= 33) {
                    if (!o20.e(Looper.myLooper(), Looper.getMainLooper())) {
                        new Handler(Looper.getMainLooper()).post(new r4(1, mainActivity, mh0Var));
                    } else {
                        mainActivity.e.a(new tf(mh0Var, mainActivity));
                    }
                }
                return mh0Var;
            default:
                return jc0.s(mainActivity);
        }
    }
}
