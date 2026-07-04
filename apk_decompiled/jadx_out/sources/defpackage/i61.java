package defpackage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class i61 extends AnimatorListenerAdapter {
    public final /* synthetic */ p61 a;
    public final /* synthetic */ View b;

    public i61(p61 p61Var, View view) {
        this.a = p61Var;
        this.b = view;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        p61 p61Var = this.a;
        p61Var.a.e(1.0f);
        l61.f(p61Var, this.b);
    }
}
