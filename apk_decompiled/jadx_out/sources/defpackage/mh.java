package defpackage;

import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.view.ViewTreeObserver;
import java.util.HashMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class mh implements ComponentCallbacks2, ViewTreeObserver.OnWindowFocusChangeListener {
    public final /* synthetic */ nh e;

    public mh(nh nhVar) {
        this.e = nhVar;
    }

    @Override // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        this.e.d(configuration);
    }

    @Override // android.content.ComponentCallbacks
    public final void onLowMemory() {
        nh nhVar = this.e;
        ((HashMap) nhVar.f.f).clear();
        j2 j2Var = nhVar.g;
        synchronized (j2Var) {
            ((he0) j2Var.f).c();
        }
    }

    @Override // android.content.ComponentCallbacks2
    public final void onTrimMemory(int i) {
        nh nhVar = this.e;
        ((HashMap) nhVar.f.f).clear();
        j2 j2Var = nhVar.g;
        synchronized (j2Var) {
            ((he0) j2Var.f).c();
        }
    }

    @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
    public final void onWindowFocusChanged(boolean z) {
        this.e.s.a.setValue(Boolean.valueOf(z));
    }
}
