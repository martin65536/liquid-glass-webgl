package defpackage;

import android.graphics.Rect;
import android.os.CancellationSignal;
import android.view.ScrollCaptureCallback;
import android.view.ScrollCaptureSession;
import java.util.function.Consumer;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class dh implements ScrollCaptureCallback {
    public final su0 a;
    public final z10 b;
    public final t70 c;
    public final b4 d;
    public final hj e;
    public final lp0 f;

    public dh(su0 su0Var, z10 z10Var, hj hjVar, t70 t70Var, b4 b4Var) {
        this.a = su0Var;
        this.b = z10Var;
        this.c = t70Var;
        this.d = b4Var;
        this.e = new hj(hjVar.e.i(hn.f));
        this.f = new lp0(z10Var.d - z10Var.b, new mj(this, null));
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x008f, code lost:
    
        if (r9 == r4) goto L35;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.Object a(defpackage.dh r11, android.view.ScrollCaptureSession r12, defpackage.z10 r13, defpackage.jj r14) {
        /*
            Method dump skipped, instructions count: 326
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dh.a(dh, android.view.ScrollCaptureSession, z10, jj):java.lang.Object");
    }

    public final void onScrollCaptureEnd(Runnable runnable) {
        f31.G(this.e, rg0.f, new d(this, runnable, null, 3), 2);
    }

    public final void onScrollCaptureImageRequest(ScrollCaptureSession scrollCaptureSession, CancellationSignal cancellationSignal, Rect rect, Consumer consumer) {
        final dy0 G = f31.G(this.e, null, new bh(this, scrollCaptureSession, rect, consumer, null, 0), 3);
        G.p(new q2(9, cancellationSignal));
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: eh
            @Override // android.os.CancellationSignal.OnCancelListener
            public final void onCancel() {
                dy0.this.a(null);
            }
        });
    }

    public final void onScrollCaptureSearch(CancellationSignal cancellationSignal, Consumer consumer) {
        consumer.accept(m20.I(this.b));
    }

    public final void onScrollCaptureStart(ScrollCaptureSession scrollCaptureSession, CancellationSignal cancellationSignal, Runnable runnable) {
        this.f.c = 0.0f;
        this.c.a.setValue(Boolean.TRUE);
        runnable.run();
    }
}
