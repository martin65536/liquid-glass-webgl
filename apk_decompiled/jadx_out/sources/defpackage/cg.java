package defpackage;

import android.app.PictureInPictureUiState;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Trace;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.kyant.backdrop.catalog.MainActivity;
import com.kyant.backdrop.catalog.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class cg extends bg implements w51, cy, ps0, nh0, sf0, i2 {
    public final gj f;
    public final j2 g;
    public final c4 h;
    public wb0 i;
    public final yf j;
    public final a01 k;
    public final ag l;
    public final CopyOnWriteArrayList m;
    public final CopyOnWriteArrayList n;
    public final CopyOnWriteArrayList o;
    public final CopyOnWriteArrayList p;
    public final CopyOnWriteArrayList q;
    public final CopyOnWriteArrayList r;
    public final CopyOnWriteArrayList s;
    public boolean t;
    public boolean u;
    public final a01 v;
    public final a01 w;

    public cg() {
        gj gjVar = new gj();
        this.f = gjVar;
        MainActivity mainActivity = (MainActivity) this;
        this.g = new j2(13);
        os0 os0Var = new os0(this, new f6(6, this));
        c4 c4Var = new c4(os0Var, 20);
        this.h = c4Var;
        this.j = new yf(mainActivity);
        int i = 1;
        this.k = new a01(new sf(mainActivity, i));
        new AtomicInteger();
        this.l = new ag(mainActivity);
        this.m = new CopyOnWriteArrayList();
        this.n = new CopyOnWriteArrayList();
        this.o = new CopyOnWriteArrayList();
        this.p = new CopyOnWriteArrayList();
        this.q = new CopyOnWriteArrayList();
        this.r = new CopyOnWriteArrayList();
        this.s = new CopyOnWriteArrayList();
        this.v = new a01(new sf(mainActivity, 2));
        l80 l80Var = this.e;
        if (l80Var != null) {
            int i2 = 0;
            l80Var.a(new uf(i2, mainActivity));
            this.e.a(new uf(i, mainActivity));
            this.e.a(new vo0(i, mainActivity));
            os0Var.a();
            a80 a80Var = this.e.c;
            if (a80Var != a80.f && a80Var != a80.g) {
                v7.m("Failed requirement.");
                throw null;
            }
            int i3 = 3;
            if (((c4) c4Var.g).q("androidx.lifecycle.internal.SavedStateHandlesProvider") == null) {
                ks0 ks0Var = new ks0((c4) c4Var.g, mainActivity);
                ((c4) c4Var.g).u("androidx.lifecycle.internal.SavedStateHandlesProvider", ks0Var);
                this.e.a(new vo0(i3, ks0Var));
            }
            if (Build.VERSION.SDK_INT == 23) {
                this.e.a(new oz(mainActivity));
            }
            ((c4) c4Var.g).u("android:support:activity-result", new vf(i2, mainActivity));
            wf wfVar = new wf(mainActivity);
            cg cgVar = gjVar.b;
            if (cgVar != null) {
                wfVar.a(cgVar);
            }
            gjVar.a.add(wfVar);
            new a01(new sf(mainActivity, i3));
            this.w = new a01(new sf(mainActivity, 4));
            return;
        }
        v7.o("getLifecycle() returned null in ComponentActivity's constructor. Please make sure you are lazily constructing your Lifecycle in the first call to getLifecycle() rather than relying on field initialization.");
        throw null;
    }

    public static void a(MainActivity mainActivity) {
        try {
            super.onBackPressed();
        } catch (IllegalStateException e) {
            if (o20.e(e.getMessage(), "Can not perform this action after onSaveInstanceState")) {
            } else {
                throw e;
            }
        } catch (NullPointerException e2) {
            if (!o20.e(e2.getMessage(), "Attempt to invoke virtual method 'android.os.Handler android.app.FragmentHostCallback.getHandler()' on a null object reference")) {
                throw e2;
            }
        }
    }

    @Override // android.app.Activity
    public final void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        d();
        View decorView = getWindow().getDecorView();
        decorView.getClass();
        this.j.a(decorView);
        super.addContentView(view, layoutParams);
    }

    @Override // defpackage.ps0
    public final c4 b() {
        return (c4) this.h.g;
    }

    public final wb0 c() {
        if (getApplication() != null) {
            if (this.i == null) {
                xf xfVar = (xf) getLastNonConfigurationInstance();
                if (xfVar != null) {
                    this.i = xfVar.a;
                }
                if (this.i == null) {
                    this.i = new wb0(2);
                }
            }
            wb0 wb0Var = this.i;
            wb0Var.getClass();
            return wb0Var;
        }
        v7.o("Your activity is not yet attached to the Application instance. You can't request ViewModel before onCreate call.");
        return null;
    }

    public final void d() {
        View decorView = getWindow().getDecorView();
        decorView.getClass();
        decorView.setTag(R.id.view_tree_lifecycle_owner, this);
        View decorView2 = getWindow().getDecorView();
        decorView2.getClass();
        decorView2.setTag(R.id.view_tree_view_model_store_owner, this);
        View decorView3 = getWindow().getDecorView();
        decorView3.getClass();
        decorView3.setTag(R.id.view_tree_saved_state_registry_owner, this);
        View decorView4 = getWindow().getDecorView();
        decorView4.getClass();
        decorView4.setTag(R.id.view_tree_on_back_pressed_dispatcher_owner, this);
        View decorView5 = getWindow().getDecorView();
        decorView5.getClass();
        decorView5.setTag(R.id.report_drawn, this);
        View decorView6 = getWindow().getDecorView();
        decorView6.getClass();
        decorView6.setTag(R.id.view_tree_navigation_event_dispatcher_owner, this);
    }

    @Override // defpackage.j80
    public final l80 f() {
        return this.e;
    }

    @Override // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        if (!this.l.a(i, i2, intent)) {
            super.onActivityResult(i, i2, intent);
        }
    }

    @Override // android.app.Activity
    public final void onBackPressed() {
        ((fn) this.v.getValue()).a();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        configuration.getClass();
        super.onConfigurationChanged(configuration);
        Iterator it = this.m.iterator();
        it.getClass();
        while (it.hasNext()) {
            ((ui) it.next()).accept(configuration);
        }
    }

    @Override // defpackage.bg, android.app.Activity
    public void onCreate(Bundle bundle) {
        this.h.s(bundle);
        gj gjVar = this.f;
        gjVar.getClass();
        gjVar.b = this;
        Iterator it = gjVar.a.iterator();
        while (it.hasNext()) {
            ((wf) it.next()).a(this);
        }
        super.onCreate(bundle);
        int i = xp0.f;
        vp0.b(this);
        getPackageManager().hasSystemFeature("android.software.picture_in_picture");
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean onCreatePanelMenu(int i, Menu menu) {
        menu.getClass();
        if (i == 0) {
            super.onCreatePanelMenu(i, menu);
            getMenuInflater();
            Iterator it = ((CopyOnWriteArrayList) this.g.f).iterator();
            if (it.hasNext()) {
                it.next().getClass();
                v7.d();
                return false;
            }
            return true;
        }
        return true;
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean onMenuItemSelected(int i, MenuItem menuItem) {
        menuItem.getClass();
        if (super.onMenuItemSelected(i, menuItem)) {
            return true;
        }
        if (i == 0) {
            Iterator it = ((CopyOnWriteArrayList) this.g.f).iterator();
            if (it.hasNext()) {
                it.next().getClass();
                v7.d();
            }
        }
        return false;
    }

    @Override // android.app.Activity
    public final void onMultiWindowModeChanged(boolean z, Configuration configuration) {
        configuration.getClass();
        this.t = true;
        try {
            super.onMultiWindowModeChanged(z, configuration);
            this.t = false;
            Iterator it = this.p.iterator();
            it.getClass();
            while (it.hasNext()) {
                ((ui) it.next()).accept(new rt(11));
            }
        } catch (Throwable th) {
            this.t = false;
            throw th;
        }
    }

    @Override // android.app.Activity
    public final void onNewIntent(Intent intent) {
        intent.getClass();
        super.onNewIntent(intent);
        Iterator it = this.o.iterator();
        it.getClass();
        while (it.hasNext()) {
            ((ui) it.next()).accept(intent);
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onPanelClosed(int i, Menu menu) {
        menu.getClass();
        Iterator it = ((CopyOnWriteArrayList) this.g.f).iterator();
        if (!it.hasNext()) {
            super.onPanelClosed(i, menu);
        } else {
            it.next().getClass();
            v7.d();
        }
    }

    @Override // android.app.Activity
    public final void onPictureInPictureModeChanged(boolean z, Configuration configuration) {
        configuration.getClass();
        this.u = true;
        try {
            super.onPictureInPictureModeChanged(z, configuration);
            this.u = false;
            Iterator it = this.q.iterator();
            it.getClass();
            while (it.hasNext()) {
                ((ui) it.next()).accept(new rt(15));
            }
        } catch (Throwable th) {
            this.u = false;
            throw th;
        }
    }

    @Override // android.app.Activity
    public final void onPictureInPictureUiStateChanged(PictureInPictureUiState pictureInPictureUiState) {
        rt rtVar;
        pictureInPictureUiState.getClass();
        super.onPictureInPictureUiStateChanged(pictureInPictureUiState);
        int i = Build.VERSION.SDK_INT;
        int i2 = 16;
        if (i >= 35) {
            pictureInPictureUiState.isStashed();
            pictureInPictureUiState.isTransitioningToPip();
            rtVar = new rt(i2);
        } else if (i >= 31) {
            pictureInPictureUiState.isStashed();
            rtVar = new rt(i2);
        } else {
            rtVar = new rt(i2);
        }
        Iterator it = this.r.iterator();
        it.getClass();
        while (it.hasNext()) {
            ((ui) it.next()).accept(rtVar);
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean onPreparePanel(int i, View view, Menu menu) {
        menu.getClass();
        if (i == 0) {
            super.onPreparePanel(i, view, menu);
            Iterator it = ((CopyOnWriteArrayList) this.g.f).iterator();
            if (it.hasNext()) {
                it.next().getClass();
                v7.d();
                return false;
            }
            return true;
        }
        return true;
    }

    @Override // android.app.Activity
    public final void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        strArr.getClass();
        iArr.getClass();
        if (!this.l.a(i, -1, new Intent().putExtra("androidx.activity.result.contract.extra.PERMISSIONS", strArr).putExtra("androidx.activity.result.contract.extra.PERMISSION_GRANT_RESULTS", iArr))) {
            super.onRequestPermissionsResult(i, strArr, iArr);
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object, xf] */
    @Override // android.app.Activity
    public final Object onRetainNonConfigurationInstance() {
        xf xfVar;
        wb0 wb0Var = this.i;
        if (wb0Var == null && (xfVar = (xf) getLastNonConfigurationInstance()) != null) {
            wb0Var = xfVar.a;
        }
        if (wb0Var == null) {
            return null;
        }
        ?? obj = new Object();
        obj.a = wb0Var;
        return obj;
    }

    @Override // defpackage.bg, android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.getClass();
        l80 l80Var = this.e;
        if (l80Var != null) {
            l80Var.getClass();
            l80Var.c("setCurrentState");
            l80Var.e(a80.g);
        }
        super.onSaveInstanceState(bundle);
        this.h.t(bundle);
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks2
    public final void onTrimMemory(int i) {
        super.onTrimMemory(i);
        Iterator it = this.n.iterator();
        it.getClass();
        while (it.hasNext()) {
            ((ui) it.next()).accept(Integer.valueOf(i));
        }
    }

    @Override // android.app.Activity
    public final void onUserLeaveHint() {
        super.onUserLeaveHint();
        Iterator it = this.s.iterator();
        it.getClass();
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
        }
    }

    @Override // android.app.Activity
    public final void reportFullyDrawn() {
        try {
            if (n30.w()) {
                n30.f("reportFullyDrawn() for ComponentActivity");
            }
            super.reportFullyDrawn();
            uu uuVar = (uu) this.k.getValue();
            synchronized (uuVar.a) {
                try {
                    uuVar.b = true;
                    ArrayList arrayList = uuVar.c;
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        ((vu) obj).a();
                    }
                    uuVar.c.clear();
                } catch (Throwable th) {
                    throw th;
                }
            }
        } finally {
            Trace.endSection();
        }
    }

    @Override // android.app.Activity
    public final void setContentView(int i) {
        d();
        View decorView = getWindow().getDecorView();
        decorView.getClass();
        this.j.a(decorView);
        super.setContentView(i);
    }

    @Override // android.app.Activity
    public final void startActivityForResult(Intent intent, int i) {
        intent.getClass();
        super.startActivityForResult(intent, i);
    }

    @Override // android.app.Activity
    public final void startIntentSenderForResult(IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4) {
        intentSender.getClass();
        super.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4);
    }

    @Override // android.app.Activity
    public final void startActivityForResult(Intent intent, int i, Bundle bundle) {
        intent.getClass();
        super.startActivityForResult(intent, i, bundle);
    }

    @Override // android.app.Activity
    public final void startIntentSenderForResult(IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4, Bundle bundle) {
        intentSender.getClass();
        super.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4, bundle);
    }

    @Override // android.app.Activity
    public void setContentView(View view) {
        d();
        View decorView = getWindow().getDecorView();
        decorView.getClass();
        this.j.a(decorView);
        super.setContentView(view);
    }

    @Override // android.app.Activity
    public final void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        d();
        View decorView = getWindow().getDecorView();
        decorView.getClass();
        this.j.a(decorView);
        super.setContentView(view, layoutParams);
    }

    @Override // android.app.Activity
    public final void onMultiWindowModeChanged(boolean z) {
        if (this.t) {
            return;
        }
        Iterator it = this.p.iterator();
        it.getClass();
        while (it.hasNext()) {
            ((ui) it.next()).accept(new rt(11));
        }
    }

    @Override // android.app.Activity
    public final void onPictureInPictureModeChanged(boolean z) {
        if (this.u) {
            return;
        }
        Iterator it = this.q.iterator();
        it.getClass();
        while (it.hasNext()) {
            ((ui) it.next()).accept(new rt(15));
        }
    }
}
