package defpackage;

import android.os.Handler;
import android.os.Looper;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class x3 extends z30 implements gv {
    public final /* synthetic */ int f;
    public final /* synthetic */ b4 g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ x3(b4 b4Var, int i) {
        super(1);
        this.f = i;
        this.g = b4Var;
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [ep0, java.lang.Object] */
    @Override // defpackage.gv
    public final Object e(Object obj) {
        Looper looper;
        int i = this.f;
        x31 x31Var = x31.a;
        b4 b4Var = this.g;
        switch (i) {
            case 0:
                int i2 = ((bt) obj).a;
                lt ltVar = (lt) b4Var.getFocusOwner();
                b4 b4Var2 = ltVar.a;
                ?? obj2 = new Object();
                obj2.e = Boolean.FALSE;
                pt f = ltVar.f();
                Boolean e = ltVar.e(i2, b4Var2.getEmbeddedViewFocusRect(), new kt(i2, obj2));
                if ((!o20.e(e, Boolean.TRUE) || f == ltVar.f()) && e != null && obj2.e != null && e.booleanValue()) {
                    ((Boolean) obj2.e).getClass();
                }
                return x31Var;
            default:
                vu vuVar = (vu) obj;
                b4Var.getUncaughtExceptionHandler$ui();
                Handler handler = b4Var.getHandler();
                if (handler != null) {
                    looper = handler.getLooper();
                } else {
                    looper = null;
                }
                if (looper == Looper.myLooper()) {
                    vuVar.a();
                } else {
                    Handler handler2 = b4Var.getHandler();
                    if (handler2 != null) {
                        handler2.post(new n3(vuVar, 1));
                    }
                }
                return x31Var;
        }
    }
}
