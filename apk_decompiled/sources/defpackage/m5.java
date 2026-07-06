package defpackage;

import android.content.Context;
import android.view.View;
import android.view.ViewParent;
import com.kyant.backdrop.catalog.R;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class m5 implements View.OnAttachStateChangeListener {
    public final /* synthetic */ int e;
    public final /* synthetic */ Object f;

    public /* synthetic */ m5(int i, Object obj) {
        this.e = i;
        this.f = obj;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        switch (this.e) {
            case 0:
                n5 n5Var = (n5) this.f;
                Context context = view.getContext();
                if (!n5Var.d) {
                    context.getApplicationContext().registerComponentCallbacks(n5Var.e);
                    n5Var.d = true;
                    return;
                }
                return;
            case 1:
            default:
                return;
        }
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        lv0<Object> csVar;
        Boolean bool;
        boolean z;
        int i = this.e;
        Object obj = this.f;
        switch (i) {
            case 0:
                n5 n5Var = (n5) obj;
                Context context = view.getContext();
                if (n5Var.d) {
                    context.getApplicationContext().unregisterComponentCallbacks(n5Var.e);
                    n5Var.d = false;
                    return;
                }
                return;
            case 1:
                p pVar = (p) obj;
                ViewParent parent = pVar.getParent();
                n51 n51Var = n51.l;
                if (parent == null) {
                    csVar = hr.a;
                } else {
                    csVar = new cs(new f6(8, parent), n51Var, 1);
                }
                for (Object obj2 : csVar) {
                    if (obj2 instanceof View) {
                        View view2 = (View) obj2;
                        view2.getClass();
                        Object tag = view2.getTag(R.id.is_pooling_container_tag);
                        if (tag instanceof Boolean) {
                            bool = (Boolean) tag;
                        } else {
                            bool = null;
                        }
                        if (bool != null) {
                            z = bool.booleanValue();
                        } else {
                            z = false;
                        }
                        if (z) {
                            return;
                        }
                    }
                }
                pVar.d();
                return;
            default:
                view.removeOnAttachStateChangeListener(this);
                ((dy0) obj).a(null);
                return;
        }
    }

    private final void a(View view) {
    }

    private final void b(View view) {
    }
}
