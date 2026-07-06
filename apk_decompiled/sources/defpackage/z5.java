package defpackage;

import android.graphics.PathMeasure;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class z5 {
    public final PathMeasure a;

    public z5(PathMeasure pathMeasure) {
        this.a = pathMeasure;
    }

    public final void a(float f, float f2, y5 y5Var) {
        if (d3.A(y5Var)) {
            this.a.getSegment(f, f2, y5Var.a, true);
            return;
        }
        throw new UnsupportedOperationException("Unable to obtain android.graphics.Path");
    }
}
