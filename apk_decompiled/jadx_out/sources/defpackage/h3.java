package defpackage;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.view.contentcapture.ContentCaptureSession;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract /* synthetic */ class h3 {
    public static /* bridge */ /* synthetic */ void C(Canvas canvas) {
        canvas.disableZ();
    }

    public static /* synthetic */ BlendModeColorFilter c(int i, BlendMode blendMode) {
        return new BlendModeColorFilter(i, blendMode);
    }

    public static /* bridge */ /* synthetic */ ContentCaptureSession d(Object obj) {
        return (ContentCaptureSession) obj;
    }

    public static /* synthetic */ void e() {
    }

    public static /* bridge */ /* synthetic */ void z(Canvas canvas) {
        canvas.enableZ();
    }
}
