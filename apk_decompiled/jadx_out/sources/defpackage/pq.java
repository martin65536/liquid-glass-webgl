package defpackage;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.emoji2.text.EmojiCompatInitializer;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class pq implements yl {
    public final /* synthetic */ l80 e;

    public pq(EmojiCompatInitializer emojiCompatInitializer, l80 l80Var) {
        this.e = l80Var;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Object, java.lang.Runnable] */
    @Override // defpackage.yl
    public final void e(j80 j80Var) {
        Handler handler;
        if (Build.VERSION.SDK_INT >= 28) {
            handler = ji.a(Looper.getMainLooper());
        } else {
            handler = new Handler(Looper.getMainLooper());
        }
        handler.postDelayed(new Object(), 500L);
        this.e.f(this);
    }

    @Override // defpackage.yl
    public final void a(j80 j80Var) {
    }

    @Override // defpackage.yl
    public final void b(j80 j80Var) {
    }

    @Override // defpackage.yl
    public final void c(j80 j80Var) {
    }

    @Override // defpackage.yl
    public final void d(j80 j80Var) {
    }

    @Override // defpackage.yl
    public final void f(j80 j80Var) {
    }
}
