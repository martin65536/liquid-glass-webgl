package defpackage;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import com.kyant.backdrop.catalog.R;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class bg extends Activity implements j80 {
    public final l80 e = new l80(this, true);

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v9, types: [i51, java.lang.Object] */
    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        keyEvent.getClass();
        View decorView = getWindow().getDecorView();
        decorView.getClass();
        if (m20.p(decorView, keyEvent)) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= 28) {
            return super.dispatchKeyEvent(keyEvent);
        }
        onUserInteraction();
        Window window = getWindow();
        if (window.hasFeature(8)) {
            ActionBar actionBar = getActionBar();
            if (keyEvent.getKeyCode() == 82 && actionBar != null) {
                boolean z = false;
                if (!m20.a) {
                    try {
                        m20.b = actionBar.getClass().getMethod("onMenuKeyEvent", KeyEvent.class);
                    } catch (NoSuchMethodException unused) {
                    }
                    m20.a = true;
                }
                Method method = m20.b;
                if (method != null) {
                    try {
                        Object invoke = method.invoke(actionBar, keyEvent);
                        if (invoke != null) {
                            z = ((Boolean) invoke).booleanValue();
                        }
                    } catch (IllegalAccessException | InvocationTargetException unused2) {
                    }
                }
                if (z) {
                    return true;
                }
            }
        }
        if (window.superDispatchKeyEvent(keyEvent)) {
            return true;
        }
        View decorView2 = window.getDecorView();
        int i = j51.a;
        KeyEvent.DispatcherState dispatcherState = null;
        if (Build.VERSION.SDK_INT < 28) {
            ArrayList arrayList = i51.d;
            i51 i51Var = (i51) decorView2.getTag(R.id.tag_unhandled_key_event_manager);
            i51 i51Var2 = i51Var;
            if (i51Var == null) {
                ?? obj = new Object();
                obj.a = null;
                obj.b = null;
                obj.c = null;
                decorView2.setTag(R.id.tag_unhandled_key_event_manager, obj);
                i51Var2 = obj;
            }
            if (keyEvent.getAction() == 0) {
                WeakHashMap weakHashMap = i51Var2.a;
                if (weakHashMap != null) {
                    weakHashMap.clear();
                }
                ArrayList arrayList2 = i51.d;
                if (!arrayList2.isEmpty()) {
                    synchronized (arrayList2) {
                        try {
                            if (i51Var2.a == null) {
                                i51Var2.a = new WeakHashMap();
                            }
                            for (int size = arrayList2.size() - 1; size >= 0; size--) {
                                ArrayList arrayList3 = i51.d;
                                View view = (View) ((WeakReference) arrayList3.get(size)).get();
                                if (view == null) {
                                    arrayList3.remove(size);
                                } else {
                                    i51Var2.a.put(view, Boolean.TRUE);
                                    for (ViewParent parent = view.getParent(); parent instanceof View; parent = parent.getParent()) {
                                        i51Var2.a.put((View) parent, Boolean.TRUE);
                                    }
                                }
                            }
                        } finally {
                        }
                    }
                }
            }
            View a = i51Var2.a(decorView2);
            if (keyEvent.getAction() == 0) {
                int keyCode = keyEvent.getKeyCode();
                if (a != null && !KeyEvent.isModifierKey(keyCode)) {
                    if (i51Var2.b == null) {
                        i51Var2.b = new SparseArray();
                    }
                    i51Var2.b.put(keyCode, new WeakReference(a));
                }
            }
            if (a != null) {
                return true;
            }
        }
        if (decorView2 != 0) {
            dispatcherState = decorView2.getKeyDispatcherState();
        }
        return keyEvent.dispatch(this, dispatcherState, this);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        keyEvent.getClass();
        View decorView = getWindow().getDecorView();
        decorView.getClass();
        if (m20.p(decorView, keyEvent)) {
            return true;
        }
        return super.dispatchKeyShortcutEvent(keyEvent);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        int i = xp0.f;
        vp0.b(this);
    }

    @Override // android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        bundle.getClass();
        l80 l80Var = this.e;
        l80Var.c("setCurrentState");
        l80Var.e(a80.g);
        super.onSaveInstanceState(bundle);
    }
}
