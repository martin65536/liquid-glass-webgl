package defpackage;

import android.os.Build;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class gh0 extends vf0 {
    public final OnBackInvokedDispatcher c;
    public final int d;
    public final OnBackInvokedCallback e;
    public boolean f;

    public gh0(OnBackInvokedDispatcher onBackInvokedDispatcher, int i) {
        OnBackInvokedCallback ih0Var;
        this.c = onBackInvokedDispatcher;
        this.d = i;
        if (Build.VERSION.SDK_INT == 33) {
            ih0Var = new OnBackInvokedCallback() { // from class: hh0
                public final void onBackInvoked() {
                    gh0.this.a();
                }
            };
        } else {
            ih0Var = new ih0(this);
        }
        this.e = ih0Var;
    }

    @Override // defpackage.vf0
    public final void b(boolean z) {
        OnBackInvokedCallback onBackInvokedCallback = this.e;
        if (z && !this.f) {
            this.c.registerOnBackInvokedCallback(this.d, onBackInvokedCallback);
            this.f = true;
        } else if (!z && this.f) {
            this.c.unregisterOnBackInvokedCallback(onBackInvokedCallback);
            this.f = false;
        }
    }
}
