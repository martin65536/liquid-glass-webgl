package defpackage;

import android.graphics.Path;
import android.graphics.RectF;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class y5 {
    public final Path a;
    public RectF b;
    public float[] c;

    public y5(Path path) {
        this.a = path;
    }

    public final void a(float f, float f2, float f3, float f4, float f5, float f6) {
        this.a.cubicTo(f, f2, f3, f4, f5, f6);
    }

    public final void b(float f, float f2) {
        this.a.lineTo(f, f2);
    }
}
