package defpackage;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Choreographer;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class zx {
    public static final /* synthetic */ int a = 0;
    private static volatile Choreographer choreographer;

    static {
        Object jq0Var;
        try {
            jq0Var = new xx(b(Looper.getMainLooper()));
        } catch (Throwable th) {
            jq0Var = new jq0(th);
        }
        if (jq0Var instanceof jq0) {
            jq0Var = null;
        }
    }

    public static final void a(pc pcVar) {
        Choreographer choreographer2 = choreographer;
        if (choreographer2 == null) {
            choreographer2 = Choreographer.getInstance();
            choreographer2.getClass();
            choreographer = choreographer2;
        }
        choreographer2.postFrameCallback(new yx(pcVar));
    }

    public static final Handler b(Looper looper) {
        if (Build.VERSION.SDK_INT >= 28) {
            Object invoke = Handler.class.getDeclaredMethod("createAsync", Looper.class).invoke(null, looper);
            invoke.getClass();
            return (Handler) invoke;
        }
        try {
            return (Handler) Handler.class.getDeclaredConstructor(Looper.class, Handler.Callback.class, Boolean.TYPE).newInstance(looper, null, Boolean.TRUE);
        } catch (NoSuchMethodException unused) {
            return new Handler(looper);
        }
    }

    public static final Object c(d dVar) {
        Choreographer choreographer2 = choreographer;
        if (choreographer2 != null) {
            pc pcVar = new pc(1, t20.w(dVar));
            pcVar.s();
            choreographer2.postFrameCallback(new yx(pcVar));
            return pcVar.p();
        }
        pc pcVar2 = new pc(1, t20.w(dVar));
        pcVar2.s();
        if (Looper.myLooper() == Looper.getMainLooper()) {
            a(pcVar2);
        } else {
            bm bmVar = mn.a;
            yb0.a.g(pcVar2.i, new z3(1, pcVar2));
        }
        return pcVar2.p();
    }
}
