package defpackage;

import android.graphics.Rect;
import android.view.WindowInsets;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class f71 extends e71 {
    public f71(k71 k71Var, WindowInsets windowInsets) {
        super(k71Var, windowInsets);
    }

    @Override // defpackage.y61, defpackage.g71
    public List<Rect> e(int i) {
        List<Rect> boundingRects;
        boundingRects = this.c.getBoundingRects(j71.a(i));
        return boundingRects;
    }

    @Override // defpackage.y61, defpackage.g71
    public List<Rect> f(int i) {
        List<Rect> boundingRectsIgnoringVisibility;
        boundingRectsIgnoringVisibility = this.c.getBoundingRectsIgnoringVisibility(j71.a(i));
        return boundingRectsIgnoringVisibility;
    }

    @Override // defpackage.y61, defpackage.g71
    public void p() {
    }
}
