package defpackage;

import android.media.ImageReader;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class h40 implements ImageReader.OnImageAvailableListener {
    public final /* synthetic */ pc a;

    public h40(pc pcVar) {
        this.a = pcVar;
    }

    @Override // android.media.ImageReader.OnImageAvailableListener
    public final void onImageAvailable(ImageReader imageReader) {
        this.a.u(imageReader.acquireLatestImage());
    }
}
