package defpackage;

import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewTreeObserver;
import com.kyant.backdrop.catalog.MainActivity;
import java.util.concurrent.Executor;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class yf implements ViewTreeObserver.OnDrawListener, Runnable, Executor {
    public final long e = SystemClock.uptimeMillis() + 10000;
    public Runnable f;
    public boolean g;
    public final /* synthetic */ MainActivity h;

    public yf(MainActivity mainActivity) {
        this.h = mainActivity;
    }

    public final void a(View view) {
        if (!this.g) {
            this.g = true;
            view.getViewTreeObserver().addOnDrawListener(this);
        }
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        runnable.getClass();
        this.f = runnable;
        View decorView = this.h.getWindow().getDecorView();
        decorView.getClass();
        if (this.g) {
            if (o20.e(Looper.myLooper(), Looper.getMainLooper())) {
                decorView.invalidate();
                return;
            } else {
                decorView.postInvalidate();
                return;
            }
        }
        decorView.postOnAnimation(new n(4, this));
    }

    @Override // android.view.ViewTreeObserver.OnDrawListener
    public final void onDraw() {
        boolean z;
        Runnable runnable = this.f;
        if (runnable != null) {
            runnable.run();
            this.f = null;
            uu uuVar = (uu) this.h.k.getValue();
            synchronized (uuVar.a) {
                z = uuVar.b;
            }
            if (z) {
                this.g = false;
                this.h.getWindow().getDecorView().post(this);
                return;
            }
            return;
        }
        if (SystemClock.uptimeMillis() > this.e) {
            this.g = false;
            this.h.getWindow().getDecorView().post(this);
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.h.getWindow().getDecorView().getViewTreeObserver().removeOnDrawListener(this);
    }
}
