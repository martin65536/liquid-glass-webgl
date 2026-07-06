package defpackage;

import android.view.ViewConfiguration;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class r6 implements l51 {
    public final ViewConfiguration a;

    public r6(ViewConfiguration viewConfiguration) {
        this.a = viewConfiguration;
    }

    @Override // defpackage.l51
    public final float a() {
        return this.a.getScaledMaximumFlingVelocity();
    }

    @Override // defpackage.l51
    public final long b() {
        return ViewConfiguration.getDoubleTapTimeout();
    }

    @Override // defpackage.l51
    public final long c() {
        return ViewConfiguration.getLongPressTimeout();
    }

    @Override // defpackage.l51
    public final float d() {
        return this.a.getScaledTouchSlop();
    }

    @Override // defpackage.l51
    public final long e() {
        return k81.c(48.0f, 48.0f);
    }
}
