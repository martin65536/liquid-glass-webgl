package defpackage;

import android.os.Looper;
import android.view.Choreographer;
import java.util.Random;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class l6 extends ThreadLocal {
    public final /* synthetic */ int a;

    public /* synthetic */ l6(int i) {
        this.a = i;
    }

    @Override // java.lang.ThreadLocal
    public final Object initialValue() {
        switch (this.a) {
            case 0:
                Choreographer choreographer = Choreographer.getInstance();
                Looper myLooper = Looper.myLooper();
                if (myLooper != null) {
                    n6 n6Var = new n6(choreographer, o20.k(myLooper));
                    return jc0.z(n6Var, n6Var.p);
                }
                v7.o("no Looper on this thread");
                return null;
            default:
                return new Random();
        }
    }
}
