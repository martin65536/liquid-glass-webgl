package defpackage;

import android.view.inputmethod.InputMethodManager;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.UUID;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class c2 implements vu {
    public final /* synthetic */ int e;

    public /* synthetic */ c2(int i) {
        this.e = i;
    }

    @Override // defpackage.vu
    public final Object a() {
        switch (this.e) {
            case 0:
                c0 c0Var = ho0.e;
                return Integer.valueOf(ho0.e.a().nextInt(2147418112) + 65536);
            case 1:
                return UUID.randomUUID().toString();
            case 2:
                qy0 qy0Var = x9.a;
                return null;
            case 3:
                return x31.a;
            case 4:
                qy0 qy0Var2 = xh.a;
                return null;
            case 5:
                rh.b("Unexpected call to default provider");
                throw new RuntimeException();
            case 6:
                return dy.e;
            case 7:
                return sv0.f;
            case 8:
                throw new IllegalStateException("CompositionLocal LocalHostDefaultProvider not present");
            case 9:
                return (o5) fz.a.getValue();
            case 10:
                return f31.g(1, 1, 0, 28);
            case 11:
                return new hz("emptyImageVector", 1.0f, 1.0f, 1.0f, 1.0f, false, 224).a();
            case 12:
                return new ca((o5) fz.a.getValue());
            case 13:
                return (uj0) fz.c.getValue();
            case 14:
                return (iz) fz.b.getValue();
            case 15:
                try {
                    Field declaredField = InputMethodManager.class.getDeclaredField("mServedView");
                    declaredField.setAccessible(true);
                    Field declaredField2 = InputMethodManager.class.getDeclaredField("mNextServedView");
                    declaredField2.setAccessible(true);
                    Field declaredField3 = InputMethodManager.class.getDeclaredField("mH");
                    declaredField3.setAccessible(true);
                    return new nz(declaredField3, declaredField, declaredField2);
                } catch (NoSuchFieldException unused) {
                    return mz.a;
                }
            case 16:
                gi giVar = wz.a;
                return il.a;
            case 17:
                qy0 qy0Var3 = o10.a;
                return null;
            case 18:
                return new m70(0, 0);
            case 19:
                gi giVar2 = na0.a;
                return null;
            case 20:
                throw new IllegalStateException("CompositionLocal LocalLifecycleOwner not present");
            case 21:
                gi giVar3 = pa0.a;
                return null;
            case 22:
                gi giVar4 = qa0.a;
                return null;
            case 23:
                qy0 qy0Var4 = ra0.a;
                return x1.I;
            case 24:
                throw new IllegalStateException("CompositionLocal LocalSavedStateRegistryOwner not present");
            case 25:
                return new ij0();
            case 26:
                return eq0.a;
            case 27:
                qy0 qy0Var5 = gq0.a;
                return gl.a;
            case 28:
                return new ds0(new LinkedHashMap());
            default:
                qy0 qy0Var6 = gs0.a;
                return null;
        }
    }
}
