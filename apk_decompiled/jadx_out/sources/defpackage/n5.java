package defpackage;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import com.kyant.backdrop.catalog.R;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class n5 implements ex {
    public static boolean f = true;
    public final b4 a;
    public final Object b = new Object();
    public r51 c;
    public boolean d;
    public final l5 e;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [l5, android.content.ComponentCallbacks, java.lang.Object] */
    public n5(b4 b4Var) {
        this.a = b4Var;
        ?? obj = new Object();
        this.e = obj;
        if (b4Var.isAttachedToWindow()) {
            Context context = b4Var.getContext();
            if (!this.d) {
                context.getApplicationContext().registerComponentCallbacks(obj);
                this.d = true;
            }
        }
        b4Var.addOnAttachStateChangeListener(new m5(0, this));
    }

    public final hx a() {
        jx rxVar;
        jx jxVar;
        hx hxVar;
        synchronized (this.b) {
            try {
                b4 b4Var = this.a;
                int i = Build.VERSION.SDK_INT;
                if (i >= 29) {
                    b4Var.getUniqueDrawingId();
                }
                if (i >= 29) {
                    jxVar = new px();
                } else {
                    if (f) {
                        try {
                            rxVar = new nx(this.a, new zc(), new yc());
                        } catch (Throwable unused) {
                            f = false;
                            rxVar = new rx(b(this.a));
                        }
                    } else {
                        rxVar = new rx(b(this.a));
                    }
                    jxVar = rxVar;
                }
                hxVar = new hx(jxVar);
            } catch (Throwable th) {
                throw th;
            }
        }
        return hxVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.view.View, sp, android.view.ViewGroup, r51] */
    public final sp b(b4 b4Var) {
        r51 r51Var = this.c;
        if (r51Var == null) {
            ?? viewGroup = new ViewGroup(b4Var.getContext());
            viewGroup.setClipChildren(false);
            viewGroup.setClipToPadding(false);
            viewGroup.setTag(R.id.hide_graphics_layer_in_inspector_tag, Boolean.TRUE);
            b4Var.addView((View) viewGroup, -1);
            this.c = viewGroup;
            return viewGroup;
        }
        return r51Var;
    }

    public final void c(hx hxVar) {
        synchronized (this.b) {
            if (!hxVar.s) {
                hxVar.s = true;
                hxVar.b();
            }
        }
    }
}
