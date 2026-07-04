package defpackage;

import android.animation.ValueAnimator;
import android.view.View;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class j61 implements Runnable {
    public final /* synthetic */ View e;
    public final /* synthetic */ p61 f;
    public final /* synthetic */ c4 g;
    public final /* synthetic */ ValueAnimator h;

    public j61(View view, p61 p61Var, c4 c4Var, ValueAnimator valueAnimator) {
        this.e = view;
        this.f = p61Var;
        this.g = c4Var;
        this.h = valueAnimator;
    }

    @Override // java.lang.Runnable
    public final void run() {
        l61.i(this.e, this.f, this.g);
        this.h.start();
    }
}
