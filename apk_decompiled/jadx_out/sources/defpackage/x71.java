package defpackage;

import android.view.View;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class x71 implements View.OnAttachStateChangeListener {
    public final /* synthetic */ View e;
    public final /* synthetic */ to0 f;

    public x71(View view, to0 to0Var) {
        this.e = view;
        this.f = to0Var;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        this.e.removeOnAttachStateChangeListener(this);
        this.f.C();
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
    }
}
